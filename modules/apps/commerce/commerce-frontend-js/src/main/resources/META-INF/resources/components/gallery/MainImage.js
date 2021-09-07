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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import PropTypes from 'prop-types';
import React from 'react';

import Arrows from './Arrows';

export default function MainImage({
	adaptiveMediaImageHTMLTag,
	background,
	loading = false,
	onNext,
	onPrev,
	onZoom,
	src,
	title,
}) {
	return (
		<div className="card main-image" onClick={onZoom} style={{background}}>
			<div className="aspect-ratio aspect-ratio-4-to-3">
				{adaptiveMediaImageHTMLTag ? (
					<div
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
			<Arrows onNext={onNext} onPrev={onPrev} />
			{loading ? <ClayLoadingIndicator /> : null}
		</div>
	);
}

MainImage.propTypes = {
	adaptiveMediaImageHTMLTag: PropTypes.string,
	background: PropTypes.string,
	loading: PropTypes.bool,
	onNext: PropTypes.func,
	onPrev: PropTypes.func,
	onZoom: PropTypes.func,
	src: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
};
