/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.batch;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.batch.BatchIndexingHelper;
import com.liferay.portal.search.configuration.ReindexConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
@Component(
	configurationPid = "com.liferay.portal.search.configuration.ReindexConfiguration",
	service = BatchIndexingHelper.class
)
public class BatchIndexingHelperImpl implements BatchIndexingHelper {

	@Override
	public int getBulkSize(String entryClassName) {
		for (String indexingBatchSize :
				_reindexConfiguration.indexingBatchSizes()) {

			String[] pair = StringUtil.split(
				indexingBatchSize, StringPool.EQUAL);

			if ((pair.length == 2) && entryClassName.equals(pair[0])) {
				return GetterUtil.getInteger(pair[1]);
			}
		}

		return Indexer.DEFAULT_INTERVAL;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_reindexConfiguration = ConfigurableUtil.createConfigurable(
			ReindexConfiguration.class, properties);
	}

	private volatile ReindexConfiguration _reindexConfiguration;

}