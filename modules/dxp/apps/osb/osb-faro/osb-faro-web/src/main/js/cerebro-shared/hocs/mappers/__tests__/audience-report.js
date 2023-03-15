import {CHART_COLOR_NAMES} from 'shared/components/Chart';
import {getAudienceReportMapper} from '../audience-report';

const {martellD4, martellL4, mormont, stark} = CHART_COLOR_NAMES;

const data = {
	form: {
		submissionsMetric: {
			anonymousUsersCount: 2000,
			knownUsersCount: 3450,
			nonsegmentedKnownUsersCount: 100,
			segment: {
				metrics: [
					{
						value: 1000,
						valueKey: 'Segment 1'
					},
					{
						value: 1500,
						valueKey: 'Segment 2'
					},
					{
						value: 2000,
						valueKey: 'Segment 3'
					},
					{
						value: 150,
						valueKey: 'Segment 4'
					},
					{
						value: 450,
						valueKey: 'Segment 5'
					},
					{
						value: 3500,
						valueKey: 'Segment 6'
					}
				],
				total: 6
			},
			segmentedAnonymousUsersCount: 100,
			segmentedKnownUsersCount: 3500
		}
	}
};

const segmentValuesPercentages = [97, 55, 41, 27, 12, 4];

describe('Shared HOCs Mappers - Segments', () => {
	it('should map segments information', () => {
		const mapper = getAudienceReportMapper(
			result => result.form.submissionsMetric
		);

		expect(mapper.props({data}).segments).toMatchSnapshot();
	});

	it('should return an array with ordered segments', () => {
		const mapper = getAudienceReportMapper(
			result => result.form.submissionsMetric
		);

		const props = mapper.props({data});

		const arr = props.segments.items.map(({progress}) => progress[0].value);

		expect(arr).toEqual(segmentValuesPercentages);
	});

	it('should return an array with only 6 segments when there are more than 6 segments', () => {
		const mapper = getAudienceReportMapper(
			result => result.form.submissionsMetric
		);
		const props = mapper.props({
			data: {
				form: {
					submissionsMetric: {
						...data.form.submissionsMetric,
						segment: {
							...data.form.submissionsMetric.segment,
							metrics: [
								...data.form.submissionsMetric.segment.metrics,
								{
									value: 1200,
									valueKey: 'Segment 7'
								}
							].sort((a, b) => b.value - a.value),
							total: 7
						}
					}
				}
			}
		});

		const arr = props.segments.items;
		const label = arr[arr.length - 1].columns[0].label;

		expect(arr.length).toEqual(6);
		expect(label).toEqual('2 More Segments');
	});

	it('if there are 6 segments, the last segment should display a tooltip with the information of the sixth segment', () => {
		const mapper = getAudienceReportMapper(
			result => result.form.submissionsMetric
		);
		const props = mapper.props({data});
		const arr = props.segments.items;
		const tooltip = arr[arr.length - 1].tooltip;

		expect(tooltip.rows.length).toEqual(1);
		expect(tooltip).toMatchSnapshot();
	});

	it('if there are 7 segments, the last segment should display 2 rows in tooltip', () => {
		const mapper = getAudienceReportMapper(
			result => result.form.submissionsMetric
		);
		const props = mapper.props({
			data: {
				form: {
					submissionsMetric: {
						...data.form.submissionsMetric,
						segment: {
							...data.form.submissionsMetric.segment,
							metrics: [
								...data.form.submissionsMetric.segment.metrics,
								{
									value: 1200,
									valueKey: 'Segment 7'
								}
							].sort((a, b) => b.value - a.value),
							total: 7
						}
					}
				}
			}
		});
		const arr = props.segments.items;
		const label = arr[arr.length - 1].columns[0].label;
		const tooltip = arr[arr.length - 1].tooltip;

		expect(tooltip.rows.length).toEqual(2);
		expect(label).toEqual('2 More Segments');
		expect(tooltip).toMatchSnapshot();
	});

	it('if there are 15 segments, the last segment should display 10 rows in tooltip', () => {
		const segment = {
			...data.form.submissionsMetric.segment,
			metrics: [
				...data.form.submissionsMetric.segment.metrics,
				{
					value: 600,
					valueKey: 'Segment 7'
				},
				{
					value: 330,
					valueKey: 'Segment 8'
				},
				{
					value: 240,
					valueKey: 'Segment 9'
				},
				{
					value: 123,
					valueKey: 'Segment 10'
				},
				{
					value: 343,
					valueKey: 'Segment 11'
				},
				{
					value: 523,
					valueKey: 'Segment 12'
				},
				{
					value: 5,
					valueKey: 'Segment 13'
				},
				{
					value: 345,
					valueKey: 'Segment 14'
				},
				{
					value: 5,
					valueKey: 'others'
				}
			].sort((a, b) => b.value - a.value),
			total: 14
		};

		const mapper = getAudienceReportMapper(
			result => result.form.submissionsMetric
		);
		const props = mapper.props({
			data: {
				form: {
					submissionsMetric: {
						...data.form.submissionsMetric,
						segment
					}
				}
			}
		});
		const arr = props.segments.items;
		const label = arr[arr.length - 1].columns[0].label;
		const tooltip = arr[arr.length - 1].tooltip;

		expect(tooltip.rows.length).toEqual(10);
		expect(label).toEqual('9 More Segments');
		expect(tooltip).toMatchSnapshot();
	});
});

describe('Shared HOCs Mappers - knownIndividuals', () => {
	it('should map knownIndividuals information', () => {
		const mapper = getAudienceReportMapper(
			result => result.form.submissionsMetric
		);

		expect(mapper.props({data}).knownIndividuals).toMatchSnapshot();
	});

	it('should return martellL4 & martellD4 for segmented & unsegmented respectively', () => {
		const mapper = getAudienceReportMapper(
			result => result.form.submissionsMetric
		);
		const props = mapper.props({data}).knownIndividuals;

		expect(props.data[0].color).toEqual(martellL4);
		expect(props.data[0].label).toEqual(Liferay.Language.get('segmented'));
		expect(props.data[1].color).toEqual(martellD4);
		expect(props.data[1].label).toEqual(
			Liferay.Language.get('unsegmented')
		);
		expect(props.data).toMatchSnapshot();
	});
});

describe('Shared HOCs Mappers - uniqueVisitors', () => {
	it('should map uniqueVisitors information', () => {
		const mapper = getAudienceReportMapper(
			result => result.form.submissionsMetric
		);

		expect(mapper.props({data}).uniqueVisitors).toMatchSnapshot();
	});

	it('should return mormont & stark for anonymousIndividuals & knownIndiviudals respectively', () => {
		const mapper = getAudienceReportMapper(
			result => result.form.submissionsMetric
		);
		const props = mapper.props({data}).uniqueVisitors;

		expect(props.data[0].color).toEqual(mormont);
		expect(props.data[0].label).toEqual(
			Liferay.Language.get('anonymous-individuals')
		);
		expect(props.data[1].color).toEqual(stark);
		expect(props.data[1].label).toEqual(
			Liferay.Language.get('known-individuals')
		);
		expect(props.data).toMatchSnapshot();
	});
});
