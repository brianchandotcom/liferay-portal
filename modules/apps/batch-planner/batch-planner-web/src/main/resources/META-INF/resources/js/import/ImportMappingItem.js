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

import ClayForm, {ClaySelect} from '@clayui/form';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

const ImportMappingItem = ({
	dbField,
	fileFields,
	formEvaluated,
	portletNamespace,
	previewValue,
	required,
	selectedFileField,
	updateFieldMapping,
}) => {
	const inputId = `input-field-${dbField.name}`;
	const hasError = formEvaluated && required && !selectedFileField;

	const hasSuccess = formEvaluated && !hasError;

	return (
		<ClayTable.Row>
			<ClayTable.Cell>
				<label htmlFor={inputId}>{dbField.label}</label>

				{dbField.description && (
					<p className="mb-0">{dbField.description}</p>
				)}
			</ClayTable.Cell>

			<ClayTable.Cell>
				<ClayForm.Group
					className={classNames({
						'has-error': hasError,
						'has-success': hasSuccess,
						'mb-0': true,
					})}
				>
					{selectedFileField && (
						<input
							hidden
							name={`${portletNamespace}internalFieldName_${dbField.name}`}
							readOnly
							value={dbField.name}
						/>
					)}

					<ClaySelect
						aria-required={required}
						disabled={!fileFields}
						id={inputId}
						name={
							selectedFileField &&
							`${portletNamespace}externalFieldName_${dbField.name}`
						}
						onChange={(event) =>
							updateFieldMapping(event.target.value)
						}
						value={selectedFileField}
					>
						<ClaySelect.Option label="" value="" />

						{fileFields &&
							fileFields.map((fileField) => {
								const columnHasNoName =
									typeof fileField === 'number';

								const label = columnHasNoName
									? `${Liferay.Language.get('column')} ${
											fileField + 1
									  }`
									: fileField;

								return (
									<ClaySelect.Option
										key={fileField}
										label={label}
										value={String(fileField)}
									/>
								);
							})}
					</ClaySelect>
				</ClayForm.Group>
			</ClayTable.Cell>

			<ClayTable.Cell>{previewValue}</ClayTable.Cell>
		</ClayTable.Row>
	);
};

ImportMappingItem.propTypes = {
	dbField: PropTypes.shape({
		description: PropTypes.string,
		label: PropTypes.string.isRequired,
		name: PropTypes.string.isRequired,
	}),
	fileFields: PropTypes.arrayOf(
		PropTypes.oneOfType([PropTypes.string, PropTypes.number])
	).isRequired,
	formEvaluated: PropTypes.bool.isRequired,
	portletNamespace: PropTypes.string.isRequired,
	previewValue: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
	required: PropTypes.bool.isRequired,
	selectedFileField: PropTypes.string.isRequired,
	updateFieldMapping: PropTypes.func.isRequired,
};

export default ImportMappingItem;
