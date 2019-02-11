import App from '../App';
import React from 'react';
import renderer from 'react-test-renderer';

test('Should render component', () => {
	const component = renderer.create(<App />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});