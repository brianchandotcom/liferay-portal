/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	CommerceServiceProvider,
	ItemFinder,
	commerceEvents,
} from 'commerce-frontend-js';
import {openToast} from 'frontend-js-web';

export default function ({
	apiUrl,
	dataSetId,
	paymentMethodGroupRelId,
	rootPortletId,
}) {
	const paymentMethodGroupRelTermsResource =
		CommerceServiceProvider.AdminChannelAPI('v1');

	function selectItem(term) {
		const termData = {
			paymentMethodGroupRelId,
			termExternalReferenceCode: term.externalReferenceCode,
			termId: term.id,
		};

		return paymentMethodGroupRelTermsResource
			.addPaymentMethodGroupRelTerm(paymentMethodGroupRelId, termData)
			.then(() => {
				Liferay.fire(commerceEvents.FDS_UPDATE_DISPLAY, {
					id: dataSetId,
				});
			})
			.catch((error) => {
				const errorsMap = {
					'the-qualifier-is-already-linked': Liferay.Language.get(
						'the-qualifier-is-already-linked'
					),
				};

				openToast({
					message:
						errorsMap[error.message] ||
						Liferay.Language.get('an-unexpected-error-occurred'),
					title: Liferay.Language.get('error'),
					type: 'danger',
				});
			});
	}

	ItemFinder('itemFinder', 'item-finder-root-payment-terms', {
		apiUrl,
		getSelectedItems: () => Promise.resolve([]),
		inputPlaceholder: Liferay.Language.get('find-a-payment-term'),
		itemCreation: false,
		itemSelectedMessage: Liferay.Language.get('payment-terms-selected'),
		itemsKey: 'id',
		linkedDataSetsId: [dataSetId],
		onItemSelected: selectItem,
		pageSize: 10,
		panelHeaderLabel: Liferay.Language.get('add-payment-terms'),
		portletId: rootPortletId,
		schema: [
			{
				fieldName: 'name',
			},
		],
		titleLabel: Liferay.Language.get('add-existing-payment-term'),
	});
}
