/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../../../services/liferay';

const formatCurrency = (
	value: number,
	currencyKey?: string,
	precision?: String
) => {
	return new Intl.NumberFormat(Liferay.ThemeDisplay.getBCP47LanguageId(), {
		currency: currencyKey ? currencyKey : 'USD',
		notation: 'compact',

		// @ts-ignore

		roundingPriority: precision || 'morePrecision',

		style: 'currency',
	}).format(value);
};

export default formatCurrency;
