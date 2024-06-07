/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {ReactNode} from 'react';

const SPLIT_REGEX = /({\d+})/g;

export default function sub<Params extends Array<string | number | ReactNode>>(
	langKey: string,
	...params: Params
) {
	const paramList = params.reduce(
		(paramList: ReactNode[], param) =>
			Array.isArray(param)
				? [...paramList, ...param]
				: [...paramList, param],
		[]
	) as ReactNode[];

	const keyArray: ReactNode[] = langKey
		.split(SPLIT_REGEX)
		.filter((val) => val.length !== 0);

	for (let i = 0; i < paramList.length; i++) {
		const param = paramList[i];

		const indexKey = `{${i}}`;

		let paramIndex = keyArray.indexOf(indexKey);

		while (paramIndex >= 0) {
			keyArray.splice(paramIndex, 1, param);

			paramIndex = keyArray.indexOf(indexKey);
		}
	}

	return (
		keyArray.some((value) => value && typeof value === 'object')
			? keyArray
			: keyArray.join('')
	) as Params extends ReactNode[] ? ReactNode[] : string;
}
