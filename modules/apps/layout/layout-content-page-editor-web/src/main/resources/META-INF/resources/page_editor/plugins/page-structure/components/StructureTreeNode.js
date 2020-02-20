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
	useSelectItem
} from '../../../app/components/Controls';
import selectShowLayoutItemRemoveButton from '../../../app/selectors/selectShowLayoutItemRemoveButton';
import {useDispatch, useSelector} from '../../../app/store/index';
import deleteItem from '../../../app/thunks/deleteItem';

const NameButton = ({id, name}) => {
	const isSelected = useIsSelected();

	return (
		<ClayButton
			className={classNames(
				'page-editor__page-structure__tree-node__name-button',
				{
					'page-editor__page-structure__tree-node__name-button--active': isSelected(
						id
					)
				}
			)}
			displayType="unstyled"
		>
			{name || Liferay.Language.get('element')}
		</ClayButton>
	);
};

const RemoveButton = ({node}) => {
	const dispatch = useDispatch();
	const store = useSelector(state => state);

	return (
		<ClayButton
			className="page-editor__page-structure__tree-node__remove-button"
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

export default function StructureTreeNode({node}) {
	const hoverItem = useHoverItem();
	const isHovered = useIsHovered();
	const isSelected = useIsSelected();
	const selectItem = useSelectItem();
	const showLayoutItemRemoveButton = useSelector(
		selectShowLayoutItemRemoveButton
	);

	return (
		<div
			className="page-editor__page-structure__tree-node"
			onClick={event => {
				event.stopPropagation();

				selectItem(node.id, {multiSelect: event.shiftKey});
			}}
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
			<NameButton id={node.id} name={node.name} />
			{showLayoutItemRemoveButton &&
				node.removable &&
				(isHovered(node.id) || isSelected(node.id)) && (
					<RemoveButton node={node} />
				)}
		</div>
	);
}

StructureTreeNode.propTypes = {
	node: PropTypes.shape({
		id: PropTypes.string.isRequired,
		name: PropTypes.string.isRequired,
		removable: PropTypes.bool
	}).isRequired
};
