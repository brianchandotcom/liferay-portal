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

import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import Proptypes from 'prop-types';
import React from 'react';

import {getDataRendererById} from '../utils/dataRenderers';
import {getSchemaString} from '../utils/index';
import DefaultRenderer from './DefaultRenderer';

function TooltipTableRow({
	contentRenderer,
	divider,
	highlighted,
	itemData,
	label,
	value,
	...options
}) {
	let DataRenderer = DefaultRenderer;

	if (contentRenderer) {
		DataRenderer = getDataRendererById(contentRenderer);
	}

	return (
		<tr
			className={classNames(
				divider && 'with-divider',
				highlighted && 'highlighted'
			)}
		>
			<td className="pr-3 table-column-text-start">{label}</td>
			<td className="table-column-text-end text-right">
				<DataRenderer
					itemData={itemData}
					options={options}
					value={value}
				/>
			</td>
		</tr>
	);
}

function TooltipSummaryRenderer({itemData, options, value}) {
	if (!value || !options.details?.rowsDefinitions) {
		return null;
	}

	const tooltipTableRows = options.details.rowsDefinitions.reduce(
		(rowsData, rowDefinition) => {
			const value = getSchemaString(
				itemData,
				rowDefinition.valueFieldKey
			);

			if (value) {
				rowsData.push({
					...rowDefinition,
					value,
				});
			}

			return rowsData;
		},
		[]
	);

	return (
		<>
			{value}
			{tooltipTableRows.length && (
				<ClayTooltipProvider
					contentRenderer={() => (
						<table className="tooltip-table">
							<tbody>
								{tooltipTableRows.map((rowData) => (
									<TooltipTableRow
										key={rowData.label}
										{...rowData}
									/>
								))}
							</tbody>
						</table>
					)}
					delay={0}
				>
					<span
						className="tooltip-provider"
						title={Liferay.Language.get('info')}
					>
						<ClayIcon symbol="info-circle" />
					</span>
				</ClayTooltipProvider>
			)}
		</>
	);
}

TooltipSummaryRenderer.propTypes = {
	options: Proptypes.shape({
		rowsDefinitions: Proptypes.arrayOf(
			Proptypes.shape({
				label: Proptypes.string,
				valueFieldKey: Proptypes.arrayOf(
					Proptypes.oneOfType([Proptypes.string, Proptypes.number])
				),
			})
		),
	}),
	value: Proptypes.oneOfType([Proptypes.string, Proptypes.number]),
};

export default TooltipSummaryRenderer;
