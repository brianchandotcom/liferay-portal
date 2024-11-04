/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import DropDown from '@clayui/drop-down';
import {ClayCheckbox} from '@clayui/form';
import ClayLink from '@clayui/link';
import {useModal} from '@clayui/modal';
import ClayTable from '@clayui/table';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {debounce, openConfirmModal} from 'frontend-js-web';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

// @ts-ignore

import ServiceProvider from '../../ServiceProvider/index';
import {
	CURRENT_ORDER_UPDATED,
	ORDER_INFORMATION_ALTERED,

	// @ts-ignore

} from '../../utilities/eventsDefinitions';

// @ts-ignore

import QuantitySelector from '../quantity_selector/QuantitySelector';
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
	handleSelection(orderItemId: number): void;
	handleSubmit(item: IOrderItem, saveFullOrder?: boolean): void;
	checked?: boolean;
	deliveryGroups?: Array<IDeliveryGroup>;
	disabled?: boolean;
	namespace?: string;
	orderId: number;
	orderItem: IOrderItem;
	readonly?: boolean;
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

export function copyColumnOrderItem(
	deliveryGroups: Array<IDeliveryGroup>,
	orderItem: IOrderItem
): IOrderItem {
	orderItem.deliveryGroups = orderItem.deliveryGroups || {};

	const defaultDeliveryGroup = orderItem.deliveryGroups[deliveryGroups[0].id];

	if (!defaultDeliveryGroup) {
		return orderItem;
	}

	if (
		(deliveryGroups?.length || 0) <= 1 ||
		!(orderItem.deliveryGroups || {})[deliveryGroups[0].id] ||
		(orderItem.settings?.maxQuantity || 1) <
			(orderItem.deliveryGroups || {})[deliveryGroups[0].id].quantity *
				deliveryGroups?.length
	) {
		throw new Error('invalid quantity');
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
}

export function removeOrderItem(orderItem: IOrderItem): IOrderItem {
	orderItem.deliveryGroups = orderItem.deliveryGroups || {};
	orderItem.quantity = 0;

	for (const [, orderItemConf] of Object.entries(orderItem.deliveryGroups)) {
		orderItemConf.quantity = 0;
	}

	return orderItem;
}

export function resetOrderItem(
	deliveryGroup: IDeliveryGroup,
	orderItem: IOrderItem
): IOrderItem {
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
}

export function splitOrderItem(
	deliveryGroups: Array<IDeliveryGroup>,
	orderItem: IOrderItem
): IOrderItem {
	orderItem.deliveryGroups = orderItem.deliveryGroups || {};

	if (!orderItem.quantity || !deliveryGroups.length) {
		return orderItem;
	}

	if (
		(deliveryGroups?.length || 0) <= 1 ||
		!!orderItem.settings?.allowedQuantities?.length ||
		orderItem.quantity <
			(orderItem.settings?.minQuantity || 1) * deliveryGroups?.length
	) {
		throw new Error('invalid quantity');
	}

	const quantity = Math.floor(orderItem.quantity / deliveryGroups.length);
	const remainder = orderItem.quantity % deliveryGroups.length;

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
		quantity + remainder;
	orderItem.deliveryGroups[deliveryGroups[0].id].quantity =
		quantity + remainder;

	orderItem.quantity = calculateOrderItemQuantity(orderItem.deliveryGroups);

	return orderItem;
}

const OrderItemRow = ({
	checked = false,
	deliveryGroups = [],
	disabled = false,
	handleSelection,
	handleSubmit,
	orderId,
	orderItem: orderItemProp,
	readonly = false,
	rowIndex = 0,
	spritemap = 'OrderItemRow',
}: IOrderItemRowProps) => {
	const {observer, onOpenChange, open} = useModal();
	const [isChecked, setIsChecked] = useState(checked);
	const [orderItem, setOrderItem] = useState<IOrderItem>(orderItemProp);

	const finalizeSave = useCallback(
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
					await ServiceProvider.DeliveryCartAPI('v1')
						.deleteItemById(
							existingOrderItemDeliveryGroup.orderItemId
						)
						.then(() => {
							delete orderItemDeliveryGroups[deliveryGroupId];

							Liferay.fire(CURRENT_ORDER_UPDATED, {
								order: {id: orderId},
								updatedFromCart: false,
							});
							Liferay.fire(ORDER_INFORMATION_ALTERED);
						})
						.catch((error: IAPIResponseError) => {
							showError(error);

							existingOrderItemDeliveryGroup.quantity =
								existingOrderItemDeliveryGroup.originalQuantity;
						});
				}
				else {
					await ServiceProvider.DeliveryCartAPI('v1')
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

							Liferay.fire(CURRENT_ORDER_UPDATED, {
								order: {id: orderId},
								updatedFromCart: false,
							});
							Liferay.fire(ORDER_INFORMATION_ALTERED);
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
					await ServiceProvider.DeliveryCartAPI('v1')
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

							Liferay.fire(CURRENT_ORDER_UPDATED, {
								order: {id: orderId},
								updatedFromCart: false,
							});
							Liferay.fire(ORDER_INFORMATION_ALTERED);
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
				quantity: calculateOrderItemQuantity(orderItemDeliveryGroups),
			};

			setOrderItem(internalOrderItem);

			handleSubmit(internalOrderItem);
		},
		[handleSubmit]
	);

	const debouncedFinalizeSave = useMemo(
		() =>
			debounce(
				async (
					currentDeliveryGroup,
					deliveryGroupId,
					orderId,
					orderItem,
					orderItemDeliveryGroups,
					quantity
				) =>
					finalizeSave(
						currentDeliveryGroup,
						deliveryGroupId,
						orderId,
						orderItem,
						orderItemDeliveryGroups,
						quantity
					),
				500
			),
		[finalizeSave]
	);

	const handleInternalSelection = useCallback(() => {
		setIsChecked((prevState) => {
			return !prevState;
		});

		handleSelection(orderItem.id);
	}, [handleSelection, orderItem]);

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

			debouncedFinalizeSave(
				currentDeliveryGroup,
				deliveryGroupId,
				orderId,
				orderItem,
				orderItemDeliveryGroups,
				quantity
			);
		},
		[deliveryGroups, debouncedFinalizeSave, orderId, orderItem]
	);

	useEffect(() => {
		setIsChecked(checked);
	}, [checked]);

	useEffect(() => {
		setOrderItem(orderItemProp);
	}, [orderItemProp]);

	return (
		<ClayTable.Row
			className={classNames({
				'row-checked': isChecked,
			})}
			data-qa-id={`orderItem${orderItem.id}Row`}
			key={orderItem.id}
		>
			{!readonly && (
				<ClayTable.Cell className="td-selection">
					<ClayCheckbox
						checked={isChecked}
						data-qa-id={`row${rowIndex}Select`}
						disabled={disabled}
						onChange={() => handleInternalSelection()}
					/>
				</ClayTable.Cell>
			)}

			<ClayTable.Cell aria-label="sku-name" className="td-sku-name">
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

						{!readonly && (
							<DropDown
								trigger={
									<ClayButtonWithIcon
										aria-label={Liferay.Language.get(
											'actions'
										)}
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
											(deliveryGroups?.length || 0) <=
												1 ||
											!!orderItem.settings
												?.allowedQuantities?.length ||
											orderItem.quantity <
												(orderItem.settings
													?.minQuantity || 1) *
													deliveryGroups?.length
										}
										key={orderItem.id + '_splitQuantity'}
										onClick={() => {
											openConfirmModal({
												message: Liferay.Language.get(
													'if-the-total-quantity-cannot-be-equally-distributed,-any-remaining-units-will-be-allocated-to-the-primary-delivery-group'
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
											(deliveryGroups?.length || 0) <=
												1 ||
											!!orderItem.settings
												?.allowedQuantities?.length ||
											orderItem.quantity <
												(orderItem.settings
													?.minQuantity || 1) *
													deliveryGroups?.length
												? Liferay.Language.get(
														'the-item-s-quantity-is-not-valid-for-the-number-of-delivery-groups'
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
													'by-resetting-the-rows,-all-columns-will-be-set-to-zero,-except-the-first-one'
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
											(deliveryGroups?.length || 0) <=
												1 ||
											!(orderItem.deliveryGroups || {})[
												deliveryGroups[0].id
											] ||
											(orderItem.settings?.maxQuantity ||
												1) <
												(orderItem.deliveryGroups ||
													{})[deliveryGroups[0].id]
													.quantity *
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

											handleSubmit(
												internalOrderItem,
												true
											);
										}}
										title={
											(deliveryGroups?.length || 0) <=
												1 ||
											!(orderItem.deliveryGroups || {})[
												deliveryGroups[0].id
											] ||
											(orderItem.settings?.maxQuantity ||
												1) <
												(orderItem.deliveryGroups ||
													{})[deliveryGroups[0].id]
													.quantity *
													deliveryGroups?.length
												? Liferay.Language.get(
														'the-item-s-quantity-is-not-valid-for-the-number-of-delivery-groups'
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
													'by-removing-the-item,-it-will-disappear-from-the-list-of-ordered-items'
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
						)}
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
				className="td-quantity"
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
