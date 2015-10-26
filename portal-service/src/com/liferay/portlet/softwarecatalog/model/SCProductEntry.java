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

package com.liferay.portlet.softwarecatalog.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the SCProductEntry service. Represents a row in the &quot;SCProductEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SCProductEntryModel
 * @see com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryImpl
 * @see com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl
 * @generated
 */
@ProviderType
public interface SCProductEntry extends SCProductEntryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SCProductEntry, Long> PRODUCT_ENTRY_ID_ACCESSOR =
		new Accessor<SCProductEntry, Long>() {
			@Override
			public Long get(SCProductEntry scProductEntry) {
				return scProductEntry.getProductEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SCProductEntry> getTypeClass() {
				return SCProductEntry.class;
			}
		};

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion getLatestVersion();

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses();

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> getScreenshots();
}