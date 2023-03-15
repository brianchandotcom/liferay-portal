import * as data from 'test/data';
import BaseDetails from '../BaseDetails';
import mockStore from 'test/mock-store';
import Promise from 'metal-promise';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<StaticRouter>
		<Provider store={mockStore()}>
			<BaseDetails
				dataSourceFn={() => Promise.resolve(data.mockAccountDetails())}
				groupId='23'
				id='test'
				{...props}
			/>
		</Provider>
	</StaticRouter>
);

describe('BaseDetails', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render w/o loading', () => {
		const {container} = render(<DefaultComponent />);

		expect(container.querySelector('.loading-animation')).toBeTruthy();
	});

	it('should render w/ ErrorDisplay', () => {
		const {queryByText} = render(
			<DefaultComponent dataSourceFn={() => Promise.reject({})} />
		);

		jest.runAllTimers();

		expect(queryByText('Reload')).toBeTruthy();
	});
});
