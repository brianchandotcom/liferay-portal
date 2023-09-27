/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.contacts.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lucas Marques de Paula
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.Contact",
	service = ModelDocumentContributor.class
)
public class ContactModelDocumentContributor
	implements ModelDocumentContributor<Contact> {

	@Override
	public void contribute(Document document, Contact contact) {
		if (contact.isUser()) {
			User user = userLocalService.fetchUserByContactId(
				contact.getContactId());

			if ((user == null) || user.isGuestUser() ||
				(user.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

				return;
			}
		}

		document.addKeyword(Field.COMPANY_ID, contact.getCompanyId());
		document.addDate(Field.MODIFIED_DATE, contact.getModifiedDate());
		document.addKeyword(Field.USER_ID, contact.getUserId());
		document.addKeyword(Field.USER_NAME, contact.getFullName());

		document.addText("emailAddress", contact.getEmailAddress());
		document.addText("firstName", contact.getFirstName());
		document.addText("fullName", contact.getFullName());
		document.addText("jobTitle", contact.getJobTitle());
		document.addText("lastName", contact.getLastName());
		document.addText("middleName", contact.getMiddleName());
	}

	@Reference
	protected UserLocalService userLocalService;

}