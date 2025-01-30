/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.scancode;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Brittney Nguyen
 */
public class AnalyzeDockerImagePipeline extends ScanCodePipeline {

	@Override
	public void execute() throws IOException, TimeoutException {
		invokeScan(getJSONObject());

		File tempDir = new File(System.getProperty("java.io.tmpdir"));

		File dockerConfigFile = new File(tempDir, "scancode-config.yml");

		try {
			JenkinsResultsParserUtil.write(
				dockerConfigFile,
				"ignored_patterns:\n - \'*opt/liferay/license/versions.html\'");

			addFileInput(dockerConfigFile.toString());

			startPipeline(_pipelineName);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		finally {
			if (dockerConfigFile != null) {
				dockerConfigFile.delete();
			}
		}

		waitForScan(_pipelineName);

		addAdditionalPipeline("match_to_matchcode");

		waitForScan("match_to_matchcode");

		checkComplianceAlerts("ERROR");

		checkComplianceAlerts("WARNING");

		downloadResultFiles();

		sendSlackNotification(getS3URL());
	}

	public JSONObject getJSONObject() throws IOException {
		Matcher matcher = _dockerTagPattern.matcher(_dockerTag);

		if (!matcher.find()) {
			throw new IllegalArgumentException(
				"Invalid Docker tag " + _dockerTag);
		}

		JSONObject jsonObject = new JSONObject();

		SimpleDateFormat simpleDateFormat = getSimpleDateFormat();

		jsonObject.put(
			"execute_now", false
		).put(
			"input_urls", "docker://liferay/" + _dockerTag
		).put(
			"labels",
			getLabels(
				"docker", matcher.group("buildProfile"),
				matcher.group("releaseVersion"))
		).put(
			"name",
			JenkinsResultsParserUtil.combine(
				_dockerTag, " Docker Scan-",
				simpleDateFormat.format(new Date()))
		).put(
			"pipeline", "analyze_docker_image"
		);

		return jsonObject;
	}

	protected AnalyzeDockerImagePipeline(String buildURL, String pipelineName) {
		super(buildURL, pipelineName);

		_pipelineName = pipelineName;

		_dockerTag = JenkinsResultsParserUtil.getBuildParameter(
			buildURL, "LIFERAY_DOCKER_TAG");
	}

	private static final Pattern _dockerTagPattern = Pattern.compile(
		"(?<buildProfile>portal|dxp):(?<releaseVersion>" +
			"\\d+.\\d+.\\d+[.\\d+]*-(ga|u)\\d+|\\d{4}.[qQ]\\d+.\\d+)");

	private final String _dockerTag;
	private final String _pipelineName;

}