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

package com.liferay.analytics.reports.web.internal.portlet;

import com.liferay.analytics.reports.constants.AnalyticsReportsWebKeys;
import com.liferay.analytics.reports.info.item.ClassNameClassPKInfoItemIdentifier;
import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsPortletKeys;
import com.liferay.analytics.reports.web.internal.display.context.AnalyticsReportsDisplayContext;
import com.liferay.info.item.InfoItemReference;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Optional;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 * @author Sarai Díaz
 */
@Component(
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=false",
		"javax.portlet.display-name=Content Performance",
		"javax.portlet.name=" + AnalyticsReportsPortletKeys.ANALYTICS_REPORTS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.version=3.0"
	},
	service = {AnalyticsReportsPortlet.class, Portlet.class}
)
public class AnalyticsReportsPortlet extends MVCPortlet {

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		String layoutMode = ParamUtil.getString(
			originalHttpServletRequest, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.PREVIEW)) {
			return;
		}

		InfoItemReference infoItemReference = _getInfoItemReference(
			httpServletRequest);

		renderRequest.setAttribute(
			AnalyticsReportsWebKeys.ANALYTICS_REPORTS_DISPLAY_CONTEXT,
			new AnalyticsReportsDisplayContext(
				infoItemReference, _portal, renderRequest, renderResponse));

		super.doDispatch(renderRequest, renderResponse);
	}

	private String _getClassName(HttpServletRequest httpServletRequest) {
		String className = ParamUtil.getString(httpServletRequest, "className");

		if (Validator.isNull(className)) {
			return Layout.class.getName();
		}

		return className;
	}

	private long _getClassPK(HttpServletRequest httpServletRequest) {
		long classPK = ParamUtil.getLong(httpServletRequest, "classPK");

		if (classPK == 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return themeDisplay.getPlid();
		}

		return classPK;
	}

	private String _getClassTypeName(HttpServletRequest httpServletRequest) {
		return ParamUtil.getString(httpServletRequest, "classTypeName");
	}

	private InfoItemReference _getInfoItemReference(
		HttpServletRequest httpServletRequest) {

		return Optional.ofNullable(
			(InfoItemReference)httpServletRequest.getAttribute(
				AnalyticsReportsWebKeys.ANALYTICS_INFO_ITEM_REFERENCE)
		).orElseGet(
			() -> Optional.ofNullable(
				_getClassTypeName(httpServletRequest)
			).filter(
				Validator::isNotNull
			).map(
				classTypeName -> new InfoItemReference(
					_getClassName(httpServletRequest),
					new ClassNameClassPKInfoItemIdentifier(
						classTypeName, _getClassPK(httpServletRequest)))
			).orElseGet(
				() -> new InfoItemReference(
					_getClassName(httpServletRequest),
					_getClassPK(httpServletRequest))
			)
		);
	}

	@Reference
	private Portal _portal;

}