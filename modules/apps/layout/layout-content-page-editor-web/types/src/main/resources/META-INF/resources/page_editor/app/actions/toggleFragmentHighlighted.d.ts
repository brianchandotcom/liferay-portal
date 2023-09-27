/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {FragmentEntry} from './updateFragments';
export default function toggleFragmentHighlighted({
	fragmentEntryKey,
	groupId,
	highlighted,
	highlightedFragments,
	initiallyHighlighted,
}: {
	fragmentEntryKey: string;
	groupId: string;
	highlighted: boolean;
	highlightedFragments: FragmentEntry[];
	initiallyHighlighted?: boolean;
}): {
	readonly fragmentEntryKey: string;
	readonly groupId: string;
	readonly highlighted: boolean;
	readonly highlightedFragments: FragmentEntry[];
	readonly initiallyHighlighted: boolean | undefined;
	readonly type: 'TOGGLE_FRAGMENT_HIGHLIGHTED';
};
