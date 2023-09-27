/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
