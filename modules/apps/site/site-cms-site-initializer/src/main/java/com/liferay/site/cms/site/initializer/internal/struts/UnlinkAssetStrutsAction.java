/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.struts;

import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Fábio Alves
 */
@Component(property = "path=/cms/unlink_asset", service = StrutsAction.class)
public class UnlinkAssetStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			ParamUtil.getLong(httpServletRequest, "objectEntryId"));

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectEntry.getObjectDefinitionId());

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				objectDefinition.getCompanyId(),
				objectDefinition.getStorageType());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		objectEntryManager.partialUpdateObjectEntry(
			objectDefinition.getCompanyId(),
			new DefaultDTOConverterContext(
				false, null, null, null, null,
				themeDisplay.getSiteDefaultLocale(), null,
				themeDisplay.getUser()),
			objectEntry.getExternalReferenceCode(), objectDefinition,
			new com.liferay.object.rest.dto.v1_0.ObjectEntry() {
				{
					setKeywords(
						() -> TransformUtil.transformToArray(
							_assetTagLocalService.getTags(
								objectDefinition.getClassName(),
								objectEntry.getObjectEntryId()),
							assetTag -> {
								if (ArrayUtil.contains(
										StringUtil.split(
											ParamUtil.getString(
												httpServletRequest,
												"keywords")),
										assetTag.getName())) {

									return null;
								}

								return assetTag.getName();
							},
							String.class));
				}
			},
			String.valueOf(objectEntry.getGroupId()));

		httpServletResponse.sendRedirect(
			ParamUtil.getString(httpServletRequest, "redirect"));

		return null;
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

}