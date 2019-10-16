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
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

const ImagePreview = ({imagePreviewURL}) => {
	if (imagePreviewURL) {
		return (
			<div className="fragments-editor-sidebar-section__card-image">
				<img alt="thumbnail" src={imagePreviewURL} />
			</div>
		);
	}

	return (
		<div className="fragments-editor-sidebar-section__card-no-preview">
			<ClayIcon symbol="picture" />
		</div>
	);
};

const SidebarCard = ({item}) => {
	const cardClassNames = classNames(
		'card',
		'card-interactive',
		'card-interactive-secondary',
		'selector-button',
		'fragments-editor__drag-source',
		'fragments-editor__drag-source--sidebar-fragment',
		'fragments-editor-sidebar-section__card',
		'overflow-hidden'
	);

	return (
		<div
			className={cardClassNames}
			data-drag-source-label={item.name}
			data-item-group-id={item.groupId}
			data-item-id={item.fragmentEntryKey}
			data-item-name={item.name}
		>
			<ImagePreview imagePreviewURL={item.imagePreviewURL} />

			<button
				className="btn btn-unstyled fragments-editor__drag-handler"
				type="button"
			>
				<span className="sr-only">
					{Liferay.Util.sub(
						Liferay.Language.get('drag-x'),
						item.name
					)}
				</span>
			</button>

			<div className="card-body">
				<div className="card-row">
					<div className="autofit-col autofit-col-expand autofit-row-center">
						<div
							className="card-title text-truncate"
							title={item.name}
						>
							{item.name}
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};

SidebarCard.propTypes = {
	icon: PropTypes.string.isRequired,
	item: PropTypes.shape({
		fragmentEntryKey: PropTypes.string.isRequired,
		groupId: PropTypes.string,
		imagePreviewURL: PropTypes.string,
		name: PropTypes.string.isRequired
	}).isRequired
};

export default SidebarCard;
