/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.contacts.service.impl;

import com.liferay.osb.faro.contacts.model.ContactsCardTemplate;
import com.liferay.osb.faro.contacts.service.base.ContactsCardTemplateLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Kong
 */
@Component(
	property = "model.class.name=com.liferay.osb.faro.contacts.model.ContactsCardTemplate",
	service = AopService.class
)
public class ContactsCardTemplateLocalServiceImpl
	extends ContactsCardTemplateLocalServiceBaseImpl {

	@Override
	public ContactsCardTemplate addContactsCardTemplate(
		long groupId, long userId, String name, String settings, int type) {

		long contactsCardTemplateId = counterLocalService.increment();

		ContactsCardTemplate contactsCardTemplate =
			contactsCardTemplatePersistence.create(contactsCardTemplateId);

		contactsCardTemplate.setGroupId(groupId);
		contactsCardTemplate.setUserId(userId);
		contactsCardTemplate.setName(name);
		contactsCardTemplate.setSettings(settings);
		contactsCardTemplate.setType(type);

		return contactsCardTemplatePersistence.update(contactsCardTemplate);
	}

	@Override
	public void deleteContactsCardTemplates(long groupId) {
		contactsCardTemplatePersistence.removeByGroupId(groupId);
	}

	@Override
	public List<ContactsCardTemplate> getContactsCardTemplates(
		String contactsCardTemplateIds) {

		List<ContactsCardTemplate> contactsCardTemplates = new ArrayList<>();

		for (String contactsCardTemplateId :
				StringUtil.split(contactsCardTemplateIds)) {

			ContactsCardTemplate contactsCardTemplate =
				contactsCardTemplatePersistence.fetchByPrimaryKey(
					Long.valueOf(contactsCardTemplateId));

			if (contactsCardTemplate != null) {
				contactsCardTemplates.add(contactsCardTemplate);
			}
		}

		return contactsCardTemplates;
	}

	@Override
	public ContactsCardTemplate updateContactsCardTemplate(
			long contactsCardTemplateId, String name, String settings, int type)
		throws PortalException {

		ContactsCardTemplate contactsCardTemplate =
			contactsCardTemplatePersistence.findByPrimaryKey(
				contactsCardTemplateId);

		contactsCardTemplate.setName(name);
		contactsCardTemplate.setSettings(settings);
		contactsCardTemplate.setType(type);

		return contactsCardTemplatePersistence.update(contactsCardTemplate);
	}

}