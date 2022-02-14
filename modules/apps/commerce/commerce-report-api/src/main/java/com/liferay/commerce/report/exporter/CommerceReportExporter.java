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

	public boolean isValidJRXMLTemplate(InputStream templateInputStream);

}