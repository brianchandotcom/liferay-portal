/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
interface PropertiesTreeViewProps {
	schemaUIData: APISchemaUIData;
	searchState: SearchState;
	setSchemaUIData: Dispatch<SetStateAction<APISchemaUIData>>;
}
interface SearchState {
	filteredSchemaProperties: TreeViewItemData[];
	searchKeyword: string;
}
export default function PropertiesTreeView({
	schemaUIData,
	searchState,
	setSchemaUIData,
}: PropertiesTreeViewProps): JSX.Element;
export {};
