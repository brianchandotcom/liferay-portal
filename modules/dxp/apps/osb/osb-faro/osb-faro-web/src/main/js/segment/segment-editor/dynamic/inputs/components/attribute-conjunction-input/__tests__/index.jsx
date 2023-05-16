import AttributeConjunctionInput from '../index';
import React from 'react';
import {fireEvent, render} from '@testing-library/react';
import {mockEventAttributeDefinition} from 'test/data';
import {range} from 'lodash';
import {RelationalOperators} from '../../../../utils/constants';

jest.unmock('react-dom');

describe('AttributeConjunctionInput', () => {
	it('should render', () => {
		const {container, getAllByText, getByText} = render(
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
		fireEvent.click(getByText('displayName-1'));

		expect(getByText('displayName-0')).toBeTruthy();
		expect(getAllByText('displayName-1')[1]).toBeTruthy();
		expect(getByText('displayName-2')).toBeTruthy();
		expect(getByText('displayName-3')).toBeTruthy();

		expect(container).toMatchSnapshot();
	});
});
