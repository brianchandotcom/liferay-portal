import React from 'react';
import {fromJS} from 'immutable';
import {DataTransformationListRow as Row} from '../Row';
import {shallow} from 'enzyme';

const defaultProps = {
	dataSourceFn: jest.fn(),
	fieldIMap: fromJS({
		source: {},
		suggestion: {}
	}),
	groupId: '23',
	index: 1
};

describe('Row', () => {
	it('should render', () => {
		const component = shallow(<Row {...defaultProps} />);

		expect(component).toMatchSnapshot();
	});

	it('should render w/ a null mappingSuggestions', () => {
		const component = shallow(
			<Row
				{...defaultProps}
				fieldIMap={fromJS({
					source: {name: 'givenName'},
					suggestion: {}
				})}
				mappingSuggestions={{givenName: [null]}}
			/>
		);

		expect(component).not.toBeNull();
	});

	it('should set showHelpIcon to false and call props.onChange', () => {
		const spy = jest.fn();
		const index = 1;
		const item = {name: 'foo'};

		const component = shallow(<Row {...defaultProps} onChange={spy} />);

		expect(component.state('showHelpIcon')).toBe(true);

		component.instance().handleMappingSelect(item);

		jest.runAllTimers();

		expect(component.state('showHelpIcon')).toBe(false);
		expect(spy).toHaveBeenCalledWith(index, item);
	});

	it('should call onRemove prop', () => {
		const index = 1;
		const onRemove = jest.fn();

		const component = shallow(
			<Row {...defaultProps} onRemove={onRemove} />
		);

		component.instance().handleRemove();

		expect(onRemove).toHaveBeenCalledWith(index);
	});

	it('should call onChange prop', () => {
		const index = 1;
		const item = {name: 'foo'};
		const onChange = jest.fn();

		const component = shallow(
			<Row {...defaultProps} onChange={onChange} />
		);

		component.instance().handleSourceSelect(item);

		expect(onChange).toHaveBeenCalledWith(index, item, true);
	});

	it('should set sourceQuery state', () => {
		const value = 'foo bar';

		const component = shallow(<Row {...defaultProps} />);

		component.instance().handleSourceSearch(value);

		jest.runAllTimers();

		expect(component.state('sourceQuery')).toEqual(value);
	});

	it('should update when receiving a new field', () => {
		const component = shallow(
			<Row
				{...defaultProps}
				fieldIMap={fromJS({
					source: {name: 'first_name'},
					suggestion: {
						name: 'foo',
						value: 'bar'
					}
				})}
			/>
		);

		component.setProps({
			fieldIMap: fromJS({
				source: {name: 'first_name'},
				suggestion: {
					name: 'baz',
					value: 'biz'
				}
			})
		});

		jest.runAllTimers();

		expect(component).toMatchSnapshot('should contain "baz" and "biz"');
	});

	it('should only make a request for querySuggestions on handleMappingSearch', () => {
		const spy = jest.fn(() => Promise.resolve({items: [], total: 0}));

		const component = shallow(<Row {...defaultProps} dataSourceFn={spy} />);

		expect(spy).not.toBeCalled();

		component.instance().handleMappingSearch('test');

		jest.runAllTimers();

		expect(spy).toBeCalled();
	});

	it('should display an error state if it is a duplicate target field', () => {
		const component = shallow(
			<Row {...defaultProps} isDuplicateTargetField />
		);

		expect(component).toMatchSnapshot();
	});
});
