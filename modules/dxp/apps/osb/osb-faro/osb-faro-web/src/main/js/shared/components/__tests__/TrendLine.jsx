import React from 'react';
import TrendLine from '../TrendLine';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('TrendLine', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<TrendLine data={[0, 1, 3, 2, 5, 3, 4, 4, 5, 6, 7]} />
		);
		expect(container).toBeTruthy();
	});
});
