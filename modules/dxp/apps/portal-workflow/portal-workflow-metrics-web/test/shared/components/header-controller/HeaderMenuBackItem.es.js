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

import HeaderMenuBackItem from '../../../../src/main/resources/META-INF/resources/js/shared/components/header/HeaderMenuBackItem.es';
import {MockRouter} from '../../../mock/MockRouter.es';

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
		<MockRouter>
			<HeaderMenuBackItem
				basePath="/"
				container={container}
				location={{
					pathname: '/slas',
					search: 'backPath=%2Fprocesses'
				}}
			/>
		</MockRouter>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
