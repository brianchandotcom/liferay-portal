/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export * from './objectActionApi';
import { ObjectActionApi } from './objectActionApi';
export * from './objectDefinitionApi';
import { ObjectDefinitionApi } from './objectDefinitionApi';
export * from './objectFieldApi';
import { ObjectFieldApi } from './objectFieldApi';
export * from './objectFolderApi';
import { ObjectFolderApi } from './objectFolderApi';
export * from './objectLayoutApi';
import { ObjectLayoutApi } from './objectLayoutApi';
export * from './objectRelationshipApi';
import { ObjectRelationshipApi } from './objectRelationshipApi';
export * from './objectValidationRuleApi';
import { ObjectValidationRuleApi } from './objectValidationRuleApi';
export * from './objectViewApi';
import { ObjectViewApi } from './objectViewApi';

import * as http from 'http';
export class HttpError extends Error {
	constructor (public response: http.IncomingMessage, public body: any, public statusCode?: number) {
		super('HTTP request failed');
		this.name = 'HttpError';
	}
}

export const APIS = [ObjectActionApi, ObjectDefinitionApi, ObjectFieldApi, ObjectFolderApi, ObjectLayoutApi, ObjectRelationshipApi, ObjectValidationRuleApi, ObjectViewApi];