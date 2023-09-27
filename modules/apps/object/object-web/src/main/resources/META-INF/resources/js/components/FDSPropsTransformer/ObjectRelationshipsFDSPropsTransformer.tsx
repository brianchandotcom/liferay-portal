/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
