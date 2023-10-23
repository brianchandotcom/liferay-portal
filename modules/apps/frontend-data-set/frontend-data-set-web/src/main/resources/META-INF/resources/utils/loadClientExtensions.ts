/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {loadModule} from 'frontend-js-web';

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
) {
	for (const {cxDefinitions, onLoad} of cxDefinitionsHandlers) {
		if (!cxDefinitions.length) {
			continue;
		}

		const promises = cxDefinitions.map(({context, importDeclaration}) => {
			return loadModule(importDeclaration).then((binding) => ({
				binding,
				context,
			}));
		});

		Promise.all(promises).then(onLoad);
	}
}
