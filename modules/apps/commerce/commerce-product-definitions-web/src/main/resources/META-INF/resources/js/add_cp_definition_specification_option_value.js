/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	CommerceServiceProvider,
	commerceEvents,
	slugify,
} from 'commerce-frontend-js';

const AdminCatalogResource = CommerceServiceProvider.AdminCatalogAPI('v1');

function addCPDefinitionSpecificationOptionValue({
	catalogDefaultLanguageId,
	namespace,
	productId,
}) {
	const productSpecificationValue = {
		label: {},
		optionCategoryId: null,
		specificationKey: null,
		value: {},
	};

	Liferay.on(`specification-created`, ({labelValue}) => {
		productSpecificationValue.label[catalogDefaultLanguageId] = labelValue;
		productSpecificationValue.specificationKey = slugify(labelValue);
	});

	Liferay.on(
		`specification-selected`,
		({optionCategoryId, specificationKey}) => {
			productSpecificationValue.optionCategoryId = optionCategoryId;
			productSpecificationValue.specificationKey = specificationKey;
		}
	);

	Liferay.on(`list-type-entry-selected`, ({name_i18n}) => {
		productSpecificationValue.value = name_i18n;
	});

	Liferay.on(`input-value-selected`, ({inputValue}) => {
		productSpecificationValue.value[catalogDefaultLanguageId] = inputValue;
	});

	Liferay.provide(window, `${namespace}apiSubmit`, async () => {
		AdminCatalogResource.createProductSpecification(
			productId,
			productSpecificationValue
		)
			.then(() => {
				window.parent.Liferay.fire(commerceEvents.CLOSE_MODAL, {
					successNotification: {
						message: Liferay.Language.get(
							'your-request-completed-successfully'
						),
						showSuccessNotification: true,
					},
				});
			})
			.catch((error) => {
				window.parent.Liferay.fire(commerceEvents.CLOSE_MODAL);

				const message =
					error.message ??
					Liferay.Language.get('an-unexpected-error-occurred');

				window.parent.Liferay.Util.openToast({
					message,
					type: 'danger',
				});
			});
	});
}

export default addCPDefinitionSpecificationOptionValue;
