/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.scancode;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.NotificationUtil;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Brittney Nguyen
 */
public class ScanCodeProject {

	public ScanCodeProject(String buildURL, String pipelineName) {
		_buildURL = buildURL;

		_labels.add("automated");

		_pipelineName = pipelineName;

		_s3URL = null;
	}

	public void addPipeline(String pipelineName)
		throws IOException, TimeoutException {

		StringBuilder sb = new StringBuilder();

		sb.append("curl ");
		sb.append(_projectApiURL);
		sb.append("add_pipeline/");
		sb.append(" --data ");

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"execute_now", true
		).put(
			"pipeline", pipelineName
		);

		sb.append("'");
		sb.append(jsonObject);
		sb.append("'");
		sb.append(" --header ");
		sb.append(_CONTENT_TYPE);
		sb.append(" --header ");
		sb.append("\"Authorization:Token " + _API_KEY + '\"');
		sb.append(" --request POST ");

		Process process = JenkinsResultsParserUtil.executeBashCommands(
			sb.toString());

		try {
			JenkinsResultsParserUtil.readInputStream(process.getInputStream());
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public void downloadResultFiles() throws IOException {
		String scanCodeResultsDir = JenkinsResultsParserUtil.getBuildProperty(
			"scancode.results.dir");

		for (String extension : _RESULT_FILES_EXTENSIONS) {
			String link = JenkinsResultsParserUtil.combine(
				_projectURL, "results/", extension);

			URL url = new URL(link);

			File file = new File(
				JenkinsResultsParserUtil.combine(
					scanCodeResultsDir, _projectNameFromURL, ".", extension));

			JenkinsResultsParserUtil.toFile(url, file);
		}

		String tarGzName = _projectNameFromURL + ".tar.gz";

		File resultsTarGzFile = new File(scanCodeResultsDir, tarGzName);

		JenkinsResultsParserUtil.tarGzip(
			new File(scanCodeResultsDir), resultsTarGzFile);

		uploadResultsToBucket(resultsTarGzFile.toString());
	}

	public JSONObject getAnalyzeDockerImageJSONObject(String dockerTag) {
		JSONObject jsonObject = new JSONObject();

		_labels.add("docker");
		_labels.add(dockerTag);

		jsonObject.put(
			"execute_now", true
		).put(
			"input_urls", "docker://liferay/" + dockerTag
		).put(
			"labels", _labels
		).put(
			"name",
			JenkinsResultsParserUtil.combine(
				dockerTag, " Docker Scan-",
				_simpleDateFormat.format(new Date()))
		).put(
			"pipeline", _pipelineName
		);

		return jsonObject;
	}

	public JSONObject getInspectPackagesJSONObject() {
		JSONObject jsonObject = new JSONObject();

		_labels.add("master");

		jsonObject.put(
			"execute_now", true
		).put(
			"input_urls",
			"https://github.com/liferay/liferay-portal/archive/refs/heads" +
				"/master.tar.gz"
		).put(
			"labels", _labels
		).put(
			"name", "Master Daily Scan-" + _simpleDateFormat.format(new Date())
		).put(
			"pipeline", _pipelineName
		);

		return jsonObject;
	}

	public JSONObject getMapDevelopToDeployJSONObject() throws IOException {
		JSONObject jsonObject = new JSONObject();

		List<String> inputURLS = new ArrayList<>();

		String tomcatURL = JenkinsResultsParserUtil.getBuildParameter(
			_buildURL, "TEST_PORTAL_RELEASE_TOMCAT_URL");

		inputURLS.add(tomcatURL + "#to");

		inputURLS.add(getReleaseTarballLink());
		inputURLS.add(
			JenkinsResultsParserUtil.getBuildProperty("scancode.tar.gz.url"));
		inputURLS.add(
			JenkinsResultsParserUtil.getBuildProperty(
				"scancode.config.file.url"));

		String portalReleaseVersion =
			JenkinsResultsParserUtil.getBuildParameter(
				_buildURL, "TEST_PORTAL_RELEASE_VERSION");

		_labels.add(portalReleaseVersion);

		jsonObject.put(
			"execute_now", true
		).put(
			"input_urls", inputURLS
		).put(
			"labels", _labels
		).put(
			"name",
			JenkinsResultsParserUtil.combine(
				portalReleaseVersion, " Scan-",
				_simpleDateFormat.format(new Date()))
		).put(
			"pipeline", _pipelineName
		);

		return jsonObject;
	}

	public String getPipelineName() {
		return _pipelineName;
	}

	public String getProjectID() {
		return _projectID;
	}

	public String getReleaseTarballLink() {
		String portalBranchUsername =
			JenkinsResultsParserUtil.getBuildParameter(
				_buildURL, "TEST_PORTAL_USER_NAME");

		String portalSHA = JenkinsResultsParserUtil.getBuildParameter(
			_buildURL, "TEST_PORTAL_RELEASE_GIT_ID");

		StringBuilder sb = new StringBuilder();

		sb.append("https://github.com/");
		sb.append(portalBranchUsername);
		sb.append("/liferay-portal-ee/archive/");
		sb.append(portalSHA);
		sb.append(".tar.gz");
		sb.append("#from");

		return sb.toString();
	}

	public String getS3URL() {
		return _s3URL;
	}

	public void invokeScan() throws IOException, TimeoutException {
		StringBuilder sb = new StringBuilder();

		sb.append("curl ");
		sb.append(_API_URL);

		JSONObject jsonObject = null;

		if (_pipelineName.equals("inspect_packages")) {
			jsonObject = getInspectPackagesJSONObject();
		}
		else if (_pipelineName.equals("analyze_docker_image")) {
			String dockerTag = JenkinsResultsParserUtil.getBuildParameter(
				_buildURL, "LIFERAY_DOCKER_TAG");

			jsonObject = getAnalyzeDockerImageJSONObject(dockerTag);
		}
		else if (_pipelineName.equals("map_deploy_to_develop")) {
			jsonObject = getMapDevelopToDeployJSONObject();
		}

		sb.append(" --data ");
		sb.append("'");
		sb.append(jsonObject);
		sb.append("'");
		sb.append(" --header ");
		sb.append(_CONTENT_TYPE);
		sb.append(" --header ");
		sb.append("\"Authorization:Token " + _API_KEY + "\"");
		sb.append(" --request POST ");

		Process process = JenkinsResultsParserUtil.executeBashCommands(
			sb.toString());

		String output = null;

		try {
			output = JenkinsResultsParserUtil.readInputStream(
				process.getInputStream());

			output = output.trim();

			JSONObject outputJSONObject = new JSONObject(output);

			_projectApiURL = outputJSONObject.getString("url");
			_projectID = outputJSONObject.getString("uuid");
			_projectName = outputJSONObject.getString("name");
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public void sendSlackNotification(String s3URL) {
		StringBuilder sb = new StringBuilder();

		sb.append("*Project link:* ");
		sb.append("<");
		sb.append(_projectURL);
		sb.append("|");
		sb.append(_projectName);
		sb.append(">\n");
		sb.append("*Pipeline:* ");

		if (_pipelineName.equals("inspect_packages")) {
			sb.append(_pipelineName);
			sb.append(", populate_purldb");
		}
		else {
			sb.append(_pipelineName);
		}

		sb.append("\n");
		sb.append("*Status:* ");
		sb.append(
			_projectStatuses.toString(
			).replaceAll(
				"(^\\[|\\]$)", ""
			));

		if (_s3URL != null) {
			sb.append("\n*S3 Tar.gz:* ");
			sb.append("<");
			sb.append(_s3URL);
			sb.append("|");
			sb.append(_projectNameFromURL + ".tar.gz");
			sb.append(">");
		}

		NotificationUtil.sendSlackNotification(
			sb.toString(), "#scancode-io", ":liferay-ci:",
			"ScanCode pipeline is complete", "Liferay CI");
	}

	public void setProjectURL(String uid, String name) {
		name = name.replaceAll(
			"[.:]", ""
		).toLowerCase();

		name = name.replace(" ", "-");

		uid = uid.substring(0, uid.indexOf("-"));

		_projectNameFromURL = name + "-" + uid;

		_projectURL =
			"https://liferay1.scancode.io/project/" + name + "-" + uid + "/";
	}

	public void uploadResultsToBucket(String tarGzFilePath) {
		File tarGzFile = new File(tarGzFilePath);

		try {
			ScanCodeS3Bucket scanCodeS3Bucket = ScanCodeS3Bucket.getInstance();

			scanCodeS3Bucket.createScanCodeS3Object(
				"inbox/" + tarGzFile.getName(), tarGzFile);

			_s3URL = scanCodeS3Bucket.getS3URL();
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void waitForScan(String pipelineName) {
		StringBuilder sb = new StringBuilder();

		sb.append("curl ");
		sb.append(_projectApiURL);
		sb.append(" --header ");
		sb.append(_CONTENT_TYPE);
		sb.append(" --header ");
		sb.append("\"Authorization:Token " + _API_KEY + '\"');
		sb.append(" --request GET ");

		while (true) {
			try {
				Process process = JenkinsResultsParserUtil.executeBashCommands(
					new String[] {sb.toString()});

				String output = JenkinsResultsParserUtil.readInputStream(
					process.getInputStream());

				output = output.trim();

				JSONObject outputJSONObject = new JSONObject(output);

				JSONArray jsonArray = outputJSONObject.getJSONArray("runs");

				Object run = jsonArray.get(0);

				if (pipelineName.equals("populate_purldb")) {
					run = jsonArray.get(1);
				}

				JSONObject runJSONObject = new JSONObject(run.toString());

				String projectStatus = runJSONObject.getString("status");

				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Project status for ", pipelineName, ": ",
						projectStatus));

				if (!projectStatus.equals("running") &&
					!projectStatus.equals("queued")) {

					_projectStatuses.add(projectStatus);

					break;
				}

				Thread.sleep(10 * 60 * 1000);
			}
			catch (Exception exception) {
				exception.printStackTrace();
			}
		}

		setProjectURL(_projectID, _projectName);
	}

	private static final String _API_KEY;

	private static final String _API_URL =
		"https://liferay1.scancode.io/api/projects/";

	private static final String _CONTENT_TYPE =
		"'Content-Type: application/json;'";

	private static final String[] _RESULT_FILES_EXTENSIONS = {
		"attribution", "cyclonedx", "spdx", "xls"
	};

	static {
		try {
			_API_KEY = JenkinsResultsParserUtil.getBuildProperty(
				"scancode.api.key");
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get Scancode api key", ioException);
		}
	}

	private final String _buildURL;
	private final List<String> _labels = new ArrayList<>();
	private final String _pipelineName;
	private String _projectApiURL;
	private String _projectID;
	private String _projectName;
	private String _projectNameFromURL;
	private final List<String> _projectStatuses = new ArrayList<>();
	private String _projectURL;
	private String _s3URL;
	private final SimpleDateFormat _simpleDateFormat = new SimpleDateFormat(
		"MMM d yy HH:mm:ss");

}