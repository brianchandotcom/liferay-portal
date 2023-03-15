import ImprovementCell from '../ImprovementCell';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Unique Visitors Cell', () => {
	it('should render', () => {
		const {container} = render(<ImprovementCell improvement={10.12345} />);

		expect(container).toMatchSnapshot();
	});

	it('should render negative improvements', () => {
		const {container} = render(<ImprovementCell improvement={-10.12345} />);

		expect(container).toMatchSnapshot();
	});
});
