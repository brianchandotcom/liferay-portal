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

import ClayAutocomplete from '@clayui/autocomplete';
import ClayButton from '@clayui/button';
import ClayCard from '@clayui/card';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput, ClayRadio, ClayRadioGroup} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import {searchDiagrams, searchSkus} from './utilities/utilities';

const AdminTooltip = ({
	deletePin,
	namespace,
	setRemovePinHandler,
	setShowTooltip,
	showTooltip,
	updatePin,
}) => {
	const [active, setActive] = useState(false);
	const [products, setProducts] = useState(null);
	const [pinPositionLabel, setPinPositionLabel] = useState(
		showTooltip.details.label
	);
	const node = useRef();
	const dropdownNode = useRef();
	const [query, setQuery] = useState('');
	const [linkedValue, setLinkedValue] = useState('sku');
	const [selectedProduct, setSelectedProduct] = useState(showTooltip.details);
	const [quantity, setQuantity] = useState(showTooltip.details.quantity);

	useEffect(() => {
		const getProducts = linkedValue === 'sku' ? searchSkus : searchDiagrams;

		if (query?.length) {
			getProducts(query, linkedValue).then((jsonResponse) =>
				setProducts(jsonResponse.items)
			);
		}
		else {
			setSelectedProduct(showTooltip.details);
		}
	}, [query, linkedValue, showTooltip]);

	useEffect(() => {
		function handleClick(event) {
			if (
				node.current.contains(event.target) ||
				(dropdownNode.current &&
					dropdownNode.current.contains(event.target))
			) {
				return;
			}

			setActive(false);
		}
		if (active) {
			document.addEventListener('mousedown', handleClick);
		}

		return () => {
			document.removeEventListener('mousedown', handleClick);
		};
	}, [active]);

	return (
		<ClayCard
			className="admin-tooltip"
			style={{
				left: showTooltip.details.cx,
				top: showTooltip.details.cy,
			}}
		>
			<ClayCard.Body className="row">
				<ClayForm.Group className="col-12 text-left" small>
					<label htmlFor={`${namespace}pin-position`}>
						{Liferay.Language.get('position')}
					</label>

					<ClayInput
						id={`${namespace}pin-position`}
						onChange={(event) =>
							setPinPositionLabel(event.target.value)
						}
						placeholder={Liferay.Language.get(
							'insert-your-name-here'
						)}
						type="text"
						value={pinPositionLabel}
					/>
				</ClayForm.Group>

				<ClayForm.Group className="col-12" small>
					<ClayRadioGroup
						className="d-flex justify-content-start mt-4"
						inline
						onSelectedValueChange={setLinkedValue}
						selectedValue={linkedValue}
					>
						<ClayRadio
							label={Liferay.Language.get('linked-to-sku')}
							value="sku"
						/>

						<ClayRadio
							label={Liferay.Language.get('linked-to-diagram')}
							value="diagram"
						/>
					</ClayRadioGroup>
				</ClayForm.Group>

				<ClayForm.Group className="col-9 text-left" small>
					<label htmlFor={`${namespace}pin-sku`}>
						{Liferay.Language.get('select-sku')}
					</label>

					<ClayAutocomplete ref={node}>
						<ClayAutocomplete.Input
							onChange={(event) => {
								setSelectedProduct(null);

								setQuery(event.target.value);
							}}
							onFocus={(_e) => {
								setActive(true);
							}}
							onKeyUp={(event) => {
								setActive(event.keyCode !== 27);
							}}
							value={selectedProduct?.sku || query}
						/>

						<ClayAutocomplete.DropDown active={active && products}>
							<div ref={dropdownNode}>
								<ClayDropDown.ItemList>
									{products?.length && (
										<ClayDropDown.Item disabled>
											{Liferay.Language.get(
												'no-results-found'
											)}
										</ClayDropDown.Item>
									)}

									{products?.length &&
										products.map((product) => (
											<ClayAutocomplete.Item
												key={product.id}
												match={selectedProduct?.sku}
												onClick={() => {
													setSelectedProduct(product);

													setActive(false);
												}}
												value={`${product.sku} - ${product.productName.en_US}`}
											/>
										))}
								</ClayDropDown.ItemList>
							</div>
						</ClayAutocomplete.DropDown>
					</ClayAutocomplete>
				</ClayForm.Group>

				<ClayForm.Group className="col-3" small>
					<label htmlFor={`${namespace}pin-quantity`}>
						{Liferay.Language.get('quantity')}
					</label>

					<ClayInput
						id={`${namespace}pin-quantity`}
						onChange={(event) =>
							setQuantity(parseInt(event.target.value, 10))
						}
						type="number"
						value={quantity}
					/>
				</ClayForm.Group>

				<ClayForm.Group
					className="col-6 d-flex justify-content-start mt-4"
					small
				>
					<ClayButton
						displayType="link"
						onClick={() => {
							deletePin({
								id: showTooltip.details.id,
								positionX: showTooltip.details.cx,
								positionY: showTooltip.details.cy,
								sequence: pinPositionLabel,
							});
							setRemovePinHandler({
								handler: true,
								pin: showTooltip.details.id,
							});
							setShowTooltip({
								details: {
									cx: null,
									cy: null,
									id: null,
									label: null,
									linked_to_sku: 'sku',
									quantity: null,
									sku: '',
								},
								tooltip: false,
							});
						}}
					>
						{Liferay.Language.get('delete')}
					</ClayButton>
				</ClayForm.Group>

				<ClayForm.Group
					className="col-6 d-flex justify-content-between mt-4"
					small
				>
					<ClayButton
						displayType="secondary"
						onClick={() => {
							setShowTooltip({
								details: {
									cx: null,
									cy: null,
									id: null,
									label: '',
									linked_to_sku: 'sku',
									quantity: null,
									sku: '',
								},
								tooltip: false,
							});
						}}
					>
						{Liferay.Language.get('close')}
					</ClayButton>

					<ClayButton
						displayType="primary"
						onClick={() => {
							updatePin({
								diagramEntry: {
									diagram: linkedValue === 'sku',
									productId: selectedProduct.productId,
									quantity,
									sequence: pinPositionLabel,
									sku: selectedProduct.sku,
									skuUuid: selectedProduct.id,
								},
								id: showTooltip.details.id,
								positionX: showTooltip.details.cx,
								positionY: showTooltip.details.cy,
								sequence: pinPositionLabel,
							});
							setShowTooltip({
								details: {
									cx: showTooltip.details.cx,
									cy: showTooltip.details.cy,
									id: showTooltip.details.id,
									label: pinPositionLabel,
									linked_to_sku: linkedValue,
									quantity,
									sku: selectedProduct.sku,
								},
								tooltip: false,
							});
						}}
					>
						{Liferay.Language.get('save')}
					</ClayButton>
				</ClayForm.Group>
			</ClayCard.Body>
		</ClayCard>
	);
};

AdminTooltip.propTypes = {
	setShowTooltip: PropTypes.func,
	showTooltip: PropTypes.shape({
		details: PropTypes.shape({
			cx: PropTypes.double,
			cy: PropTypes.double,
			id: PropTypes.number,
			label: PropTypes.string,
			linked_to_sku: PropTypes.oneOf(['sku', 'diagram']),
			quantity: PropTypes.number,
			sku: PropTypes.string,
		}),
		tooltip: PropTypes.bool,
	}),
};

export default AdminTooltip;
