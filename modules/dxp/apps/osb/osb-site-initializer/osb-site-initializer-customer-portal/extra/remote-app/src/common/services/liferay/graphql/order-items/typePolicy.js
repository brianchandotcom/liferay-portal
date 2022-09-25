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
			options: {
				read(options) {
					let finalOptions = options;

					if (typeof options === 'string') {
						finalOptions = JSON.parse(options);
					}

					if (!finalOptions.instanceSize) {
						finalOptions.instanceSize = 0;
					}

					return finalOptions;
				},
			},
			reducedCustomFields: {
				read(_, {readField}) {
					const customFields = readField('customFields');

					if (Array.isArray(customFields)) {
						return customFields.reduce(
							(customFieldsAccumulator, currentCustomField) => ({
								...customFieldsAccumulator,
								[readField(
									'name',
									currentCustomField
								)]: readField(
									'data',
									readField('customValue', currentCustomField)
								),
							}),
							{}
						);
					}

					return {
						[readField('name', customFields)]: readField(
							'data',
							readField('customValue', customFields)
						),
					};
				},
			},
		},
		keyFields: ['externalReferenceCode'],
	},
	OrderItemPage: {
		fields: {
			items: {
				...concatPageSizePagination(),
				...readPageSizePagination(),
			},
		},
	},
};

export const orderItemsQueryTypePolicy = {
	orderItems: {
		keyArgs: ['filter'],
	},
};
