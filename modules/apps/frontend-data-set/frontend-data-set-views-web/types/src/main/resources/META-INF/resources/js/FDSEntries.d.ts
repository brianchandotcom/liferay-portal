/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		update: {
			href: string;
			method: string;
		};
	};
	externalReferenceCode: string;
	id: string;
	label: string;
	restApplication: string;
	restEndpoint: string;
	restSchema: string;
};
interface IFDSEntriesInterface {
	fdsViewsURL: string;
	namespace: string;
	permissionsURL: string;
	restApplications: Array<string>;
}
declare const FDSEntries: ({
	fdsViewsURL,
	namespace,
	permissionsURL,
	restApplications,
}: IFDSEntriesInterface) => JSX.Element;
export {FDSEntryType};
export default FDSEntries;
