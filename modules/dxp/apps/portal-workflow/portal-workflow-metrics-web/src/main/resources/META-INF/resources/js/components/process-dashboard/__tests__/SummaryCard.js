import React from 'react';
import { MockRouter as Router } from '../../../test/mock/MockRouter';
import SummaryCard from '../SummaryCard';

test('Should test mouse over', () => {
	const props = {
		elementClasses: 'test',
		iconColor: 'black',
		iconName: 'test',
		percentage: 10,
		title: 'test',
		total: 10
	};

	const component = mount(
		<Router>
			<SummaryCard {...props} />
		</Router>
	);

	const instance = component.find(SummaryCard).instance();

	instance.handleMouseOver();

	instance.handleMouseOut();

	expect(component).toMatchSnapshot();
});