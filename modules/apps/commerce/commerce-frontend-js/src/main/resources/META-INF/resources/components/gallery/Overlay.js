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

import Arrows from './Arrows';

export default function Overlay({
	adaptiveMediaImageHTMLTag,
	background,
	onClose,
	onNext,
	onPrev,
	src,
	title,
}) {
	return (
		<div className="gallery-overlay" onClick={onClose} style={{background}}>
			{adaptiveMediaImageHTMLTag ? (
				<div
					dangerouslySetInnerHTML={{
						__html: adaptiveMediaImageHTMLTag,
					}}
				/>
			) : (
				<img alt={title} src={src} />
			)}
			<Arrows onNext={onNext} onPrev={onPrev} />
		</div>
	);
}

Overlay.propTypes = {
	adaptiveMediaImageHTMLTag: PropTypes.string,
	onClose: PropTypes.func,
	onNext: PropTypes.func,
	onPrev: PropTypes.func,
	src: PropTypes.string,
	title: PropTypes.string,
};
