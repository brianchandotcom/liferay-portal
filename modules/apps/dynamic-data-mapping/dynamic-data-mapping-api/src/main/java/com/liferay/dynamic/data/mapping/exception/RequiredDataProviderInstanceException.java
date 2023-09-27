/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marcellus Tavares
 */
public class RequiredDataProviderInstanceException extends PortalException {

	public static class
		MustNotDeleteDataProviderInstanceReferencedByDataProviderInstanceLinks
			extends RequiredDataProviderInstanceException {

		public MustNotDeleteDataProviderInstanceReferencedByDataProviderInstanceLinks(
			long dataProviderInstanceId) {

			super(
				String.format(
					"Data provider instance %s cannot be deleted because it " +
						"is referenced by one or more data provider instance " +
							"links",
					dataProviderInstanceId));

			this.dataProviderInstanceId = dataProviderInstanceId;
		}

		public long dataProviderInstanceId;

	}

	private RequiredDataProviderInstanceException(String message) {
		super(message);
	}

}