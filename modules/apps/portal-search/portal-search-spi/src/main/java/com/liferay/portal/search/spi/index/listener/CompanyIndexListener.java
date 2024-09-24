/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.index.listener;

/**
 * @author Adam Brandizzi
 */
public interface CompanyIndexListener {

	/**
	 * This method is called after a company index is created. Override this
	 * method to implement specific logic to be executed after index creation.
	 *
	 * @param indexName The name of the newly created company index.
	 */
	public default void onAfterCreate(String indexName) {
	}

	/**
	 * This method is called before a company index is deleted. Override this
	 * method to implement specific logic to be executed before index deletion.
	 *
	 * @param indexName The name of the company index to delete.
	 */
	public default void onBeforeDelete(String indexName) {
	}

}