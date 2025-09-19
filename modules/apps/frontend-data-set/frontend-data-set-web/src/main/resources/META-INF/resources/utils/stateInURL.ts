/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EStateInURLSettings, IStateInURL} from './types';

function getStateParamName(id: string): string {
	return `${id}_fdsState`;
}

export function readStateFromURL(id: string): Partial<IStateInURL> | null {
	if (!Liferay.FeatureFlags['LPD-22473']) {
		return null;
	}

	const params = new URLSearchParams(window.location.search);

	const stateParam = params.get(getStateParamName(id));

	if (!stateParam) {
		return null;
	}

	let state = {};

	try {
		state = JSON.parse(stateParam);
	}
	catch (error) {
		return null;
	}

	return state;
}

export function writeStateInURL(
	id: string,
	state: Partial<IStateInURL>,
	settings: EStateInURLSettings
) {
	if (
		!state ||
		settings === EStateInURLSettings.OFF ||
		!Liferay.FeatureFlags['LPD-22473']
	) {
		return;
	}

	const currentState = readStateFromURL(id);

	if (contains(state, currentState)) {
		return;
	}

	Object.keys(state).forEach((key: string) => {
		const stateKey: keyof IStateInURL = key as keyof IStateInURL;

		if (state[stateKey] === undefined) {
			delete state[stateKey];

			if (currentState && currentState[stateKey]) {
				delete currentState[stateKey];
			}
		}
	});

	const params = new URLSearchParams(window.location.search);

	params.set(
		getStateParamName(id),
		JSON.stringify({...(currentState || {}), ...state})
	);

	const path = `${window.location.pathname}?${params.toString()}`;

	const replaceState =
		settings === EStateInURLSettings.REPLACE || !currentState;

	if (Liferay.SPA && Liferay.SPA.app) {
		Liferay.SPA.app.browserPathBeforeNavigate = path;

		Liferay.SPA.app.updateHistory_(
			document.title,
			path,
			{
				...window.history.state,
				path,
				redirectPath: path,
				senna: true,
			},
			replaceState
		);

		return;
	}

	if (replaceState) {
		window.history.replaceState(
			{...window.history.state},
			document.title,
			path
		);
	}
	else {
		window.history.pushState(
			{...window.history.state},
			document.title,
			path
		);
	}
}

export function contains(
	a: Partial<IStateInURL> | null,
	b: Partial<IStateInURL> | null
) {
	if (a === null || b === null) {
		return false;
	}

	return deepContains(a, b);
}

function deepContains(subset: any, superset: any) {
	if (typeof subset !== 'object' || subset === null) {
		return subset === superset;
	}

	if (typeof superset !== 'object' || superset === null) {
		return false;
	}

	if (
		Array.isArray(subset) &&
		Array.isArray(superset) &&
		(subset.length > superset.length || !subset.length)
	) {
		return false;
	}

	for (const key of Object.keys(subset)) {
		if (!Object.prototype.hasOwnProperty.call(superset, key)) {
			return false;
		}

		if (!deepContains(subset[key], superset[key])) {
			return false;
		}
	}

	return true;
}
