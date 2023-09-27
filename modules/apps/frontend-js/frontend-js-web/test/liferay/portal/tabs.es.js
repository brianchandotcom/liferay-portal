/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	applyTabSelectionDOMChanges,
	showTab,
} from '../../../src/main/resources/META-INF/resources/liferay/portal/tabs.es';
import toCharCode from '../../../src/main/resources/META-INF/resources/liferay/util/to_char_code.es';

describe('Liferay.Portal.Tabs.show', () => {
	it('fires the showTab custom event and executes callback', () => {
		const id = 'tab2';
		const namespace = 'abcd';
		const names = ['tab1', 'tab2'];
		const callback = jest.fn();

		Liferay.fire = jest.fn((type, details) => {
			expect(type).toBe('showTab');
			expect(details.id).toBe(id);
			expect(details.names).toBe(names);
			expect(details.namespace).toBe(namespace);
			expect(details.selectedTab.nodeType).toBe(1);
			expect(details.selectedTabSection.nodeType).toBe(1);
		});

		const namespacedId = namespace + toCharCode(id);

		document.body.innerHTML = `
			<div id="${namespacedId}TabsId"></div>
			<div id="${namespacedId}TabsSection"></div>
		`;

		showTab(namespace, names, id, callback);

		expect(callback).toHaveBeenCalled();
	});
});

describe('Liferay.Portal.Tabs.applyTabSelectionDOMChanges', () => {
	it('applies DOM changes when setting an active tab', () => {
		const id = 'tab2';
		const namespace = 'abcd';
		const names = ['tab1', 'tab2', 'tab3'];

		const ids = [
			namespace + toCharCode(names[0]),
			namespace + toCharCode(names[1]),
			namespace + toCharCode(names[2]),
		];

		document.body.innerHTML = `
			<div>
				<div id="${ids[0]}TabsId">
					<a class="active">First</a>
				</div>
				<div id="${ids[1]}TabsId">
					<a><b>Second</b></a>
				</div>
				<div id="${ids[2]}TabsId">
					<a>Third</a>
				</div>
			<div>
			<div>
				<div id="${ids[0]}TabsSection"></div>
				<div id="${ids[1]}TabsSection" class="hide"></div>
				<div id="${ids[2]}TabsSection" class="hide"></div>
			<div>
			<div id="${namespace}dropdownTitle" class="hide"></div>
		`;

		applyTabSelectionDOMChanges({
			id,
			names,
			namespace,
			selectedTab: document.getElementById(`${ids[1]}TabsId`),
			selectedTabSection: document.getElementById(`${ids[1]}TabsSection`),
		});

		const link1 = document.querySelector(`#${ids[0]}TabsId a`);
		const link2 = document.querySelector(`#${ids[1]}TabsId a`);
		const link3 = document.querySelector(`#${ids[2]}TabsId a`);

		expect(link1.classList.contains('active')).toBe(false);
		expect(link2.classList.contains('active')).toBe(true);
		expect(link3.classList.contains('active')).toBe(false);

		const section1 = document.getElementById(`${ids[0]}TabsSection`);
		const section2 = document.getElementById(`${ids[1]}TabsSection`);
		const section3 = document.getElementById(`${ids[2]}TabsSection`);

		expect(section1.classList.contains('hide')).toBe(true);
		expect(section2.classList.contains('hide')).toBe(false);
		expect(section3.classList.contains('hide')).toBe(true);

		const title = document.getElementById(`${namespace}dropdownTitle`);

		expect(title.innerHTML).toBe('Second');
	});
});
