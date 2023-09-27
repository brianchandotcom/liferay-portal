/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.asset.auto.tagger.tensorflow.internal.petra.process;

import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.util.InceptionImageLabelerUtil;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;

/**
 * @author Shuyang Zhou
 */
public class GetLabelProbabilitiesProcessCallable
	implements ProcessCallable<float[]> {

	public GetLabelProbabilitiesProcessCallable(
		byte[] imageBytes, String mimeType) {

		_imageBytes = imageBytes;
		_mimeType = mimeType;
	}

	@Override
	public float[] call() throws ProcessException {
		return InceptionImageLabelerUtil.getLabelProbabilities(
			_imageBytes, _mimeType);
	}

	private static final long serialVersionUID = 1L;

	private final byte[] _imageBytes;
	private final String _mimeType;

}