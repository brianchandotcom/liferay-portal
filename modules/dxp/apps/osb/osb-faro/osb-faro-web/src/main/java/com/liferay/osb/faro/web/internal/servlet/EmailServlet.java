/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.servlet;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.osb.faro.model.FaroUser;
import com.liferay.osb.faro.service.FaroEmailLocalService;
import com.liferay.osb.faro.service.FaroUserLocalService;
import com.liferay.osb.faro.util.EmailUtil;
import com.liferay.osb.faro.util.FaroPropsValues;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.ResourceBundle;

import javax.mail.internet.InternetAddress;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
@Component(
	property = {
		"osgi.http.whiteboard.context.path=/email",
		"osgi.http.whiteboard.servlet.name=com.liferay.osb.faro.web.internal.servlet.EmailServlet",
		"osgi.http.whiteboard.servlet.pattern=/email/*"
	},
	service = Servlet.class
)
public class EmailServlet extends BaseAsahServlet {

	@Override
	protected void doPost(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			_sendEmail(
				_jsonFactory.createJSONObject(
					StringUtil.read(httpServletRequest.getInputStream())));
		}
		catch (Exception exception) {
			throw new ServletException(exception);
		}
	}

	private String _getDownloadURL(String batchId, long groupId) {
		String url =
			FaroPropsValues.FARO_URL + "/o/proxy/download/data-control-tasks";

		url = HttpComponentsUtil.addParameter(url, "projectGroupId", groupId);

		return HttpComponentsUtil.addParameter(
			url, "filter", "batchId eq '" + batchId + "'");
	}

	private void _sendEmail(JSONObject jsonObject) throws Exception {
		FaroUser faroUser = _faroUserLocalService.fetchFaroUser(
			jsonObject.getLong("ownerId"));

		if (faroUser == null) {
			return;
		}

		InternetAddress from = new InternetAddress(
			"ac@liferay.com", "Analytics Cloud");

		User user = _userLocalService.getUser(faroUser.getLiveUserId());

		InternetAddress to = new InternetAddress(
			user.getEmailAddress(), user.getFullName());

		ResourceBundle resourceBundle =
			_faroEmailLocalService.getResourceBundle(user.getLocale());

		String subject = _language.get(
			resourceBundle, "your-request-is-complete");

		String body = StringUtil.replace(
			_faroEmailLocalService.getTemplate(
				"com/liferay/osb/faro/dependencies" +
					"/data-control-task-complete.html"),
			new String[] {
				"[$BUTTON_TEXT$]", "[$BUTTON_URL$]", "[$EMAIL_TITLE$]",
				"[$LOGO_ICON_URL$]", "[$NOTIFICATION_MSG$]",
				"[$TITLE_ICON_URL$]", "[$TITLE_MSG$]"
			},
			new String[] {
				_language.get(resourceBundle, "download"),
				_getDownloadURL(
					jsonObject.getString("batchId"), faroUser.getGroupId()),
				subject, EmailUtil.getLogoIconURL(),
				_language.format(
					resourceBundle, "request-x-has-been-processed",
					jsonObject.getString("batchId")),
				EmailUtil.getTitleIconURL(), subject
			});

		_mailService.sendEmail(new MailMessage(from, to, subject, body, true));
	}

	@Reference
	private FaroEmailLocalService _faroEmailLocalService;

	@Reference
	private FaroUserLocalService _faroUserLocalService;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private MailService _mailService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}