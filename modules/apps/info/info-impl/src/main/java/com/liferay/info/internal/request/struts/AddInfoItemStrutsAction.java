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

package com.liferay.info.internal.request.struts;

import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.info.exception.InfoFormException;
import com.liferay.info.exception.InfoFormPrincipalException;
import com.liferay.info.exception.InfoFormValidationException;
import com.liferay.info.internal.request.helper.InfoRequestFieldValuesProviderHelper;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.creator.InfoItemCreator;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true, property = "path=/portal/add_info_item",
	service = StrutsAction.class
)
public class AddInfoItemStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		String className = _portal.getClassName(
			ParamUtil.getLong(httpServletRequest, "classNameId"));
		String formItemId = ParamUtil.getString(
			httpServletRequest, "formItemId");

		String redirect = null;

		try {
			CaptchaUtil.check(httpServletRequest);

			InfoItemCreator<Object> infoItemCreator =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemCreator.class, className);

			if (infoItemCreator == null) {
				throw new InfoFormException();
			}

			infoItemCreator.createFromInfoItemFieldValues(
				ParamUtil.getLong(httpServletRequest, "groupId"),
				InfoItemFieldValues.builder(
				).infoFieldValues(
					_infoRequestFieldValuesProviderHelper.getInfoFieldValues(
						httpServletRequest)
				).infoItemReference(
					new InfoItemReference(className, 0)
				).build());

			redirect = ParamUtil.getString(httpServletRequest, "redirect");

			if (Validator.isNull(redirect)) {
				SessionMessages.add(httpServletRequest, formItemId);
			}
		}
		catch (CaptchaException captchaException) {
			if (_log.isDebugEnabled()) {
				_log.debug(captchaException);
			}

			SessionErrors.add(
				httpServletRequest, formItemId,
				new InfoFormValidationException.InvalidCaptcha());
		}
		catch (InfoFormValidationException infoFormValidationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(infoFormValidationException);
			}

			SessionErrors.add(
				httpServletRequest, formItemId, infoFormValidationException);

			if (Validator.isNotNull(
					infoFormValidationException.getInfoFieldUniqueId())) {

				SessionErrors.add(
					httpServletRequest,
					infoFormValidationException.getInfoFieldUniqueId(),
					infoFormValidationException);
			}
		}
		catch (InfoFormException infoFormException) {
			if (_log.isDebugEnabled()) {
				_log.debug(infoFormException);
			}

			SessionErrors.add(
				httpServletRequest, formItemId, infoFormException);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			InfoFormException infoFormException = new InfoFormException();

			if (exception instanceof PrincipalException) {
				infoFormException = new InfoFormPrincipalException();
			}

			SessionErrors.add(
				httpServletRequest, formItemId, infoFormException);
		}

		if (Validator.isNull(redirect)) {
			redirect = httpServletRequest.getHeader(HttpHeaders.REFERER);
		}

		httpServletResponse.sendRedirect(redirect);

		return null;
	}

	@Activate
	@Modified
	protected void activate() {
		_infoRequestFieldValuesProviderHelper =
			new InfoRequestFieldValuesProviderHelper(_infoItemServiceTracker);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddInfoItemStrutsAction.class);

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	private volatile InfoRequestFieldValuesProviderHelper
		_infoRequestFieldValuesProviderHelper;

	@Reference
	private Portal _portal;

}