/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen, waitFor} from '@testing-library/react';
import React from 'react';

import DigitalSalesRoomService from '../../../src/main/resources/META-INF/resources/js/commons/DigitalSalesRoomService';
import DSRRoomSaveAsTemplate from '../../../src/main/resources/META-INF/resources/js/components/DSRRoomSaveAsTemplate';
import {TDSRRoomSaveAsTemplateProps} from '../../../src/main/resources/META-INF/resources/js/components/DSRTypes';
import {setFieldValue} from '../utils';

const renderComponent = ({
	closeModal,
	digitalSalesRoomId,
}: TDSRRoomSaveAsTemplateProps) => {
	return render(
		<DSRRoomSaveAsTemplate
			closeModal={closeModal}
			digitalSalesRoomId={digitalSalesRoomId}
		></DSRRoomSaveAsTemplate>
	);
};

describe('DSRRoomSaveAsTemplate', () => {
	it('renders the correct UI elements', async () => {
		renderComponent({
			closeModal: jest.fn(),
			digitalSalesRoomId: 10,
		});

		expect(screen.getByTestId('descriptionInput')).toBeInTheDocument();
		expect(screen.getByTestId('roomNameInput')).toBeInTheDocument();
		expect(
			screen.queryByRole('button', {name: 'cancel'})
		).toBeInTheDocument();
		expect(
			screen.queryByRole('button', {name: 'save'})
		).toBeInTheDocument();
	});

	it('closes modal on cancel button', async () => {
		const closeModal = jest.fn();

		renderComponent({
			closeModal,
			digitalSalesRoomId: 10,
		});

		screen.getByRole('button', {name: 'cancel'}).click();

		expect(closeModal).toBeCalledTimes(1);
	});

	it('calls API on save button', async () => {
		const spyOnPostDigitalSalesRoom = jest.spyOn(
			DigitalSalesRoomService,
			'postDigitalSalesRoomDigitalSalesRoomTemplate'
		);

		renderComponent({
			closeModal: jest.fn(),
			digitalSalesRoomId: 10,
		});

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(screen.queryByTestId('roomNameError')).toBeInTheDocument();

		await setFieldValue(
			screen.getByTestId('roomNameInput'),
			'testRoomName'
		);

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(screen.queryByTestId('roomNameError')).not.toBeInTheDocument();

		expect(spyOnPostDigitalSalesRoom).toBeCalledWith(10, {
			description: '',
			name: 'testRoomName',
		});

		await setFieldValue(
			screen.getByTestId('descriptionInput'),
			'testDescription'
		);

		await waitFor(() => {
			screen.getByRole('button', {name: 'save'}).click();
		});

		expect(spyOnPostDigitalSalesRoom).toBeCalledWith(10, {
			description: 'testDescription',
			name: 'testRoomName',
		});
	});
});
