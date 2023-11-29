/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React from 'react';

import {
	useDisableKeyboardMovement,
	useMovementSource,
	useSetMovementSource,
} from '../../contexts/KeyboardMovementContext';
import useDragSource from '../../hooks/useDragSource';
import useKeyboardNavigation from '../../hooks/useKeyboardNavigation';
import {DragTypes} from '../../utils/dragTypes';
import {LIST_ITEM_TYPES} from '../../utils/listItemTypes';
import {TYPE_ICONS} from '../../utils/typeIcons';

export default function CriteriaSidebarItem({
	className,
	defaultValue,
	icon,
	label,
	name: propertyName,
	propertyKey,
	type,
}) {
	const {isTarget, setElement} = useKeyboardNavigation({
		type: LIST_ITEM_TYPES.listItem,
	});

	const itemIcon = icon || TYPE_ICONS[type] || 'text';

	const {handlerRef, isDragging} = useDragSource({
		item: {
			criterion: {
				defaultValue,
				propertyName,
				type,
			},
			icon: itemIcon,
			name: label,
			propertyKey,
			type: DragTypes.PROPERTY,
		},
	});

	const setMovementSource = useSetMovementSource();
	const movementSource = useMovementSource();
	const disableMovement = useDisableKeyboardMovement();

	const isKeyboardSource =
		movementSource?.propertyKey === propertyKey &&
		movementSource?.propertyName === propertyName;

	return (
		<li
			aria-label={sub(Liferay.Language.get('drag-x'), label)}
			className={classNames(
				'align-items-center criteria-sidebar-item-root c-gap-2 c-my-2 c-p-2 d-flex',
				className,
				{
					dragging: isDragging || isKeyboardSource,
				}
			)}
			onBlur={disableMovement}
			onKeyDown={(event) => {
				if (event.key === 'Enter') {
					setMovementSource({
						defaultValue,
						icon: itemIcon,
						label,
						propertyKey,
						propertyName,
						type,
					});
				}
			}}
			ref={setElement}
			role="menuitem"
			tabIndex={isTarget ? 0 : -1}
		>
			<div className="h-100 w-100" ref={handlerRef}>
				<ClayIcon className="mt-0" symbol="drag" />

				<span className="sticker">
					<ClayIcon symbol={icon || TYPE_ICONS[type] || 'text'} />
				</span>

				{label}
			</div>
		</li>
	);
}

CriteriaSidebarItem.propTypes = {
	className: PropTypes.string,
	defaultValue: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
	icon: PropTypes.string,
	label: PropTypes.string,
	name: PropTypes.string,
	propertyKey: PropTypes.string.isRequired,
	type: PropTypes.string,
};
