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
import Proptypes from 'prop-types';
import React from 'react';

function TooltipTable({value}) {
	return (
		<div className="bg-dark">
			<table className="tooltip-table">
				<tbody>
					{value.details.map((detail, i) => (
						<tr key={i}>
							<td className="table-column-text-start">
								{detail.label}
							</td>
							<td className="table-column-text-end">
								{Array.isArray(detail.value)
									? detail.value.join(' | ')
									: detail.value}
							</td>
						</tr>
					))}
					<tr>
						<td className="table-column-text-start">
							{value.final.label ||
								Liferay.Language.get('final-price')}
						</td>
						<td className="table-column-text-end">
							{value.final.value}
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	);
}

function TooltipPriceRenderer({value}) {
	if (!value) {
		return null;
	}

	return (
		<>
			{value.final.value}
			{value.details && (
				<ClayTooltipProvider
					contentRenderer={() => <TooltipTable value={value} />}
					delay={0}
				>
					<span
						className="tooltip-provider"
						title={Liferay.Language.get('price-summary')}
					>
						<ClayIcon symbol="info-circle" />
					</span>
				</ClayTooltipProvider>
			)}
		</>
	);
}

TooltipPriceRenderer.propTypes = {
	value: Proptypes.shape({
		details: Proptypes.arrayOf(
			Proptypes.oneOfType([
				Proptypes.shape({
					label: Proptypes.string,
					value: Proptypes.oneOfType([
						Proptypes.number,
						Proptypes.string,
					]),
				}),
				Proptypes.shape({
					label: Proptypes.string,
					value: Proptypes.arrayOf(
						Proptypes.oneOfType([
							Proptypes.string,
							Proptypes.number,
						])
					),
				}),
			])
		),
		final: Proptypes.shape({
			label: Proptypes.string,
			value: Proptypes.string.isRequired,
		}).isRequired,
	}),
};

export default TooltipPriceRenderer;
