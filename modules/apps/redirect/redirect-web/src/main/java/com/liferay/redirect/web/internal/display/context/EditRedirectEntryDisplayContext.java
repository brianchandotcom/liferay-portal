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

package com.liferay.redirect.web.internal.display.context;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.redirect.model.RedirectEntry;

import java.text.DateFormat;

import java.util.Date;

import javax.portlet.ResourceURL;

/**
 * @author Adolfo Pérez
 */
public class EditRedirectEntryDisplayContext {

	public EditRedirectEntryDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		RedirectEntry redirectEntry) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_redirectEntry = redirectEntry;
	}

	public String getBackURL() {
		return ParamUtil.getString(_liferayPortletRequest, "backURL");
	}

	public String getDestinationURL() {
		if (_destinationURL != null) {
			return _destinationURL;
		}

		if (_redirectEntry != null) {
			_destinationURL = _redirectEntry.getDestinationURL();
		}
		else {
			_destinationURL = ParamUtil.getString(
				_liferayPortletRequest, "destinationURL");
		}

		return _destinationURL;
	}

	public String getEditRedirectEntryURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/redirect/edit_redirect_entry"
		).buildString();
	}

	public String getExpirationDateInputValue() {
		if (_redirectEntry == null) {
			return null;
		}

		Date expirationDate = _redirectEntry.getExpirationDate();

		if (expirationDate == null) {
			return null;
		}

		DateFormat simpleDateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd", _liferayPortletRequest.getLocale());

		return simpleDateFormat.format(expirationDate);
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_liferayPortletRequest, "redirect");

		return _redirect;
	}

	public String getRedirectEntryChainCauseURL() {
		ResourceURL resourceURL = _liferayPortletResponse.createResourceURL();

		resourceURL.setResourceID("/redirect/get_redirect_entry_chain_cause");

		return resourceURL.toString();
	}

	public long getRedirectEntryId() {
		if (_redirectEntry == null) {
			return 0;
		}

		return _redirectEntry.getRedirectEntryId();
	}

	public String getSourceURL() {
		if (_sourceURL != null) {
			return _sourceURL;
		}

		if (_redirectEntry != null) {
			_sourceURL = _redirectEntry.getSourceURL();
		}
		else {
			_sourceURL = ParamUtil.getString(
				_liferayPortletRequest, "sourceURL");
		}

		return _sourceURL;
	}

	public String getSubmitButtonLabel() {
		if (_redirectEntry == null) {
			return LanguageUtil.get(
				_liferayPortletRequest.getHttpServletRequest(), "create");
		}

		return LanguageUtil.get(
			_liferayPortletRequest.getHttpServletRequest(), "save");
	}

	public String getTitle() {
		if (_redirectEntry == null) {
			return LanguageUtil.get(
				_liferayPortletRequest.getHttpServletRequest(), "new-redirect");
		}

		return LanguageUtil.get(
			_liferayPortletRequest.getHttpServletRequest(), "edit-redirect");
	}

	public boolean isAutoFocusDestinationURL() {
		if (Validator.isNotNull(getSourceURL()) &&
			Validator.isNull(getDestinationURL())) {

			return true;
		}

		return false;
	}

	public boolean isAutoFocusSourceURL() {
		if (Validator.isNull(getSourceURL()) ||
			Validator.isNotNull(getDestinationURL())) {

			return true;
		}

		return false;
	}

	public boolean isRedirectEntryPermanent() {
		if (_redirectEntry == null) {
			return false;
		}

		return _redirectEntry.isPermanent();
	}

	public boolean isRedirectEntryTemporary() {
		if (isRedirectEntryPermanent()) {
			return false;
		}

		return true;
	}

	public boolean isShowAlertMessage() {
		if (_redirectEntry == null) {
			return false;
		}

		return true;
	}

	private String _destinationURL;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;
	private final RedirectEntry _redirectEntry;
	private String _sourceURL;

}