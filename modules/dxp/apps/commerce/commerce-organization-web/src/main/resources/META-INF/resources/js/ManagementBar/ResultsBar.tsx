/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayResultsBar} from '@clayui/management-toolbar';
import {sub} from 'frontend-js-web';
import React, {MouseEvent} from 'react';

const ResultsBar = ({
	searchResult,
	searchResultCount,
	setSearchResult,
}: IResultsBar) => (
	<ClayResultsBar className="position-fixed w-100">
		<ClayResultsBar.Item expand>
			<span className="component-text text-truncate-inline">
				<span className="text-truncate">
					{(!searchResultCount || searchResultCount > 1) &&
						sub(
							Liferay.Language.get('x-results-for-x'),

							// @ts-ignore

							[searchResultCount, searchResult.name]
						)}

					{

						// @ts-ignore

						searchResultCount === 1 &&
							sub(
								Liferay.Language.get('x-result-for-x'),

								// @ts-ignore

								[searchResultCount, searchResult.name]
							)
					}
				</span>
			</span>
		</ClayResultsBar.Item>

		<ClayResultsBar.Item>
			<ClayButton
				className="component-link tbar-link"
				displayType="unstyled"
				onClick={(event: MouseEvent) => {
					event.preventDefault();

					setSearchResult({id: null, name: '', type: ''});
				}}
			>
				{Liferay.Language.get('clear')}
			</ClayButton>
		</ClayResultsBar.Item>
	</ClayResultsBar>
);

interface IResultsBar {
	searchResult: {id: number | null; name: string; type: string};
	searchResultCount: number;
	setSearchResult: (p: {
		id: number | null;
		name: string;
		type: string;
	}) => void;
}

export default ResultsBar;
