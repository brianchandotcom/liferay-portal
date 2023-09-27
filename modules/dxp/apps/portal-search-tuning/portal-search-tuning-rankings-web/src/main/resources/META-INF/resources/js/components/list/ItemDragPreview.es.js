/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import Item from './Item.es';

const DRAG_PREVIEW_STYLES = {
	borderRadius: '2px',
	borderWidth: 0,
	boxShadow:
		'0 0 0 0.125rem #FFF, 0 0 0 0.25rem #80ACFF, 0px 8px 16px rgba(39, 40, 51, 0.16)',
	fontSize: '14px',
	maxWidth: '800px',
};

const ItemDragPreview = (props) => (
	<div className="list-group">
		<Item.DecoratedComponent style={DRAG_PREVIEW_STYLES} {...props} />
	</div>
);

export default ItemDragPreview;
