/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';

import {DiagramBuilderContextProvider} from '../../src/main/resources/META-INF/resources/designer/js/definition-builder/diagram-builder/DiagramBuilderContext';
import {defaultNodes} from '../../src/main/resources/META-INF/resources/designer/js/definition-builder/diagram-builder/components/nodes/utils';

export default function MockDiagramBuilderContext({
	children,
	mockSelectedNode = null,
}) {
	const [collidingElements] = useState(null);
	const [, setElements] = useState(defaultNodes);
	const [selectedItem, setSelectedItem] = useState(mockSelectedNode);
	const [selectedItemNewId, setSelectedItemNewId] = useState(null);

	const contextProps = {
		collidingElements,
		selectedItem,
		selectedItemNewId,
		setElements,
		setSelectedItem,
		setSelectedItemNewId,
	};

	return (
		<DiagramBuilderContextProvider {...contextProps}>
			{children}
		</DiagramBuilderContextProvider>
	);
}
