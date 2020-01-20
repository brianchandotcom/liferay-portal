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
import ReactDOM from 'react-dom';
import renderer from 'react-test-renderer';

import HeaderTitle from '../../../../src/main/resources/META-INF/resources/js/shared/components/header/HeaderTitle.es';

beforeAll(() => {
	const vbody = document.createElement('div');

	vbody.innerHTML = '<div id="workflow"></div>';
	document.body.appendChild(vbody);

	ReactDOM.createPortal = jest.fn(element => {
		return element;
	});
});

test('Should render component on container', () => {
	const container = document.getElementById('workflow');

	const component = renderer.create(
		<HeaderTitle container={container} title="Metrics" />
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should set document title', () => {
	document.title = 'Metrics';

	const container = document.getElementById('workflow');

	const component = shallow(
		<HeaderTitle container={container} title="Metrics" />
	);

	const instance = component.instance();

	instance.setDocumentTitle('Metrics', 'SLAs');

	expect(document.title).toEqual('SLAs');
});

test('Should set document title if title prop changed', () => {
	document.title = 'Metrics';

	const container = document.getElementById('workflow');

	const component = shallow(
		<HeaderTitle container={container} title="Metrics" />
	);

	component.setProps({title: 'SLAs'});

	expect(document.title).toEqual('SLAs');
});
