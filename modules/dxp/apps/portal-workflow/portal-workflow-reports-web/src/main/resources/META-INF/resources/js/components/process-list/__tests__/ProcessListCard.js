import React from 'react';
import ProcessListCard from '../ProcessListCard';
import renderer from 'react-test-renderer';

test('Should render component', () => {
	const component = renderer.create(<ProcessListCard />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});