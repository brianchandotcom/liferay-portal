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

import React, {useCallback, useContext} from 'react';

import InfoItemService from '../services/InfoItemService';

const INITIAL_STATE = {
	collectionFields: null,
	collectionItem: null,
	fromControlsId: itemId => itemId,
	toControlsId: controlId => controlId,
};

const ControlsIdConverterContext = React.createContext(INITIAL_STATE);

const ControlsIdConverterContextProvider = ControlsIdConverterContext.Provider;

const useFromControlsId = () => {
	const context = useContext(ControlsIdConverterContext);

	return context.fromControlsId;
};

const useToControlsId = () => {
	const context = useContext(ControlsIdConverterContext);

	return context.toControlsId;
};

const useCollectionFields = () => {
	const context = useContext(ControlsIdConverterContext);

	return context.collectionFields;
};

const useGetFieldValue = () => {
	const context = useContext(ControlsIdConverterContext);

	const getFromServer = useCallback(
		({classNameId, classPK, fieldId, languageId}) =>
			InfoItemService.getAssetFieldValue({
				classNameId,
				classPK,
				fieldId,
				languageId,
				onNetworkStatus: () => {},
			}).then(response => {
				const {fieldValue = ''} = response;

				return fieldValue;
			}),
		[]
	);

	const getFromCollectionItem = useCallback(
		({collectionFieldId}) =>
			Promise.resolve(context.collectionItem[collectionFieldId]),
		[context.collectionItem]
	);

	if (context.collectionFields !== null && context.collectionItem !== null) {
		return getFromCollectionItem;
	}
	else {
		return getFromServer;
	}
};

export {
	ControlsIdConverterContext,
	ControlsIdConverterContextProvider,
	useCollectionFields,
	useFromControlsId,
	useToControlsId,
	useGetFieldValue,
};
