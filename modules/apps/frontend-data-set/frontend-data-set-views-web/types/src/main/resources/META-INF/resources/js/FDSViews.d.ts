/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

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
