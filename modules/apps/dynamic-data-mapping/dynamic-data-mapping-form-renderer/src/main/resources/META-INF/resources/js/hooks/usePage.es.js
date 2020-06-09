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

import {useResource} from '@clayui/data-provider';
import {fetch} from 'frontend-js-web';
import React, {useContext, useEffect, useState} from 'react';

const PageContext = React.createContext({});

export const EVENT_TYPES = {
	CHANGE_ACTIVE_PAGE: 'activePageUpdated',
	FIELD_BLUR: 'fieldBlurred',
	FIELD_CHANGE: 'fieldEdited',
	FIELD_DROP: 'FIELD_DROP',
	FIELD_FOCUS: 'fieldFocused',
	PAGE_ADDED: 'pageAdded',
	PAGE_DELETED: 'pageDeleted',
	PAGE_RESET: 'pageReset',
	PAGE_SWAPPED: 'pagesSwapped',
	PAGINATION: 'paginationItemClicked',
	PAGINATION_NEXT: 'paginationNextClicked',
	PAGINATION_PREVIOUS: 'paginationPreviousClicked',
	REMOVED: 'fieldRemoved',
	REPEATED: 'fieldRepeated',
	SUCCESS_CHANGED: 'successPageChanged',
};

const endpoint = `${window.location.origin}/o/dynamic-data-mapping-form-field-types`;

const HEADERS = {
	Accept: 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
};

export const PageProvider = ({children, dispatch, emit, value}) => {
	const [store, setStore] = useState(value);

	// `useResource` is unmounted all the time in the Sidebar, to avoid
	// making new requests every time someone changes tabs and closing
	// the Sidebar we must couple a volume from the global store of the
	// application to the storage of` useResource` to persist the cache
	// in the application.

	const {resource: fieldTypes} = useResource({
		link: (variables) =>
			fetch(`${endpoint}?${variables}`, {headers: HEADERS}).then((res) =>
				res.json()
			),
		variables: {
			p_auth: Liferay.authToken,
		},
	});

	useEffect(() => {
		setStore(value);
	}, [value]);

	const onDispatch = ({payload, type}) => {
		switch (type) {
			case EVENT_TYPES.CHANGE_ACTIVE_PAGE:
			case EVENT_TYPES.FIELD_DROP:
			case EVENT_TYPES.PAGE_ADDED:
			case EVENT_TYPES.PAGE_DELETED:
			case EVENT_TYPES.PAGE_RESET:
			case EVENT_TYPES.PAGE_SWAPPED:
			case EVENT_TYPES.PAGINATION:
			case EVENT_TYPES.REMOVED:
			case EVENT_TYPES.REPEATED:
			case EVENT_TYPES.SUCCESS_CHANGED:
				dispatch(type, payload);
				break;
			case EVENT_TYPES.PAGINATION_NEXT:
			case EVENT_TYPES.PAGINATION_PREVIOUS:
				dispatch(type);
				break;
			case EVENT_TYPES.FIELD_BLUR:
			case EVENT_TYPES.FIELD_CHANGE:
			case EVENT_TYPES.FIELD_FOCUS:
				emit(type, payload);
				break;
			default:
				throw new TypeError('The type does not exist');
		}
	};

	return (
		<PageContext.Provider
			value={{dispatch: onDispatch, store: {...store, fieldTypes}}}
		>
			{children}
		</PageContext.Provider>
	);
};

export const usePage = () => {
	return useContext(PageContext);
};
