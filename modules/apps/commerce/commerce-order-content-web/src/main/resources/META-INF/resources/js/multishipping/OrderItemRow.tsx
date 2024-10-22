/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayLink from '@clayui/link';
import {useModal} from '@clayui/modal';
import ClayTable from '@clayui/table';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {
	CommerceServiceProvider,
	QuantitySelectorComponent as QuantitySelector,

	// @ts-ignore

} from 'commerce-frontend-js';
import {debounce, openConfirmModal} from 'frontend-js-web';
import React, {useCallback, useState} from 'react';

import {showError} from './ErrorMessage';
import {formatCartItem} from './Multishipping';
import OrderItemDetailModal from './OrderItemDetailModal';
import {
	IAPIResponseError,
	IDeliveryGroup,
	IOrderItem,
	IOrderItemDeliveryGroup,
} from './Types';

interface IOrderItemRowProps {
	handleSubmit(item: IOrderItem, saveFullOrder?: boolean): void;
	deliveryGroups?: Array<IDeliveryGroup>;
	disabled?: boolean;
	namespace?: string;
	orderId: number;
	orderItem: IOrderItem;
	rowIndex?: number;
	spritemap?: string;
}

const calculateOrderItemQuantity = (orderItemDeliveryGroups: {
	[key: string]: IOrderItemDeliveryGroup;
}) => {
	return Object.keys(orderItemDeliveryGroups).reduce(
		(quantity, deliveryGroupId) => {
			return quantity + orderItemDeliveryGroups[deliveryGroupId].quantity;
		},
		0
	);
};

const copyColumnOrderItem = (
	deliveryGroups: Array<IDeliveryGroup>,
	orderItem: IOrderItem
): IOrderItem => {
	orderItem.deliveryGroups = orderItem.deliveryGroups || {};

	const defaultDeliveryGroup = orderItem.deliveryGroups[deliveryGroups[0].id];

	if (!defaultDeliveryGroup) {
		return orderItem;
	}

	for (const deliveryGroup of deliveryGroups) {
		if (orderItem.deliveryGroups[deliveryGroup.id]) {
			orderItem.deliveryGroups[deliveryGroup.id].quantity =
				defaultDeliveryGroup?.quantity || 0;
		}
		else {
			orderItem.deliveryGroups[deliveryGroup.id] = {
				options: orderItem.options,
				orderItemId: 0,
				originalQuantity: defaultDeliveryGroup?.quantity || 0,
				quantity: defaultDeliveryGroup?.quantity || 0,
				replacedSkuId: orderItem.replacedSkuId,
				skuId: orderItem.skuId,
				skuUnitOfMeasure: orderItem.skuUnitOfMeasure,
			};
		}
	}

	orderItem.quantity = calculateOrderItemQuantity(orderItem.deliveryGroups);

	return orderItem;
};

const removeOrderItem = (orderItem: IOrderItem): IOrderItem => {
	orderItem.deliveryGroups = orderItem.deliveryGroups || {};
	orderItem.quantity = 0;

	for (const [, orderItemConf] of Object.entries(orderItem.deliveryGroups)) {
		orderItemConf.quantity = 0;
	}

	return orderItem;
};

const resetOrderItem = (
	deliveryGroup: IDeliveryGroup,
	orderItem: IOrderItem
): IOrderItem => {
	orderItem.deliveryGroups = orderItem.deliveryGroups || {};
	orderItem.quantity = orderItem.settings?.minQuantity || 1;

	for (const [, orderItemConf] of Object.entries(orderItem.deliveryGroups)) {
		orderItemConf.quantity = 0;
	}

	const defaultDeliveryGroup = orderItem.deliveryGroups[deliveryGroup.id];

	if (defaultDeliveryGroup) {
		defaultDeliveryGroup.quantity = orderItem.quantity;
	}
	else {
		orderItem.deliveryGroups[deliveryGroup.id] = {
			options: orderItem.options,
			orderItemId: 0,
			originalQuantity: orderItem.quantity,
			quantity: orderItem.quantity,
			replacedSkuId: orderItem.replacedSkuId,
			skuId: orderItem.skuId,
			skuUnitOfMeasure: orderItem.skuUnitOfMeasure,
		};
	}

	return orderItem;
};

