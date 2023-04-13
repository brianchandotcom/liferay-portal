jest.mock('shared/hoc/WithAction', () => () => wrappedComponent =>
	wrappedComponent
);

import withCurrentUser from '../WithCurrentUser';
import {renderWithStore} from 'test/mock-store';

describe('WithCurrentUser', () => {
	it('should pass the currentUser to the WrappedComponent', () => {
		const MockComponent = jest.fn();
		const WrappedComponent = withCurrentUser(MockComponent);

		const component = renderWithStore(WrappedComponent, {
			currentUser: 'fooUser',
			id: 'test'
		});

		expect(component.find(MockComponent).prop('currentUser')).toBe(
			'fooUser'
		);
	});
});
