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

import ClayLabel from '@clayui/label';
import React from 'react';

interface HierarchyDataRendererProps {
	value: boolean;
}

function HierarchyDataRenderer({value}: HierarchyDataRendererProps) {
	return (
		<ClayLabel displayType={value ? 'info' : 'success'}>
			{value
				? Liferay.Language.get('child')
				: Liferay.Language.get('parent')}
		</ClayLabel>
	);
}

export default function ObjectRelationshipsFDSPropsTransformer({
	...otherProps
}) {
	return {
		...otherProps,
		customDataRenderers: {
			hierarchyDataRenderer: HierarchyDataRenderer,
		},
		onActionDropdownItemClick({
			action,
			itemData,
		}: {
			action: {data: {id: string}};
			itemData: ObjectRelationship;
		}) {
			if (action.data.id === 'deleteObjectRelationship') {
				Liferay.fire('deleteObjectRelationship', {itemData});
			}
		},
	};
}
