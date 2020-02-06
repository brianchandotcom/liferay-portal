/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React from 'react';

import MultiSelect from '../../../src/main/resources/META-INF/resources/js/shared/components/MultiSelect.es';

const data = [
	{desc: 'ops', id: '1'},
	{desc: 'message', id: '2'},
	{desc: 'meeting', id: '3'},
	{desc: 'assist', id: '4'},
	{desc: 'not real', id: '5'},
	{desc: 'advocate', id: '6'},
	{desc: 'Maybe', id: '7'},
	{desc: 'much', id: '8'},
	{desc: 'so much', id: '9'},
	{desc: 'to do any', id: '10'},
	{
		desc: 'a long component text example to test its overflow behavior.',
		id: '11'
	}
];
const selectedTagsId = ['5', '2', '6', '7'];

test('Should add a tag', () => {
	const component = mount(
		<MultiSelect data={data} selectedTagsId={selectedTagsId} />
	);

	const instance = component.instance();

	instance.addTag({
		currentTarget: {
			getAttribute: () => 'test'
		}
	});
	expect(component).toMatchSnapshot();
});

test('Should hide the dropDown list', () => {
	const component = mount(
		<MultiSelect data={data} selectedTagsId={selectedTagsId} />
	);

	const instance = component.instance();

	instance.hideDropList();
	expect(component).toMatchSnapshot();
});

test('Should remove a tag', () => {
	const component = mount(
		<MultiSelect data={data} selectedTagsId={selectedTagsId} />
	);

	const instance = component.instance();

	instance.removeTag({
		currentTarget: {
			getAttribute: () => '0'
		}
	});
	expect(component).toMatchSnapshot();
});

test('Should search', () => {
	const component = mount(
		<MultiSelect data={data} selectedTagsId={selectedTagsId} />
	);

	const instance = component.instance();

	instance.onSearch({
		keyCode: 84,
		target: {value: 'test'}
	});
	expect(component).toMatchSnapshot();
});

test('Should show the dropDown list', () => {
	const component = mount(
		<MultiSelect data={data} selectedTagsId={selectedTagsId} />
	);

	const instance = component.instance();

	instance.showDropList();
	expect(component).toMatchSnapshot();
});

test('Should test component render', () => {
	const component = mount(
		<MultiSelect data={data} selectedTagsId={selectedTagsId} />
	);

	expect(component).toMatchSnapshot();
});

test('Should test keydown press', () => {
	const component = mount(
		<MultiSelect data={data} selectedTagsId={selectedTagsId} />
	);

	const instance = component.instance();

	instance.onSearch({
		keyCode: 38,
		target: {value: ''}
	});
	expect(component).toMatchSnapshot();
});

test('Should test keyenter press', () => {
	const component = mount(
		<MultiSelect data={data} selectedTagsId={selectedTagsId} />
	);

	const instance = component.instance();

	instance.onSearch({
		keyCode: 38,
		target: {value: ''}
	});
	instance.onSearch({
		keyCode: 13,
		target: {value: ''}
	});
	expect(component).toMatchSnapshot();
});

test('Should test keyup press', () => {
	const component = mount(
		<MultiSelect data={data} selectedTagsId={selectedTagsId} />
	);

	const instance = component.instance();

	instance.onSearch({
		keyCode: 40,
		target: {value: ''}
	});
	expect(component).toMatchSnapshot();
});
