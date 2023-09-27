/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
