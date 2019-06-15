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

package com.liferay.frontend.taglib.clay.servlet.taglib.soy.base;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.clay.internal.ClayTagContextContributorsProvider;
import com.liferay.frontend.taglib.clay.internal.js.loader.modules.extender.npm.NPMResolverProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.contributor.ClayTagContextContributor;
import com.liferay.frontend.taglib.soy.servlet.taglib.TemplateRendererTag;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletResponse;

/**
 * @author Chema Balsas
 */
public abstract class BaseClayTag extends TemplateRendererTag {

	@Override
	public int doStartTag() {
		super.setWrapper(false);

		Map<String, Object> context = getContext();

		if (Validator.isNull(context.get("spritemap"))) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			String pathThemeImages = themeDisplay.getPathThemeImages();

			putValue("spritemap", pathThemeImages.concat("/clay/icons.svg"));
		}

		String namespace = getNamespace();
		String[] namespacedParams = getNamespacedParams();

		if (Validator.isNotNull(namespace) && (namespacedParams != null)) {
			for (String parameterName : namespacedParams) {
				String parameterValue = (String)context.get(parameterName);

				putValue(parameterName, namespace + parameterValue);
			}
		}

		String contributorKey = GetterUtil.getString(
			context.get("contributorKey"));

		if (Validator.isNotNull(contributorKey)) {
			_populateContext(contributorKey);
		}

		setTemplateNamespace(_componentBaseName + ".render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		NPMResolver npmResolver = NPMResolverProvider.getNPMResolver();

		if (npmResolver == null) {
			return StringPool.BLANK;
		}

		return npmResolver.resolveModuleName(
			StringBundler.concat(
				"clay-", _moduleBaseName, "/lib/", _componentBaseName));
	}

	public String getNamespace() {
		if (_namespace != null) {
			return _namespace;
		}

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (portletResponse != null) {
			_namespace = portletResponse.getNamespace();
		}

		return _namespace;
	}

	public void setComponentBaseName(String componentBaseName) {
		_componentBaseName = componentBaseName;
	}

	public void setContributorKey(String contributorKey) {
		putValue("contributorKey", contributorKey);
	}

	public void setData(Map<String, String> data) {
		putValue("data", data);
	}

	public void setDefaultEventHandler(String defaultEventHandler) {
		if (Validator.isNotNull(defaultEventHandler)) {
			putValue("defaultEventHandler", defaultEventHandler);
		}
	}

	public void setElementClasses(String elementClasses) {
		putValue("elementClasses", elementClasses);
	}

	public void setId(String id) {
		putValue("id", id);
	}

	public void setModuleBaseName(String moduleBaseName) {
		_moduleBaseName = moduleBaseName;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	public void setSpritemap(String spritemap) {
		putValue("spritemap", spritemap);
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_componentBaseName = null;
		_moduleBaseName = null;
		_namespace = null;
	}

	protected String[] getNamespacedParams() {
		return null;
	}

	private void _populateContext(String contributorKey) {
		List<ClayTagContextContributor> clayTagContextContributors =
			ClayTagContextContributorsProvider.getClayTagContextContributors(
				contributorKey);

		if (clayTagContextContributors == null) {
			return;
		}

		for (ClayTagContextContributor clayTagContextContributor :
				clayTagContextContributors) {

			clayTagContextContributor.populate(getContext());
		}
	}

	private String _componentBaseName;
	private String _moduleBaseName;
	private String _namespace;

}