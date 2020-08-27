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
 * The extended model interface for the CommerceDataIntegrationProcessLog service. Represents a row in the &quot;CDataIntegrationProcessLog&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcessLogModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.data.integration.model.impl.CommerceDataIntegrationProcessLogImpl"
)
@ProviderType
public interface CommerceDataIntegrationProcessLog
	extends CommerceDataIntegrationProcessLogModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.data.integration.model.impl.CommerceDataIntegrationProcessLogImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceDataIntegrationProcessLog, Long>
		COMMERCE_DATA_INTEGRATION_PROCESS_LOG_ID_ACCESSOR =
			new Accessor<CommerceDataIntegrationProcessLog, Long>() {

				@Override
				public Long get(
					CommerceDataIntegrationProcessLog
						commerceDataIntegrationProcessLog) {

					return commerceDataIntegrationProcessLog.
						getCommerceDataIntegrationProcessLogId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceDataIntegrationProcessLog> getTypeClass() {
					return CommerceDataIntegrationProcessLog.class;
				}

			};

}