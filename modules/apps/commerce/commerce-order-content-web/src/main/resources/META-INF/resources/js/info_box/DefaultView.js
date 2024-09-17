/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';
import {CommerceServiceProvider} from 'commerce-frontend-js';
import {openToast, sub} from 'frontend-js-web';
import React, {useState} from 'react';

import InfoBoxModal from '../InfoBoxModal';

const formatValue = (value, type) => {
	if (type === 'date' && value) {
		return new Intl.DateTimeFormat(
			Liferay.ThemeDisplay.getBCP47LanguageId(),
			{dateStyle: 'short'}
		).format(new Date(value));
	}

	return value;
};

const isEditable = (field, isOpen) => {
	if (
		['requestedDeliveryDate', 'shippingMethod'].indexOf(field) >= 0 &&
		!isOpen
	) {
		return false;
	}

	return true;
};

const DefaultView = ({
	additionalProps,
	buttonDisplayType,
	elementId,
	field,
	fieldValue,
	fieldValueType,
	hasPermission,
	isOpen,
	label,
	namespace,
	orderId,
	readOnly,
	spritemap,
}) => {
	const {observer, onOpenChange, open} = useModal();
	const [inputValue, setInputValue] = useState(
		additionalProps?.value ? additionalProps?.value : fieldValue
	);
	const [parseRequest, setParseRequest] = useState(
		() => (field, inputValue) => {
			return {
				[field]: inputValue,
			};
		}
	);
	const [parseResponse, setParseResponse] = useState(
		() => (field, response) => {
			if (response) {
				return response[field];
			}

			return null;
		}
	);
	const [value, setValue] = useState(fieldValue);

	const handleSubmit = async (event) => {
		event.preventDefault();

		const updateOrder = isOpen
			? CommerceServiceProvider.DeliveryCartAPI('v1').updateCartById
			: CommerceServiceProvider.DeliveryOrderAPI('v1')
					.updatePlacedOrderById;

		updateOrder(orderId, parseRequest(field, inputValue))
			.then((response) => {
				setValue(parseResponse(field, response));

				onOpenChange(false);
			})
			.catch((error) => {
				openToast({
					message:
						error.message ||
						Liferay.Language.get('an-unexpected-error-occurred'),
					type: 'danger',
				});
			});
	};

	return (
		<div className={namespace + 'info-box'} id={elementId}>
			{label ? (
				<div className="align-items-center d-flex">
					<div className="h5 info-box-label m-0">{label}</div>

					{hasPermission && !readOnly && isEditable(field, isOpen) ? (
						<ClayButton
							aria-controls={`${namespace}infoBoxModal`}
							aria-label={
								value
									? sub(Liferay.Language.get('edit-x'), label)
									: sub(Liferay.Language.get('add-x'), label)
							}
							className="ml-2"
							data-qa-id={`${label}-infoBoxButton`}
							displayType={buttonDisplayType}
							onClick={() => onOpenChange(true)}
							size="xs"
						>
							{value
								? Liferay.Language.get('edit')
								: Liferay.Language.get('add')}
						</ClayButton>
					) : null}
				</div>
			) : null}

			<div>
				<p className="info-box-value">
					{formatValue(value, fieldValueType)}
				</p>
			</div>

			<InfoBoxModal
				additionalProps={additionalProps}
				field={field}
				fieldValueType={fieldValueType}
				handleSubmit={handleSubmit}
				id={`${namespace}infoBoxModal`}
				inputValue={inputValue}
				label={label}
				observer={observer}
				onOpenChange={onOpenChange}
				open={open}
				orderId={orderId}
				setInputValue={setInputValue}
				setParseRequest={setParseRequest}
				setParseResponse={setParseResponse}
				spritemap={spritemap}
			/>
		</div>
	);
};

export default DefaultView;
