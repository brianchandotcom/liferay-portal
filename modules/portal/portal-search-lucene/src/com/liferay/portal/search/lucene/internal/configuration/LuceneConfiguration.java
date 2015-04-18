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

package com.liferay.portal.search.lucene.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Michael C. Han
 */
@Meta.OCD(
	id = "com.liferay.portal.search.lucene.internal.configuration.LuceneConfiguration",
	localization = "content.Language"
)
public interface LuceneConfiguration {

	@Meta.AD(deflt = "10000", required = false)
	public int analyzerMaxTokens();

	@Meta.AD(deflt = "16", required = false)
	public int bufferSize();

	@Meta.AD(deflt = "0", required = false)
	public int commitBatchSize();

	@Meta.AD(deflt = "0", required = false)
	public long commitTimeInterval();

	@Meta.AD(deflt = "${liferay.home}/data/lucene", required = false)
	public String dir();

	@Meta.AD(deflt = "true", required = false)
	public boolean indexDumpCompressionEnabled();

	@Meta.AD(deflt = "10", required = false)
	public int mergeFactor();

	@Meta.AD(
		deflt = "org.apache.lucene.index.LogDocMergePolicy", required = false
	)
	public String mergePolicy();

	@Meta.AD(
		deflt = "org.apache.lucene.index.ConcurrentMergeScheduler",
		required = false
	)
	public String mergeScheduler();

	@Meta.AD(deflt = "ram", optionValues = {"file", "ram"}, required = false)
	public String storeType();

	@Meta.AD(deflt = "false", required = false)
	public boolean storeTypeFileForceMMap();

}