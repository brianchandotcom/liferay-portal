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

import React, {useState} from 'react';

export const PageHeader = ({
	description: initialDescription,
	placeholder,
	title: initialTitle,
}) => {
	const [description, setDescription] = useState(initialDescription);
	const [title, setTitle] = useState(initialTitle);

	return (
		<div>
			<input
				className="form-builder-page-header-title form-control p-0"
				defaultValue={title}
				maxLength="120"
				onChange={(event) => {
					setTitle(event.target.value);
				}}
				placeholder={placeholder}
				value={title}
			/>
			<input
				className="form-builder-page-header-description form-control p-0"
				defaultValue={description}
				maxLength="120"
				onChange={(event) => {
					setDescription(event.target.value);
				}}
				placeholder={Liferay.Language.get(
					'add-a-short-description-for-this-page'
				)}
				value={description}
			/>
		</div>
	);
};
