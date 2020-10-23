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
import React, {createRef, useEffect, useState} from 'react';

import throttle from '../../utilities/throttle';

const THROTTLE_TIMEOUT = 1000;

function QuantitySelector(props) {
	const [currentQuantity, setCurrentQuantity] = useState(
		!!props.minQuantity && props.minQuantity > props.quantity
			? props.minQuantity
			: props.quantity
	);
	const [nextAvailable, setNextAvailable] = useState(
		currentQuantity + props.multipleQuantity <= props.maxQuantity
	);
	const [prevAvailable, setPrevAvailable] = useState(
		currentQuantity - props.multipleQuantity >= props.minQuantity
	);
	const [isThrottling, setIsThrottling] = useState(false);

	const inputRef = createRef();

	useEffect(() => {
		setCurrentQuantity(props.quantity);
	}, [props.quantity, setCurrentQuantity]);

	useEffect(() => {
		if (props.throttleOnUpdate) {
			setIsThrottling(true);

			props.onUpdate(currentQuantity).then(() => setIsThrottling(false));
		}
		else {
			props.onUpdate(currentQuantity);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [currentQuantity]);

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

	function _increaseQuantity() {
		if (nextAvailable) {
			updateCurrentQuantity(currentQuantity + props.multipleQuantity);
		}
	}

	function _decreaseQuantity() {
		if (prevAvailable) {
			updateCurrentQuantity(currentQuantity - props.multipleQuantity);
		}
	}

	function handleInputChange() {
		const {value} = inputRef.current;

		return updateCurrentQuantity(parseInt(value, 10));
	}

	const decreaseQuantity = throttle(_decreaseQuantity, THROTTLE_TIMEOUT),
		increaseQuantity = throttle(_increaseQuantity, THROTTLE_TIMEOUT);

	function handleInputKeyUp(e) {
		switch (e.key) {
			case 'ArrowUp':
				increaseQuantity();
				break;
			case 'ArrowDown':
				decreaseQuantity();
				break;
			case 'Enter':
			default:
				break;
		}
	}

	function handleSelectChange() {
		const {value} = inputRef.current;

		setCurrentQuantity(value);
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
						ref={inputRef}
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
							ref={inputRef}
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
							disabled={
								isThrottling || props.disabled || !prevAvailable
							}
							onClick={decreaseQuantity}
							type={'button'}
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
							max={props.maxQuantity}
							min={props.minQuantity}
							name={props.inputName}
							onChange={handleInputChange}
							onKeyUp={handleInputKeyUp}
							ref={inputRef}
							step={props.multipleQuantity}
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
							disabled={
								isThrottling || props.disabled || !nextAvailable
							}
							onClick={increaseQuantity}
							type={'button'}
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

	/**
	 * if 'throttleOnUpdate' is true,
	 * 'onUpdate' must return a <Promise>.
	 */
	onUpdate: PropTypes.func,
	prependedIcon: PropTypes.string,
	prependedText: PropTypes.string,
	quantity: PropTypes.number,
	size: PropTypes.oneOf(['large', 'medium', 'small']),
	spritemap: PropTypes.string,
	style: PropTypes.oneOf(['default', 'simple']),
	throttleOnUpdate: PropTypes.bool,
};

QuantitySelector.defaultProps = {
	disabled: false,
	maxQuantity: 99999999,
	minQuantity: 1,
	multipleQuantity: 1,
	onUpdate: () => {},
	style: 'default',
	throttleOnUpdate: false,
};

export default QuantitySelector;
