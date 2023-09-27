/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	const targetItem = document.querySelector(
		'.portlet-dropzone:not(.portlet-dropzone-disabled)'
	);

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
			<div
				className="sidebar-body__add-panel__tab-item-body"
				title={item.label}
			>
				<div className="icon">
					<ClayIcon symbol={item.icon} />
				</div>

				<div className="text">
					<div className="mr-1 text-truncate title">{item.label}</div>

					{isContent && (
						<div className="subtitle text-break">
							{item.category}
						</div>
					)}
				</div>
			</div>

			{!item.disabled && (
				<ClayButton
					aria-label={`${Liferay.Language.get('add-content')}`}
					className="btn-monospaced sidebar-body__add-panel__tab-item-add"
					data-tooltip-align="top-left"
					displayType="unstyled"
					onClick={() => addItem({item, plid, setWidgets, widgets})}
					size="sm"
					title={`${Liferay.Language.get('add-content')}`}
				>
					<ClayIcon symbol="plus" />
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
