import DropZone from '../DropZone';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {wrapInTestContext} from 'react-dnd-test-utils';

jest.unmock('react-dom');

describe('DropZone', () => {
	afterEach(cleanup);

	it('should render', () => {
		const DropZoneContext = wrapInTestContext(DropZone);

		const {container} = render(<DropZoneContext />);

		expect(container).toMatchSnapshot();
	});
});
