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