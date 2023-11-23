/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React from 'react';
import {DragSource as dragSource} from 'react-dnd';

import useKeyboardNavigation from '../../hooks/useKeyboardNavigation';
import {DragTypes} from '../../utils/drag-types';
import {LIST_ITEM_TYPES} from '../../utils/listItemTypes';
import {TYPE_ICONS} from '../../utils/typeIcons';

function CriteriaSidebarItem({
	className,
	connectDragPreview,
	connectDragSource,
	dragging,
	icon,
	label,
	type,
}) {
	const {isTarget, setElement} = useKeyboardNavigation({
		type: LIST_ITEM_TYPES.listItem,
	});

	return connectDragSource(
		<li
			aria-label={sub(Liferay.Language.get('drag-x'), label)}
			className={classNames(
				'align-items-center criteria-sidebar-item-root c-py-2 c-pr-3 c-pl-1 c-my-1 d-flex ',
				{
					dragging,
				},
				className
			)}
			ref={setElement}
			role="menuitem"
			tabIndex={isTarget ? 0 : -1}
		>
			<ClayIcon symbol="drag" />

			<span className="c-mx-2 c-my-0 criteria-sidebar-item-type sticker sticker-dark">
				<span className="inline-item">
					<ClayIcon symbol={icon || TYPE_ICONS[type] || 'text'} />
				</span>
			</span>

			{label}

			{connectDragPreview(<div />)}
		</li>
	);
}

CriteriaSidebarItem.propTypes = {
	className: PropTypes.string,
	connectDragSource: PropTypes.func,
	defaultValue: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
	dragging: PropTypes.bool,
	icon: PropTypes.string,
	label: PropTypes.string,
	name: PropTypes.string,
	propertyKey: PropTypes.string.isRequired,
	type: PropTypes.string,
};

/**
 * Passes the required values to the drop target.
 * This method must be called `beginDrag`.
 * @param {Object} props Component's current props
 * @returns {Object} The props to be passed to the drop target.
 */
function beginDrag({defaultValue, icon, label, name, propertyKey, type}) {
	return {
		criterion: {
			defaultValue,
			propertyName: name,
			type,
		},
		icon: icon || TYPE_ICONS[type] || 'text',
		name: label,
		propertyKey,
	};
}

export default dragSource(
	DragTypes.PROPERTY,
	{
		beginDrag,
	},
	(connect, monitor) => ({
		connectDragPreview: connect.dragPreview(),
		connectDragSource: connect.dragSource(),
		dragging: monitor.isDragging(),
	})
)(CriteriaSidebarItem);
