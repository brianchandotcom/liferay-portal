import OccurenceConjunctionInput from '../OccurenceConjunctionInput';
import React from 'react';
import {RelationalOperators} from '../../../utils/constants';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('OccurenceConjunctionInput', () => {
	it('should render', () => {
		const {container} = render(
			<OccurenceConjunctionInput
				onChange={jest.fn()}
				operatorName={RelationalOperators.LT}
				touched={false}
				valid
				value={123}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render with error', () => {
		const {container} = render(
			<OccurenceConjunctionInput
				onChange={jest.fn()}
				operatorName={RelationalOperators.GT}
				touched
				valid={false}
				value=''
			/>
		);

		expect(container.querySelector('.has-error')).toBeTruthy();
	});
});
