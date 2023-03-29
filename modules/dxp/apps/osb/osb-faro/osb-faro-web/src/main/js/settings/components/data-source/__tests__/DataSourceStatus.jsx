import DataSourceStatus from '../DataSourceStatus';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('DataSourceStatus', () => {
	it('should render', () => {
		const {container} = render(
			<DataSourceStatus display='info' label='foo' message='bar' />
		);

		expect(container).toMatchSnapshot();
	});
});
