/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.data.masking.internal.resource.v1_0;

import com.liferay.headless.data.masking.dto.v1_0.DataMaskPreviewRequest;
import com.liferay.headless.data.masking.dto.v1_0.DataMaskPreviewResult;
import com.liferay.headless.data.masking.internal.masking.DataMask;
import com.liferay.headless.data.masking.resource.v1_0.DataMaskResource;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jose Luis Navarro
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/data-mask.properties",
	scope = ServiceScope.PROTOTYPE, service = DataMaskResource.class
)
public class DataMaskResourceImpl extends BaseDataMaskResourceImpl {

	@Override
	public DataMaskPreviewResult previewDataMask(
			DataMaskPreviewRequest dataMaskPreviewRequest)
		throws Exception {

		String detectionRegex = dataMaskPreviewRequest.getDetectionRegex();
		String replacementValue = dataMaskPreviewRequest.getReplacementValue();
		String sampleText = dataMaskPreviewRequest.getSampleText();

		DataMaskPreviewResult dataMaskPreviewResult =
			new DataMaskPreviewResult();

		dataMaskPreviewResult.setOutput(() -> sampleText);

		if (Validator.isNull(detectionRegex) ||
			Validator.isNull(replacementValue) ||
			Validator.isNull(sampleText)) {

			dataMaskPreviewResult.setError(
				() ->
					"\"detectionRegex\", \"replacementValue\", and " +
						"\"sampleText\" are required");

			return dataMaskPreviewResult;
		}

		try {
			Pattern detectionPattern = Pattern.compile(detectionRegex);

			String replacementRegex =
				dataMaskPreviewRequest.getReplacementRegex();

			Pattern replacementPattern = null;

			if (Validator.isNotNull(replacementRegex)) {
				replacementPattern = Pattern.compile(replacementRegex);
			}

			DataMask dataMask = new DataMask(
				"preview", detectionPattern, replacementPattern,
				replacementValue);

			String finalOutput = dataMask.apply(sampleText);

			dataMaskPreviewResult.setOutput(() -> finalOutput);
		}
		catch (PatternSyntaxException patternSyntaxException) {
			dataMaskPreviewResult.setError(patternSyntaxException::getMessage);
		}
		catch (RuntimeException runtimeException) {
			dataMaskPreviewResult.setError(runtimeException::getMessage);
		}

		return dataMaskPreviewResult;
	}

}