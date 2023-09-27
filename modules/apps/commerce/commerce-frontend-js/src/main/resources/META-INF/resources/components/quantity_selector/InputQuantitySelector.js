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
	getNumberOfDecimals,
	getProductMaxQuantity,
	isMultiple,
} from '../../utilities/quantities';
import RulesPopover from './RulesPopover';

const getErrors = (value, min, max, step, precision = 0) => {
	const errors = [];

	if (getNumberOfDecimals(value) > precision) {
		errors.push('decimals');
	}

	if (!value || value < min) {
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
		},
		inputRef
	) => {
		const [inputProperties, setInputProperties] = useState({
			currentUnitOfMeasure: unitOfMeasure,
			max: unitOfMeasure
				? getProductMaxQuantity(
						max,
						unitOfMeasure.incrementalOrderQuantity,
						unitOfMeasure.precision
				  )
				: getProductMaxQuantity(Math.ceil(max), Math.ceil(step)),
			min: unitOfMeasure
				? getMinQuantity(
						min,
						unitOfMeasure.incrementalOrderQuantity,
						unitOfMeasure.precision
				  )
				: getMinQuantity(Math.ceil(min), Math.ceil(step)),
			quantity,
			step: unitOfMeasure
				? unitOfMeasure.incrementalOrderQuantity
				: Math.ceil(step),
		});
		const [showPopover, setShowPopover] = useState(false);
		const [visibleErrors, setVisibleErrors] = useState(() =>
			getErrors(
				quantity,
				unitOfMeasure ? min : Math.ceil(min),
				unitOfMeasure ? max : Math.ceil(max),
				unitOfMeasure
					? unitOfMeasure.incrementalOrderQuantity
					: Math.ceil(step)
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
					errors: getErrors(quantity, min, max, step, precision),
					value: quantity,
				});
			};

			if (unitOfMeasure) {
				let quantity = inputProperties.quantity;

				if (resetQuantity) {
					quantity = Number(
						getMinQuantity(
							min,
							unitOfMeasure.incrementalOrderQuantity,
							unitOfMeasure.precision
						)
					);

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
					step: unitOfMeasure.incrementalOrderQuantity,
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
						min,
						max,
						inputProperties.step,
						inputProperties.currentUnitOfMeasure.precision
					);
				}
				else {
					return getErrors(
						inputProperties.quantity,
						Math.ceil(min),
						Math.ceil(max),
						Math.ceil(inputProperties.step)
					);
				}
			});
		}, [inputProperties, max, min]);

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

		return (
			<ClayForm.Group
				className={classNames({
					'has-error': visibleErrors.length,
					'mb-0': true,
				})}
			>
				<ClayInput
					className={className}
					disabled={disabled}
					max={inputProperties.max}
					min={inputProperties.min}
					name={name}
					onBlur={() => {
						setShowPopover(false);
					}}
					onChange={({target}) => {
						const numValue = Number(target.value);

						const errors = getErrors(
							numValue,
							min,
							max,
							inputProperties.step,
							inputProperties.currentUnitOfMeasure?.precision
						);

						setInputProperties((inputProperties) => ({
							...inputProperties,
							quantity: target.value,
						}));

						onUpdate({
							errors,
							value: numValue,
						});
					}}
					onFocus={() => setShowPopover(true)}
					ref={inputRef}
					step={inputProperties.step > 0 ? inputProperties.step : ''}
					type="number"
					value={String(inputProperties.quantity || '')}
				/>

				{showPopover &&
					(inputProperties.step > 0 ||
						min > 0 ||
						visibleErrors.includes('max')) && (
						<RulesPopover
							alignment={alignment}
							errors={visibleErrors}
							inputRef={inputRef}
							max={max || ''}
							min={min}
							multiple={inputProperties.step}
							precision={
								inputProperties.currentUnitOfMeasure
									?.precision || 0
							}
						/>
					)}
			</ClayForm.Group>
		);
	}
);

InputQuantitySelector.defaultProps = {
	max: null,
	min: 1,
	step: 1,
};

export default InputQuantitySelector;
