/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import DropDown from '@clayui/drop-down';
import {useModal} from '@clayui/modal';
import ClayTable from '@clayui/table';
import {openConfirmModal} from 'frontend-js-web';
import React, {useCallback} from 'react';

import DeliveryGroupModal from './DeliveryGroupModal';
import {IDeliveryGroup} from './Types';

interface IDeliveryGroupHeaderCellProps {
	handleDeleteDeliveryGroup(deliveryGroup: IDeliveryGroup): void;
	handleSubmitDeliveryGroup(deliveryGroup: IDeliveryGroup): void;
	accountId: number;
	deliveryGroup: IDeliveryGroup;
	hasManageAddressesPermission?: boolean;
	namespace?: string;
	spritemap?: string;
}

const DeliveryGroupHeaderCell = ({
	accountId,
	deliveryGroup,
	handleDeleteDeliveryGroup,
	handleSubmitDeliveryGroup,
	hasManageAddressesPermission = true,
	namespace = 'DeliveryGroupHeaderCell',
	spritemap = '',
}: IDeliveryGroupHeaderCellProps) => {
	const {observer, onOpenChange, open} = useModal();

	const handleDeleteDeliveryGroupWrapper = useCallback(
		(deliveryGroup: IDeliveryGroup) => {
			onOpenChange(false);

			handleDeleteDeliveryGroup(deliveryGroup);
		},
		[handleDeleteDeliveryGroup, onOpenChange]
	);

	const handleSubmitDeliveryGroupWrapper = useCallback(
		(deliveryGroup: IDeliveryGroup) => {
			onOpenChange(false);

			handleSubmitDeliveryGroup(deliveryGroup);
		},
		[handleSubmitDeliveryGroup, onOpenChange]
	);

	return (
		<ClayTable.Cell headingCell key={deliveryGroup.id}>
			<div className="align-items-center d-flex flex-nowrap">
				<div className="flex-grow-1">
					<div className="text-nowrap text-truncate">
						{deliveryGroup.name}
					</div>

					{!!deliveryGroup.deliveryDate && (
						<div className="text-2 text-secondary text-truncate">
							{new Intl.DateTimeFormat(
								Liferay.ThemeDisplay.getBCP47LanguageId(),
								{dateStyle: 'short'}
							).format(new Date(deliveryGroup.deliveryDate))}
						</div>
					)}
				</div>

				<DropDown
					trigger={
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get('actions')}
							displayType="unstyled"
							size="xs"
							symbol="ellipsis-v"
						/>
					}
				>
					<DropDown.ItemList>
						<DropDown.Item
							aria-label={Liferay.Language.get('edit')}
							key={`${deliveryGroup.id}_edit`}
							onClick={() => {
								onOpenChange(true);
							}}
						>
							{Liferay.Language.get('edit')}
						</DropDown.Item>

						<DropDown.Item
							aria-label={Liferay.Language.get('delete')}
							key="{deliveryGroup.id}_delete"
							onClick={() => {
								openConfirmModal({
									message: Liferay.Language.get(
										'are-you-sure-you-want-to-delete-this'
									),
									onConfirm: (isConfirmed) => {
										if (isConfirmed) {
											handleDeleteDeliveryGroupWrapper(
												deliveryGroup
											);
										}
									},
								});
							}}
						>
							{Liferay.Language.get('delete')}
						</DropDown.Item>
					</DropDown.ItemList>
				</DropDown>
			</div>

			{open && (
				<DeliveryGroupModal
					accountId={accountId}
					deliveryGroup={deliveryGroup}
					handleSubmit={handleSubmitDeliveryGroupWrapper}
					hasManageAddressesPermission={hasManageAddressesPermission}
					namespace={namespace}
					observerModal={observer}
					onOpenModal={onOpenChange}
					spritemap={spritemap}
				/>
			)}
		</ClayTable.Cell>
	);
};

export default DeliveryGroupHeaderCell;
