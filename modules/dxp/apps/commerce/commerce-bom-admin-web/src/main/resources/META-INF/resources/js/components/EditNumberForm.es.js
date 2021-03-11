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

import React, {useMemo, useState} from 'react';

import {StoreContext} from './StoreContext.es';
import HighlightedText from './utilities/HighlightedText.es';
import Icon from './utilities/Icon.es';

export function DropdownItem(props) {
	return (
		<li>
			<a
				className="dropdown-item"
				href={`#${props.value}`}
				onClick={(e) => props.handleClickFn(e, props.value)}
			>
				<HighlightedText
					inverted={false}
					query={props.query}
					text={props.label}
				/>
			</a>
		</li>
	);
}

export function DropdownInput(props) {
	return (
		<div className="dropdown-full input-group-item">
			<input
				autoComplete="off"
				className="form-control input-group-inset input-group-inset-after"
				disabled={props.disabled}
				id={props.inputId || null}
				onChange={props.handleChangeFn}
				placeholder={props.inputPlaceholder}
				type="text"
				value={props.query || ''}
			/>
			{!props.disabled && (
				<span className="input-group-inset-item input-group-inset-item-after">
					{!props.query ? (
						<span
							className={`btn btn-unstyled${
								props.searchAdditionalCssClasses
									? ` ${props.searchAdditionalCssClasses}`
									: ''
							}`}
						>
							{props.searchIcon}
						</span>
					) : (
						<button
							className={`btn btn-unstyled${
								props.resetAdditionalCssClasses
									? ` ${props.resetAdditionalCssClasses}`
									: ''
							}`}
							onClick={props.handleResetFn}
							type="button"
						>
							{props.resetIcon}
						</button>
					)}
				</span>
			)}
			{props.elements && props.elements.length ? (
				<ul className="dropdown-menu show">
					{props.elements.map((el, i) => (
						<DropdownItem
							handleClickFn={props.handleClickOnItem}
							key={i}
							label={el.label}
							query={props.query}
							value={el.value}
						/>
					))}
				</ul>
			) : null}
		</div>
	);
}

