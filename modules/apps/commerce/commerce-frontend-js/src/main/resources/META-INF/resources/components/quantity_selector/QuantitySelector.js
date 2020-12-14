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

import {ClayInput, ClaySelectWithOption} from '@clayui/form';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {UPDATE_AFTER, generateQuantityOptions} from './utils/index';

function QuantitySelector({
	componentId,
	disabled,
	forceDropdown,
	large,
	name,
	onUpdate,
	quantity,
	...optionSettings
}) {
	const [selectedQuantity, setSelectedQuantity] = useState(
		Math.max(optionSettings.minQuantity, quantity)
	);

	const isDropdown =
		optionSettings.allowedQuantities.length > 0 || forceDropdown;

	/**
	 * If source is <input /> and multipleQuantity > 1,
	 * the newly set value will always be floored to the
	 * closest lower multiple value.
	 */
	const onChange = ({target}) => {
		if (target.value) {
			const value = parseInt(target.value, 10);

			setSelectedQuantity(
				value - (value % optionSettings.multipleQuantity)
			);
		}
		else {
			setSelectedQuantity(optionSettings.minQuantity || 1);
		}
	};

	const keypressDebounce = useRef();

	const willUpdate = useCallback(() => {
		clearTimeout(keypressDebounce.current);

		keypressDebounce.current = setTimeout(
			() => {
				onUpdate(selectedQuantity);
			},
			isDropdown ? 0 : UPDATE_AFTER
		);
	}, [isDropdown, onUpdate, selectedQuantity]);

	// eslint-disable-next-line react-hooks/exhaustive-deps
	useEffect(willUpdate, [selectedQuantity]);

	const commonProps = {
		className: classnames({
			'quantity-selector': true,
			'form-control-lg': large
		}),
		'data-component-id': componentId,
		disabled,
		name,
		onChange,
		value: selectedQuantity,
	};

	return (
		<>
			{isDropdown ? (
				<ClaySelectWithOption
					options={generateQuantityOptions(optionSettings)}
					{...commonProps}
				/>
			) : (
				<ClayInput
					max={optionSettings.maxQuantity}
					min={optionSettings.minQuantity}
					step={optionSettings.multipleQuantity}
					type={'number'}
					{...commonProps}
				/>
			)}
		</>
	);
}

QuantitySelector.defaultProps = {
	allowedQuantities: [],
	disabled: false,
	forceDropdown: false,
	large: false,
	maxQuantity: 99,
	minQuantity: 1,
	multipleQuantity: 1,
	onUpdate: () => {},
	quantity: 1,
};

QuantitySelector.propTypes = {
	allowedQuantities: PropTypes.arrayOf(PropTypes.number),
	componentId: PropTypes.string,
	disabled: PropTypes.bool,
	forceDropdown: PropTypes.bool,
	large: PropTypes.bool,
	maxQuantity: PropTypes.number,
	minQuantity: PropTypes.number,
	multipleQuantity: PropTypes.number,
	name: PropTypes.string,
	onUpdate: PropTypes.func,
	quantity: PropTypes.number,
};

export default QuantitySelector;
