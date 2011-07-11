/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.util;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Marcellus Tavares
 */
public abstract class BaseDDLExporter implements DDLExporter {

	public byte[] export(long recordSetId) throws Exception {
		return doExport(
			recordSetId, WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public byte[] export(long recordSetId, int status) throws Exception {
		return doExport(
			recordSetId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public byte[] export(long recordSetId, int status, int start, int end)
		throws Exception {

		return doExport(recordSetId, status, start, end, null);
	}

	public byte[] export(
			long recordSetId, int status, int start, int end,
			OrderByComparator orderByComparator)
		throws Exception {

		return doExport(recordSetId, status, start, end, orderByComparator);
	}

	protected abstract byte[] doExport(
			long recordSetId, int status, int start, int end,
			OrderByComparator orderByComparator)
		throws Exception;

}