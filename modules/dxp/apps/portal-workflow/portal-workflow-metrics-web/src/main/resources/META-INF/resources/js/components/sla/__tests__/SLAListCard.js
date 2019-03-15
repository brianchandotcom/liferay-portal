import fetch from '../../../test/mock/fetch';
import React from 'react';
import renderer from 'react-test-renderer';
import SLAListCard from '../SLAListCard';

test('Should change page', () => {
	const data = {items: [], totalCount: 0};
	const component = shallow(<SLAListCard client={fetch(data)} companyId={1} />);
	const instance = component.instance();

	instance.setPage(2).then(() => expect(component.state('page')).toBe(2));
});

test('Should change page size', () => {
	const data = {items: [], totalCount: 0};
	const component = shallow(<SLAListCard client={fetch(data)} companyId={1} />);
	const instance = component.instance();

	instance
		.setPageSize(20)
		.then(() => expect(component.state('pageSize')).toBe(20));
});

test('Should render component', () => {
	const data = {items: [], totalCount: 0};
	const component = renderer.create(
		<SLAListCard client={fetch(data)} companyId={1} />
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component after item was removed', () => {
	const data = {items: [], totalCount: 0};
	const component = renderer.create(
		<SLAListCard client={fetch(data)} companyId={1} itemRemoved={'test'} />
	);
	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should remove a item', () => {
	const data = {items: [], totalCount: 0};
	const component = shallow(<SLAListCard client={fetch(data)} companyId={1} />);
	const instance = component.instance();

	instance.removeItem();
	expect(component).toMatchSnapshot();
});

test('Should search', () => {
	const data = {items: [], totalCount: 0};
	const component = shallow(<SLAListCard client={fetch(data)} companyId={1} />);
	const instance = component.instance();

	instance.onSearch('test');
	expect(component.state('totalCount')).toBe(0);
});