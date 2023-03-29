import React from 'react';
import withAction from '../WithAction';
import {compose} from 'redux';
import {RemoteData} from '../../util/records';
import {renderWithStore} from 'test/mock-store';
import {withStaticRouter} from 'test/mock-router';

describe('withAction', () => {
	const action = () => ({type: 'NO_OP'});
	const mapStateToRemoteData = () => new RemoteData({loading: false});

	it('should return a new Component', () => {
		const WrappedComponent = withAction(
			action,
			mapStateToRemoteData
		)(jest.fn());

		expect(WrappedComponent).toBeInstanceOf(Function);
	});

	it('should render the wrapped component', () => {
		const WrappedComponent = withAction(
			action,
			mapStateToRemoteData
		)(() => <div>{'foo'}</div>);

		const component = renderWithStore(WrappedComponent);

		expect(component.render()).toMatchSnapshot();
	});

	it('should render loading if the RemoteData is loading and data is null', () => {
		const WrappedComponent = withAction(
			action,
			() => new RemoteData()
		)(jest.fn());

		const component = renderWithStore(WrappedComponent);

		expect(component.render()).toMatchSnapshot();
	});

	it('should render error if the RemoteData has error', () => {
		const WrappedComponent = compose(
			withStaticRouter,
			withAction(action, () => new RemoteData({error: true}))
		)(jest.fn());

		const component = renderWithStore(WrappedComponent);

		expect(component.render()).toMatchSnapshot();
	});

	it('should render a custom error message', () => {
		const WrappedComponent = compose(
			withStaticRouter,
			withAction(action, () => new RemoteData({error: true}), {
				errorPageProps: {
					message: 'my fancy message, oh so fancy'
				}
			})
		)(jest.fn());

		const component = renderWithStore(WrappedComponent);

		expect(component.render()).toMatchSnapshot();
	});

	it('should render the wrapped component if bypassErrorPage is true even if the RemoteData has an error', () => {
		const WrappedComponent = compose(
			withStaticRouter,
			withAction(
				action,
				() => new RemoteData({data: {test: 'test'}, error: true}),
				{bypassErrorPage: true}
			)
		)(() => <div>{'foo'}</div>);

		const component = renderWithStore(WrappedComponent);

		expect(component.render()).toMatchSnapshot();
	});
});
