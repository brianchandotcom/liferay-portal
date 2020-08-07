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

package com.liferay.commerce.data.integration.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceDataIntegrationProcess service. Represents a row in the &quot;CDataIntegrationProcess&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcessModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.data.integration.model.impl.CommerceDataIntegrationProcessImpl"
)
@ProviderType
public interface CommerceDataIntegrationProcess
	extends CommerceDataIntegrationProcessModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.data.integration.model.impl.CommerceDataIntegrationProcessImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceDataIntegrationProcess, Long>
		COMMERCE_DATA_INTEGRATION_PROCESS_ID_ACCESSOR =
			new Accessor<CommerceDataIntegrationProcess, Long>() {

				@Override
				public Long get(
					CommerceDataIntegrationProcess
						commerceDataIntegrationProcess) {

					return commerceDataIntegrationProcess.
						getCommerceDataIntegrationProcessId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceDataIntegrationProcess> getTypeClass() {
					return CommerceDataIntegrationProcess.class;
				}

			};

	public com.liferay.portal.kernel.util.UnicodeProperties
		getTypeSettingsProperties();

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			typeSettingsUnicodeProperties);

}