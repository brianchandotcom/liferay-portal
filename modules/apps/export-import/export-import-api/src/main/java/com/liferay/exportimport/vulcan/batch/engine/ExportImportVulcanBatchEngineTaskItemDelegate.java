/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.vulcan.batch.engine;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alejandro Tardín
 */
public interface ExportImportVulcanBatchEngineTaskItemDelegate<T>
	extends VulcanBatchEngineTaskItemDelegate<T> {

	public ExportImportDescriptor getExportImportDescriptor();

	public interface ExportImportDescriptor {

		public default String getDescription(Locale locale) {
			return null;
		}

		public String getKey();

		public String getLabelLanguageKey();

		public String getModelClassName();

		public default List<String> getNestedFields() {
			return null;
		}

		public default Map<String, Serializable> getParameters(
			PortletDataContext portletDataContext) {

			return null;
		}

		public String getPortletId();

		public default int getRank() {
			return 100;
		}

		public default Map<String, String[]> getReferences() {
			return null;
		}

		public Scope getScope();

		public default String getTag(Locale locale) {
			return null;
		}

		public default boolean isActive(PortletDataContext portletDataContext) {
			return true;
		}

		public default boolean isApplicableExternalReferenceCode(
			String externalReferenceCode) {

			return true;
		}

		public default boolean isHidden() {
			return false;
		}

		public default boolean isStagingSupported() {
			return false;
		}

	}

	public enum Scope {

		COMPANY, DEPOT, SITE

	}

}