/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import '../css/DataSets.scss';
export interface IDataSet {
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
	defaultItemsPerPage: number;
	defaultVisualizationMode: string;
	description: string;
	externalReferenceCode: string;
	id: string;
	label: string;
	listOfItemsPerPage: string;
	restApplication: string;
	restEndpoint: string;
	restSchema: string;
}
declare const DataSets: ({
	editDataSetURL,
	namespace,
	permissionsURL,
	restApplications,
}: {
	editDataSetURL: string;
	namespace: string;
	permissionsURL: string;
	restApplications: Array<string>;
}) => JSX.Element;
export default DataSets;
