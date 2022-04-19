/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
