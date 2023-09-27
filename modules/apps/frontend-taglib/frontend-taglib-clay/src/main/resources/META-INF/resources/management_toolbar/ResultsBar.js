/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import ClayLink from '@clayui/link';
import {ManagementToolbar} from 'frontend-js-components-web';
import {navigate, sub} from 'frontend-js-web';
import React, {useEffect, useRef} from 'react';

const ResultsBar = ({
	clearResultsURL,
	filterLabelItems,
	itemsTotal,
	searchContainerId,
	searchValue,
}) => {
	const resultsBarRef = useRef();

	const searchContainerRef = useRef();

	useEffect(() => {
		Liferay.componentReady(searchContainerId).then((searchContainer) => {
			searchContainerRef.current = searchContainer;
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		resultsBarRef.current?.focus();
	}, [searchValue]);

	return (
		<>
			<ManagementToolbar.ResultsBar>
				<ManagementToolbar.ResultsBarItem
					expand={!(filterLabelItems?.length > 0)}
				>
					<span
						aria-label={sub(
							itemsTotal === 1
								? Liferay.Language.get('x-result-for-x')
								: Liferay.Language.get('x-results-for-x'),
							[
								itemsTotal,
								filterLabelItems
									?.map((item) => item.label)
									.join(', '),
							]
						)}
						className="component-text text-truncate-inline"
						ref={resultsBarRef}
						tabIndex={-1}
					>
						<span className="text-truncate">
							{sub(
								itemsTotal === 1
									? Liferay.Language.get('x-result-for')
									: Liferay.Language.get('x-results-for'),
								itemsTotal
							)}

							{searchValue && (
								<strong>{` "${searchValue}"`}</strong>
							)}
						</span>
					</span>
				</ManagementToolbar.ResultsBarItem>

				{filterLabelItems?.map((item, index) => (
					<ManagementToolbar.ResultsBarItem
						expand={index === filterLabelItems.length - 1}
						key={index}
					>
						<ClayLabel
							className="component-label tbar-label"
							closeButtonProps={{
								['aria-label']: sub(
									Liferay.Language.get('remove-x-filter'),
									item.label
								),
								onClick: () => {
									searchContainerRef.current?.fire(
										'clearFilter'
									);
									navigate(item.data?.removeLabelURL);
								},
							}}
							dismissible
							displayType="unstyled"
							withClose
						>
							{item.label}
						</ClayLabel>
					</ManagementToolbar.ResultsBarItem>
				))}

				<ManagementToolbar.ResultsBarItem>
					<ClayLink
						aria-label={sub(
							itemsTotal === 1
								? Liferay.Language.get('clear-x-result-for-x')
								: Liferay.Language.get('clear-x-results-for-x'),
							itemsTotal,
							searchValue !== null
								? searchValue
								: filterLabelItems?.map((item) => item.label)
						)}
						className="component-link tbar-link"
						onClick={(event) => {
							event.preventDefault();

							searchContainerRef.current?.fire('clearFilter');

							navigate(clearResultsURL);
						}}
					>
						{Liferay.Language.get('clear')}
					</ClayLink>
				</ManagementToolbar.ResultsBarItem>
			</ManagementToolbar.ResultsBar>
		</>
	);
};

export default ResultsBar;
