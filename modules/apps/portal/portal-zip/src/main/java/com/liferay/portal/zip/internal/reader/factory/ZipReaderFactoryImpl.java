/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.zip.internal.reader.factory;

import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactory;
import com.liferay.portal.zip.internal.reader.IOZipReader;
import com.liferay.portal.zip.internal.reader.NIOZipReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 */
@Component(service = ZipReaderFactory.class)
public class ZipReaderFactoryImpl implements ZipReaderFactory {

	@Override
	public ZipReader getZipReader(File file) {
		if (FeatureFlagManagerUtil.isEnabled(
				CompanyThreadLocal.getCompanyId(), "LPD-75525")) {

			return new NIOZipReader(file);
		}

		return new IOZipReader(file);
	}

	@Override
	public ZipReader getZipReader(InputStream inputStream) throws IOException {
		if (FeatureFlagManagerUtil.isEnabled(
				CompanyThreadLocal.getCompanyId(), "LPD-75525")) {

			return new NIOZipReader(inputStream);
		}

		return new IOZipReader(inputStream);
	}

}