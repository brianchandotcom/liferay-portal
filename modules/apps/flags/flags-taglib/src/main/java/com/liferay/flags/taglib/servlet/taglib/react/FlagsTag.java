package com.liferay.flags.taglib.servlet.taglib.react;

import com.liferay.flags.configuration.FlagsGroupServiceConfiguration;
import com.liferay.flags.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import java.util.HashMap;
import java.util.Map;

public class FlagsTag extends IncludeTag {

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setContentTitle(String contentTitle) {
		_contentTitle = contentTitle;
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	public void setLabel(boolean label) {
		_label = label;
	}

	public void setMessage(String message) {
		_message = message;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setReportedUserId(long reportedUserId) {
		_reportedUserId = reportedUserId;
	}

	private String _getCurrentURL() {
		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		if ((portletRequest == null) || (portletResponse == null)) {
			return PortalUtil.getCurrentURL(request);
		}

		PortletURL currentURLObj = PortletURLUtil.getCurrent(
			PortalUtil.getLiferayPortletRequest(portletRequest),
			PortalUtil.getLiferayPortletResponse(portletResponse));

		return currentURLObj.toString();
	}

	private Map<String, String> _getReasons(long companyId)
		throws PortalException {

		FlagsGroupServiceConfiguration flagsGroupServiceConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				FlagsGroupServiceConfiguration.class, companyId);

		Map<String, String> reasons = new HashMap<>();

		for (String reason : flagsGroupServiceConfiguration.reasons()) {
			reasons.put(reason, LanguageUtil.get(request, reason));
		}

		return reasons;
	}

	private String _getURI() {
		PortletURL portletURL = PortletURLFactoryUtil.create(
			request, PortletKeys.FLAGS, PortletRequest.ACTION_PHASE);

		portletURL.setParameter(ActionRequest.ACTION_NAME, "/flags/edit_entry");

		return portletURL.toString();
	}

	private boolean _isFlagsEnabled(ThemeDisplay themeDisplay)
		throws PortalException {

		FlagsGroupServiceConfiguration flagsGroupServiceConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				FlagsGroupServiceConfiguration.class,
				themeDisplay.getCompanyId());

		if (flagsGroupServiceConfiguration.guestUsersEnabled() ||
			themeDisplay.isSignedIn()) {

			return true;
		}

		return false;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = null;
		_classPK = 0;
		_contentTitle = null;
		_enabled = true;
		_label = true;
		_message = null;
		_reportedUserId = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {

		httpServletRequest.setAttribute(
			"liferay-flags:flags:className", _className);
		httpServletRequest.setAttribute(
			"liferay-flags:flags:classPK", String.valueOf(_classPK));
		httpServletRequest.setAttribute(
			"liferay-flags:flags:contentTitle", _contentTitle);
		httpServletRequest.setAttribute(
			"liferay-flags:flags:contentURL", _getCurrentURL());
		httpServletRequest.setAttribute(
			"liferay-flags:flags:enabled", String.valueOf(_enabled));
		httpServletRequest.setAttribute(
			"liferay-flags:flags:label", String.valueOf(_label));
		httpServletRequest.setAttribute(
			"liferay-flags:flags:message", _message);
		httpServletRequest.setAttribute(
			"liferay-flags:flags:reportedUserId",
			String.valueOf(_reportedUserId));

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);


			httpServletRequest.setAttribute(
				"liferay-flags:flags:companyName",
				themeDisplay.getCompany().getName()
			);
			httpServletRequest.setAttribute(
				"liferay-flags:flags:flagsEnabled",
				_isFlagsEnabled(themeDisplay)
			);
			httpServletRequest.setAttribute(
				"liferay-flags:flags:reasons",
				_getReasons(themeDisplay.getCompanyId()));
			httpServletRequest.setAttribute(
				"liferay-flags:flags:portletNamespace",
				PortalUtil.getPortletNamespace(PortletKeys.FLAGS));

			boolean signedIn = themeDisplay.isSignedIn();

			httpServletRequest.setAttribute(
				"liferay-flags:flags:signedIn", signedIn);

			if (signedIn) {
				User user = themeDisplay.getUser();

				httpServletRequest.setAttribute(
					"liferay-flags:flags:reporterEmailAddress", user.getEmailAddress());
			}

			httpServletRequest.setAttribute(
				"liferay-flags:flags:reasons",
				_getReasons(themeDisplay.getCompanyId()));

			httpServletRequest.setAttribute(
				"liferay-flags:flags:uri", _getURI());

		}
		catch (Exception e) {
			_log.error(e, e);
		}

	}

	private static final String _PAGE = "/flags/react/page.jsp";

	private String _className;
	private long _classPK;
	private String _contentTitle;
	private boolean _enabled = true;
	private boolean _label = true;
	private String _message;
	private long _reportedUserId;

	private static final Log _log = LogFactoryUtil.getLog(FlagsTag.class);
}
