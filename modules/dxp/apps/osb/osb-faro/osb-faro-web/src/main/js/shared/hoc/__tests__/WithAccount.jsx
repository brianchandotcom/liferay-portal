import withAccount from '../WithAccount';
import {renderWithStore} from 'test/mock-store';

jest.unmock('react-dom');

jest.mock('shared/hoc/WithAction', () => () => wrappedComponent =>
	wrappedComponent
);

describe('WithAccount', () => {
	it('should pass the Account to the WrappedComponent', () => {
		let result = null;

		const MockComponent = props => {
			result = props;

			return null;
		};

		const WrappedComponent = withAccount(MockComponent);

		renderWithStore(WrappedComponent, {
			account: 'fooAccount',
			id: 'test'
		});

		expect(result.account).toBe('fooAccount');
	});
});