const splitOrderItem = (
	deliveryGroups: Array<IDeliveryGroup>,
	orderItem: IOrderItem
): IOrderItem => {
	orderItem.deliveryGroups = orderItem.deliveryGroups || {};

	if (!orderItem.quantity || !deliveryGroups.length) {
		return orderItem;
	}

	const quantity = Math.floor(orderItem.quantity / deliveryGroups.length);
	const reminder = orderItem.quantity % deliveryGroups.length;

	for (const deliveryGroup of deliveryGroups) {
		if (orderItem.deliveryGroups[deliveryGroup.id]) {
			orderItem.deliveryGroups[deliveryGroup.id].quantity = quantity;
		}
		else {
			orderItem.deliveryGroups[deliveryGroup.id] = {
				options: orderItem.options,
				orderItemId: 0,
				originalQuantity: quantity,
				quantity,
				replacedSkuId: orderItem.replacedSkuId,
				skuId: orderItem.skuId,
				skuUnitOfMeasure: orderItem.skuUnitOfMeasure,
			};
		}
	}

	orderItem.deliveryGroups[deliveryGroups[0].id].originalQuantity =
		quantity + reminder;
	orderItem.deliveryGroups[deliveryGroups[0].id].quantity =
		quantity + reminder;

	orderItem.quantity = calculateOrderItemQuantity(orderItem.deliveryGroups);

	return orderItem;
};

