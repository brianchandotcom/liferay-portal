/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import {ManagementToolbar} from 'frontend-js-components-web';
import {navigate, sub} from 'frontend-js-web';
import React, {useEffect, useRef} from 'react';

/**
 * @see {ManagementToolbarTag._getResultsLanguageKey} It should have the exact
 * 	same logic than the corresponding Java method.
 */
function getResultText(searchValue, itemTotal, filterTotal) {
	if (!searchValue) {
		if (filterTotal) {
			if (itemTotal === 1) {
				return Liferay.Language.get('x-result-found-with-filters');
			}

			return Liferay.Language.get('x-results-found-with-filters');
		}

		if (itemTotal === 1) {
			return Liferay.Language.get('x-result-found');
		}

		return Liferay.Language.get('x-results-found');
	}

	if (filterTotal) {
		if (itemTotal === 1) {
			return Liferay.Language.get('x-result-found-for-x-with-filters');
		}

		return Liferay.Language.get('x-results-found-for-x-with-filters');
	}

	if (itemTotal === 1) {
		return Liferay.Language.get('x-result-found-for-x');
	}

	return Liferay.Language.get('x-results-found-for-x');
}

const ResultsBar = ({
	clearResultsURL,
	filterLabelItems,
	itemsTotal,
	searchContainerId,
	searchValue,
	title,
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
							getResultText(
								itemsTotal,
								filterLabelItems?.length || 0
							),
							itemsTotal,
							`"${searchValue}"`
						)}
						className="component-text text-truncate-inline"
						data-qa-id="searchResultText"
						ref={resultsBarRef}
						tabIndex={-1}
					>
						<span className="text-truncate">
							{sub(
								getResultText(
									searchValue,
									itemsTotal,
									filterLabelItems?.length || 0
								),
								itemsTotal,
								<strong>{`"${searchValue}"`}</strong>
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
					<ClayButton
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
						displayType="unstyled"
						onClick={(event) => {
							event.preventDefault();

							searchContainerRef.current?.fire('clearFilter');

							navigate(clearResultsURL);
						}}
						onKeyPress={(event) => {
							if (event.key === 'Enter') {
								event.preventDefault();

								searchContainerRef.current?.fire('clearFilter');

								navigate(clearResultsURL);
							}
						}}
						tabIndex={0}
					>
						{Liferay.Language.get('clear')}
					</ClayButton>
				</ManagementToolbar.ResultsBarItem>
			</ManagementToolbar.ResultsBar>

			{Boolean(title) && (
				<ClayLayout.ContainerFluid className="c-mt-4" size="xl">
					<h3>{title}</h3>
				</ClayLayout.ContainerFluid>
			)}
		</>
	);
};

export default ResultsBar;
