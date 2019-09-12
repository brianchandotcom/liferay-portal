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

package com.liferay.document.library.opener.onedrive.web.internal.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alicia García García
 */
public class GraphServicePortalException extends PortalException {

	public GraphServicePortalException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public static class AccessDenied extends GraphServicePortalException {

		public AccessDenied(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class ActivityLimitReached
		extends GraphServicePortalException {

		public ActivityLimitReached(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class InvalidRange extends GraphServicePortalException {

		public InvalidRange(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class InvalidRequest extends GraphServicePortalException {

		public InvalidRequest(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class ItemNotFound extends GraphServicePortalException {

		public ItemNotFound(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class MalwareDetected extends GraphServicePortalException {

		public MalwareDetected(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class NameAlreadyExists extends GraphServicePortalException {

		public NameAlreadyExists(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class NotAllowed extends GraphServicePortalException {

		public NotAllowed(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class NotSupported extends GraphServicePortalException {

		public NotSupported(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class QuotaLimitReached extends GraphServicePortalException {

		public QuotaLimitReached(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class ResourceModified extends GraphServicePortalException {

		public ResourceModified(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class ResyncRequired extends GraphServicePortalException {

		public ResyncRequired(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class ServiceNotAvailable
		extends GraphServicePortalException {

		public ServiceNotAvailable(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class Unauthenticated extends GraphServicePortalException {

		public Unauthenticated(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

}