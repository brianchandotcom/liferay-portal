/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal.handler;

import com.liferay.document.library.opener.onedrive.web.internal.exception.GraphServicePortalException;
import com.liferay.portal.kernel.exception.PortalException;

import com.microsoft.graph.http.GraphError;
import com.microsoft.graph.http.GraphServiceException;

import java.util.Objects;

/**
 * @author Alicia García García
 */
public class GraphServiceExceptionHandler {

	public static void handlePortalException(GraphServiceException gse)
		throws PortalException {

		GraphError serviceError = gse.getServiceError();

		if (Objects.equals(serviceError.code, "accessDenied")) {
			throw new GraphServicePortalException.AccessDenied(
				gse.getMessage(), gse);
		}
		else if (Objects.equals(serviceError.code, "activityLimitReached")) {
			throw new GraphServicePortalException.ActivityLimitReached(
				gse.getMessage(), gse);
		}
		else if (Objects.equals(serviceError.code, "invalidRange")) {
			throw new GraphServicePortalException.InvalidRange(
				gse.getMessage(), gse);
		}
		else if (Objects.equals(serviceError.code, "invalidRequest")) {
			throw new GraphServicePortalException.InvalidRequest(
				gse.getMessage(), gse);
		}
		else if (Objects.equals(serviceError.code, "itemNotFound")) {
			throw new GraphServicePortalException.ItemNotFound(
				gse.getMessage(), gse);
		}
		else if (Objects.equals(serviceError.code, "malwareDetected")) {
			throw new GraphServicePortalException.MalwareDetected(
				gse.getMessage(), gse);
		}
		else if (Objects.equals(serviceError.code, "nameAlreadyExists")) {
			throw new GraphServicePortalException.NameAlreadyExists(
				gse.getMessage(), gse);
		}
		else if (Objects.equals(serviceError.code, "notAllowed")) {
			throw new GraphServicePortalException.NotAllowed(
				gse.getMessage(), gse);
		}
		else if (Objects.equals(serviceError.code, "notSupported")) {
			throw new GraphServicePortalException.NotSupported(
				gse.getMessage(), gse);
		}
		else if (Objects.equals(serviceError.code, "resourceModified")) {
			throw new GraphServicePortalException.ResourceModified(
				gse.getMessage(), gse);
		}
		else if (Objects.equals(serviceError.code, "resyncRequired")) {
			throw new GraphServicePortalException.ResyncRequired(
				gse.getMessage(), gse);
		}
		else if (Objects.equals(serviceError.code, "serviceNotAvailable")) {
			throw new GraphServicePortalException.ServiceNotAvailable(
				gse.getMessage(), gse);
		}
		else if (Objects.equals(serviceError.code, "quotaLimitReached")) {
			throw new GraphServicePortalException.QuotaLimitReached(
				gse.getMessage(), gse);
		}
		else if (Objects.equals(serviceError.code, "unauthenticated")) {
			throw new GraphServicePortalException.Unauthenticated(
				gse.getMessage(), gse);
		}
		else {
			throw new GraphServicePortalException(gse.getMessage(), gse);
		}
	}

}