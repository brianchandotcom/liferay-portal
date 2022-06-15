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

import '@testing-library/jest-dom/extend-expect';
import {render} from '@testing-library/react';
import React from 'react';

import Sidebar from '../../../../../../../src/main/resources/META-INF/resources/designer/js/definition-builder/diagram-builder/components/sidebar/Sidebar';
import MockDefinitionBuilderContext from '../../../../../../mock/MockDefinitionBuilderContext';
import MockDiagramBuilderContext from '../../../../../../mock/MockDiagramBuilderContext';

describe('The Sidebar component should', () => {
	let container;

	it('Be rendered with correct title and all node types', () => {
		const renderResult = render(
			<MockDefinitionBuilderContext>
				<MockDiagramBuilderContext>
					<Sidebar />
				</MockDiagramBuilderContext>
			</MockDefinitionBuilderContext>
		);

		container = renderResult.container;

		const title = container.querySelector('span.title');

		expect(title).toHaveTextContent('nodes');

		const nodes = container.querySelectorAll('div.node');

		expect(nodes.length).toBe(8);
		expect(nodes[0].classList).toContain('condition-node');
		expect(nodes[1].classList).toContain('end-node');
		expect(nodes[2].classList).toContain('fork-node');
		expect(nodes[3].classList).toContain('join-node');
		expect(nodes[4].classList).toContain('join-xor-node');
		expect(nodes[5].classList).toContain('start-node');
		expect(nodes[6].classList).toContain('state-node');
		expect(nodes[7].classList).toContain('task-node');
	});

	it('Be rendered with selected node info when a node is selected', () => {
		const renderResult = render(
			<MockDefinitionBuilderContext>
				<MockDiagramBuilderContext
					mockSelectedNode={{
						data: {label: {en_US: 'start node'}},
						id: 'node_0',
						type: 'start',
					}}
				>
					<Sidebar />
				</MockDiagramBuilderContext>
			</MockDefinitionBuilderContext>
		);

		container = renderResult.container;

		const title = container.querySelector('span.title');
		const panel = container.querySelector('div.panel');

		expect(title).toHaveTextContent('start');
		expect(panel).toHaveTextContent('information');

		const labels = container.querySelectorAll('label');

		expect(labels.length).toBe(3);
		expect(labels[0]).toHaveTextContent('label*');
		expect(labels[1]).toHaveTextContent('node id*');
		expect(labels[2]).toHaveTextContent('description');

		const inputLabel = container.querySelector('input#nodeLabel');
		const inputId = container.querySelector('input#nodeId');
		const textareaDescription = container.querySelector(
			'textarea#nodeDescription'
		);

		expect(inputLabel).toHaveValue('start node');
		expect(inputId).toHaveValue('node_0');
		expect(textareaDescription).toHaveValue('');
	});
});
