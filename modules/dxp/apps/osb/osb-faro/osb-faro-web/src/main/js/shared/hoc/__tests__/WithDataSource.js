jest.mock('shared/hoc/WithAction', () => () => wrappedComponent =>
	wrappedComponent
);

import withDataSource from '../WithDataSource';
import {renderWithStore} from 'test/mock-store';

describe('WithDataSource', () => {
	it('should pass dataSource to the WrappedComponent', () => {
		const MockComponent = jest.fn();
		const WrappedComponent = withDataSource(MockComponent);

		const component = renderWithStore(WrappedComponent, {
			dataSource: 'fooDataSource',
			id: 'test'
		});

		expect(component.find(MockComponent).prop('dataSource')).toBe(
			'fooDataSource'
		);
	});
});
