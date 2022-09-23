/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import concatPageSizePagination from '../common/utils/concatPageSizePagination';
import readPageSizePagination from '../common/utils/readPageSizePagination';

export const orderItemsTypePolicy = {
	OrderItem: {
		fields: {
			customFields: {
				read(customFields) {
					return customFields.reduce(
						(fieldsAccumulator, currentField) => ({
							...fieldsAccumulator,
							[currentField.name]: currentField.customValue.data,
						}),
						{}
					);
				},
			},
			options: {
				read(options) {
					return JSON.parse(options);
				},
			},
		},
		keyFields: ['externalReferenceCode'],
	},
	OrderItemPage: {
		fields: {
			items: {
				...readPageSizePagination(),
				...concatPageSizePagination(),
			},
		},
	},
};

export const orderItemsQueryTypePolicy = {
	orderItems: {
		keyArgs: ['filter'],
	},
};
