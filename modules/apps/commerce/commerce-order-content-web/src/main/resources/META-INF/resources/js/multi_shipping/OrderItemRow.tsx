/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import ClayLink from '@clayui/link';
import {useModal} from '@clayui/modal';
import ClayTable from '@clayui/table';

// @ts-ignore

import {CommerceServiceProvider} from 'commerce-frontend-js';
import {debounce} from 'frontend-js-web';
import React, {useCallback, useState} from 'react';

import {showError} from './ErrorMessage';
import {formatCartItem} from './MultiShipping';
import OrderItemDetailModal from './OrderItemDetailModal';
import {
	IAPIResponseError,
	IDeliveryGroup,
	IOrderItem,
	IOrderItemDeliveryGroup,
} from './Types';

interface IOrderItemRowProps {
	handleSubmit(item: IOrderItem): void;
	deliveryGroups?: Array<IDeliveryGroup>;
	disabled?: boolean;
	namespace?: string;
	orderId: number;
	orderItem: IOrderItem;
	spritemap?: string;
}

const OrderItemRow = ({
	deliveryGroups = [],
	disabled = false,
	handleSubmit,
	orderId,
	orderItem: orderItemProp,
	spritemap = 'OrderItemRow',
}: IOrderItemRowProps) => {
	const {observer, onOpenChange, open} = useModal();
	const [orderItem, setOrderItem] = useState<IOrderItem>(orderItemProp);

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const finalizeSave = useCallback(
		debounce(
			async (
				currentDeliveryGroup: IDeliveryGroup,
				deliveryGroupId: number,
				orderId: number,
				orderItem: IOrderItem,
				orderItemDeliveryGroups: {
					[key: string]: IOrderItemDeliveryGroup;
				},
				quantity: number
			) => {
				const existingOrderItemDeliveryGroup =
					orderItemDeliveryGroups[deliveryGroupId];

				if (
					existingOrderItemDeliveryGroup &&
					existingOrderItemDeliveryGroup.orderItemId
				) {
					if (quantity <= 0) {
						await CommerceServiceProvider.DeliveryCartAPI('v1')
							.deleteItemById(
								existingOrderItemDeliveryGroup.orderItemId
							)
							.then(() => {
								delete orderItemDeliveryGroups[deliveryGroupId];
							})
							.catch((error: IAPIResponseError) => {
								showError(error);

								existingOrderItemDeliveryGroup.quantity =
									existingOrderItemDeliveryGroup.originalQuantity;
							});
					}
					else {
						await CommerceServiceProvider.DeliveryCartAPI('v1')
							.updateItemById(
								existingOrderItemDeliveryGroup.orderItemId,
								formatCartItem(
									currentDeliveryGroup,
									orderItem,
									quantity
								)
							)
							.then((response: IOrderItem) => {
								existingOrderItemDeliveryGroup.originalQuantity =
									response.quantity;
								existingOrderItemDeliveryGroup.quantity =
									response.quantity;
							})
							.catch((error: IAPIResponseError) => {
								showError(error);

								existingOrderItemDeliveryGroup.quantity =
									existingOrderItemDeliveryGroup.originalQuantity;
							});
					}
				}
				else {
					if (quantity > 0) {
						await CommerceServiceProvider.DeliveryCartAPI('v1')
							.createItemByCartId(
								orderId,
								formatCartItem(
									currentDeliveryGroup,
									orderItem,
									quantity
								)
							)
							.then((response: IOrderItem) => {
								orderItemDeliveryGroups[deliveryGroupId] = {
									...orderItemDeliveryGroups[deliveryGroupId],
									orderItemId: response.id,
									originalQuantity: quantity,
									quantity,
								};
							})
							.catch((error: IAPIResponseError) => {
								showError(error);

								existingOrderItemDeliveryGroup.quantity =
									existingOrderItemDeliveryGroup.originalQuantity;
							});
					}
				}

				const internalOrderItem = {
					...orderItem,
					deliveryGroups: orderItemDeliveryGroups,
					quantity: Object.keys(orderItemDeliveryGroups).reduce(
						(quantity, deliveryGroupId) => {
							return (
								quantity +
								orderItemDeliveryGroups[deliveryGroupId]
									.quantity
							);
						},
						0
					),
				};

				setOrderItem(internalOrderItem);

				handleSubmit(internalOrderItem);
			},
			500
		),
		[handleSubmit]
	);

	const handleUpdateField = useCallback(
		(deliveryGroupId: number, quantity: number) => {
			const currentDeliveryGroup = deliveryGroups?.find(
				(deliveryGroup) => deliveryGroup.id === deliveryGroupId
			);

			if (!currentDeliveryGroup) {
				return;
			}

			const orderItemDeliveryGroups = {
				...(orderItem.deliveryGroups || {}),
			};

			const existingOrderItemDeliveryGroup =
				orderItemDeliveryGroups[deliveryGroupId];

			if (existingOrderItemDeliveryGroup) {
				existingOrderItemDeliveryGroup.quantity = quantity;
			}
			else {
				orderItemDeliveryGroups[deliveryGroupId] = {
					options: orderItem.options,
					orderItemId: 0,
					originalQuantity: quantity,
					quantity,
					replacedSkuId: orderItem.replacedSkuId || 0,
					skuId: orderItem.skuId,
					skuUnitOfMeasure: orderItem.skuUnitOfMeasure,
				};
			}

			setOrderItem((prevState) => {
				return {
					...prevState,
					deliveryGroups: orderItemDeliveryGroups,
					quantity: Object.keys(orderItemDeliveryGroups).reduce(
						(quantity, deliveryGroupId) => {
							return (
								quantity +
								orderItemDeliveryGroups[deliveryGroupId]
									.quantity
							);
						},
						0
					),
				};
			});

			finalizeSave(
				currentDeliveryGroup,
				deliveryGroupId,
				orderId,
				orderItem,
				orderItemDeliveryGroups,
				quantity
			);
		},
		[deliveryGroups, finalizeSave, orderId, orderItem]
	);

	return (
		<ClayTable.Row
			data-qa-id={`orderItem${orderItem.id}Row`}
			key={orderItem.id}
		>
			<ClayTable.Cell aria-label="sku-name">
				<div className="align-items-center d-flex flex-nowrap sku-name">
					<ClayLink
						className="flex-grow-1 mr-4 text-nowrap text-truncate"
						displayType="unstyled"
						onClick={(event) => {
							event.preventDefault();

							onOpenChange(true);
						}}
					>
						<img
							alt="thumbnail"
							className="mr-2 order-item-thumbnail"
							src={orderItem.thumbnail}
						/>

						<span>{orderItem.sku}</span>
					</ClayLink>
				</div>

				{open && (
					<OrderItemDetailModal
						observer={observer}
						orderItem={orderItem}
						spritemap={spritemap}
					/>
				)}
			</ClayTable.Cell>

			<ClayTable.Cell aria-label="quantity" className="text-center">
				<span>{orderItem.quantity}</span>
			</ClayTable.Cell>

			{deliveryGroups.map((deliveryGroup) => (
				<ClayTable.Cell
					className="delivery-group"
					data-qa-id={`orderItem${orderItem.id}-${deliveryGroup.id}`}
					key={`${orderItem.id}_${deliveryGroup.id}`}
				>
					<ClayInput
						data-qa-id={`orderItem${orderItem.id}-${deliveryGroup.id}Input`}
						disabled={disabled}
						min={0}
						onChange={({
							target: {value},
						}: {
							target: {
								value: boolean | number | string | undefined;
							};
						}) => {
							handleUpdateField(deliveryGroup.id, Number(value));
						}}
						type="number"
						value={
							(orderItem.deliveryGroups || {})[deliveryGroup.id]
								?.quantity || ''
						}
					/>
				</ClayTable.Cell>
			))}
		</ClayTable.Row>
	);
};

export default OrderItemRow;
