import Promise from 'metal-promise';
import React from 'react';
import withQuery from '../WithQuery';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

const mockData = {test: 'pass'};
const mockFailData = {test: 'fail'};

const request = jest.fn(() => Promise.resolve(mockData));
const rejectRequest = jest.fn(() => Promise.reject(mockFailData));

describe('WithQuery', () => {
	afterEach(cleanup);
	it('should pass result props to the wrapped component', () => {
		const WrappedComponent = withQuery(
			request,
			val => val
		)(({data}) => <div>{data && data.test}</div>);

		const {queryByText} = render(<WrappedComponent />);

		jest.runAllTimers();

		expect(queryByText('pass')).toBeTruthy();
	});

	it('should return an error', () => {
		const WrappedComponent = withQuery(
			rejectRequest,
			val => val
		)(({error}) => <div>{error && 'error'}</div>);

		const {queryByText} = render(<WrappedComponent />);

		jest.runAllTimers();

		expect(queryByText('error')).toBeTruthy();
	});

	it('should return the result mapped to props', () => {
		const WrappedComponent = withQuery(
			request,
			val => val,
			({data}) => ({fooProp: data})
		)(({fooProp}) => <div>{fooProp && fooProp.test}</div>);

		const {queryByText} = render(<WrappedComponent />);

		jest.runAllTimers();

		expect(queryByText('pass')).toBeTruthy();
	});
});
