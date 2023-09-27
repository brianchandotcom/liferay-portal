/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.rule.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the COREntryRel service. Represents a row in the &quot;COREntryRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Luca Pellizzon
 * @see COREntryRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.order.rule.model.impl.COREntryRelImpl"
)
@ProviderType
public interface COREntryRel extends COREntryRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.order.rule.model.impl.COREntryRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<COREntryRel, Long> COR_ENTRY_REL_ID_ACCESSOR =
		new Accessor<COREntryRel, Long>() {

			@Override
			public Long get(COREntryRel corEntryRel) {
				return corEntryRel.getCOREntryRelId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<COREntryRel> getTypeClass() {
				return COREntryRel.class;
			}

		};

	public COREntry getCOREntry()
		throws com.liferay.portal.kernel.exception.PortalException;

}