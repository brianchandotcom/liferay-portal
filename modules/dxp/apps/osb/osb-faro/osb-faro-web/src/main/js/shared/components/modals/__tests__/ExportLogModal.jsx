import ExportLogModal from '../ExportLogModal';
import Promise from 'metal-promise';
import React from 'react';
import {
	cleanup,
	fireEvent,
	getAllByText,
	getByLabelText,
	getByTestId,
	getByText,
	render
} from '@testing-library/react';
import {noop} from 'lodash';

jest.unmock('react-dom');

const assertLoadingStatesForDownload = container => {
	fireEvent.click(getByLabelText(container, 'Choose Date Range'));

	const datePickerOverlay = getByTestId(document.body, 'overlay');
	// select day 1
	fireEvent.click(getAllByText(datePickerOverlay, '1')[0]);
	// select day 2
	fireEvent.click(getAllByText(datePickerOverlay, '2')[0]);

	fireEvent.click(getByText(container, 'Download'));

	expect(
		container.querySelector('.button-root .loading-animation')
	).toBeTruthy();

	jest.runAllTimers();

	expect(
		container.querySelector('.button-root .loading-animation')
	).toBeNull();
};

describe('ExportLogModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<ExportLogModal
				description='Test description'
				onClose={noop}
				title='Test Title'
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should have a loading state when download is triggered', () => {
		const {container} = render(
			<ExportLogModal
				description='Test description'
				onClose={noop}
				onSubmit={() => Promise.resolve('csv-string')}
				title='Test Title'
			/>
		);

		assertLoadingStatesForDownload(container);
	});

	it('should stop loading if the download failed', () => {
		const {container} = render(
			<ExportLogModal
				description='Test description'
				onClose={noop}
				onSubmit={() => Promise.reject('Request Error')}
				title='Test Title'
			/>
		);

		assertLoadingStatesForDownload(container);
	});
});
