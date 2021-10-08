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