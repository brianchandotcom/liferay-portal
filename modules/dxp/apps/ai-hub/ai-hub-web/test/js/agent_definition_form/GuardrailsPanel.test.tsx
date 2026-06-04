/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import GuardrailsPanel from '../../../src/main/resources/META-INF/resources/js/agent_definition_form/GuardrailsPanel';

import type {RelationshipPicker} from '../../../src/main/resources/META-INF/resources/js/agent_definition_form/hooks/useRelationshipPicker';
import type {Guardrail} from '../../../src/main/resources/META-INF/resources/js/agent_definition_form/types/Guardrail';

jest.mock('@clayui/multi-select', () => {
	const React = require('react');

	return {
		__esModule: true,
		default: ({
			disabled,
			inputName,
			items,
			locator,
			onChange,
			onItemsChange,
			sourceItems,
			value,
		}: any) =>
			React.createElement(
				'div',
				{'data-testid': `multi-select-${inputName}`},
				React.createElement('input', {
					'data-testid': `multi-select-input-${inputName}`,
					disabled,
					'name': inputName,
					'onChange': (event: any) => onChange(event.target.value),
					value,
				}),
				React.createElement(
					'ul',
					{'data-testid': `multi-select-items-${inputName}`},
					items.map((item: any, index: number) =>
						React.createElement(
							'li',
							{key: index},
							item[locator.label],
							React.createElement(
								'button',
								{
									'data-testid': `remove-${item[locator.value]}`,
									'onClick': () =>
										onItemsChange(
											items.filter(
												(i: any) =>
													i[locator.value] !==
													item[locator.value]
											)
										),
									'type': 'button',
								},
								'remove'
							)
						)
					)
				),
				React.createElement(
					'ul',
					{'data-testid': `multi-select-sources-${inputName}`},
					sourceItems.map((item: any, index: number) =>
						React.createElement(
							'li',
							{key: index},
							React.createElement(
								'button',
								{
									'data-testid': `add-${item[locator.value]}`,
									'onClick': () =>
										onItemsChange([...items, item]),
									'type': 'button',
								},
								item[locator.label]
							)
						)
					)
				)
			),
	};
});

(global as any).Liferay = {
	Language: {
		get: (key: string) => key,
	},
};

function buildPicker(
	overrides: Partial<RelationshipPicker<Guardrail>> = {}
): RelationshipPicker<Guardrail> {
	return {
		inputValue: '',
		reset: jest.fn(),
		selected: [],
		setInputValue: jest.fn(),
		setSelected: jest.fn(),
		sourceList: [],
		sync: jest.fn(),
		...overrides,
	};
}

describe('GuardrailsPanel', () => {
	afterEach(() => {
		cleanup();
	});

	it('calls setSelected when a source item is added', () => {
		const picker = buildPicker({
			sourceList: [{externalReferenceCode: 'MAT_1', title: 'Guard A'}],
		});

		render(<GuardrailsPanel guardrails={picker} readOnly={false} />);

		fireEvent.click(screen.getByTestId('add-MAT_1'));

		expect(picker.setSelected).toHaveBeenCalledWith([
			{externalReferenceCode: 'MAT_1', title: 'Guard A'},
		]);
	});

	it('calls setSelected without the removed item when remove is clicked', () => {
		const picker = buildPicker({
			selected: [
				{externalReferenceCode: 'MAT_1', title: 'Guard A'},
				{externalReferenceCode: 'MAT_2', title: 'Guard B'},
			],
		});

		render(<GuardrailsPanel guardrails={picker} readOnly={false} />);

		fireEvent.click(screen.getByTestId('remove-MAT_1'));

		expect(picker.setSelected).toHaveBeenCalledWith([
			{externalReferenceCode: 'MAT_2', title: 'Guard B'},
		]);
	});

	it('disables the multi-select when readOnly is true', () => {
		render(<GuardrailsPanel guardrails={buildPicker()} readOnly={true} />);

		expect(
			screen.getByTestId('multi-select-input-assignedGuardrails')
		).toBeDisabled();
	});

	it('renders the currently selected templates using the title field', () => {
		const picker = buildPicker({
			selected: [
				{externalReferenceCode: 'MAT_1', title: 'Guard A'},
				{externalReferenceCode: 'MAT_2', title: 'Guard B'},
			],
		});

		render(<GuardrailsPanel guardrails={picker} readOnly={false} />);

		expect(screen.getByText('Guard A')).toBeInTheDocument();
		expect(screen.getByText('Guard B')).toBeInTheDocument();
	});

	it('renders the title and assigned-guardrails label', () => {
		render(<GuardrailsPanel guardrails={buildPicker()} readOnly={false} />);

		expect(screen.getByText('guardrails')).toBeInTheDocument();
		expect(screen.getByText('assigned-guardrails')).toBeInTheDocument();
	});
});
