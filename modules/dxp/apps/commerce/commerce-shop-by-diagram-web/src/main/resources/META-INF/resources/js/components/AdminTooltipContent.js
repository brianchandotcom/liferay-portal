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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {openToast} from 'frontend-js-web';
import {UPDATE_DATASET_DISPLAY} from 'frontend-taglib-clay/data_set_display/utils/eventsDefinitions';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import {
	DEFAULT_LINK_OPTION,
	DIAGRAM_EVENTS,
	LINKING_OPTIONS,
} from '../utilities/constants';
import {deletePin, savePin} from '../utilities/data';
import {formatMappedProduct} from '../utilities/index';

function AdminTooltipContent({
	closeTooltip,
	datasetDisplayId,
	productId,
	readOnlySequence,
	selectedPin,
	sequence: sequenceProp,
	updatePins,
	x,
	y,
}) {
	const [type, updateType] = useState(
		selectedPin?.mappedProduct.type || DEFAULT_LINK_OPTION
	);
	const [quantity, updateQuantity] = useState(
		selectedPin?.mappedProduct.quantity || 1
	);
	const [mappedProduct, updateMappedProduct] = useState(
		selectedPin?.mappedProduct || null
	);
	const [sequence, updateSequence] = useState(
		sequenceProp || selectedPin?.sequence || ''
	);
	const [saving, updateSaving] = useState(false);
	const [deleting, updateDeleting] = useState(false);
	const isMounted = useIsMounted();

	useEffect(() => {
		updateQuantity(selectedPin?.mappedProduct.quantity || 1);
		updateSequence(
			selectedPin?.mappedProduct.sequence || sequenceProp || ''
		);
		updateType(selectedPin?.mappedProduct.type || DEFAULT_LINK_OPTION);
		updateMappedProduct(selectedPin?.mappedProduct || null);
	}, [selectedPin, sequenceProp]);

	function _handleSubmit(event) {
		event.preventDefault();

		const productDetails = formatMappedProduct(
			type,
			quantity,
			sequence,
			mappedProduct
		);

		const update = Boolean(selectedPin?.id);

		updateSaving(true);

		savePin(
			update ? selectedPin.id : null,
			productDetails,
			sequence,
			x,
			y,
			productId
		)
			.then((newPin) => {
				if (!isMounted()) {
					return;
				}

				updatePins((pins) => {
					const updatedPins = pins.map((pin) =>
						pin.sequence === newPin.sequence
							? {
									...pin,
									mappedProduct: newPin.mappedProduct,
									quantity: newPin.quantity,
							  }
							: pin
					);

					return update
						? updatedPins.map((updatedPin) =>
								updatedPin.id === newPin.id
									? newPin
									: updatedPin
						  )
						: [...updatedPins, newPin];
				});

				updateSaving(false);

				closeTooltip();

				openToast({
					message: update
						? Liferay.Language.get('pin-updated')
						: Liferay.Language.get('pin-created'),
					type: 'success',
				});
			})
			.then(() => {
				if (datasetDisplayId) {
					Liferay.fire(UPDATE_DATASET_DISPLAY, {
						id: datasetDisplayId,
					});
				}

				Liferay.fire(DIAGRAM_EVENTS.DIAGRAM_UPDATED, {
					diagramProductId: productId,
				});
			})
			.catch((error) => {
				openToast({
					message: error.message || error,
					type: 'danger',
				});

				updateSaving(false);
			});
	}

	function _handleDelete() {
		updateDeleting(true);

		deletePin(selectedPin.id)
			.then(() => {
				if (!isMounted()) {
					return;
				}

				updatePins((pins) =>
					pins.filter((pin) => pin.id !== selectedPin.id)
				);

				updateDeleting(false);

				closeTooltip();

				openToast({
					message: Liferay.Language.get('pin-deleted'),
					type: 'success',
				});
			})
			.then(() => {
				if (datasetDisplayId) {
					Liferay.fire(UPDATE_DATASET_DISPLAY, {
						id: datasetDisplayId,
					});
				}

				Liferay.fire(DIAGRAM_EVENTS.DIAGRAM_UPDATED, {
					diagramProductId: productId,
				});
			})
			.catch((error) => {
				openToast({
					message: error.message || error,
					type: 'danger',
				});

				updateDeleting(false);
			});
	}

	const LinkedProductFormGroup = useMemo(
		() => React.memo(LINKING_OPTIONS[type].component),
		[type]
	);
	const saveMessage = selectedPin
		? Liferay.Language.get('update')
		: Liferay.Language.get('save');
	const loading = saving || deleting;

	const disabled = !mappedProduct || !sequence || loading;

	return (
		<ClayForm onSubmit={_handleSubmit}>
			<ClayForm.Group>
				<label htmlFor="sequenceInput">
					{Liferay.Language.get('position')}
				</label>

				<ClayInput
					id="sequenceInput"
					onChange={(event) => updateSequence(event.target.value)}
					readOnly={readOnlySequence}
					type="text"
					value={sequence}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="typeInput">
					{Liferay.Language.get('type')}
				</label>

				<ClaySelect
					id="typeInput"
					onChange={(event) => {
						updateMappedProduct(null);
						updateType(event.target.value);
					}}
					value={type}
				>
					{['sku', 'diagram', 'external'].map((link) => (
						<ClaySelect.Option
							key={link}
							label={LINKING_OPTIONS[link].label}
							value={link}
						/>
					))}
				</ClaySelect>
			</ClayForm.Group>

			<div className="row">
				<div className="col">
					<LinkedProductFormGroup
						updateValue={updateMappedProduct}
						value={mappedProduct}
					/>
				</div>

				{type !== 'diagram' && (
					<div className="col-3">
						<ClayForm.Group>
							<label htmlFor="quantityInput">
								{Liferay.Language.get('quantity')}
							</label>

							<ClayInput
								id="quantityInput"
								min={1}
								onChange={(event) =>
									updateQuantity(event.target.value)
								}
								type="number"
								value={quantity}
							/>
						</ClayForm.Group>
					</div>
				)}
			</div>

			<div className="d-flex justify-content-end mt-3">
				{selectedPin && (
					<ClayButton
						className="mr-auto"
						disabled={loading}
						displayType="link"
						onClick={_handleDelete}
						type="button"
					>
						{deleting ? (
							<ClayLoadingIndicator small />
						) : (
							Liferay.Language.get('delete')
						)}
					</ClayButton>
				)}

				<ClayButton
					className="mr-3"
					displayType="secondary"
					onClick={() => closeTooltip()}
					type="button"
				>
					{Liferay.Language.get('cancel')}
				</ClayButton>

				<ClayButton disabled={disabled} type="submit">
					{saving ? <ClayLoadingIndicator small /> : saveMessage}
				</ClayButton>
			</div>
		</ClayForm>
	);
}

AdminTooltipContent.propTypes = {
	closeTooltip: PropTypes.func.isRequired,
	datasetDisplayId: PropTypes.string,
	productId: PropTypes.oneOfType([PropTypes.number, PropTypes.string])
		.isRequired,
	readOnlySequence: PropTypes.bool,
	selectedPin: PropTypes.shape({
		id: PropTypes.oneOfType([PropTypes.number, PropTypes.string])
			.isRequired,
		mappedProduct: PropTypes.shape({
			quantity: PropTypes.number.isRequired,
			sequence: PropTypes.string.isRequired,
			type: PropTypes.string.isRequired,
		}),
		sequence: PropTypes.string,
	}),
	sequence: PropTypes.string,
	target: PropTypes.any,
	updatePins: PropTypes.func.isRequired,
	x: PropTypes.number,
	y: PropTypes.number,
};

export default AdminTooltipContent;
