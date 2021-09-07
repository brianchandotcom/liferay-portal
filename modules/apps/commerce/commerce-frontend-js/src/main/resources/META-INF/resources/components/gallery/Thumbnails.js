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

import PropTypes from 'prop-types';
import React from 'react';

import Thumbnail from './Thumbnail';

export default function Thumbnails({
	background,
	images,
	onChange,
	selected = false,
}) {
	return (
		<div className="gallery-thumbnails">
			{images.map((image, i) => (
				<Thumbnail
					active={selected === i}
					adaptiveMediaImageHTMLTag={image.adaptiveMediaImageHTMLTag}
					background={background}
					key={image.thumbnailURL}
					onClick={onChange ? () => onChange(i) : null}
					src={image.thumbnailURL}
					title={image.title}
				/>
			))}
		</div>
	);
}

Thumbnails.propTypes = {
	background: PropTypes.string,
	images: PropTypes.arrayOf(
		PropTypes.shape({
			thumbnailURL: PropTypes.string.isRequired,
			title: PropTypes.string.isRequired,
		})
	),
	onChange: PropTypes.func,
	selected: PropTypes.number,
};
