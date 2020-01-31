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

package com.liferay.analytics.reports.web.internal.product.navigation.control.menu;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItemTracker;
import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsPortletKeys;
import com.liferay.analytics.reports.web.internal.util.AnalyticsReportsUtil;
import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.taglib.aui.IconTag;
import com.liferay.taglib.portletext.RuntimeTag;
import com.liferay.taglib.util.BodyBottomTag;

import java.io.IOException;
import java.io.Writer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.USER,
		"product.navigation.control.menu.entry.order:Integer=500"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class AnalyticsReportsProductNavigationControlMenuEntry
	extends BaseProductNavigationControlMenuEntry {

	@Override
	public String getLabel(Locale locale) {
		return null;
	}

	@Override
	public String getURL(HttpServletRequest httpServletRequest) {
		return null;
	}

	@Override
	public boolean includeBody(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		BodyBottomTag bodyBottomTag = new BodyBottomTag();

		bodyBottomTag.setOutputKey("analyticsReportsPanel");

		try {
			bodyBottomTag.doBodyTag(
				httpServletRequest, httpServletResponse,
				this::_processBodyBottomTagBody);
		}
		catch (JspException jspException) {
			throw new IOException(jspException);
		}

		return true;
	}

	@Override
	public boolean includeIcon(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		Map<String, String> values = new HashMap<>();

		if (isPanelStateOpen(httpServletRequest)) {
			values.put("analyticsReportsPanelURL", StringPool.BLANK);
			values.put("cssClass", "active");
		}
		else {
			PortletURL portletURL = _portletURLFactory.create(
				httpServletRequest,
				AnalyticsReportsPortletKeys.ANALYTICS_REPORTS,
				RenderRequest.RENDER_PHASE);

			portletURL.setParameter("mvcPath", "/analytics_reports_panel.jsp");

			try {
				portletURL.setWindowState(LiferayWindowState.EXCLUSIVE);
			}
			catch (WindowStateException windowStateException) {
				ReflectionUtil.throwException(windowStateException);
			}

			InfoDisplayObjectProvider infoDisplayObjectProvider =
				(InfoDisplayObjectProvider)httpServletRequest.getAttribute(
					AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER);

			portletURL.setParameter(
				"classNameId",
				String.valueOf(infoDisplayObjectProvider.getClassNameId()));
			portletURL.setParameter(
				"classPK",
				String.valueOf(infoDisplayObjectProvider.getClassPK()));

			values.put("analyticsReportsPanelURL", portletURL.toString());

			values.put("cssClass", StringPool.BLANK);
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_portal.getLocale(httpServletRequest), getClass());

		values.put(
			"title",
			_html.escape(_language.get(resourceBundle, "content-performance")));

		IconTag iconTag = new IconTag();

		iconTag.setCssClass("icon-monospaced");
		iconTag.setImage("analytics");
		iconTag.setMarkupView("lexicon");

		try {
			values.put(
				"iconTag",
				iconTag.doTagAsString(httpServletRequest, httpServletResponse));
		}
		catch (JspException jspException) {
			ReflectionUtil.throwException(jspException);
		}

		values.put("portletNamespace", _portletNamespace);

		Writer writer = httpServletResponse.getWriter();

		writer.write(StringUtil.replace(_ICON_TMPL_CONTENT, "${", "}", values));

		return true;
	}

	public boolean isPanelStateOpen(HttpServletRequest httpServletRequest) {
		String analyticsReportsPanelState = SessionClicks.get(
			httpServletRequest, "com.liferay.analytics.reports.web_panelState",
			"closed");

		if (Objects.equals(analyticsReportsPanelState, "open")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (!layout.isTypeAssetDisplay()) {
			return false;
		}

		if (!_hasAnalyticsReportsInfoItem(httpServletRequest)) {
			return false;
		}

		if (isEmbeddedPersonalApplicationLayout(layout)) {
			return false;
		}

		String layoutMode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.EDIT)) {
			return false;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				httpServletRequest);

		boolean hidePanel = GetterUtil.getBoolean(
			portalPreferences.getValue(
				AnalyticsReportsPortletKeys.ANALYTICS_REPORTS, "hide-panel"));

		if (!AnalyticsReportsUtil.isAnalyticsEnabled(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId()) &&
			hidePanel) {

			return false;
		}

		return super.isShow(httpServletRequest);
	}

	@Activate
	protected void activate() {
		_portletNamespace = _portal.getPortletNamespace(
			AnalyticsReportsPortletKeys.ANALYTICS_REPORTS);
	}

	private boolean _hasAnalyticsReportsInfoItem(
		HttpServletRequest httpServletRequest) {

		InfoDisplayObjectProvider infoDisplayObjectProvider =
			(InfoDisplayObjectProvider)httpServletRequest.getAttribute(
				AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER);

		if (infoDisplayObjectProvider == null) {
			return false;
		}

		AnalyticsReportsInfoItem analyticsReportsInfoItem =
			_analyticsReportsInfoItemTracker.getAnalyticsReportsInfoItem(
				_portal.getClassName(
					infoDisplayObjectProvider.getClassNameId()));

		if ((analyticsReportsInfoItem == null) ||
			(infoDisplayObjectProvider.getDisplayObject() == null)) {

			return false;
		}

		return true;
	}

	private void _processBodyBottomTagBody(PageContext pageContext) {
		try {
			HttpServletRequest httpServletRequest =
				(HttpServletRequest)pageContext.getRequest();

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				_portal.getLocale(httpServletRequest), getClass());

			pageContext.setAttribute("resourceBundle", resourceBundle);

			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write("<div class=\"");

			if (isPanelStateOpen(httpServletRequest)) {
				jspWriter.write(
					"lfr-has-analytics-reports-panel open-admin-panel ");
			}

			jspWriter.write(
				StringBundler.concat(
					"hidden-print lfr-admin-panel lfr-product-menu-panel ",
					"lfr-analytics-reports-panel sidenav-fixed ",
					"sidenav-menu-slider sidenav-right\" id=\""));

			jspWriter.write(_portletNamespace);

			jspWriter.write("analyticsReportsPanelId\">");
			jspWriter.write(
				"<div class=\"sidebar sidebar-default sidenav-menu " +
					"sidebar-sm\">");

			RuntimeTag runtimeTag = new RuntimeTag();

			runtimeTag.setPortletName(
				AnalyticsReportsPortletKeys.ANALYTICS_REPORTS);

			runtimeTag.doTag(pageContext);

			jspWriter.write("</div></div>");
		}
		catch (Exception exception) {
			ReflectionUtil.throwException(exception);
		}
	}

	private static final String _ICON_TMPL_CONTENT = StringUtil.read(
		AnalyticsReportsProductNavigationControlMenuEntry.class, "icon.tmpl");

	@Reference
	private AnalyticsReportsInfoItemTracker _analyticsReportsInfoItemTracker;

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	private String _portletNamespace;

	@Reference
	private PortletURLFactory _portletURLFactory;

}