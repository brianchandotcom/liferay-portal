import mockStore from 'test/mock-store';
import React from 'react';
import TimeZoneAlert from '../TimeZoneAlert';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

describe('TimeZoneAlert', () => {
	it('should render', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<TimeZoneAlert groupId='23' onClose={jest.fn()} />
				</StaticRouter>
			</Provider>
		);
		expect(container).toMatchSnapshot();
	});
});
