/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';
import React, {useCallback} from 'react';

import DeliveryGroupModal from './DeliveryGroupModal';

interface IAddDeliveryGroupButtonProps {
	accountId: number;
	disabled?: boolean;
	handleSubmit: any;
	hasManageAddressesPermission?: boolean;
	namespace?: string;
	spritemap?: string;
}

const AddDeliveryGroupButton = ({
	accountId,
	disabled = false,
	handleSubmit,
	hasManageAddressesPermission = true,
	namespace = '',
	spritemap = '',
}: IAddDeliveryGroupButtonProps) => {
	const {observer, onOpenChange, open} = useModal();

	const handleSubmitWrapper = useCallback(
		(params) => {
			onOpenChange(false);

			handleSubmit(params);
		},
		[handleSubmit, onOpenChange]
	);

	return (
		<>
			<ClayButton
				aria-label={Liferay.Language.get('add-delivery-group')}
				disabled={disabled}
				displayType="primary"
				onClick={() => {
					onOpenChange(true);
				}}
			>
				{Liferay.Language.get('add-delivery-group')}
			</ClayButton>

			{open && (
				<DeliveryGroupModal
					accountId={accountId}
					handleSubmit={handleSubmitWrapper}
					hasManageAddressesPermission={hasManageAddressesPermission}
					namespace={namespace}
					observerModal={observer}
					onOpenModal={onOpenChange}
					spritemap={spritemap}
				/>
			)}
		</>
	);
};

export default AddDeliveryGroupButton;
