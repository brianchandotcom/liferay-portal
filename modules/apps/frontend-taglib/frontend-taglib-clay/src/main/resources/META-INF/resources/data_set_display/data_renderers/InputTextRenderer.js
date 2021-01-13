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
import PropType from 'prop-types';
import React from 'react';

function InputTextRenderer({options = {}, updateItem, value}) {
	const {appendText, inputProps, prependText} = options;

	return (
		<ClayInput.Group small>
			{prependText && (
				<ClayInput.GroupItem prepend shrink>
					{prependText}
				</ClayInput.GroupItem>
			)}
			<ClayInput.GroupItem append={appendText} prepend={prependText}>
				<ClayInput
					onChange={(event) => {
						updateItem(event.target.value);
					}}
					value={value ?? ''}
					{...inputProps}
				/>
			</ClayInput.GroupItem>
			{appendText && (
				<ClayInput.GroupItem append shrink>
					<ClayInput.GroupText>{appendText}</ClayInput.GroupText>
				</ClayInput.GroupItem>
			)}
		</ClayInput.Group>
	);
}

InputTextRenderer.propTypes = {
	options: PropType.shape({
		appendText: PropType.string,
		prependText: PropType.string,
	}),
	updateItem: PropType.func.isRequired,
	value: PropType.string,
};

export default InputTextRenderer;
