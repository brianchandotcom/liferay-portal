/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.contacts;

import com.liferay.osb.faro.contacts.model.ContactsCardTemplate;
import com.liferay.osb.faro.contacts.model.constants.ContactsCardTemplateConstants;
import com.liferay.osb.faro.contacts.model.constants.JSONConstants;
import com.liferay.osb.faro.contacts.service.ContactsCardTemplateLocalService;
import com.liferay.osb.faro.web.internal.card.template.ContactsCardTemplateManagerHelper;
import com.liferay.osb.faro.web.internal.card.template.type.ContactsCardTemplateType;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.osb.faro.web.internal.controller.FaroController;
import com.liferay.osb.faro.web.internal.exception.FaroException;
import com.liferay.osb.faro.web.internal.model.display.contacts.card.template.ContactsCardTemplateDisplay;
import com.liferay.osb.faro.web.internal.param.FaroParam;
import com.liferay.osb.faro.web.internal.util.JSONUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;

import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
@Component(
	service = {ContactsCardTemplateController.class, FaroController.class}
)
@Path("/{groupId}/contacts_card_template")
@Produces(MediaType.APPLICATION_JSON)
public class ContactsCardTemplateController extends BaseFaroController {

	@POST
	@RolesAllowed(StringPool.BLANK)
	public ContactsCardTemplateDisplay create(
			@PathParam("groupId") long groupId, @FormParam("name") String name,
			@DefaultValue(JSONConstants.NULL_JSON_OBJECT) @FormParam("settings")
				FaroParam<Map<String, Object>> settingsFaroParam,
			@FormParam("type") int type)
		throws Exception {

		validateType(type);

		ContactsCardTemplate contactsCardTemplate =
			_contactsCardTemplateLocalService.addContactsCardTemplate(
				groupId, getUserId(), name,
				JSONUtil.writeValueAsString(settingsFaroParam.getValue()),
				type);

		return _contactsCardTemplateManagerUtil.getContactsCardTemplateDisplay(
			contactsCardTemplate);
	}

	@DELETE
	@Path("/{id}")
	@RolesAllowed(StringPool.BLANK)
	public ContactsCardTemplateDisplay delete(@PathParam("id") long id)
		throws Exception {

		return _contactsCardTemplateManagerUtil.getContactsCardTemplateDisplay(
			_contactsCardTemplateLocalService.deleteContactsCardTemplate(id));
	}

	@GET
	@Path("/types")
	public List<ContactsCardTemplateType> getTypes() {
		return TransformUtil.transform(
			_contactsCardTemplateManagerUtil.getContactsCardTemplateTypes(),
			contactsCardTemplateType -> {
				if (contactsCardTemplateType.getType() ==
						ContactsCardTemplateConstants.TYPE_PROFILE) {

					return null;
				}

				return contactsCardTemplateType;
			});
	}

	@Path("/{id}")
	@PUT
	@RolesAllowed(StringPool.BLANK)
	public ContactsCardTemplateDisplay update(
			@PathParam("groupId") long groupId, @PathParam("id") long id,
			@FormParam("name") String name,
			@DefaultValue(JSONConstants.NULL_JSON_OBJECT) @FormParam("settings")
				FaroParam<Map<String, Object>> settingsFaroParam,
			@FormParam("type") int type)
		throws Exception {

		if (id == 0) {
			return create(groupId, name, settingsFaroParam, type);
		}

		validateType(type);

		return _contactsCardTemplateManagerUtil.getContactsCardTemplateDisplay(
			_contactsCardTemplateLocalService.updateContactsCardTemplate(
				id, name,
				JSONUtil.writeValueAsString(settingsFaroParam.getValue()),
				type));
	}

	protected void validateType(int type) {
		Map<String, Integer> cardTypes =
			ContactsCardTemplateConstants.getCardTypes();

		if (!cardTypes.containsValue(type)) {
			throw new FaroException("Invalid card template type: " + type);
		}
	}

	@Reference
	private ContactsCardTemplateLocalService _contactsCardTemplateLocalService;

	@Reference
	private ContactsCardTemplateManagerHelper _contactsCardTemplateManagerUtil;

}