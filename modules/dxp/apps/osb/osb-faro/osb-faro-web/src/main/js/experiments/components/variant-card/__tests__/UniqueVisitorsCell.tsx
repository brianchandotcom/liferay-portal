import React from 'react';
import UniqueVisitorsCell from '../UniqueVisitorsCell';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Unique Visitors Cell', () => {
	it('should render', () => {
		const {container, queryByText} = render(
			<UniqueVisitorsCell trafficSplit={50} uniqueVisitors={123} />
		);

		expect(queryByText('50% Traffic Split')).toBeTruthy();
		expect(queryByText('123')).toBeTruthy();

		expect(container).toMatchSnapshot();
	});
});
