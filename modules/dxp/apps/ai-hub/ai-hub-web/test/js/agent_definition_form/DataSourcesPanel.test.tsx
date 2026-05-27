/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import DataSourcesPanel from '../../../src/main/resources/META-INF/resources/js/agent_definition_form/DataSourcesPanel';

import type {RelationshipPicker} from '../../../src/main/resources/META-INF/resources/js/agent_definition_form/hooks/useRelationshipPicker';
import type {ContentRetriever} from '../../../src/main/resources/META-INF/resources/js/agent_definition_form/types/ContentRetriever';

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
	overrides: Partial<RelationshipPicker<ContentRetriever>> = {}
): RelationshipPicker<ContentRetriever> {
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

describe('DataSourcesPanel', () => {
	afterEach(() => {
		cleanup();
	});

	it('calls setInputValue when the user types into the field', () => {
		const picker = buildPicker();

		render(
			<DataSourcesPanel contentRetrievers={picker} readOnly={false} />
		);

		fireEvent.change(
			screen.getByTestId('multi-select-input-assignedSources'),
			{target: {value: 'partial'}}
		);

		expect(picker.setInputValue).toHaveBeenCalledWith('partial');
	});

	it('calls setSelected when a source item is added', () => {
		const picker = buildPicker({
			sourceList: [{externalReferenceCode: 'CR_1', title: 'First'}],
		});

		render(
			<DataSourcesPanel contentRetrievers={picker} readOnly={false} />
		);

		fireEvent.click(screen.getByTestId('add-CR_1'));

		expect(picker.setSelected).toHaveBeenCalledWith([
			{externalReferenceCode: 'CR_1', title: 'First'},
		]);
	});

	it('calls setSelected without the removed item when remove is clicked', () => {
		const picker = buildPicker({
			selected: [
				{externalReferenceCode: 'CR_1', title: 'First'},
				{externalReferenceCode: 'CR_2', title: 'Second'},
			],
		});

		render(
			<DataSourcesPanel contentRetrievers={picker} readOnly={false} />
		);

		fireEvent.click(screen.getByTestId('remove-CR_1'));

		expect(picker.setSelected).toHaveBeenCalledWith([
			{externalReferenceCode: 'CR_2', title: 'Second'},
		]);
	});

	it('disables the multi-select when readOnly is true', () => {
		render(
			<DataSourcesPanel
				contentRetrievers={buildPicker()}
				readOnly={true}
			/>
		);

		expect(
			screen.getByTestId('multi-select-input-assignedSources')
		).toBeDisabled();
	});

	it('renders source items as add options', () => {
		const picker = buildPicker({
			sourceList: [
				{externalReferenceCode: 'CR_1', title: 'First'},
				{externalReferenceCode: 'CR_2', title: 'Second'},
			],
		});

		render(
			<DataSourcesPanel contentRetrievers={picker} readOnly={false} />
		);

		expect(screen.getByTestId('add-CR_1')).toBeInTheDocument();
		expect(screen.getByTestId('add-CR_2')).toBeInTheDocument();
	});

	it('renders the currently selected content retrievers using the title field', () => {
		const picker = buildPicker({
			selected: [
				{externalReferenceCode: 'CR_1', title: 'First'},
				{externalReferenceCode: 'CR_2', title: 'Second'},
			],
		});

		render(
			<DataSourcesPanel contentRetrievers={picker} readOnly={false} />
		);

		expect(screen.getByText('First')).toBeInTheDocument();
		expect(screen.getByText('Second')).toBeInTheDocument();
	});

	it('renders the title and assigned-sources label', () => {
		render(
			<DataSourcesPanel
				contentRetrievers={buildPicker()}
				readOnly={false}
			/>
		);

		expect(screen.getByText('data-sources')).toBeInTheDocument();
		expect(screen.getByText('assigned-sources')).toBeInTheDocument();
	});
});
