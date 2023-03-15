import mockStore from 'test/mock-store';
import React from 'react';
import ReactDOM from 'react-dom';
import {cleanup, render} from '@testing-library/react';
import {mockUser} from 'test/data';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {TrackedBehaviors} from '../TrackedBehaviors';
import {User} from 'shared/util/records';

jest.unmock('react-dom');

ReactDOM.createPortal = jest.fn();

describe('TrackedBehaviorsList', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<TrackedBehaviors
						currentUser={new User(mockUser())}
						groupId='23'
					/>
				</StaticRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
