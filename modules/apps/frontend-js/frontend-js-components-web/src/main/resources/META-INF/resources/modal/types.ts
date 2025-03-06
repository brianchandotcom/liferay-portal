/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface Disposable {
	dispose: () => void;
}

export interface EventHandler {
	(event?: Event): void;
}

export interface IframeWindow extends Window {
	Liferay: {
		componentReady<T>(id: string): Promise<T>;
		SPA: unknown;
		on: (
			events: string | string[],
			callback?: () => void
		) => Liferay.EventHandler;
	};
}

export interface SearchContainer {
	select: {
		_getAllElements: (b: boolean) => SearchContainerElements;
		getAllSelectedElements: () => SearchContainerElements;
	};
}

export interface SearchContainerElements {
	getDOMNodes: () => SearchContainerNode[];
}

export interface SearchContainerNode extends Element {
	checked?: boolean;
	dataset?: DOMStringMap;
	value: string;
}
