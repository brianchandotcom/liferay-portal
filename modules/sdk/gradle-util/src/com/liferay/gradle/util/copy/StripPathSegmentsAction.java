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

package com.liferay.gradle.util.copy;

import java.util.Arrays;

import org.gradle.api.Action;
import org.gradle.api.file.FileCopyDetails;
import org.gradle.api.file.RelativePath;

/**
 * @author Andrea Di Giorgi
 */
public class StripPathSegmentsAction implements Action<FileCopyDetails> {

	public StripPathSegmentsAction(int from) {
		this(from, Integer.MAX_VALUE);
	}

	public StripPathSegmentsAction(int from, int to) {
		_from = from;
		_to = to;
	}

	@Override
	public void execute(FileCopyDetails fileCopyDetails) {
		RelativePath relativePath = fileCopyDetails.getRelativePath();

		String[] segments = relativePath.getSegments();

		segments = Arrays.copyOfRange(
			segments, _from, Math.min(_to, segments.length));

		fileCopyDetails.setRelativePath(new RelativePath(true, segments));
	}

	private final int _from;
	private final int _to;

}