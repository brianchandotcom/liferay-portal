/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React, {createContext, useContext, useReducer} from 'react';
import reducer from './reducer';

const dxpActivationKeysTableContext = createContext();

export default function DxpActivationKeysTableContext({children}) {
	const [state, dispatch] = useReducer(reducer, {
		activationKeys: [],
		activationKeysFilteredByConditions: [],
		toSearchAndFilterKeys: {
			complimentary: [],
			dne: [],
			expirationDate: [],
			licenseEntryType: [],
			maxClusterNodes: [],
			productName: [],
			productVersion: [],
			sizing: [],
			startDate: [],
			status: [],
			toSearchTerm: [],
		},

		wasFiltered: false,
		wasSearched: false,
	});

	return (
		<dxpActivationKeysTableContext.Provider value={[state, dispatch]}>
			{children}
		</dxpActivationKeysTableContext.Provider>
	);
}

const useActivationKeys = () => useContext(dxpActivationKeysTableContext);

export {useActivationKeys};
