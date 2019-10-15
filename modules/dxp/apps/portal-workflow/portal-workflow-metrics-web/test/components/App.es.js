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

import App from '../../src/main/resources/META-INF/resources/js/components/App.es';
import React from 'react';
import ReactDOM from 'react-dom';
import renderer from 'react-test-renderer';

beforeAll(() => {
	const vbody = document.createElement('div');

	vbody.innerHTML = `<div id="workflow_controlMenu">
		<div class="sites-control-group">
			<ul class="control-menu-nav"></ul>
		</div>
		<div class="tools-control-group">
			<ul class="control-menu-nav">
				<label class="control-menu-level-1-heading">title</label>
			</ul>
		</div>
	</div>`;
	document.body.appendChild(vbody);

	ReactDOM.createPortal = jest.fn(element => {
		return element;
	});

	global.Liferay = {
		Language: {
			get: key => key
		},
		ThemeDisplay: {
			getPathThemeImages: () => '/'
		}
	};
});

afterAll(() => {
	global.Liferay = null;
});

test('Should render default component', () => {
	const component = renderer.create(<App namespace="workflow_" />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render default component without custom header', () => {
	document.getElementById('workflow_controlMenu').id = '';

	const component = renderer.create(<App />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should set status', () => {
	const component = renderer.create(<App />);

	const instance = component.getInstance();

	instance.setStatus('sla-updated');

	expect(instance.state.status).toEqual('sla-updated');
});
