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

import PropType from 'prop-types';

function DateRenderer({options, value}) {
	if (!value) {
		return null;
	}

	const locale = themeDisplay.getLanguageId().replace('_', '-');
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

DateRenderer.propTypes = {
	options: PropType.shape({
		format: PropType.object,
	}),
	value: PropType.string.isRequired,
};

export default DateRenderer;
