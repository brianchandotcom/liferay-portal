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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';
import {useDrag, useDrop} from 'react-dnd';

const DROP_ZONES = {
	BOTTOM: 'BOTTOM',
	TOP: 'TOP',
};

const ACCEPTING_TYPES = {
	ITEM: 'ITEM',
};

const ITEM_HOVER_BORDER_LIMIT = 28;

const isValidTarget = (source, target, dropZone) => {
	return !!(
		source.id !== target.id &&
		((dropZone === DROP_ZONES.TOP &&
			(target.index < source.index || target.index > source.index + 1)) ||
			(dropZone === DROP_ZONES.BOTTOM &&
				(target.index > source.index ||
					target.index < source.index - 1)))
	);
};

const getDropZone = (ref, monitor) => {
	if (!ref.current) {
		return;
	}

	const clientOffset = monitor.getClientOffset();
	const dropItemBoundingRect = ref.current.getBoundingClientRect();
	const hoverTopLimit = ITEM_HOVER_BORDER_LIMIT;
	const hoverClientY = clientOffset.y - dropItemBoundingRect.top;

	let dropZone = DROP_ZONES.BOTTOM;

	if (hoverClientY < hoverTopLimit) {
		dropZone = DROP_ZONES.TOP;
	}

	return dropZone;
};

const noop = () => {};

const LanguageListItem = ({
	displayName,
	isDefault,
	localeId,
	onMakeDefault,
	isFirst = false,
	isLast = false,
	index,
	onItemDrop = noop,
	onMoveUp = noop,
	onMoveDown = noop,
}) => {
	const [active, setActive] = useState(false);
	const [dropZone, setDropZone] = useState();
	const itemRef = useRef();

	const [{isDragging}, drag] = useDrag({
		collect: (monitor) => ({
			isDragging: !!monitor.isDragging(),
		}),
		item: {
			id: localeId,
			index,
			type: ACCEPTING_TYPES.ITEM,
		},
	});

	const [{isOver}, drop] = useDrop({
		accept: ACCEPTING_TYPES.ITEM,
		canDrop(source, monitor) {
			const dropZone = getDropZone(itemRef, monitor);

			return isValidTarget(source, {id: localeId, index}, dropZone);
		},
		collect: (monitor) => ({
			isOver: !!monitor.isOver(),
		}),
		drop(source, monitor) {
			if (monitor.canDrop()) {
				let newIndex = index;

				if (dropZone === DROP_ZONES.BOTTOM) {
					newIndex = index + 1;
				}

				if (newIndex > source.index) {
					newIndex = newIndex - 1;
				}

				onItemDrop(source.index, newIndex);
			}
		},
		hover(source, monitor) {
			let dropZone;

			if (isOver && monitor.canDrop()) {
				dropZone = getDropZone(itemRef, monitor);
			}

			setDropZone(dropZone);
		},
	});

	useEffect(() => {
		drag(drop(itemRef));
	}, [drag, drop]);

	const makeDefault = (event) => {
		setActive(false);
		onMakeDefault({localeId});
		Liferay.fire('inputLocalized:defaultLocaleChanged', {
			item: event.currentTarget,
		});
	};

	const moveUp = () => {
		setActive(false);
		onMoveUp();
	};

	const moveDown = () => {
		setActive(false);
		onMoveDown();
	};

	return (
		<ClayTable.Row
			className={classNames('language-list-item', {
				'dragging': isDragging,
				'drop-bottom': isOver && dropZone === DROP_ZONES.BOTTOM,
				'drop-top': isOver && dropZone === DROP_ZONES.TOP,
			})}
			ref={itemRef}
		>
			<ClayTable.Cell expanded>
				<ClayLayout.ContentRow verticalAlign="center">
					<ClayLayout.ContentCol className="autofit-padded-no-gutters">
						<div className="language-drag-handler">
							<ClayIcon symbol="drag" />
						</div>
					</ClayLayout.ContentCol>

					<ClayLayout.ContentCol
						className="align-items-center autofit-padded-no-gutters flex-row justify-content-start"
						expand
					>
						{displayName}
						{isDefault && (
							<ClayLabel className="ml-3" displayType="info">
								{Liferay.Language.get('default')}
							</ClayLabel>
						)}
					</ClayLayout.ContentCol>
				</ClayLayout.ContentRow>
			</ClayTable.Cell>
			<ClayTable.Cell align="right">
				<ClayDropDown
					active={active}
					onActiveChange={setActive}
					trigger={
						<ClayButton
							borderless
							displayType="secondary"
							monospaced
							small
						>
							<ClayIcon symbol="ellipsis-v" />
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						<ClayDropDown.Item
							data-value={localeId}
							key={localeId}
							onClick={makeDefault}
						>
							{Liferay.Language.get('make-default')}
						</ClayDropDown.Item>
						{!isFirst && (
							<ClayDropDown.Item onClick={moveUp}>
								{Liferay.Language.get('move-up')}
							</ClayDropDown.Item>
						)}
						{!isLast && (
							<ClayDropDown.Item onClick={moveDown}>
								{Liferay.Language.get('move-down')}
							</ClayDropDown.Item>
						)}
					</ClayDropDown.ItemList>
				</ClayDropDown>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
};

LanguageListItem.propTypes = {
	index: PropTypes.number.isRequired,
	isDefault: PropTypes.bool.isRequired,
	isFirst: PropTypes.bool,
	isLast: PropTypes.bool,
	onItemDrop: PropTypes.func,
	onMakeDefault: PropTypes.func,
	onMoveDown: PropTypes.func,
	onMoveUp: PropTypes.func,
};

export default LanguageListItem;
