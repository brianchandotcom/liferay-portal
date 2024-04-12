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

jest.mock('shared/hooks/useTimeZone', () => ({
	useTimeZone: () => ({timeZoneId: 'UTC'})
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
	infoMessage: string;
	minDate?: Moment;
	requiredDateRange?: boolean;
	type: ReportType;
}

const WrapperComponent: React.FC<IWrapperComponent> = ({
	alertMessage,
	children,
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
				'The Individuals list will be downloaded respecting the current ordering, filter, and search results. Please verify if the desired changes are applied.'
			)
		);

		expect(getByText('Date Range')).toBeInTheDocument();

		expect(getByTestId('cancel')).toBeInTheDocument();
		expect(getByTestId('submit')).toBeInTheDocument();
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
				'The dashboard will be downloaded exactly as it is displayed on your screen. Please verify if the desired tabs and filters are selected before downloading.'
			)
		);

		expect(
			getByText(
				'Only select a date range if you want to modify the current date filter.'
			)
		).toBeInTheDocument();

		expect(getByText('Date Range (Optional)')).toBeInTheDocument();

		expect(getByTestId('cancel')).toBeInTheDocument();
		expect(getByTestId('submit')).toBeInTheDocument();
	});
});
