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

import moment from 'moment';
import PropType from 'prop-types';

function DateRenderer(props) {
	switch (props.options.type) {
		case 'calendar':
			return moment(props.value).calendar();
		case 'relative': {
			const date = moment(props.value).fromNow();

			return date.replace(/^./, date[0].toUpperCase());
		}
		default:
			return moment(props.value).format(
				props.options.format || 'MMMM Do YYYY'
			);
	}
}

DateRenderer.propTypes = {
	options: PropType.shape({
		format: PropType.string,
		type: PropType.oneOf(['calendar', 'relative', 'default']),
	}),
	value: PropType.string.isRequired,
};

DateRenderer.defaultProps = {
	options: {
		format: 'MMMM Do YYYY',
		type: 'default',
	},
};

export default DateRenderer;
