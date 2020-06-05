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

import ClayIcon, {ClayIconSpriteContext} from '@clayui/icon';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

function QuantitySelector(props) {
	const [currentQuantity, setCurrentQuantity] = useState(props.quantity);
	const [nextAvailable, setNextAvailable] = useState(
		currentQuantity + props.multipleQuantity <= props.maxQuantity
	);
	const [prevAvailable, setPrevAvailable] = useState(
		currentQuantity - props.multipleQuantity >= props.minQuantity
	);

	useEffect(() => {
		setCurrentQuantity(props.quantity);
	}, [props.quantity, setCurrentQuantity]);

	useEffect(() => {
		if (props.updateQuantity) {
			props.updateQuantity(currentQuantity);
		}
	}, [currentQuantity, props, props.updateQuantity]);

	useEffect(() => {
		setNextAvailable(
			currentQuantity + props.multipleQuantity <= props.maxQuantity
		);
		setPrevAvailable(
			currentQuantity - props.multipleQuantity >= props.minQuantity
		);
	}, [
		currentQuantity,
		props.maxQuantity,
		props.minQuantity,
		props.multipleQuantity,
	]);

	function updateCurrentQuantity(newQuantity) {
		if (
			newQuantity >= props.minQuantity &&
			newQuantity <= props.maxQuantity &&
			newQuantity % props.multipleQuantity === 0
		) {
			setCurrentQuantity(newQuantity);
		}
	}

	function increaseQuantity() {
		if (nextAvailable) {
			setCurrentQuantity(currentQuantity + props.multipleQuantity);
		}
	}

	function decreaseQuantity() {
		if (prevAvailable) {
			setCurrentQuantity(currentQuantity - props.multipleQuantity);
		}
	}

	function handleInputChange(e) {
		return updateCurrentQuantity(parseInt(e.target.value, 10));
	}

	function handleInputKeyUp(e) {
		if (e.keyCode == 38) {
			return increaseQuantity();
		}
		if (e.keyCode == 40) {
			return decreaseQuantity();
		}
	}

	function handleSelectChange(e) {
		e.preventDefault();
		setCurrentQuantity(e.target.value);
	}

	let btnSizeClass;
	let formControlSizeClass;

	if (props.size === 'large') {
		btnSizeClass = 'btn-lg';
		formControlSizeClass = 'form-control-lg';
	}

	if (props.size === 'small') {
		btnSizeClass = 'btn-sm';
		formControlSizeClass = 'form-control-sm';
	}

	const content = (
		<div className="quantity-selector">
			{props.allowedQuantities ? (
				<>
					<select
						className={classnames(
							'form-control',
							formControlSizeClass
						)}
						name={props.inputName}
						onChange={handleSelectChange}
						value={currentQuantity}
					>
						{props.allowedQuantities.map((val) => (
							<option key={val} value={val}>
								{val}
							</option>
						))}
					</select>
				</>
			) : props.style === 'simple' ? (
				<div className="input-group input-group-sm simple">
					{(props.prependedIcon || props.prependedText) && (
						<div className="input-group-item input-group-item-shrink input-group-prepend">
							<span className="input-group-text">
								{props.prependedIcon ? (
									<ClayIcon symbol={props.prependedIcon} />
								) : (
									props.prependedText
								)}
							</span>
						</div>
					)}
					<div
						className={classnames(
							'input-group-item input-group-item-shrink',
							(props.appendedIcon || props.appendedText) &&
								'input-group-prepend'
						)}
					>
						<input
							className={classnames(
								'form-control text-center',
								formControlSizeClass
							)}
							disabled={props.disabled}
							max={props.maxQuantity}
							min={props.minQuantity}
							name={props.inputName}
							onChange={handleInputChange}
							step={props.multipleQuantity}
							type="number"
							value={currentQuantity}
						/>
					</div>
					{(props.appendedIcon || props.appendedText) && (
						<div className="input-group-append input-group-item input-group-item-shrink">
							<span className="input-group-text">
								{props.appendedIcon ? (
									<ClayIcon symbol={props.appendedIcon} />
								) : (
									props.appendedText
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
							disabled={props.disabled || !prevAvailable}
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
							disabled={props.disabled}
							name={props.inputName}
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
							disabled={props.disabled || !nextAvailable}
							onClick={increaseQuantity}
						>
							<ClayIcon symbol="plus" />
						</button>
					</div>
				</div>
			)}
		</div>
	);

	return props.spritemap ? (
		<ClayIconSpriteContext.Provider value={props.spritemap}>
			{content}
		</ClayIconSpriteContext.Provider>
	) : (
		content
	);
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
	quantity: PropTypes.number.isRequired,
	size: PropTypes.oneOf(['large', 'medium', 'small']),
	spritemap: PropTypes.string,
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
