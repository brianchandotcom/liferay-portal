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

package com.liferay.change.tracking.web.internal.servlet.taglib;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.change.tracking.CTTransactionException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.taglib.aui.ScriptTag;

import java.io.IOException;
import java.io.Writer;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = DynamicInclude.class)
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
				pageContext -> {
					try {
						Writer writer = pageContext.getOut();

						writer.write(
							"Liferay.Util.openToast({autoClose:10000," +
								"message:'");

						ResourceBundle resourceBundle =
							ResourceBundleUtil.getBundle(
								_portal.getLocale(httpServletRequest),
								CTTransactionExceptionDynamicInclude.class);

						writer.write(
							_language.get(
								resourceBundle,
								"this-action-can-only-be-performed-in-" +
									"production-mode"));

						writer.write("',title:'");

						writer.write(_language.get(resourceBundle, "error"));

						writer.write(":',type:'danger',});");
					}
					catch (IOException ioException) {
						ReflectionUtil.throwException(ioException);
					}
				});
		}
		catch (JspException jspException) {
			ReflectionUtil.throwException(jspException);
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/bottom.jsp#post");
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}