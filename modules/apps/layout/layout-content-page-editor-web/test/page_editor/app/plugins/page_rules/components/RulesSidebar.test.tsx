/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, fireEvent, render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import '@testing-library/jest-dom';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {StoreAPIContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import {State} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/reducers';
import deleteRule from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/deleteRule';
import {
	CACHE_KEYS,
	disposeCache,
	initializeCache,
	setCacheItem,
} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/utils/cache';
import RulesSidebar from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page_rules/components/RulesSidebar';
import {Rule} from '../../../../../../src/main/resources/META-INF/resources/page_editor/types/Rule';

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

const mockOpenRulesModal = jest.fn();

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/RulesModalContext',
	() => ({
		useRulesModal: () => ({
			closeRulesModal: jest.fn(),
			openRulesModal: mockOpenRulesModal,
			updateRule: jest.fn(),
		}),
		useRulesModalState: () => ({rule: null, visible: false}),
	})
);

const renderComponent = ({rules = []}: {rules?: Rule[]} = {}) =>
	render(

		// @ts-ignore

		<DndProvider backend={HTML5Backend}>
			<StoreAPIContextProvider
				dispatch={() => Promise.resolve()}
				getState={() =>
					({
						fragmentEntryLinks: {
							fragmentEntryLink1: {
								name: 'Fragment 1',
							},
							fragmentEntryLink2: {
								fragmentEntryType: 'input',
								name: 'Fragment 2',
							},
							fragmentEntryLink3: {
								fragmentEntryType: 'input',
								name: 'Fragment 3',
							},
						},
						layoutData: {
							deletedItems: [],
							items: {
								formItem1: {
									config: {
										fragmentEntryLinkId:
											'fragmentEntryLink2',
									},
									itemId: 'formItem1',
									type: LAYOUT_DATA_ITEM_TYPES.fragment,
								},
								formItem2: {
									config: {
										fragmentEntryLinkId:
											'fragmentEntryLink3',
									},
									itemId: 'formItem2',
									type: LAYOUT_DATA_ITEM_TYPES.fragment,
								},
								item1: {
									config: {
										fragmentEntryLinkId:
											'fragmentEntryLink1',
									},
									itemId: 'item1',
									type: LAYOUT_DATA_ITEM_TYPES.fragment,
								},
							},
							pageRules: rules || [],
						},
					}) as unknown as State
				}
			>
				<RulesSidebar />
			</StoreAPIContextProvider>
		</DndProvider>
	);

