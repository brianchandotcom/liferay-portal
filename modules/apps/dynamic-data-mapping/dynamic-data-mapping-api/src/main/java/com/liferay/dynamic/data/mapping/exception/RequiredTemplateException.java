/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class RequiredTemplateException extends PortalException {

	public RequiredTemplateException() {
	}

	public RequiredTemplateException(String msg) {
		super(msg);
	}

	public RequiredTemplateException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public RequiredTemplateException(Throwable throwable) {
		super(throwable);
	}

	public static class MustNotDeleteTemplateReferencedByTemplateLinks
		extends RequiredTemplateException {

		public MustNotDeleteTemplateReferencedByTemplateLinks(long templateId) {
			super(
				String.format(
					"Template %s cannot be deleted because it is referenced " +
						"by one or more template links",
					templateId));
		}

	}

}