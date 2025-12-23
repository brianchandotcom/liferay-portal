/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import '@testing-library/jest-dom';

import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {RulesModalContext} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/RulesModalContext';
import {StoreAPIContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import {State} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/reducers';
import addRule from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/addRule';
import {
	CACHE_KEYS,
	disposeCache,
	initializeCache,
	setCacheItem,
} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/utils/cache';
import RulesModal from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page_rules/components/RulesModal';
import {Rule} from '../../../../../../src/main/resources/META-INF/resources/page_editor/types/Rule';

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/serviceFetch',
	() => jest.fn(() => Promise.resolve())
);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/addRule',
	() => jest.fn()
);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/utils/useConditionValues',
	() => jest.fn(() => [{id: 'condition-id'}])
);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/utils/isInputFragment',
	() => jest.fn(() => false)
);

jest.mock('frontend-js-components-web', () => ({
	...jest.requireActual<typeof import('frontend-js-components-web')>(
		'frontend-js-components-web'
	),
	openToast: jest.fn(),
}));

const DEFAULT_RULE: Rule = {
	actions: [{id: 'action-1', type: undefined}],
	conditionType: 'all',
	conditions: [{id: 'condition-1', type: undefined}],
	id: '',
	name: 'Rule',
};

function MockRulesContextProvider({
	children,
	editingRule: initialEditingRule,
}: {
	children: React.ReactNode;
	editingRule: Rule;
}) {
	const [editingRule, setEditingRule] = React.useState(initialEditingRule);
	const [visible, setVisible] = React.useState(true);
	const [trigger, setTrigger] = React.useState<HTMLButtonElement | null>(
		null
	);
	const [shouldValidate, setShouldValidate] = React.useState(false);

	return (
		<RulesModalContext.Provider
			value={{
				editingRule,
				scriptError: '',
				scriptInputRef: {current: null},
				setEditingRule,
				setScriptError: jest.fn(),
				setShouldValidate,
				setTrigger,
				setVisible,
				shouldValidate,
				trigger,
				visible,
			}}
		>
			{children}
		</RulesModalContext.Provider>
	);
}

const renderComponent = ({
	rules = [],
	editingRule = DEFAULT_RULE,
}: {
	editingRule?: Rule;
	rules?: Array<any>;
} = {}) => {
	jest.useFakeTimers();

	render(
		<StoreAPIContextProvider
			dispatch={() => Promise.resolve()}
			getState={() =>
				({
					fragmentEntryLinks: {},
					layoutData: {
						deletedItems: [],
						items: {
							item1: {
								config: {
									name: 'containercillo',
								},
								itemId: 'item1',
								type: LAYOUT_DATA_ITEM_TYPES.container,
							},
						},
						pageRules: rules,
					},
				}) as unknown as State
			}
		>
			<MockRulesContextProvider editingRule={editingRule}>
				<RulesModal />
			</MockRulesContextProvider>
		</StoreAPIContextProvider>
	);

	act(() => {
		jest.advanceTimersByTime(100);
	});

	jest.useRealTimers();
};

const selectPickerOption = async (pickerLabel: string, optionValue: string) => {
	const picker = await screen.findByLabelText(pickerLabel);

	await userEvent.click(picker);

	const option = await screen.findByText(optionValue, {
		selector: '[role="option"]',
	});

	await userEvent.click(option);
};

