/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.metatype.annotations;

import aQute.bnd.annotation.xml.XMLAttribute;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Iván Zaera
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@XMLAttribute(
	embedIn = "*", namespace = ExtendedObjectClassDefinition.XML_NAMESPACE,
	prefix = ExtendedObjectClassDefinition.XML_ATTRIBUTE_PREFIX
)
public @interface ExtendedObjectClassDefinition {

	public static final String XML_ATTRIBUTE_PREFIX = "cf";

	public static final String XML_NAMESPACE =
		"http://www.liferay.com/xsd/liferay-configuration-admin_1_0_0.xsd";

	public String category() default "";

	public String[] descriptionArguments() default {};

	public String factoryInstanceLabelAttribute() default "";

	public String featureFlagKey() default StringPool.BLANK;

	public boolean generateUI() default true;

	public String liferayLearnMessageKey() default "";

	public String liferayLearnMessageResource() default "";

	public String[] nameArguments() default {};

	public Scope scope() default Scope.SYSTEM;

	public String settingsId() default "";

	/**
	 * Whether or not the configuration's visibility will be limited to the
	 * declared scope.
	 *
	 * If true, the configuration will only be visible at the declared scope.
	 *
	 * If false, the configuration will be visible at the declared scope and all
	 * broader scopes, unless otherwise restricted by a
	 * ConfigurationVisibilityController.
	 *
	 * The default value is false.
	 *
	 * @review
	 */
	public boolean strictScope() default false;

	public String visibilityControllerKey() default StringPool.BLANK;

	public enum Scope {

		COMPANY("companyWebId", "companyId", "company"),
		GROUP("groupKey", "groupId", "group"),
		PORTLET_INSTANCE(
			"portletInstanceKey", "portletInstanceId", "portlet-instance"),
		SYSTEM(null, null, "system");

		public boolean equals(Scope scope) {
			return equals(scope.getValue());
		}

		public boolean equals(String value) {
			return _value.equals(value);
		}

		public String getDelimiterString() {
			return StringBundler.concat(_SEPARATOR, name(), _SEPARATOR);
		}

		public String getPortablePropertyKey() {
			return _portablePropertyKey;
		}

		public String getPropertyKey() {
			return _propertyKey;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Scope(
			String portablePropertyKey, String propertyKey, String value) {

			_portablePropertyKey = portablePropertyKey;
			_propertyKey = propertyKey;
			_value = value;
		}

		private static final String _SEPARATOR = StringPool.DOUBLE_UNDERLINE;

		private final String _portablePropertyKey;
		private final String _propertyKey;
		private final String _value;

	}

}