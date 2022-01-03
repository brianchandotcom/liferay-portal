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
		'overflow-hidden',
		{active}
	);

	return (
		<div className={cardClasses} onClick={onClick} style={{background}}>
			{adaptiveMediaImageHTMLTag ? (
				<div
					className="h-100 w-100"
					dangerouslySetInnerHTML={{
						__html: adaptiveMediaImageHTMLTag,
					}}
				/>
			) : (
				<img alt={title} className="product-img" src={src} />
			)}
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
