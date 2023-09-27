/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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