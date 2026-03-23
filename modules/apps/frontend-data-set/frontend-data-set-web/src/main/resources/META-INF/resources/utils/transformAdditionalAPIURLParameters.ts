/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {deepClone} from 'frontend-js-web';

import {TLoadDataParams} from './types';

export function transformAdditionalAPIURLParameters(
	loadDataParams: TLoadDataParams,
	transformer?: (loadDataParams: TLoadDataParams) => string | undefined
): string | undefined {
	if (transformer) {
		return transformer(deepClone(loadDataParams));
	}

	return loadDataParams.additionalAPIURLParameters;
}
