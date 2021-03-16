/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useRef, useState} from 'react';

import FieldRow from './FieldRow';

function FieldListInput({
	showBoost,
	configKey,
	defaultValue = [],
	disabled,
	id,
	indexFields,
	initialValue,
	onChange,
	typeOptions,
}) {
	const idCounter = useRef(0);

	const [value, setValue] = useState(
		(initialValue || defaultValue).map((item) => ({
			...item,
			id: idCounter.current++,
		}))
	);

	const _handleBlur = () => {
		onChange(
			configKey,
			value.map((item) => ({
				boost: item.boost,
				field: item.field,
				languageIdPosition: item.languageIdPosition,
				locale: item.locale,
			}))
		); // Removes temporary ID
	};

	const _handleChange = (index) => (newValue) => {
		setValue(
			value.map((field, i) =>
				index === i ? {...field, ...newValue} : field
			)
		); // Filters through values and replace the modified index with the new value
	};

	const _handleFieldRowAdd = () => {
		setValue([...value, {field: '', id: idCounter.current++}]);
	};

	const _handleFieldRowDelete = (index) => () => {
		setValue([...value.filter((_, i) => index !== i)]);
	};

	return (
		<div className="field">
			{value.map((item, index) => (
				<FieldRow
					boost={item.boost}
					configKey={configKey}
					defaultValue={defaultValue}
					disabled={disabled}
					field={item.field}
					id={`${id}_${index}`}
					index={index}
					indexFields={indexFields}
					key={item.id}
					languageIdPosition={item.languageIdPosition}
					locale={item.locale}
					onBlur={_handleBlur}
					onChange={_handleChange(index)}
					onDelete={_handleFieldRowDelete(index)}
					showBoost={showBoost}
					typeOptions={typeOptions}
				/>
			))}

			<ClayForm.Group className="add-remove-field">
				<ClayButton.Group spaced>
					<ClayButton
						aria-label={Liferay.Language.get('add-field')}
						disabled={disabled}
						displayType="secondary"
						monospaced
						onClick={_handleFieldRowAdd}
						small
					>
						<ClayIcon symbol="plus" />
					</ClayButton>
				</ClayButton.Group>
			</ClayForm.Group>
		</div>
	);
}

export default React.memo(FieldListInput);
