/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PageDefinition} from '../types/PageDefinition';
import {PageElement} from '../types/PageElement';
import {getRandomId} from './util';

export function getPageDefinition(pageElements: PageElement[]): PageDefinition {
	return {
		pageElement: {
			id: getRandomId(),
			pageElements,
			type: 'Root',
		},
	};
}
