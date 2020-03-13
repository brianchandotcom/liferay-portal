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

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import Tabs from '../../../../src/main/resources/META-INF/resources/js/shared/components/tabs/Tabs.es';
import {MockRouter} from '../../../mock/MockRouter.es';

describe('The Tabs component should', () => {
	let container, renderResult, tabButtons;
	const setCurrentTab = jest.fn();

	beforeAll(() => {
		const tabs = [
			{name: 'Tab 1', tabKey: 'tab1'},
			{name: 'Tab 2', tabKey: 'tab2'},
			{name: 'Tab 3', tabKey: 'tab3'},
		];

		renderResult = render(
			<MockRouter>
				<Tabs
					currentTab="tab3"
					setCurrentTab={setCurrentTab}
					tabs={tabs}
				/>
			</MockRouter>
		);

		container = renderResult.container;
		tabButtons = container.querySelectorAll('button');
	});

	test('Render tabs correctly', () => {
		expect(tabButtons.length).toBe(3);
		expect(tabButtons[0]).toHaveTextContent('Tab 1');
		expect(tabButtons[1]).toHaveTextContent('Tab 2');
		expect(tabButtons[2]).toHaveTextContent('Tab 3');
		expect(tabButtons[2].classList).toContain('active');
	});

	test('Call setCurrentTab with correct key when respective tab is clicked', () => {
		fireEvent.click(tabButtons[0]);

		expect(setCurrentTab).toHaveBeenCalledWith('tab1');

		fireEvent.click(tabButtons[1]);

		expect(setCurrentTab).toHaveBeenCalledWith('tab2');

		fireEvent.click(tabButtons[2]);

		expect(setCurrentTab).toHaveBeenCalledWith('tab3');
	});
});
