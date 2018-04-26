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

package com.liferay.portal.workflow.kaleo.forms.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the KaleoProcess service. Represents a row in the &quot;KaleoProcess&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marcellus Tavares
 * @see KaleoProcessModel
 * @see com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessImpl
 * @see com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessImpl")
@ProviderType
public interface KaleoProcess extends KaleoProcessModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<KaleoProcess, Long> KALEO_PROCESS_ID_ACCESSOR = new Accessor<KaleoProcess, Long>() {
			@Override
			public Long get(KaleoProcess kaleoProcess) {
				return kaleoProcess.getKaleoProcessId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<KaleoProcess> getTypeClass() {
				return KaleoProcess.class;
			}
		};

	public com.liferay.dynamic.data.lists.model.DDLRecordSet getDDLRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.dynamic.data.mapping.model.DDMTemplate getDDMTemplate()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getDescription()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getDescription(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<KaleoProcessLink> getKaleoProcessLinks();

	public String getName()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getName(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getWorkflowDefinition();
}