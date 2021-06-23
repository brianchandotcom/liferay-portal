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

package com.liferay.dispatch.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DispatchTrigger service. Represents a row in the &quot;DispatchTrigger&quot; database table, with each column mapped to a property of this class.
 *
 * @author Matija Petanjek
 * @see DispatchTriggerModel
 * @generated
 */
@ImplementationClassName("com.liferay.dispatch.model.impl.DispatchTriggerImpl")
@ProviderType
public interface DispatchTrigger extends DispatchTriggerModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.dispatch.model.impl.DispatchTriggerImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DispatchTrigger, Long>
		DISPATCH_TRIGGER_ID_ACCESSOR = new Accessor<DispatchTrigger, Long>() {

			@Override
			public Long get(DispatchTrigger dispatchTrigger) {
				return dispatchTrigger.getDispatchTriggerId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DispatchTrigger> getTypeClass() {
				return DispatchTrigger.class;
			}

		};

	public com.liferay.portal.kernel.util.UnicodeProperties
		getDispatchTaskSettingsUnicodeProperties();

	public com.liferay.dispatch.executor.DispatchTaskStatus
		getDispatchTaskStatus();

	public java.util.Date getNextFireDate();

	public void setDispatchTaskSettingsUnicodeProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			dispatchTaskSettingsUnicodeProperties);

}