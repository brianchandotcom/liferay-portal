/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {
	SelectOptions,
	SpaceMembersInputWithSelect,
} from '../../../../src/main/resources/META-INF/resources/js/main/spaces/SpaceMembersInputWithSelect';

describe('SpaceMembersInputWithSelect', () => {
	const {ResizeObserver: ResizeObserverOriginal} = window;

	beforeAll(() => {
		window.ResizeObserver = jest.fn().mockImplementation(() => ({
			disconnect: jest.fn(),
			observe: jest.fn(),
			unobserve: jest.fn(),
		}));
	});

	afterEach(() => {
		jest.clearAllMocks();
	});

	afterAll(() => {
		window.ResizeObserver = ResizeObserverOriginal;
		jest.restoreAllMocks();
	});

	it('accepts a custom className', () => {
		const customClass = 'custom-class';

		const {container} = render(
			<SpaceMembersInputWithSelect className={customClass} />
		);

		expect(container.getElementsByClassName(customClass)).toHaveLength(1);
	});

	it('renders with initial value for select', () => {
		const selectValue = SelectOptions.USERS;

		render(<SpaceMembersInputWithSelect selectValue={selectValue} />);

		const typeSelect = screen.getByRole('combobox', {
			name: 'add-people-to-collaborate',
		});
		expect(typeSelect).toBeInTheDocument();
		expect(typeSelect).toHaveValue(selectValue);
	});

	it('renders with initial value for select', () => {
		const inputValue = 'initial value';

		render(<SpaceMembersInputWithSelect inputValue={inputValue} />);

		expect(
			screen.getByPlaceholderText('enter-name-or-email')
		).toBeInTheDocument();
	});

	it('calls "onInputChange" callback when changing value for input', async () => {
		const onInputChange = jest.fn();
		const inputText = 'test';

		render(<SpaceMembersInputWithSelect onInputChange={onInputChange} />);

		expect(onInputChange).not.toHaveBeenCalled();

		await userEvent.type(
			screen.getByPlaceholderText('enter-name-or-email'),
			inputText
		);

		expect(onInputChange).toHaveBeenCalledTimes(inputText.length);
		expect(onInputChange).toHaveBeenCalledWith(inputText);
	});

	it('calls "onSelectChange" callback when changing value for input', async () => {
		const onSelectChange = jest.fn();

		render(<SpaceMembersInputWithSelect onSelectChange={onSelectChange} />);

		expect(onSelectChange).not.toHaveBeenCalled();

		await userEvent.selectOptions(
			screen.getByRole('combobox', {name: 'add-people-to-collaborate'}),
			SelectOptions.GROUPS
		);

		expect(onSelectChange).toHaveBeenCalledTimes(1);
		expect(onSelectChange).toHaveBeenCalledWith(SelectOptions.GROUPS);
	});
});
