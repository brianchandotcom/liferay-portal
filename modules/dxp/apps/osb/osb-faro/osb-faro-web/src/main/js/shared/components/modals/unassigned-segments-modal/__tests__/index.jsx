import React from 'react';
import UnassignedSegmentsModal from '../index';
import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';

jest.unmock('react-dom');

describe('UnassignedSegmentsModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<UnassignedSegmentsModal onClose={noop} />);

		expect(container).toMatchSnapshot();
	});
});
