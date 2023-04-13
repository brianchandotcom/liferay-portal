jest.mock('shared/hoc/WithAction', () => () => wrappedComponent =>
	wrappedComponent
);

import withProject from '../WithProject';
import {renderWithStore} from 'test/mock-store';

describe('WithProject', () => {
	it('should pass the project to the WrappedComponent', () => {
		const MockComponent = jest.fn();
		const WrappedComponent = withProject(MockComponent);

		const component = renderWithStore(WrappedComponent, {
			id: 'test',
			project: 'fooProject'
		});

		expect(component.find(MockComponent).prop('project')).toBe(
			'fooProject'
		);
	});
});
