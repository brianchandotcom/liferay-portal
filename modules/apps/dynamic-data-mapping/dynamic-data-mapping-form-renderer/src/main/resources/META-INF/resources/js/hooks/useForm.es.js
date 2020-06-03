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

import React, {useContext} from 'react';

const FormContext = React.createContext({});

export const EVENT_TYPES = {
	CHANGE_ACTIVE_PAGE: 'activePageUpdated',
	FIELD_BLUR: 'fieldBlurred',
	FIELD_CHANGE: 'fieldEdited',
	FIELD_DROP: 'fieldDrop',
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

export const FormProvider = ({children, on}) => {
	const dispatch = ({payload, type}) => {
		if (!EVENT_TYPES[type]) {
			throw new TypeError('The type does not exist');
		}

		on(type, payload);
	};

	return (
		<FormContext.Provider value={dispatch}>{children}</FormContext.Provider>
	);
};

export const useForm = () => {
	return useContext(FormContext);
};
