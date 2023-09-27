/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.onedrive.web.internal.exception.mapper;

import com.liferay.document.library.opener.onedrive.web.internal.exception.GraphServicePortalException;

import com.microsoft.graph.http.GraphError;
import com.microsoft.graph.http.GraphServiceException;

import java.util.Objects;

/**
 * @author Alicia García García
 */
public class GraphServiceExceptionPortalExceptionMapper {

	public static GraphServicePortalException map(
		GraphServiceException graphServiceException) {

		GraphError serviceError = graphServiceException.getServiceError();

		if (Objects.equals(serviceError.code, "accessDenied")) {
			return new GraphServicePortalException.AccessDenied(
				graphServiceException.getMessage(), graphServiceException);
		}
		else if (Objects.equals(serviceError.code, "activityLimitReached")) {
			return new GraphServicePortalException.ActivityLimitReached(
				graphServiceException.getMessage(), graphServiceException);
		}
		else if (Objects.equals(serviceError.code, "invalidRange")) {
			return new GraphServicePortalException.InvalidRange(
				graphServiceException.getMessage(), graphServiceException);
		}
		else if (Objects.equals(serviceError.code, "invalidRequest")) {
			return new GraphServicePortalException.InvalidRequest(
				graphServiceException.getMessage(), graphServiceException);
		}
		else if (Objects.equals(serviceError.code, "itemNotFound")) {
			return new GraphServicePortalException.ItemNotFound(
				graphServiceException.getMessage(), graphServiceException);
		}
		else if (Objects.equals(serviceError.code, "malwareDetected")) {
			return new GraphServicePortalException.MalwareDetected(
				graphServiceException.getMessage(), graphServiceException);
		}
		else if (Objects.equals(serviceError.code, "nameAlreadyExists")) {
			return new GraphServicePortalException.NameAlreadyExists(
				graphServiceException.getMessage(), graphServiceException);
		}
		else if (Objects.equals(serviceError.code, "notAllowed")) {
			return new GraphServicePortalException.NotAllowed(
				graphServiceException.getMessage(), graphServiceException);
		}
		else if (Objects.equals(serviceError.code, "notSupported")) {
			return new GraphServicePortalException.NotSupported(
				graphServiceException.getMessage(), graphServiceException);
		}
		else if (Objects.equals(serviceError.code, "resourceModified")) {
			return new GraphServicePortalException.ResourceModified(
				graphServiceException.getMessage(), graphServiceException);
		}
		else if (Objects.equals(serviceError.code, "resyncRequired")) {
			return new GraphServicePortalException.ResyncRequired(
				graphServiceException.getMessage(), graphServiceException);
		}
		else if (Objects.equals(serviceError.code, "serviceNotAvailable")) {
			return new GraphServicePortalException.ServiceNotAvailable(
				graphServiceException.getMessage(), graphServiceException);
		}
		else if (Objects.equals(serviceError.code, "quotaLimitReached")) {
			return new GraphServicePortalException.QuotaLimitReached(
				graphServiceException.getMessage(), graphServiceException);
		}
		else if (Objects.equals(serviceError.code, "unauthenticated")) {
			return new GraphServicePortalException.Unauthenticated(
				graphServiceException.getMessage(), graphServiceException);
		}

		return new GraphServicePortalException(
			graphServiceException.getMessage(), graphServiceException);
	}

}