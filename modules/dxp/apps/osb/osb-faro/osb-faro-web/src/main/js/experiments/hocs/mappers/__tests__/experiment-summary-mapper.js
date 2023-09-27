import getSummaryMapper from 'experiments/hocs/mappers/experiment-summary-mapper';
import {mergedVariants} from 'experiments/util/experiments';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const DXP_VARIANTS_MOCK = [
	{
		changes: 0,
		control: true,
		dxpVariantId: 'DEFAULT',
		dxpVariantName: 'Control',
		trafficSplit: 50,
		uniqueVisitors: 1
	},
	{
		changes: 0,
		control: false,
		dxpVariantId: '4203992243',
		dxpVariantName: 'Variant 01',
		trafficSplit: 50,
		uniqueVisitors: 1
	}
];

const VARIANT_METRICS_MOCK = [
	{
		confidenceInterval: [0.3, 0.4],
		dxpVariantId: 'DEFAULT',
		improvement: 1,
		median: 0.5,
		probabilityToWin: 0.2
	},
	{
		confidenceInterval: [0.3, 0.4],
		dxpVariantId: '4203992243',
		improvement: 1,
		median: 0.5,
		probabilityToWin: 0.2
	}
];

const BEST_VARIANT = mergedVariants(
	DXP_VARIANTS_MOCK,
	VARIANT_METRICS_MOCK
).reduce((prev, current) =>
	prev.improvement > current.improvement ? prev : current
);

const DATA_MOCK = {
	experiment: {
		bestVariant: BEST_VARIANT,
		currentStep: 3,
		description:
			'Lorem ipsum, dolor sit amet consectetur adipisicing elit.',
		dxpExperienceName: 'Experience Name',
		dxpSegmentName: 'Segment Name',
		dxpVariants: DXP_VARIANTS_MOCK,
		finishedDate: '2019-08-14T12:00:00.063Z',
		goal: {
			metric: 'CLICK_RATE'
		},
		id: '370220349949814900',
		metrics: {
			completion: 100,
			elapsedDays: 5,
			estimatedDaysLeft: 15,
			variantMetrics: VARIANT_METRICS_MOCK
		},
		pageURL: 'http://localhost/web/guest/content-page',
		progress: 100,
		publishedDXPVariantId: '4203992243',
		sessions: 3000,
		startedDate: '2019-08-05T20:26:59.063Z',
		winnerDXPVariantId: '4203992243'
	}
};

describe('Summary Mapper for status in DRAFT', () => {
	const mapper = getSummaryMapper({
		...DATA_MOCK,
		experiment: {
			...DATA_MOCK.experiment,
			status: 'DRAFT'
		}
	});

	it('should return formatted data', () => {
		expect(mapper.status).toEqual('draft');
		expect(mapper).toMatchSnapshot();
	});

	it.each`
		step
		${1}
		${2}
		${3}
		${4}
	`('should render step $step description', ({step}) =>
		expect(mapper.setup.steps[step - 1].Description()).toMatchSnapshot()
	);

	it('should return formatted header', () => {
		expect(mapper.header.title).toEqual('Test Is in Draft Mode');
		expect(mapper.header.Description()).toEqual(
			'Finish the setup to run the test.'
		);
	});

	it('should return 4 steps', () => {
		expect(mapper.setup.steps.length).toBe(4);
	});

	it('should return formatted setup', () => {
		expect(mapper.setup.current).toBe(3);
		expect(mapper.setup.title).toEqual('Setup');
	});

	it('should return formatted summary', () => {
		expect(mapper.summary).toEqual({
			description:
				'Lorem ipsum, dolor sit amet consectetur adipisicing elit.',
			subtitle: 'Description',
			title: 'Summary'
		});
	});

	it('should display empty state', () => {
		expect(mapper).toMatchSnapshot();
	});

	it.each`
		step | emptyText
		${1} | ${'Select a control experience and target segment for your test.'}
		${2} | ${"Choose a metric that determines your campaign's success."}
		${3} | ${'No variants created.'}
		${4} | ${'Review traffic split and run your test.'}
	`('should display empty state for step $step', ({emptyText, step}) => {
		const mapper = getSummaryMapper({
			experiment: {
				status: 'DRAFT'
			}
		});

		const {getByText} = render(mapper.setup.steps[step - 1].Description());

		expect(getByText(emptyText)).toBeTruthy();
	});
});

