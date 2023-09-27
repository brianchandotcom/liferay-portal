/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ExpandoColumn service. Represents a row in the &quot;ExpandoColumn&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoColumnModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.expando.model.impl.ExpandoColumnImpl"
)
@ProviderType
public interface ExpandoColumn extends ExpandoColumnModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.expando.model.impl.ExpandoColumnImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ExpandoColumn, Long> COLUMN_ID_ACCESSOR =
		new Accessor<ExpandoColumn, Long>() {

			@Override
			public Long get(ExpandoColumn expandoColumn) {
				return expandoColumn.getColumnId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ExpandoColumn> getTypeClass() {
				return ExpandoColumn.class;
			}

		};

	public java.io.Serializable getDefaultValue();

	public String getDisplayName(java.util.Locale locale);

	public com.liferay.portal.kernel.util.UnicodeProperties
		getTypeSettingsProperties();

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			typeSettingsUnicodeProperties);

}