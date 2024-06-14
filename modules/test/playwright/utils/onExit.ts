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
};

// Register handlers for exit and SIGNALS

process.on('exit', handleExit);

Object.keys(SIGNALS).forEach((signal) => {
	process.on(signal, handleSignal);
});

// Rest of functions

function handleExit(status: number): void {
	invokeCallbacks();

	process.exit(status);
}

function handleSignal(signal: string): void {
	invokeCallbacks();

	const exitCode = SIGNALS[signal] || 0;

	process.exit(128 + exitCode);
}

function invokeCallbacks() {
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
}

export default function onExit(callback: OnExitCallback): void {
	callbacks.push(callback);
}