const EditNumberForm = React.memo((props) => {
	const {actions, state} = React.useContext(StoreContext);
	const formData = state.area.spotFormData || {};
	const position = formData.position || {};
	const [dropdownDisabled, disableDropdown] = useState(false);
	const inputPlaceholder = Liferay.Language.get(
		'search-for-product-name-or-sku'
	);

	function isNumberAlreadyAdded(number) {
		return state.area.spots.reduce(
			(acc, el) => acc || el.number === number,
			false
		);
	}

	const orientationClasses = useMemo(
		() =>
			[
				position.y > 50
					? 'edit-number-form--bottom'
					: 'edit-number-form--top',
				position.x > 50
					? 'edit-number-form--left'
					: 'edit-number-form--right',
			].join(' '),
		[position.x, position.y]
	);

	function getProductByNumber(number) {
		return (
			number &&
			state.area.spots.reduce((acc, spot) => {
				if (acc) {
					return acc;
				}
				if (spot.number === number) {
					return state.area.mappedProducts.reduce(
						(acc, product) =>
							acc || (spot.productId === product.id && product),
						null
					);
				}

				return null;
			}, null)
		);
	}

	function handleNumberChange(e) {
		e.preventDefault();
		const number = e.target.value ? parseInt(e.target.value, 10) : null;

		actions.updateFormValue('number', number);

		const numberAdded = isNumberAlreadyAdded(number);

		if (numberAdded) {
			const productToBeMapped = getProductByNumber(number);
			selectProduct(productToBeMapped);
			disableDropdown(true);
		}
		else {
			disableDropdown(false);
		}
	}

	function handleProductInputChange(e) {
		const query = e.target.value || null;
		actions.updateFormValue('query', query);

		if (query) {
			actions.getProducts(state.app.productApiUrl, query);
		}
		else {
			actions.resetProducts();
		}
	}

	function resetProducts() {
		actions.updateFormValue('productId', null);
		actions.updateFormValue('query', null);
		actions.resetProducts();
	}

	function selectProduct(selectedProduct) {
		actions.updateFormValue('productId', selectedProduct.id);
		actions.updateFormValue('query', selectedProduct.name);
		actions.resetProducts();
	}

	function handleClickOnDropdownItem(e, value) {
		e.preventDefault();
		const productToBeMapped = state.area.availableProducts.reduce(
			(acc, el) => acc || (el.id === value && el),
			null
		);
		selectProduct(productToBeMapped);
	}

	function closeEditForm() {
		actions.unselectSpot();
	}

	function submitForm() {
		if (!formData.changed) {
			return null;
		}
		const params = [
			state.app.areaApiUrl,
			state.area.id,
			state.area.spotFormData,
		];

		return formData.state === 'create'
			? actions.submitNewSpot.apply(null, params)
			: actions.submitSpotChanges.apply(null, params);
	}

	function handleClickOnDelete() {
		actions.deleteSpot(
			state.app.areaApiUrl,
			state.area.id,
			state.area.spotFormData.id
		);
	}

	return (
		<div
			className="edit-number-form-wrapper"
			style={
				position
					? {
							bottom: position.y + '%',
							left: position.x + '%',
					  }
					: null
			}
		>
			{typeof position.x === 'number' &&
				typeof position.y === 'number' && (
					<span
						className={`spot-number spot-number--placeholder${
							position ? ` spot-number--placeholder--visible` : ''
						}`}
					>
						{formData.number}
					</span>
				)}
			{formData.state && (
				<div
					className={`edit-number-form panel panel-secondary ${orientationClasses}`}
				>
					<button
						className="btn btn-outline-borderless edit-number-form__close"
						onClick={closeEditForm}
						type="button"
					>
						<Icon spritemap={state.app.spritemap} symbol="times" />
					</button>
					<div className="panel-body">
						<div className="form-group">
							<label htmlFor="edit-number-form__input-number">
								{Liferay.Language.get('number')}
							</label>
							<input
								autoComplete="off"
								className="form-control"
								id="edit-number-form__input-number"
								min="1"
								onChange={handleNumberChange}
								placeholder={Liferay.Language.get(
									'select-number'
								)}
								type="number"
								value={formData.number || ''}
							/>
						</div>
						<div className="form-group">
							<label htmlFor="edit-number-form__input-product">
								{Liferay.Language.get('product')}
							</label>
							<div className="input-group">
								<DropdownInput
									disabled={dropdownDisabled}
									elements={state.area.availableProducts.map(
										(el) => ({label: el.name, value: el.id})
									)}
									handleChangeFn={handleProductInputChange}
									handleClickOnItem={
										handleClickOnDropdownItem
									}
									handleResetFn={resetProducts}
									inputClassName="edit-number-form__input-number"
									inputId="edit-number-form__input-number"
									inputPlaceholder={inputPlaceholder}
									query={formData.query}
									resetAdditionalCssClasses="edit-number-form__reset"
									resetIcon={
										props.resetIcon || (
											<Icon
												spritemap={state.app.spritemap}
												symbol="times-circle"
											/>
										)
									}
									searchAdditionalCssClasses="edit-number-form__search"
									searchIcon={
										props.searchIcon || (
											<Icon
												spritemap={state.app.spritemap}
												symbol="search"
											/>
										)
									}
									selectedElement={
										formData.productId
											? {
													label: formData.query,
													value: formData.productId,
											  }
											: null
									}
								/>
							</div>
						</div>
						<div className="btn-group d-block">
							<div className="row">
								{state.area.spotFormData.state === 'edit' && (
									<div className="col-auto">
										<div className="btn-group-item">
											<button
												className="btn btn-outline-borderless btn-outline-primary edit-number-form__delete-btn"
												onClick={handleClickOnDelete}
												type="button"
											>
												{Liferay.Language.get('delete')}
											</button>
										</div>
									</div>
								)}
								<div className="col text-right">
									<div className="btn-group-item">
										<button
											className="btn btn-secondary"
											onClick={closeEditForm}
											type="button"
										>
											{Liferay.Language.get('cancel')}
										</button>
									</div>
									<div className="btn-group-item">
										<button
											className="btn btn-primary"
											disabled={
												!(
													formData.productId &&
													formData.number &&
													formData.changed
												)
											}
											onClick={submitForm}
											type="submit"
										>
											{Liferay.Language.get('save')}
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			)}
		</div>
	);
});

export default EditNumberForm;
