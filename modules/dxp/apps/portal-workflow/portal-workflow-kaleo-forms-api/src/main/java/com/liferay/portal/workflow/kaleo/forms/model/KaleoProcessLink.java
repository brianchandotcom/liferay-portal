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

package com.liferay.portal.workflow.kaleo.forms.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the KaleoProcessLink service. Represents a row in the &quot;KaleoProcessLink&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marcellus Tavares
 * @see KaleoProcessLinkModel
 * @see com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessLinkImpl
 * @see com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessLinkModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessLinkImpl")
@ProviderType
public interface KaleoProcessLink extends KaleoProcessLinkModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessLinkImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<KaleoProcessLink, Long> KALEO_PROCESS_LINK_ID_ACCESSOR =
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