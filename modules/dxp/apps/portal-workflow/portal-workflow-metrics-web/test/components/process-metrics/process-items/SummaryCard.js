import React from 'react';
import {MockRouter as Router} from '../../../mock/MockRouter';
import SummaryCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/process-items/SummaryCard';

test('Should format percentage', () => {
	const props = {
		getTitle: () => 'Overdue',
		iconColor: 'danger',
		iconName: 'exclamation-circle',
		processId: 12345,
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

	expect(instance.formattedPercentage).toEqual('18.23%');
});

test('Should format value for values with more than 3 digits', () => {
	const props = {
		getTitle: () => 'Overdue',
		iconColor: 'danger',
		iconName: 'exclamation-circle',
		processId: 12345,
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

test('Should not format percentage for total item', () => {
	const props = {
		getTitle: () => 'Total',
		processId: 12345,
		total: true,
		totalValue: 858000,
		value: 858000
	};

	const component = mount(
		<Router>
			<SummaryCard {...props} />
		</Router>
	);

	const instance = component.find(SummaryCard).instance();

	expect(instance.formattedPercentage).toEqual(null);
});

test('Should not format value for values with 3 or less digits', () => {
	const props = {
		getTitle: () => 'Overdue',
		iconColor: 'danger',
		iconName: 'exclamation-circle',
		processId: 12345,
		total: false,
		totalValue: 3500,
		value: 310
	};

	const component = mount(
		<Router>
			<SummaryCard {...props} />
		</Router>
	);

	const instance = component.find(SummaryCard).instance();

	expect(instance.formattedValue).toEqual('310');
});

test('Should render component', () => {
	const props = {
		getTitle: () => 'On Time',
		iconColor: 'success',
		iconName: 'check-circle',
		processId: 12345,
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

test('Should render component with disabled state', () => {
	const props = {
		getTitle: () => 'On Time',
		iconColor: 'success',
		iconName: 'check-circle',
		processId: 12345,
		total: false,
		totalValue: 55,
		value: undefined
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
		getTitle: () => 'On Time',
		iconColor: 'success',
		iconName: 'check-circle',
		processId: 12345,
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
