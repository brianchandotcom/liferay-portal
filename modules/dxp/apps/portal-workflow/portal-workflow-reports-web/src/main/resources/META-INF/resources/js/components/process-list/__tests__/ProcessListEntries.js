import ProcessListEntries from '../ProcessListEntries';
import React from 'react';
import renderer from 'react-test-renderer';

test('Should render component', () => {
	const component = renderer.create(
		<ProcessListEntries entries={[10, 20, 30, 40]} selectedEntry={10} />
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should change entry', () => {
	const onSelectEntry = () => entry => entry;

	const component = shallow(
		<ProcessListEntries
			entries={[10, 20, 30, 40]}
			onSelectEntry={onSelectEntry()}
			selectedEntry={30}
		/>
	);

	const instance = component.instance();

	instance.setEntry(30);
	expect(component.state('selectedEntry')).toBe(30);
});