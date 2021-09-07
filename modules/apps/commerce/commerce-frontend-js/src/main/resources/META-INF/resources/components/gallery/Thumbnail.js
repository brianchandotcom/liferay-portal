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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

export default function Thumbnail({
	active = false,
	adaptiveMediaImageHTMLTag,
	background,
	onClick,
	src,
	title,
}) {
	const cardClasses = classNames(
		'card',
		'card-interactive',
		'card-interactive-primary',
		{active}
	);

	return (
		<div className={cardClasses} onClick={onClick} style={{background}}>
			<div className="aspect-ratio aspect-ratio-4-to-3">
				{adaptiveMediaImageHTMLTag ? (
					<div
						className="aspect-ratio-item-center-middle aspect-ratio-item-fluid aspect-ratio-item-vertical-fluid"
						dangerouslySetInnerHTML={{
							__html: adaptiveMediaImageHTMLTag,
						}}
					/>
				) : (
					<img
						alt={title}
						className="aspect-ratio-item-center-middle aspect-ratio-item-fluid aspect-ratio-item-vertical-fluid"
						src={src}
					/>
				)}
			</div>
		</div>
	);
}

Thumbnail.propTypes = {
	active: PropTypes.bool,
	adaptiveMediaImageHTMLTag: PropTypes.string,
	onClick: PropTypes.func,
	src: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
};
