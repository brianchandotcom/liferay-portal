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

function ImageRenderer({value}) {
	return (
		<div className="row">
			<div className="col-auto">
				<ClaySticker
					shape={value.shape || 'rounded'}
					size={value.size || 'xl'}
				>
					<div className="sticker-overlay">
						<img
							alt={value.alt}
							className="sticker-img"
							src={value.src}
						/>
					</div>
				</ClaySticker>
			</div>
		</div>
	);
}

ImageRenderer.propTypes = {
	value: PropType.shape({
		alt: PropType.string.isRequired,
		shape: PropType.oneOf(['circle', 'rounded']),
		size: PropType.oneOf(['lg', 'sm', 'xl']),
		src: PropType.string.isRequired,
	}),
};

export default ImageRenderer;
