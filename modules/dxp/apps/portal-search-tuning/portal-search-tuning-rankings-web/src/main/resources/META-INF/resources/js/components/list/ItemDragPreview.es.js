/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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

const ItemDragPreview = props => (
	<div className="list-group">
		<Item.DecoratedComponent style={DRAG_PREVIEW_STYLES} {...props} />
	</div>
);

export default ItemDragPreview;
