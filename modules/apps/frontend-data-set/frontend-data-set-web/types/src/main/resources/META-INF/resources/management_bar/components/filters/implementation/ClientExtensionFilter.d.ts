/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {FDSFilter} from '@liferay/js-api/data-set';
import type {FilterImplementation, FilterImplementationArgs} from '../Filter';
export interface ClientExtensionFilterImplementationArgs
	extends FilterImplementationArgs<unknown> {
	clientExtensionFilterImplementation?: FDSFilter<unknown>;
	clientExtensionFilterURL: string;
}
declare const filterImplementation: FilterImplementation<ClientExtensionFilterImplementationArgs>;
export default filterImplementation;
