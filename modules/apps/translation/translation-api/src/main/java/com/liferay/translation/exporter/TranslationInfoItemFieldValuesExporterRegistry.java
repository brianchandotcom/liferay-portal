/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.exporter;

import java.util.Collection;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Alejandro Tardín
 */
@ProviderType
public interface TranslationInfoItemFieldValuesExporterRegistry {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getTranslationInfoItemFieldValuesExporters()}
	 */
	@Deprecated
	public default Collection<TranslationInfoItemFieldValuesExporter>
		getTranslationInfoItemFieldValueExporters() {

		return getTranslationInfoItemFieldValuesExporters();
	}

	public TranslationInfoItemFieldValuesExporter
		getTranslationInfoItemFieldValuesExporter(String mimeType);

	public Collection<TranslationInfoItemFieldValuesExporter>
		getTranslationInfoItemFieldValuesExporters();

}