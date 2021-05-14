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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import classNames from 'classnames';
import React, {useState} from 'react';

const ListItem = React.forwardRef(({item}, ref) => {
	const [showPreview, setShowPreview] = useState(false);

	return (
		<li
			className={classNames(
				'page-editor__fragments-widgets__tab-list-item',
				{
					disabled: item.disabled,
					'page-editor__fragments-widgets__tab-portlet-item':
						item.data.portletItemId,
				}
			)}
			ref={ref}
		>
			<div className="page-editor__fragments-widgets__tab-list-item-body">
				<ClayIcon className="mr-3" symbol={item.icon} />
				<div className="text-truncate title">{item.label}</div>
			</div>

			{item.preview && (
				<div className="page-editor__fragments-widgets__tab-list-item-preview">
					<ClayButton
						className="btn-monospaced preview-icon"
						displayType="unstyled"
						onBlur={() => setShowPreview(false)}
						onFocus={() => setShowPreview(true)}
						onMouseLeave={() => setShowPreview(false)}
						onMouseOver={() => setShowPreview(true)}
						small
					>
						<ClayIcon symbol="info-circle-open" />
						<span className="sr-only">{name}</span>
					</ClayButton>
					{showPreview && (
						<ClayPopover disableScroll>
							<img alt="thumbnail" src={item.preview} />
						</ClayPopover>
					)}
				</div>
			)}
		</li>
	);
});

export default ListItem;
