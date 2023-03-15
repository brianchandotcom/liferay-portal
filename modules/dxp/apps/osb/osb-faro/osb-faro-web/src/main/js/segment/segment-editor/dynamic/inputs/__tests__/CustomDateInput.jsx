import CustomDateInput from '../CustomDateInput';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {createCustomValueMap} from '../../utils/custom-inputs';
import {Property} from 'shared/util/records';
import {RelationalOperators} from '../../utils/constants';

jest.unmock('react-dom');

const mockValue = createCustomValueMap([
	{
		key: 'criterionGroup',
		value: [
			{
				operatorName: RelationalOperators.GT,
				propertyName: 'completeDate',
				value: '2020-01-17'
			}
		]
	}
]);

describe('CustomDateInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<CustomDateInput property={new Property()} value={mockValue} />
		);

		expect(container).toMatchSnapshot();
	});
});
