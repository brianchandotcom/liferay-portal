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

package com.liferay.knowledge.base.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBTemplateServiceUtil;
import com.liferay.knowledge.base.web.internal.security.permission.resource.AdminPermission;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBFolderPermission;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class KBAdminManagementToolbarDisplayContext {

	public KBAdminManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		PortletConfig portletConfig) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_portletConfig = portletConfig;

		_request = liferayPortletRequest.getHttpServletRequest();

		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public CreationMenu getCreationMenu() throws PortalException {
		return new CreationMenu() {
			{
				long kbFolderClassNameId = PortalUtil.getClassNameId(
					KBFolderConstants.getClassName());

				long parentResourceClassNameId = ParamUtil.getLong(
					_request, "parentResourceClassNameId", kbFolderClassNameId);

				long parentResourcePrimKey = ParamUtil.getLong(
					_request, "parentResourcePrimKey",
					KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

				boolean hasAddKBArticlePermission = false;
				boolean hasAddKBFolderPermission = false;

				PermissionChecker permissionChecker =
					_themeDisplay.getPermissionChecker();

				if (parentResourceClassNameId == kbFolderClassNameId) {
					hasAddKBArticlePermission = KBFolderPermission.contains(
						permissionChecker, _themeDisplay.getScopeGroupId(),
						parentResourcePrimKey, KBActionKeys.ADD_KB_ARTICLE);
					hasAddKBFolderPermission = KBFolderPermission.contains(
						permissionChecker, _themeDisplay.getScopeGroupId(),
						parentResourcePrimKey, KBActionKeys.ADD_KB_FOLDER);
				}
				else {
					hasAddKBArticlePermission = AdminPermission.contains(
						permissionChecker, _themeDisplay.getScopeGroupId(),
						KBActionKeys.ADD_KB_ARTICLE);
				}

				if (hasAddKBFolderPermission) {
					addDropdownItem(
						dropdownItem -> {
							PortletURL addFolderURL =
								_liferayPortletResponse.createRenderURL();

							addFolderURL.setParameter(
								"mvcPath", "/admin/common/edit_folder.jsp");
							addFolderURL.setParameter(
								"redirect", PortalUtil.getCurrentURL(_request));
							addFolderURL.setParameter(
								"parentResourceClassNameId",
								String.valueOf(
									PortalUtil.getClassNameId(
										KBFolderConstants.getClassName())));
							addFolderURL.setParameter(
								"parentResourcePrimKey",
								String.valueOf(parentResourcePrimKey));

							dropdownItem.setHref(addFolderURL);

							dropdownItem.setLabel(
								LanguageUtil.get(_request, "folder"));
						});
				}

				if (hasAddKBArticlePermission) {
					String templatePath = _portletConfig.getInitParameter(
						"template-path");

					addDropdownItem(
						dropdownItem -> {
							PortletURL addBasicKBArticleURL =
								_liferayPortletResponse.createRenderURL();

							addBasicKBArticleURL.setParameter(
								"mvcPath", templatePath + "edit_article.jsp");

							addBasicKBArticleURL.setParameter(
								"redirect", PortalUtil.getCurrentURL(_request));
							addBasicKBArticleURL.setParameter(
								"parentResourceClassNameId",
								String.valueOf(parentResourceClassNameId));
							addBasicKBArticleURL.setParameter(
								"parentResourcePrimKey",
								String.valueOf(parentResourcePrimKey));

							dropdownItem.setHref(addBasicKBArticleURL);

							dropdownItem.setLabel(
								LanguageUtil.get(_request, "basic-article"));
						});

					OrderByComparator<KBTemplate> obc =
						OrderByComparatorFactoryUtil.create(
							"KBTemplate", "title", false);

					List<KBTemplate> kbTemplates =
						KBTemplateServiceUtil.getGroupKBTemplates(
							_themeDisplay.getScopeGroupId(), QueryUtil.ALL_POS,
							QueryUtil.ALL_POS, obc);

					if (!kbTemplates.isEmpty()) {
						for (KBTemplate kbTemplate : kbTemplates) {
							addDropdownItem(
								dropdownItem -> {
									PortletURL addKBArticleURL =
										_liferayPortletResponse.
											createRenderURL();

									addKBArticleURL.setParameter(
										"mvcPath",
										templatePath + "edit_article.jsp");
									addKBArticleURL.setParameter(
										"redirect",
										PortalUtil.getCurrentURL(_request));
									addKBArticleURL.setParameter(
										"parentResourceClassNameId",
										String.valueOf(
											parentResourceClassNameId));
									addKBArticleURL.setParameter(
										"parentResourcePrimKey",
										String.valueOf(parentResourcePrimKey));
									addKBArticleURL.setParameter(
										"kbTemplateId",
										String.valueOf(
											kbTemplate.getKbTemplateId()));

									dropdownItem.setHref(addKBArticleURL);

									dropdownItem.setLabel(
										LanguageUtil.get(
											_request, kbTemplate.getTitle()));
								});
						}
					}
				}

				if ((parentResourceClassNameId == kbFolderClassNameId) &&
					AdminPermission.contains(
						permissionChecker, _themeDisplay.getScopeGroupId(),
						KBActionKeys.ADD_KB_ARTICLE)) {

					addDropdownItem(
						dropdownItem -> {
							PortletURL importURL =
								_liferayPortletResponse.createRenderURL();

							importURL.setParameter(
								"mvcPath", "/admin/import.jsp");
							importURL.setParameter(
								"redirect", PortalUtil.getCurrentURL(_request));

							importURL.setParameter(
								"parentKBFolderId",
								String.valueOf(parentResourcePrimKey));

							dropdownItem.setHref(importURL);

							dropdownItem.setLabel(
								LanguageUtil.get(_request, "import"));
						});
				}
			}
		};
	}

	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final PortletConfig _portletConfig;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}