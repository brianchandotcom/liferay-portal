/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FragmentComposition} from './addFragmentComposition';
import type {FragmentEntryType} from '../config/constants/fragmentEntryTypes';
export interface FragmentEntry {
	fragmentEntryKey: string;
	groupId?: string;
	highlighted: boolean;
	icon: string;
	imagePreviewURL: string;
	name: string;
	type: FragmentEntryType;
}
export interface FragmentSet {
	fragmentCollectionId: string;
	fragmentEntries: Array<FragmentEntry | FragmentComposition>;
	name: string;
}
export default function updateFragments({
	fragments,
}: {
	fragments: FragmentSet[];
}): {
	readonly fragments: FragmentSet[];
	readonly type: 'UPDATE_FRAGMENTS';
};
