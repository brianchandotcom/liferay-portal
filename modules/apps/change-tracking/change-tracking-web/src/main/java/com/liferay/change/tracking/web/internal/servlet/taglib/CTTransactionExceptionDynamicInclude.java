/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.servlet.taglib;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.change.tracking.CTTransactionException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.taglib.aui.ScriptTag;

import java.io.IOException;
import java.io.Writer;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = DynamicInclude.class)
public class CTTransactionExceptionDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		if (!SessionErrors.contains(
				httpServletRequest, CTTransactionException.class)) {

			return;
		}

		try {
			ScriptTag scriptTag = new ScriptTag();

			scriptTag.setPosition("inline");

			scriptTag.doBodyTag(
				httpServletRequest, httpServletResponse,
				pageContext -> _processScriptTagBody(
					httpServletRequest, pageContext));
		}
		catch (JspException jspException) {
			ReflectionUtil.throwException(jspException);
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/bottom.jsp#post");
	}

	private void _processScriptTagBody(
		HttpServletRequest httpServletRequest, PageContext pageContext) {

		try {
			Writer writer = pageContext.getOut();

			writer.write(
				"Liferay.Util.openToast({autoClose: 10000, message: '");

			Locale locale = _portal.getLocale(httpServletRequest);

			writer.write(
				_language.get(
					locale,
					"this-action-can-only-be-performed-in-production-mode"));

			writer.write("', title: '");

			writer.write(_language.get(locale, "error"));

			writer.write(":', type: 'danger',});");
		}
		catch (IOException ioException) {
			ReflectionUtil.throwException(ioException);
		}
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}