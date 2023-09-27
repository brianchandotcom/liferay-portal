/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.model.listener.BaseEntityModelListener;
import com.liferay.analytics.message.sender.model.listener.EntityModelListener;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ContactLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(service = {EntityModelListener.class, ModelListener.class})
public class ContactModelListener extends BaseEntityModelListener<Contact> {

	@Override
	public List<String> getAttributeNames(long companyId) {
		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationRegistry.getAnalyticsConfiguration(companyId);

		if (ArrayUtil.isEmpty(
				analyticsConfiguration.syncedContactFieldNames())) {

			return _attributeNames;
		}

		return Arrays.asList(analyticsConfiguration.syncedContactFieldNames());
	}

	@Override
	protected Contact getModel(long id) throws Exception {
		return _contactLocalService.getContact(id);
	}

	@Override
	protected String getPrimaryKeyName() {
		return "contactId";
	}

	@Override
	protected boolean isExcluded(Contact contact) {
		User user = userLocalService.fetchUser(contact.getClassPK());

		if (!isUserActive(user)) {
			return true;
		}

		return isUserExcluded(user);
	}

	private static final List<String> _attributeNames = Arrays.asList(
		"birthday", "classNameId", "classPK", "companyId", "createDate",
		"emailAddress", "employeeNumber", "employeeStatusId", "facebookSn",
		"firstName", "hoursOfOperation", "jabberSn", "jobClass", "jobTitle",
		"lastName", "male", "middleName", "modifiedDate", "parentContactId",
		"prefixListTypeId", "skypeSn", "smsSn", "suffixListTypeId", "twitterSn",
		"userId", "userName");

	@Reference
	private ContactLocalService _contactLocalService;

}