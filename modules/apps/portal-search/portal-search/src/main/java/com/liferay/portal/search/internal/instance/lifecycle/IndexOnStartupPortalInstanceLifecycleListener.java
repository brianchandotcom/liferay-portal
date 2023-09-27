/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.SearchException;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Michael C. Han
 */
public class IndexOnStartupPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	public IndexOnStartupPortalInstanceLifecycleListener(
		IndexWriterHelper indexWriterHelper, String className,
		Map<String, Serializable> taskContextMap) {

		_indexWriterHelper = indexWriterHelper;
		_className = className;
		_taskContextMap = taskContextMap;
	}

	@Override
	public void portalInstanceRegistered(Company company) {
		try {
			_indexWriterHelper.reindex(
				UserConstants.USER_ID_DEFAULT,
				"reindexOnActivate#" + _className,
				new long[] {company.getCompanyId()}, _className,
				_taskContextMap);
		}
		catch (SearchException searchException) {
			_log.error("Unable to reindex on activation", searchException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexOnStartupPortalInstanceLifecycleListener.class);

	private final String _className;
	private final IndexWriterHelper _indexWriterHelper;
	private final Map<String, Serializable> _taskContextMap;

}