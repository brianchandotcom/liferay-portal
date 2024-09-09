/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import React, {useMemo} from 'react';

import FieldBase from '../FieldBase/ReactFieldBase.es';
import {useSyncValue} from '../hooks/useSyncValue.es';
import {setJSONArrayValue} from '../util/setters.es';

import './Radio.scss';

const KEYCODES = {
	TAB: 9,
};

const Radio = ({
	editingLanguageId,
	inline,
	name,
	onBlur,
	onChange,
	onFocus,
	options = [
		{
			label: 'Option 1',
			value: 'option1',
		},
		{
			label: 'Option 2',
			value: 'option2',
		},
	],
	predefinedValue,
	readOnly: disabled,
	value: initialValue,
	...otherProps
}) => {
	const predefinedValueMemo = useMemo(() => {
		if (typeof predefinedValue === 'string') {
			return predefinedValue;
		}

		const predefinedValueJSONArray =
			setJSONArrayValue(predefinedValue) || [];

		return predefinedValueJSONArray[0];
	}, [predefinedValue]);

	const [currentValue, setCurrentValue] = useSyncValue(
		initialValue !== undefined &&
			initialValue !== null &&
			initialValue !== ''
			? initialValue.toString()
			: predefinedValueMemo,
		true,
		editingLanguageId
	);

	return (
		<FieldBase {...otherProps} name={name} readOnly={disabled}>
			<div className="ddm__radio" onBlur={onBlur} onFocus={onFocus}>
				<ClayRadioGroup
					inline={inline}
					onChange={(value) => {
						setCurrentValue(value);
						onChange({target: {value}});
					}}
					onKeyUp={(event) => {
						if (!currentValue && event.keyCode === KEYCODES.TAB) {
							const value = options[0].value;

							setCurrentValue(value);
							onChange({target: {value}});
						}
					}}
					value={currentValue}
				>
					{options.map((option, index) => (
						<ClayRadio
							aria-required={otherProps.required}
							containerProps={{
								'data-checked': currentValue === option.value,
							}}
							data-option-reference={option.reference}
							disabled={disabled}
							key={option.value}
							label={option.label}
							name={`${name}_${index}`}
							value={option.value}
						/>
					))}
				</ClayRadioGroup>
			</div>

			<input name={name} type="hidden" value={currentValue} />
		</FieldBase>
	);
};

export default Radio;
