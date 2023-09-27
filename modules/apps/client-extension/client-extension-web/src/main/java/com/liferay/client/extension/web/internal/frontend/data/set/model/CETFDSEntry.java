/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.web.internal.frontend.data.set.model;

import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.web.internal.display.context.util.CETLabelUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.Locale;

/**
 * @author Bruno Basto
 */
public class CETFDSEntry {

	public CETFDSEntry(CET cet, Locale locale) {
		_cet = cet;
		_locale = locale;
	}

	public String getConfiguredFrom() {
		if (_cet.isReadOnly()) {
			return LanguageUtil.get(_locale, "workspace");
		}

		return LanguageUtil.get(_locale, "ui");
	}

	public Date getCreateDate() {
		return _cet.getCreateDate();
	}

	public String getExternalReferenceCode() {
		return _cet.getExternalReferenceCode();
	}

	public Date getModifiedDate() {
		return _cet.getModifiedDate();
	}

	public String getName() {
		return _cet.getName(_locale);
	}

	public StatusInfo getStatus() {
		String label = WorkflowConstants.getStatusLabel(_cet.getStatus());

		return new StatusInfo(label, LanguageUtil.get(_locale, label));
	}

	public String getType() {
		return CETLabelUtil.getTypeLabel(_locale, _cet.getType());
	}

	public boolean isReadOnly() {
		return _cet.isReadOnly();
	}

	private final CET _cet;
	private final Locale _locale;

}