/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLabel from '@clayui/label';
import {ClayResultsBar} from '@clayui/management-toolbar';
import React from 'react';

import lang from '../../../../utils/lang.es';
import {slugToText} from '../../../../utils/utils.es';

const ManagementToolbarResults = ({
	keywords,
	resultBar = [],
	onClear,
	totalResults,
}) => (
	<ClayResultsBar style={{width: '100%'}}>
		<ClayResultsBar.Item expand={!resultBar.length}>
			<span className="component-text text-truncate-inline">
				<span className="text-truncate">
					{lang.sub(
						totalResults === 1
							? Liferay.Language.get('x-result-for')
							: Liferay.Language.get('x-results-for'),
						[totalResults]
					)}

					{keywords && (
						<strong>{` "${slugToText(keywords)}"`}</strong>
					)}
				</span>
			</span>
		</ClayResultsBar.Item>

		{!!resultBar.length && (
			<ClayResultsBar.Item expand>
				{resultBar.map(({label, value}, index) => (
					<ClayLabel
						className="component-label mr-2 tbar-label"
						displayType="unstyled"
						key={index}
					>
						<span className="text-capitalize">{`${label}: ${value}`}</span>
					</ClayLabel>
				))}
			</ClayResultsBar.Item>
		)}

		<ClayResultsBar.Item>
			<ClayButton
				aria-label={Liferay.Language.get('clear')}
				className="component-link tbar-link"
				displayType="unstyled"
				onClick={onClear}
			>
				{Liferay.Language.get('clear')}
			</ClayButton>
		</ClayResultsBar.Item>
	</ClayResultsBar>
);

export default ManagementToolbarResults;
