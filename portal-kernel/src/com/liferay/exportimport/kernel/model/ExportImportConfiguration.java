/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ExportImportConfiguration service. Represents a row in the &quot;ExportImportConfiguration&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportConfigurationModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.exportimport.model.impl.ExportImportConfigurationImpl"
)
@ProviderType
public interface ExportImportConfiguration
	extends ExportImportConfigurationModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.exportimport.model.impl.ExportImportConfigurationImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ExportImportConfiguration, Long>
		EXPORT_IMPORT_CONFIGURATION_ID_ACCESSOR =
			new Accessor<ExportImportConfiguration, Long>() {

				@Override
				public Long get(
					ExportImportConfiguration exportImportConfiguration) {

					return exportImportConfiguration.
						getExportImportConfigurationId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ExportImportConfiguration> getTypeClass() {
					return ExportImportConfiguration.class;
				}

			};

	public java.util.Map<String, java.io.Serializable> getSettingsMap();

}