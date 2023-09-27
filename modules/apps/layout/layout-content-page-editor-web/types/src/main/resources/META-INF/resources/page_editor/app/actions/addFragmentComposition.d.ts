/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface FragmentComposition {
	fragmentCollectionId: string;
	fragmentCollectionName: string;
	fragmentEntryKey: string;
	groupId: string;
	icon: string;
	imagePreviewURL: string;
	name: string;
	type: 'composition';
}
export default function addFragmentComposition({
	fragmentCollectionId,
	fragmentComposition,
}: {
	fragmentCollectionId: string;
	fragmentComposition: FragmentComposition;
}): {
	readonly fragmentCollectionId: string;
	readonly fragmentComposition: FragmentComposition;
	readonly type: 'ADD_FRAGMENT_COMPOSITION';
};
