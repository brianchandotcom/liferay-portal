/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

const Preview = ({compressed, imageURL, title, url}) => {
	return (
		<div
			className={classnames('document-preview sidebar-section', {
				'sidebar-section--compress': compressed,
			})}
		>
			{imageURL && (
				<figure className="document-preview-figure mb-2">
					{url ? (
						<a
							className="align-items-center c-focus-inset d-flex h-100"
							href={url}
							target="_blank"
						>
							<img alt={title} src={imageURL} />

							<ClayIcon
								className="document-preview-icon"
								symbol="shortcut"
							/>
						</a>
					) : (
						<div className="align-items-center d-flex h-100">
							<img alt={title} src={imageURL} />
						</div>
					)}
				</figure>
			)}
		</div>
	);
};

Preview.defaultProps = {
	compressed: false,
	url: null,
};

Preview.propTypes = {
	compressed: PropTypes.bool,
	imageURL: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
	url: PropTypes.string,
};

export default Preview;
