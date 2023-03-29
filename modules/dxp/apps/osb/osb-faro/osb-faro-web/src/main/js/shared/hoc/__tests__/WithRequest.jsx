jest.mock('shared/hoc/WithQuery');

import Promise from 'metal-promise';
import React from 'react';
import withQuery from 'shared/hoc/WithQuery';
import withRequest from '../WithRequest';
import {shallow} from 'enzyme';

const mockParams = {
	data: [{foo: 'bar'}],
	error: false,
	loading: false,
	refetch: jest.fn()
};

describe('withRequest', () => {
	beforeEach(() => {
		jest.resetAllMocks();
	});

	const request = jest.fn(() => Promise.resolve({test: 'pass'}));

	it('should return a new Component', () => {
		withQuery.mockImplementationOnce(() => Component => () => (
			<Component />
		));

		const WrappedComponent = withRequest(request)(jest.fn());

		expect(WrappedComponent).toBeInstanceOf(Function);
	});

	it('should render the wrapped component', () => {
		withQuery.mockImplementationOnce(() => Component => () => (
			<Component {...mockParams} />
		));

		const WrappedComponent = withRequest(request)(() => <div>{'foo'}</div>);

		const component = shallow(<WrappedComponent />).render();

		expect(component.text()).toBe('foo');
	});

	it('should return the result mapped to props', () => {
		withQuery.mockImplementationOnce(() => Component => () => (
			<Component {...mockParams} />
		));

		const WrappedComponent = withRequest(request, val => ({
			foo: val
		}))(() => <div>{'foo'}</div>);

		const component = shallow(<WrappedComponent groupId='23' />);

		const props = component.dive().dive().dive().shallow().props();

		const hasFooProperty = Object.prototype.hasOwnProperty.call(
			props,
			'foo'
		);

		expect(hasFooProperty).toBe(true);
	});
});
