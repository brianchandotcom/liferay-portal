import React from 'react';
import withQuery from '../WithQuery';
import {cleanup, render} from '@testing-library/react';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

const mockData = {test: 'pass'};
const mockFailData = {test: 'fail'};

const request = jest.fn(() => Promise.resolve(mockData));
const rejectRequest = jest.fn(() => Promise.reject(mockFailData));

describe('WithQuery', () => {
	afterEach(cleanup);
	it('should pass result props to the wrapped component', async () => {
		const WrappedComponent = withQuery(
			request,
			val => val
		)(({data}) => <div>{data && data.test}</div>);

		const {container, queryByText} = render(<WrappedComponent />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(queryByText('pass')).toBeTruthy();
	});

	it('should return an error', async () => {
		const WrappedComponent = withQuery(
			rejectRequest,
			val => val
		)(({error}) => <div>{error && 'error'}</div>);

		const {container, queryByText} = render(<WrappedComponent />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(queryByText('error')).toBeTruthy();
	});

	it('should return the result mapped to props', async () => {
		const WrappedComponent = withQuery(
			request,
			val => val,
			({data}) => ({fooProp: data})
		)(({fooProp}) => <div>{fooProp && fooProp.test}</div>);

		const {container, queryByText} = render(<WrappedComponent />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(queryByText('pass')).toBeTruthy();
	});
});
