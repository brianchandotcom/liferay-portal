/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/common/I18n';
import {
	addHighPriorityContact,
	deleteHighPriorityContacts,
} from '~/common/services/liferay/graphql/queries';

const HIGH_PRIORITY_CONTACT_CATEGORIES = {
	criticalIncident: i18n.translate('critical-incident'),
	privacyBreach: i18n.translate('privacy-breach'),
	securityBreach: i18n.translate('security-breach'),
};

const removeHighPriorityContactsList = async (client, item) => {
	return client.mutate({
		context: {
			displaySuccess: false,
			type: 'liferay-rest',
		},
		mutation: deleteHighPriorityContacts,
		variables: {
			highPriorityContactsId: item,
		},
	});
};

const addHighPriorityContactsList = async (client, item) => {
	return client.mutate({
		context: {
			displaySuccess: false,
			type: 'liferay-rest',
		},
		mutation: addHighPriorityContact,
		variables: {
			HighPriorityContacts: {
				contactsCategory: {
					key: item.category.key,
					name: item.category.name,
				},
				r_userToHighPriorityContacts_userId: item.id,
			},
		},
	});
};

export {
	addHighPriorityContactsList,
	HIGH_PRIORITY_CONTACT_CATEGORIES,
	removeHighPriorityContactsList,
};
