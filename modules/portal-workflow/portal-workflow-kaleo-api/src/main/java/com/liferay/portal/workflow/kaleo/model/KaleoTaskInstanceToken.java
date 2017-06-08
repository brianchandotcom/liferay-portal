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

package com.liferay.portal.workflow.kaleo.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the KaleoTaskInstanceToken service. Represents a row in the &quot;KaleoTaskInstanceToken&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskInstanceTokenModel
 * @see com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenImpl
 * @see com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenImpl")
@ProviderType
public interface KaleoTaskInstanceToken extends KaleoTaskInstanceTokenModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<KaleoTaskInstanceToken, Long> KALEO_TASK_INSTANCE_TOKEN_ID_ACCESSOR =
		new Accessor<KaleoTaskInstanceToken, Long>() {
			@Override
			public Long get(KaleoTaskInstanceToken kaleoTaskInstanceToken) {
				return kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<KaleoTaskInstanceToken> getTypeClass() {
				return KaleoTaskInstanceToken.class;
			}
		};

	public com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken getKaleoInstanceToken()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.workflow.kaleo.model.KaleoTask getKaleoTask()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance> getKaleoTaskAssignmentInstances();
}