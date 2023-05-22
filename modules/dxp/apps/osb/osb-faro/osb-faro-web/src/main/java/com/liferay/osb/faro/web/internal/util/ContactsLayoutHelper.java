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

package com.liferay.osb.faro.web.internal.util;

import com.liferay.osb.faro.contacts.model.ContactsCardTemplate;
import com.liferay.osb.faro.contacts.service.ContactsCardTemplateLocalService;
import com.liferay.osb.faro.web.internal.card.template.ContactsCardTemplateManagerHelper;
import com.liferay.osb.faro.web.internal.model.display.contacts.card.template.ContactsLayoutTemplateSettingDisplay;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.UserConstants;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
@Component(service = ContactsLayoutHelper.class)
public class ContactsLayoutHelper {

	public String addContactsCardTemplateIds(long groupId, int type)
		throws Exception {

		List<List<ContactsLayoutTemplateSettingDisplay>>
			contactsLayoutTemplateSettingDisplaysList = new ArrayList<>();

		for (int[] contactsCardTemplateTypes :
				_contactsCardTemplateManagerUtil.
					getDefaultContactsCardTemplateTypes(type)) {

			List<ContactsLayoutTemplateSettingDisplay>
				contactsLayoutTemplateSettingDisplays = new ArrayList<>();

			for (int contactsCardTemplateType : contactsCardTemplateTypes) {
				ContactsCardTemplate contactsCardTemplate =
					_contactsCardTemplateLocalService.addContactsCardTemplate(
						groupId, UserConstants.USER_ID_DEFAULT,
						_contactsCardTemplateManagerUtil.getDefaultName(
							contactsCardTemplateType),
						_contactsCardTemplateManagerUtil.getDefaultSettings(
							contactsCardTemplateType),
						contactsCardTemplateType);

				contactsLayoutTemplateSettingDisplays.add(
					new ContactsLayoutTemplateSettingDisplay(
						contactsCardTemplate.getContactsCardTemplateId(), 0));
			}

			contactsLayoutTemplateSettingDisplaysList.add(
				contactsLayoutTemplateSettingDisplays);
		}

		return JSONUtil.writeValueAsString(
			contactsLayoutTemplateSettingDisplaysList);
	}

	public String addHeaderContactsCardTemplateIds(long groupId, int type) {
		int[] contactsCardTemplateTypes =
			_contactsCardTemplateManagerUtil.
				getDefaultHeaderContactsCardTemplateTypes(type);

		if (contactsCardTemplateTypes.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(
			contactsCardTemplateTypes.length * 2);

		for (int contactsCardTemplateType : contactsCardTemplateTypes) {
			ContactsCardTemplate contactsCardTemplate =
				_contactsCardTemplateLocalService.addContactsCardTemplate(
					groupId, UserConstants.USER_ID_DEFAULT,
					_contactsCardTemplateManagerUtil.getDefaultName(
						contactsCardTemplateType),
					_contactsCardTemplateManagerUtil.getDefaultSettings(
						contactsCardTemplateType),
					contactsCardTemplateType);

			sb.append(contactsCardTemplate.getContactsCardTemplateId());

			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	@Reference
	private ContactsCardTemplateLocalService _contactsCardTemplateLocalService;

	@Reference
	private ContactsCardTemplateManagerHelper _contactsCardTemplateManagerUtil;

}