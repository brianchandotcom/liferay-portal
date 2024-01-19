import ClayForm from '@clayui/form';
import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import moment, {Moment} from 'moment';
import React, {useState} from 'react';
import ReactDOM from 'react-dom';
import {act, cleanup, fireEvent, render} from '@testing-library/react';
import {ApolloProvider} from '@apollo/react-hooks';
import {Checkbox, Containers, formatContainers} from '../DownloadPDFReport';
import {DownloadReportButton} from '../DownloadReportButton';
import {DownloadReportModal, ReportType} from '../DownloadReportModal';
import {MockedProvider} from '@apollo/react-testing';
import {mockPreferenceReq} from 'test/graphql-data';
import {MomentDateRange} from 'shared/components/DateRangeInput';
import {Provider} from 'react-redux';
import {sub} from 'shared/util/lang';
import {toLocale} from 'shared/util/numbers';
import {useModal} from '@clayui/modal';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456',
		groupId: '2000',
		query: {
			rangeKey: '30'
		}
	})
}));

const WrapperCSVComponent = props => (
	<WrapperComponent
		{...props}
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
		infoMessage={
			sub(
				Liferay.Language.get(
					'the-x-list-will-be-downloaded-respecting-the-current-ordering,-filter,-and-search-results.-please-verify-if-the-desired-changes-are-applied'
				),
				[Liferay.Language.get('individuals')]
			) as string
		}
		requiredDateRange
		type={ReportType.CSV}
	/>
);

const WrapperPDFomponent = ({children, ...otherProps}) => (
	<WrapperComponent
		alertMessage={
			sub(
				Liferay.Language.get(
					'the-x-file-is-being-generated-and-your-download-will-start-soon'
				),
				['PDF']
			) as string
		}
		descriptionMessage={
			sub(
				Liferay.Language.get(
					'select-the-reports,-and-optionally-specify-the-date-range-to-generate-a-PDF-file-from-the-current-dashboard.-your-download-may-take-a-couple-of-minutes-to-process'
				),
				[toLocale(10000)]
			) as string
		}
		infoMessage={Liferay.Language.get(
			'the-dashboard-will-be-downloaded-exactly-as-it-is-displayed-on-your-screen.-please-verify-if-the-desired-tabs-and-filters-are-selected-before-downloading'
		)}
		type={ReportType.PDF}
		{...otherProps}
	>
		{children}
	</WrapperComponent>
);

interface IWrapperComponent extends React.HTMLAttributes<HTMLElement> {
	alertMessage: string;
	date?: MomentDateRange;
	descriptionMessage: string;
	infoMessage: string;
	minDate?: Moment;
	requiredDateRange?: boolean;
	type: ReportType;
}

const WrapperComponent: React.FC<IWrapperComponent> = ({
	alertMessage,
	children,
	descriptionMessage,
	infoMessage,
	requiredDateRange = false,
	type,
	...otherProps
}) => {
	const [visible, setVisible] = useState(false);
	const {observer} = useModal({onClose: () => setVisible(false)});

	return (
		<>
			{visible && (
				<ApolloProvider client={client}>
					<MockedProvider mocks={[mockPreferenceReq()]}>
						<Provider store={mockStore()}>
							<DownloadReportModal
								{...otherProps}
								alertMessage={alertMessage}
								descriptionMessage={descriptionMessage}
								infoMessage={infoMessage}
								observer={observer}
								onClose={jest.fn()}
								onSubmit={jest.fn()}
								requiredDateRange={requiredDateRange}
								type={type}
							>
								{children}
							</DownloadReportModal>
						</Provider>
					</MockedProvider>
				</ApolloProvider>
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
		const {getByRole, getByTestId, getByText} = render(
			<WrapperCSVComponent
				date={{end: moment(0), start: moment(0)}}
				maxDate={moment(0)}
				minDate={moment(0).subtract(1, 'year')}
			/>
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
				'The Individuals list will be downloaded respecting the current ordering, filter, and search results. Please verify if the desired changes are applied.'
			)
		);

		expect(getByText('Date Range')).toBeInTheDocument();

		expect(getByTestId('cancel')).toBeInTheDocument();
		expect(getByTestId('submit')).toBeInTheDocument();
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
		const {getByRole, getByTestId} = render(
			<WrapperCSVComponent
				date={{end: moment(0), start: moment(0)}}
				maxDate={moment(0)}
				minDate={moment(0).subtract(1, 'year')}
			/>
		);

		fireEvent.click(
			getByRole('button', {
				name: /download report/i
			})
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(getByRole('textbox', {name: /date range/i})).toHaveAttribute(
			'value',
			'1970-01-01 - 1970-01-01'
		);
		expect(getByTestId('submit')).not.toHaveAttribute('disabled');
	});
});

describe('DownloadReportModal PDF', () => {
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
		const containers = [
			Containers.AcquisitionsCard,
			Containers.ActiveIndividualsCard,
			Containers.AssetAppearsOnCard,
			Containers.AudienceCard,
			Containers.CohortAnalysisCard,
			Containers.CurrentTotalsCard,
			Containers.DistributionBreakdownCard,
			Containers.DownloadsByLocationCard,
			Containers.DownloadsByTechnologyCard,
			Containers.EnrichedProfilesCard,
			Containers.InterestsCard,
			Containers.SearchTermsCard,
			Containers.SegmentCompositionCard,
			Containers.SegmentCriteriaCard,
			Containers.SegmentMembershipCard,
			Containers.SessionsByLocationCard,
			Containers.SessionTechnologyCard,
			Containers.SiteActivityCard,
			Containers.SubmissionsByLocationCard,
			Containers.SubmissionsByTechnologyCard,
			Containers.TopInterestsAsOfYesterdayCard,
			Containers.TopInterestsCard,
			Containers.TopPagesCard,
			Containers.ViewsByLocationCard,
			Containers.ViewsByTechnologyCard,
			Containers.VisitorsBehaviorCard,
			Containers.VisitorsByTimeCard
		];

		const {getByRole, getByTestId, getByText} = render(
			<WrapperPDFomponent
				date={{end: moment(0), start: moment(0)}}
				maxDate={moment(0)}
				minDate={moment(0).subtract(1, 'year')}
			>
				<ClayForm.Group>
					<label>{Liferay.Language.get('select-reports')}</label>

					{Object.values(formatContainers(containers)).map(
						({id, label}) => (
							<Checkbox
								key={id}
								label={label}
								onChange={jest.fn()}
							/>
						)
					)}
				</ClayForm.Group>
			</WrapperPDFomponent>
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
				'Select the reports, and optionally specify the date range to generate a PDF file from the current dashboard. Your download may take a couple of minutes to process.'
			)
		).toBeInTheDocument();

		expect(
			getByText(
				'The dashboard will be downloaded exactly as it is displayed on your screen. Please verify if the desired tabs and filters are selected before downloading.'
			)
		);

		expect(getByText('Date Range (Optional)')).toBeInTheDocument();

		expect(getByTestId('cancel')).toBeInTheDocument();
		expect(getByTestId('submit')).toBeInTheDocument();
	});
});
