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

	public static String getNamespacedName(String namespace, String name) {
		return StringBundler.concat(
			StringPool.UNDERLINE, namespace, StringPool.UNDERLINE, name);
	}

	public PortletDataHandlerControl(String namespace, String name) {
		this(namespace, name, false);
	}

	public PortletDataHandlerControl(
		String namespace, String name, boolean disabled) {

		this(namespace, name, disabled, null);
	}

	public PortletDataHandlerControl(
		String namespace, String name, boolean disabled, String className) {

		this(namespace, name, disabled, className, null);
	}

	public PortletDataHandlerControl(
		String namespace, String name, boolean disabled, String className,
		String referrerClassName) {

		this(namespace, name, name, disabled, className, referrerClassName);
	}

	public PortletDataHandlerControl(
		String namespace, String name, String label, boolean disabled,
		String className, String referrerClassName) {

		_namespace = namespace;
		_name = name;
		_label = label;
		_disabled = disabled;
		_className = className;
		_referrerClassName = referrerClassName;
	}

	public String getClassName() {
		return _className;
	}

	public String getHelpMessage(Locale locale, String action) {
		String helpMessage = LanguageUtil.get(
			locale, StringBundler.concat(action, "-", _label, "-help"),
			StringPool.BLANK);

		if (Validator.isNull(helpMessage)) {
			helpMessage = LanguageUtil.get(
				locale, "export-import-publish-" + _label + "-help",
				StringPool.BLANK);
		}

		return helpMessage;
	}

	public String getLabel() {
		return _label;
	}

	public String getName() {
		return _name;
	}

	public String getNamespace() {
		return _namespace;
	}

	public String getNamespacedName() {
		return getNamespacedName(_namespace, getName());
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
	private final boolean _disabled;
	private final String _label;
	private final String _name;
	private String _namespace;
	private final String _referrerClassName;

}