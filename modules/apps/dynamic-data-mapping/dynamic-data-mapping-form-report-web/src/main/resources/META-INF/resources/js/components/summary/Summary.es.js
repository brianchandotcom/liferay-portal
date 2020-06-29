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

const formatSummary = (summary) => {
	const formatedSummary = {};

	formatedSummary['sum'] = summary['sum'];
	formatedSummary['average'] = summary['average'];
	formatedSummary['min'] = summary['min'];
	formatedSummary['max'] = summary['max'];

	return formatedSummary;
};

export default ({summary}) => {
	summary = formatSummary(summary);

	const formatNumber = (number) => {
		let formatedNumber = number.toString();

		if (formatedNumber.length > 12) {
			formatedNumber = number.toString().substring(0, 8) + '...';
		}

		return formatedNumber;
	};

	const summaryItems = Object.keys(summary).map((key, index) => {
		const formatedNumber = formatNumber(summary[key]);

		const attributes = {
			className: 'value',
		};

		if (formatedNumber != summary[key]) {
			attributes['title'] = summary[key];
		}

		return (
			<div className="summary-item" key={`summary-item-${index}`}>
				<div className="type">
					{key.charAt(0).toUpperCase() + key.slice(1)}
				</div>
				<div {...attributes} data-tooltip-align="bottom">
					{formatNumber(summary[key])}
				</div>
			</div>
		);
	});

	return (
		<ClayTooltipProvider>
			<div className="summary">{summaryItems}</div>
		</ClayTooltipProvider>
	);
};
