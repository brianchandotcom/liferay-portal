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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import React, {useMemo, useRef, useState} from 'react';
import {useDrag, useDrop} from 'react-dnd';

import {ACCEPTING_TYPES, DROP_ZONES} from './constants';

const ITEM_STATES_COLORS = {
	'conversion-draft': 'info',
	draft: 'secondary',
	pending: 'info'
};

const noop = () => {};

const MillerColumnsItem = ({
	actionHandlers = {},
	actions = [],
	active,
	bulkActions = '',
	checked,
	child = [],
	description,
	draggable,
	hasChild,
	itemId,
	columnId,
	namespace,
	onItemDrop = noop,
	order,
	parent,
	parentable,
	selectable,
	states = [],
	title,
	url
}) => {
	const ref = useRef();

	const [dropdownActionsActive, setDropdownActionsActive] = useState();
	const [dropZone, setDropZone] = useState();

	const dropdownActions = useMemo(() => {
		const dropdownActions = [];

		actions.forEach(action => {
			if (!action.quickAction && action.url) {
				dropdownActions.push({
					...action,
					handler: action.handler || actionHandlers[action.id] || noop
				});
			}
		});

		return dropdownActions;
	}, [actions, actionHandlers]);

	const quickActions = useMemo(() => {
		const quickActions = [];

		actions.forEach(action => {
			if (action.quickAction && action.url) {
				quickActions.push({
					...action,
					handler: action.handler || actionHandlers[action.id] || noop
				});
			}
		});

		return quickActions;
	}, [actions, actionHandlers]);

	const getDropZone = monitor => {
		if (!ref.current) {
			return;
		}

		const clientOffset = monitor.getClientOffset();
		const dropItemBoundingRect = ref.current.getBoundingClientRect();
		const hoverTopLimit = 20;
		const hoverBottomLimit = dropItemBoundingRect.height - hoverTopLimit;
		const hoverClientY = clientOffset.y - dropItemBoundingRect.top;

		let dropZone = DROP_ZONES.ELEMENT;

		if (hoverClientY < hoverTopLimit) {
			dropZone = DROP_ZONES.TOP;
		}
		else if (hoverClientY > hoverBottomLimit) {
			dropZone = DROP_ZONES.BOTTOM;
		}

		return dropZone;
	};

	const isValidTarget = (dropZone, sourceItem, targetItemId) => {
		let isValid;

		if (
			(parent && columnId <= sourceItem.columnId) ||
			(columnId > sourceItem.columnId && !sourceItem.active)
		) {
			if (
				dropZone !== DROP_ZONES.ELEMENT ||
				(targetItemId !== sourceItem.id &&
					dropZone === DROP_ZONES.ELEMENT &&
					parentable)
			) {
				isValid = true;
			}
		}

		return isValid;
	};

	const [{isDragging}, drag] = useDrag({
		collect: monitor => ({
			isDragging: !!monitor.isDragging()
		}),
		item: {
			active,
			columnId,
			id: itemId,
			type: ACCEPTING_TYPES.ITEM
		}
	});

	const [{isOver}, drop] = useDrop({
		accept: ACCEPTING_TYPES.ITEM,
		canDrop(sourceItem, monitor) {
			const dropZone = getDropZone(monitor);

			return isValidTarget(dropZone, sourceItem, itemId);
		},
		collect: monitor => ({
			isOver: !!monitor.isOver()
		}),
		drop(sourceItem, monitor) {
			if (monitor.canDrop()) {
				if (dropZone === DROP_ZONES.ELEMENT) {
					onItemDrop(sourceItem.id, itemId, child.length);
				}
				else {
					let newOrder = order;

					if (dropZone === DROP_ZONES.BOTTOM) {
						newOrder = order + 1;
					}

					onItemDrop(sourceItem.id, parent.id, newOrder);
				}
			}
		},
		hover(sourceItem, monitor) {
			let dropZone;

			if (isOver && monitor.canDrop()) {
				dropZone = getDropZone(monitor);
			}

			setDropZone(dropZone);
		}
	});

	drag(drop(ref));

	return (
		<li
			className={classNames(
				'autofit-row autofit-row-center list-group-item-flex miller-columns-item',
				{
					active,
					dragging: isDragging,
					'drop-bottom': isOver && dropZone === DROP_ZONES.BOTTOM,
					'drop-element': isOver && dropZone === DROP_ZONES.ELEMENT,
					'drop-top': isOver && dropZone === DROP_ZONES.TOP
				}
			)}
			data-actions={bulkActions}
			ref={ref}
		>
			<a className="miller-columns-item-mask" href={url}>
				<span className="sr-only">{`${Liferay.Language.get(
					'select'
				)} ${title}`}</span>
			</a>

			{draggable && (
				<div className="autofit-col autofit-padded-no-gutters h2 miller-columns-item-drag-handler">
					<ClayIcon symbol="drag" />
				</div>
			)}

			{selectable && (
				<div className="autofit-col">
					<ClayCheckbox
						checked={checked}
						name={`${namespace}rowIds`}
						value={itemId}
					/>
				</div>
			)}

			<div className="autofit-col autofit-col-expand">
				<h4 className="list-group-title">
					<span className="text-truncate">{title}</span>
				</h4>

				{description && (
					<h5 className="list-group-subtitle small text-truncate">
						{description}

						{states.map(state => (
							<ClayLabel
								className="inline-item-after"
								displayType={ITEM_STATES_COLORS[state.id]}
								key={state.id}
							>
								{state.label}
							</ClayLabel>
						))}
					</h5>
				)}
			</div>

			{quickActions.map(action => (
				<div
					className="autofit-col miller-columns-item-quick-action"
					key={action.id}
				>
					<ClayLink
						borderless
						displayType="secondary"
						href={action.url}
						monospaced
						outline
					>
						<ClayIcon symbol={action.icon} />
					</ClayLink>
				</div>
			))}

			{dropdownActions.length > 0 && (
				<div className="autofit-col miller-columns-item-actions">
					<ClayDropDown
						active={dropdownActionsActive}
						onActiveChange={setDropdownActionsActive}
						trigger={
							<ClayButtonWithIcon
								borderless
								displayType="secondary"
								small
								symbol="ellipsis-v"
							/>
						}
					>
						<ClayDropDown.ItemList>
							{dropdownActions.map(action => (
								<ClayDropDown.Item
									href={action.url}
									id={action.id}
									key={action.id}
									onClick={action.handler}
								>
									{action.label}
								</ClayDropDown.Item>
							))}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</div>
			)}

			{hasChild && (
				<div className="autofit-col autofit-padded-no-gutters miller-columns-item-child-indicator text-muted">
					<ClayIcon symbol="caret-right" />
				</div>
			)}
		</li>
	);
};

export default MillerColumnsItem;
