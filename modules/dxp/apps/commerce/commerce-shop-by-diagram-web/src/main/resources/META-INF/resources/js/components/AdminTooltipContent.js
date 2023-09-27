/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {FDS_EVENT} from '@liferay/frontend-data-set-web';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import {
	DEFAULT_LINK_OPTION,
	DIAGRAM_EVENTS,
	LINKING_OPTIONS,
} from '../utilities/constants';

function AdminTooltipContent({
	closeTooltip,
	datasetDisplayId,
	onDelete,
	onSave,
	productId,
	readOnlySequence,
	selectedPin,
	sequence: sequenceProp,
}) {
	const [type, setType] = useState(
		selectedPin?.mappedProduct?.type || DEFAULT_LINK_OPTION
	);
	const [quantity, setQuantity] = useState(
		selectedPin?.mappedProduct?.quantity || 1
	);
	const [mappedProduct, setMappedProduct] = useState(
		selectedPin?.mappedProduct || null
	);
	const [sequence, setSequence] = useState(
		sequenceProp || selectedPin?.sequence || ''
	);
	const [saving, setSaving] = useState(false);
	const [deleting, setDeleting] = useState(false);
	const isMounted = useIsMounted();

	useEffect(() => {
		setQuantity(selectedPin?.mappedProduct?.quantity || 1);
		setSequence(selectedPin?.mappedProduct?.sequence || sequenceProp || '');
		setType(selectedPin?.mappedProduct?.type || DEFAULT_LINK_OPTION);
		setMappedProduct(selectedPin?.mappedProduct || null);
	}, [selectedPin, sequenceProp]);

	function _handleSubmit(event) {
		event.preventDefault();

		setSaving(true);

		onSave(type, quantity, sequence, mappedProduct)
			.then(() => {
				if (!isMounted()) {
					return;
				}

				setSaving(false);

				closeTooltip();

				if (datasetDisplayId) {
					Liferay.fire(FDS_EVENT.FDS_UPDATE_DISPLAY, {
						id: datasetDisplayId,
					});
				}

				Liferay.fire(DIAGRAM_EVENTS.DIAGRAM_UPDATED, {
					diagramProductId: productId,
				});
			})
			.catch(() => {
				if (isMounted()) {
					setSaving(false);
				}
			});
	}

	function _handleDelete() {
		setDeleting(true);

		onDelete()
			.then(() => {
				if (!isMounted()) {
					return;
				}

				setDeleting(false);

				closeTooltip();

				if (datasetDisplayId) {
					Liferay.fire(FDS_EVENT.UPDATE_DISPLAY, {
						id: datasetDisplayId,
					});
				}

				Liferay.fire(DIAGRAM_EVENTS.DIAGRAM_UPDATED, {
					diagramProductId: productId,
				});
			})
			.catch(() => {
				if (isMounted()) {
					setDeleting(false);
				}
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
		<ClayForm className="diagram-admin-tooltip" onSubmit={_handleSubmit}>
			<ClayForm.Group>
				<label htmlFor="sequenceInput">
					{Liferay.Language.get('position')}
				</label>

				<ClayInput
					id="sequenceInput"
					onChange={(event) => setSequence(event.target.value)}
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
						setMappedProduct(null);
						setType(event.target.value);
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
						updateValue={setMappedProduct}
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
									setQuantity(event.target.value)
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

				<ClayButton
					disabled={disabled}
					monospaced={saving}
					type="submit"
				>
					{saving ? <ClayLoadingIndicator small /> : saveMessage}
				</ClayButton>
			</div>
		</ClayForm>
	);
}

AdminTooltipContent.propTypes = {
	closeTooltip: PropTypes.func.isRequired,
	datasetDisplayId: PropTypes.string,
	onDelete: PropTypes.func.isRequired,
	productId: PropTypes.oneOfType([PropTypes.number, PropTypes.string])
		.isRequired,
	readOnlySequence: PropTypes.bool,
	selectedPin: PropTypes.shape({
		id: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
		mappedProduct: PropTypes.shape({
			quantity: PropTypes.number.isRequired,
			sequence: PropTypes.string.isRequired,
			type: PropTypes.string.isRequired,
		}),
		sequence: PropTypes.string,
	}),
	sequence: PropTypes.string,
	target: PropTypes.any,
	x: PropTypes.number,
	y: PropTypes.number,
};

export default AdminTooltipContent;
