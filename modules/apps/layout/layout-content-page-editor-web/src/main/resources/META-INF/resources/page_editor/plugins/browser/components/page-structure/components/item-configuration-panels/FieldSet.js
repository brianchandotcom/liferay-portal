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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import {FRAGMENT_CONFIGURATION_FIELDS} from '../../../../../../app/components/fragment-configuration-fields/index';
import {CONTAINER_WIDTH_TYPES} from '../../../../../../app/config/constants/containerWidthTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../../../app/config/constants/viewportSizes';
import {useSelector} from '../../../../../../app/contexts/StoreContext';
import {getEditableLocalizedValue} from '../../../../../../app/utils/getEditableLocalizedValue';
import isNullOrUndefined from '../../../../../../app/utils/isNullOrUndefined';
import Collapse from '../../../../../../common/components/Collapse';
import CurrentLanguageFlag from '../../../../../../common/components/CurrentLanguageFlag';
import {ConfigurationFieldPropTypes} from '../../../../../../prop-types/index';

const DISPLAY_SIZES = {
	small: 'small',
};

const fieldIsDisabled = (item, field) =>
	item.type === LAYOUT_DATA_ITEM_TYPES.container &&
	item.config?.widthType === CONTAINER_WIDTH_TYPES.fixed &&
	(field.name === 'marginRight' || field.name === 'marginLeft');

export function FieldSet({
	fields,
	item = {},
	label,
	languageId,
	onValueSelect,
	values,
}) {
	const store = useSelector((state) => state);

	const {selectedViewportSize} = store;

	const availableFields =
		selectedViewportSize === VIEWPORT_SIZES.desktop
			? fields
			: fields.filter(
					(field) =>
						field.responsive || field.name === 'backgroundImage'
			  );

	return availableFields.length > 0 && label ? (
		<Collapse label={label} open>
			<FieldSetContent
				fields={availableFields}
				item={item}
				languageId={languageId}
				onValueSelect={onValueSelect}
				values={values}
			/>
		</Collapse>
	) : (
		<FieldSetContent
			fields={availableFields}
			item={item}
			languageId={languageId}
			onValueSelect={onValueSelect}
			values={values}
		/>
	);
}

function FieldSetContent({fields, item, languageId, onValueSelect, values}) {
	return (
		<div className="page-editor__sidebar__fieldset">
			{fields.map((field, index) => {
				const FieldComponent =
					field.type && FRAGMENT_CONFIGURATION_FIELDS[field.type];

				return (
					<div
						className={classNames(
							'autofit-row',
							'page-editor__sidebar__fieldset__field align-items-end',
							{
								'page-editor__sidebar__fieldset__field-small':
									field.displaySize === DISPLAY_SIZES.small,
							}
						)}
						key={index}
					>
						<div className="autofit-col autofit-col-expand">
							<FieldComponent
								disabled={fieldIsDisabled(item, field)}
								field={field}
								onValueSelect={onValueSelect}
								value={getFieldValue({
									field,
									languageId,
									values,
								})}
							/>
						</div>

						{field.localizable && (
							<CurrentLanguageFlag className="ml-2" />
						)}
					</div>
				);
			})}
		</div>
	);
}

function getFieldValue({field, languageId, values}) {
	if (field.name === '') {
		return values;
	}

	const value = values[field.name];

	if (isNullOrUndefined(value)) {
		return field.defaultValue;
	}

	if (!field.localizable || typeof value !== 'object') {
		return value;
	}

	return getEditableLocalizedValue(value, languageId, field.defaultValue);
}

FieldSet.propTypes = {
	fields: PropTypes.arrayOf(PropTypes.shape(ConfigurationFieldPropTypes)),
	item: PropTypes.object,
	label: PropTypes.string,
	languageId: PropTypes.string.isRequired,
	onValueSelect: PropTypes.func.isRequired,
	values: PropTypes.object,
};
