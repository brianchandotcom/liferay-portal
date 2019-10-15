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

import {ClayCardWithHorizontal} from '@clayui/card';
import PropTypes from 'prop-types';
import React from 'react';

export default function TreeviewCard({node, onNodeSelected, selectedNodesIds}) {
	return (
		<div className="p-2">
			<ClayCardWithHorizontal
				onSelectChange={() => onNodeSelected(node.id)}
				selected={selectedNodesIds.includes(node.id)}
				symbol={node.icon}
				title={node.name}
			/>
		</div>
	);
}

TreeviewCard.propTypes = {
	node: PropTypes.shape({
		icon: PropTypes.string,
		name: PropTypes.string.isRequired
	}).isRequired,
	onNodeSelected: PropTypes.func.isRequired,
	selectedNodesIds: PropTypes.arrayOf(PropTypes.string).isRequired
};
