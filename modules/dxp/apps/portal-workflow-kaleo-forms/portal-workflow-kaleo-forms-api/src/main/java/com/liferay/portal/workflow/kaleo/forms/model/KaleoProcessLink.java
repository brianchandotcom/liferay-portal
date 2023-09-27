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
 * The extended model interface for the KaleoProcessLink service. Represents a row in the &quot;KaleoProcessLink&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marcellus Tavares
 * @see KaleoProcessLinkModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessLinkImpl"
)
@ProviderType
public interface KaleoProcessLink
	extends KaleoProcessLinkModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessLinkImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<KaleoProcessLink, Long>
		KALEO_PROCESS_LINK_ID_ACCESSOR =
			new Accessor<KaleoProcessLink, Long>() {

				@Override
				public Long get(KaleoProcessLink kaleoProcessLink) {
					return kaleoProcessLink.getKaleoProcessLinkId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<KaleoProcessLink> getTypeClass() {
					return KaleoProcessLink.class;
				}

			};

	public KaleoProcess getKaleoProcess()
		throws com.liferay.portal.kernel.exception.PortalException;

}