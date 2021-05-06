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

package com.liferay.layout.list.retriever;

import com.liferay.info.pagination.Pagination;

import java.util.Optional;

/**
 * @author Eudaldo Alonso
 */
public class DefaultLayoutListRetrieverContext
	implements LayoutListRetrieverContext {

	@Override
	public Optional<long[][]> getAssetCategoryIdsOptional() {
		return Optional.ofNullable(_assetCategoryIds);
	}

	@Override
	public Optional<Object> getContextObjectOptional() {
		return Optional.ofNullable(_contextObject);
	}

	@Override
	public Optional<Pagination> getPaginationOptional() {
		return Optional.ofNullable(_pagination);
	}

	@Override
	public Optional<long[]> getSegmentsEntryIdsOptional() {
		return Optional.ofNullable(_segmentsEntryIds);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public Optional<long[]> getSegmentsExperienceIdsOptional() {
		return Optional.ofNullable(_segmentsExperienceIds);
	}

	public void setAssetCategoryIds(long[][] assetCategoryIds) {
		_assetCategoryIds = assetCategoryIds;
	}

	public void setContextObject(Object contextObject) {
		_contextObject = contextObject;
	}

	public void setPagination(Pagination pagination) {
		_pagination = pagination;
	}

	public void setSegmentsEntryIds(long[] segmentsEntryIds) {
		_segmentsEntryIds = segmentsEntryIds;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public void setSegmentsExperienceIds(long[] segmentsExperienceIds) {
		_segmentsExperienceIds = segmentsExperienceIds;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #setSegmentsExperienceIds(long[])}
	 */
	@Deprecated
	public void setSegmentsExperienceIdsOptional(long[] segmentsExperienceIds) {
		_segmentsExperienceIds = segmentsExperienceIds;
	}

	private long[][] _assetCategoryIds;
	private Object _contextObject;
	private Pagination _pagination;
	private long[] _segmentsEntryIds;
	private long[] _segmentsExperienceIds;

}