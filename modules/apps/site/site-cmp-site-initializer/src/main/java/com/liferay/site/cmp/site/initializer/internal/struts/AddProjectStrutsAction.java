/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.struts;

import com.liferay.headless.asset.library.dto.v1_0.AssetLibrary;
import com.liferay.headless.asset.library.dto.v1_0.Settings;
import com.liferay.headless.asset.library.resource.v1_0.AssetLibraryResource;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.site.cmp.site.initializer.internal.util.ActionUtil;
import com.liferay.site.cmp.site.initializer.internal.util.CMPHttpComponentsUtil;
import com.liferay.site.cmp.site.initializer.internal.util.SiteInitializerUtil;
import com.liferay.site.initializer.SiteInitializer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(property = "path=/cms/add_project", service = StrutsAction.class)
public class AddProjectStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionService.getObjectDefinition(
				ParamUtil.getLong(httpServletRequest, "objectDefinitionId"));

		if (!StringUtil.equals(
				objectDefinition.getExternalReferenceCode(), "L_CMP_PROJECT")) {

			return null;
		}

		AssetLibraryResource.Builder builder =
			_assetLibraryResourceFactory.create();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = _groupLocalService.getGroup(
			themeDisplay.getCompanyId(), GroupConstants.CMS);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchDefaultLayoutPageTemplateEntry(
					group.getGroupId(),
					PortalUtil.getClassNameId(objectDefinition.getClassName()),
					0);

		if (layoutPageTemplateEntry == null) {
			SiteInitializerUtil.initialize(
				themeDisplay.getCompanyId(), _siteInitializer);
		}

		AssetLibraryResource assetLibraryResource = builder.user(
			themeDisplay.getUser()
		).build();

		AssetLibrary assetLibrary = assetLibraryResource.postAssetLibrary(
			new AssetLibrary() {
				{
					setName(StringUtil::randomString);
					setSettings(
						() -> new Settings() {
							{
								setLogoColor(() -> "outline-0");
								setTrashEnabled(() -> false);
							}
						});
					setType(() -> Type.PROJECT);
				}
			});

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				objectDefinition.getCompanyId(),
				objectDefinition.getStorageType());

		ObjectEntry objectEntry = objectEntryManager.addObjectEntry(
			new DefaultDTOConverterContext(
				false, null, null, null, null,
				themeDisplay.getSiteDefaultLocale(), null,
				themeDisplay.getUser()),
			objectDefinition,
			new ObjectEntry() {
				{
					setObjectEntryFolderExternalReferenceCode(
						() -> ParamUtil.getString(
							httpServletRequest,
							"objectEntryFolderExternalReferenceCode"));
					setStatus(
						() -> new Status() {
							{
								setCode(() -> WorkflowConstants.STATUS_DRAFT);
							}
						});
				}
			},
			String.valueOf(assetLibrary.getSiteId()));

		String editProjectURL =
			ActionUtil.getBaseEditProjectURL(objectDefinition, themeDisplay) +
				objectEntry.getId();

		editProjectURL = CMPHttpComponentsUtil.addParameter(
			httpServletRequest, "action", editProjectURL);
		editProjectURL = CMPHttpComponentsUtil.addParameter(
			httpServletRequest, "redirect", editProjectURL);

		httpServletResponse.sendRedirect(editProjectURL);

		return null;
	}

	@Reference
	private AssetLibraryResource.Factory _assetLibraryResourceFactory;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference(
		target = "(site.initializer.key=com.liferay.site.initializer.cmp)"
	)
	private SiteInitializer _siteInitializer;

}