import CustomDateTimeInput from '../CustomDateTimeInput';
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
				value: '2020-01-17T00:00:00.000Z'
			}
		]
	}
]);

describe('CustomDateTimeInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<CustomDateTimeInput
				property={new Property()}
				timeZoneId='UTC'
				value={mockValue}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
