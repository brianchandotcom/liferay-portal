import CriteriaGroup from '../CriteriaGroup';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {wrapInTestContext} from 'react-dnd-test-utils';

jest.unmock('react-dom');

describe('CriteriaGroup', () => {
	afterEach(cleanup);

	it('should render', () => {
		const CriteriaGroupContext = wrapInTestContext(CriteriaGroup);

		const {container} = render(<CriteriaGroupContext />);

		expect(container).toMatchSnapshot();
	});
});
