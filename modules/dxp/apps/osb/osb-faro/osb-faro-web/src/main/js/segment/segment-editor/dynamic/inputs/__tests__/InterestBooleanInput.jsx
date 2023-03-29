import InterestBooleanInput from '../InterestBooleanInput';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {createCustomValueMap} from '../../utils/custom-inputs';
import {Property} from 'shared/util/records';
import {RelationalOperators} from '../../utils/constants';

jest.unmock('react-dom');

describe('InterestBooleanInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<InterestBooleanInput
				property={new Property({entityName: 'Foo Entity'})}
				value={createCustomValueMap([
					{
						key: 'criterionGroup',
						value: [
							{
								operatorName: RelationalOperators.EQ,
								propertyName: 'name',
								value: 'foo interest'
							},
							{
								operatorName: RelationalOperators.EQ,
								propertyName: 'score',
								value: 'true'
							}
						]
					}
				])}
			/>
		);
		expect(container).toMatchSnapshot();
	});
});
