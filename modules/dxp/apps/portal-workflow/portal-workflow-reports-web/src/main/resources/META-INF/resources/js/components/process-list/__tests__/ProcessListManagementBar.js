import ProcessListManagementBar from '../ProcessListManagementBar';
import React from 'react';
import renderer from 'react-test-renderer';

test('Should render component', () => {
	const component = renderer.create(<ProcessListManagementBar />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});