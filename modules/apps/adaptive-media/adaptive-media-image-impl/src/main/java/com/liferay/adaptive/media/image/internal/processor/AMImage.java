/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.processor;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.internal.configuration.AMImageAttributeMapping;
import com.liferay.adaptive.media.processor.AMProcessor;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.InputStream;

import java.net.URI;

import java.util.function.Supplier;

/**
 * @author Adolfo Pérez
 */
public final class AMImage implements AdaptiveMedia<AMProcessor<FileVersion>> {

	public AMImage(
		Supplier<InputStream> supplier,
		AMImageAttributeMapping amImageAttributeMapping, URI uri) {

		_supplier = supplier;
		_amImageAttributeMapping = amImageAttributeMapping;
		_uri = uri;
	}

	@Override
	public InputStream getInputStream() {
		return _supplier.get();
	}

	@Override
	public URI getURI() {
		return _uri;
	}

	@Override
	public <V> V getValue(
		AMAttribute<AMProcessor<FileVersion>, V> amAttribute) {

		return _amImageAttributeMapping.getValue(amAttribute);
	}

	private final AMImageAttributeMapping _amImageAttributeMapping;
	private final Supplier<InputStream> _supplier;
	private final URI _uri;

}