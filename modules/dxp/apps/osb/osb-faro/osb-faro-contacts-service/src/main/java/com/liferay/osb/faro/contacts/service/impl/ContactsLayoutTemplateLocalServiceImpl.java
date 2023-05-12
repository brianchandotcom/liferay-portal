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

import com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate;
import com.liferay.osb.faro.contacts.service.base.ContactsLayoutTemplateLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Kong
 */
@Component(
	property = "model.class.name=com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate",
	service = AopService.class
)
public class ContactsLayoutTemplateLocalServiceImpl
	extends ContactsLayoutTemplateLocalServiceBaseImpl {

	@Override
	public ContactsLayoutTemplate addContactsLayoutTemplate(
		long groupId, long userId, String headerContactsCardTemplateIds,
		String name, String settings, int type) {

		long contactsLayoutTemplateId = counterLocalService.increment();

		ContactsLayoutTemplate contactsLayoutTemplate =
			contactsLayoutTemplatePersistence.create(contactsLayoutTemplateId);

		contactsLayoutTemplate.setGroupId(groupId);
		contactsLayoutTemplate.setUserId(userId);
		contactsLayoutTemplate.setHeaderContactsCardTemplateIds(
			headerContactsCardTemplateIds);
		contactsLayoutTemplate.setName(name);
		contactsLayoutTemplate.setSettings(settings);
		contactsLayoutTemplate.setType(type);

		return contactsLayoutTemplatePersistence.update(contactsLayoutTemplate);
	}

	@Override
	public void deleteContactsLayoutTemplates(long groupId) {
		contactsLayoutTemplatePersistence.removeByGroupId(groupId);
	}

	@Override
	public List<ContactsLayoutTemplate> getContactsLayoutTemplates(
		long groupId, int type, int start, int end) {

		return contactsLayoutTemplatePersistence.findByG_T(
			groupId, type, start, end);
	}

	@Override
	public ContactsLayoutTemplate updateContactsLayoutTemplate(
			long contactsLayoutTemplateId, String headerContactsCardTemplateIds,
			String name, String settings)
		throws PortalException {

		ContactsLayoutTemplate contactsLayoutTemplate =
			contactsLayoutTemplatePersistence.findByPrimaryKey(
				contactsLayoutTemplateId);

		contactsLayoutTemplate.setHeaderContactsCardTemplateIds(
			headerContactsCardTemplateIds);
		contactsLayoutTemplate.setName(name);
		contactsLayoutTemplate.setSettings(settings);

		return contactsLayoutTemplatePersistence.update(contactsLayoutTemplate);
	}

}