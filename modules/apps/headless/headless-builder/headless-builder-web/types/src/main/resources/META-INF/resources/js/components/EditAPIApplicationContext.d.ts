/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
interface APIBuilderContext {
	fetchedData: FetchedData;
	isDataUnsaved: boolean;
	setFetchedData: Dispatch<SetStateAction<FetchedData>>;
	setHideManagementButtons: Dispatch<SetStateAction<boolean>>;
	setIsDataUnsaved: Dispatch<SetStateAction<boolean>>;
}
export declare const EditAPIApplicationContext: import('react').Context<APIBuilderContext>;
interface APISchemaContext {
	apiSchemaId: number;
	fetchedSchemaData: FetchedSchemaData;
	objectDefinitionBasePath: string;
	setFetchedSchemaData: Dispatch<SetStateAction<FetchedSchemaData>>;
}
export declare const EditSchemaContext: import('react').Context<APISchemaContext>;
export {};
