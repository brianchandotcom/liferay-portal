import Link from '../Link';
import React from 'react';
import Router from '../Router';

afterEach(() => {
	const vbodyElement = document.body.getElementsByClassName('vbody')[0];

	vbodyElement.parentNode.removeChild(vbodyElement);
});

beforeEach(() => {
	const vbody = document.createElement('div');

	vbody.className = 'vbody';
	vbody.innerHTML =
		'<ul class="control-menu-nav"><label class="control-menu-level-1-heading">title</label></ul><ul class="control-menu-nav"></ul>';
	document.body.appendChild(vbody);
});

test('Should test component render', () => {
	const component = shallow(<Router defautPath="test" paths={[]} />);
	const instance = component.instance();

	instance.componentDidMount();

	const link = shallow(
		<Link className="test" text="test" to="test" type="button" />
	);

	link.find('a').simulate('click');

	instance.componentWillUnmount();

	expect(component).toMatchSnapshot();
});

test('Should test navigate', () => {
	const paths = [
		{
			component: ({title}) => <div>{title}</div>,
			path: 'test-1',
			title: 'test-1'
		},
		{
			component: () => <div>{'test-2'}</div>,
			path: 'test-2',
			title: 'test-2'
		},
		{
			component: ({title}) => <div>{title}</div>,
			path: 'test-3',
			title: 'test-3'
		}
	];
	const component = shallow(<Router defautPath="test-1" paths={paths} />);
	const instance = component.instance();

	instance.componentDidMount();

	const link = shallow(
		<Link
			className="test2"
			query={{title: 'test-2'}}
			text="test-2"
			to="test-2"
			type="button"
		/>
	);

	link.find('a').simulate('click');

	expect(component).toMatchSnapshot();
});

test('Should test navigate with title', () => {
	const paths = [
		{
			component: ({title}) => <div>{title}</div>,
			path: 'test-1',
			title: 'test-1'
		},
		{
			component: () => <div>{'test-2'}</div>,
			path: 'test-2',
			title: 'test-2'
		},
		{
			component: ({title}) => <div>{title}</div>,
			path: 'test-3',
			title: 'test-3'
		}
	];
	const component = shallow(<Router defautPath="test-3" paths={paths} />);
	const instance = component.instance();

	instance.componentDidMount();

	const link = shallow(
		<Link
			className="test2"
			query={{title: 'test-2'}}
			text="test"
			to="test-2"
			type="button"
		/>
	);

	link.find('a').simulate('click');

	expect(component).toMatchSnapshot();
});

test('Should test returning navigate', () => {
	const paths = [
		{
			component: ({title}) => <div>{title}</div>,
			path: 'test-1',
			title: 'test-1'
		},
		{
			component: () => <div>{'test-2'}</div>,
			path: 'test-2',
			title: 'test-2'
		},
		{
			component: ({title}) => <div>{title}</div>,
			path: 'test-3',
			title: 'test-3'
		}
	];
	const component = shallow(<Router defautPath="test-3" paths={paths} />);
	const instance = component.instance();

	instance.componentDidMount();

	const link = shallow(
		<Link
			className="test2"
			query={{title: 'test-2'}}
			text="test2"
			title="test-2"
			to="test-2"
			type="button"
		/>
	);

	link.find('a').simulate('click');

	const linkreturn = shallow(
		<Link
			className="test3"
			query={{title: 'test-3'}}
			text="test3"
			to="test-3"
		/>
	);

	linkreturn.find('a').simulate('click');

	expect(component).toMatchSnapshot();
});

test('Should test returning navigate by click', () => {
	const paths = [
		{
			component: () => <div>{'test-1'}</div>,
			path: 'test-1',
			title: 'test-1'
		},
		{
			component: () => <div>{'test-2'}</div>,
			path: 'test-2',
			title: 'test-2'
		}
	];
	const component = shallow(<Router defautPath="test-1" paths={paths} />);
	const instance = component.instance();

	instance.componentDidMount();

	instance.onPageChanged();

	const link = shallow(
		<Link
			className="test2"
			query={{title: 'test-2'}}
			text="test2"
			title="test-2"
			to="test-2"
			type="button"
		/>
	);

	link.find('a').simulate('click');

	expect(component).toMatchSnapshot();
});

test('Should test query when clicked', () => {
	const paths = [
		{
			component: () => <div>{'test-1'}</div>,
			path: 'test-1',
			title: 'test-1'
		},
		{
			component: () => <div>{'test-1'}</div>,
			path: 'test-2',
			title: 'test-2'
		}
	];
	const component = shallow(<Router defautPath="test-1" paths={paths} />);
	const instance = component.instance();

	instance.componentDidMount();

	const link = shallow(
		<Link
			className="test2"
			query={{title: 'test-2', ztext: 'basic'}}
			text="test2"
			title="test-2"
			to="test-2"
			type="button"
		/>
	);

	link.find('a').simulate('click');
	instance.onPageChanged();
	expect(instance.getQuery()).toEqual({});
});