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

import {
	useHoverItem,
	useIsHovered,
	useIsSelected,
	useSelectItem,
} from '../../../app/components/Controls';
import {ITEM_ACTIVATION_ORIGINS} from '../../../app/config/constants/itemActivationOrigins';
import selectCanUpdateLayoutContent from '../../../app/selectors/selectCanUpdateLayoutContent';
import {useDispatch, useSelector} from '../../../app/store/index';
import deleteItem from '../../../app/thunks/deleteItem';

export default function StructureTreeNode({node}) {
	const hoverItem = useHoverItem();
	const isHovered = useIsHovered();
	const isSelected = useIsSelected();
	const selectItem = useSelectItem();
	const canUpdateLayoutContent = useSelector(selectCanUpdateLayoutContent);

	return (
		<div
			className={classNames('page-editor__page-structure__tree-node', {
				'page-editor__page-structure__tree-node--active':
					node.activable && isSelected(node.id),
			})}
			onMouseLeave={event => {
				event.stopPropagation();

				if (isHovered(node.id)) {
					hoverItem(null);
				}
			}}
			onMouseOver={event => {
				event.stopPropagation();
				hoverItem(node.id);
			}}
		>
			<ClayButton
				aria-label={Liferay.Util.sub(Liferay.Language.get('select-x'), [
					node.name,
				])}
				className="page-editor__page-structure__tree-node__mask"
				disabled={node.disabled}
				displayType="unstyled"
				onClick={event => {
					event.stopPropagation();
					event.target.focus();

					selectItem(node.id, {
						itemType: node.type,
						multiSelect: event.shiftKey,
						origin: ITEM_ACTIVATION_ORIGINS.structureTree,
					});
				}}
				onDoubleClick={event => event.stopPropagation()}
			/>

			<NameLabel
				activable={node.activable}
				disabled={node.disabled}
				id={node.id}
				name={node.name}
			/>

			{canUpdateLayoutContent && node.removable && (
				<RemoveButton
					node={node}
					visible={isHovered(node.id) || isSelected(node.id)}
				/>
			)}
		</div>
	);
}

StructureTreeNode.propTypes = {
	node: PropTypes.shape({
		id: PropTypes.string.isRequired,
		name: PropTypes.string.isRequired,
		removable: PropTypes.bool,
	}).isRequired,
};

const NameLabel = ({activable, disabled, id, name}) => {
	const isSelected = useIsSelected();

	return (
		<div
			className={classNames(
				'page-editor__page-structure__tree-node__name',
				{
					'page-editor__page-structure__tree-node__name--active':
						activable && isSelected(id),
					'page-editor__page-structure__tree-node__name--disabled': disabled,
				}
			)}
		>
			{name || Liferay.Language.get('element')}
		</div>
	);
};

const RemoveButton = ({node, visible}) => {
	const dispatch = useDispatch();
	const store = useSelector(state => state);

	return (
		<ClayButton
			aria-label={Liferay.Util.sub(Liferay.Language.get('remove-x'), [
				node.name,
			])}
			className={classNames(
				'page-editor__page-structure__tree-node__remove-button',
				{
					'page-editor__page-structure__tree-node__remove-button--visible': visible,
				}
			)}
			displayType="unstyled"
			onClick={event => {
				event.stopPropagation();

				dispatch(deleteItem({itemId: node.id, store}));
			}}
		>
			<ClayIcon symbol="times-circle" />
		</ClayButton>
	);
};
