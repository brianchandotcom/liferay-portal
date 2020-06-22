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

import {getValueFromItem} from '../utilities/index';

function ListRenderer(props) {
	if (!props.value || props.value.length === 0) {
		return null;
	}
	if (props.options.singleItemLabel && props.value.length === 1) {
		return props.options.singleItemLabel;
	}
	if (props.options.multipleItemsLabel && props.value.length > 1) {
		return props.options.multipleItemsLabel;
	}

	return props.value
		.map((el) => getValueFromItem(el, props.options.labelKey))
		.join(props.options.separator || ', ');
}

ListRenderer.propTypes = {
	options: PropType.shape({
		labelKey: PropType.oneOfType([PropType.array, PropType.string])
			.isRequired,
		multipleItemsLabel: PropType.string,
		separator: PropType.string,
		singleItemLabel: PropType.string,
	}),
	value: PropType.array,
};

export default ListRenderer;
