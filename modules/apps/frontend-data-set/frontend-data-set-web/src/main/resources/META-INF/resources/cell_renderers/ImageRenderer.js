/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
