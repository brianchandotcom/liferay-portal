/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import toggleDisabled from '../../../src/main/resources/META-INF/resources/liferay/util/toggle_disabled';

describe('Liferay.Uitl.toggleDisabled', () => {
	beforeEach(() => {
		const fragment = document.createDocumentFragment();

		for (let i = 0; i < 10; i++) {
			const button = document.createElement('button');
			button.setAttribute('id', `button-${i + 1}`);
			button.classList.add('test-button');
			fragment.appendChild(button);
		}

		document.body.appendChild(fragment);
	});

	afterEach(() => {
		const buttons = document.querySelectorAll('button.test-button');

		if (buttons) {
			buttons.forEach((button) => {
				document.body.removeChild(button);
			});
		}
	});

	it('sets the `disabled` attribute on a collection of nodes if the `state` argument is passed', () => {
		toggleDisabled('button.test-button', 'disabled');

		const nodes = document.querySelectorAll('button.test-button');

		nodes.forEach((node) => {
			expect(node.disabled).toEqual(true);
		});
	});
});