describe('Summary Mapper for status in RUNNING', () => {
	const mapper = getSummaryMapper({
		...DATA_MOCK,
		experiment: {
			...DATA_MOCK.experiment,
			status: 'RUNNING'
		}
	});

	it('should return formatted data for status in RUNNING', () => {
		expect(mapper.status).toEqual('running');
		expect(mapper).toMatchSnapshot();
	});

	it('should return formatted header', () => {
		expect(mapper.header.Description()).toEqual('Started: Aug 5, 2019');
		expect(mapper.header.title).toEqual('Test Is Running');
	});

	it('should return total sections', () => {
		expect(mapper.sections.length).toBe(4);
	});

	it('should return Body for section 1', () => {
		const {container, getByText} = render(mapper.sections[0].Body());

		expect(getByText('Test Completion'));
		expect(getByText('100%'));
		expect(
			container.querySelector('.analytics-summary-section-progress-bar')
		).toHaveStyle('width: 100%;');
	});

	it('should return Body for section 2', () => {
		const {getByText} = render(mapper.sections[1].Body());

		expect(getByText('Days Running'));
		expect(getByText('5'));
		expect(getByText('About 15 Days Left'));
	});

	it('should return Body for section 3', () => {
		const {getByText} = render(mapper.sections[2].Body());

		expect(getByText('Total Test Sessions'));
		expect(getByText('3K'));
	});

	it('should return Body for section 4', () => {
		const {container, getByText} = render(mapper.sections[3].Body());

		expect(getByText('Test Metric'));
		expect(getByText('Click-Through Rate'));
		expect(
			container.querySelector(
				'.analytics-summary-section-variant-status-up'
			)
		).toBeTruthy();
		expect(getByText('1% lift'));
	});
});

describe('Summary Mapper for status in FINISHED_WINNER and winner no declared', () => {
	const mapper = getSummaryMapper({
		...DATA_MOCK,
		experiment: {
			...DATA_MOCK.experiment,
			status: 'FINISHED_NO_WINNER'
		}
	});

	it('should return formatted data for status in FINISHED_NO_WINNER', () => {
		expect(mapper.status).toEqual('finished_no_winner');
		expect(mapper).toMatchSnapshot();
	});

	it('should return formatted alert', () => {
		expect(mapper.alert.description).toEqual(
			'We recommend that you use any of the test candidates, as they will perform similarly.'
		);
		expect(mapper.alert.symbol).toEqual('exclamation-circle');
		expect(mapper.alert.title).toEqual('There is no clear winner.');
	});

	it('should return formatted header', () => {
		expect(mapper.header.Description()).toEqual('Started: Aug 5, 2019');
		expect(mapper.header.title).toEqual('No Clear Winner');
	});

	it('should return total sections', () => {
		expect(mapper.sections.length).toBe(4);
	});

	it('should return Body for section 1', () => {
		const {container, getByText} = render(mapper.sections[0].Body());

		expect(getByText('Test Completion')).toBeTruthy();
		expect(getByText('100%'));
		expect(
			container.querySelector('.analytics-summary-section-progress-bar')
		).toHaveStyle('width: 100%;');
	});

	it('should return Body for section 2', () => {
		const {getByText} = render(mapper.sections[1].Body());

		expect(getByText('Days Running')).toBeTruthy();
		expect(getByText('5')).toBeTruthy();
	});

	it('should return Body for section 3', () => {
		const {getByText} = render(mapper.sections[2].Body());

		expect(getByText('Total Test Sessions')).toBeTruthy();
		expect(getByText('3K')).toBeTruthy();
	});

	it('should return Body for section 4', () => {
		const {container, getByText} = render(mapper.sections[3].Body());

		expect(getByText('Test Metric')).toBeTruthy();
		expect(getByText('Click-Through Rate')).toBeTruthy();
		expect(
			container.querySelector(
				'.analytics-summary-section-variant-status-up'
			)
		).toBeTruthy();
		expect(getByText('1% lift'));
	});
});

