import * as data from 'test/data';
import AccountDisplay from '../AccountDisplay';
import React from 'react';
import {
	CustomFunctionOperators,
	PropertyTypes,
	RelationalOperators
} from 'segment/segment-editor/dynamic/utils/constants';
import {List, Map} from 'immutable';
import {Property} from 'shared/util/records';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('AccountDisplay', () => {
	const propertyName = 'organization/description/value';

	const mockCriterion = {
		operatorName: CustomFunctionOperators.AccountsFilter,
		propertyName,
		value: Map({
			criterionGroup: Map({
				items: List([
					Map({
						operatorName: RelationalOperators.EQ,
						propertyName: 'organization/description/value',
						value: 'this is a description'
					})
				])
			})
		})
	};

	const mockProperty = data.getImmutableMock(Property, data.mockProperty, 1, {
		entityName: 'Account',
		label: 'description',
		name: propertyName,
		propertykey: 'account',
		type: PropertyTypes.AccountText
	});

	it('renders', () => {
		const {container} = render(
			<AccountDisplay criterion={mockCriterion} property={mockProperty} />
		);

		expect(container).toMatchSnapshot();
	});

	it('renders w/ a unknownType', () => {
		const criterion = {...mockCriterion};

		criterion.value = criterion.value.setIn(
			['criterionGroup', 'items', 0, 'value'],
			null
		);

		const {container} = render(
			<AccountDisplay criterion={criterion} property={mockProperty} />
		);

		expect(container).toHaveTextContent(/is unknown/);
	});
});
