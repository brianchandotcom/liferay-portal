/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import Numeric from '../Numeric/Numeric';
import Select from '../Select/Select.es';
import Text from '../Text/Text.es';
import {EVENT_TYPES} from './validationReducer';

const ValidationTextAndNumeric = ({
	append,
	appendType,
	dataType,
	decimalPlaces,
	dispatch,
	errorMessage,
	inputMask,
	inputMaskFormat,
	localizationMode,
	localizedSymbols,
	localizedValue,
	name,
	onBlur,
	parameter,
	readOnly,
	selectedValidation,
	symbols,
	transformSelectedValidation,
	validations,
	visible,
}) => {
	const DynamicComponent =
		selectedValidation &&
		selectedValidation.parameterMessage &&
		dataType === 'string'
			? Text
			: Numeric;

	return (
		<>
			<Select
				disableEmptyOption
				label={Liferay.Language.get('accept-if-input')}
				name="selectedValidation"
				onChange={(event, value) => {
					dispatch({
						payload: {
							selectedValidation: transformSelectedValidation(
								value
							),
						},
						type: EVENT_TYPES.CHANGE_SELECTED_VALIDATION,
					});
				}}
				options={validations}
				placeholder={Liferay.Language.get('choose-an-option')}
				readOnly={readOnly || localizationMode}
				showEmptyOption={false}
				value={[selectedValidation.name]}
				visible={visible}
			/>

			{selectedValidation.parameterMessage && (
				<DynamicComponent
					{...(dataType !== 'string' && {
						append,
						appendType,
						decimalPlaces,
						inputMask,
						inputMaskFormat,
						localizedSymbols,
						symbols,
					})}
					dataType={dataType}
					label={Liferay.Language.get('value')}
					name={`${name}_parameter`}
					onChange={(event) => {
						let parameter = event.target.value;

						if (dataType === 'double') {
							const decimalSymbol = parameter.match(/[^-\d]/g);

							parameter = decimalSymbol
								? parameter.replace(decimalSymbol[0], '.')
								: parameter;
						}

						dispatch({
							payload: {
								parameter,
							},
							type: EVENT_TYPES.SET_PARAMETER,
						});
					}}
					placeholder={selectedValidation.parameterMessage}
					readOnly={readOnly}
					required={false}
					value={localizedValue(parameter)}
					visible={visible}
				/>
			)}

			<Text
				label={Liferay.Language.get('error-message')}
				name={`${name}_errorMessage`}
				onBlur={onBlur}
				onChange={(event) => {
					dispatch({
						payload: {
							errorMessage: event.target.value,
						},
						type: EVENT_TYPES.CHANGE_ERROR_MESSAGE,
					});
				}}
				placeholder={Liferay.Language.get('this-field-is-invalid')}
				readOnly={readOnly}
				required={false}
				value={localizedValue(errorMessage)}
				visible={visible}
			/>
		</>
	);
};

export default ValidationTextAndNumeric;
