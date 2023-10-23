/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface CXDefinition<T> {
	context: T;
	importDeclaration: string;
}
interface CXDefinitionsHandlerItem<T> {
	binding: any;
	context: T;
}
interface CXDefinitionsHandler<T> {
	onLoad(items: CXDefinitionsHandlerItem<T>[]): void;
	cxDefinitions: CXDefinition<T>[];
}
export default function loadClientExtensions(
	cxDefinitionsHandlers: CXDefinitionsHandler<unknown>[]
): void;
export {};
