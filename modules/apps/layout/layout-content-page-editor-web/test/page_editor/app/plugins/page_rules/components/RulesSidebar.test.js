/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {StoreAPIContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import deleteRule from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/deleteRule';
import RulesSidebar from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page_rules/components/RulesSidebar';

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/deleteRule',
	() => jest.fn()
);

const renderComponent = ({rules = []} = {}) =>
	render(
		<StoreAPIContextProvider
			dispatch={() => {}}
			getState={() => ({
				fragmentEntryLinks: [],
				layoutData: {
					items: {
						itemId: {
							itemId: 'item1',
							type: LAYOUT_DATA_ITEM_TYPES.fragment,
						},
					},
					pageRules: rules,
				},
			})}
		>
			<RulesSidebar />
		</StoreAPIContextProvider>
	);

describe('RulesSidebar', () => {
	afterAll(() => {
		jest.useRealTimers();
	});

	beforeAll(() => {
		jest.useFakeTimers();
	});

	it('shows empty state when there are no rules', () => {
		renderComponent();

		expect(screen.getByText('no-rules-yet')).toBeInTheDocument();
	});

	it('shows list of rules when there is any', () => {
		renderComponent({
			rules: [
				{
					id: 'rule-1',
					name: 'Rule 1',
				},
			],
		});

		expect(screen.getByText('Rule 1')).toBeInTheDocument();
	});

	it('opens modal to create new rule when clicking that button', () => {
		renderComponent({
			rules: [
				{
					id: 'rule',
					name: 'rule',
				},
				{
					id: 'rule-1',
					name: 'rule 1',
				},
			],
		});

		const addRuleButton = screen.getByText('new-rule');

		fireEvent.click(addRuleButton);

		act(() => {
			jest.runAllTimers();
		});

		const modalTitle = document.querySelector('.modal-title');

		expect(modalTitle.innerHTML).toBe('new-rule');

		expect(screen.getByLabelText('rule-name')).toHaveValue('rule 2');
	});

	it('opens modal to edit a rule when clicking that option', () => {
		renderComponent({
			rules: [
				{
					id: 'rule-1',
					name: 'rule 1',
				},
			],
		});

		const openOptionsButton = document.querySelector('.dropdown-toggle');

		fireEvent.click(openOptionsButton);

		act(() => {
			jest.runAllTimers();
		});

		fireEvent.click(screen.getByText('edit'));

		act(() => {
			jest.runAllTimers();
		});

		const modalTitle = document.querySelector('.modal-title');

		expect(modalTitle.innerHTML).toBe('edit-rule');

		expect(screen.getByLabelText('rule-name')).toHaveValue('rule 1');
	});

	it('calls delete rule thunk with correct rule id when clicking that option', () => {
		renderComponent({
			rules: [
				{
					id: 'rule-1',
					name: 'rule 1',
				},
			],
		});

		const openOptionsButton = document.querySelector('.dropdown-toggle');

		fireEvent.click(openOptionsButton);

		act(() => {
			jest.runAllTimers();
		});

		fireEvent.click(screen.getByText('delete'));

		expect(deleteRule).toBeCalledWith(
			expect.objectContaining({
				ruleId: 'rule-1',
			})
		);
	});
});
