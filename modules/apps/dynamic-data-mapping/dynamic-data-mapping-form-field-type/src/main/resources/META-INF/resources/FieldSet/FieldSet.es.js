/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './FieldSet.scss';

import {Layout, getRepeatedIndex, usePage} from 'data-engine-js-components-web';
import React, {useMemo} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import Panel from './Panel.es';

const getRowsArray = (rows) => {
	if (typeof rows === 'string') {
		try {
			return JSON.parse(rows);
		}
		catch (error) {
			return [];
		}
	}

	return rows;
};

const getRows = (rows, nestedFields) => {
	const normalizedRows = getRowsArray(rows);

	return normalizedRows.map((row) => ({
		...row,
		columns: row.columns.map((column) => {
			return {
				...column,
				fields: nestedFields
					.map((field, index) => ({
						...field,
						nestedFieldIndex: index,
					}))
					.filter((nestedField) =>
						column.fields.includes(nestedField.fieldName)
					),
			};
		}),
	}));
};

const FieldSet = ({
	collapsible,
	ddmStructureId,
	itemPath,
	label,
	name,
	nestedFields = [],
	readOnly,
	repeatable,
	rows,
	showLabel,
	type,
	...otherProps
}) => {
	let belongsToFieldSet = false;
	let fieldInsidePage = null;

	const isFieldsGroup = type === 'fieldset' && !ddmStructureId;
	const {editable, page} = usePage();
	const repeatedIndex = useMemo(() => getRepeatedIndex(name), [name]);

	const findFieldInsidePage = (fields) =>
		fields?.find((field) => {
			if (!belongsToFieldSet) {
				belongsToFieldSet = !!field.ddmStructureId;
			}

			return field.name === name
				? field
				: findFieldInsidePage(field.nestedFields);
		});

	if (isFieldsGroup) {
		page.rows.forEach((row) => {
			row.columns.forEach((column) => {
				if (!fieldInsidePage) {
					belongsToFieldSet = false;
					fieldInsidePage = findFieldInsidePage(column.fields);
				}
			});
		});
	}

	return (
		<FieldBase
			{...otherProps}
			itemPath={itemPath}
			label={label}
			name={name}
			readOnly={readOnly}
			repeatable={collapsible ? false : repeatable}
			required={false}
			showLabel={false}
			tip={null}
			type={type}
		>
			<div className="ddm-field-types-fieldset__nested">
				{showLabel && !collapsible && (
					<>
						<label className="text-uppercase">{label}</label>
						<div className="ddm-field-types-fieldset__nested-separator">
							<hr className="mt-1 separator" />
						</div>
					</>
				)}

				{collapsible ? (
					<Panel
						name={name}
						readOnly={readOnly}
						repeatable={repeatable}
						showRepeatableRemoveButton={
							repeatable && repeatedIndex > 0
						}
						title={label}
					>
						<Layout
							editable={
								editable
									? isFieldsGroup && !belongsToFieldSet
									: editable
							}
							itemPath={itemPath}
							rows={getRows(rows, nestedFields)}
						/>
					</Panel>
				) : (
					<Layout
						editable={
							editable
								? isFieldsGroup && !belongsToFieldSet
								: editable
						}
						itemPath={itemPath}
						rows={getRows(rows, nestedFields)}
					/>
				)}
			</div>
		</FieldBase>
	);
};

export default FieldSet;
