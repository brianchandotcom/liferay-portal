import React from 'react';
import ReactDOM from 'react-dom';
import TypeTrendCard from '../TypeTrendCard';
import {cleanup, render} from '@testing-library/react';
import {MockedProvider} from '@apollo/react-testing';
import {mockIndividualMetricsReq} from 'test/graphql-data';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '123123'
	})
}));

ReactDOM.createPortal = jest.fn();

describe('TypeTrendCard', () => {
	afterEach(cleanup);

	xit('should render', () => {
		const {container} = render(
			<MockedProvider mocks={[mockIndividualMetricsReq()]}>
				<TypeTrendCard channelId='123123' />
			</MockedProvider>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
