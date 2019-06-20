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

import {updateField} from './fieldEditedHandler.es';

export const handleFieldBlurred = (state, event) => {
	const {propertyName, propertyValue} = event;
	let newState = {pages: state.pages};

	if (propertyName === 'name' && propertyValue === '') {
		newState = updateField(state, propertyName, propertyValue);
	}

	return newState;
};

export default handleFieldBlurred;
