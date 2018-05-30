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

package com.liferay.commerce.product.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CPInstance service. Represents a row in the &quot;CPInstance&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPInstanceModel
 * @see com.liferay.commerce.product.model.impl.CPInstanceImpl
 * @see com.liferay.commerce.product.model.impl.CPInstanceModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.model.impl.CPInstanceImpl")
@ProviderType
public interface CPInstance extends CPInstanceModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.model.impl.CPInstanceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPInstance, Long> CP_INSTANCE_ID_ACCESSOR = new Accessor<CPInstance, Long>() {
			@Override
			public Long get(CPInstance cpInstance) {
				return cpInstance.getCPInstanceId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CPInstance> getTypeClass() {
				return CPInstance.class;
			}
		};

	public CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException;
}