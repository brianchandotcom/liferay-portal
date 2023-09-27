/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.onedrive.web.internal.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alicia García García
 */
public class GraphServicePortalException extends PortalException {

	public GraphServicePortalException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public static class AccessDenied extends GraphServicePortalException {

		public AccessDenied(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

	public static class ActivityLimitReached
		extends GraphServicePortalException {

		public ActivityLimitReached(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

	public static class InvalidRange extends GraphServicePortalException {

		public InvalidRange(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

	public static class InvalidRequest extends GraphServicePortalException {

		public InvalidRequest(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

	public static class ItemNotFound extends GraphServicePortalException {

		public ItemNotFound(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

	public static class MalwareDetected extends GraphServicePortalException {

		public MalwareDetected(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

	public static class NameAlreadyExists extends GraphServicePortalException {

		public NameAlreadyExists(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

	public static class NotAllowed extends GraphServicePortalException {

		public NotAllowed(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

	public static class NotSupported extends GraphServicePortalException {

		public NotSupported(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

	public static class QuotaLimitReached extends GraphServicePortalException {

		public QuotaLimitReached(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

	public static class ResourceModified extends GraphServicePortalException {

		public ResourceModified(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

	public static class ResyncRequired extends GraphServicePortalException {

		public ResyncRequired(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

	public static class ServiceNotAvailable
		extends GraphServicePortalException {

		public ServiceNotAvailable(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

	public static class Unauthenticated extends GraphServicePortalException {

		public Unauthenticated(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

}