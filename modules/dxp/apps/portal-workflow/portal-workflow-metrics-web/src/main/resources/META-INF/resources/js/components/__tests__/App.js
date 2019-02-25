import App from '../App';
import Link from '../../shared/components/router/Link';
import React from 'react';
import renderer from 'react-test-renderer';

beforeAll(() => {
	const vbody = document.createElement('div');
	vbody.innerHTML =
		'<ul class="control-menu-nav"><label class="control-menu-level-1-heading">title</label></ul><ul class="control-menu-nav"></ul>';
	document.body.appendChild(vbody);
});

test('Should render component', () => {
	const component = renderer.create(<App companyId="123" />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should test component navegation', () => {
	const component = renderer.create(<App companyId="123" />);

	const tree = component.toJSON();

	const linkProcessList = shallow(
		<Link
			className="process-list"
			query={{title: 'process-list'}}
			text="process-list"
			to="process-list"
		/>
	);
	linkProcessList.find('a').simulate('click');

	expect(tree).toMatchSnapshot();
});