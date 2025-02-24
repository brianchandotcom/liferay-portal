/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as http from 'http';

	import {ObjectActionApi} from './objectActionApi';
	import {ObjectDefinitionApi} from './objectDefinitionApi';
	import {ObjectFieldApi} from './objectFieldApi';
	import {ObjectFolderApi} from './objectFolderApi';
	import {ObjectLayoutApi} from './objectLayoutApi';
	import {ObjectRelationshipApi} from './objectRelationshipApi';
	import {ObjectValidationRuleApi} from './objectValidationRuleApi';
	import {ObjectViewApi} from './objectViewApi';

	export * from './objectActionApi';
	export * from './objectDefinitionApi';
	export * from './objectFieldApi';
	export * from './objectFolderApi';
	export * from './objectLayoutApi';
	export * from './objectRelationshipApi';
	export * from './objectValidationRuleApi';
	export * from './objectViewApi';

/**
 * @author Javier Gamarra
 * @generated
 */

export class HttpError extends Error {
	constructor (
		public response: http.IncomingMessage,
		public body: any,
		public statusCode?: number
	) {
		super('HTTP request failed');
		this.name = 'HttpError';
	}
}

export const APIS = [ObjectActionApi, ObjectDefinitionApi, ObjectFieldApi, ObjectFolderApi, ObjectLayoutApi, ObjectRelationshipApi, ObjectValidationRuleApi, ObjectViewApi];