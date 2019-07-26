import PageSizeEntries from '../../../../src/main/resources/META-INF/resources/js/shared/components/pagination/PageSizeEntries';
import React from 'react';
import {MockRouter as Router} from '../../../mock/MockRouter';

test('Should change page size', () => {
	const component = shallow(
		<PageSizeEntries
			pageSizeEntries={[10, 20, 30, 40]}
			selectedPageSize={30}
		/>
	);

	expect(component).toMatchSnapshot();
});

test('Should render component', () => {
	const component = shallow(
		<PageSizeEntries
			pageSizeEntries={[10, 20, 30, 40]}
			selectedPageSize={10}
		/>
	);

	expect(component).toMatchSnapshot();
});

test('Should render with default deltas', () => {
	const component = mount(
		<Router>
			<PageSizeEntries selectedPageSize={30} />
		</Router>
	);

	expect(component).toMatchSnapshot();
});
