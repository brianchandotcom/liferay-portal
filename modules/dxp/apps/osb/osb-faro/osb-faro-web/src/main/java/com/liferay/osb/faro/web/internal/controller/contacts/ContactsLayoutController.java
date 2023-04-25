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

package com.liferay.osb.faro.web.internal.controller.contacts;

import com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate;
import com.liferay.osb.faro.contacts.service.ContactsLayoutTemplateLocalService;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.card.template.ContactsCardTemplateManagerHelper;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.osb.faro.web.internal.controller.FaroController;
import com.liferay.osb.faro.web.internal.model.display.contacts.ContactsLayoutDisplay;
import com.liferay.osb.faro.web.internal.model.display.contacts.card.template.ContactsLayoutTemplateDisplay;
import com.liferay.osb.faro.web.internal.model.display.main.FaroEntityDisplay;
import com.liferay.portal.kernel.model.RoleConstants;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
@Component(service = {ContactsLayoutController.class, FaroController.class})
@Path("/{groupId}/contacts_layout")
@Produces(MediaType.APPLICATION_JSON)
public class ContactsLayoutController extends BaseFaroController {

	@GET
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	public ContactsLayoutDisplay get(
			@PathParam("groupId") long groupId,
			@QueryParam("contactsEntityId") String contactsEntityId,
			@QueryParam("contactsLayoutTemplateId") long
				contactsLayoutTemplateId,
			@QueryParam("type") int type)
		throws Exception {

		ContactsLayoutTemplateDisplay contactsLayoutTemplateDisplay =
			getContactsLayoutTemplateDisplay(
				groupId, contactsLayoutTemplateId, type);

		FaroProject faroProject =
			faroProjectLocalService.getFaroProjectByGroupId(groupId);

		FaroEntityDisplay faroEntityDisplay =
			contactsHelper.getContactsEntityDisplay(
				faroProject, contactsEntityId, type);

		faroEntityDisplay.addProperties(
			contactsLayoutTemplateDisplay.getFieldMappingNames());

		return new ContactsLayoutDisplay(
			faroEntityDisplay, contactsLayoutTemplateDisplay,
			contactsLayoutTemplateDisplay.getContactsCardData(
				faroProject, faroEntityDisplay, contactsEngineClient));
	}

	protected ContactsLayoutTemplateDisplay getContactsLayoutTemplateDisplay(
			long groupId, long contactsLayoutTemplateId, int type)
		throws Exception {

		ContactsLayoutTemplate contactsLayoutTemplate = null;

		if (contactsLayoutTemplateId == 0) {
			List<ContactsLayoutTemplate> contactsLayoutTemplates =
				_contactsLayoutTemplateLocalService.getContactsLayoutTemplates(
					groupId, type, 0, 1);

			contactsLayoutTemplate = contactsLayoutTemplates.get(0);
		}
		else {
			contactsLayoutTemplate =
				_contactsLayoutTemplateLocalService.getContactsLayoutTemplate(
					contactsLayoutTemplateId);
		}

		return new ContactsLayoutTemplateDisplay(
			contactsLayoutTemplate, _contactsCardTemplateManagerUtil);
	}

	@Reference
	private ContactsCardTemplateManagerHelper _contactsCardTemplateManagerUtil;

	@Reference
	private ContactsLayoutTemplateLocalService
		_contactsLayoutTemplateLocalService;

}