/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
