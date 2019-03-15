import React from 'react';
import renderer from 'react-test-renderer';
import SLAConfirmDialog from '../SLAConfirmDialog';

test('Should render component', () => {
	const component = renderer.create(<SLAConfirmDialog item={'test'} />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});