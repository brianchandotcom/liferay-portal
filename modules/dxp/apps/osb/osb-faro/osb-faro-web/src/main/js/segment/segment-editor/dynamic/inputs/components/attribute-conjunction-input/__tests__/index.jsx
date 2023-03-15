import AttributeConjunctionInput from '../index';
import React from 'react';
import {mockEventAttributeDefinition} from 'test/data';
import {range} from 'lodash';
import {RelationalOperators} from '../../../../utils/constants';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('AttributeConjunctionInput', () => {
	it('should render', () => {
		const {container} = render(
			<AttributeConjunctionInput
				attributes={range(4).map(index =>
					mockEventAttributeDefinition(index)
				)}
				conjunctionCriterion={{
					operatorName: RelationalOperators.EQ,
					propertyName: 'attribute/1',
					value: 'test value'
				}}
				onChange={jest.fn()}
				touched={{attribute: true, attributeValue: true}}
				valid={{attribute: true, attributeValue: true}}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
