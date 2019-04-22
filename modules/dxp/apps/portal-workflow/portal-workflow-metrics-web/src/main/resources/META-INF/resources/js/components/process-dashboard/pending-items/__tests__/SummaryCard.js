import React from 'react';
import { MockRouter as Router } from '../../../../test/mock/MockRouter';
import SummaryCard from '../SummaryCard';

test('Should format value for values with more than 4 digits', () => {
	const props = {
		iconColor: 'danger',
		iconName: 'exclamation-circle',
		processId: 12345,
		title: 'Overdue',
		total: false,
		totalValue: 858000,
		value: 156403
	};

	const component = mount(
		<Router>
			<SummaryCard {...props} />
		</Router>
	);

	const instance = component.find(SummaryCard).instance();

	expect(instance.formattedValue).toEqual('156.4K');
});

test('Should not format value for values with 4 or less digits', () => {
	const props = {
		iconColor: 'danger',
		iconName: 'exclamation-circle',
		processId: 12345,
		title: 'Overdue',
		total: false,
		totalValue: 3500,
		value: 3000
	};

	const component = mount(
		<Router>
			<SummaryCard {...props} />
		</Router>
	);

	const instance = component.find(SummaryCard).instance();

	expect(instance.formattedValue).toEqual('3,000');
});

test('Should render component', () => {
	const props = {
		iconColor: 'success',
		iconName: 'check-circle',
		processId: 12345,
		title: 'On Time',
		total: false,
		totalValue: 55,
		value: 31
	};

	const component = mount(
		<Router>
			<SummaryCard {...props} />
		</Router>
	);

	expect(component).toMatchSnapshot();
});

test('Should test mouse over', () => {
	const props = {
		iconColor: 'success',
		iconName: 'check-circle',
		processId: 12345,
		title: 'On Time',
		total: false,
		totalValue: 55,
		value: 31
	};

	const component = mount(
		<Router>
			<SummaryCard {...props} />
		</Router>
	);

	const instance = component.find(SummaryCard).instance();

	instance.handleMouseOver(null, () => {
		expect(instance.state.hovered).toEqual(true);
	});

	instance.handleMouseOut(null, () => {
		expect(instance.state.hovered).toEqual(false);
	});
});