import CriteriaBuilder from '../index';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {wrapInTestContext} from 'react-dnd-test-utils';

jest.unmock('react-dom');

describe('CriteriaBuilder', () => {
	afterEach(cleanup);

	it('should render', () => {
		const CriteriaBuilderContext = wrapInTestContext(CriteriaBuilder);

		const {container} = render(<CriteriaBuilderContext />);

		expect(container).toMatchSnapshot();
	});
});
