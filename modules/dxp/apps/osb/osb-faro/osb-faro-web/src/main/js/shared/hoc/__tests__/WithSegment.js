jest.mock('shared/hoc/WithAction', () => () => wrappedComponent =>
	wrappedComponent
);

import withSegment from '../WithSegment';
import {renderWithStore} from 'test/mock-store';
import {Segment} from 'shared/util/records';

describe('WithSegment', () => {
	it('should pass the segment to the WrappedComponent', () => {
		const MockComponent = jest.fn();
		const WrappedComponent = withSegment(MockComponent);

		const component = renderWithStore(WrappedComponent, {
			id: 'test',
			segment: new Segment()
		});

		expect(component.find(MockComponent).prop('segment')).toBeInstanceOf(
			Segment
		);
	});
});
