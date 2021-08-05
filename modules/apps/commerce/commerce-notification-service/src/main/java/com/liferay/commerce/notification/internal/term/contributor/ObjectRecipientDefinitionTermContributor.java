/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.notification.internal.term.contributor;

import com.liferay.commerce.order.CommerceDefinitionTermContributor;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Marco Leo
 */
public class ObjectRecipientDefinitionTermContributor
	implements CommerceDefinitionTermContributor {

	public ObjectRecipientDefinitionTermContributor(
		UserGroupLocalService userGroupLocalService,
		UserLocalService userLocalService) {

		_userGroupLocalService = userGroupLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	public Map<String, String> getDefinitionTerms(Locale locale) {
		Map<String, String> map = new HashMap<>();

		List<String> terms = getTerms();

		for (String term : terms) {
			map.put(term, getLabel(term, locale));
		}

		return map;
	}

	@Override
	public String getFilledTerm(String term, Object object, Locale locale)
		throws PortalException {

		ObjectEntry objectEntry = null;

		if (object instanceof ObjectEntry) {
			objectEntry = (ObjectEntry)object;
		}

		if (term.equals(_OBJECT_ENTRY_CREATOR)) {
			return String.valueOf(objectEntry.getUserId());
		}

		if (term.startsWith("[%USER_GROUP_")) {
			String[] s = term.split("_");

			String userGroupName = StringUtil.removeChars(s[2], '%', ']');

			UserGroup userGroup = _userGroupLocalService.getUserGroup(
				objectEntry.getCompanyId(), userGroupName);

			return _getUserIds(userGroup);
		}

		return term;
	}

	@Override
	public String getLabel(String term, Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle, _commerceOrderDefinitionTermsMap.get(term));
	}

	@Override
	public List<String> getTerms() {
		return new ArrayList<>(_commerceOrderDefinitionTermsMap.keySet());
	}

	private String _getUserIds(UserGroup userGroup) throws PortalException {
		List<User> groupUsers = _userLocalService.getUserGroupUsers(
			userGroup.getUserGroupId());

		StringBundler resultsSB = new StringBundler();

		for (User user : groupUsers) {
			resultsSB.append(user.getUserId());
			resultsSB.append(",");
		}

		return resultsSB.toString();
	}

	private static final String _OBJECT_ENTRY_CREATOR =
		"[%OBJECT_ENTRY_CREATOR%]";

	private static final String _USER_GROUP_NAME = "[%USER_GROUP_NAME%]";

	private static final Map<String, String> _commerceOrderDefinitionTermsMap =
		HashMapBuilder.put(
			_OBJECT_ENTRY_CREATOR, "creator"
		).put(
			_USER_GROUP_NAME, "user-group-name"
		).build();

	private final UserGroupLocalService _userGroupLocalService;
	private final UserLocalService _userLocalService;

}