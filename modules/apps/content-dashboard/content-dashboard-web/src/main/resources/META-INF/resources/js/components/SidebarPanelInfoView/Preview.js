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
import ClayLink from '@clayui/link';
import PropTypes from 'prop-types';
import React from 'react';

const Preview = ({downloadURL, imageURL, title, url}) => {
	return (
		<div className="document-preview sidebar-section">
			{imageURL && (
				<figure className="document-preview-figure mb-2">
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
				</figure>
			)}

			<div>
				{downloadURL && (
					<ClayLink className="btn btn-primary" href={downloadURL}>
						{Liferay.Language.get('download')}
					</ClayLink>
				)}
			</div>
		</div>
	);
};

Preview.defaultProps = {
	viewURL: null,
};

Preview.propTypes = {
	downloadURL: PropTypes.string,
	imageURL: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
	viewURL: PropTypes.string,
};

export default Preview;
