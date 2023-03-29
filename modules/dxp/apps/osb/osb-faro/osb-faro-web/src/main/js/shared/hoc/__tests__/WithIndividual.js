jest.mock('shared/hoc/WithAction', () => () => wrappedComponent =>
	wrappedComponent
);

import withIndividual from '../WithIndividual';
import {renderWithStore} from 'test/mock-store';

describe('WithIndividual', () => {
	it('should pass the individual to the WrappedComponent', () => {
		const MockComponent = jest.fn();
		const WrappedComponent = withIndividual(MockComponent);

		const component = renderWithStore(WrappedComponent, {
			id: 'test',
			individual: 'fooIndividual'
		});

		expect(component.find(MockComponent).prop('individual')).toBe(
			'fooIndividual'
		);
	});
});
