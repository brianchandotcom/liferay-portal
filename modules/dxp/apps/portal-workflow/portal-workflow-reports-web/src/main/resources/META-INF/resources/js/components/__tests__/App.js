import React from 'react';
import App from '../App';
import renderer from 'react-test-renderer';

test('Link changes the class when hovered', () => {
	const component = renderer.create(<App />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});