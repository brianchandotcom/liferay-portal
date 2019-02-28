import PageSizeEntries from '../PageSizeEntries';
import React from 'react';
import renderer from 'react-test-renderer';

test('Should render component', () => {
	const component = renderer.create(
		<PageSizeEntries pageSizeEntries={[10, 20, 30, 40]} selectedPageSize={10} />
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should change page size', () => {
	const onSelectPageSize = () => pageSize => pageSize;

	const component = shallow(
		<PageSizeEntries
			onSelectPageSize={onSelectPageSize()}
			pageSizeEntries={[10, 20, 30, 40]}
			selectedPageSize={30}
		/>
	);

	const instance = component.instance();

	instance.setPageSize(30);
	expect(component.state('selectedPageSize')).toBe(30);
});