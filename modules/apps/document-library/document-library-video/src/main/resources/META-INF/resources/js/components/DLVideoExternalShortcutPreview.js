/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

const DLVideoExternalShortcutPreview = ({
	error,
	framed,
	loading,
	small,
	videoHTML,
}) => {
	return (
		<div
			className={classNames('video-preview mt-4', {
				['video-preview-framed']: framed,
				['video-preview-sm']: small,
			})}
		>
			{videoHTML && !error && !loading ? (
				<div
					className="video-preview-aspect-ratio"
					dangerouslySetInnerHTML={{__html: videoHTML}}
				/>
			) : (
				<div className="video-preview-aspect-ratio">
					<div className="video-preview-placeholder">
						{loading ? (
							<ClayLoadingIndicator />
						) : (
							<>
								<ClayIcon symbol="video" />
								{error && (
									<div className="video-preview-placeholder-text">
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

DLVideoExternalShortcutPreview.propTypes = {
	error: PropTypes.string,
	framed: PropTypes.bool,
	loading: PropTypes.bool,
	small: PropTypes.bool,
	videoHTML: PropTypes.string,
};

export default DLVideoExternalShortcutPreview;
