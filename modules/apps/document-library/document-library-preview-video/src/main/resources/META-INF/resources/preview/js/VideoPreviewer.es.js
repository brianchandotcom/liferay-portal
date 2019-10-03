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

const VideoPreviewer = ({componentId, videoPosterURL, videoSources}) => {
	return (
		<div className="preview-file" id={componentId}>
			<div className="preview-file-container preview-file-max-height">
				<video
					className="preview-file-video"
					controls
					controlsList="nodownload"
					poster={videoPosterURL}
				>
					{videoSources.map((videoSource, index) => (
						<source
							key={index}
							src={videoSource.url}
							type={videoSource.type}
						/>
					))}
				</video>
			</div>
		</div>
	);
};

VideoPreviewer.propTypes = {
	componentId: PropTypes.string,
	videoPosterURL: PropTypes.string.isRequired,
	videoSources: PropTypes.arrayOf(
		PropTypes.shape({
			type: PropTypes.string.isRequired,
			url: PropTypes.string.isRequired
		})
	)
};

export default function(props) {
	return <VideoPreviewer {...props} />;
}
