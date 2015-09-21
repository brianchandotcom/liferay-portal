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

import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the MDRRuleGroupInstance service. Represents a row in the &quot;MDRRuleGroupInstance&quot; database table, with each column mapped to a property of this class.
 *
 * @author Edward C. Han
 * @see MDRRuleGroupInstanceModel
 * @see com.liferay.mobile.device.rules.model.impl.MDRRuleGroupInstanceImpl
 * @see com.liferay.mobile.device.rules.model.impl.MDRRuleGroupInstanceModelImpl
 * @generated
 */
@ProviderType
public interface MDRRuleGroupInstance extends MDRRuleGroupInstanceModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.mobile.device.rules.model.impl.MDRRuleGroupInstanceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public java.util.List<com.liferay.mobile.device.rules.model.MDRAction> getActions();

	public com.liferay.mobile.device.rules.model.MDRRuleGroup getRuleGroup()
		throws com.liferay.portal.kernel.exception.PortalException;
}