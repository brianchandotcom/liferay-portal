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
@Component(immediate = true, service = AMImageURLFactory.class)
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
		catch (UnsupportedEncodingException uee) {
			throw new AMRuntimeException.UnsupportedEncodingException(uee);
		}
	}

	@Reference
	private AMURIResolver _amURIResolver;

}