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

import React from 'react';

import FieldType from './FieldType.es';

export default ({
	deleteLabel,
	fieldTypes,
	keywords,
	onClick,
	onDelete,
	onDoubleClick
}) => {
	const regex = new RegExp(keywords, 'ig');

	return fieldTypes
		.filter(({system}) => !system)
		.filter(({description, label}) => {
			if (!keywords) {
				return true;
			}

			return regex.test(description) || regex.test(label);
		})
		.map((fieldType, index) => (
			<FieldType
				{...fieldType}
				deleteLabel={deleteLabel}
				key={`${fieldType.name}_${index}`}
				onClick={onClick}
				onDelete={onDelete}
				onDoubleClick={onDoubleClick}
			/>
		));
};
