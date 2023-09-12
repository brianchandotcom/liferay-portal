/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {LayoutData} from '../../types/LayoutData';
import type {FragmentEntryLinkMap} from './addFragmentEntryLinks';
declare type MasterLayoutOptions =
	| {
			masterLayoutData: null;
			masterLayoutPlid: '0';
	  }
	| {
			masterLayoutData: LayoutData;
			masterLayoutPlid: string;
	  };
export default function changeMasterLayout({
	fragmentEntryLinks,
	masterLayoutData,
	masterLayoutPlid,
}: MasterLayoutOptions & {
	fragmentEntryLinks: FragmentEntryLinkMap;
}): {
	readonly fragmentEntryLinks: FragmentEntryLinkMap;
	readonly masterLayoutData: LayoutData | null;
	readonly masterLayoutPlid: string;
	readonly type: 'CHANGE_MASTER_LAYOUT';
};
export {};
