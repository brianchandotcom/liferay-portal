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

package com.liferay.commerce.discount.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceDiscountRule service. Represents a row in the &quot;CommerceDiscountRule&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceDiscountRuleModel
 * @see com.liferay.commerce.discount.model.impl.CommerceDiscountRuleImpl
 * @see com.liferay.commerce.discount.model.impl.CommerceDiscountRuleModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.discount.model.impl.CommerceDiscountRuleImpl")
@ProviderType
public interface CommerceDiscountRule extends CommerceDiscountRuleModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.discount.model.impl.CommerceDiscountRuleImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceDiscountRule, Long> COMMERCE_DISCOUNT_RULE_ID_ACCESSOR =
		new Accessor<CommerceDiscountRule, Long>() {
			@Override
			public Long get(CommerceDiscountRule commerceDiscountRule) {
				return commerceDiscountRule.getCommerceDiscountRuleId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceDiscountRule> getTypeClass() {
				return CommerceDiscountRule.class;
			}
		};

	public com.liferay.portal.kernel.util.UnicodeProperties getSettingsProperties();

	public String getSettingsProperty(String key);

	public void setSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties settingsProperties);
}