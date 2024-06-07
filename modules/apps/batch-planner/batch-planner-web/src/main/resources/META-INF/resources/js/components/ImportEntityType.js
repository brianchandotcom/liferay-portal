/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelect} from '@clayui/form';
import React, {useEffect, useReducer} from 'react';

import {
	CSV_FORMAT,
	DISALLOWED_CSV_ENTITY_TYPES,
	FILE_EXTENSION_EVENT,
} from '../constants';

function ImportEntityType({
	internalClassNameKeyId,
	internalClassNameKeyInitialOptions,
	internalClassNameKeyLabel,
	internalClassNameKeyName,
	portletNamespace,
}) {
	const [internalClassNameKeyOptions, dispatchInternalClassNameKeyOptions] =
		useReducer((state, fileExtension) => {
			if (fileExtension === CSV_FORMAT) {
				return internalClassNameKeyInitialOptions.filter(
					(item) => !DISALLOWED_CSV_ENTITY_TYPES.includes(item.value)
				);
			}
			else {
				return internalClassNameKeyInitialOptions;
			}
		}, internalClassNameKeyInitialOptions);

	useEffect(() => {
		function handleFileExtensionUpdate({fileExtension}) {
			dispatchInternalClassNameKeyOptions(fileExtension);
		}

		Liferay.on(FILE_EXTENSION_EVENT, handleFileExtensionUpdate);

		return () => {
			Liferay.detach(FILE_EXTENSION_EVENT, handleFileExtensionUpdate);
		};
	}, [portletNamespace]);

	return (
		<div className="form-group">
			<label htmlFor={internalClassNameKeyId}>
				{internalClassNameKeyLabel}
			</label>

			<ClaySelect
				aria-label={internalClassNameKeyLabel}
				id={internalClassNameKeyId}
				name={internalClassNameKeyName}
			>
				{internalClassNameKeyOptions.map((item) => (
					<ClaySelect.Option
						key={item.value}
						label={item.label}
						value={item.value}
					/>
				))}
			</ClaySelect>
		</div>
	);
}

export default ImportEntityType;
