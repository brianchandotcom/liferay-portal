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

package com.liferay.digital.signature.model.builder;

import com.liferay.digital.signature.model.DSDocument;
import com.liferay.digital.signature.model.DSDocumentDisplay;
import com.liferay.digital.signature.model.DSSignerAcknowledgement;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface DSDocumentBuilder {

	public DSDocument getDSDocument();

	public DSDocumentBuilder setAuthoritative(Boolean authoritative);

	public DSDocumentBuilder setBase64String(String base64String);

	public DSDocumentBuilder setDescription(String description);

	public DSDocumentBuilder setDocumentURL(String documentURL);

	public DSDocumentBuilder setExtension(String extension);

	public DSDocumentBuilder setSupplementalDocumentInfo(
		DSDocumentDisplay dsDocumentDisplay,
		DSSignerAcknowledgement dsSignerAcknowledgement,
		Boolean includeInDownload);

}