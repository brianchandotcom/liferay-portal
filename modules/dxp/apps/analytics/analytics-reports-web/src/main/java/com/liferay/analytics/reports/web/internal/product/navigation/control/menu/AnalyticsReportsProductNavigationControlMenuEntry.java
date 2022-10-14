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

import com.liferay.analytics.reports.constants.AnalyticsReportsWebKeys;
import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItemTracker;
import com.liferay.analytics.reports.info.item.ClassNameClassPKInfoItemIdentifier;
import com.liferay.analytics.reports.info.item.provider.AnalyticsReportsInfoItemObjectProvider;
import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsPortletKeys;
import com.liferay.analytics.reports.web.internal.info.item.provider.AnalyticsReportsInfoItemObjectProviderTracker;
import com.liferay.analytics.reports.web.internal.util.AnalyticsReportsUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.taglib.aui.IconTag;
import com.liferay.taglib.util.BodyBottomTag;

import java.io.IOException;
import java.io.Writer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;

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
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.USER,
		"product.navigation.control.menu.entry.order:Integer=400"
	},
	service = {
		AnalyticsReportsProductNavigationControlMenuEntry.class,
		ProductNavigationControlMenuEntry.class
	}
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
				pageContext -> {
					try {
						_processBodyBottomTagBody(pageContext);
					}
					catch (Exception exception) {
						throw new ProcessBodyBottomTagBodyException(exception);
					}
				});
		}
		catch (JspException jspException) {
			throw new IOException(jspException);
		}
		catch (ProcessBodyBottomTagBodyException
					processBodyBottomTagBodyException) {

			throw new IOException(processBodyBottomTagBodyException);
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
			values.put("cssClass", "active");
		}
		else {
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

		try {
			values.put(
				"iconTag",
				iconTag.doTagAsString(httpServletRequest, httpServletResponse));
		}
		catch (JspException jspException) {
			throw new IOException(jspException);
		}

		values.put("portletNamespace", _portletNamespace);

		Writer writer = httpServletResponse.getWriter();

		writer.write(StringUtil.replace(_ICON_TMPL_CONTENT, "${", "}", values));

		return true;
	}

	public boolean isPanelStateOpen(HttpServletRequest httpServletRequest) {
		String analyticsReportsPanelState = SessionClicks.get(
			httpServletRequest, _SESSION_CLICKS_KEY, "closed");

		if (Objects.equals(analyticsReportsPanelState, "open")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		InfoItemReference infoItemReference = _getInfoItemReference(
			httpServletRequest);

		AnalyticsReportsInfoItemObjectProvider<Object>
			analyticsReportsInfoItemObjectProvider =
				(AnalyticsReportsInfoItemObjectProvider<Object>)
					_analyticsReportsInfoItemObjectProviderTracker.
						getAnalyticsReportsInfoItemObjectProvider(
							infoItemReference.getClassName());

		if (analyticsReportsInfoItemObjectProvider == null) {
			return false;
		}

		Object analyticsReportsInfoItemObject =
			analyticsReportsInfoItemObjectProvider.
				getAnalyticsReportsInfoItemObject(infoItemReference);

		if (analyticsReportsInfoItemObject == null) {
			return false;
		}

		AnalyticsReportsInfoItem<Object> analyticsReportsInfoItem =
			(AnalyticsReportsInfoItem<Object>)
				_analyticsReportsInfoItemTracker.getAnalyticsReportsInfoItem(
					infoItemReference.getClassName());

		if ((analyticsReportsInfoItem == null) ||
			!analyticsReportsInfoItem.isShow(analyticsReportsInfoItemObject)) {

			return false;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!AnalyticsReportsUtil.isShowAnalyticsReportsPanel(
				themeDisplay.getCompanyId(), httpServletRequest)) {

			return false;
		}

		return super.isShow(httpServletRequest);
	}

	public void setPanelState(
		HttpServletRequest httpServletRequest, String panelState) {

		SessionClicks.put(httpServletRequest, _SESSION_CLICKS_KEY, panelState);
	}

	public static class ProcessBodyBottomTagBodyException
		extends RuntimeException {

		public ProcessBodyBottomTagBodyException(Throwable throwable) {
			super(throwable);
		}

	}

	@Activate
	protected void activate() {
		_portletNamespace = _portal.getPortletNamespace(
			AnalyticsReportsPortletKeys.ANALYTICS_REPORTS);
	}

	private String _getAnalyticsReportsURL(
		HttpServletRequest httpServletRequest) {

		InfoItemReference infoItemReference = _getInfoItemReference(
			httpServletRequest);

		if (infoItemReference.getInfoItemIdentifier() instanceof
				ClassNameClassPKInfoItemIdentifier) {

			ClassNameClassPKInfoItemIdentifier
				classNameClassPKInfoItemIdentifier =
					(ClassNameClassPKInfoItemIdentifier)
						infoItemReference.getInfoItemIdentifier();

			return PortletURLBuilder.create(
				_portletURLFactory.create(
					httpServletRequest,
					AnalyticsReportsPortletKeys.ANALYTICS_REPORTS,
					PortletRequest.RESOURCE_PHASE)
			).setParameter(
				"className", infoItemReference.getClassName()
			).setParameter(
				"classPK", classNameClassPKInfoItemIdentifier.getClassPK()
			).setParameter(
				"classTypeName",
				classNameClassPKInfoItemIdentifier.getClassName()
			).setParameter(
				"p_p_resource_id", "/analytics_reports/get_data"
			).buildString();
		}
		else if (infoItemReference.getInfoItemIdentifier() instanceof
					ClassPKInfoItemIdentifier) {

			return PortletURLBuilder.create(
				_portletURLFactory.create(
					httpServletRequest,
					AnalyticsReportsPortletKeys.ANALYTICS_REPORTS,
					PortletRequest.RESOURCE_PHASE)
			).setParameter(
				"className", infoItemReference.getClassName()
			).setParameter(
				"classPK", infoItemReference.getClassPK()
			).setParameter(
				"p_p_resource_id", "/analytics_reports/get_data"
			).buildString();
		}

		return StringPool.BLANK;
	}

	private InfoItemReference _getInfoItemReference(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return Optional.ofNullable(
			(InfoItemReference)httpServletRequest.getAttribute(
				AnalyticsReportsWebKeys.ANALYTICS_INFO_ITEM_REFERENCE)
		).orElseGet(
			() -> new InfoItemReference(
				Layout.class.getName(), themeDisplay.getPlid())
		);
	}

	private void _processBodyBottomTagBody(PageContext pageContext)
		throws IOException, JspException {

		try {
			HttpServletRequest httpServletRequest =
				(HttpServletRequest)pageContext.getRequest();

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				_portal.getLocale(httpServletRequest), getClass());

			pageContext.setAttribute("resourceBundle", resourceBundle);

			JspWriter jspWriter = pageContext.getOut();

			StringBundler sb = new StringBundler(23);

			sb.append("<div class=\"");

			if (isPanelStateOpen(httpServletRequest)) {
				sb.append("lfr-has-analytics-reports-panel open-admin-panel ");
			}

			sb.append(
				StringBundler.concat(
					"cadmin d-print-none lfr-admin-panel ",
					"lfr-product-menu-panel lfr-analytics-reports-panel ",
					"sidenav-fixed sidenav-menu-slider sidenav-right\" id=\""));
			sb.append(_portletNamespace);
			sb.append("analyticsReportsPanelId\">");
			sb.append("<div class=\"sidebar sidebar-light sidenav-menu ");
			sb.append("sidebar-sm\">");
			sb.append("<div class=\"lfr-analytics-reports-sidebar\" ");
			sb.append("id=\"analyticsReportsSidebar\">");
			sb.append("<div class=\"d-flex justify-content-between p-3 ");
			sb.append("sidebar-header\">");
			sb.append("<h1 class=\"sr-only\">");
			sb.append(
				_language.get(httpServletRequest, "content-performance-panel"));
			sb.append("</h1>");
			sb.append("<span class=\"font-weight-bold\">");
			sb.append(_language.get(httpServletRequest, "content-performance"));
			sb.append("</span>");

			IconTag iconTag = new IconTag();

			iconTag.setCssClass(
				"btn btn-monospaced btn-unstyled component-action " +
					"sidenav-close text-secondary");
			iconTag.setImage("times");
			iconTag.setUrl("javascript:void(0);");

			sb.append(iconTag.doTagAsString(pageContext));

			sb.append("</div>");
			sb.append("<div class=\"sidebar-body\">");
			sb.append("<span aria-hidden=\"true\" ");
			sb.append("className=\"loading-animation ");
			sb.append("loading-animation-sm\" />");

			jspWriter.write(sb.toString());

			_reactRenderer.renderReact(
				new ComponentDescriptor(
					_npmResolver.resolveModuleName("analytics-reports-web") +
						"/js/AnalyticsReportsApp"),
				HashMapBuilder.<String, Object>put(
					"context",
					Collections.singletonMap(
						"analyticsReportsDataURL",
						_getAnalyticsReportsURL(httpServletRequest))
				).put(
					"portletNamespace", _portletNamespace
				).build(),
				httpServletRequest, jspWriter);

			jspWriter.write("</div></div></div></div>");
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}
	}

	private static final String _ICON_TMPL_CONTENT = StringUtil.read(
		AnalyticsReportsProductNavigationControlMenuEntry.class, "icon.tmpl");

	private static final String _SESSION_CLICKS_KEY =
		"com.liferay.analytics.reports.web_panelState";

	@Reference
	private AnalyticsReportsInfoItemObjectProviderTracker
		_analyticsReportsInfoItemObjectProviderTracker;

	@Reference
	private AnalyticsReportsInfoItemTracker _analyticsReportsInfoItemTracker;

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Portal _portal;

	private String _portletNamespace;

	@Reference
	private PortletURLFactory _portletURLFactory;

	@Reference
	private ReactRenderer _reactRenderer;

}