/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {formatNumber} from './../../utils/numeric';

export default function Summary({summary}) {
	const getAttributes = (summaryItem) => {
		return {
			className: 'value',
			title: formatNumber(summaryItem),
		};
	};

	return (
		<div className="summary" tabIndex={0}>
			<div className="summary-item">
				<div className="type">{Liferay.Language.get('sum')}</div>

				<div
					{...getAttributes(summary['sum'])}
					data-tooltip-align="bottom"
				>
					{formatNumber(summary['sum'], true)}
				</div>
			</div>

			<div className="summary-item">
				<div className="type">{Liferay.Language.get('average')}</div>

				<div
					{...getAttributes(summary['average'])}
					data-tooltip-align="bottom"
				>
					{formatNumber(summary['average'], true)}
				</div>
			</div>

			<div className="summary-item">
				<div className="type">{Liferay.Language.get('min')}</div>

				<div
					{...getAttributes(summary['min'])}
					data-tooltip-align="bottom"
				>
					{formatNumber(summary['min'], true)}
				</div>
			</div>

			<div className="summary-item">
				<div className="type">{Liferay.Language.get('max')}</div>

				<div
					{...getAttributes(summary['max'])}
					data-tooltip-align="bottom"
				>
					{formatNumber(summary['max'], true)}
				</div>
			</div>
		</div>
	);
}
