import {Filter} from '../../../../src/main/resources/META-INF/resources/js/shared/components/filter/Filter';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../../mock/MockRouter';

function mockItems(count) {
	const items = [];

	for (let i = 0; i < count; i++) {
		items.push({
			active: i % 2 === 0,
			key: `key-${i}`,
			name: `Item Name ${i}`
		});
	}

	return items;
}

test('Should active item when input is checked', () => {
	const items = [
		{
			active: false,
			key: 'overdue',
			name: 'Overdue'
		}
	];

	const component = mount(
		<Router query="?filters.slaStatus%5B0%5D=overdue">
			<Filter
				filterKey="slaStatus"
				items={items}
				location={{search: '?filters.slaStatus%5B0%5D=overdue'}}
				name="SLA Status"
			/>
		</Router>
	);

	const instance = component.find(Filter).instance();

	instance.onInputChange({
		target: {checked: true, dataset: {key: 'overdue'}}
	});

	expect(instance.state.items[0].active).toEqual(true);
});

test('Should hide dropdown when click outside filter', () => {
	const items = [
		{
			active: true,
			key: 'overdue',
			name: 'Overdue'
		}
	];

	const component = mount(
		<Router query="?filters.slaStatus%5B0%5D=overdue">
			<Filter
				filterKey="slaStatus"
				items={items}
				location={{search: '?filters.slaStatus%5B0%5D=overdue'}}
				match={{params: {page: 3}, path: '/instances/:page'}}
				name="SLA Status"
			/>
		</Router>
	);

	const instance = component.find(Filter).instance();

	instance.toggleDropDown();
	instance.onInputChange({
		target: {checked: true, dataset: {key: 'overdue'}}
	});
	instance.onClickOutside(document.body);

	expect(instance.state.expanded).toEqual(false);
});

test('Should keep dropdown open when click inside filter', () => {
	const items = [
		{
			active: true,
			key: 'overdue',
			name: 'Overdue'
		}
	];

	const component = mount(
		<Router query="?filters.slaStatus%5B0%5D=overdue">
			<Filter
				filterKey="slaStatus"
				items={items}
				location={{search: '?filters.slaStatus%5B0%5D=overdue'}}
				name="SLA Status"
			/>
		</Router>
	);

	const instance = component.find(Filter).instance();

	instance.toggleDropDown();

	instance.onClickOutside({target: instance.wrapperRef});

	expect(instance.state.expanded).toEqual(true);
});

test('Should render component', () => {
	const component = renderer.create(
		<Router query="?filters.slaStatus%5B0%5D=overdue">
			<Filter filterKey="slaStatus" name="SLA Status" />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component with search wrapper', () => {
	const items = mockItems(15);

	const component = renderer.create(
		<Router query="?filters.slaStatus%5B0%5D=overdue">
			<Filter filterKey="slaStatus" items={items} name="SLA Status" />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should search items', () => {
	const items = [
		{
			active: true,
			key: 'overdue',
			name: 'Overdue'
		},
		{
			active: false,
			key: 'ontime',
			name: 'On Time'
		}
	];

	const component = mount(
		<Router query="?filters.slaStatus%5B0%5D=overdue">
			<Filter filterKey="slaStatus" items={items} name="SLA Status" />
		</Router>
	);

	const instance = component.find(Filter).instance();

	instance.onSearchChange({
		target: {
			value: 'over'
		}
	});

	expect(instance.filteredItems.length).toEqual(1);
});

test('Should toggle dropdown', () => {
	const component = mount(
		<Router query="?filters.slaStatus%5B0%5D=overdue">
			<Filter filterKey="slaStatus" name="SLA Status" />
		</Router>
	);

	const instance = component.find(Filter).instance();

	instance.toggleDropDown();

	expect(instance.state.expanded).toEqual(true);
});
