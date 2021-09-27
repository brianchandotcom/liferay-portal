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
import React, {
	useEffect,
	useLayoutEffect,
	useMemo,
	useRef,
	useState,
} from 'react';

import {DEFAULT_LINK_OPTION, LINKING_OPTIONS} from '../utilities/constants';
import {deletePin, savePin} from '../utilities/data';
import {
	calculateTooltipStyle,
	formatMappedProduct,
	getTypeFromSelectedPin,
} from '../utilities/index';

function Tooltip({
	closeTooltip,
	productId,
	readOnlySequence,
	selectedPin,
	sequence: originalSequence,
	updatePins,
	x,
	y,
}) {
	const originalType = useMemo(() => getTypeFromSelectedPin(selectedPin), [
		selectedPin,
	]);
	const originalMappedProduct = useMemo(
		() => selectedPin?.mappedProduct || null,
		[selectedPin]
	);
	const [type, updateType] = useState(null);
	const [quantity, updateQuantity] = useState(
		selectedPin?.mappedProduct || 1
	);
	const [linkedProduct, updateLinkedProduct] = useState(
		selectedPin?.mappedProduct || null
	);
	const [sequence, updateSequence] = useState(originalSequence);
	const [saving, updateSaving] = useState(false);
	const [deleting, updateDeleting] = useState(false);
	const [tooltipStyle, updateTooltipStyle] = useState({});
	const isMounted = useIsMounted();
	const tooltipRef = useRef();

	useLayoutEffect(() => {
		const style = calculateTooltipStyle(x, y, tooltipRef);

		updateTooltipStyle(style);
	}, [x, y]);

	useEffect(() => {
		updateQuantity(selectedPin?.mappedProduct.quantity || 1);
		updateSequence(originalSequence);
		updateType(() => getTypeFromSelectedPin(selectedPin));
		updateLinkedProduct(selectedPin?.mappedProduct || null);
	}, [selectedPin, originalSequence]);

	useLayoutEffect(() => {
		function handleWindowClick(event) {
			if (
				!tooltipRef.current.contains(event.target) &&
				event.target.tagName !== 'text' &&
				!event.target.closest('.autocomplete-dropdown-menu')
			) {
				closeTooltip();
			}
		}

		document.addEventListener('mousedown', handleWindowClick);

		return () => {
			document.removeEventListener('mousedown', handleWindowClick);
		};
	}, [closeTooltip]);

	function _handleSubmit(event) {
		event.preventDefault();

		const mappedProduct = formatMappedProduct(
			type || originalType || DEFAULT_LINK_OPTION,
			quantity,
			sequence,
			linkedProduct
		);

		const update = selectedPin?.id !== undefined;

		updateSaving(true);

		savePin(
			productId,
			update ? selectedPin.id : null,
			mappedProduct,
			sequence
		)
			.then((newPin) => {
				if (!isMounted()) {
					return;
				}

				updatePins((pins) => {
					return update
						? pins.map((pin) =>
								pin.sequence === sequence ? newPin : pin
						  )
						: [...pins, newPin];
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

				updatePins((pins) => {
					return pins.filter((pin) => pin.id !== selectedPin.id);
				});

				updateDeleting(false);

				closeTooltip();

				openToast({
					message: Liferay.Language.get('pin-deleted'),
					type: 'success',
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

	const LinkedProductFormGroup = useMemo(() => {
		const componentKey = type || originalType || DEFAULT_LINK_OPTION;

		return React.memo(LINKING_OPTIONS[componentKey].component);
	}, [originalType, type]);

	const loading = saving || deleting;

	const disabled = !linkedProduct || !sequence.length || loading;

	const saveMessage = selectedPin
		? Liferay.Language.get('update')
		: Liferay.Language.get('save');

	return (
		<div className="diagram-tooltip" ref={tooltipRef} style={tooltipStyle}>
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
							updateLinkedProduct(null);
							updateType(event.target.value);
						}}
						value={type || originalType || DEFAULT_LINK_OPTION}
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
							updateValue={updateLinkedProduct}
							value={
								!type ? originalMappedProduct : linkedProduct
							}
						/>
					</div>

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
						className="mr-1"
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
		</div>
	);
}

export default Tooltip;
