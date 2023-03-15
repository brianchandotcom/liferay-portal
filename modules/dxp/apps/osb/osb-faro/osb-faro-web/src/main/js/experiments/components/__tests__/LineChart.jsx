import LineChart from '../LineChart';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {range} from 'lodash';

jest.unmock('react-dom');

const createData = value => ({
	color: `#${value}`,
	data: [{key: new Date().toISOString(), value}],
	name: `name${value}`
});

const createDate = () => new Date().toISOString();

describe('LineChart', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<LineChart
				data={range(2).map(createData)}
				intervals={range(2).map(createDate)}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should get short intervals', () => {
		const {container} = render(
			<LineChart
				data={range(2).map(createData)}
				intervals={range(15).map(createData)}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
