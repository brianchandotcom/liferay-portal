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
import ClayPopover from '@clayui/popover';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const ImagePreview = ({imagePreviewURL}) => (
	<ClayPopover>
		<img alt="thumbnail" src={imagePreviewURL} />
	</ClayPopover>
);

export default function FragmentCard({icon, imagePreviewURL, name, sourceRef}) {
	const [focusedItem, setFocusedItem] = useState(false);
	const [hoveredItem, setHoveredItem] = useState(false);
	const [showPreview, setShowPreview] = useState(false);

	return (
		<div
			className="page-editor__fragments-widgets__fragment-card"
			onFocus={() => setFocusedItem(true)}
			onMouseLeave={() => setHoveredItem(false)}
			onMouseOver={() => setHoveredItem(true)}
			ref={sourceRef}
			tabIndex="0"
		>
			<div className="page-editor__fragments-widgets__fragment-card-body">
				<ClayIcon className="mr-3" symbol={icon} />
				<div className="text-truncate title">{name}</div>
			</div>

			{imagePreviewURL && (hoveredItem || focusedItem) && (
				<div
					className="page-editor__fragments-widgets__fragment-card-preview"
					onBlur={() => setShowPreview(false)}
					onFocus={() => setShowPreview(true)}
					onMouseLeave={() => setShowPreview(false)}
					onMouseOver={() => setShowPreview(true)}
					tabIndex="0"
				>
					<ClayIcon symbol="info-circle-open" />
					{showPreview && (
						<ImagePreview imagePreviewURL={imagePreviewURL} />
					)}
				</div>
			)}
		</div>
	);
}

FragmentCard.propTypes = {
	icon: PropTypes.string,
	imagePreviewURL: PropTypes.string,
	name: PropTypes.string.isRequired,
	sourceRef: PropTypes.func,
};
