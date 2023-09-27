/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ObjectValidationRule service. Represents a row in the &quot;ObjectValidationRule&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see ObjectValidationRuleModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.object.model.impl.ObjectValidationRuleImpl"
)
@ProviderType
public interface ObjectValidationRule
	extends ObjectValidationRuleModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.object.model.impl.ObjectValidationRuleImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ObjectValidationRule, Long>
		OBJECT_VALIDATION_RULE_ID_ACCESSOR =
			new Accessor<ObjectValidationRule, Long>() {

				@Override
				public Long get(ObjectValidationRule objectValidationRule) {
					return objectValidationRule.getObjectValidationRuleId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ObjectValidationRule> getTypeClass() {
					return ObjectValidationRule.class;
				}

			};

	public boolean compareOutputType(String outputType);

	public java.util.List<ObjectValidationRuleSetting>
		getObjectValidationRuleSettings();

	public void setObjectValidationRuleSettings(
		java.util.List<ObjectValidationRuleSetting>
			objectValidationRuleSettings);

}