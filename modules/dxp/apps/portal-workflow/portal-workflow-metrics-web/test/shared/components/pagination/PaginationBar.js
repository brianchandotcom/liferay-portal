import PaginationBar from 'shared/components/pagination/PaginationBar';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from 'test/mock/MockRouter';

jest.mock('components/AppContext');

test('Should render component', () => {
	const component = renderer.create(
		<Router>
			<PaginationBar
				page={1}
				pageCount={5}
				pageSize={5}
				totalCount={22}
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component with total count minor than last page', () => {
	const component = renderer.create(
		<Router>
			<PaginationBar page={1} pageCount={5} pageSize={5} totalCount={5} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
