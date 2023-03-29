import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import TimeZoneSelectionModal from '../TimeZoneSelectionModal';
import {mockGetDateNow} from 'test/mock-date';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

describe('TimeZoneSelectionModal', () => {
	mockGetDateNow(data.getTimestamp(0));

	it('should render', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<TimeZoneSelectionModal groupId={23} />
				</StaticRouter>
			</Provider>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
