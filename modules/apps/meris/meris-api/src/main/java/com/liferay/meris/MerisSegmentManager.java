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

package com.liferay.meris;

import aQute.bnd.annotation.ProviderType;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Models a service to retrieve segments.
 *
 * @author Eduardo Garcia
 */
@ProviderType
public interface MerisSegmentManager<T extends MerisSegment> {

	public T getMerisSegment(String merisSegmentId);

	public List<T> getMerisSegments(
		long groupId, int start, int end, Comparator<T> comparator);

	public List<T> getMerisSegments(
		long groupId, long userId, String merisSegmentId,
		Map<String, Object> context, int start, int end,
		Comparator<T> comparator);

	public boolean matches(
		long userId, String merisSegmentId, Map<String, Object> context);

}