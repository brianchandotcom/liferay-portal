/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface HierarchyDataRendererProps {
	value: boolean;
}
declare function HierarchyDataRenderer({
	value,
}: HierarchyDataRendererProps): JSX.Element;
export default function ObjectRelationshipsFDSPropsTransformer({
	...otherProps
}: {
	[x: string]: any;
}): {
	customDataRenderers: {
		hierarchyDataRenderer: typeof HierarchyDataRenderer;
	};
	onActionDropdownItemClick({
		action,
		itemData,
	}: {
		action: {
			data: {
				id: string;
			};
		};
		itemData: ObjectRelationship;
	}): void;
};
export {};
