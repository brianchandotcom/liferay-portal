jest.mock('shared/hoc/WithAction', () => () => wrappedComponent =>
	wrappedComponent
);

import withAccount from '../WithAccount';
import {renderWithStore} from 'test/mock-store';

describe('WithAccount', () => {
	it('should pass the Account to the WrappedComponent', () => {
		const MockComponent = jest.fn();
		const WrappedComponent = withAccount(MockComponent);

		const component = renderWithStore(WrappedComponent, {
			account: 'fooAccount',
			id: 'test'
		});

		expect(component.find(MockComponent).prop('account')).toBe(
			'fooAccount'
		);
	});
});
