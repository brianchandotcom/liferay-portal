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

const DefaultView = ({
	buttonDisplayType,
	elementId,
	field,
	fieldValue,
	hasPermission,
	isOpen,
	label,
	namespace,
	orderId,
	readOnly,
	spritemap,
}) => {
	const {observer, onOpenChange, open} = useModal();
	const [inputValue, setInputValue] = useState(fieldValue);
	const [value, setValue] = useState(fieldValue);

	const handleSubmit = async (event) => {
		event.preventDefault();

		const updateOrder = isOpen
			? CommerceServiceProvider.DeliveryCartAPI('v1').updateCartById
			: CommerceServiceProvider.DeliveryOrderAPI('v1')
					.updatePlacedOrderById;

		updateOrder(orderId, {
			[field]: inputValue,
		})
			.then((response) => {
				setValue(response[field]);

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

					{hasPermission && !readOnly ? (
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
				<p className="info-box-value">{value}</p>
			</div>

			<InfoBoxModal
				handleSubmit={handleSubmit}
				id={`${namespace}infoBoxModal`}
				inputValue={inputValue}
				label={label}
				observer={observer}
				onOpenChange={onOpenChange}
				open={open}
				setInputValue={setInputValue}
				spritemap={spritemap}
			/>
		</div>
	);
};

export default DefaultView;
