/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	cleanup,
	fireEvent,
	render,
	screen,
	waitFor,
} from '@testing-library/react';
import {fetch} from 'frontend-js-web';
import React from 'react';

import SpacePicker from '../../../../components/ObjectField/Tabs/BasicInfo/SpacePicker';

jest.mock('frontend-js-web', () => ({
	fetch: jest.fn(),
	sub: jest.fn((langKey, arg) => langKey.replace('x', arg)),
}));

jest.mock(
	'../../../../components/ObjectField/Tabs/BasicInfo/SpaceSticker',
	() => {
		return function DummySticker(props: {name?: string}) {
			return <span>{props.name ?? 'SpaceSticker'}</span>;
		};
	}
);

const mockSpacesResponse = {
	items: [
		{
			externalReferenceCode: 1,
			name: 'Marketing Space',
			settings: {logoColor: 'red'},
		},
		{
			externalReferenceCode: 2,
			name: 'Engineering Space',
			settings: {logoColor: 'blue'},
		},
	],
};

describe('The SpacePicker component', () => {
	const defaultProps = {
		onChange: jest.fn(),
		value: undefined,
	};

	beforeEach(() => {
		jest.clearAllMocks();

		(fetch as jest.Mock).mockResolvedValue({
			json: async () => mockSpacesResponse,
		});
	});

	afterEach(cleanup);

	it('calls onChange with spaceId when an option is selected', async () => {
		const onChangeMock = jest.fn();

		render(<SpacePicker {...defaultProps} onChange={onChangeMock} />);

		await waitFor(() => expect(fetch).toHaveBeenCalled());

		fireEvent.click(screen.getByText('choose-a-space'));

		const option = await screen.findByRole('option', {
			name: 'Marketing Space',
		});

		fireEvent.click(option);

		expect(onChangeMock).toHaveBeenCalledWith('1');
	});

	it('displays error message when the error prop is passed', async () => {
		const errorMessage = 'This field is required';

		render(<SpacePicker {...defaultProps} error={errorMessage} />);

		expect(screen.getByText(errorMessage)).toBeInTheDocument();
	});

	it('space options are fetched and displayed when clicked', async () => {
		render(<SpacePicker {...defaultProps} />);

		await waitFor(() => expect(fetch).toHaveBeenCalled());

		const trigger = screen.getByText('choose-a-space');

		fireEvent.click(trigger);

		expect(
			await screen.findByRole('option', {name: 'Marketing Space'})
		).toBeInTheDocument();

		expect(
			await screen.findByRole('option', {name: 'Engineering Space'})
		).toBeInTheDocument();
	});

	it('space options are not rendered when the API response is empty', async () => {
		(fetch as jest.Mock).mockResolvedValueOnce({
			json: async () => ({items: []}),
		});

		render(<SpacePicker {...defaultProps} />);

		await waitFor(() => expect(fetch).toHaveBeenCalled());

		const trigger = screen.getByText('choose-a-space');

		fireEvent.click(trigger);

		expect(screen.queryByText('Engineering Space')).not.toBeInTheDocument();
		expect(screen.queryByText('Marketing Space')).not.toBeInTheDocument();
	});

	it('renders the label and initial placeholder', async () => {
		render(<SpacePicker {...defaultProps} />);

		expect(screen.getByText('cms-space')).toBeInTheDocument();

		expect(screen.getByText('choose-a-space')).toBeInTheDocument();
	});

	it('renders the selected space name if a value is provided', async () => {
		render(<SpacePicker {...defaultProps} value="2" />);

		await waitFor(() => expect(fetch).toHaveBeenCalled());

		expect(screen.queryByText('choose-a-space')).not.toBeInTheDocument();

		const combobox = screen.getByRole('combobox');

		expect(combobox).toHaveTextContent('Engineering Space');
	});
});
