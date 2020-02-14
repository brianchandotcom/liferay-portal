/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {fireEvent, render} from '@testing-library/react';
import React, {useState} from 'react';

import {ModalContext} from '../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/ModalContext.es';
import {SingleUpdateDueDateModal} from '../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/update-due-date/SingleUpdateDueDateModal.es';
import {InstanceListContext} from '../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/store/InstanceListPageStore.es';
import ToasterProvider from '../../../../../src/main/resources/META-INF/resources/js/shared/components/toaster/ToasterProvider.es';
import {MockRouter} from '../../../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

const ContainerMock = ({children}) => {
	const [updateDueDate, setUpdateDueDate] = useState({
		visible: true
	});

	return (
		<InstanceListContext.Provider value={{setSelectedItems: jest.fn()}}>
			<ModalContext.Provider value={{setUpdateDueDate, updateDueDate}}>
				<ToasterProvider>{children}</ToasterProvider>
			</ModalContext.Provider>
		</InstanceListContext.Provider>
	);
};

describe('The SingleUpdateDueDateModal component should', () => {
	let getByTestId;

	const items = [{dateDue: '2020-02-01T10:00:00', id: 1}];

	const clientMock = {
		get: jest
			.fn()
			.mockRejectedValueOnce(new Error('Request failed'))
			.mockResolvedValue({data: {items}}),
		post: jest
			.fn()
			.mockRejectedValueOnce(new Error('Request failed'))
			.mockResolvedValue({data: {items: []}})
	};

	beforeAll(() => {
		const renderResult = render(
			<MockRouter client={clientMock}>
				<SingleUpdateDueDateModal />
			</MockRouter>,
			{
				wrapper: ContainerMock
			}
		);
		getByTestId = renderResult.getByTestId;

		jest.runAllTimers();
	});

	test('Render modal with error message and retry', () => {
		const alertError = getByTestId('alertError');
		const emptyState = getByTestId('emptyState');
		const retryBtn = getByTestId('retryButton');

		expect(alertError).toHaveTextContent('your-request-has-failed');
		expect(retryBtn).toHaveTextContent('retry');
		expect(emptyState.children[0]).toHaveTextContent(
			'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
		);

		fireEvent.click(retryBtn);
	});

	test('Render modal with form inputs with defaultValues', () => {
		const cancelBtn = getByTestId('cancelButton');
		const commentInput = getByTestId('commentInput');
		const dateInput = getByTestId('dateInput');
		const doneBtn = getByTestId('doneButton');
		const timeInput = getByTestId('timeInput');

		expect(dateInput.value).toBe('02/01/2020');
		expect(timeInput.value).toBe('10:00');
		expect(commentInput.value).toBe('');

		expect(cancelBtn).not.toHaveAttribute('disabled');
		expect(doneBtn).toHaveAttribute('disabled');

		const newDate = '01/01';
		const newTime = '12:00 PM';

		fireEvent.change(dateInput, {target: {value: newDate}});
		fireEvent.change(timeInput, {target: {value: newTime}});
		fireEvent.change(commentInput, {target: {value: 'test'}});

		expect(dateInput.parentNode).toHaveClass('has-error');
		expect(timeInput.parentNode).toHaveClass('has-error');
		expect(doneBtn).toHaveAttribute('disabled');

		fireEvent.change(dateInput, {target: {value: `${newDate}/2020`}});
		fireEvent.change(timeInput, {target: {value: '10:00'}});

		expect(dateInput.parentNode).not.toHaveClass('has-error');
		expect(timeInput.parentNode).not.toHaveClass('has-error');

		expect(doneBtn).not.toHaveAttribute('disabled');

		fireEvent.click(doneBtn);
	});

	test('Render modal reassign error and retry', () => {
		const alertError = getByTestId('alertError');
		const doneBtn = getByTestId('doneButton');

		expect(alertError).toHaveTextContent(
			'your-request-has-failed select-done-to-retry'
		);
		expect(doneBtn).not.toHaveAttribute('disabled');

		fireEvent.click(doneBtn);
	});

	test('Render alert with success message and close modal', () => {
		const alertToast = getByTestId('alertToast');
		const alertClose = alertToast.children[1];

		expect(alertToast).toHaveTextContent(
			'the-due-date-for-this-task-has-been-updated'
		);

		fireEvent.click(alertClose);

		const alertContainer = getByTestId('alertContainer');
		expect(alertContainer.children[0].children.length).toBe(0);
	});
});
