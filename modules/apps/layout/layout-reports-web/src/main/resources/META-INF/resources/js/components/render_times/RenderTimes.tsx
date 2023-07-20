/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayButtonWithIcon} from '@clayui/button';
import ClayLabel from '@clayui/label';
import {SearchResultsMessage} from '@liferay/layout-js-components-web';
import {fetch, navigate, sub} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import Filter from './Filter';

type Fragment = {
	fragmentCollectionURL: string;
	hierarchy: string;
	isCache: boolean;
	isFragment: boolean;
	isFromMaster: boolean;
	itemId: string;
	itemType: string;
	name: string;
	renderTime: number;
};

export default function RenderTimes({url}: {url: string}) {
	const [ascending, setAscending] = useState(false);
	const [fragments, setFragments] = useState<Fragment[]>([]);
	const [searchValue, setSearchValue] = useState(null);
	const [visibleInfo, setVisibleInfo] = useState<boolean>(true);

	const filteredFragments = useMemo(
		() =>
			searchValue
				? fragments.filter(
						(fragment) =>
							fragment.name.toLowerCase().indexOf(searchValue) !==
							-1
				  )
				: fragments,
		[fragments, searchValue]
	);

	useEffect(() => {
		fetch(url, {method: 'GET'})
			.then((response) => response.json())
			.then((fragments) => setFragments(fragments))
			.catch((error) => console.error(error));
	}, [url]);

	return (
		<>
			<Filter
				isAscendingSort={ascending}
				onSearchValue={setSearchValue}
				onSort={setAscending}
			/>

			<SearchResultsMessage numberOfResults={filteredFragments.length} />

			{visibleInfo ? (
				<ClayAlert
					className="c-mb-4"
					displayType="info"
					onClose={() => setVisibleInfo(false)}
					role="none"
				>
					{Liferay.Language.get(
						'render-times-are-approximate-and-subject-to-slight-variations-due-to-server-status-and-load'
					)}
				</ClayAlert>
			) : null}

			<div className="page-audit__fragmentList">
				{filteredFragments
					.sort((a: Fragment, b: Fragment) =>
						ascending
							? a.renderTime - b.renderTime
							: b.renderTime - a.renderTime
					)
					.map(
						({
							fragmentCollectionURL,
							isCache,
							isFragment,
							isFromMaster,
							itemId,
							name,
							renderTime,
						}) => {
							return (
								<div
									className="c-p-1 d-flex flex-column page-audit__fragment"
									key={itemId}
								>
									<span className="font-weight-bold position-relative">
										{name}

										<span className="page-audit__fragment__buttons">
											<ClayButtonWithIcon
												aria-label={sub(
													Liferay.Language.get(
														'locate-x-in-page'
													),
													name
												)}
												displayType="unstyled"
												size="sm"
												symbol="search"
												title={sub(
													Liferay.Language.get(
														'locate-x-in-page'
													),
													name
												)}
											/>

											<ClayButtonWithIcon
												aria-label={sub(
													Liferay.Language.get(
														'open-x-in-fragment-library'
													),
													name
												)}
												className="c-ml-2"
												displayType="unstyled"
												onClick={() =>
													navigate(
														fragmentCollectionURL
													)
												}
												size="sm"
												symbol="shortcut"
												title={sub(
													Liferay.Language.get(
														'open-x-in-fragment-library'
													),
													name
												)}
											/>
										</span>
									</span>

									<span>{renderTime}ms</span>

									<span>
										<ClayLabel displayType="secondary">
											{isFragment
												? Liferay.Language.get(
														'fragment'
												  )
												: Liferay.Language.get(
														'widget'
												  )}
										</ClayLabel>

										{isFromMaster && (
											<ClayLabel displayType="secondary">
												{Liferay.Language.get(
													'from-master'
												)}
											</ClayLabel>
										)}

										{isCache && (
											<ClayLabel displayType="info">
												{Liferay.Language.get('cached')}
											</ClayLabel>
										)}
									</span>
								</div>
							);
						}
					)}
			</div>
		</>
	);
}
