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

import React, {createContext, useEffect, useState} from 'react';

import {getItem} from '../../utils/client.es';

const PermissionsContext = createContext();

const ACTIONS = {
	ADD_DATA_RECORD: 'ADD_DATA_RECORD',
	DELETE_DATA_RECORD: 'DELETE_DATA_RECORD',
	UPDATE_DATA_RECORD: 'UPDATE_DATA_RECORD',
	VIEW: 'VIEW',
	VIEW_DATA_RECORD: 'VIEW_DATA_RECORD'
};

const PermissionsContextProvider = ({children, dataDefinitionId}) => {
	const [actionIds, setActionIds] = useState([]);

	useEffect(() => {
		getItem(
			`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-record-collection`
		)
			.then(({id: dataRecordCollectionId}) =>
				getItem(
					`/o/data-engine/v2.0/data-record-collections/${dataRecordCollectionId}/permissions/by-current-user`
				)
			)
			.then(actionIds => setActionIds(actionIds))
			.catch(_ => setActionIds([]));
	}, [dataDefinitionId]);

	return (
		<PermissionsContext.Provider value={actionIds}>
			{children}
		</PermissionsContext.Provider>
	);
};

export {ACTIONS, PermissionsContext, PermissionsContextProvider};