describe('RulesSidebar', () => {
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

	it('renders', async () => {
		renderComponent();

		expect(screen.getByText('add-action')).toBeInTheDocument();
		expect(screen.getByText('add-condition')).toBeInTheDocument();
	});

	it('does not allow saving an incomplete rule', async () => {
		renderComponent();

		await userEvent.click(screen.getByText('save'));

		expect(
			screen.getByText('please-review-the-following-fields-before-saving')
		).toBeInTheDocument();
	});

	it('does not allow saving an unnamed rule', async () => {
		renderComponent({editingRule: {...DEFAULT_RULE, name: ''}});

		await userEvent.click(screen.getByText('save'));

		const nameInput = screen.getByLabelText('rule-name');

		await expect(nameInput).toHaveAccessibleDescription(
			'this-field-is-required'
		);

		const nameErrorLink = screen.getByRole('link', {
			name: 'the-rule-name-field-is-required',
		});

		await userEvent.click(nameErrorLink);

		await expect(nameInput).toHaveFocus();
	});

	it('does allow completing a condition', async () => {
		renderComponent();

		await selectPickerOption('select-item-for-the-condition', 'user');
		await selectPickerOption('select-condition', 'is-the-user');
		await selectPickerOption('select-user', 'user1');

		expect(
			screen.getByText('user1', {selector: '[role="combobox"]'})
		).toBeInTheDocument();
	});

	it('does allow completing a action', async () => {
		renderComponent();

		await selectPickerOption('select-action', 'show');

		await selectPickerOption(
			'select-fragment-for-the-action',
			'containercillo'
		);

		expect(
			screen.getByText('containercillo', {selector: '[role="combobox"]'})
		).toBeInTheDocument();
	});

	it('limits action types to show/hide for readOnly non-input fragments', async () => {
		renderComponent({
			editingRule: {
				...DEFAULT_RULE,
				actions: [
					{
						id: 'action-1',
						itemId: 'item1',
						readOnly: true,
						type: undefined,
					},
				],
			},
		});

		const picker = await screen.findByLabelText('select-action');

		await userEvent.click(picker);

		expect(
			await screen.findByText('show', {selector: '[role="option"]'})
		).toBeInTheDocument();

		expect(
			await screen.findByText('hide', {selector: '[role="option"]'})
		).toBeInTheDocument();

		expect(
			screen.queryByText('enable', {selector: '[role="option"]'})
		).not.toBeInTheDocument();

		expect(
			screen.queryByText('disable', {selector: '[role="option"]'})
		).not.toBeInTheDocument();
	});

	it('allows saving a rule', async () => {
		renderComponent();

		await selectPickerOption('select-item-for-the-condition', 'user');
		await selectPickerOption('select-condition', 'is-the-user');
		await selectPickerOption('select-user', 'user1');

		await selectPickerOption('select-action', 'show');
		await selectPickerOption(
			'select-fragment-for-the-action',
			'containercillo'
		);

		await userEvent.click(screen.getByText('save'));

		expect(addRule).toBeCalledWith(
			expect.objectContaining({
				actions: [
					expect.objectContaining({
						itemId: 'item1',
						type: 'show',
					}),
				],
				conditions: [
					expect.objectContaining({
						field: 'user',
						options: {
							type: 'equal',
							value: 'userId1',
						},
						type: 'user',
					}),
				],
				name: 'Rule',
			})
		);
	});

	it('removes selection in first condition when pressing delete condition', async () => {
		renderComponent();

		await selectPickerOption('select-item-for-the-condition', 'user');
		await selectPickerOption('select-condition', 'is-the-user');
		await selectPickerOption('select-user', 'user1');

		await userEvent.click(screen.getByTitle('delete-condition'));

		expect(screen.queryByText('select-condition')).not.toBeInTheDocument();
		expect(screen.queryByText('select-user')).not.toBeInTheDocument();
	});

	it('removes selection in first action when pressing delete action', async () => {
		renderComponent();

		await selectPickerOption('select-action', 'show');
		await selectPickerOption(
			'select-fragment-for-the-action',
			'containercillo'
		);

		await userEvent.click(screen.getByTitle('delete-action'));

		expect(
			screen.queryByText('select-item-for-the-action')
		).not.toBeInTheDocument();
		expect(
			screen.queryByText('select-fragment-for-the-action')
		).not.toBeInTheDocument();
	});

	it('shows all errors in the error summary with links', async () => {
		const getLink = (name: string) => screen.queryByRole('link', {name});

		renderComponent();

		await userEvent.click(screen.getByText('save'));

		const firstConditionPicker = getLink('select-item-for-the-condition');

		await expect(firstConditionPicker).toBeInTheDocument();
		await expect(getLink('select-action')).toBeInTheDocument();

		await userEvent.click(firstConditionPicker as HTMLElement);

		await expect(
			screen.getByRole('combobox', {
				name: 'select-item-for-the-condition',
			})
		).toHaveFocus();

		await selectPickerOption('select-item-for-the-condition', 'user');

		await userEvent.click(screen.getByText('save'));

		await expect(firstConditionPicker).not.toBeInTheDocument();
		await expect(getLink('select-condition')).toBeInTheDocument();
		await expect(getLink('select-action')).toBeInTheDocument();

		await selectPickerOption('select-condition', 'is-the-user');

		await userEvent.click(screen.getByText('save'));

		await expect(getLink('select-condition')).not.toBeInTheDocument();
		await expect(getLink('select-user')).toBeInTheDocument();

		await selectPickerOption('select-user', 'user1');

		await userEvent.click(screen.getByText('save'));

		await expect(getLink('select-user')).not.toBeInTheDocument();

		await selectPickerOption('select-action', 'hide');

		await userEvent.click(screen.getByText('save'));

		await expect(getLink('select-action')).not.toBeInTheDocument();
		await expect(
			getLink('select-fragment-for-the-action')
		).toBeInTheDocument();

		await selectPickerOption(
			'select-fragment-for-the-action',
			'containercillo'
		);

		await userEvent.click(screen.getByText('save'));

		await expect(addRule).toBeCalled();
	});

	it('resets the fragment when the action changes', async () => {
		renderComponent({
			editingRule: {
				...DEFAULT_RULE,
				actions: [{id: 'action-1', itemId: 'item1', type: 'show'}],
			},
		});

		await expect(
			screen.getByRole('combobox', {
				name: 'select-fragment-for-the-action',
			})
		).toHaveTextContent('containercillo');

		await selectPickerOption('select-action', 'hide');

		await expect(
			screen.getByRole('combobox', {
				name: 'select-fragment-for-the-action',
			})
		).toHaveTextContent('select');
	});
});
