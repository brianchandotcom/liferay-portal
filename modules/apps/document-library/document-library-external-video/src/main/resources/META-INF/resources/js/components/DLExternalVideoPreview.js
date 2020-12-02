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

import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

const DLExternalVideoPreview = ({error, framed, loading, small, videoHTML}) => {
	return (
		<div
			className={classNames('external-video-preview mt-4', {
				['external-video-preview-framed']: framed,
				['external-video-preview-sm']: small,
			})}
		>
			{videoHTML && !error && !loading ? (
				<div
					className="external-video-preview-aspect-ratio"
					dangerouslySetInnerHTML={{__html: videoHTML}}
				/>
			) : (
				<div className="external-video-preview-aspect-ratio">
					<div className="external-video-preview-placeholder">
						{loading ? (
							<ClayLoadingIndicator />
						) : (
							<>
								<ClayIcon symbol="video" />
								{error && (
									<div className="external-video-preview-placeholder-text">
										{error}
									</div>
								)}
							</>
						)}
					</div>
				</div>
			)}
		</div>
	);
};

DLExternalVideoPreview.propTypes = {
	error: PropTypes.string,
	framed: PropTypes.bool,
	loading: PropTypes.bool,
	small: PropTypes.bool,
	videoHTML: PropTypes.string,
};

export default DLExternalVideoPreview;
