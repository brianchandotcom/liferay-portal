import DisplayResult from '../DisplayResult';
import React from 'react';
import renderer from 'react-test-renderer';

test('Should render component', () => {
	const component = renderer.create(
		<DisplayResult count={10} page={1} totalCount={20} />
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});