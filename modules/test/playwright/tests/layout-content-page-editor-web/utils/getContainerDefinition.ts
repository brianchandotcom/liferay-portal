/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getRandomString from '../../../utils/getRandomString';

type Props = {
	fragmentStyle?: Record<string, string>;
	id?: string;
	pageElements?: PageElement[];
};

export default function getContainerDefinition({
	id = getRandomString(),
	pageElements = [],
	fragmentStyle,
}: Props): PageElement {
	return {
		definition: {
			fragmentStyle,
			layout: {},
		},
		id,
		pageElements,
		type: 'Section',
	};
}
