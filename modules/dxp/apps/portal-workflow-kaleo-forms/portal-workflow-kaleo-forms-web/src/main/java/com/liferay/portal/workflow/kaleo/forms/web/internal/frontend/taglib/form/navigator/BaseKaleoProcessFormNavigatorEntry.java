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

package com.liferay.portal.workflow.kaleo.forms.web.internal.frontend.taglib.form.navigator;

import com.liferay.frontend.taglib.form.navigator.BaseJSPFormNavigatorEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseKaleoProcessFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<KaleoProcess> {

	@Override
	public String getCategoryKey() {
		return StringPool.BLANK;
	}

	@Override
	public String getFormNavigatorId() {
		return "kaleo.form";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(_getResourceBundle(locale), getKey());
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		Class<?> clazz = getClass();

		return ResourceBundleUtil.getBundle(locale, clazz.getClassLoader());
	}

}