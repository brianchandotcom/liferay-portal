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
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import TreeviewContext from './TreeviewContext';

export default function TreeviewCard({node}) {
	const {state} = useContext(TreeviewContext);
	const {filterQuery, focusedNodeId} = state;

	const path =
		node.nodePath && filterQuery ? (
			<div className="lfr-card-subtitle-text text-default text-truncate treeview-node-name">
				{node.nodePath}
			</div>
		) : null;

	return (
		<div
			className={classNames({
				'card-type-directory': true,
				disabled: node.disabled,
				focused: node.id === focusedNodeId,
				'form-check': true,
				'form-check-card': true,
				'form-check-middle-left': true,
				selected: node.selected
			})}
		>
			<div className="card card-horizontal">
				<div className="card-body">
					<ClayCard.Row>
						<div className="autofit-col">
							<ClaySticker displayType="secondary" inline>
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
				</div>
			</div>
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
