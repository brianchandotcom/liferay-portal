/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the BatchEngineImportReportEntry service. Represents a row in the &quot;BatchEngineImportReportEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportReportEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.batch.engine.model.impl.BatchEngineImportReportEntryImpl"
)
@ProviderType
public interface BatchEngineImportReportEntry
	extends BatchEngineImportReportEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.batch.engine.model.impl.BatchEngineImportReportEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<BatchEngineImportReportEntry, Long>
		BATCH_ENGINE_IMPORT_REPORT_ENTRY_ID_ACCESSOR =
			new Accessor<BatchEngineImportReportEntry, Long>() {

				@Override
				public Long get(
					BatchEngineImportReportEntry batchEngineImportReportEntry) {

					return batchEngineImportReportEntry.
						getBatchEngineImportReportEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<BatchEngineImportReportEntry> getTypeClass() {
					return BatchEngineImportReportEntry.class;
				}

			};

}