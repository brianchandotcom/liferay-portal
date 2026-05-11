import LifecycleChart from '../LifecycleChart';
import React from 'react';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {
	LifecycleContextProvider,
	useLifecycle
} from '../../context/LifecycleContext';
import {LifecycleStages} from 'contacts/pages/account/utils/constants';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({groupId: '2000'})
}));

jest.mock('shared/hooks/useRequest', () => ({
	useRequest: jest.fn(() => ({
		data: null,
		error: false,
		loading: false
	}))
}));

const mockUseRequest = (state: {
	data?: any;
	error?: boolean;
	loading?: boolean;
}) => {
	const useRequest = require('shared/hooks/useRequest');
	useRequest.useRequest = jest.fn(() => ({
		data: state.data,
		error: state.error ?? false,
		loading: state.loading ?? false
	}));
};

const sampleStages = [
	{
		accountCount: 13,
		averageDaysInStage: 9.8,
		description: 'Aware description.',
		percentage: 43.6,
		stageType: LifecycleStages.AWARE
	},
	{
		accountCount: 64,
		averageDaysInStage: 4.6,
		description: 'Engaged description.',
		percentage: 28.4,
		stageType: LifecycleStages.ENGAGED
	},
	{
		accountCount: 41,
		averageDaysInStage: 4.5,
		description: 'Pipeline description.',
		percentage: 18.2,
		stageType: LifecycleStages.PIPELINE
	},
	{
		accountCount: 22,
		averageDaysInStage: 12,
		description: 'Onboarding description.',
		percentage: 9.8,
		stageType: LifecycleStages.ONBOARDING
	},
	{
		accountCount: 0,
		averageDaysInStage: 0,
		description: 'Established description.',
		percentage: 0,
		stageType: LifecycleStages.ESTABLISHED
	}
];

const FilterProbe = () => {
	const {filters} = useLifecycle();
	return (
		<span data-testid='lifecycle-filter'>
			{filters.lifecycleStageFilter ?? 'none'}
		</span>
	);
};

const renderChart = () =>
	render(
		<LifecycleContextProvider>
			<LifecycleChart />
			<FilterProbe />
		</LifecycleContextProvider>
	);

describe('LifecycleChart', () => {
	afterEach(cleanup);

	it('should render a loading spinner while the request is in flight', () => {
		mockUseRequest({loading: true});

		const {container, queryByText} = renderChart();

		expect(container.querySelector('.loading-root')).toBeInTheDocument();
		expect(queryByText('Aware')).toBeNull();
	});

	it('should render every stage title in the empty state when the request errors', () => {
		mockUseRequest({error: true});

		const {getByText} = renderChart();

		expect(getByText('Aware')).toBeInTheDocument();
		expect(getByText('Engaged')).toBeInTheDocument();
		expect(getByText('Pipeline')).toBeInTheDocument();
		expect(getByText('Onboarding')).toBeInTheDocument();
		expect(getByText('Established')).toBeInTheDocument();
	});

	it('should omit bar and trapezium visuals in the empty state', () => {
		mockUseRequest({error: true});

		const {container, getAllByText} = renderChart();

		expect(getAllByText('no activity')).toHaveLength(5);

		expect(container.querySelector('.stage-metrics__bar')).toBeNull();
		expect(
			container.querySelector('.stage-metrics__bar--empty')
		).toBeNull();
		expect(container.querySelector('.stage-progression__fill')).toBeNull();
	});

	it('should fall back to the empty state when the data array is empty', () => {
		mockUseRequest({data: []});

		const {getAllByText} = renderChart();

		expect(getAllByText('no activity')).toHaveLength(5);
	});

	it('should render each stage with its metrics when data is available', () => {
		mockUseRequest({data: sampleStages});

		const {container, getByText} = renderChart();

		expect(getByText('13')).toBeInTheDocument();
		expect(getByText('64')).toBeInTheDocument();
		expect(getByText('41')).toBeInTheDocument();
		expect(getByText('22')).toBeInTheDocument();

		expect(container.querySelectorAll('.stage-metrics__bar')).toHaveLength(
			4
		);
		expect(
			container.querySelectorAll('.stage-metrics__bar--empty')
		).toHaveLength(1);

		expect(
			container.querySelectorAll('.stage-progression__fill')
		).toHaveLength(4);
	});

	it('should show "avg. day" for non-zero averages and "no activity" otherwise', () => {
		mockUseRequest({data: sampleStages});

		const {getAllByText, getByText} = renderChart();

		expect(getAllByText('avg. day')).toHaveLength(4);
		expect(getByText('no activity')).toBeInTheDocument();
	});

	it('should compute progression labels from adjacent accountCounts', () => {
		mockUseRequest({data: sampleStages});

		const {container} = renderChart();

		const labels = Array.from(
			container.querySelectorAll('.label-info')
		).map(el => el.textContent);

		expect(labels[0]).toContain('492%');
		expect(labels[1]).toContain('64%');
		expect(labels[2]).toContain('54%');
		expect(labels[3]).toContain('0%');
	});

	it('should default the context lifecycleStageFilter to AT_RISK on mount', () => {
		mockUseRequest({data: sampleStages});

		const {getByTestId} = renderChart();

		expect(getByTestId('lifecycle-filter').textContent).toBe(
			LifecycleStages.AT_RISK
		);
	});

	it('should update the context lifecycleStageFilter when a stage filter button is clicked', () => {
		mockUseRequest({data: sampleStages});

		const {getAllByRole, getByTestId} = renderChart();

		const filterButtons = getAllByRole('button');
		fireEvent.click(filterButtons[0]);

		expect(getByTestId('lifecycle-filter').textContent).toBe(
			LifecycleStages.AWARE
		);

		fireEvent.click(filterButtons[2]);

		expect(getByTestId('lifecycle-filter').textContent).toBe(
			LifecycleStages.PIPELINE
		);
	});
});
