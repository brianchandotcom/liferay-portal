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

import {SLA_STATUS_TYPES} from '../../../../utils/constants';
import pagePageSizePagination from '../common/utils/pagePageSizePagination';

export const koroneikiAccountsTypePolicy = {
	C_KoroneikiAccount: {
		fields: {
			accountBriefId: {
				read(_, {readField, toReference}) {
					const accountBriefRef = toReference({
						__typename: 'AccountBrief',
						externalReferenceCode: readField('accountKey'),
					});

					if (accountBriefRef) {
						return readField('id', accountBriefRef);
					}

					const accountRef = toReference({
						__typename:
							'com_liferay_headless_admin_user_dto_v1_0_Account',
						externalReferenceCode: readField('accountKey'),
					});

					return readField('id', accountRef);
				},
			},
			status: {
				read(_, {readField}) {
					if (readField('slaCurrent')) {
						return SLA_STATUS_TYPES.active;
					}

					if (readField('slaFuture')) {
						return SLA_STATUS_TYPES.future;
					}

					return SLA_STATUS_TYPES.expired;
				},
			},
		},
		keyFields: ['accountKey'],
	},
	C_KoroneikiAccountPage: {
		fields: {
			items: {
				...pagePageSizePagination(),
			},
		},
	},
};

export const koroneikiAccountsQueryTypePolicy = {
	koroneikiAccounts: {
		keyArgs: ['filter'],
	},
};
