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
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.creator.InfoItemCreator;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

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
	immediate = true, property = "path=/info/add_info_item",
	service = StrutsAction.class
)
public class AddInfoItemStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		String className = _portal.getClassName(
			ParamUtil.getLong(originalHttpServletRequest, "classNameId"));

		try {
			CaptchaUtil.check(httpServletRequest);

			InfoItemCreator<Object> infoItemCreator =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemCreator.class, className);

			if (infoItemCreator == null) {
				throw new InfoFormException();
			}

			infoItemCreator.createFromInfoItemFieldValues(
				InfoItemFieldValues.builder(
				).infoFieldValues(
					_infoRequestFieldValuesProviderHelper.getInfoFieldValues(
						httpServletRequest)
				).build());
		}
		catch (CaptchaException captchaException) {
			if (_log.isDebugEnabled()) {
				_log.debug(captchaException);
			}

			String formItemId = ParamUtil.getString(
				httpServletRequest, "formItemId");

			SessionErrors.add(
				originalHttpServletRequest, formItemId,
				new InfoFormValidationException.InvalidCaptcha());
		}
		catch (InfoFormValidationException infoFormValidationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(infoFormValidationException);
			}

			String formItemId = ParamUtil.getString(
				httpServletRequest, "formItemId");

			SessionErrors.add(
				originalHttpServletRequest, formItemId,
				infoFormValidationException);
		}
		catch (InfoFormException infoFormException) {
			if (_log.isDebugEnabled()) {
				_log.debug(infoFormException);
			}

			String formItemId = ParamUtil.getString(
				httpServletRequest, "formItemId");

			SessionErrors.add(
				originalHttpServletRequest, formItemId, infoFormException);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to add info item", exception);
			}

			InfoFormException infoFormException = new InfoFormException();

			if (exception instanceof PrincipalException) {
				infoFormException = new InfoFormPrincipalException();
			}

			String formItemId = ParamUtil.getString(
				httpServletRequest, "formItemId");

			SessionErrors.add(
				originalHttpServletRequest, formItemId, infoFormException);
		}

		httpServletResponse.sendRedirect(
			httpServletRequest.getHeader(HttpHeaders.REFERER));

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