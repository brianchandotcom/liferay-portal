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
 * The extended model interface for the KaleoNode service. Represents a row in the &quot;KaleoNode&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNodeModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.workflow.kaleo.model.impl.KaleoNodeImpl"
)
@ProviderType
public interface KaleoNode extends KaleoNodeModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNodeImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<KaleoNode, Long> KALEO_NODE_ID_ACCESSOR =
		new Accessor<KaleoNode, Long>() {

			@Override
			public Long get(KaleoNode kaleoNode) {
				return kaleoNode.getKaleoNodeId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<KaleoNode> getTypeClass() {
				return KaleoNode.class;
			}

		};

	public KaleoTransition getDefaultKaleoTransition()
		throws com.liferay.portal.kernel.exception.PortalException;

	public KaleoTransition getKaleoTransition(String name)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<KaleoTransition> getKaleoTransitions();

	public boolean hasKaleoTransition();

}