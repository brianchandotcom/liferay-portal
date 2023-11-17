import mockStore from 'test/mock-store';
import React, {useState} from 'react';
import ReactDOM from 'react-dom';
import {act, cleanup, fireEvent, render} from '@testing-library/react';
import {DownloadReportButton} from '../DownloadReportButton';
import {DownloadReportModal, ReportType} from '../DownloadReportModal';
import {Provider} from 'react-redux';
import {sub} from 'shared/util/lang';
import {toLocale} from 'shared/util/numbers';
import {useModal} from '@clayui/modal';

jest.unmock('react-dom');

const WrapperCSVComponent = () => (
	<WrapperComponent
		alertMessage={
			sub(
				Liferay.Language.get(
					'the-x-file-is-being-generated-and-your-download-will-start-soon'
				),
				['CSV']
			) as string
		}
		descriptionMessage={
			sub(
				Liferay.Language.get(
					'select-a-date-range-to-export-this-list-as-a-csv-document.-the-maximum-number-of-entries-supported-per-export-is-x.-the-request-may-take-a-couple-minutes-to-process'
				),
				[toLocale(10000)]
			) as string
		}
		infoMessage={Liferay.Language.get(
			'the-individuals-list-will-be-downloaded-respecting-the-current-ordering,-filter,-and-search-results.-please-verify-if-the-desired-changes-are-applied'
		)}
		requiredDateRange
		type={ReportType.CSV}
	/>
);

const WrapperComponent = ({
	alertMessage,
	descriptionMessage,
	infoMessage,
	requiredDateRange,
	type
}) => {
	const [visible, setVisible] = useState(false);
	const {observer} = useModal({onClose: () => setVisible(false)});

	return (
		<>
			{visible && (
				<Provider store={mockStore()}>
					<DownloadReportModal
						alertMessage={alertMessage}
						descriptionMessage={descriptionMessage}
						infoMessage={infoMessage}
						observer={observer}
						onClose={jest.fn()}
						onSubmit={jest.fn()}
						requiredDateRange={requiredDateRange}
						type={type}
					/>
				</Provider>
			)}

			<DownloadReportButton
				disabled={false}
				onClick={() => setVisible(true)}
			/>
		</>
	);
};

describe('DownloadReportModal CSV', () => {
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
			<WrapperCSVComponent />
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
			getByRole('heading', {
				name: /download report/i
			})
		).toBeInTheDocument();

		expect(
			getByText(
				'Select a date range to export this list as a CSV document. The maximum number of entries supported per export is 10,000. The request may take a couple minutes to process.'
			)
		).toBeInTheDocument();

		expect(
			getByText(
				'The individuals list will be downloaded respecting the current ordering, filter, and search results. Please verify if the desired changes are applied.'
			)
		);

		expect(getByText(/date range/i)).toBeInTheDocument();

		expect(getByTestId('cancel')).toBeInTheDocument();
		expect(getByTestId('submit')).toBeInTheDocument();

		expect(container).toMatchSnapshot();
	});

	it('download button should be disabled when there are no date range value', () => {
		const {getByRole, getByTestId} = render(<WrapperCSVComponent />);

		fireEvent.click(
			getByRole('button', {
				name: /download report/i
			})
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(getByTestId('submit')).toHaveAttribute('disabled');
	});

	it('download button should be enabled when there are date range value', () => {
		const {getByRole, getByTestId} = render(<WrapperCSVComponent />);

		fireEvent.click(
			getByRole('button', {
				name: /download report/i
			})
		);

		act(() => {
			jest.runAllTimers();
		});

		const customRangeInput = getByRole('textbox', {name: /date range/i});

		fireEvent.click(customRangeInput);

		const startDate = getByRole('button', {name: /10/i});
		const endDate = getByRole('button', {name: /11/i});

		fireEvent.click(startDate);
		fireEvent.click(endDate);

		expect(customRangeInput).toHaveAttribute(
			'value',
			'2023-11-10 - 2023-11-11'
		);
		expect(getByTestId('submit')).not.toHaveAttribute('disabled');
	});
});
