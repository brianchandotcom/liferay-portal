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

package com.liferay.portal.kernel.search;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author Shuyang Zhou
 */
public class BaseModelSearchResult<T extends BaseModel<T>>
	implements Serializable {

	public static <T extends BaseModel<T>> BaseModelSearchResult<T>
		createWithStartAndEnd(
			Function<StartAndEnd, List<T>> getBaseModelsFunction, int length,
			int start, int end) {

		try {
			return unsafeCreateWithStartAndEnd(
				getBaseModelsFunction::apply, length, start, end);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	public static <T extends BaseModel<T>> BaseModelSearchResult<T>
			unsafeCreateWithStartAndEnd(
				UnsafeFunction<StartAndEnd, List<T>, PortalException>
					getBaseModelsUnsafeFunction,
				int length, int start, int end)
		throws PortalException {

		int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
			start, end, length);

		return new BaseModelSearchResult<>(
			getBaseModelsUnsafeFunction.apply(
				new StartAndEnd(startAndEnd[0], startAndEnd[1])),
			length);
	}

	public BaseModelSearchResult(List<T> baseModels, int length) {
		if (baseModels == null) {
			_baseModels = Collections.emptyList();
		}
		else {
			_baseModels = baseModels;
		}

		_length = length;
	}

	public BaseModelSearchResult(List<T> baseModels, Long length) {
		this(baseModels, length.intValue());
	}

	public List<T> getBaseModels() {
		return _baseModels;
	}

	public int getLength() {
		return _length;
	}

	@Override
	public String toString() {
		if (_baseModels.isEmpty()) {
			return StringBundler.concat(
				"{baseModels={}, length=", _length, StringPool.CLOSE_BRACKET);
		}

		StringBundler sb = new StringBundler((2 * _baseModels.size()) + 3);

		sb.append("{baseModels={");

		for (T baseModel : _baseModels) {
			sb.append(baseModel);
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setStringAt(StringPool.CLOSE_BRACKET, sb.index() - 1);

		sb.append(", length=");
		sb.append(_length);
		sb.append(StringPool.CLOSE_BRACKET);

		return sb.toString();
	}

	public static class StartAndEnd {

		public StartAndEnd(int start, int end) {
			_start = start;
			_end = end;
		}

		public int getEnd() {
			return _end;
		}

		public int getStart() {
			return _start;
		}

		private final int _end;
		private final int _start;

	}

	private final List<T> _baseModels;
	private final int _length;

}