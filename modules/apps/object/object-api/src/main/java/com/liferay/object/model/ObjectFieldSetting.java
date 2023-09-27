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
 * The extended model interface for the ObjectFieldSetting service. Represents a row in the &quot;ObjectFieldSetting&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see ObjectFieldSettingModel
 * @generated
 */
@ImplementationClassName("com.liferay.object.model.impl.ObjectFieldSettingImpl")
@ProviderType
public interface ObjectFieldSetting
	extends ObjectFieldSettingModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.object.model.impl.ObjectFieldSettingImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ObjectFieldSetting, Long>
		OBJECT_FIELD_SETTING_ID_ACCESSOR =
			new Accessor<ObjectFieldSetting, Long>() {

				@Override
				public Long get(ObjectFieldSetting objectFieldSetting) {
					return objectFieldSetting.getObjectFieldSettingId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ObjectFieldSetting> getTypeClass() {
					return ObjectFieldSetting.class;
				}

			};

	public boolean compareName(String name);

	public java.util.List<ObjectFilter> getObjectFilters();

	public ObjectStateFlow getObjectStateFlow();

	public void setObjectFilters(java.util.List<ObjectFilter> objectFilters);

	public void setObjectStateFlow(ObjectStateFlow objectStateFlow);

}