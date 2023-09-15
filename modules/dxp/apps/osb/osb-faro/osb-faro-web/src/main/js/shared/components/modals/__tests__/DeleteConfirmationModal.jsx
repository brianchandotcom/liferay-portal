import DeleteConfirmationModal from '../DeleteConfirmationModal';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('DeleteConfirmationModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<DeleteConfirmationModal
				DeleteConfirmationText='Test delete confirm text'
				onClose={jest.fn()}
				onSubmit={jest.fn()}
				title='Test title'
			>
				<p>{'I am child'}</p>
			</DeleteConfirmationModal>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('renders w/ custom delete button label', () => {
		const {getByText} = render(
			<DeleteConfirmationModal
				deleteButtonLabel='Custom Delete Button'
				onClose={jest.fn()}
				onSubmit={jest.fn()}
				title='Test title'
			/>
		);

		jest.runAllTimers();

		expect(getByText('Custom Delete Button')).toBeTruthy();
	});

	it('renders w/ input disabled', () => {
		const {getByTestId} = render(
			<DeleteConfirmationModal
				deleteButtonLabel='Custom Delete Button'
				disabled
				onClose={jest.fn()}
				onSubmit={jest.fn()}
				title='Test title'
			/>
		);

		jest.runAllTimers();

		expect(getByTestId('delete-confirmation-input').disabled).toBeTruthy();
	});
});
