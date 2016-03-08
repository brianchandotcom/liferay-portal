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

import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.search.internal.SearchConstants;

/**
 * @author Michael C. Han
 */
public class IndexOnStartupPortalInstanceLifecycleListener
	implements PortalInstanceLifecycleListener {

	public IndexOnStartupPortalInstanceLifecycleListener(
		ClusterMasterExecutor clusterMasterExecutor,
		IndexWriterHelper indexWriterHelper, Props props, String className) {

		_clusterMasterExecutor = clusterMasterExecutor;
		_indexWriterHelper = indexWriterHelper;
		_props = props;
		_className = className;
	}

	@Override
	public void portalInstancePreregistered(long companyId) {
	}

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (!GetterUtil.getBoolean(_props.get(PropsKeys.INDEX_ON_STARTUP)) ||
			!_clusterMasterExecutor.isMaster()) {

			return;
		}

		try {
			_indexWriterHelper.reindex(
				UserConstants.USER_ID_DEFAULT,
				SearchConstants.INDEX_ON_ACTIVATE_BACKGROUND_TASK_NAME + "." +
					_className,
				new long[] {company.getCompanyId()}, _className, null);
		}
		catch (SearchException se) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to reindex on activation", se);
			}
		}
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexOnStartupPortalInstanceLifecycleListener.class);

	private final String _className;
	private final ClusterMasterExecutor _clusterMasterExecutor;
	private final IndexWriterHelper _indexWriterHelper;
	private final Props _props;

}