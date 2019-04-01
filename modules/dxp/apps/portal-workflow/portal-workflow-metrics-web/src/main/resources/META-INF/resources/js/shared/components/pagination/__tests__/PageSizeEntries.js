import PageSizeEntries from '../PageSizeEntries';
import React from 'react';

test('Should render component', () => {
	const component = shallow(
		<PageSizeEntries pageSizeEntries={[10, 20, 30, 40]} selectedPageSize={10} />
	);

	expect(component).toMatchSnapshot();
});

test('Should change page size', () => {
	const component = shallow(
		<PageSizeEntries pageSizeEntries={[10, 20, 30, 40]} selectedPageSize={30} />
	);

	expect(component).toMatchSnapshot();
});