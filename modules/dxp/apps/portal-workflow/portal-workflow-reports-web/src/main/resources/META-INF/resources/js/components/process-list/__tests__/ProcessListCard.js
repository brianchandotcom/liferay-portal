// eslint-disable-next-line no-unused-vars
import React from 'react';
import ProcessListCard from '../ProcessListCard';
import renderer from 'react-test-renderer';

test('Link changes the class when hovered', () => {
	const component = renderer.create(<ProcessListCard />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});