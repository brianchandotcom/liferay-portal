/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData} from '../../types/layout_data/LayoutData';
import {FragmentEntryLink} from './addFragmentEntryLinks';
import {SWAP_FRAGMENT} from './types';

type Props = {
	fragmentEntryLink: FragmentEntryLink;
	fragmentEntryLinkId: FragmentEntryLink['fragmentEntryLinkId'];
	layoutData: LayoutData;
	previousFragmentEntryKey: FragmentEntryLink['fragmentEntryKey'];
};

export default function swapFragment({
	fragmentEntryLink,
	fragmentEntryLinkId,
	layoutData,
	previousFragmentEntryKey,
}: Props) {
	return {
		fragmentEntryLink,
		fragmentEntryLinkId,
		layoutData,
		previousFragmentEntryKey,
		type: SWAP_FRAGMENT,
	} as const;
}
