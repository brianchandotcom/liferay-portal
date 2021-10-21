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
import React, {useContext} from 'react';

import {AddPanelContext, updateUsedWidget} from './AddPanel';
import addPortlet from './addPortlet';
import {LAYOUT_DATA_ITEM_TYPES} from './constants/layoutDataItemTypes';
import {useDragSymbol} from './useDragAndDrop';

const addItem = ({item, plid, setWidgets, widgets}) => {
	const targetItem = document.querySelector('.portlet-dropzone');

	addPortlet({item, plid, targetItem});

	if (!item.data.instanceable) {
		const updatedWidgets = updateUsedWidget({
			item,
			widgets,
		});

		setWidgets(updatedWidgets);
	}
};

const TabItem = ({item}) => {
	const {plid, setWidgets, widgets} = useContext(AddPanelContext);

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
			className={classNames('sidebar-body__add-panel__tab-item', {
				'disabled': item.disabled,
				'multiline': isContent,
				'sidebar-body__add-panel__tab-portlet-item':
					item.data.portletItemId,
			})}
			ref={item.disabled ? null : sourceRef}
		>
			<div className="sidebar-body__add-panel__tab-item-body">
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
					className="btn-monospaced sidebar-body__add-panel__tab-item-add"
					displayType="unstyled"
					onClick={() => addItem({item, plid, setWidgets, widgets})}
					small
					title={item.name}
				>
					<ClayIcon symbol="plus" />
					<span className="sr-only">{item.name}</span>
				</ClayButton>
			)}
		</li>
	);
};

TabItem.propTypes = {
	item: PropTypes.shape({
		data: PropTypes.object.isRequired,
		icon: PropTypes.string.isRequired,
		label: PropTypes.string.isRequired,
		type: PropTypes.string.isRequired,
	}).isRequired,
};

export default TabItem;
