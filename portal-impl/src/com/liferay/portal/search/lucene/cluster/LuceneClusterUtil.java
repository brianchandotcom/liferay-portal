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

package com.liferay.portal.search.lucene.cluster;

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.lucene.LuceneHelperUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Shuyang Zhou
 * @author Tina Tian
 */
public class LuceneClusterUtil {

	public static void loadIndexesFromCluster(long companyId) {
		LuceneHelperUtil.loadIndexesFromCluster(companyId);
	}

	public static void loadIndexesFromCluster(
		long[] companyIds, Address bootupAddress) {

		if (bootupAddress == null) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Start loading Lucene index files from cluster node " +
					bootupAddress);
		}

		InputStream inputStream = null;

		for (long companyId : companyIds) {
			try {
				inputStream =
					LuceneHelperUtil.getLoadIndexesInputStreamFromCluster(
						companyId, bootupAddress);

				LuceneHelperUtil.loadIndex(companyId, inputStream);
			}
			catch (SystemException se) {
				throw se;
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
			finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					}
					catch (IOException ioe) {
						throw new SystemException(ioe);
					}
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LuceneClusterUtil.class);

}