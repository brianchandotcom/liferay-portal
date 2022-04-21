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

import getLexiconIcon from './get_lexicon_icon';
import {setSessionValue} from './session.es';

const MAP_TOGGLE_STATE = {
	false: {
		cssClass: 'controls-hidden',
		iconCssClass: 'hidden',
		state: 'hidden',
	},
	true: {
		cssClass: 'controls-visible',
		iconCssClass: 'view',
		state: 'visible',
	},
};

export default function toggleControls(node) {
	const body = document.body;

	node = node._node || body;

	const trigger = node.querySelector('.toggle-controls');

	if (!trigger) {
		return;
	}

	let controlsVisible = Liferay._editControlsState === 'visible';

	let currentState = MAP_TOGGLE_STATE[controlsVisible];

	let icon = trigger.querySelector('.lexicon-icon');

	if (icon) {
		currentState.icon = icon;
	}

	body.classList.add(currentState.cssClass);

	Liferay.fire('toggleControls', {
		enabled: controlsVisible,
	});

	trigger.addEventListener('click', () => {
		controlsVisible = !controlsVisible;

		const previousState = currentState;

		currentState = MAP_TOGGLE_STATE[controlsVisible];

		body.classList.toggle(previousState.cssClass);
		body.classList.toggle(currentState.cssClass);

		const editControlsIconClass = currentState.iconCssClass;
		const editControlsState = currentState.state;

		const newIcon = getLexiconIcon(editControlsIconClass);

		currentState.icon = newIcon;

		icon.replaceWith(newIcon);

		icon = newIcon;

		Liferay._editControlsState = editControlsState;

		setSessionValue(
			'com.liferay.frontend.js.web_toggleControls',
			editControlsState
		);

		Liferay.fire('toggleControls', {
			enabled: controlsVisible,
			src: 'ui',
		});
	});
}
