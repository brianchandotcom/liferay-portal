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

package com.liferay.portal.search.internal.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;

/**
 * @author Michael C. Han
 */
public class IndexOnStartupPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	public IndexOnStartupPortalInstanceLifecycleListener(
		IndexWriterHelper indexWriterHelper, Props props, String className) {

		_indexWriterHelper = indexWriterHelper;
		_props = props;
		_className = className;
	}

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (!GetterUtil.getBoolean(_props.get(PropsKeys.INDEX_ON_STARTUP))) {
			return;
		}

		try {
			_indexWriterHelper.reindex(
				UserConstants.USER_ID_DEFAULT,
				"reindexOnActivate#" + _className,
				new long[] {company.getCompanyId()}, _className, null);
		}
		catch (SearchException se) {
			_log.error("Unable to reindex on activation", se);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexOnStartupPortalInstanceLifecycleListener.class);

	private final String _className;
	private final IndexWriterHelper _indexWriterHelper;
	private final Props _props;

}