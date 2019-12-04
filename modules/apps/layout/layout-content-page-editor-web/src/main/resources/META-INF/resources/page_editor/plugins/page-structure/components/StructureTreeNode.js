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
import React from 'react';

const NameButton = ({name}) => {
	return (
		<ClayButton
			className="page-editor__page-structure__tree-node__name-button"
			displayType="unstyled"
		>
			{name}
		</ClayButton>
	);
};

const RemoveButton = () => {
	return (
		<ClayButton
			className="page-editor__page-structure__tree-node__remove-button"
			displayType="unstyled"
		>
			<ClayIcon symbol="times-circle" />
		</ClayButton>
	);
};

export default function StructureTreeNode({node}) {
	return (
		<div className="page-editor__page-structure__tree-node">
			<NameButton name={node.name} />
			{node.removable && <RemoveButton />}
		</div>
	);
}
