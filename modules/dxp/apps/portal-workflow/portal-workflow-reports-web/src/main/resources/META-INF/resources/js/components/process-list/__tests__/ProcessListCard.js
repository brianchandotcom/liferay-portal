import ProcessListCard from '../ProcessListCard';
import React from 'react';
import renderer from 'react-test-renderer';

xtest('Should render component', () => {
	const component = renderer.create(<ProcessListCard companyId={1} />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

xtest('Should change entry', () => {
	const component = shallow(<ProcessListCard companyId={1} />);

	const instance = component.instance();

	instance.setEntry(20);
	expect(component.state('selectedEntry')).toBe(20);
});

xtest('Should change page', () => {
	const component = shallow(<ProcessListCard companyId={1} />);

	const instance = component.instance();

	instance.setPage({size: 10, start: 1});
	expect(component.state('selectedEntry')).toBe(20);
});