describe('Summary Mapper for status FINISHED_WINNER and winner declared', () => {
	const mapper = getSummaryMapper({
		...DATA_MOCK,
		experiment: {
			...DATA_MOCK.experiment,
			status: 'FINISHED_WINNER'
		}
	});

	const mapperControlWinner = getSummaryMapper({
		...DATA_MOCK,
		experiment: {
			...DATA_MOCK.experiment,
			status: 'FINISHED_WINNER',
			winnerDXPVariantId: 'DEFAULT'
		}
	});

	it('should return formatted data for status in FINISHED_WINNER', () => {
		expect(mapper.status).toEqual('finished_winner');
		expect(mapper).toMatchSnapshot();
	});

	it('should return formatted alert', () => {
		expect(mapper.alert.description).toEqual(
			'We recommend that you publish the winning variant.'
		);
		expect(mapper.alert.symbol).toEqual('check-circle');
		expect(mapper.alert.title).toEqual(
			'Variant 01 has outperformed control by at least 1%.'
		);
	});

	it('should return formatted alert with Control winner', () => {
		expect(mapperControlWinner.alert.description).toEqual(
			'We recommend that you keep control published and complete this test.'
		);
		expect(mapperControlWinner.alert.symbol).toEqual('check-circle');
		expect(mapperControlWinner.alert.title).toEqual(
			'Control has outperformed Variant 01 by at least 1%.'
		);
	});

	it('should return formatted header', () => {
		expect(mapper.header.Description()).toEqual('Started: Aug 5, 2019');
		expect(mapper.header.title).toEqual('Winner Declared');
	});

	it('should return total sections', () => {
		expect(mapper.sections.length).toBe(4);
	});

	it('should return Body for section 1', () => {
		const {container, getByText} = render(mapper.sections[0].Body());

		expect(getByText('Test Completion')).toBeTruthy();
		expect(getByText('100%'));
		expect(
			container.querySelector('.analytics-summary-section-progress-bar')
		).toHaveStyle('width: 100%;');
	});

	it('should return Body for section 2', () => {
		const {getByText} = render(mapper.sections[1].Body());

		expect(getByText('Days Running'));
		expect(getByText('5'));
	});

	it('should return Body for section 3', () => {
		const {getByText} = render(mapper.sections[2].Body());

		expect(getByText('Total Test Sessions'));
		expect(getByText('3K'));
	});

	it('should return Body for section 4', () => {
		const {container, getByText} = render(mapper.sections[3].Body());

		expect(getByText('Test Metric'));
		expect(getByText('Click-Through Rate'));
		expect(
			container.querySelector(
				'.analytics-summary-section-variant-status-up'
			)
		).toBeTruthy();
		expect(getByText('1% lift'));
	});
});

describe('Summary Mapper for status in COMPLETED', () => {
	const mapper = getSummaryMapper({
		...DATA_MOCK,
		experiment: {
			...DATA_MOCK.experiment,
			publishedDXPVariantId: 'DEFAULT',
			status: 'COMPLETED'
		}
	});

	const mapperWithWinnerVariant = getSummaryMapper({
		...DATA_MOCK,
		experiment: {
			...DATA_MOCK.experiment,
			publishedDXPVariantId: '4203992243',
			status: 'COMPLETED'
		}
	});

	it('should return formatted data', () => {
		expect(mapper.status).toEqual('completed');
		expect(mapper).toMatchSnapshot();
	});

	it('should return formatted data and complete a variant', () => {
		expect(mapperWithWinnerVariant.status).toEqual('completed');
		expect(mapperWithWinnerVariant.alert.description).toEqual(
			'Your new experience was successfully published and no more data will be collected for this test.'
		);
		expect(mapperWithWinnerVariant.alert.symbol).toEqual('check-circle');
		expect(mapperWithWinnerVariant.alert.title).toEqual(
			'Variant 01 has been published.'
		);

		expect(mapperWithWinnerVariant).toMatchSnapshot();
	});

	it('should return formatted alert', () => {
		expect(mapper.alert.description).toEqual(
			'No more data will be collected for this test.'
		);
		expect(mapper.alert.symbol).toEqual('check-circle');
		expect(mapper.alert.title).toEqual(
			'Control has been kept as the experience.'
		);
	});

	it('should return formatted header', () => {
		const {getByText} = render(mapper.header.Description());

		expect(getByText('Started: Aug 5, 2019')).toBeTruthy();
		expect(getByText('Ended: Aug 14, 2019')).toBeTruthy();
		expect(mapper.header.title).toEqual('Test Complete');
	});

	it('should return total sections', () => {
		expect(mapper.sections.length).toBe(4);
	});

	it('should return Body for section 1', () => {
		const {container, getByText} = render(mapper.sections[0].Body());

		expect(getByText('Test Completion')).toBeTruthy();
		expect(getByText('100%'));
		expect(
			container.querySelector('.analytics-summary-section-progress-bar')
		).toHaveStyle('width: 100%;');
	});

	it('should return Body for section 2', () => {
		const {getByText} = render(mapper.sections[1].Body());

		expect(getByText('Days Ran'));
		expect(getByText('5'));
	});

	it('should return Body for section 3', () => {
		const {getByText} = render(mapper.sections[2].Body());

		expect(getByText('Total Test Sessions'));
		expect(getByText('3K'));
	});

	it('should return Body for section 4', () => {
		const {getByText} = render(mapper.sections[3].Body());

		expect(getByText('Test Metric'));
		expect(getByText('Click-Through Rate'));
	});
});

