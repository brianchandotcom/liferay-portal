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
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import TreeviewContext from './TreeviewContext.es';

export default function TreeviewCard({node, onNodeSelected, selectedNodeIds}) {
	const {filterQuery} = useContext(TreeviewContext);

	const path =
		node.nodePath && filterQuery ? (
			<div className="lfr-card-subtitle-text text-default text-truncate treeview-node-name">
				{node.nodePath}
			</div>
		) : null;

	return (
		<div className="p-2">
			<ClayCard horizontal selectable={true}>
				<ClayCheckbox
					checked={selectedNodeIds.includes(node.id)}
					onChange={() => onNodeSelected(node.id)}
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
				</ClayCheckbox>
			</ClayCard>
		</div>
	);
}

TreeviewCard.propTypes = {
	node: PropTypes.shape({
		icon: PropTypes.string,
		name: PropTypes.string.isRequired,
		nodePath: PropTypes.string
	}).isRequired,
	onNodeSelected: PropTypes.func.isRequired,
	selectedNodeIds: PropTypes.arrayOf(PropTypes.string).isRequired
};
