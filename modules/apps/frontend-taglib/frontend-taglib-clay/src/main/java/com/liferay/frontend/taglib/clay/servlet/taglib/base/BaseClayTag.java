/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.base;

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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Chema Balsas
 */
public abstract class BaseClayTag extends TemplateRendererTag {

	@Override
	public int doStartTag() {
		super.setWrapper(false);

		Map<String, Object> context = getContext();

		if (Validator.isNull(context.get("spritemap"))) {
			HttpServletRequest httpServletRequest = getRequest();

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			putValue("spritemap", themeDisplay.getPathThemeSpritemap());
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

		HttpServletRequest httpServletRequest = getRequest();

		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
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