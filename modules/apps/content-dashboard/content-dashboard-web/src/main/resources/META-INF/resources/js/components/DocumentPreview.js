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

const DocumentPreview = ({
	documentSrc,
	documentTitle,
	downloadURL,
	isFile,
	viewURL,
}) => {
	return (
		<div className="document-preview sidebar-section sidebar-section--spaced">
			{documentSrc && (
				<figure className="document-preview-figure">
					<a
						className="align-items-center c-focus-inset d-flex h-100"
						href={viewURL}
						target="_blank"
					>
						<img alt={documentTitle} src={documentSrc} />
						<ClayIcon
							className="document-preview-icon"
							symbol="shortcut"
						/>
					</a>
				</figure>
			)}
			<div>
				{isFile && (
					<ClayLink className="btn btn-primary" href={downloadURL}>
						{Liferay.Language.get('download')}
					</ClayLink>
				)}
			</div>
		</div>
	);
};

DocumentPreview.defaultProps = {
	isFile: false,
	viewURL: null,
};

DocumentPreview.propTypes = {
	documentSrc: PropTypes.string.isRequired,
	documentTitle: PropTypes.string.isRequired,
	downloadURL: PropTypes.string,
	isFile: PropTypes.bool,
	viewURL: PropTypes.string,
};

export default DocumentPreview;
