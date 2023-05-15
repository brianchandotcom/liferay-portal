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

/// <reference types="react" />

import '../css/FDSEntries.scss';
import {OBJECT_RELATIONSHIP} from './Constants';
import {FDSViewType} from './FDSViews';
declare type FDSEntryType = {
	[OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW]: Array<FDSViewType>;
	actions: {
		delete: {
			href: string;
			method: string;
		};
	};
	id: string;
	label: string;
	restApplication: string;
	restEndpoint: string;
	restSchema: string;
};
interface IFDSEntriesInterface {
	fdsViewsURL: string;
	namespace: string;
	restApplications: Array<string>;
}
declare const FDSEntries: ({
	fdsViewsURL,
	namespace,
	restApplications,
}: IFDSEntriesInterface) => JSX.Element;
export {FDSEntryType};
export default FDSEntries;
