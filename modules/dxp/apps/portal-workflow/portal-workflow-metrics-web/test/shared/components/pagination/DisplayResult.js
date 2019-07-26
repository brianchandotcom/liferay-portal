import DisplayResult from '../../../../src/main/resources/META-INF/resources/js/shared/components/pagination/DisplayResult';
import React from 'react';
import renderer from 'react-test-renderer';

test('Should render component', () => {
	const component = renderer.create(
		<DisplayResult page={1} pageCount={10} pageSize={10} totalCount={12} />
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component to second page', () => {
	const component = renderer.create(
		<DisplayResult page={2} pageCount={2} pageSize={10} totalCount={12} />
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
