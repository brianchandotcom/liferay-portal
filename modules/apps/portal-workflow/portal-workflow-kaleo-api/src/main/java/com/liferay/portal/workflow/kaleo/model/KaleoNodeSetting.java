/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the KaleoNodeSetting service. Represents a row in the &quot;KaleoNodeSetting&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNodeSettingModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.workflow.kaleo.model.impl.KaleoNodeSettingImpl"
)
@ProviderType
public interface KaleoNodeSetting
	extends KaleoNodeSettingModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNodeSettingImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<KaleoNodeSetting, Long>
		KALEO_NODE_SETTING_ID_ACCESSOR =
			new Accessor<KaleoNodeSetting, Long>() {

				@Override
				public Long get(KaleoNodeSetting kaleoNodeSetting) {
					return kaleoNodeSetting.getKaleoNodeSettingId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<KaleoNodeSetting> getTypeClass() {
					return KaleoNodeSetting.class;
				}

			};

}
// LIFERAY-SERVICE-BUILDER-HASH:853869169