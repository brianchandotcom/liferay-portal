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

package com.liferay.commerce.product.content.web.configuration;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(service = CPContentConfigurationHelper.class)
public class CPContentConfigurationHelper {

	public String getCPTypeDisplayStyle(
		CPContentPortletInstanceConfiguration
			cpContentPortletInstanceConfiguration,
		String cpTypeName) {

		int cpTypeIndex = getCPTypeIndex(
			cpContentPortletInstanceConfiguration, cpTypeName);

		if (cpTypeIndex < 0) {
			return StringPool.BLANK;
		}

		String[] displayStyles = StringUtil.split(
			cpContentPortletInstanceConfiguration.displayStyle());

		return displayStyles[cpTypeIndex];
	}

	public long getCPTypeDisplayStyleGroupId(
		CPContentPortletInstanceConfiguration
			cpContentPortletInstanceConfiguration,
		String cpTypeName) {

		int cpTypeIndex = getCPTypeIndex(
			cpContentPortletInstanceConfiguration, cpTypeName);

		if (cpTypeIndex < 0) {
			return 0;
		}

		String[] displayStylesGroupId = StringUtil.split(
			cpContentPortletInstanceConfiguration.displayStyleGroupId());

		return GetterUtil.getLong(displayStylesGroupId[cpTypeIndex]);
	}

	protected int getCPTypeIndex(
		CPContentPortletInstanceConfiguration
			cpContentPortletInstanceConfiguration,
		String cpTypeName) {

		String[] cpTypes = StringUtil.split(
			cpContentPortletInstanceConfiguration.cpType());

		List<String> cpTypeList = Arrays.asList(cpTypes);

		return cpTypeList.indexOf(cpTypeName);
	}

}