/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import {ClayInput} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';

// @ts-ignore

import {CommerceServiceProvider} from 'commerce-frontend-js';
import React, {useCallback, useEffect, useState} from 'react';

import './Multishipping.scss';
import AddDeliveryGroupButton from './AddDeliveryGroupButton';
import DeliveryGroupHeaderCell from './DeliveryGroupHeaderCell';
import {showError} from './ErrorMessage';
import OrderItemRow from './OrderItemRow';
import {
	IAPIResponseError,
	IDeliveryGroup,
	IOrderItem,
	IOrderItemAPIResponse,
	IOrderItemDeliveryGroup,
} from './Types';

const MAX_DELIVERY_GROUPS = 20;

interface IMultishippingProps {
	accountId: number;
	namespace?: string;
	orderId: number;
	readonly?: boolean;
	spritemap?: string;
}

export function formatCartItem(
	deliveryGroup: IDeliveryGroup,
	orderItem: IOrderItem,
	quantity: number = 1
) {
	return {
		deliveryGroup: deliveryGroup.name,
		options: orderItem.options,
		quantity: Number(
			Number(quantity).toFixed(orderItem.skuUnitOfMeasure?.precision || 0)
		),
		replacedSkuId: orderItem.replacedSkuId || 0,
		requestedDeliveryDate: deliveryGroup.deliveryDate,
		shippingAddressId: deliveryGroup.addressId,
		skuId: orderItem.skuId,
		skuUnitOfMeasure: orderItem.skuUnitOfMeasure,
	};
}

const createRequestData = (
	deliveryGroups: Array<IDeliveryGroup>,
	items: Array<IOrderItem>
) => {
	const data: Array<IOrderItem> = [];

	items.forEach((orderItem) => {
		for (const [deliveryGroupId, orderItemConf] of Object.entries(
			orderItem.deliveryGroups || {}
		)) {
			let deliveryGroup = null;

			if (0 === Number(deliveryGroupId)) {
				deliveryGroup = deliveryGroups[0];
			}
			else {
				deliveryGroup = deliveryGroups.find((item) => {
					return item.id === Number(deliveryGroupId);
				});
			}

			if (deliveryGroup && orderItemConf.quantity > 0) {
				data.push({
					deliveryGroup: deliveryGroup.name,
					id: orderItemConf.orderItemId,
					options: orderItemConf.options,
					quantity: orderItemConf.quantity,
					replacedSkuId: orderItemConf.replacedSkuId,
					requestedDeliveryDate: deliveryGroup.deliveryDate,
					shippingAddressId: deliveryGroup.addressId,
					skuId: orderItemConf.skuId,
					skuUnitOfMeasure: orderItemConf.skuUnitOfMeasure,
				});
			}
		}
	});

	return data;
};

