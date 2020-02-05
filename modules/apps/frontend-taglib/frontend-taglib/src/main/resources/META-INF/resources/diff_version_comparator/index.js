/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayCard from '@clayui/card';
import {ClayInput} from '@clayui/form';
import {useIsMounted} from 'frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';

import Diff from './components/Diff';
import Filter from './components/Filter';
import List from './components/List';
import LocaleSelector from './components/LocaleSelector';
import Selector from './components/Selector';
import sub from './utils/sub';

function Comparator({
	availableLocales,
	diffHtmlResults,
	diffVersions,
	languageId,
	nextVersion,
	portletNamespace,
	portletURL,
	previousVersion,
	resourceURL,
	sourceVersion,
	targetVersion
}) {
	const isMounted = useIsMounted();

	const availableVersions = useMemo(
		() =>
			diffVersions.filter(
				diffVersion =>
					sourceVersion !== diffVersion.version &&
					targetVersion !== diffVersion.version
			),
		[diffVersions, sourceVersion, targetVersion]
	);

	const selectableVersions = useMemo(
		() => availableVersions.filter(version => version.inRange),
		[availableVersions]
	);

	const getDiffURL = useCallback(
		filterTargetVersion => {
			const diffURL = new URL(resourceURL);

			diffURL.searchParams.append(
				`_${portletNamespace}_filterSourceVersion`,
				sourceVersion
			);

			diffURL.searchParams.append(
				`_${portletNamespace}_filterTargetVersion`,
				filterTargetVersion
			);

			return diffURL.toString();
		},
		[portletNamespace, resourceURL, sourceVersion]
	);

	const handleFilterChange = useCallback(
		event => {
			const query = event.target.value.toLowerCase();

			const visibleVersions = selectableVersions.filter(
				({label, userName}) => {
					return (
						label.toLowerCase().includes(query) ||
						userName.toLowerCase().includes(query)
					);
				}
			);

			setFilterQuery(query);
			setVisibleVersions(visibleVersions);
		},
		[selectableVersions]
	);

	const [diff, setDiff] = useState(diffHtmlResults);
	const [diffURL, setDiffURL] = useState(getDiffURL(targetVersion));
	const [filterQuery, setFilterQuery] = useState('');
	const [selectedLanguageId, setSelectedLanguageId] = useState(languageId);
	const [selectedVersion, setSelectedVersion] = useState(null);
	const [visibleVersions, setVisibleVersions] = useState(selectableVersions);

	const diffCache = useRef({[diffURL]: diff});
	const formRef = useRef();

	const handleTargetChange = useCallback(
		event => {
			const target = event.target.closest('[data-version]');

			const currentTargetVersion = target
				? target.getAttribute('data-version')
				: null;

			const selectedVersion = selectableVersions.find(version => {
				return version.version === currentTargetVersion;
			});

			setDiffURL(getDiffURL(currentTargetVersion || targetVersion));
			setSelectedVersion(selectedVersion);
		},
		[getDiffURL, selectableVersions, targetVersion]
	);

	useEffect(() => {
		const cached = diffCache.current[diffURL];

		if (cached) {
			setDiff(cached);
		}
		else {
			fetch(diffURL)
				.then(res => res.text())
				.then(text => {
					diffCache.current[diffURL] = text;
				})
				.catch(() => {
					diffCache.current[diffURL] = Liferay.Language.get(
						'an-error-occurred-while-processing-the-requested-resource'
					);
				})
				.finally(() => {
					if (isMounted()) {
						setDiff(diffCache.current[diffURL]);
					}
				});
		}
	}, [diffURL, isMounted]);

	return (
		<form
			action={portletURL}
			className="container-fluid-1280 diff-version-comparator"
			method="post"
			name={`${portletNamespace}diffVersionFm`}
			ref={formRef}
		>
			<ClayInput
				name={`_${portletNamespace}_sourceVersion`}
				type="hidden"
				value={sourceVersion}
			/>

			<ClayInput
				name={`_${portletNamespace}_targetVersion`}
				type="hidden"
				value={targetVersion}
			/>

			<ClayCard className="main-content-card" horizontal>
				<ClayCard.Body>
					<ClayCard.Description displayType="title">
						{Liferay.Language.get(
							'you-are-comparing-these-versions'
						)}
					</ClayCard.Description>

					<ClayCard.Row>
						<div className="col-md-4">
							<div className="float-right">
								<Selector
									label={sub(
										Liferay.Language.get('version-x'),
										[sourceVersion]
									)}
									selectedVersion={previousVersion}
									uniqueVersionLabel={Liferay.Language.get(
										'first-version'
									)}
									urlSelector="sourceURL"
									versions={availableVersions}
								/>
							</div>
						</div>

						<div className="col-md-8 diff-target-selector">
							<Selector
								label={sub(Liferay.Language.get('version-x'), [
									targetVersion
								])}
								selectedVersion={nextVersion}
								uniqueVersionLabel={Liferay.Language.get(
									'last-version'
								)}
								urlSelector="targetURL"
								versions={availableVersions}
							/>
						</div>
					</ClayCard.Row>

					<div className="divider row"></div>

					<ClayCard.Row>
						<div className="col-md-4">
							{selectableVersions.length >= 5 && (
								<Filter
									onChange={handleFilterChange}
									query={filterQuery}
								/>
							)}

							{availableLocales && availableLocales.length > 1 && (
								<LocaleSelector
									locales={availableLocales}
									onChange={event => {
										setSelectedLanguageId(
											event.target.value
										);

										submitForm(formRef.current);
									}}
									portletNamespace={portletNamespace}
									selectedLanguageId={selectedLanguageId}
								/>
							)}

							<List
								onChange={handleTargetChange}
								selected={selectedVersion}
								versions={visibleVersions}
							/>
						</div>

						<div className="col-md-8">
							<Diff
								diff={diff}
								onClose={handleTargetChange}
								version={selectedVersion}
							/>
						</div>
					</ClayCard.Row>
				</ClayCard.Body>
			</ClayCard>
		</form>
	);
}

export default function(props) {
	return <Comparator {...props} />;
}
