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

import JoinNode from '../../../../../../../src/main/resources/META-INF/resources/designer/js/definition-builder/diagram-builder/components/nodes/JoinNode';
import MockDefinitionBuilderContext from '../../../../../../mock/MockDefinitionBuilderContext';
import MockDiagramBuilderContext from '../../../../../../mock/MockDiagramBuilderContext';

describe('The JoinNode component should', () => {
	let container;

	it('Be rendered with correct icon and default values for label and description, if they are not set', () => {
		const renderResult = render(
			<MockDefinitionBuilderContext>
				<MockDiagramBuilderContext>
					<JoinNode />
				</MockDiagramBuilderContext>
			</MockDefinitionBuilderContext>
		);

		container = renderResult.container;

		const icon = container.querySelector('svg.lexicon-icon-radio-button');

		expect(icon).toBeTruthy();

		const label = container.querySelector('span.node-label');
		const description = container.querySelector('span.node-description');

		expect(label).toHaveTextContent('join');
		expect(description).toHaveTextContent(
			'all-interactions-need-to-be-closed'
		);
	});

	it('Be rendered with custom label and description', () => {
		const renderResult = render(
			<MockDefinitionBuilderContext>
				<MockDiagramBuilderContext>
					<JoinNode
						data={{
							description: 'test description',
							label: {en_US: 'test label'},
						}}
					/>
				</MockDiagramBuilderContext>
			</MockDefinitionBuilderContext>
		);

		container = renderResult.container;

		const label = container.querySelector('span.node-label');
		const description = container.querySelector('span.node-description');

		expect(label).toHaveTextContent('test label');
		expect(description).toHaveTextContent('test description');
	});
});
