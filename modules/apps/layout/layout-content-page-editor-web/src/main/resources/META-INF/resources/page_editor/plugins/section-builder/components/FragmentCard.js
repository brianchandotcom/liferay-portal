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
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

const ImagePreview = ({imagePreviewURL}) => {
	if (imagePreviewURL) {
		return (
			<div className="page-editor-sidebar-panel__section-builder__card-image">
				<img alt="thumbnail" src={imagePreviewURL} />
			</div>
		);
	}

	return (
		<div className="page-editor-sidebar-panel__section-builder__card-no-preview">
			<ClayIcon symbol="picture" />
		</div>
	);
};

export default function FragmentCard({imagePreviewURL, name}) {
	return (
		<div
			className={classNames(
				'page-editor-sidebar-panel__section-builder__card',
				'card',
				'card-interactive',
				'card-interactive-secondary',
				'selector-button',
				'overflow-hidden'
			)}
		>
			<ImagePreview imagePreviewURL={imagePreviewURL} />

			<div className="card-body">
				<div className="card-row">
					<div className="autofit-col autofit-col-expand autofit-row-center">
						<div className="card-title text-truncate" title={name}>
							{name}
						</div>
					</div>
				</div>
			</div>
		</div>
	);
}

FragmentCard.propTypes = {
	imagePreviewURL: PropTypes.string,
	name: PropTypes.string.isRequired
};
