import CustomBooleanInput from '../CustomBooleanInput';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import {Property} from 'shared/util/records';

jest.unmock('react-dom');

describe('CustomBooleanInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<CustomBooleanInput
				displayValue='Do Not Call'
				operatorRenderer={() => <div>{'operator'}</div>}
				property={new Property({entityName: 'Organization'})}
				value={fromJS({
					criterionGroup: {
						conjunctionName: 'and',
						groupId: '123',
						items: [
							{
								operatorName: 'eq',
								propertyName: 'custom/doNotCall/value',
								value: 'true'
							}
						]
					}
				})}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
