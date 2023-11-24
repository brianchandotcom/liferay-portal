/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceVirtualOrderItemFileEntry service. Represents a row in the &quot;CVirtualOrderItemFileEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemFileEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemFileEntryImpl"
)
@ProviderType
public interface CommerceVirtualOrderItemFileEntry
	extends CommerceVirtualOrderItemFileEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemFileEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceVirtualOrderItemFileEntry, Long>
		COMMERCE_VIRTUAL_ORDER_ITEM_FILE_ENTRY_ID_ACCESSOR =
			new Accessor<CommerceVirtualOrderItemFileEntry, Long>() {

				@Override
				public Long get(
					CommerceVirtualOrderItemFileEntry
						commerceVirtualOrderItemFileEntry) {

					return commerceVirtualOrderItemFileEntry.
						getCommerceVirtualOrderItemFileEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceVirtualOrderItemFileEntry> getTypeClass() {
					return CommerceVirtualOrderItemFileEntry.class;
				}

			};

	public CommerceVirtualOrderItem getCommerceVirtualOrderItem()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

}