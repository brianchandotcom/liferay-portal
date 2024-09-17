/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.spi.contributor.helper;

/**
 * @author André de Oliveira
 */
public interface CriteriaBuilder {

	/**
	 * Specifies the className of the similar result.
	 *
	 * @param  className The fully qualified class name of the content type.
	 * @return This {@link CriteriaBuilder} instance.
	 */
	public CriteriaBuilder type(String className);

	/**
	 * Specifies the unique identifier (UID) of the similar result.
	 *
	 * @param  uid The unique identifier of the similar result.
	 * @return This {@link CriteriaBuilder} instance.
	 */
	public CriteriaBuilder uid(String uid);

}