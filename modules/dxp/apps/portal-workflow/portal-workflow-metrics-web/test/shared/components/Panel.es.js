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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import Panel from '../../../src/main/resources/META-INF/resources/js/shared/components/Panel.es';

describe('The Panel component should', () => {
	afterEach(cleanup);

	test('Not render child components without content', () => {
		const {container} = render(
			<Panel>
				<Panel.Body />
				<Panel.Footer />
			</Panel>
		);

		const panel = container.querySelector('.panel');

		expect(panel.children[0]).toBeUndefined();
		expect(panel.children[1]).toBeUndefined();
	});

	test('Render components correctly', () => {
		const {container} = render(
			<Panel>
				<Panel.Header>{'Header'}</Panel.Header>
				<Panel.Body>{'Body'}</Panel.Body>
				<Panel.Footer>{'Footer'}</Panel.Footer>
			</Panel>
		);

		const panel = container.querySelector('.panel');

		expect(panel.children.length).toBe(3);
		expect(panel.children[0]).toHaveTextContent('Header');
		expect(panel.children[1]).toHaveTextContent('Body');
		expect(panel.children[2]).toHaveTextContent('Footer');
	});

	test('Render class passed by props', () => {
		const {container} = render(
			<Panel elementClasses={'custom-class'}>
				<Panel.Header elementClasses={'custom-class-header'}>
					{'Header'}
				</Panel.Header>
				<Panel.Body elementClasses={'custom-class-body'}>
					{'Body'}
				</Panel.Body>
				<Panel.Footer elementClasses={'custom-class-footer'}>
					{'Footer'}
				</Panel.Footer>
			</Panel>
		);

		const panel = container.querySelector('.panel');

		expect(panel.classList[2]).toBe('custom-class');
		expect(panel.children[0].classList[1]).toBe('custom-class-header');
		expect(panel.children[1].classList[1]).toBe('custom-class-body');
		expect(panel.children[2].classList[1]).toBe('custom-class-footer');
	});

	test('Render header with title', () => {
		const {container} = render(
			<Panel>
				<Panel.Header title={'Lorem Ipsum'}>{'Header'}</Panel.Header>
			</Panel>
		);

		const panelHeader = container.querySelector('.panel-header');

		expect(panelHeader.children[0]).toHaveTextContent('Lorem Ipsum');
		expect(panelHeader.children[1]).toHaveTextContent('Header');
	});
});
