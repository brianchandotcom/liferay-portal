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

import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import TreeviewContext from './TreeviewContext';

/**
 * Local version of `classnames()` required due to loader bug which prevents us
 * from importing the shared version.
 *
 * See: https://github.com/liferay/liferay-amd-loader/issues/225
 */
function classNames({disabled, focused, selected}) {
	return [
		disabled && 'disabled',
		focused && 'focused',
		selected && 'selected'
	]
		.filter(Boolean)
		.join(' ');
}

export default function TreeviewCard({node}) {
	const {dispatch, state} = useContext(TreeviewContext);
	const {filterQuery, focusedNodeId} = state;

	const path =
		node.nodePath && filterQuery ? (
			<div className="lfr-card-subtitle-text text-default text-truncate treeview-node-name">
				{node.nodePath}
			</div>
		) : null;

	return (
		<div className="p-2" role="treeitem">
			<ClayCard
				className={classNames({
					disabled: node.disabled,
					focused: node.id === focusedNodeId,
					selected: node.selected
				})}
				horizontal
				onClick={() => {
					dispatch({nodeId: node.id, type: 'TOGGLE_SELECT'});
				}}
				selectable={true}
			>
				<ClayCard.Body>
					<ClayCard.Row>
						<div className="autofit-col">
							<ClaySticker inline>
								<ClayIcon symbol={node.icon} />
							</ClaySticker>
						</div>
					</ClayCard.Row>

					<div className="autofit-col autofit-col-expand autofit-col-gutters">
						<ClayCard.Description displayType="title">
							{node.name}
						</ClayCard.Description>
					</div>
					{path}
				</ClayCard.Body>
			</ClayCard>
		</div>
	);
}

TreeviewCard.propTypes = {
	node: PropTypes.shape({
		icon: PropTypes.string,
		name: PropTypes.string.isRequired,
		nodePath: PropTypes.string
	}).isRequired
};
