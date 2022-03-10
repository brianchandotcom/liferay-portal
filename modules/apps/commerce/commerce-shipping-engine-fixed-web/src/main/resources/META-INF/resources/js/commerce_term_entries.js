/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ServiceProvider from 'commerce-frontend-js/ServiceProvider/index';
import itemFinder from 'commerce-frontend-js/components/item_finder/entry';
import {UPDATE_DATASET_DISPLAY} from 'commerce-frontend-js/utilities/eventsDefinitions';
import {openToast} from 'frontend-js-web';

export default function ({
	apiUrl,
	datasetId,
	rootPortletId,
	shippingFixedOptionId,
}) {
	const shippingFixedOptionTermsResource = ServiceProvider.AdminChannelAPI(
		'v1'
	);

	function selectItem(term) {
		const termData = {
			shippingFixedOptionId,
			termExternalReferenceCode: term.externalReferenceCode,
			termId: term.id,
		};

		return shippingFixedOptionTermsResource
			.addShippingFixedOptionTerm(shippingFixedOptionId, termData)
			.then(() => {
				Liferay.fire(UPDATE_DATASET_DISPLAY, {
					id: datasetId,
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

	itemFinder('itemFinder', 'item-finder-root-delivery-terms', {
		apiUrl,
		getSelectedItems: () => Promise.resolve([]),
		inputPlaceholder: Liferay.Language.get('find-a-delivery-term'),
		itemCreation: false,
		itemSelectedMessage: Liferay.Language.get('delivery-terms-selected'),
		itemsKey: 'id',
		linkedDatasetsId: [datasetId],
		onItemSelected: selectItem,
		pageSize: 10,
		panelHeaderLabel: Liferay.Language.get('add-delivery-terms'),
		portletId: rootPortletId,
		schema: [
			{
				fieldName: 'name',
			},
		],
		titleLabel: Liferay.Language.get('add-existing-delivery-term'),
	});
}
