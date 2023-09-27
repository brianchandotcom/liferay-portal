/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import toggleControls from '../../../src/main/resources/META-INF/resources/liferay/util/toggle_controls';

describe('Liferay.Util.toggleControls', () => {
	Liferay = {
		Util: {
			getLexiconIcon: jest.fn(() => global),
			setSessionValue: jest.fn(),
		},
		fire: jest.fn(),
		on: (_eventName, callback) => {
			callback();
		},
	};

	beforeEach(() => {
		const trigger = document.createElement('button');
		const icon = document.createElement('span');

		icon.classList.add('lexicon-icon');
		trigger.classList.add('toggle-controls');

		document.body.appendChild(trigger);
		document.body.appendChild(icon);
	});

	it('adds the class to the body corresponding to the current state', () => {
		toggleControls(document.body);

		expect(document.body.classList.contains('controls-hidden')).toBe(true);
	});

	it('consumes the toggleControls event', () => {
		toggleControls(document.body);

		const spy = jest.fn();

		Liferay.on('toggleControls', spy);

		expect(spy).toHaveBeenCalled();
	});
});
