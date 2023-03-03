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
