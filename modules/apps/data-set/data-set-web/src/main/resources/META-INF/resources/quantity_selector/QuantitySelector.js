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

import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

function QuantitySelector({
	allowedQuantities,
	appendedIcon,
	appendedText,
	disabled,
	inputName,
	maxQuantity,
	minQuantity,
	multipleQuantity,
	prependedIcon,
	prependedText,
	quantity,
	size,
	style,
	updateQuantity,
}) {
	const [currentQuantity, setCurrentQuantity] = useState(quantity);
	const [nextAvailable, setNextAvailable] = useState(
		currentQuantity + multipleQuantity <= maxQuantity
	);
	const [prevAvailable, setPrevAvailable] = useState(
		currentQuantity - multipleQuantity >= minQuantity
	);

	useEffect(() => {
		setCurrentQuantity(quantity);
	}, [quantity, setCurrentQuantity]);

	useEffect(() => {
		if (updateQuantity) {
			updateQuantity(currentQuantity);
		}
	}, [currentQuantity, updateQuantity]);

	useEffect(() => {
		setNextAvailable(currentQuantity + multipleQuantity <= maxQuantity);
		setPrevAvailable(currentQuantity - multipleQuantity >= minQuantity);
	}, [currentQuantity, maxQuantity, minQuantity, multipleQuantity]);

	function updateCurrentQuantity(newQuantity) {
		if (
			newQuantity >= minQuantity &&
			newQuantity <= maxQuantity &&
			newQuantity % multipleQuantity === 0
		) {
			setCurrentQuantity(newQuantity);
		}
	}

	function increaseQuantity() {
		if (nextAvailable) {
			setCurrentQuantity(currentQuantity + multipleQuantity);
		}
	}

	function decreaseQuantity() {
		if (prevAvailable) {
			setCurrentQuantity(currentQuantity - multipleQuantity);
		}
	}

	function handleInputChange(event) {
		return updateCurrentQuantity(parseInt(event.target.value, 10));
	}

	function handleInputKeyUp(event) {
		if (event.keyCode == 38) {
			increaseQuantity();
		}
		else if (event.keyCode == 40) {
			decreaseQuantity();
		}
	}

	function handleSelectChange(event) {
		event.preventDefault();
		setCurrentQuantity(event.target.value);
	}

	let btnSizeClass;
	let formControlSizeClass;

	if (size === 'large') {
		btnSizeClass = 'btn-lg';
		formControlSizeClass = 'form-control-lg';
	}

	if (size === 'small') {
		btnSizeClass = 'btn-sm';
		formControlSizeClass = 'form-control-sm';
	}

	const content = (
		<div className="quantity-selector">
			{allowedQuantities ? (
				<>
					<select
						className={classnames(
							'form-control',
							formControlSizeClass
						)}
						name={inputName}
						onChange={handleSelectChange}
						value={currentQuantity}
					>
						{allowedQuantities.map((value) => (
							<option key={value} value={value}>
								{value}
							</option>
						))}
					</select>
				</>
			) : style === 'simple' ? (
				<div className="input-group input-group-sm simple">
					{(prependedIcon || prependedText) && (
						<div className="input-group-item input-group-item-shrink input-group-prepend">
							<span className="input-group-text">
								{prependedIcon ? (
									<ClayIcon symbol={prependedIcon} />
								) : (
									prependedText
								)}
							</span>
						</div>
					)}
					<div
						className={classnames(
							'input-group-item input-group-item-shrink',
							(appendedIcon || appendedText) &&
								'input-group-prepend'
						)}
					>
						<input
							className={classnames(
								'form-control text-center',
								formControlSizeClass
							)}
							disabled={disabled}
							max={maxQuantity}
							min={minQuantity}
							name={inputName}
							onChange={handleInputChange}
							step={multipleQuantity}
							type="number"
							value={currentQuantity}
						/>
					</div>
					{(appendedIcon || appendedText) && (
						<div className="input-group-append input-group-item input-group-item-shrink">
							<span className="input-group-text">
								{appendedIcon ? (
									<ClayIcon symbol={appendedIcon} />
								) : (
									appendedText
								)}
							</span>
						</div>
					)}
				</div>
			) : (
				<div className="input-group justify-content-center">
					<div className="input-group-item input-group-item-shrink input-group-prepend">
						<button
							className={classnames(
								'btn btn-monospaced btn-secondary',
								btnSizeClass
							)}
							disabled={disabled || !prevAvailable}
							onClick={decreaseQuantity}
						>
							<ClayIcon symbol="hr" />
						</button>
					</div>

					<div className="input-group-item input-group-prepend">
						<input
							className={classnames(
								'form-control text-center',
								formControlSizeClass
							)}
							disabled={disabled}
							name={inputName}
							onChange={handleInputChange}
							onKeyUp={handleInputKeyUp}
							type="text"
							value={currentQuantity}
						/>
					</div>

					<div className="input-group-append input-group-item input-group-item-shrink">
						<button
							className={classnames(
								'btn btn-monospaced btn-secondary',
								btnSizeClass
							)}
							disabled={disabled || !nextAvailable}
							onClick={increaseQuantity}
						>
							<ClayIcon symbol="plus" />
						</button>
					</div>
				</div>
			)}
		</div>
	);

	return content;
}

QuantitySelector.propTypes = {
	allowedQuantities: PropTypes.arrayOf(PropTypes.number),
	appendedIcon: PropTypes.string,
	appendedText: PropTypes.string,
	disabled: PropTypes.bool,
	inputName: PropTypes.string,
	maxQuantity: PropTypes.number,
	minQuantity: PropTypes.number,
	multipleQuantity: PropTypes.number,
	prependedIcon: PropTypes.string,
	prependedText: PropTypes.string,
	quantity: PropTypes.number,
	size: PropTypes.oneOf(['large', 'medium', 'small']),
	style: PropTypes.oneOf(['default', 'simple']),
	updateQuantity: PropTypes.func,
};

QuantitySelector.defaultProps = {
	disabled: false,
	maxQuantity: 99999999,
	minQuantity: 1,
	multipleQuantity: 1,
	style: 'default',
};

export default QuantitySelector;
