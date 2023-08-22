/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction, createContext} from 'react';

interface APIBuilderContext {
	fetchedData: FetchedData;
	setFetchedData: Dispatch<SetStateAction<FetchedData>>;
	setHideManagementButtons: Dispatch<SetStateAction<boolean>>;
	setIsDataUnsaved: Dispatch<SetStateAction<boolean>>;
}

export const EditAPIApplicationContext = createContext<APIBuilderContext>({
	fetchedData: {},
	setFetchedData: () => {},
	setHideManagementButtons: () => {},
	setIsDataUnsaved: () => {},
});
