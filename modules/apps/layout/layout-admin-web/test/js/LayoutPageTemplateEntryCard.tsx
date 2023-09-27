/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {act, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {fetch, openModal} from 'frontend-js-web';
import * as React from 'react';

import LayoutPageTemplateEntryCard from '../../src/main/resources/META-INF/resources/js/LayoutPageTemplateEntryCard';

jest.mock('frontend-js-web', () => ({
	createPortletURL: jest.fn(),
	fetch: jest.fn(),
	openModal: jest.fn(),
}));

const openModalMock = openModal as jest.Mock<typeof openModal>;

const fetchMock = fetch as jest.Mock<typeof fetch>;

const renderComponent = () => {
	return render(
		<LayoutPageTemplateEntryCard
			addLayoutURL=""
			getLayoutPageTemplateEntryListURL=""
			layoutPageTemplateEntryId="1"
			portletNamespace="portletNamespace"
			subtitle="subtitle"
			thumbnailURL=""
			title="title"
		/>
	);
};

describe('LayoutPageTemplateEntryCard', () => {
	beforeEach(() => {
		openModalMock.mockReset();

		fetchMock.mockReturnValue(
			Promise.resolve({
				json: () => {
					return [
						{
							layoutPageTemplateEntryId: '1',
							name: 'name1',
							previewLayout: '',
						},
						{
							layoutPageTemplateEntryId: '2',
							name: 'name2',
							previewLayout: '',
						},
					];
				},
			})
		);
	});

	it('renders', () => {
		renderComponent();

		expect(screen.getByTitle('title')).toBeInTheDocument();
	});

	it('call openModal when clicking on the card', () => {
		renderComponent();

		userEvent.click(screen.getByTitle('title'));

		expect(openModalMock).toBeCalled();
	});

	it('shows a modal when clicking the preview button', async () => {
		renderComponent();

		jest.useFakeTimers();

		userEvent.click(screen.getByTitle('preview-page-template'));

		await act(async () => {
			jest.runAllTimers();
		});

		jest.useRealTimers();

		const button = await screen.findByText(
			'create-page-from-this-template'
		);

		expect(button).toBeInTheDocument();
	});

	it('open creation modal when clicking on modal button', async () => {
		renderComponent();

		jest.useFakeTimers();

		userEvent.click(screen.getByTitle('preview-page-template'));

		await act(async () => {
			jest.runAllTimers();
		});

		jest.useRealTimers();

		const button = await screen.findByText(
			'create-page-from-this-template'
		);

		userEvent.click(button);

		expect(openModalMock).toBeCalled();
	});
});
