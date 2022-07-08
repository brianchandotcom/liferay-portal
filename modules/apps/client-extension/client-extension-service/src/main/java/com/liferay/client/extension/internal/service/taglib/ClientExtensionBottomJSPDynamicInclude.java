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

package com.liferay.client.extension.internal.service.taglib;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.internal.service.taglib.util.ClientExtensionDynamicIncludeUtil;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.type.GlobalJSCET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = DynamicInclude.class)
public class ClientExtensionBottomJSPDynamicInclude implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PrintWriter printWriter = httpServletResponse.getWriter();

		List<ClientExtensionEntryRel> clientExtensionEntryRels =
			ClientExtensionDynamicIncludeUtil.getClientExtensionEntryRels(
				themeDisplay.getLayout(),
				ClientExtensionEntryConstants.TYPE_GLOBAL_JS);

		for (ClientExtensionEntryRel clientExtensionEntryRel :
				clientExtensionEntryRels) {

			GlobalJSCET globalJSCET = (GlobalJSCET)_cetManager.getCET(
				clientExtensionEntryRel.getCompanyId(),
				clientExtensionEntryRel.getCETExternalReferenceCode());

			if (globalJSCET == null) {
				continue;
			}

			UnicodeProperties typeSettingsUnicodeProperties =
				UnicodePropertiesBuilder.create(
					true
				).fastLoad(
					clientExtensionEntryRel.getTypeSettings()
				).build();

			if (!Objects.equals(
					typeSettingsUnicodeProperties.getProperty(
						"scriptLocation", StringPool.BLANK),
					"bottom")) {

				continue;
			}

			printWriter.print("<script ");

			String loadType = typeSettingsUnicodeProperties.getProperty(
				"loadType", StringPool.BLANK);

			if (Validator.isNotNull(loadType) &&
				!Objects.equals(loadType, "default")) {

				printWriter.print(loadType);
				printWriter.print(StringPool.SPACE);
			}

			printWriter.print("data-senna-track=\"temporary\" src=\"");
			printWriter.print(globalJSCET.getURL());
			printWriter.print("\" type=\"text/javascript\"></script>");
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/bottom.jsp#post");
	}

	@Reference
	private CETManager _cetManager;

}