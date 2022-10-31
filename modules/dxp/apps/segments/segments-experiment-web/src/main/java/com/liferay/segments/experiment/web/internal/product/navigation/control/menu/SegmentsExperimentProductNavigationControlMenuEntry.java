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

package com.liferay.segments.experiment.web.internal.product.navigation.control.menu;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.experiment.web.internal.configuration.SegmentsExperimentConfiguration;
import com.liferay.segments.experiment.web.internal.util.SegmentsExperimentUtil;
import com.liferay.segments.manager.SegmentsExperienceManager;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.segments.service.SegmentsExperimentRelService;
import com.liferay.segments.service.SegmentsExperimentService;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.taglib.aui.IconTag;
import com.liferay.taglib.util.BodyBottomTag;

import java.io.IOException;
import java.io.Writer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceURL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	configurationPid = "com.liferay.segments.experiment.web.internal.configuration.SegmentsExperimentConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.USER,
		"product.navigation.control.menu.entry.order:Integer=500"
	},
	service = {
		ProductNavigationControlMenuEntry.class,
		SegmentsExperimentProductNavigationControlMenuEntry.class
	}
)
public class SegmentsExperimentProductNavigationControlMenuEntry
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

		bodyBottomTag.setOutputKey("segmentsExperimentPanel");

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
			"title", _html.escape(_language.get(resourceBundle, "ab-test")));

		IconTag iconTag = new IconTag();

		iconTag.setCssClass("icon-monospaced");
		iconTag.setImage("test");

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
		String segmentsExperimentPanelState = SessionClicks.get(
			httpServletRequest, _SESSION_CLICKS_KEY, "closed");

		if (Objects.equals(segmentsExperimentPanelState, "open")) {
			return true;
		}

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		String segmentsExperimentKey = ParamUtil.getString(
			originalHttpServletRequest, "segmentsExperimentKey");

		if (Validator.isNotNull(segmentsExperimentKey)) {
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

		if (!themeDisplay.isSignedIn()) {
			return false;
		}

		Layout layout = themeDisplay.getLayout();

		if (layout.isTypeControlPanel() ||
			isEmbeddedPersonalApplicationLayout(layout) ||
			!layout.isTypeContent() ||
			!LayoutPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), layout,
				ActionKeys.UPDATE)) {

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
				SegmentsPortletKeys.SEGMENTS_EXPERIMENT, "hide-panel"));

		if (!SegmentsExperimentUtil.isAnalyticsConnected(
				themeDisplay.getCompanyId()) &&
			hidePanel) {

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
	protected void activate(Map<String, Object> properties) {
		_portletNamespace = _portal.getPortletNamespace(
			SegmentsPortletKeys.SEGMENTS_EXPERIMENT);

		_segmentsExperimentConfiguration = ConfigurableUtil.createConfigurable(
			SegmentsExperimentConfiguration.class, properties);
	}

	private Map<String, Object> _getData(
			HttpServletRequest httpServletRequest, boolean panelStateOpen)
		throws Exception {

		return HashMapBuilder.<String, Object>put(
			"context",
			HashMapBuilder.<String, Object>put(
				"isPanelStateOpen", panelStateOpen
			).put(
				"namespace",
				_portal.getPortletNamespace(
					SegmentsPortletKeys.SEGMENTS_EXPERIMENT)
			).put(
				"segmentExperimentDataURL",
				_getSegmentExperimentDataURL(httpServletRequest)
			).build()
		).build();
	}

	private String _getRedirect(
			HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay)
		throws Exception {

		Layout draftLayout = _layoutLocalService.fetchDraftLayout(
			themeDisplay.getPlid());

		if (draftLayout == null) {
			return StringPool.BLANK;
		}

		String layoutFullURL = _portal.getLayoutFullURL(
			draftLayout, themeDisplay);

		String layoutURL = _portal.getLayoutURL(themeDisplay);

		long segmentsExperienceId = _getSegmentsExperienceId(
			httpServletRequest, themeDisplay);

		if (segmentsExperienceId != -1) {
			layoutURL = HttpComponentsUtil.setParameter(
				layoutURL, "segmentsExperienceId", segmentsExperienceId);
		}

		layoutFullURL = HttpComponentsUtil.setParameter(
			layoutFullURL, "p_l_back_url", layoutURL);

		layoutFullURL = HttpComponentsUtil.setParameter(
			layoutFullURL, "p_l_mode", Constants.EDIT);
		layoutFullURL = HttpComponentsUtil.setParameter(
			layoutFullURL, "redirect", layoutFullURL);

		return layoutFullURL;
	}

	private String _getSegmentExperimentDataURL(
			HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String layoutURL = _portal.getLayoutURL(themeDisplay);

		long segmentsExperienceId = _getSegmentsExperienceId(
			httpServletRequest, themeDisplay);

		if (segmentsExperienceId != -1) {
			layoutURL = HttpComponentsUtil.setParameter(
				layoutURL, "segmentsExperienceId", segmentsExperienceId);
		}

		ResourceURL resourceURL = (ResourceURL)PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, themeDisplay.getScopeGroup(),
				SegmentsPortletKeys.SEGMENTS_EXPERIMENT, 0, 0,
				PortletRequest.RESOURCE_PHASE)
		).setRedirect(
			_getRedirect(httpServletRequest, themeDisplay)
		).setBackURL(
			layoutURL
		).setParameter(
			"plid", themeDisplay.getPlid()
		).setParameter(
			"segmentsExperienceId",
			_getSelectedSegmentsExperienceId(httpServletRequest)
		).buildPortletURL();

		resourceURL.setResourceID("/segments_experiment/get_data");

		return resourceURL.toString();
	}

	private long _getSegmentsExperienceId(
			HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay)
		throws Exception {

		long segmentsExperienceId = _getSelectedSegmentsExperienceId(
			httpServletRequest);

		Layout layout = themeDisplay.getLayout();

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentService.fetchSegmentsExperiment(
				segmentsExperienceId, _portal.getClassNameId(Layout.class),
				layout.getPlid(),
				SegmentsExperimentConstants.Status.getExclusiveStatusValues());

		if (segmentsExperiment != null) {
			return segmentsExperiment.getSegmentsExperienceId();
		}

		return segmentsExperienceId;
	}

	private long _getSelectedSegmentsExperienceId(
		HttpServletRequest httpServletRequest) {

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		long segmentsExperienceId = ParamUtil.getLong(
			originalHttpServletRequest, "segmentsExperienceId", -1);

		if (segmentsExperienceId != -1) {
			return segmentsExperienceId;
		}

		SegmentsExperienceManager segmentsExperienceManager =
			new SegmentsExperienceManager(_segmentsExperienceLocalService);

		return segmentsExperienceManager.getSegmentsExperienceId(
			httpServletRequest);
	}

	private void _processBodyBottomTagBody(PageContext pageContext)
		throws IOException, JspException {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_portal.getLocale(httpServletRequest), getClass());

		pageContext.setAttribute("resourceBundle", resourceBundle);

		JspWriter jspWriter = pageContext.getOut();

		try {
			StringBundler sb = new StringBundler(23);

			sb.append("<div class=\"");

			boolean panelStateOpen = isPanelStateOpen(httpServletRequest);

			if (panelStateOpen) {
				sb.append(
					"lfr-has-segments-experiment-panel open-admin-panel ");
			}

			sb.append(
				StringBundler.concat(
					"cadmin d-print-none lfr-admin-panel ",
					"lfr-product-menu-panel lfr-segments-experiment-panel ",
					"sidenav-fixed sidenav-menu-slider sidenav-right\" id=\""));
			sb.append(_portletNamespace);
			sb.append("segmentsExperimentPanelId\">");
			sb.append("<div class=\"sidebar sidebar-light sidenav-menu ");
			sb.append("sidebar-sm\">");

			sb.append("<div class=\"lfr-segments-experiment-sidebar\" ");
			sb.append("id=\"segmentsExperimentSidebar\">");
			sb.append("<div class=\"d-flex justify-content-between p-3 ");
			sb.append("sidebar-header\">");
			sb.append("<h1 class=\"sr-only\">");
			sb.append(_language.get(httpServletRequest, "ab-test-panel"));
			sb.append("</h1>");
			sb.append("<span class=\"font-weight-bold\">");
			sb.append(_language.get(httpServletRequest, "ab-test"));
			sb.append("</span>");

			IconTag iconTag = new IconTag();

			iconTag.setCssClass(
				"btn btn-monospaced btn-unstyle component-action " +
					"sidenav-close text-secondary");
			iconTag.setImage("times");
			iconTag.setMarkupView("lexicon");
			iconTag.setUrl("javascript:;");

			sb.append(iconTag.doTagAsString(pageContext));

			sb.append("</div>");
			sb.append("<div class=\"sidebar-body\">");
			sb.append("<span aria-hidden=\"true\" ");
			sb.append("className=\"loading-animation ");
			sb.append("loading-animation-sm\" />");

			jspWriter.write(sb.toString());

			_reactRenderer.renderReact(
				new ComponentDescriptor(
					_npmResolver.resolveModuleName("segments-experiment-web") +
						"/js/SegmentsExperimentApp.es"),
				_getData(httpServletRequest, panelStateOpen),
				httpServletRequest, jspWriter);

			jspWriter.write("</div></div></div></div>");
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}
	}

	private static final String _ICON_TMPL_CONTENT = StringUtil.read(
		SegmentsExperimentProductNavigationControlMenuEntry.class, "icon.tmpl");

	private static final String _SESSION_CLICKS_KEY =
		"com.liferay.segments.experiment.web_panelState";

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Portal _portal;

	private String _portletNamespace;

	@Reference
	private ReactRenderer _reactRenderer;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private SegmentsExperienceService _segmentsExperienceService;

	private volatile SegmentsExperimentConfiguration
		_segmentsExperimentConfiguration;

	@Reference
	private SegmentsExperimentRelService _segmentsExperimentRelService;

	@Reference
	private SegmentsExperimentService _segmentsExperimentService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.segments.experiment.web)"
	)
	private ServletContext _servletContext;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}