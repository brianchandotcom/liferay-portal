import OpenProcessesSummary from 'components/open-processes-summary/OpenProcessesSummary';
import React from 'react';
import renderer from 'react-test-renderer';

test('Should render component', () => {
	const component = renderer.create(<OpenProcessesSummary />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});