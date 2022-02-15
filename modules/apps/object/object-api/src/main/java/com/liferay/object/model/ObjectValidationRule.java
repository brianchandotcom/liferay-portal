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

}