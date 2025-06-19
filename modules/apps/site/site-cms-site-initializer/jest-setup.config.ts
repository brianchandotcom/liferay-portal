/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

class MockBroadcastChannel {
	name: string;
	onmessage: ((event: MessageEvent) => void) | null = null;
	private listeners: ((event: MessageEvent) => void)[] = [];

	constructor(name: string) {
		this.name = name;
	}

	postMessage(message: any) {
		const event = {data: message} as MessageEvent;
		this.listeners.forEach((listener) => listener(event));
	}

	addEventListener(_: string, listener: (event: MessageEvent) => void) {
		this.listeners.push(listener);
	}

	removeEventListener(_: string, listener: (event: MessageEvent) => void) {
		this.listeners = this.listeners.filter((l) => l !== listener);
	}

	close() {
		this.listeners = [];
	}
}

(globalThis as any).BroadcastChannel = MockBroadcastChannel;
