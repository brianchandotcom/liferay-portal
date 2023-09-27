/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public java.util.Date getTimeZoneEndDate();

	public java.util.Date getTimeZoneStartDate();

	public void setDispatchTaskSettingsUnicodeProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			dispatchTaskSettingsUnicodeProperties);

}