describe('RulesSidebar', () => {
	beforeEach(() => {
		disposeCache();
		initializeCache();

		mockOpenRulesModal.mockClear();

		setCacheItem({
			data: [
				{screenName: 'user1', userId: 'userId1'},
				{screenName: 'user2', userId: 'userId2'},
			],
			key: CACHE_KEYS.users,
			status: 'saved',
		});
	});

	it('shows empty state when there are no rules', () => {
		renderComponent();

		expect(screen.getByText('no-rules-yet')).toBeInTheDocument();
	});

	it('shows list of rules when there is any', () => {
		renderComponent({
			rules: [
				{
					actions: [],
					conditionType: 'any',
					conditions: [],
					id: 'rule-1',
					name: 'Rule 1',
				},
			],
		});

		expect(screen.getByText('Rule 1')).toBeInTheDocument();
	});

	it('shows user conditions and actions description', () => {
		renderComponent({
			rules: [
				{
					actions: [
						{
							id: 'action-id',
							itemId: 'item1',
							type: 'show',
						},
					],
					conditionType: 'all',
					conditions: [
						{
							field: 'user',
							id: 'condition-id',
							options: {
								type: 'equal',
								value: 'userId1',
							},
							type: 'user',
						},
					],
					id: 'rule-1',
					name: 'Rule 1',
				},
			],
		});

		const rule = document.querySelector('li')!;

		expect(rule.textContent).toBe(
			'Rule 1ifuseris-the-useruser1showFragment 1'
		);
	});

	it('shows form fragment conditions and actions description', () => {
		renderComponent({
			rules: [
				{
					actions: [
						{
							id: 'action-id',
							itemId: 'formItem1',
							type: 'disable',
						},
					],
					conditionType: 'all',
					conditions: [
						{
							field: 'formItem2',
							id: 'condition-id',
							options: {
								type: 'equal',
								value: 'true',
							},
							type: 'form',
						},
					],
					id: 'rule-1',
					name: 'Rule 1',
				},
			],
		});

		const rule = document.querySelector('li')!;

		expect(rule.textContent).toBe(
			'Rule 1ifFragment 3is-equal-totruedisableFragment 2'
		);
	});

	it('shows advanced rule description', () => {
		renderComponent({
			rules: [
				{
					actions: [
						{
							id: 'action-id',
							itemId: 'item1',
							type: 'show',
						},
					],
					id: 'rule-1',
					name: 'Rule 1',
					script: 'some script',
				},
			],
		});

		const rule = document.querySelector('li')!;

		expect(rule.textContent).toBe('Rule 1advanced-rule');
	});

	it('adds aria-label to the user rule with conditions and actions description', () => {
		renderComponent({
			rules: [
				{
					actions: [
						{
							id: 'action-id',
							itemId: 'item1',
							type: 'show',
						},
					],
					conditionType: 'all',
					conditions: [
						{
							field: 'user',
							id: 'condition-id',
							options: {
								type: 'equal',
								value: 'userId1',
							},
							type: 'user',
						},
					],
					id: 'rule-1',
					name: 'Rule 1',
				},
			],
		});

		expect(
			screen.getByLabelText(
				'Rule 1: if user is-the-user user1 show fragment Fragment 1'
			)
		).toBeInTheDocument();
	});

	it('adds aria-label to the user form fragment rule with conditions and actions description', () => {
		renderComponent({
			rules: [
				{
					actions: [
						{
							id: 'action-id',
							itemId: 'formItem1',
							type: 'disable',
						},
					],
					conditionType: 'all',
					conditions: [
						{
							field: 'formItem2',
							id: 'condition-id',
							options: {
								type: 'equal',
								value: 'true',
							},
							type: 'form',
						},
					],
					id: 'rule-1',
					name: 'Rule 1',
				},
			],
		});

		expect(
			screen.getByLabelText(
				'Rule 1: if Fragment 3 is-equal-to true disable fragment Fragment 2'
			)
		).toBeInTheDocument();
	});

	it('filters rules', async () => {
		const rules = [
			{
				actions: [],
				conditionType: 'any',
				conditions: [],
				id: 'apple-rule',
				name: 'Apple',
			},
			{
				actions: [],
				conditionType: 'any',
				conditions: [],
				id: 'blackberry-rule',
				name: 'Blackberry',
			},
			{
				actions: [],
				conditionType: 'any',
				conditions: [],
				id: 'orange-rule',
				name: 'Orange',
			},
		] as Rule[];

		renderComponent({rules});

		const newRuleButton = screen.getByText('new-rule');

		for (const rule of rules) {
			expect(await screen.findByText(rule.name)).toBeInTheDocument();
		}

		const search = await screen.findByPlaceholderText(/search/i);
		await userEvent.type(search, 'app');

		await waitFor(() => {
			expect(screen.getByText('Apple')).toBeInTheDocument();
			expect(screen.queryByText('Blackberry')).not.toBeInTheDocument();
			expect(screen.queryByText('Orange')).not.toBeInTheDocument();
			expect(newRuleButton).not.toBeInTheDocument();
		});

		await userEvent.clear(search);
		await userEvent.type(search, 'fruit');

		await waitFor(() => {
			expect(screen.queryByText('Apple')).not.toBeInTheDocument();
			expect(screen.getByText('no-results-found')).toBeInTheDocument();
			expect(newRuleButton).not.toBeInTheDocument();
		});
	});

	it('shows a warning alert when there are 20 or more rules', async () => {
		const rules = [];

		for (let i = 0; i < 20; i++) {
			rules.push({
				actions: [],
				conditionType: 'any',
				conditions: [],
				id: `rule-${i}`,
				name: `Rule ${i}`,
			});
		}

		renderComponent({rules: rules as Rule[]});

		expect(
			screen.getByText('excessive-rules-may-affect-page-performance')
		).toBeInTheDocument();

		const search = await screen.findByPlaceholderText(/search/i);
		await userEvent.type(search, 'rule 1');

		await waitFor(() => {
			expect(
				screen.queryByText(
					'excessive-rules-may-affect-page-performance'
				)
			).not.toBeInTheDocument();
		});
	});

	it('navigates through the rules using the keyboard', async () => {
		renderComponent({
			rules: [
				{
					actions: [],
					conditionType: 'any',
					conditions: [],
					id: 'apple-rule',
					name: 'Apple',
				},
				{
					actions: [],
					conditionType: 'any',
					conditions: [],
					id: 'blackberry-rule',
					name: 'Blackberry',
				},
			],
		});

		const appleRule = screen.getByRole('menuitem', {name: 'Apple:'});
		const blackberryRule = screen.getByRole('menuitem', {
			name: 'Blackberry:',
		});

		appleRule.focus();

		expect(appleRule).toHaveFocus();

		await userEvent.keyboard('{ArrowDown}');

		expect(blackberryRule).toHaveFocus();

		await userEvent.keyboard('{ArrowUp}');

		expect(appleRule).toHaveFocus();
	});

	describe('Rules Modal', () => {
		afterAll(() => {
			jest.useRealTimers();
		});

		beforeAll(() => {
			jest.useFakeTimers();
		});

		it('opens modal to create new rule when clicking that button', async () => {
			renderComponent({
				rules: [
					{
						actions: [],
						conditionType: 'all',
						conditions: [],
						id: 'rule',
						name: 'rule',
					},
					{
						actions: [],
						conditionType: 'all',
						conditions: [],
						id: 'rule-1',
						name: 'rule 1',
					},
				],
			});

			const addRuleButton = screen.getByText('new-rule');

			fireEvent.click(addRuleButton);

			act(() => {
				jest.advanceTimersByTime(100);
			});

			expect(mockOpenRulesModal).toHaveBeenCalledTimes(1);
		});

		it('opens modal to edit a rule when clicking that option', async () => {
			renderComponent({
				rules: [
					{
						actions: [],
						conditionType: 'all',
						conditions: [],
						id: 'rule-1',
						name: 'rule 1',
					},
				],
			});

			const openOptionsButton =
				document.querySelector('.dropdown-toggle')!;

			fireEvent.click(openOptionsButton);

			act(() => {
				jest.advanceTimersByTime(100);
			});

			fireEvent.click(screen.getByText('edit'));

			act(() => {
				jest.advanceTimersByTime(100);
			});

			expect(mockOpenRulesModal).toHaveBeenCalledTimes(1);

			expect(mockOpenRulesModal).toHaveBeenCalledWith(
				expect.objectContaining({
					rule: expect.objectContaining({id: 'rule-1'}),
				})
			);
		});

		it('calls delete rule thunk with correct rule id when clicking that option', async () => {
			renderComponent({
				rules: [
					{
						actions: [],
						conditionType: 'all',
						conditions: [],
						id: 'rule-1',
						name: 'rule 1',
					},
				],
			});

			const openOptionsButton =
				document.querySelector('.dropdown-toggle')!;

			fireEvent.click(openOptionsButton);

			act(() => {
				jest.advanceTimersByTime(100);
			});

			fireEvent.click(screen.getByText('delete'));

			expect(deleteRule).toBeCalledWith(
				expect.objectContaining({
					ruleId: 'rule-1',
				})
			);
		});
	});
});
