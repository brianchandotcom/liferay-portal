// import * as Redux from 'react-redux';
import React from 'react';
import withPolling from '../WithPolling';
import {render} from '@testing-library/react';
import {renderWithStore} from 'test/mock-store';
import {shallow} from 'enzyme';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.mock('react-redux', () => ({
	...jest.requireActual('react-redux'),
	connect: jest.fn(() => wrappedComponent => wrappedComponent)
}));

// Redux.connect = jest.fn(() => wrappedComponent => wrappedComponent);

class TestComponent extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				{'component body'}
			</div>
		);
	}
}

const mockData = {foo: 'bar'};

const mockRequest = jest.fn(() => Promise.resolve(mockData));

describe('WithPolling', () => {
	afterEach(() => {
		mockRequest.mockClear();
	});

	it('should render the WrappedComponent', () => {
		const WrappedComponent = withPolling(mockRequest)(TestComponent);

		const component = renderWithStore(WrappedComponent, {groupId: '23'});

		expect(component.render()).toMatchSnapshot();
	});

	it('should pass the data to the WrappedComponent', async () => {
		const WrappedComponent = withPolling(mockRequest)(TestComponent);

		const component = shallow(<WrappedComponent />);

		const {container} = render(<WrappedComponent />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(component.prop('data')).toMatchSnapshot();
	});

	it('should stop polling once the stopCondition has been fulfilled', async () => {
		mockRequest.mockReturnValueOnce(Promise.resolve(mockData));
		mockRequest.mockReturnValueOnce(Promise.resolve({foo: 'notBar'}));

		const mockStopCondition = ({foo}) => foo !== 'bar';

		const WrappedComponent = withPolling(
			mockRequest,
			mockStopCondition
		)(TestComponent);

		shallow(<WrappedComponent />);

		const {container} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(mockRequest.mock.calls.length).toBe(2);
	});

	it('should receive the prop "foo" from its parent', async () => {
		const foo = 'bar';

		const request = response => Promise.resolve(response.foo);

		const WrappedComponent = withPolling(request, undefined, {
			propName: 'test',
			requestProps: ['foo']
		})(TestComponent);

		const component = shallow(<WrappedComponent foo={foo} />);

		const {container} = render(<WrappedComponent />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(component.prop('test')).toBe(foo);
	});
});
