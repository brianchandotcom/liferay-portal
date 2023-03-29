import ConfirmationModal from '../ConfirmationModal';
import Promise from 'metal-promise';
import React from 'react';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {noop} from 'lodash';

jest.unmock('react-dom');

const MESSAGE = 'message';

const DefaultComponent = props => (
	<ConfirmationModal {...props} onClose={noop} />
);

describe('ConfirmationModal', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);
		expect(container).toMatchSnapshot();
	});

	it('should render with a message', () => {
		const message = 'This is a message.';

		const {getByText} = render(
			<DefaultComponent message='This is a message.' />
		);

		expect(getByText(message)).toBeTruthy();
	});

	it('should render with a warning submit button', () => {
		const {container, getByText} = render(
			<ConfirmationModal
				submitButtonDisplay='warning'
				submitMessage='delete'
			/>
		);

		expect(container.querySelector('.btn-warning')).toBeTruthy();
		expect(getByText('delete')).toBeTruthy();
	});

	it('should render with a custom cancel button text', () => {
		const {getByText} = render(<ConfirmationModal cancelMessage='leave' />);

		expect(getByText('leave')).toBeTruthy();
	});

	it('should submit when the promisse is rejected', () => {
		const {getByText} = render(
			<ConfirmationModal
				onClose={noop}
				onSubmit={() => Promise.reject()}
				submitMessage={MESSAGE}
			/>
		);

		fireEvent.click(getByText(MESSAGE));

		expect(getByText(MESSAGE)).toBeTruthy();
	});

	it('should submit when the promisse is resolved', () => {
		const {getByText} = render(
			<ConfirmationModal
				onClose={noop}
				onSubmit={() => Promise.resolve()}
				submitMessage={MESSAGE}
			/>
		);

		fireEvent.click(getByText(MESSAGE));

		expect(getByText(MESSAGE)).toBeTruthy();
	});

	it('should submit when the submit is not a promise', () => {
		const {getByText} = render(
			<ConfirmationModal
				onClose={noop}
				onSubmit={noop}
				submitMessage={MESSAGE}
			/>
		);

		fireEvent.click(getByText(MESSAGE));

		expect(getByText(MESSAGE)).toBeTruthy();
	});
});
