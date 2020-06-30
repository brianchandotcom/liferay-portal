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
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import {LAYOUT_DATA_ITEM_TYPES, updateUsedWidget} from './AddPanel';
import {useSetWidgetsContext, useWidgetsContext} from './AddPanelContext';
import {addPortlet, useDragSymbol} from './useDragAndDrop';

import 'product-navigation-control-menu/css/TabItem.scss';

const addItem = (item, widgets, setWidgets) => {
	const targetItem = document.querySelector('.portlet-dropzone');

	if (!item.used) {
		addPortlet({item, targetItem});

		if (!item.data.instanceable) {
			const updatedWidgets = updateUsedWidget({
				item,
				widgets,
			});

			setWidgets(updatedWidgets);
		}
	}
};
export default function TabItem({item}) {
	const setWidgets = useSetWidgetsContext();
	const widgets = useWidgetsContext();

	const isContent = item.type === LAYOUT_DATA_ITEM_TYPES.content;

	const {sourceRef} = useDragSymbol({
		data: item.data,
		icon: item.icon,
		label: item.label,
		portletId: item.itemId,
		type: item.type,
	});

	return (
		<li
			className={classNames('sidebar__add-panel__tab-item', {
				disabled: item.disabled,
				multiline: isContent,
			})}
			ref={item.disabled ? null : sourceRef}
		>
			<div className="sidebar__add-panel__tab-item-body">
				<div className="icon">
					<ClayIcon symbol={item.icon} />
				</div>
				<div className="text">
					<div className="text-truncate title">{item.label}</div>
					{isContent && (
						<div className="subtitle text-truncate">
							{item.category}
						</div>
					)}
				</div>
			</div>

			{!item.disabled && (
				<ClayButton
					className="btn-monospaced sidebar__add-panel__tab-item-add"
					displayType="unstyled"
					onClick={() => addItem(item, widgets, setWidgets)}
					small
					title={item.name}
				>
					<ClayIcon symbol="plus" />
					<span className="sr-only">{item.name}</span>
				</ClayButton>
			)}
		</li>
	);
}

TabItem.propTypes = {
	item: PropTypes.shape({
		data: PropTypes.object.isRequired,
		icon: PropTypes.string.isRequired,
		label: PropTypes.string.isRequired,
		type: PropTypes.string.isRequired,
	}).isRequired,
};
