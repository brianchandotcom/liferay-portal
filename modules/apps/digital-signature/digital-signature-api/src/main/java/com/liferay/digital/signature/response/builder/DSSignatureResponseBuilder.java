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

package com.liferay.digital.signature.response.builder;

import com.liferay.digital.signature.common.DSSignaturePackageStatus;
import com.liferay.digital.signature.response.DSSignatureResponse;

import java.time.ZonedDateTime;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface DSSignatureResponseBuilder {

	public DSSignatureResponse getDSSignatureResponse();

	public DSSignatureResponseBuilder setDSSignatureRequestStatus(
		DSSignaturePackageStatus dsSignaturePackageStatus);

	public DSSignatureResponseBuilder setErrorCode(String errorCode);

	public DSSignatureResponseBuilder setErrorDebugMessage(
		String errorDebugMessage);

	public DSSignatureResponseBuilder setErrorMessage(String errorMessage);

	public DSSignatureResponseBuilder setExternalReferenceKey(
		String externalReferenceKey);

	public DSSignatureResponseBuilder setExternalReferenceURI(
		String externalReferenceURI);

	public DSSignatureResponseBuilder setTimestampZonedDateTime(
		ZonedDateTime timestampZonedDateTime);

}