const OrderItemRow = ({
	deliveryGroups = [],
	disabled = false,
	handleSubmit,
	orderId,
	orderItem: orderItemProp,
	rowIndex = 0,
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
					quantity: calculateOrderItemQuantity(
						orderItemDeliveryGroups
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
					quantity: calculateOrderItemQuantity(
						orderItemDeliveryGroups
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
				<ClayTooltipProvider>
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

						<DropDown
							trigger={
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get('Actions')}
									data-qa-id={`row${rowIndex}Actions`}
									disabled={disabled}
									displayType="unstyled"
									size="xs"
									symbol="ellipsis-v"
								/>
							}
						>
							<DropDown.ItemList>
								<DropDown.Item
									aria-label={Liferay.Language.get(
										'split-quantity-evenly'
									)}
									data-qa-id={`row${rowIndex}SplitQuantity`}
									data-tooltip-align="right"
									disabled={
										(deliveryGroups?.length || 0) <= 1 ||
										!!orderItem.settings?.allowedQuantities
											?.length ||
										orderItem.quantity <
											(orderItem.settings?.minQuantity ||
												1) *
												deliveryGroups?.length
									}
									key={orderItem.id + '_splitQuantity'}
									onClick={() => {
										openConfirmModal({
											message: Liferay.Language.get(
												'if-the-quantity-cannot-be-equally-distributed,-the-primary-delivery-group-will-have-the-remaining-quantity-to-reach-the-total-quantity-set.-are-you-sure-you-want-to-split'
											),
											onConfirm: (isConfirmed) => {
												if (isConfirmed) {
													const internalOrderItem =
														splitOrderItem(
															deliveryGroups,
															{
																...orderItem,
															}
														);

													setOrderItem(
														internalOrderItem
													);

													handleSubmit(
														internalOrderItem,
														true
													);
												}
											},
										});
									}}
									title={
										(deliveryGroups?.length || 0) <= 1 ||
										!!orderItem.settings?.allowedQuantities
											?.length ||
										orderItem.quantity <
											(orderItem.settings?.minQuantity ||
												1) *
												deliveryGroups?.length
											? Liferay.Language.get(
													'the-item-s-quantity-is-not-valid-for-the-the-number-of-delivery-groups'
												)
											: ''
									}
								>
									{Liferay.Language.get(
										'split-quantity-evenly'
									)}
								</DropDown.Item>

								<DropDown.Item
									aria-label={Liferay.Language.get(
										'reset-row'
									)}
									data-qa-id={`row${rowIndex}ResetRow`}
									disabled={!deliveryGroups?.length}
									key={orderItem.id + '_resetRow'}
									onClick={() => {
										openConfirmModal({
											message: Liferay.Language.get(
												'by-resetting-the-row-s,-all-the-columns-will-have-a-quantity-of-0-except-the-first-one,-which-will-have-the-minimum quantity.-are-you-sure-you-want-to-reset'
											),
											onConfirm: (isConfirmed) => {
												if (isConfirmed) {
													const internalOrderItem =
														resetOrderItem(
															deliveryGroups[0],
															{
																...orderItem,
															}
														);

													setOrderItem(
														internalOrderItem
													);

													handleSubmit(
														internalOrderItem,
														true
													);
												}
											},
										});
									}}
								>
									{Liferay.Language.get('reset-row')}
								</DropDown.Item>

								<DropDown.Item
									aria-label={Liferay.Language.get(
										'copy-column-1-to-all'
									)}
									data-qa-id={`row${rowIndex}CopyColumn`}
									data-tooltip-align="right"
									disabled={
										(deliveryGroups?.length || 0) <= 1 ||
										!(orderItem.deliveryGroups || {})[
											deliveryGroups[0].id
										] ||
										(orderItem.settings?.maxQuantity || 1) <
											(orderItem.deliveryGroups || {})[
												deliveryGroups[0].id
											].quantity *
												deliveryGroups?.length
									}
									key={orderItem.id + '_CopyColumn'}
									onClick={() => {
										const internalOrderItem =
											copyColumnOrderItem(
												deliveryGroups,
												{
													...orderItem,
												}
											);

										setOrderItem(internalOrderItem);

										handleSubmit(internalOrderItem, true);
									}}
									title={
										(deliveryGroups?.length || 0) <= 1 ||
										!(orderItem.deliveryGroups || {})[
											deliveryGroups[0].id
										] ||
										(orderItem.settings?.maxQuantity || 1) <
											(orderItem.deliveryGroups || {})[
												deliveryGroups[0].id
											].quantity *
												deliveryGroups?.length
											? Liferay.Language.get(
													'the-item-s-quantity-is-not-valid-for-the-the-number-of-delivery-groups'
												)
											: ''
									}
								>
									{Liferay.Language.get(
										'copy-column-1-to-all'
									)}
								</DropDown.Item>

								<DropDown.Item
									aria-label={Liferay.Language.get(
										'remove-item'
									)}
									data-qa-id={`row${rowIndex}RemoveItem`}
									key={orderItem.id + '_RemoveItem'}
									onClick={() => {
										openConfirmModal({
											message: Liferay.Language.get(
												'by-removing-the-item-s,-it-they-will-disappear-from-the-list-of-ordered-items.-are-you-sure-you-want-to-remove-the-item-s'
											),
											onConfirm: (isConfirmed) => {
												if (isConfirmed) {
													const internalOrderItem =
														removeOrderItem({
															...orderItem,
														});

													setOrderItem(
														internalOrderItem
													);

													handleSubmit(
														internalOrderItem,
														true
													);
												}
											},
										});
									}}
								>
									{Liferay.Language.get('remove-item')}
								</DropDown.Item>
							</DropDown.ItemList>
						</DropDown>
					</div>
				</ClayTooltipProvider>

				{open && (
					<OrderItemDetailModal
						observer={observer}
						orderItem={orderItem}
						spritemap={spritemap}
					/>
				)}
			</ClayTable.Cell>

			<ClayTable.Cell
				aria-label="quantity"
				data-qa-id={`orderItem${orderItem.id}Quantity`}
			>
				<span>{orderItem.quantity}</span>
			</ClayTable.Cell>

			{deliveryGroups.map((deliveryGroup) => (
				<ClayTable.Cell
					className="delivery-group"
					data-qa-id={`orderItem${orderItem.id}-${deliveryGroup.id}`}
					key={`${orderItem.id}_${deliveryGroup.id}`}
				>
					<QuantitySelector
						alignment={rowIndex > 0 ? 'top' : 'bottom'}
						allowEmptyValue={true}
						allowedQuantities={
							orderItem?.settings?.allowedQuantities
						}
						data-qa-id={`orderItem${orderItem.id}-${deliveryGroup.id}Input`}
						disabled={disabled}
						max={orderItem?.settings?.maxQuantity}
						min={orderItem?.settings?.minQuantity || 0}
						onUpdate={({
							errors,
							value,
						}: {
							errors: any;
							value: any;
						}) => {
							if (!errors.length) {
								handleUpdateField(
									deliveryGroup.id,
									Number(value)
								);
							}
						}}
						quantity={
							(orderItem.deliveryGroups || {})[deliveryGroup.id]
								?.quantity
						}
						step={
							orderItem?.skuUnitOfMeasure
								?.incrementalOrderQuantity ||
							orderItem?.settings?.multipleQuantity ||
							1
						}
						{...orderItem?.settings}
						unitOfMeasure={orderItem?.skuUnitOfMeasure}
					/>
				</ClayTable.Cell>
			))}
		</ClayTable.Row>
	);
};

export default OrderItemRow;
