import MultiSelect from '../MultiSelect';
import React from 'react';

const data = [
	'ops',
	'message',
	'meeting',
	'assist',
	'not real',
	'advocate',
	'Maybe',
	'much',
	'so much',
	'to do any',
	'a long component text example to test its overflow behavior.'
];
const selectedTags = ['ops', 'message', 'advocate', 'Maybe'];

test('Should test component render', () => {
	const component = shallow(
		<MultiSelect data={data} selectedTags={selectedTags} />
	);

	expect(component).toMatchSnapshot();
});

test('Should show the dropdown list', () => {
	const component = shallow(
		<MultiSelect data={data} selectedTags={selectedTags} />
	);

	const instance = component.instance();

	instance.showDropList();
	expect(component).toMatchSnapshot();
});

test('Should hide the dropdown list', () => {
	const component = shallow(
		<MultiSelect data={data} selectedTags={selectedTags} />
	);

	const instance = component.instance();

	instance.hideDropList();
	expect(component).toMatchSnapshot();
});

test('Should add a tag', () => {
	const component = shallow(
		<MultiSelect data={data} selectedTags={selectedTags} />
	);

	const instance = component.instance();

	instance.addTag({
		currentTarget: {
			getAttribute: () => 'test'
		}
	});
	expect(component).toMatchSnapshot();
});

test('Should remove a tag', () => {
	const component = shallow(
		<MultiSelect data={data} selectedTags={selectedTags} />
	);

	const instance = component.instance();

	instance.removeTag({
		currentTarget: {
			getAttribute: () => '0'
		}
	});
	expect(component).toMatchSnapshot();
});

test('Should test keydown press', () => {
	const component = shallow(
		<MultiSelect data={data} selectedTags={selectedTags} />
	);

	const instance = component.instance();

	instance.onSearch({
		keyCode: 38,
		target: { value: '' }
	});
	expect(component).toMatchSnapshot();
});

test('Should test keyup press', () => {
	const component = shallow(
		<MultiSelect data={data} selectedTags={selectedTags} />
	);

	const instance = component.instance();

	instance.onSearch({
		keyCode: 40,
		target: { value: '' }
	});
	expect(component).toMatchSnapshot();
});

test('Should test keyenter press', () => {
	const component = shallow(
		<MultiSelect data={data} selectedTags={selectedTags} />
	);

	const instance = component.instance();

	instance.onSearch({
		keyCode: 38,
		target: { value: '' }
	});
	instance.onSearch({
		keyCode: 13,
		target: { value: '' }
	});
	expect(component).toMatchSnapshot();
});

test('Should search', () => {
	const component = shallow(
		<MultiSelect data={data} selectedTags={selectedTags} />
	);

	const instance = component.instance();

	instance.onSearch({
		keyCode: 84,
		target: { value: 'test' }
	});
	expect(component).toMatchSnapshot();
});