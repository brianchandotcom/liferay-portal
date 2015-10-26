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

package com.liferay.mobile.device.rules.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the MDRRule service. Represents a row in the &quot;MDRRule&quot; database table, with each column mapped to a property of this class.
 *
 * @author Edward C. Han
 * @see MDRRuleModel
 * @see com.liferay.mobile.device.rules.model.impl.MDRRuleImpl
 * @see com.liferay.mobile.device.rules.model.impl.MDRRuleModelImpl
 * @generated
 */
@ProviderType
public interface MDRRule extends MDRRuleModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.mobile.device.rules.model.impl.MDRRuleImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MDRRule, Long> RULE_ID_ACCESSOR = new Accessor<MDRRule, Long>() {
			@Override
			public Long get(MDRRule mdrRule) {
				return mdrRule.getRuleId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<MDRRule> getTypeClass() {
				return MDRRule.class;
			}
		};

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties();

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties);
}