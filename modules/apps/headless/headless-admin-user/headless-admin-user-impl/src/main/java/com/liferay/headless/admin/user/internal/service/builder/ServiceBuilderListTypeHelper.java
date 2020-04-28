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

package com.liferay.headless.admin.user.internal.service.builder;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.ListTypeService;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = ServiceBuilderListTypeHelper.class)
public class ServiceBuilderListTypeHelper {

	public long getServiceBuilderListTypeId(String value, String type) {
		ListType listType = _listTypeLocalService.addListType(value, type);

		return listType.getListTypeId();
	}

	public String getServiceBuilderListTypeMessage(
			long listTypeId, Locale locale)
		throws Exception {

		if (listTypeId == 0) {
			return null;
		}

		ListType listType = _listTypeService.getListType(listTypeId);

		return LanguageUtil.get(locale, listType.getName());
	}

	public long toServiceBuilderListTypeId(
		String defaultName, String name, String type) {

		ListType listType = _listTypeLocalService.getListType(name, type);

		if (listType == null) {
			listType = _listTypeLocalService.getListType(defaultName, type);
		}

		if (listType != null) {
			return listType.getListTypeId();
		}

		return 0;
	}

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private ListTypeService _listTypeService;

}