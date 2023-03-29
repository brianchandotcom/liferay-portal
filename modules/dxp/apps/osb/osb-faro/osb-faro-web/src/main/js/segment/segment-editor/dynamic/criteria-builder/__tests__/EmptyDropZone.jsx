import EmptyDropZone from '../EmptyDropZone';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {wrapInTestContext} from 'react-dnd-test-utils';

jest.unmock('react-dom');

describe('EmptyDropZone', () => {
	afterEach(cleanup);

	it('should render', () => {
		const EmptyDropZoneContext = wrapInTestContext(EmptyDropZone);

		const {container} = render(<EmptyDropZoneContext />);

		expect(container).toMatchSnapshot();
	});
});
