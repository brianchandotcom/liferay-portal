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

package com.liferay.portal.model;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Cleydyr de Albuquerque
 */
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onBeforeUpdate(User user) {
		if (!user.isDefaultUser() || !isLocaleUpdated(user)) {
			return;
		}

		try {
			verifyGroupsNameMap(user);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to update groups name maps for company with ID " +
					user.getCompanyId(),
				pe);
		}
	}

	protected boolean isLocaleUpdated(User user) {
		Locale newLocale = user.getLocale();
		Locale defaultLocale = LocaleUtil.getDefault();

		if (newLocale.equals(defaultLocale)) {
			return false;
		}

		return true;
	}

	protected void verifyGroupsNameMap(User user) throws PortalException {
		List<Group> groups = GroupLocalServiceUtil.search(
			user.getCompanyId(), null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Group group : groups) {
			Map<Locale, String> nameMap = group.getNameMap();

			if (MapUtil.isEmpty(nameMap)) {
				continue;
			}

			Locale locale = user.getLocale();

			String groupDefaultName = nameMap.get(locale);

			if (Validator.isNotNull(groupDefaultName)) {
				continue;
			}

			String oldGroupDefaultName = nameMap.get(LocaleUtil.getDefault());

			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(5);

				sb.append("No name was found for locale ");
				sb.append(locale);
				sb.append(". Using name ");
				sb.append(oldGroupDefaultName);
				sb.append("instead.");

				_log.warn(sb.toString());
			}

			nameMap.put(locale, oldGroupDefaultName);

			group.setNameMap(nameMap);

			GroupLocalServiceUtil.updateGroup(group);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserModelListener.class);

}