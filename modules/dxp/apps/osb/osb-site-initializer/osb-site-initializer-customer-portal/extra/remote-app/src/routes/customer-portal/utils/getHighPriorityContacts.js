/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/common/I18n';
import {
	addHighPriorityContact,
	deleteHighPriorityContacts,
} from '~/common/services/liferay/graphql/queries';
import {
	associateContactRoleNameByEmailByProject,
	deleteContactRoleNameByEmailByProject,
} from '../../../../src/common/services/liferay/rest/raysource/LicenseKeys';

const HIGH_PRIORITY_CONTACT_CATEGORIES = {
	criticalIncident: i18n.translate('critical-incident'),
	privacyBreach: i18n.translate('privacy-breach'),
	securityBreach: i18n.translate('security-breach'),
};

const removeContactRole = async (
	item,
	project,
	sessionId,
	provisioningServerAPI
) => {
	return await deleteContactRoleNameByEmailByProject({
		accountKey: project.accountKey,
		emailURI: encodeURI(item.email),
		provisioningServerAPI,
		rolesToDelete: item.filter.role,
		sessionId,
	});
};

const associateContactRole = async (
	item,
	project,
	sessionId,
	provisioningServerAPI
) => {
	return await associateContactRoleNameByEmailByProject({
		accountKey: project.accountKey,
		emailURI: encodeURI(item.email),
		firstName: item.label,
		lastName: item.label,
		provisioningServerAPI,
		roleName: item.category.role,
		sessionId,
	});
};

const removeHighPriorityContactsList = async (client, item) => {
	return client.mutate({
		context: {
			displaySuccess: false,
			type: 'liferay-rest',
		},
		mutation: deleteHighPriorityContacts,
		variables: {
			highPriorityContactsId: item.objectId,
		},
	});
};

const addHighPriorityContactsList = async (client, item, project) => {
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
				r_accountEntryToHighPriorityContacts_accountEntryERC:
					project.accountKey,
				r_userToHighPriorityContacts_userId: item.id,
			},
		},
	});
};

export {
	removeContactRole,
	associateContactRole,
	addHighPriorityContactsList,
	HIGH_PRIORITY_CONTACT_CATEGORIES,
	removeHighPriorityContactsList,
};
