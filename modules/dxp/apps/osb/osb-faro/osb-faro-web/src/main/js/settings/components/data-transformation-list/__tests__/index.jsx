import DataTransformationList from '../index';
import React from 'react';
import {fromJS} from 'immutable';
import {renderWithStore} from 'test/mock-store';
import {shallow} from 'enzyme';

const fieldsIList = fromJS([
	{
		source: {
			name: 'givenName',
			value: 'Joe'
		},
		suggestion: {
			name: 'name',
			value: 'Steve'
		}
	},
	{
		source: {
			name: 'email',
			value: 'test@test.com'
		},
		suggestion: {
			name: 'email',
			value: 'joe@bloggs.com'
		}
	}
]);

const mappingSuggestions = {
	email: [{name: 'address', values: ['123']}],
	name: []
};

const sourceFields = {
	email: [],
	givenName: []
};

const defaultTestProps = {
	fieldsIList,
	groupId: '23',
	mappingSuggestions,
	sourceFields,
	sourceTitle: 'Source Title',
	suggestionsTitle: 'Suggestions Title'
};

describe('DataTransformationList', () => {
	it('should render', () => {
		const component = shallow(
			<DataTransformationList {...defaultTestProps} />
		);

		expect(component.shallow()).toMatchSnapshot();
	});

	it('should call props.onChange when suggestions fields change', () => {
		const spy = jest.fn();

		const component = shallow(
			<DataTransformationList {...defaultTestProps} onChange={spy} />
		);

		const item = {name: 'email'};

		component.instance().handleChange(0, item);

		expect(spy.mock.calls[0][0].first().get('suggestion').toJS()).toEqual(
			item
		);
	});

	it('should call props.onChange when source fields change', () => {
		const spy = jest.fn();

		const component = shallow(
			<DataTransformationList {...defaultTestProps} onChange={spy} />
		);

		const item = {name: 'email'};

		component.instance().handleChange(0, item, true);

		expect(spy.mock.calls[0][0].first().get('source').toJS()).toEqual(item);
	});

	it('should remove first field', () => {
		const spy = jest.fn();

		const component = shallow(
			<DataTransformationList {...defaultTestProps} onChange={spy} />
		);

		component.instance().handleRemove(0);

		jest.runAllTimers();

		expect(spy).toHaveBeenCalledWith(fieldsIList.slice(1));
	});

	xit('should hide mapped fields if hide mapped fields is true', () => {
		const component = renderWithStore(DataTransformationList, {
			...defaultTestProps,
			fieldsIList: fromJS([
				{source: {name: 'hoursOfOperation'}, suggestion: {}},
				{source: {name: 'foo'}, suggestion: {name: 'bar'}}
			]),
			hideMappedFields: true
		});

		expect(
			component.render().find('.data-transformation-item-root.hidden')
		).toMatchSnapshot();
	});
});
