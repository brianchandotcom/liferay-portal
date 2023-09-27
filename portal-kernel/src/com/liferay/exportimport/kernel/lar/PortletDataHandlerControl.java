/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lar;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

/**
 * @author Raymond Augé
 */
public class PortletDataHandlerControl {

	public static String getNamespacedControlName(
		String namespace, String controlName) {

		return StringBundler.concat(
			StringPool.UNDERLINE, namespace, StringPool.UNDERLINE, controlName);
	}

	public PortletDataHandlerControl(String namespace, String controlName) {
		this(namespace, controlName, false);
	}

	public PortletDataHandlerControl(
		String namespace, String controlName, boolean disabled) {

		this(namespace, controlName, disabled, null);
	}

	public PortletDataHandlerControl(
		String namespace, String controlName, boolean disabled,
		String className) {

		this(namespace, controlName, disabled, className, null);
	}

	public PortletDataHandlerControl(
		String namespace, String controlName, boolean disabled,
		String className, String referrerClassName) {

		this(
			namespace, controlName, controlName, disabled, className,
			referrerClassName);
	}

	public PortletDataHandlerControl(
		String namespace, String controlName, String controlLabel,
		boolean disabled, String className, String referrerClassName) {

		_namespace = namespace;
		_controlName = controlName;
		_controlLabel = controlLabel;
		_disabled = disabled;
		_className = className;
		_referrerClassName = referrerClassName;
	}

	public String getClassName() {
		return _className;
	}

	public String getControlLabel() {
		return _controlLabel;
	}

	public String getControlName() {
		return _controlName;
	}

	public String getHelpMessage(Locale locale, String action) {
		String helpMessage = LanguageUtil.get(
			locale, StringBundler.concat(action, "-", _controlLabel, "-help"),
			StringPool.BLANK);

		if (Validator.isNull(helpMessage)) {
			helpMessage = LanguageUtil.get(
				locale, "export-import-publish-" + _controlLabel + "-help",
				StringPool.BLANK);
		}

		return helpMessage;
	}

	public String getNamespace() {
		return _namespace;
	}

	public String getNamespacedControlName() {
		return getNamespacedControlName(_namespace, getControlName());
	}

	public String getReferrerClassName() {
		return _referrerClassName;
	}

	public boolean isDisabled() {
		return _disabled;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	private final String _className;
	private final String _controlLabel;
	private final String _controlName;
	private final boolean _disabled;
	private String _namespace;
	private final String _referrerClassName;

}