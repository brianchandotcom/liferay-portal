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

import {ClayInput} from '@clayui/form';
import React, {useState} from 'react';

const FileNamePicker = ({namespace, validExtensions}) => {
	const inputId = namespace + 'file';
	const [inputValue, setInputValue] = useState('');

	const onInputChange = ({target}) => {
		setInputValue(target.value);

		window[`${namespace}updateFileNameAndTitle`]();
	};

	return (
		<div className="form-group">
			<label className="btn btn-secondary" htmlFor={inputId}>
				{Liferay.Language.get('select-file')}
			</label>

			<ClayInput
				accept={validExtensions}
				className="d-none"
				id={inputId}
				name={inputId}
				onChange={onInputChange}
				type="file"
				value={inputValue}
			/>
		</div>
	);
};

export default FileNamePicker;
