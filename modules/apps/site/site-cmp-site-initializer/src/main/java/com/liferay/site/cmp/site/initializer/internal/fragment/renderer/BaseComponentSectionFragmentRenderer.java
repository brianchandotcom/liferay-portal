/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.frontend.taglib.react.servlet.taglib.ComponentTag;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.subscription.service.SubscriptionLocalService;
import com.liferay.taglib.servlet.PageContextFactoryUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
public abstract class BaseComponentSectionFragmentRenderer
	implements FragmentRenderer {

	@Override
	public String getLabel(Locale locale) {
		return language.get(locale, getLabelKey());
	}

	@Override
	public boolean isSelectable(HttpServletRequest httpServletRequest) {
		return false;
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		String layoutMode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		if (Objects.equals(layoutMode, Constants.READ)) {
			return;
		}

		try {
			PrintWriter printWriter = httpServletResponse.getWriter();

			printWriter.write("<div><span aria-hidden=\"true\" class=\"");
			printWriter.write("loading-animation\"></span>");

			ComponentTag componentTag = new ComponentTag();

			componentTag.setModule(
				StringBundler.concat(
					"{", getComponentName(httpServletRequest), "} from ",
					getModuleName()));
			componentTag.setPageContext(
				PageContextFactoryUtil.create(
					httpServletRequest, httpServletResponse));
			componentTag.setProps(
				getProps(fragmentRendererContext, httpServletRequest));
			componentTag.setServletContext(servletContext);

			componentTag.doStartTag();

			componentTag.doEndTag();

			printWriter.write("</div>");
		}
		catch (IOException | RuntimeException exception) {
			throw exception;
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}
	}

	protected void addSubscribeActionItem(
			HttpServletRequest httpServletRequest, JSONArray jsonArray,
			String label1, String label2, ObjectDefinition objectDefinition,
			ObjectEntry objectEntry, String redirectURL,
			ThemeDisplay themeDisplay, String title)
		throws PortalException {

		if (!objectEntryService.hasModelResourcePermission(
				objectEntry, ActionKeys.SUBSCRIBE)) {

			return;
		}

		String restContextPath = StringBundler.concat(
			"/o", objectDefinition.getRESTContextPath(), "/scopes/",
			objectEntry.getGroupId(), "/by-external-reference-code/",
			objectEntry.getExternalReferenceCode());

		if (!subscriptionLocalService.isSubscribed(
				objectEntry.getCompanyId(), themeDisplay.getUserId(),
				objectEntry.getModelClassName(),
				objectEntry.getObjectEntryId())) {

			jsonArray.put(
				JSONUtil.put(
					"href", restContextPath + "/subscribe"
				).put(
					"label", LanguageUtil.get(httpServletRequest, label1)
				).put(
					"redirect", redirectURL
				).put(
					"successMessage",
					LanguageUtil.format(
						httpServletRequest, "you-are-successfully-watching-x",
						StringBundler.concat("<strong>", title, "</strong>"))
				).put(
					"symbolLeft", "bell-on"
				).put(
					"target", "asyncPost"
				));
		}
		else {
			jsonArray.put(
				JSONUtil.put(
					"href", restContextPath + "/unsubscribe"
				).put(
					"label", LanguageUtil.get(httpServletRequest, label2)
				).put(
					"redirect", redirectURL
				).put(
					"symbolLeft", "bell-off"
				).put(
					"target", "asyncPost"
				));
		}
	}

	protected abstract String getComponentName(
		HttpServletRequest httpServletRequest);

	protected abstract String getLabelKey();

	protected abstract String getModuleName();

	protected abstract Map<String, Object> getProps(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest)
		throws Exception;

	@Reference
	protected Language language;

	@Reference
	protected ObjectEntryService objectEntryService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.cmp.site.initializer)"
	)
	protected ServletContext servletContext;

	@Reference
	protected SubscriptionLocalService subscriptionLocalService;

}