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

import DefinitionBuilder from '../../../../src/main/resources/META-INF/resources/designer/js/definition-builder/DefinitionBuilder';

const props = {
	displayNames: ['English (United States)'],
	languageIds: ['en_US'],
	title: 'New Workflow',
	translations: {},
	version: '0',
};

describe('The DefinitionBuilder component should', () => {
	let container;

	beforeAll(async () => {
		const renderResult = render(<DefinitionBuilder {...props} />);

		container = renderResult.container;
	});

	it('Be rendered with DiagramBuilder and UpperToolbar', () => {
		const diagramBuilder = container.querySelector('div.diagram-builder');
		const upperToolbar = container.querySelector('nav.upper-toolbar');

		expect(diagramBuilder).toBeTruthy();
		expect(upperToolbar).toBeTruthy();
	});
});
