/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type Props = {
	cssClasses?: string[];
	fragmentConfig?: Record<string, any>;
	fragmentFields?: FragmentField[];
	id: string;
	key: string;
	pageElements?: PageElement[];
};

export default function getFragmentDefinition({
	cssClasses = [],
	fragmentConfig = {},
	fragmentFields,
	id,
	key,
	pageElements,
}: Props): PageElement {
	return {
		definition: {
			cssClasses,
			fragment: {
				key,
			},
			fragmentConfig,
			fragmentFields,
		},
		id,
		pageElements,
		type: 'Fragment',
	};
}
