/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface OnExitCallback {
	(): void;
}

const callbacks: OnExitCallback[] = [];

const SIGNALS = {
	SIGHUP: 1,
	SIGINT: 2,
	SIGQUIT: 3,
	SIGTERM: 15,
	exit: -1,
};

Object.keys(SIGNALS).forEach((signal) => {
	process.on(signal, handleSignal);
});

function handleSignal(signal: number): void {
	for (const callback of callbacks) {
		try {
			callback();
		}
		catch (error) {
			console.error(
				`Caught error in onExit callback ${callback}: ${error}`
			);
		}
	}

	if (signal !== -1) {
		process.exit(128 + SIGNALS[signal]);
	}
}

export default function onExit(callback: OnExitCallback): void {
	callbacks.push(callback);
}
