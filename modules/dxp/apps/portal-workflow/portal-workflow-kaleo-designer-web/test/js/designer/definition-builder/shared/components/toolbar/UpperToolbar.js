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

import UpperToolbar from '../../../../../../../src/main/resources/META-INF/resources/designer/js/definition-builder/shared/components/toolbar/UpperToolbar';
import MockDefinitionBuilderContext from '../../../../../../mock/MockDefinitionBuilderContext';

const props = {
	displayNames: ['English (United States)'],
	languageIds: ['en_US'],
	title: 'New Workflow',
	translations: {},
	version: '0',
};

describe('The UpperToolbar component should', () => {
	let container;

	beforeAll(() => {
		const renderResult = render(
			<MockDefinitionBuilderContext>
				<UpperToolbar {...props} />
			</MockDefinitionBuilderContext>
		);

		container = renderResult.container;
	});

	it('Be rendered with all buttons and title input', () => {
		const tbarItems = container.querySelectorAll('li.tbar-item');

		expect(tbarItems.length).toBe(7);
		expect(tbarItems[0]).toHaveTextContent('en_US');
		const inputTitle = tbarItems[1].querySelector('input#definition-title');

		expect(inputTitle).toBeTruthy();

		expect(tbarItems[2].children[0].children[0]).toHaveClass(
			'lexicon-icon-info-circle-open'
		);
		expect(tbarItems[3]).toHaveTextContent('cancel');
		expect(tbarItems[4]).toHaveTextContent('save');
		expect(tbarItems[5]).toHaveTextContent('publish');
		const sourceButton = tbarItems[6].querySelector(
			'svg.lexicon-icon-code'
		);

		expect(sourceButton).toBeTruthy();
	});
});
