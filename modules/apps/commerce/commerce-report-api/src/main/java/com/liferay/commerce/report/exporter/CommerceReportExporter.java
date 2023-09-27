/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.report.exporter;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.repository.model.FileEntry;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface CommerceReportExporter {

	public byte[] export(
			Collection<?> beanCollection, FileEntry fileEntry,
			Map<String, Object> parameters)
		throws IOException;

	public boolean isValidJRXMLTemplate(InputStream inputStream);

}