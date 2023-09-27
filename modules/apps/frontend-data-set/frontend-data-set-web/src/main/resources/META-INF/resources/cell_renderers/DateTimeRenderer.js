/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropType from 'prop-types';

function DateTimeRenderer({options, value}) {
	if (!value) {
		return null;
	}

	const locale = themeDisplay.getLanguageId().replaceAll('_', '-');
	const dateOptions = options?.format || {
		day: 'numeric',
		hour: 'numeric',
		minute: 'numeric',
		month: 'short',
		second: 'numeric',
		year: 'numeric',
	};
	const formattedDate = new Intl.DateTimeFormat(locale, dateOptions).format(
		new Date(value)
	);

	return formattedDate;
}

DateTimeRenderer.propTypes = {
	options: PropType.shape({
		format: PropType.object,
	}),
	value: PropType.string.isRequired,
};

export default DateTimeRenderer;
