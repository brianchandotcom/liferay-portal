/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {loadModule} from 'frontend-js-web';

interface ClientExtensionDefinition<T> {
	context: T;
	importDeclaration: string;
}

interface ClientExtensionDefinitionsHandlerItem<T> {
	binding: any;
	context: T;
}

interface ClientExtensionDefinitionsHandler<T> {
	onLoad(items: ClientExtensionDefinitionsHandlerItem<T>[]): void;
	clientExtensionDefinitions: ClientExtensionDefinition<T>[];
}

export default function loadClientExtensions(
	clientExtensionDefinitionsHandlers: ClientExtensionDefinitionsHandler<
		unknown
	>[]
) {
	for (const {
		clientExtensionDefinitions,
		onLoad,
	} of clientExtensionDefinitionsHandlers) {
		if (!clientExtensionDefinitions.length) {
			continue;
		}

		const promises = clientExtensionDefinitions.map(
			({context, importDeclaration}) => {
				return loadModule(importDeclaration).then((binding) => ({
					binding,
					context,
				}));
			}
		);

		Promise.all(promises).then(onLoad);
	}
}
