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
import {
	CACHE_KEYS,
	disposeCache,
	initializeCache,
	setCacheItem,
} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/utils/cache';
import RulesSidebar from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page_rules/components/RulesSidebar';

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
			availableSegmentsEntries: {},
		},
	})
);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/serviceFetch',
	() => jest.fn(() => Promise.resolve({}))
);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/deleteRule',
	() => jest.fn()
);

const renderComponent = ({rules = []} = {}) =>
	render(
		<StoreAPIContextProvider
			dispatch={() => Promise.resolve()}
			getState={() => ({
				fragmentEntryLinks: {
					fragmentEntryLink1: {
						name: 'Fragment 1',
					},
				},
				layoutData: {
					items: {
						item1: {
							config: {
								fragmentEntryLinkId: 'fragmentEntryLink1',
							},
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

	beforeEach(() => {
		disposeCache();
		initializeCache();

		setCacheItem({
			data: [
				{screenName: 'user1', userId: 'userId1'},
				{screenName: 'user2', userId: 'userId2'},
			],
			key: CACHE_KEYS.users,
			status: 'saved',
		});
	});

	it('shows empty state when there are no rules', async () => {
		await act(async () => {
			renderComponent();
		});

		expect(screen.getByText('no-rules-yet')).toBeInTheDocument();
	});

	it('shows list of rules when there is any', async () => {
		await act(async () => {
			renderComponent({
				rules: [
					{
						actions: [],
						conditions: [],
						id: 'rule-1',
						name: 'Rule 1',
					},
				],
			});
		});

		expect(screen.getByText('Rule 1')).toBeInTheDocument();
	});

	it('opens modal to create new rule when clicking that button', async () => {
		await act(async () => {
			renderComponent({
				rules: [
					{
						actions: [],
						conditions: [],
						id: 'rule',
						name: 'rule',
					},
					{
						actions: [],
						conditions: [],
						id: 'rule-1',
						name: 'rule 1',
					},
				],
			});
		});

		const addRuleButton = screen.getByText('new-rule');

		act(() => {
			fireEvent.click(addRuleButton);
		});

		act(() => {
			jest.runAllTimers();
		});

		const modalTitle = document.querySelector('.modal-title');

		expect(modalTitle.innerHTML).toBe('new-rule');

		expect(screen.getByLabelText('rule-name')).toHaveValue('rule 2');
	});

	it('opens modal to edit a rule when clicking that option', async () => {
		await act(async () => {
			renderComponent({
				rules: [
					{
						actions: [],
						conditions: [],
						id: 'rule-1',
						name: 'rule 1',
					},
				],
			});
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

	it('calls delete rule thunk with correct rule id when clicking that option', async () => {
		await act(async () => {
			renderComponent({
				rules: [
					{
						actions: [],
						conditions: [],
						id: 'rule-1',
						name: 'rule 1',
					},
				],
			});
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

	it('shows conditions and actions description', async () => {
		await act(async () => {
			renderComponent({
				rules: [
					{
						actions: [
							{
								action: 'fragment',
								id: 'action-id',
								itemId: 'item1',
								type: 'show',
							},
						],
						conditionType: 'all',
						conditions: [
							{
								condition: 'user',
								id: 'condition-id',
								type: 'user',
								value: 'userId1',
							},
						],
						id: 'rule-1',
						name: 'Rule 1',
					},
				],
			});
		});

		const rule = document.querySelector('li');

		expect(rule.textContent).toBe(
			'Rule 1ifuseris-the-useruser1showfragmentFragment 1'
		);
	});

	it('adds aria-label to the rule with conditions and actions description', async () => {
		await act(async () => {
			renderComponent({
				rules: [
					{
						actions: [
							{
								action: 'fragment',
								id: 'action-id',
								itemId: 'item1',
								type: 'show',
							},
						],
						conditionType: 'all',
						conditions: [
							{
								condition: 'user',
								id: 'condition-id',
								type: 'user',
								value: 'userId1',
							},
						],
						id: 'rule-1',
						name: 'Rule 1',
					},
				],
			});
		});

		expect(
			screen.getByLabelText(
				'Rule 1: if user is-the-user user1 show fragment Fragment 1'
			)
		).toBeInTheDocument();
	});
});
