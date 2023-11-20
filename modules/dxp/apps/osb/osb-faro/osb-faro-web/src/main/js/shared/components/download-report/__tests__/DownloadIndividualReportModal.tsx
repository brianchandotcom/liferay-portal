import React, {useState} from 'react';
import ReactDOM from 'react-dom';
import {act, cleanup, fireEvent, render} from '@testing-library/react';
import {DownloadReportButton} from '../DownloadReportButton';
import {Modal} from '../DownloadIndividualReportModal';
import {useModal} from '@clayui/modal';

jest.unmock('react-dom');

const WrapperComponent = () => {
	const [visible, setVisible] = useState(false);
	const {observer} = useModal({onClose: () => setVisible(false)});

	return (
		<>
			{visible && (
				<Modal
					observer={observer}
					onClose={jest.fn()}
					onSubmit={jest.fn()}
				/>
			)}

			<DownloadReportButton
				disabled={false}
				onClick={() => setVisible(true)}
			/>
		</>
	);
};

describe('DownloadReportIndividualModal CSV', () => {
	afterEach(() => {
		jest.clearAllTimers();

		cleanup();
	});

	beforeAll(() => {
		jest.useFakeTimers();

		// @ts-ignore
		ReactDOM.createPortal = jest.fn(element => element);
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	it('renders component', () => {
		const {container, getByRole, getByTestId, getByText} = render(
			<WrapperComponent />
		);

		fireEvent.click(
			getByRole('button', {
				name: /download report/i
			})
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(
			getByText(
				'This list will be downloaded respecting the current ordering and search results. The maximum number of entries supported per export is 10,000. Are you sure you want to download the CSV file?'
			)
		).toBeInTheDocument();

		expect(getByTestId('cancel')).toBeInTheDocument();
		expect(getByTestId('submit')).toBeInTheDocument();

		expect(container).toMatchSnapshot();
	});
});
