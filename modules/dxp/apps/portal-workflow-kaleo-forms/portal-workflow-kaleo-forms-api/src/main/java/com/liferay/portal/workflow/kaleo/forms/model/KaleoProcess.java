/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.forms.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the KaleoProcess service. Represents a row in the &quot;KaleoProcess&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marcellus Tavares
 * @see KaleoProcessModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessImpl"
)
@ProviderType
public interface KaleoProcess extends KaleoProcessModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<KaleoProcess, Long> KALEO_PROCESS_ID_ACCESSOR =
		new Accessor<KaleoProcess, Long>() {

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