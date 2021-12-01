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

import DiagramBuilder from '../../../../../src/main/resources/META-INF/resources/designer/js/definition-builder/diagram-builder/DiagramBuilder';
import MockDefinitionBuilderContext from '../../../../mock/MockDefinitionBuilderContext';

describe('The DiagramBuilder component should', () => {
	let container;

	beforeAll(() => {
		const renderResult = render(
			<MockDefinitionBuilderContext>
				<DiagramBuilder version="0" />
			</MockDefinitionBuilderContext>
		);

		container = renderResult.container;
	});

	it('Be rendered with control buttons, default nodes and sidebar', () => {
		const controlButtons = container.querySelector(
			'div.react-flow__controls'
		);
		const startNode = container.querySelector('div.start-node');
		const endNode = container.querySelector('div.end-node');
		const sidebar = container.querySelector('div.sidebar');

		expect(controlButtons).toBeTruthy();
		expect(startNode).toBeTruthy();
		expect(endNode).toBeTruthy();
		expect(sidebar).toBeTruthy();
	});
});
