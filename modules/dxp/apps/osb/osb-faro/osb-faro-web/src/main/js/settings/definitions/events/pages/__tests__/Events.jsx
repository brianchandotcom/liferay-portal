import Events from '../Events';
import mockStore from 'test/mock-store';
import React from 'react';
import {MemoryRouter} from 'react-router-dom';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		groupId: '23'
	})
}));

describe('Events', () => {
	it('should render', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<MemoryRouter>
					<Events groupId='23' />
				</MemoryRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
