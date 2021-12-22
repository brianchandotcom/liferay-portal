/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
