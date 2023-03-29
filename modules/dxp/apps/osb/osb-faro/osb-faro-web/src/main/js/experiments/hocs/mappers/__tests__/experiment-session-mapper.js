import getSessionMapper from '../experiment-session-mapper';
import {shallow} from 'enzyme';

const DATA_MOCK = {
	experiment: {
		sessionsHistogram: [
			{
				key: '2018-05-17T00:00',
				value: 2884,
				valueKey: '2018-05-17T00:00'
			},
			{
				key: '2018-05-18T00:00',
				value: 2967,
				valueKey: '2018-05-18T00:00'
			},
			{
				key: '2018-05-19T00:00',
				value: 1981,
				valueKey: '2018-05-19T00:00'
			},
			{
				key: '2018-05-20T00:00',
				value: 1502,
				valueKey: '2018-05-20T00:00'
			},
			{
				key: '2018-05-21T00:00',
				value: 1253,
				valueKey: '2018-05-21T00:00'
			},
			{
				key: '2018-05-22T00:00',
				value: 3944,
				valueKey: '2018-05-22T00:00'
			}
		]
	}
};

describe('Experiment Session Mapper', () => {
	const mapper = getSessionMapper(DATA_MOCK);

	it('should return formatted data', () => {
		expect(mapper).toMatchSnapshot();
	});

	it('should group values from data', () => {
		expect(mapper.data[0].data).toEqual([
			{
				key: '2018-05-17T00:00',
				value: 2884,
				valueKey: '2018-05-17T00:00'
			},
			{
				key: '2018-05-18T00:00',
				value: 2967,
				valueKey: '2018-05-18T00:00'
			},
			{
				key: '2018-05-19T00:00',
				value: 1981,
				valueKey: '2018-05-19T00:00'
			},
			{
				key: '2018-05-20T00:00',
				value: 1502,
				valueKey: '2018-05-20T00:00'
			},
			{
				key: '2018-05-21T00:00',
				value: 1253,
				valueKey: '2018-05-21T00:00'
			},
			{
				key: '2018-05-22T00:00',
				value: 3944,
				valueKey: '2018-05-22T00:00'
			}
		]);
	});

	it('should format number', () => {
		expect(mapper.format(1000)).toEqual('1K');
	});

	it('should get intervals', () => {
		expect(mapper.intervals).toEqual([
			'2018-05-17T00:00',
			'2018-05-18T00:00',
			'2018-05-19T00:00',
			'2018-05-20T00:00',
			'2018-05-21T00:00',
			'2018-05-22T00:00'
		]);
	});

	it('should render Tooltip', () => {
		const Tooltip = shallow(
			mapper.Tooltip({
				dataPoint: [
					{
						color: '#6B6C7E',
						name: 'data1',
						payload: {
							key: '2018-05-17T00:00',
							value: 1500
						}
					}
				]
			})
		);
		expect(Tooltip.render()).toMatchSnapshot();
	});

	it('should return empty true when sessionHistogram to be null', () => {
		expect(
			getSessionMapper({experiment: {sessionsHistogram: null}})
		).toEqual({empty: true});
	});

	it('should return empty true when sessionHistogram to be an empty array', () => {
		expect(
			getSessionMapper({experiment: {sessionsHistogram: []}})
		).toEqual({empty: true});
	});
});