describe('Summary Mapper for status in COMPLETED and a variant published', () => {
	const mapper = getSummaryMapper({
		...DATA_MOCK,
		experiment: {
			...DATA_MOCK.experiment,
			status: 'COMPLETED'
		}
	});

	it('should return formatted data', () => {
		expect(mapper.status).toEqual('completed');
		expect(mapper).toMatchSnapshot();
	});

	it('should return formatted alert', () => {
		expect(mapper.alert.description).toEqual(
			'Your new experience was successfully published and no more data will be collected for this test.'
		);
		expect(mapper.alert.symbol).toEqual('check-circle');
		expect(mapper.alert.title).toEqual('Variant 01 has been published.');
	});

	it('should return formatted header', () => {
		const {getByText} = render(mapper.header.Description());

		expect(getByText('Started: Aug 5, 2019')).toBeTruthy();
		expect(getByText('Ended: Aug 14, 2019')).toBeTruthy();
		expect(mapper.header.title).toEqual('Test Complete');
	});

	it('should return total sections', () => {
		expect(mapper.sections.length).toBe(4);
	});

	it('should return Body for section 1', () => {
		const {container, getByText} = render(mapper.sections[0].Body());

		expect(getByText('Test Completion')).toBeTruthy();
		expect(getByText('100%'));
		expect(
			container.querySelector('.analytics-summary-section-progress-bar')
		).toHaveStyle('width: 100%;');
	});

	it('should return Body for section 2', () => {
		const {getByText} = render(mapper.sections[1].Body());

		expect(getByText('Days Ran'));
		expect(getByText('5'));
	});

	it('should return Body for section 3', () => {
		const {getByText} = render(mapper.sections[2].Body());

		expect(getByText('Total Test Sessions'));
		expect(getByText('3K'));
	});

	it('should return Body for section 4', () => {
		const {getByText} = render(mapper.sections[3].Body());

		expect(getByText('Test Metric'));
		expect(getByText('Click-Through Rate'));
	});
});

