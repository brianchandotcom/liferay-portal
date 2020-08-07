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

package com.liferay.commerce.bom.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceBOMEntry service. Represents a row in the &quot;CommerceBOMEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Luca Pellizzon
 * @see CommerceBOMEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.bom.model.impl.CommerceBOMEntryImpl"
)
@ProviderType
public interface CommerceBOMEntry
	extends CommerceBOMEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.bom.model.impl.CommerceBOMEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceBOMEntry, Long>
		COMMERCE_BOM_ENTRY_ID_ACCESSOR =
			new Accessor<CommerceBOMEntry, Long>() {

				@Override
				public Long get(CommerceBOMEntry commerceBOMEntry) {
					return commerceBOMEntry.getCommerceBOMEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceBOMEntry> getTypeClass() {
					return CommerceBOMEntry.class;
				}

			};

}