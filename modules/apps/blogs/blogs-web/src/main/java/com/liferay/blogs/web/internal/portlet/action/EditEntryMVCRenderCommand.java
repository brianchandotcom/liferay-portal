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

package com.liferay.blogs.web.internal.portlet.action;

import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfigurationFactory;
import com.liferay.blogs.configuration.BlogsFileUploadsConfiguration;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.exception.NoSuchEntryException;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.settings.BlogsGroupServiceSettings;
import com.liferay.blogs.web.internal.display.context.BlogsEditEntryDisplayContext;
import com.liferay.blogs.web.internal.helper.BlogsItemSelectorHelper;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@Component(
	configurationPid = "com.liferay.blogs.configuration.BlogsFileUploadsConfiguration",
	immediate = true,
	property = {
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_ADMIN,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_AGGREGATOR,
		"mvc.command.name=/blogs/edit_entry"
	},
	service = MVCRenderCommand.class
)
public class EditEntryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			BlogsEntry entry = ActionUtil.getEntry(renderRequest);

			if (entry != null) {
				_blogsEntryModelResourcePermission.check(
					themeDisplay.getPermissionChecker(), entry,
					ActionKeys.UPDATE);
			}

			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(renderRequest);

			renderRequest.setAttribute(
				BlogsEditEntryDisplayContext.class.getName(),
				new BlogsEditEntryDisplayContext(
					_getAssetAutoTaggerConfiguration(renderRequest), entry,
					_blogsFileUploadsConfiguration,
					BlogsGroupServiceSettings.getInstance(
						themeDisplay.getScopeGroupId()),
					_blogsItemSelectorHelper, httpServletRequest,
					_portal.getLiferayPortletResponse(renderResponse)));
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchEntryException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(renderRequest, exception.getClass());

				return "/blogs/error.jsp";
			}

			throw new PortletException(exception);
		}

		return "/blogs/edit_entry.jsp";
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_blogsFileUploadsConfiguration = ConfigurableUtil.createConfigurable(
			BlogsFileUploadsConfiguration.class, properties);
	}

	private AssetAutoTaggerConfiguration _getAssetAutoTaggerConfiguration(
		RenderRequest renderRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _assetAutoTaggerConfigurationFactory.
			getGroupAssetAutoTaggerConfiguration(themeDisplay.getSiteGroup());
	}

	@Reference
	private AssetAutoTaggerConfigurationFactory
		_assetAutoTaggerConfigurationFactory;

	@Reference(target = "(model.class.name=com.liferay.blogs.model.BlogsEntry)")
	private volatile ModelResourcePermission<BlogsEntry>
		_blogsEntryModelResourcePermission;

	private volatile BlogsFileUploadsConfiguration
		_blogsFileUploadsConfiguration;

	@Reference
	private BlogsItemSelectorHelper _blogsItemSelectorHelper;

	@Reference
	private Portal _portal;

}