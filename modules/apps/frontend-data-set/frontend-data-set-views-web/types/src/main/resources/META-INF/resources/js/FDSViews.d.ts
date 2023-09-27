/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {OBJECT_RELATIONSHIP} from './Constants';
import {FDSEntryType} from './FDSEntries';
declare type FDSViewType = {
	[OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW]: FDSEntryType;
	defaultItemsPerPage: number;
	description: string;
	externalReferenceCode: string;
	fdsFiltersOrder: string;
	fdsSortsOrder: string;
	id: string;
	label: string;
	listOfItemsPerPage: string;
};
interface IFDSViewsInterface {
	fdsEntryId: string;
	fdsEntryLabel: string;
	fdsViewURL: string;
	namespace: string;
}
declare const FDSViews: ({
	fdsEntryId,
	fdsEntryLabel,
	fdsViewURL,
	namespace,
}: IFDSViewsInterface) => JSX.Element;
export {FDSViewType};
export default FDSViews;
