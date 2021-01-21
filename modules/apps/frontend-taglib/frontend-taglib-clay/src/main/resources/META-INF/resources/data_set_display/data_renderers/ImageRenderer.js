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

import ClaySticker from '@clayui/sticker';
import PropType from 'prop-types';
import React from 'react';

import {getValueFromItem} from '../utils/index';

function ImageRenderer(props) {
	const imageProps =
		typeof props.value === 'string'
			? {
					alt:
						props.options.label ||
						(props.options.labelKey
							? getValueFromItem(
									props.itemData,
									props.options.labelKey
							  )
							: Liferay.Language.get('thumbnail')),
					src: props.value,
			  }
			: {
					alt: props.value.alt,
					src: props.value.src,
			  };

	return (
		<ClaySticker
			shape={props.options.shape || 'rounded'}
			size={props.options.size || 'xl'}
		>
			<img className="sticker-img" {...imageProps} />
		</ClaySticker>
	);
}

ImageRenderer.propTypes = {
	options: PropType.shape({
		label: PropType.string,
		labelKey: PropType.oneOfType([PropType.array, PropType.string]),
		shape: PropType.string,
		size: PropType.string,
	}),
	value: PropType.oneOfType([
		PropType.shape({
			alt: PropType.string,
			shape: PropType.string,
			size: PropType.string,
			src: PropType.string.isRequired,
		}),
		PropType.string,
	]),
};

export default ImageRenderer;
