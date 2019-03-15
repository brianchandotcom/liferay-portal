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

test('Should render default component', () => {
	const component = renderer.create(<App companyId="123" />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render SLA Form component', () => {
	const component = renderer.create(
		<App companyId="123" defaultPath="sla-form" />
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render SLA List component', () => {
	const component = renderer.create(
		<App companyId="123" defaultPath="sla-list" />
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should test component navigation', () => {
	const component = renderer.create(<App companyId="123" />);

	const tree = component.toJSON();

	const linkSlaForm = shallow(
		<Link
			className="sla-form"
			query={{title: 'sla-form'}}
			text="sla-form"
			to="sla-form"
		/>
	);

	linkSlaForm.find('a').simulate('click');

	const linkSlaList = shallow(
		<Link
			className="sla-list"
			query={{title: 'sla-list'}}
			text="sla-list"
			to="sla-list"
		/>
	);

	linkSlaList.find('a').simulate('click');

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