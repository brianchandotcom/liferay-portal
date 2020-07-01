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

import {ClayTooltipProvider} from '@clayui/tooltip';
import React from 'react';

import ellipsize from './../../utils/ellipsize.es';

export default ({summary}) => {
	const formatNumber = (number) => {
		let formattedNumber = number.toString();

		if (formattedNumber.length > 12) {
			formattedNumber = ellipsize(number.toString(), 8);
		}

		return formattedNumber;
	};

	const getAttributes = (summaryItem) => {
		const formatedNumber = formatNumber(summaryItem);

		const attributes = {
			className: 'value',
		};

		if (formatedNumber != summaryItem) {
			attributes['title'] = summaryItem;
		}

		return attributes;
	};

	return (
		<ClayTooltipProvider>
			<div className="summary">
				<div className="summary-item">
					<div className="type">{Liferay.Language.get('sum')}</div>
					<div
						{...getAttributes(summary['sum'])}
						data-tooltip-align="bottom"
					>
						{formatNumber(summary['sum'])}
					</div>
				</div>
				<div className="summary-item">
					<div className="type">
						{Liferay.Language.get('average')}
					</div>
					<div
						{...getAttributes(summary['average'])}
						data-tooltip-align="bottom"
					>
						{formatNumber(summary['average'])}
					</div>
				</div>
				<div className="summary-item">
					<div className="type">{Liferay.Language.get('min')}</div>
					<div
						{...getAttributes(summary['min'])}
						data-tooltip-align="bottom"
					>
						{formatNumber(summary['min'])}
					</div>
				</div>
				<div className="summary-item">
					<div className="type">{Liferay.Language.get('max')}</div>
					<div
						{...getAttributes(summary['max'])}
						data-tooltip-align="bottom"
					>
						{formatNumber(summary['max'])}
					</div>
				</div>
			</div>
		</ClayTooltipProvider>
	);
};
