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

package com.liferay.client.extension.type.manager;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CET;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public interface CETManager {

	public void addCET(ClientExtensionEntry clientExtensionEntry)
		throws PortalException;

	public CET addCET(
			String baseURL, long companyId, String description, String name,
			String primaryKey, Properties properties, String sourceCodeURL,
			String type, String typeSettings)
		throws PortalException;

	public void deleteCET(CET cet);

	public void deleteCET(ClientExtensionEntry clientExtensionEntry);

	public CET getCET(long companyId, String primaryKey);

	public List<CET> getCETs(
		long companyId, String keywords, String type, Pagination pagination,
		Sort sort);

	public int getCETsCount(long companyId, String keywords, String type);

}