describe('Summary Mapper for status in TERMINATED', () => {
	// Note: this mapper is using "Click" metric and "Variant" as a winner
	const mapper = getSummaryMapper({
		...DATA_MOCK,
		experiment: {
			...DATA_MOCK.experiment,
			goal: {metric: 'CLICK_RATE'},
			status: 'TERMINATED'
		}
	});

	const mapperBounceMetricControlWinner = getSummaryMapper({
		...DATA_MOCK,
		experiment: {
			...DATA_MOCK.experiment,
			goal: {metric: 'BOUNCE_RATE'},
			status: 'TERMINATED',
			winnerDXPVariantId: 'DEFAULT'
		}
	});

	const mapperBounceMetricVariantWinner = getSummaryMapper({
		...DATA_MOCK,
		experiment: {
			...DATA_MOCK.experiment,
			goal: {metric: 'BOUNCE_RATE'},
			status: 'TERMINATED'
		}
	});

	const mapperClickMetricControlWinner = getSummaryMapper({
		...DATA_MOCK,
		experiment: {
			...DATA_MOCK.experiment,
			goal: {metric: 'CLICK_RATE'},
			status: 'TERMINATED',
			winnerDXPVariantId: 'DEFAULT'
		}
	});

	const mapperNoWinnerBounceMetric = getSummaryMapper({
		...DATA_MOCK,
		experiment: {
			...DATA_MOCK.experiment,
			goal: {metric: 'BOUNCE_RATE'},
			status: 'TERMINATED',
			winnerDXPVariantId: null
		}
	});

	const mapperNoWinnerClickMetric = getSummaryMapper({
		...DATA_MOCK,
		experiment: {
			...DATA_MOCK.experiment,
			goal: {metric: 'CLICK_RATE'},
			status: 'TERMINATED',
			winnerDXPVariantId: null
		}
	});

	it('should return formatted data for status in TERMINATED', () => {
		expect(mapper.status).toEqual('terminated');
		expect(mapper).toMatchSnapshot();
	});

	it('should return status TERMINATED with a WINNER and using CLICK metric', () => {
		const {getByText} = render(mapper.sections[3].Body());

		expect(mapper.status).toEqual('terminated');

		expect(mapper.alert.title).toEqual(
			'Variant 01 has outperformed Control by at least 1.00%.'
		);

		expect(getByText('Test Metric'));
		expect(getByText('Click-Through Rate'));
	});

	it('should return status TERMINATED with a NO WINNER and using CLICK metric', () => {
		const {getByText} = render(mapper.sections[3].Body());

		expect(mapperNoWinnerClickMetric.status).toEqual('terminated');

		expect(mapperNoWinnerClickMetric.alert.title).toEqual(
			'There is no clear winner.'
		);

		expect(getByText('Test Metric'));
		expect(getByText('Click-Through Rate'));
	});

	it('should return status TERMINATED with a WINNER and using BOUNCE metric', () => {
		const {getByText} = render(
			mapperBounceMetricVariantWinner.sections[3].Body()
		);

		expect(mapperBounceMetricVariantWinner.status).toEqual('terminated');

		expect(mapperBounceMetricVariantWinner.alert.title).toEqual(
			'Variant 01 has outperformed Control by at least 1.00%.'
		);

		expect(getByText('Test Metric'));
		expect(getByText('Bounce Rate'));
	});

	it('should return status TERMINATED with CONTROL as WINNER and using BOUNCE metric', () => {
		const {getByText} = render(
			mapperBounceMetricControlWinner.sections[3].Body()
		);

		expect(mapperBounceMetricControlWinner.status).toEqual('terminated');

		expect(mapperBounceMetricControlWinner.alert.title).toEqual(
			'Control has outperformed Variant 01 by at least 1.00%.'
		);

		expect(getByText('Test Metric'));
		expect(getByText('Bounce Rate'));
	});

	it('should return status TERMINATED with CONTROL as WINNER and using CLICK metric', () => {
		const {getByText} = render(
			mapperClickMetricControlWinner.sections[3].Body()
		);

		expect(mapperClickMetricControlWinner.status).toEqual('terminated');

		expect(mapperClickMetricControlWinner.alert.title).toEqual(
			'Control has outperformed Variant 01 by at least 1.00%.'
		);

		expect(getByText('Test Metric'));
		expect(getByText('Click-Through Rate'));
	});

	it('should return status TERMINATED with a NO WINNER and using BOUNCE metric', () => {
		const {getByText} = render(
			mapperNoWinnerBounceMetric.sections[3].Body()
		);

		expect(mapperNoWinnerBounceMetric.status).toEqual('terminated');

		expect(mapperNoWinnerBounceMetric.alert.title).toEqual(
			'There is no clear winner.'
		);

		expect(getByText('Test Metric'));
		expect(getByText('Bounce Rate'));
	});

	it('should return formatted header', () => {
		const {getByText} = render(mapper.header.Description());

		expect(getByText('Started: Aug 5, 2019')).toBeTruthy();
		expect(getByText('Stopped: Aug 14, 2019')).toBeTruthy();
		expect(mapper.header.title).toEqual('Test Was Terminated');
	});

	it('should return total sections', () => {
		expect(mapper.sections.length).toBe(4);
	});

	it('should return Body for section 1', () => {
		const {container, getByText} = render(mapper.sections[0].Body());

		expect(getByText('Test Completion')).toBeTruthy();
		expect(getByText('100%'));
		expect(
			container.querySelector('.analytics-summary-section-progress-bar')
		).toHaveStyle('width: 100%;');
	});

	it('should return Body for section 2', () => {
		const {getByText} = render(mapper.sections[1].Body());

		expect(getByText('Days Running'));
		expect(getByText('5'));
	});

	it('should return Body for section 3', () => {
		const {getByText} = render(mapper.sections[2].Body());

		expect(getByText('Total Test Sessions'));
		expect(getByText('3K'));
	});

	it('should return Body for section 4', () => {
		const {container, getByText} = render(mapper.sections[3].Body());

		expect(getByText('Test Metric'));
		expect(getByText('Click-Through Rate'));
		expect(
			container.querySelector(
				'.analytics-summary-section-variant-status-up'
			)
		).toBeTruthy();
		expect(getByText('1% lift'));
	});
});
