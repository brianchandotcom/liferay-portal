import ExportLogModal from '../ExportLogModal';
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
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

async function assertLoadingStatesForDownload(container) {
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

	await waitForLoadingToBeRemoved(container);

	expect(
		container.querySelector('.button-root .loading-animation')
	).toBeNull();
}

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

	it('should have a loading state when download is triggered', async () => {
		const {container, debug} = render(
			<ExportLogModal
				description='Test description'
				onClose={noop}
				onSubmit={() => Promise.resolve('csv-string')}
				title='Test Title'
			/>
		);

		await assertLoadingStatesForDownload(container, debug);
	});

	it('should stop loading if the download failed', async () => {
		const {container} = render(
			<ExportLogModal
				description='Test description'
				onClose={noop}
				onSubmit={() => Promise.reject('Request Error')}
				title='Test Title'
			/>
		);

		await assertLoadingStatesForDownload(container);
	});
});