const Multishipping = ({
	accountId,
	namespace = '',
	orderId,
	readonly = false,
	spritemap,
}: IMultishippingProps) => {
	const [deliveryGroups, setDeliveryGroups] = useState<Array<IDeliveryGroup>>(
		[]
	);
	const [filterFormattedOrderItems, setFilteredFormattedOrderItems] =
		useState<Array<IOrderItem>>([]);
	const [formattedOrderItems, setFormattedOrderItems] = useState<
		Array<IOrderItem>
	>([]);
	const [loading, setLoading] = useState(true);
	const [pagination, setPagination] = useState({
		currentPage: 1,
		pageSize: 20,
	});
	const [saving, setSaving] = useState(false);
	const [search, setSearch] = useState('');

	const prepareData = useCallback(async (items: Array<IOrderItem>) => {
		const formattedDeliveryGroups: Array<IDeliveryGroup> = [];
		const formattedItems: Array<IOrderItem> = [];

		items.forEach((orderItem) => {
			let deliveryGroup = formattedDeliveryGroups.find((item) => {
				return (
					item.name === (orderItem.deliveryGroup || '') &&
					item.deliveryDate ===
						(orderItem.requestedDeliveryDate || '') &&
					item.addressId === orderItem.shippingAddressId
				);
			});

			if (!deliveryGroup && orderItem.shippingAddressId > 0) {
				deliveryGroup = {
					addressId: orderItem.shippingAddressId,
					deliveryDate: orderItem.requestedDeliveryDate || '',
					id: Math.floor(Math.random() * 100000000),
					name: orderItem.deliveryGroup || '',
				};

				formattedDeliveryGroups.push(deliveryGroup);
			}

			let formattedItem = formattedItems.find((item) => {
				return (
					item.skuId === orderItem.skuId &&
					item.skuUnitOfMeasure?.key ===
						orderItem.skuUnitOfMeasure?.key &&
					item.options === orderItem.options &&
					orderItem.deliveryGroup !== ''
				);
			});

			if (
				!formattedItem ||
				(formattedItem &&
					deliveryGroup &&
					(formattedItem.deliveryGroups || {})[deliveryGroup.id])
			) {
				formattedItem = {
					...orderItem,
					deliveryGroups: {},
					quantity: 0,
				};

				formattedItems.push(formattedItem);
			}

			if (deliveryGroup) {
				formattedItem.deliveryGroups = {
					...formattedItem.deliveryGroups,
					[deliveryGroup.id]: {
						options: orderItem.options,
						orderItemId: orderItem.id,
						originalQuantity: orderItem.quantity,
						quantity: orderItem.quantity,
						replacedSkuId: orderItem.replacedSkuId || 0,
						skuId: orderItem.skuId,
						skuUnitOfMeasure: orderItem.skuUnitOfMeasure,
					} as IOrderItemDeliveryGroup,
				};
			}
			else {
				formattedItem.deliveryGroups = {
					[0]: {
						options: orderItem.options,
						orderItemId: orderItem.id,
						originalQuantity: orderItem.quantity,
						quantity: orderItem.quantity,
						replacedSkuId: orderItem.replacedSkuId || 0,
						skuId: orderItem.skuId,
						skuUnitOfMeasure: orderItem.skuUnitOfMeasure,
					},
				};
			}

			formattedItem.quantity =
				formattedItem.quantity + orderItem.quantity;
		});

		if (formattedDeliveryGroups.length) {
			formattedDeliveryGroups.sort((item1, item2) => {
				return item1.name.localeCompare(item2.name);
			});

			if (formattedItems.find((item) => item.deliveryGroup === '')) {
				try {

					// eslint-disable-next-line @typescript-eslint/no-use-before-define
					await updateFullCart(
						createRequestData(
							formattedDeliveryGroups,
							formattedItems
						)
					);

					return;
				}
				catch (error) {
					console.error(error);

					showError(error as IAPIResponseError);
				}
			}
		}

		setDeliveryGroups(formattedDeliveryGroups);
		setFormattedOrderItems(
			formattedItems.sort(
				(item1, item2) =>
					item1.sku?.localeCompare(item2.sku || '') ||
					item1.id - item2.id
			)
		);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const loadOrderItemData = useCallback(() => {
		setLoading(true);

		CommerceServiceProvider.DeliveryCartAPI('v1')
			.getItemsByCartId(orderId, {pageSize: -1})
			.then(async (response: IOrderItemAPIResponse) => {
				await prepareData(response.items);
			})
			.catch((error: IAPIResponseError) => {
				console.error(error);

				showError(error);
			})
			.finally(() => {
				setLoading(false);
			});
	}, [orderId, prepareData]);

	const updateFullCart = useCallback(
		async (data) => {
			setSaving(true);

			await CommerceServiceProvider.DeliveryCartAPI('v1')
				.updateCartById(
					orderId,
					{
						cartItems: data,
					},
					{pageSize: -1}
				)
				.then(async (response: IOrderItemAPIResponse) => {
					await prepareData(response.cartItems);
				})
				.finally(() => {
					setSaving(false);
				});
		},
		[orderId, prepareData]
	);

	const handleDeleteDeliveryGroup = useCallback(
		async (deliveryGroup) => {
			try {
				if (deliveryGroups.length === 1) {
					await updateFullCart(
						formattedOrderItems.map((item) => {
							return formatCartItem(
								{
									addressId: 0,
									deliveryDate: '',
									id: 0,
									name: '',
								},
								item,
								item.quantity
							);
						})
					);
				}
				else {
					const updatedDeliveryGroups = deliveryGroups.filter(
						(item) => {
							return item.id !== deliveryGroup.id;
						}
					);

					await updateFullCart(
						createRequestData(
							updatedDeliveryGroups,
							formattedOrderItems
						)
					);
				}
			}
			catch (error) {
				console.error(error);

				showError(error as IAPIResponseError);
			}
		},
		[deliveryGroups, formattedOrderItems, updateFullCart]
	);

	const handlePaginationDeltaChange = useCallback((value) => {
		setPagination((prevState) => ({
			...prevState,
			pageSize: value,
		}));
	}, []);

	const handlePaginationPageChange = useCallback((value) => {
		setPagination((prevState) => ({
			...prevState,
			currentPage: value,
		}));
	}, []);

	const handleRowUpdate = useCallback((orderItem: IOrderItem) => {
		setFormattedOrderItems((prevState) => {
			return prevState.map((item) => {
				if (item.id === orderItem.id) {
					return orderItem;
				}

				return item;
			});
		});
	}, []);

	const handleSubmitDeliveryGroup = useCallback(
		async (deliveryGroup: IDeliveryGroup) => {
			const deliveryGroupsState = [...deliveryGroups];

			const index = deliveryGroupsState.findIndex(
				(item: IDeliveryGroup) => item.id === deliveryGroup.id
			);

			if (index >= 0) {
				deliveryGroupsState[index] = deliveryGroup;

				try {
					await updateFullCart(
						createRequestData(
							deliveryGroupsState,
							formattedOrderItems
						)
					);
				}
				catch (error) {
					console.error(error);

					showError(error as IAPIResponseError);
				}
			}
			else {
				deliveryGroup.id = new Date().getTime();
				deliveryGroupsState.push(deliveryGroup);

				setDeliveryGroups(deliveryGroupsState);

				if (deliveryGroupsState.length === 1) {
					try {
						await updateFullCart(
							formattedOrderItems.map((item) => {
								return formatCartItem(
									deliveryGroup,
									item,
									item.quantity
								);
							})
						);
					}
					catch (error) {
						console.error(error);

						showError(error as IAPIResponseError);

						setDeliveryGroups([]);

						return;
					}
				}

				Liferay.Util.openToast({
					message: Liferay.Language.get(
						'your-request-completed-successfully'
					),
				});
			}
		},
		[deliveryGroups, formattedOrderItems, updateFullCart]
	);

	useEffect(() => {
		loadOrderItemData();
	}, [loadOrderItemData]);

	useEffect(() => {
		if (search) {
			setFilteredFormattedOrderItems(
				formattedOrderItems.filter((item) => {
					return (
						(item.name || '')
							.toLowerCase()
							.indexOf(search.toLowerCase()) >= 0 ||
						(item.sku || '')
							.toLowerCase()
							?.indexOf(search.toLowerCase()) >= 0
					);
				})
			);
		}
		else {
			setFilteredFormattedOrderItems(formattedOrderItems);
		}
	}, [formattedOrderItems, search]);

	const managementBar = (
		<div className="management-bar-wrapper">
			<>
				<ClayManagementToolbar>
					<ClayManagementToolbar.Search>
						<ClayInput.Group>
							<ClayInput.GroupItem>
								<ClayInput
									aria-label="search"
									className="form-control"
									disabled={loading}
									onChange={({target: {value}}) => {
										setSearch(value);
									}}
									type="text"
									value={search}
								/>
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</ClayManagementToolbar.Search>

					<ClayManagementToolbar.ItemList>
						<ClayManagementToolbar.Item>
							<AddDeliveryGroupButton
								accountId={accountId}
								disabled={
									loading ||
									readonly ||
									saving ||
									deliveryGroups.length >= MAX_DELIVERY_GROUPS
								}
								handleSubmit={handleSubmitDeliveryGroup}
								hasManageAddressesPermission={true}
								namespace={namespace}
								spritemap={spritemap}
							/>
						</ClayManagementToolbar.Item>
					</ClayManagementToolbar.ItemList>
				</ClayManagementToolbar>
			</>
		</div>
	);

	const view = (
		<div className="data-set-content-wrapper">
			{formattedOrderItems.length ? (
				<ClayTable
					borderedColumns
					borderless
					className="order-items-table"
					striped
				>
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell headingCell key="sku">
								<div className="align-items-center d-flex flex-nowrap">
									<div className="flex-grow-1">
										<div className="text-nowrap text-truncate">
											{Liferay.Language.get('sku')}
										</div>
									</div>
								</div>
							</ClayTable.Cell>

							<ClayTable.Cell headingCell key="quantity">
								<div className="align-items-center d-flex flex-nowrap">
									<div className="flex-grow-1">
										<div className="text-nowrap text-truncate">
											{Liferay.Language.get('quantity')}
										</div>
									</div>
								</div>
							</ClayTable.Cell>

							{deliveryGroups.map((deliveryGroup) => (
								<DeliveryGroupHeaderCell
									accountId={accountId}
									deliveryGroup={deliveryGroup}
									disabled={readonly}
									handleDeleteDeliveryGroup={
										handleDeleteDeliveryGroup
									}
									handleSubmitDeliveryGroup={
										handleSubmitDeliveryGroup
									}
									key={deliveryGroup.id}
									saving={saving}
								/>
							))}
						</ClayTable.Row>
					</ClayTable.Head>

					<ClayTable.Body>
						{filterFormattedOrderItems
							.slice(
								(pagination.currentPage - 1) *
									pagination.pageSize,
								pagination.currentPage * pagination.pageSize
							)
							.map((orderItem, currentIndex) => (
								<OrderItemRow
									deliveryGroups={deliveryGroups}
									disabled={readonly || saving}
									handleSubmit={handleRowUpdate}
									key={orderItem.id}
									orderId={orderId}
									orderItem={orderItem}
									rowIndex={currentIndex}
								/>
							))}
					</ClayTable.Body>
				</ClayTable>
			) : (
				<ClayEmptyState
					description={Liferay.Language.get(
						'sorry,-no-results-were-found'
					)}
					imgSrc={
						Liferay.ThemeDisplay.getPathThemeImages() +
						'/states/search_state.svg'
					}
					title={Liferay.Language.get('no-results-found')}
				/>
			)}
		</div>
	);

	const paginationComponent = (
		<div className="data-set-pagination-wrapper">
			<ClayPaginationBarWithBasicItems
				activeDelta={pagination.pageSize}
				deltas={[4, 8, 20, 40, 60].map((size) => ({
					label: size,
				}))}
				ellipsisBuffer={3}
				onActiveChange={handlePaginationPageChange}
				onDeltaChange={handlePaginationDeltaChange}
				totalItems={filterFormattedOrderItems.length}
			/>
		</div>
	);

	return (
		<div className="data-set data-set-fluid multi-shipping-container">
			{managementBar}

			<div className="container-fluid container-fluid-max-xl">
				{loading ? (
					<ClayLoadingIndicator
						data-qa-id="loadingSpinner"
						displayType="secondary"
						size="sm"
					/>
				) : (
					<>
						{view}

						{paginationComponent}
					</>
				)}
			</div>
		</div>
	);
};

export default Multishipping;
