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

package com.liferay.portal.search.internal.batch;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.batch.BatchIndexingHelper;
import com.liferay.portal.search.configuration.ReindexConfiguration;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
@Component(
	configurationPid = "com.liferay.portal.search.configuration.ReindexConfiguration",
	immediate = true, service = BatchIndexingHelper.class
)
public class BatchIndexingHelperImpl implements BatchIndexingHelper {

	@Override
	public int getBulkSize(String entryClassName) {
		Optional<Integer> optional = Stream.of(
			_reindexConfiguration.indexingBatchSizes()
		).map(
			line -> StringUtil.split(line, StringPool.EQUAL)
		).filter(
			pair -> pair.length == 2
		).filter(
			pair -> entryClassName.equals(pair[0])
		).map(
			pair -> GetterUtil.getInteger(pair[1])
		).findAny();

		return optional.orElse(Indexer.DEFAULT_INTERVAL);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_reindexConfiguration = ConfigurableUtil.createConfigurable(
			ReindexConfiguration.class, properties);
	}

	private ReindexConfiguration _reindexConfiguration;

}