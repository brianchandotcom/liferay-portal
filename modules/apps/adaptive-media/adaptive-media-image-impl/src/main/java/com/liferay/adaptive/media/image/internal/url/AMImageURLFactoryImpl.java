/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.url;

import com.liferay.adaptive.media.AMURIResolver;
import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.url.AMImageURLFactory;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.UnsupportedEncodingException;

import java.net.URI;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = AMImageURLFactory.class)
public class AMImageURLFactoryImpl implements AMImageURLFactory {

	@Override
	public URI createFileEntryURL(
		FileVersion fileVersion,
		AMImageConfigurationEntry amImageConfigurationEntry) {

		Date modifiedDate = fileVersion.getModifiedDate();

		String relativeURI = String.format(
			"image/%d/%s/%s?t=%d", fileVersion.getFileEntryId(),
			amImageConfigurationEntry.getUUID(),
			_encode(fileVersion.getFileName()), modifiedDate.getTime());

		return _amURIResolver.resolveURI(URI.create(relativeURI));
	}

	@Override
	public URI createFileVersionURL(
		FileVersion fileVersion,
		AMImageConfigurationEntry amImageConfigurationEntry) {

		String relativeURI = String.format(
			"image/%d/%d/%s/%s", fileVersion.getFileEntryId(),
			fileVersion.getFileVersionId(), amImageConfigurationEntry.getUUID(),
			_encode(fileVersion.getFileName()));

		return _amURIResolver.resolveURI(URI.create(relativeURI));
	}

	private String _encode(String s) {
		try {
			return URLEncoder.encode(s, StandardCharsets.UTF_8.name());
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new AMRuntimeException.UnsupportedEncodingException(
				unsupportedEncodingException);
		}
	}

	@Reference
	private AMURIResolver _amURIResolver;

}