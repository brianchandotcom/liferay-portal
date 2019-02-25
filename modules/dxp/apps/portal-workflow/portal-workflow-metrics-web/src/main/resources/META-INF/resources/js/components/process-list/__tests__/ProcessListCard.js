import fetch from '../../../test/mock/fetch';
import ProcessListCard from '../ProcessListCard';
import React from 'react';
import renderer from 'react-test-renderer';

test('Should render component', () => {
	const data = {items: [], totalCount: 0};
	const component = renderer.create(
		<ProcessListCard client={fetch(data)} companyId={1} />
	);
	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component with 10 records', () => {
	const data = {
		items: [
			{
				instancesCount: 0,
				title: 'Single Approver 1'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 2'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 3'
			},
			{
				instancesCount: 1,
				title: 'Single Approver 4'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 5'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 6'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 7'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 8'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 9'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 10'
			}
		],
		totalCount: 10
	};
	const component = renderer.create(
		<ProcessListCard client={fetch(data)} companyId={1} />
	);
	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component with 4 records', () => {
	const data = {
		items: [
			{
				instancesCount: 0,
				title: 'Single Approver 1'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 2'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 3'
			},
			{
				instancesCount: 1,
				title: 'Single Approver 4'
			}
		],
		totalCount: 4
	};
	const component = renderer.create(
		<ProcessListCard client={fetch(data)} companyId={1} />
	);
	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should change entry', () => {
	const data = {items: [], totalCount: 0};
	const component = shallow(
		<ProcessListCard client={fetch(data)} companyId={1} />
	);
	const instance = component.instance();

	instance.setEntry(20);
	expect(component.state('selectedEntry')).toBe(20);
});

test('Should change page', () => {
	const data = {items: [], totalCount: 0};
	const component = shallow(
		<ProcessListCard client={fetch(data)} companyId={1} />
	);
	const instance = component.instance();

	instance
		.setPage({size: 10, start: 2})
		.then(() => expect(component.state('start')).toBe(2));
});

test('Should search', () => {
	const data = {items: [], totalCount: 0};
	const component = shallow(
		<ProcessListCard client={fetch(data)} companyId={1} />
	);
	const instance = component.instance();

	instance.onSearch('test');
	expect(component.state('total')).toBe(0);
});