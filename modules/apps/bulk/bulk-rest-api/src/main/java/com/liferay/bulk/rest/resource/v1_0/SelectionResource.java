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

package com.liferay.bulk.rest.resource.v1_0;

import com.liferay.bulk.rest.dto.v1_0.DocumentBulkSelection;
import com.liferay.bulk.rest.dto.v1_0.Selection;
import com.liferay.portal.kernel.model.Company;

import javax.annotation.Generated;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/bulk-rest/v1.0
 *
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public interface SelectionResource {

	public Selection postBulkSelection(
			DocumentBulkSelection documentBulkSelection)
		throws Exception;

	public void setContextCompany(Company contextCompany);

}