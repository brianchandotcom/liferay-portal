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

import {getValueFromItem} from '../utilities/index';

function ImageRenderer(props) {
	return (
		<div className="row">
			<div className="col-auto">
				{typeof props.value === 'string' ? (
					<ClaySticker
						shape={props.options.shape || 'rounded'}
						size={props.options.size || 'xl'}
					>
						<div className="sticker-overlay">
							<img
								alt={
									props.options.label ||
									(props.options.labelKey
										? getValueFromItem(
												props.itemData,
												props.options.labelKey
										  )
										: Liferay.Language.get('thumbnail'))
								}
								className="sticker-img"
								src={props.value}
							/>
						</div>
					</ClaySticker>
				) : (
					<ClaySticker
						shape={
							props.options.shape ||
							props.value.shape ||
							'rounded'
						}
						size={props.options.size || props.value.size || 'xl'}
					>
						<div className="sticker-overlay">
							<img
								alt={
									props.value.alt ||
									Liferay.Language.get('thumbnail')
								}
								className="sticker-img"
								src={props.value.src}
							/>
						</div>
					</ClaySticker>
				)}
			</div>
		</div>
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
