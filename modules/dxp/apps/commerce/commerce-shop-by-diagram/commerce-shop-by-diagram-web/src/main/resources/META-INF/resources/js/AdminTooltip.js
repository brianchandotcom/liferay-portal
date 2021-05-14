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

import ClayAutocomplete from '@clayui/autocomplete';
import ClayButton from '@clayui/button';
import ClayCard from '@clayui/card';
import {useResource} from '@clayui/data-provider';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput, ClayRadio, ClayRadioGroup} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const AdminTooltip = ({setRemovePinHandler, setShowTooltip, showTooltip}) => {
	const [position, setPosition] = useState(showTooltip.details.label);
	const [linkedValue, setLinkedValue] = useState(
		showTooltip.details.linked_to_sku
	);
	const [sku, setSku] = useState(showTooltip.details.sku);
	const [quantity, setQuantity] = useState(showTooltip.details.quantity);

	const [networkStatus, setNetworkStatus] = useState(4);

	const initialLoading = networkStatus === 1;
	const loadingAd = networkStatus < 4;
	const error = networkStatus === 5;

	const {resource} = useResource({
		fetchPolicy: 'cache-first',
		link: 'https://rickandmortyapi.com/api/character/',
		onNetworkStatusChange: setNetworkStatus,
		variables: {
			name: sku,
		},
	});

	const cardStyle = {
		left: showTooltip.details.cx,
		top: showTooltip.details.cy,
	};

	return (
		<ClayCard className="admin-tooltip" style={cardStyle}>
			<ClayCard.Body className="row">
				<ClayForm.Group className="col-12 form-group-sm text-left">
					<label htmlFor="basicInputText">Position</label>
					<ClayInput
						id="basicInputText"
						onChange={(event) => setPosition(event.target.value)}
						placeholder="Insert your name here"
						type="text"
						value={position}
					/>
				</ClayForm.Group>

				<ClayForm.Group className="col-12 form-group-sm">
					<ClayRadioGroup
						className="d-flex justify-content-start mt-4"
						inline
						onSelectedValueChange={(val) => setLinkedValue(val)}
						selectedValue={linkedValue}
					>
						<ClayRadio label="Linked to SKU" value="sku" />
						<ClayRadio label="Linked to Diagram" value="diagram" />
					</ClayRadioGroup>
				</ClayForm.Group>

				<ClayForm.Group className="col-9 form-group-sm text-left">
					<label htmlFor="basicInputText">Select SKU</label>
					<ClayAutocomplete>
						<ClayAutocomplete.Input
							onChange={(event) => setSku(event.target.value)}
							placeholder="Type SKU here"
							value={sku}
						/>
						<ClayAutocomplete.DropDown
							active={(!!resource && !!sku) || initialLoading}
						>
							<ClayDropDown.ItemList>
								{(error || (resource && resource.error)) && (
									<ClayDropDown.Item className="disabled">
										No Results Found
									</ClayDropDown.Item>
								)}
								{!error &&
									resource &&
									resource.results &&
									resource.results.map((item) => (
										<ClayAutocomplete.Item
											key={item.id}
											match={sku}
											value={item.name}
										/>
									))}
							</ClayDropDown.ItemList>
						</ClayAutocomplete.DropDown>
						{loadingAd && <ClayAutocomplete.LoadingIndicator />}
					</ClayAutocomplete>
				</ClayForm.Group>

				<ClayForm.Group className="col-3 form-group-sm">
					<label htmlFor="basicInputText">Qty</label>
					<ClayInput
						id="basicInputText"
						onChange={(event) =>
							setQuantity(parseInt(event.target.value,10))
						}
						type="number"
						value={quantity}
					/>
				</ClayForm.Group>

				<ClayForm.Group className="col-6 d-flex form-group-sm justify-content-start mt-4">
					<ClayButton
						displayType="link"
						onClick={() => {
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
						Delete
					</ClayButton>
				</ClayForm.Group>

				<ClayForm.Group className="col-6 d-flex form-group-sm justify-content-between mt-4">
					<ClayButton
						displayType="secondary"
						onClick={() =>
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
							})
						}
					>
						Cancel
					</ClayButton>
					<ClayButton
						displayType="primary"
						onClick={() => {
							setShowTooltip({
								details: {
									cx: showTooltip.details.cx,
									cy: showTooltip.details.cy,
									id: showTooltip.details.id,
									label: position,
									linked_to_sku: linkedValue,
									quantity,
									sku,
								},
								tooltip: false,
							});
						}}
					>
						Save
					</ClayButton>
				</ClayForm.Group>
			</ClayCard.Body>
		</ClayCard>
	);
};

AdminTooltip.defaultProps = {};

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
