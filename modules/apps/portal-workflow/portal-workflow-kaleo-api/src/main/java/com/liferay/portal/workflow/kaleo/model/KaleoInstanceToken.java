/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the KaleoInstanceToken service. Represents a row in the &quot;KaleoInstanceToken&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoInstanceTokenModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.workflow.kaleo.model.impl.KaleoInstanceTokenImpl"
)
@ProviderType
public interface KaleoInstanceToken
	extends KaleoInstanceTokenModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoInstanceTokenImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<KaleoInstanceToken, Long>
		KALEO_INSTANCE_TOKEN_ID_ACCESSOR =
			new Accessor<KaleoInstanceToken, Long>() {

				@Override
				public Long get(KaleoInstanceToken kaleoInstanceToken) {
					return kaleoInstanceToken.getKaleoInstanceTokenId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<KaleoInstanceToken> getTypeClass() {
					return KaleoInstanceToken.class;
				}

			};

	public java.util.List<KaleoInstanceToken> getChildrenKaleoInstanceTokens();

	public KaleoNode getCurrentKaleoNode()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<KaleoInstanceToken>
		getIncompleteChildrenKaleoInstanceTokens();

	public KaleoInstance getKaleoInstance()
		throws com.liferay.portal.kernel.exception.PortalException;

	public KaleoInstanceToken getParentKaleoInstanceToken()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean hasIncompleteChildrenKaleoInstanceToken();

	public void setCurrentKaleoNode(KaleoNode kaleoNode)
		throws com.liferay.portal.kernel.exception.PortalException;

}