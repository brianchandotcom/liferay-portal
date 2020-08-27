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

package com.liferay.commerce.product.type.virtual.internal.product.content.contributor;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingLocalService;
import com.liferay.commerce.product.type.virtual.util.VirtualCPTypeHelper;
import com.liferay.commerce.product.util.CPContentContributor;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.product.content.contributor.name=" + SampleFileCPContentContributor.NAME,
	service = CPContentContributor.class
)
public class SampleFileCPContentContributor implements CPContentContributor {

	public static final String NAME = "sampleFile";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public JSONObject getValue(
			CPInstance cpInstance, HttpServletRequest httpServletRequest)
		throws PortalException {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		if (cpInstance == null) {
			return jsonObject;
		}

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			_cpDefinitionVirtualSettingLocalService.
				fetchCPDefinitionVirtualSetting(
					CPInstance.class.getName(), cpInstance.getCPInstanceId());

		if ((cpDefinitionVirtualSetting == null) ||
			!cpDefinitionVirtualSetting.isOverride()) {

			cpDefinitionVirtualSetting =
				_cpDefinitionVirtualSettingLocalService.
					fetchCPDefinitionVirtualSetting(
						CPDefinition.class.getName(),
						cpInstance.getCPDefinitionId());
		}

		if (cpDefinitionVirtualSetting == null) {
			jsonObject.put(NAME, StringPool.BLANK);

			return jsonObject;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String sampleURL = _virtualCPTypeHelper.getSampleURL(
			cpInstance.getCPDefinitionId(), cpInstance.getCPInstanceId(),
			themeDisplay);

		if (Validator.isNull(sampleURL)) {
			jsonObject.put(NAME, StringPool.BLANK);

			return jsonObject;
		}

		String sampleFile = _getSampleFileHtml(sampleURL, httpServletRequest);

		jsonObject.put(NAME, sampleFile);

		return jsonObject;
	}

	private String _getSampleFileHtml(
		String sampleURL, HttpServletRequest httpServletRequest) {

		StringBundler sb = new StringBundler(6);

		sb.append("<a class=\"btn btn-primary\" href=\"");
		sb.append(sampleURL);
		sb.append(StringPool.QUOTE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(LanguageUtil.get(httpServletRequest, "download-sample-file"));
		sb.append("</a>");

		return sb.toString();
	}

	@Reference
	private CPDefinitionVirtualSettingLocalService
		_cpDefinitionVirtualSettingLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private VirtualCPTypeHelper _virtualCPTypeHelper;

}