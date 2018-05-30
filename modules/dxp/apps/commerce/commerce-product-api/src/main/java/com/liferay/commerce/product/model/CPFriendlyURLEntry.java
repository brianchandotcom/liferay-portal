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

package com.liferay.commerce.product.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CPFriendlyURLEntry service. Represents a row in the &quot;CPFriendlyURLEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPFriendlyURLEntryModel
 * @see com.liferay.commerce.product.model.impl.CPFriendlyURLEntryImpl
 * @see com.liferay.commerce.product.model.impl.CPFriendlyURLEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.model.impl.CPFriendlyURLEntryImpl")
@ProviderType
public interface CPFriendlyURLEntry extends CPFriendlyURLEntryModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.model.impl.CPFriendlyURLEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPFriendlyURLEntry, Long> CP_FRIENDLY_URL_ENTRY_ID_ACCESSOR =
		new Accessor<CPFriendlyURLEntry, Long>() {
			@Override
			public Long get(CPFriendlyURLEntry cpFriendlyURLEntry) {
				return cpFriendlyURLEntry.getCPFriendlyURLEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CPFriendlyURLEntry> getTypeClass() {
				return CPFriendlyURLEntry.class;
			}
		};

	public java.util.Locale getLocale();
}