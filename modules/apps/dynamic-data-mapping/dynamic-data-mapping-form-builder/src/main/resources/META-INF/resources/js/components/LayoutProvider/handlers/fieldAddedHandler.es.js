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

import {
	addField as addFieldToPage,
	createField,
} from '../../../util/fieldSupport.es';

export const addField = (
	props,
	{indexes, newField, pages, parentFieldName}
) => {
	return addFieldToPage({
		indexes,
		newField,
		pages,
		parentFieldName,
		...props,
	});
};

const handleFieldAdded = (props, state, event) => {
	const {data, indexes} = event;
	const {pages} = state;
	const {parentFieldName} = data;

	const newField = event.newField || createField(props, event);

	return addField(props, {
		indexes,
		newField,
		pages,
		parentFieldName,
	});
};

export default handleFieldAdded;
