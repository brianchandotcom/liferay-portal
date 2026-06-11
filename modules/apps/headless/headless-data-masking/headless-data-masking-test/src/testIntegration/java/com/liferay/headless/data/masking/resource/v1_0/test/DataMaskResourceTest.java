/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.data.masking.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.data.masking.client.dto.v1_0.DataMaskPreviewRequest;
import com.liferay.headless.data.masking.client.dto.v1_0.DataMaskPreviewResult;
import com.liferay.headless.data.masking.client.problem.Problem;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jose Luis Navarro
 */
@RunWith(Arquillian.class)
public class DataMaskResourceTest extends BaseDataMaskResourceTestCase {

	@Override
	@Test
	public void testPreviewDataMask() throws Exception {
		DataMaskPreviewRequest dataMaskPreviewRequest =
			new DataMaskPreviewRequest();

		dataMaskPreviewRequest.setDetectionRegex(_EMAIL_DETECTION_REGEX);
		dataMaskPreviewRequest.setReplacementValue("[EMAIL]");
		dataMaskPreviewRequest.setSampleText(
			"From alice@example.com to bob@example.org");

		DataMaskPreviewResult dataMaskPreviewResult =
			dataMaskResource.previewDataMask(dataMaskPreviewRequest);

		Assert.assertNull(dataMaskPreviewResult.getError());
		Assert.assertEquals(
			"From [EMAIL] to [EMAIL]", dataMaskPreviewResult.getOutput());
	}

	@Test
	public void testPreviewDataMaskWhenDetectionRegexIsInvalid()
		throws Exception {

		DataMaskPreviewRequest dataMaskPreviewRequest =
			new DataMaskPreviewRequest();

		dataMaskPreviewRequest.setDetectionRegex("[unclosed");
		dataMaskPreviewRequest.setReplacementValue("[REDACTED]");
		dataMaskPreviewRequest.setSampleText("anything");

		DataMaskPreviewResult dataMaskPreviewResult =
			dataMaskResource.previewDataMask(dataMaskPreviewRequest);

		Assert.assertNotNull(dataMaskPreviewResult.getError());
		Assert.assertEquals("anything", dataMaskPreviewResult.getOutput());
	}

	@Test(expected = Problem.ProblemException.class)
	public void testPreviewDataMaskWhenDetectionRegexIsMissing()
		throws Exception {

		DataMaskPreviewRequest dataMaskPreviewRequest =
			new DataMaskPreviewRequest();

		dataMaskPreviewRequest.setReplacementValue("[REDACTED]");
		dataMaskPreviewRequest.setSampleText("anything");

		dataMaskResource.previewDataMask(dataMaskPreviewRequest);
	}

	@Test
	public void testPreviewDataMaskWhenReplacementRegexHasCaptureGroups()
		throws Exception {

		DataMaskPreviewRequest dataMaskPreviewRequest =
			new DataMaskPreviewRequest();

		dataMaskPreviewRequest.setDetectionRegex(
			"\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b");
		dataMaskPreviewRequest.setReplacementRegex(
			"(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})\\.\\d{1,3}");
		dataMaskPreviewRequest.setReplacementValue("$1.0/24");
		dataMaskPreviewRequest.setSampleText("Connected from 192.168.1.42");

		DataMaskPreviewResult dataMaskPreviewResult =
			dataMaskResource.previewDataMask(dataMaskPreviewRequest);

		Assert.assertNull(dataMaskPreviewResult.getError());
		Assert.assertEquals(
			"Connected from 192.168.1.0/24", dataMaskPreviewResult.getOutput());
	}

	@Test
	public void testPreviewDataMaskWhenReplacementRegexIsInvalid()
		throws Exception {

		DataMaskPreviewRequest dataMaskPreviewRequest =
			new DataMaskPreviewRequest();

		dataMaskPreviewRequest.setDetectionRegex(_EMAIL_DETECTION_REGEX);
		dataMaskPreviewRequest.setReplacementRegex("[unclosed");
		dataMaskPreviewRequest.setReplacementValue("[REDACTED]");
		dataMaskPreviewRequest.setSampleText("alice@example.com");

		DataMaskPreviewResult dataMaskPreviewResult =
			dataMaskResource.previewDataMask(dataMaskPreviewRequest);

		Assert.assertNotNull(dataMaskPreviewResult.getError());
		Assert.assertEquals(
			"alice@example.com", dataMaskPreviewResult.getOutput());
	}

	@Test(expected = Problem.ProblemException.class)
	public void testPreviewDataMaskWhenReplacementValueIsMissing()
		throws Exception {

		DataMaskPreviewRequest dataMaskPreviewRequest =
			new DataMaskPreviewRequest();

		dataMaskPreviewRequest.setDetectionRegex(_EMAIL_DETECTION_REGEX);
		dataMaskPreviewRequest.setSampleText("anything");

		dataMaskResource.previewDataMask(dataMaskPreviewRequest);
	}

	@Test
	public void testPreviewDataMaskWhenSampleHasNoMatches() throws Exception {
		DataMaskPreviewRequest dataMaskPreviewRequest =
			new DataMaskPreviewRequest();

		dataMaskPreviewRequest.setDetectionRegex(_EMAIL_DETECTION_REGEX);
		dataMaskPreviewRequest.setReplacementValue("[EMAIL]");
		dataMaskPreviewRequest.setSampleText("No email here at all.");

		DataMaskPreviewResult dataMaskPreviewResult =
			dataMaskResource.previewDataMask(dataMaskPreviewRequest);

		Assert.assertNull(dataMaskPreviewResult.getError());
		Assert.assertEquals(
			"No email here at all.", dataMaskPreviewResult.getOutput());
	}

	@Test(expected = Problem.ProblemException.class)
	public void testPreviewDataMaskWhenSampleTextIsMissing() throws Exception {
		DataMaskPreviewRequest dataMaskPreviewRequest =
			new DataMaskPreviewRequest();

		dataMaskPreviewRequest.setDetectionRegex(_EMAIL_DETECTION_REGEX);
		dataMaskPreviewRequest.setReplacementValue("[EMAIL]");

		dataMaskResource.previewDataMask(dataMaskPreviewRequest);
	}

	private static final String _EMAIL_DETECTION_REGEX =
		"\\b[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}\\b";

}