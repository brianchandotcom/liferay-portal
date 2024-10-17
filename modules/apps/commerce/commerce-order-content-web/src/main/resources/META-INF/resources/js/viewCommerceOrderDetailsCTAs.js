/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceServiceProvider, commerceEvents} from 'commerce-frontend-js';
import {createPortletURL, openModal, openToast} from 'frontend-js-web';

function toggleModalTitleTooltip(isShow) {
	const tooltipElement = document.querySelector(
		'#returnable-items-header-tooltip'
	);

	tooltipElement.classList[isShow ? 'add' : 'remove']('show');
}

async function submitReturnableItems(
	returnableOrderItemsContextParams,
	selectedReturnableItems
) {
	const CommerceReturnResource = CommerceServiceProvider.ReturnAPI();

	const {
		accountEntryId,
		channelGroupId,
		channelId,
		channelName,
		commerceOrderId,
		commerceReturnId,
	} = returnableOrderItemsContextParams;

	const commerceReturn = {
		channelGroupId: parseInt(channelGroupId, 10),
		channelId: parseInt(channelId, 10),
		channelName,
		commerceReturnToCommerceReturnItems: selectedReturnableItems.map(
			({
				id: commerceOrderItemId,
				price: {finalPrice: amount},
				quantity,
			}) => ({
				amount,
				quantity,
				r_accountToCommerceReturnItems_accountEntryId: parseInt(
					accountEntryId,
					10
				),
				r_commerceOrderItemToCommerceReturnItems_commerceOrderItemId:
					commerceOrderItemId,
			})
		),
		r_accountToCommerceReturns_accountEntryId: parseInt(accountEntryId, 10),
		r_commerceOrderToCommerceReturns_commerceOrderId: parseInt(
			commerceOrderId,
			10
		),
	};

	if (parseInt(commerceReturnId, 10)) {
		return CommerceReturnResource.updateItemById(
			commerceReturnId,
			commerceReturn
		);
	}

	return CommerceReturnResource.createItem(commerceReturn);
}

export default function ({
	namespace,
	returnableOrderItemsContextParams,
	viewReturnableOrderItemsURL,
}) {
	const formElement = document[`${namespace}fm`];
	const cmdInputElement = formElement ? formElement[`${namespace}cmd`] : null;

	Liferay.on(`${namespace}editCommerceReturnableItems`, () => {
		window.top[`${namespace}handleCTA`](null, 'makeReturn', null);
	});
	Liferay.on(`${namespace}makeReturn`, ({accountId, orderId}) => {
		window.top[`${namespace}handleCTA`](accountId, 'makeReturn', orderId);
	});

	Liferay.provide(
		window,
		`${namespace}handleCTA`,
		(accountId, cmdValue, orderId) => {
			if (cmdValue === 'makeReturn') {
				let selectedReturnableItems;

				window.top.Liferay.on(
					commerceEvents.SELECTED_RETURNABLE_ITEMS,
					({selectedItems}) => {
						selectedReturnableItems = selectedItems;
					}
				);

				openModal({
					buttons: [
						{
							displayType: 'secondary',
							label: Liferay.Language.get('cancel'),
							type: 'cancel',
						},
						{
							label: Liferay.Language.get('submit'),
							onClick: () => {
								submitReturnableItems(
									accountId && orderId
										? {
												...returnableOrderItemsContextParams,
												accountEntryId: accountId,
												commerceOrderId: orderId,
											}
										: returnableOrderItemsContextParams,
									selectedReturnableItems
								)
									.then((response) => {
										const portletURL = createPortletURL(
											returnableOrderItemsContextParams.redirect,
											{
												commerceReturnId: response.id,
											}
										);
										window.top.location.href =
											portletURL.toString();
									})
									.catch((error) => {
										openToast({
											message:
												error.message ||
												Liferay.Language.get(
													'an-unexpected-error-occurred'
												),
											type: 'danger',
										});
									});
							},
						},
					],
					containerProps: {
						center: true,
						className: 'commerce-modal',
					},
					headerHTML: `
					<div class="d-inline-flex flex-row align-items-center">
						<h1 class="modal-title" id="clay-modal-label-1">${Liferay.Language.get(
							'select-returnable-items'
						)}</h1>
						<span
							class="label-icon lfr-portal-tooltip ml-2 mt-1 small"
							id="returnable-items-header-tooltip-icon"
						>
							${Liferay.Util.getLexiconIconTpl('question-circle-full')}
						</span>
						<div id="returnable-items-header-tooltip" class="position-relative fade tooltip clay-tooltip-right" role="tooltip">
							<div class="arrow"></div>
							<div class="tooltip-inner small">${Liferay.Language.get(
								'only-items-that-support-returns-are-displayed-here'
							)}</div>
						</div>
					</div>
				`,
					height: '32rem',
					iframeBodyCssClass: 'w-100',
					onOpen: () => {
						const tooltipHeaderIcon = document.querySelector(
							'#returnable-items-header-tooltip-icon'
						);

						tooltipHeaderIcon.onmouseover = () =>
							toggleModalTitleTooltip(true);
						tooltipHeaderIcon.onmouseout = () =>
							toggleModalTitleTooltip(false);
					},
					size: 'lg',
					title: Liferay.Language.get('select-returnable-items'),
					url: orderId
						? createPortletURL(viewReturnableOrderItemsURL, {
								commerceOrderId: orderId,
							})
						: viewReturnableOrderItemsURL,
				});
			}
			else {
				cmdInputElement.value = cmdValue;

				submitForm(formElement);
			}
		}
	);
}
