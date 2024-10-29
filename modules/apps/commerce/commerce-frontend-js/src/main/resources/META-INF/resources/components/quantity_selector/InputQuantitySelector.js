/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {debounce} from 'frontend-js-web';
import React, {forwardRef, useEffect, useRef, useState} from 'react';

import {CP_UNIT_OF_MEASURE_SELECTOR_CHANGED} from '../../utilities/eventsDefinitions';
import {
	getMinQuantity,
	getMultipleQuantity,
	getNumberOfDecimals,
	getProductMaxQuantity,
	isMultiple,
} from '../../utilities/quantities';
import RulesPopover from './RulesPopover';

const getErrors = (
	value,
	min,
	max,
	step,
	precision = 0,
	allowEmptyValue = false
) => {
	const errors = [];

	if (getNumberOfDecimals(value) > precision) {
		errors.push('decimals');
	}

	if (allowEmptyValue) {
		if (value && value < min) {
			errors.push('min');
		}
	}
	else if (!value || value < min) {
		errors.push('min');
	}

	if (max && value > max) {
		errors.push('max');
	}

	if (step > 0 && !isMultiple(value, step, precision)) {
		errors.push('multiple');
	}

	return errors;
};

const InputQuantitySelector = forwardRef(
	(
		{
			alignment,
			allowEmptyValue,
			className,
			disabled,
			max,
			min,
			name,
			namespace,
			onUpdate,
			quantity,
			step,
			unitOfMeasure,
			...props
		},
		inputRef
	) => {
		const [inputProperties, setInputProperties] = useState({
			currentUnitOfMeasure: unitOfMeasure,
			max: unitOfMeasure
				? getProductMaxQuantity(
						max,
						getMultipleQuantity(
							unitOfMeasure.incrementalOrderQuantity,
							step,
							unitOfMeasure.precision
						),
						unitOfMeasure.precision
					)
				: getProductMaxQuantity(Math.ceil(max), Math.ceil(step)),
			min: unitOfMeasure
				? getMinQuantity(
						min,
						getMultipleQuantity(
							unitOfMeasure.incrementalOrderQuantity,
							step,
							unitOfMeasure.precision
						),
						unitOfMeasure.precision
					)
				: getMinQuantity(Math.ceil(min), Math.ceil(step)),
			quantity,
			step: unitOfMeasure
				? getMultipleQuantity(
						unitOfMeasure.incrementalOrderQuantity,
						step,
						unitOfMeasure.precision
					)
				: Math.ceil(step),
		});
		const [showPopover, setShowPopover] = useState(false);
		const [visibleErrors, setVisibleErrors] = useState(() =>
			getErrors(
				quantity,
				inputProperties.min,
				inputProperties.max,
				inputProperties.step,
				unitOfMeasure?.precision ? unitOfMeasure.precision : 0,
				allowEmptyValue
			)
		);
		const isMounted = useIsMounted();
		const debouncedSetVisibleErrorsRef = useRef(
			debounce((newErrors) => {
				if (isMounted()) {
					setVisibleErrors(newErrors);
				}
			}, 500)
		);

		// eslint-disable-next-line react-hooks/exhaustive-deps
		const handleUOMChanged = ({resetQuantity, unitOfMeasure}) => {
			const setStateContext = ({
				max,
				min,
				precision,
				quantity,
				step,
				unitOfMeasure,
			}) => {
				setInputProperties((inputProperties) => ({
					...inputProperties,
					currentUnitOfMeasure: unitOfMeasure,
					max: getProductMaxQuantity(max, step, precision),
					min: getMinQuantity(min, step, precision),
					step,
				}));

				onUpdate({
					errors: getErrors(
						quantity,
						inputProperties.min,
						inputProperties.max,
						step,
						precision,
						allowEmptyValue
					),
					unitOfMeasure,
					value: quantity,
				});
			};

			if (unitOfMeasure) {
				let quantity = inputProperties.quantity;

				if (resetQuantity) {
					quantity = Number(inputProperties.min);

					setInputProperties((inputProperties) => ({
						...inputProperties,
						quantity,
					}));
				}

				setStateContext({
					max,
					min,
					precision: unitOfMeasure.precision,
					quantity,
					step:
						unitOfMeasure.incrementalOrderQuantity === step
							? unitOfMeasure.incrementalOrderQuantity
							: getMultipleQuantity(
									unitOfMeasure.incrementalOrderQuantity,
									step,
									unitOfMeasure.precision
								),
					unitOfMeasure,
				});
			}
			else {
				setStateContext({
					max: Math.ceil(max),
					min: Math.ceil(min),
					precision: 0,
					quantity,
					step: Math.ceil(step),
					unitOfMeasure: null,
				});
			}
		};

		useEffect(() => {
			debouncedSetVisibleErrorsRef.current(() => {
				if (inputProperties.currentUnitOfMeasure) {
					return getErrors(
						inputProperties.quantity,
						inputProperties.min,
						inputProperties.max,
						inputProperties.step,
						inputProperties.currentUnitOfMeasure.precision,
						allowEmptyValue
					);
				}
				else {
					return getErrors(
						inputProperties.quantity,
						inputProperties.min,
						Math.ceil(inputProperties.max),
						Math.ceil(inputProperties.step),
						0,
						allowEmptyValue
					);
				}
			});
		}, [allowEmptyValue, inputProperties]);

		useEffect(() => {
			Liferay.on(
				`${namespace}${CP_UNIT_OF_MEASURE_SELECTOR_CHANGED}`,
				handleUOMChanged
			);

			return () => {
				Liferay.detach(
					`${namespace}${CP_UNIT_OF_MEASURE_SELECTOR_CHANGED}`,
					handleUOMChanged
				);
			};
		}, [handleUOMChanged, namespace]);

		useEffect(() => {
			setInputProperties((prevState) => {
				return {
					...prevState,
					quantity,
				};
			});
		}, [quantity]);

		return (
			<ClayForm.Group
				className={classNames({
					'has-error': visibleErrors.length,
					'mb-0': true,
				})}
			>
				<ClayInput
					className={className}
					data-qa-id={props['data-qa-id']}
					disabled={disabled}
					max={inputProperties.max}
					min={allowEmptyValue ? 0 : inputProperties.min}
					name={name}
					onBlur={() => {
						setShowPopover(false);
					}}
					onChange={({target}) => {
						const numValue = Number(target.value);

						const errors = getErrors(
							numValue,
							inputProperties.min,
							inputProperties.max,
							inputProperties.step,
							inputProperties.currentUnitOfMeasure?.precision,
							allowEmptyValue
						);

						setInputProperties((inputProperties) => ({
							...inputProperties,
							quantity: target.value,
						}));

						onUpdate({
							errors,
							unitOfMeasure: inputProperties.currentUnitOfMeasure,
							value: numValue,
						});
					}}
					onFocus={() => setShowPopover(true)}
					ref={inputRef}
					step={inputProperties.step > 0 ? inputProperties.step : ''}
					type="number"
					value={String(inputProperties.quantity || '')}
				/>

				{showPopover && (
					<RulesPopover
						alignment={alignment}
						errors={visibleErrors}
						inputRef={inputRef}
						max={inputProperties.max || ''}
						min={inputProperties.min}
						multiple={inputProperties.step}
						precision={
							inputProperties.currentUnitOfMeasure?.precision || 0
						}
					/>
				)}
			</ClayForm.Group>
		);
	}
);

InputQuantitySelector.defaultProps = {
	allowEmptyValue: false,
	max: null,
	min: 1,
	step: 1,
};

export default InputQuantitySelector;
