package com.liferay.jenkins.results.parser;

public class FailedJobsMessageUtil {

	public static void getFailedJobsMessage(Project project) {
		StringBuilder sb = new StringBuilder();

		String buildURL = project.getProperty("build.url");

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(buildURL + "api/json"));

		String result = jsonObject.getString("result");

		File javacOutputFile =
			new File(project.getProperty("top.level.shared.dir")
				+ "/javac.output.txt");

		if (result.equals("ABORTED")) {
			sb.append("<pre>Build was aborted</pre>");
		}
		else if (result.equals("FAILURE")) {
			if (jsonObject.has("runs")) {
				JSONArray runsJSONArray = jsonObject.getJSONArray("runs");

				List runBuildURLs = new ArrayList();
				List failureBuildURLs = new ArrayList();

				for (int i = 0; i < runsJSONArray.length(); i++) {
					JSONObject runsJSONObject = runsJSONArray.get(i);

					String runBuildURL = runsJSONObject.get("url");

					if (runBuildURL.endsWith(
							"/"+ jsonObject.get("number") + "/")) {

						JSONObject runJSONObject =
							JenkinsResultsParserUtil.toJSONObject(
								JenkinsResultsParserUtil.getLocalURL(
									runBuildURL + "api/json"));

						String runResult = runJSONObject.getString("result");

						if (!runResult.equals("SUCCESS")) {
							failureBuildURLs.add(runBuildURL);
						}

						runBuildURLs.add(runBuildURL);
					}
				}

				sb.append("<h6>Job Results:</h6>");
				sb.append("<p>");
				sb.append(runBuildURLs.size());
				sb.append(" Test");

				if (runBuildURLs.size() != 1) {
					sb.append("s");
				}

				sb.append(" Passed.<br />");
				sb.append(failureBuildURLs.size());
				sb.append(" Test");

				if (failureBuildURLs.size() != 1) {
					sb.append("s");
				}

				sb.append(" Failed.</p>");

				sb.append("<ol>");

				for (int i = 0; i < failureBuildURLs.size(); i++) {
					String failureBuildURL = failureBuildURLs.get(i);

					JSONObject failureJSONObject =
						JenkinsResultsParserUtil.toJSONObject(
							JenkinsResultsParserUtil.getLocalURL(
								failureBuildURL + "api/json"));

					sb.append("<li><strong><a href=\\\"");
					sb.append(failureBuildURL);
					sb.append("\\\">");
					sb.append(failureJSONObject.get("fullDisplayName"));
					sb.append("</a></strong>");
					sb.append(
						FailureMessageUtil.getFailureMessage(
							project, failureBuildURL));
					sb.append("</li>");

					if (i >= 2) {
						break;
					}
				}

				sb.append("</ol>");
			}
			else {
				sb.append(
					FailureMessageUtil.getFailureMessage(project, buildURL));
			}
		}
		else if (result.equals("UNSTABLE")) {
			sb.append(UnstableMessageUtil.getUnstableMessage(buildURL));
		}
		else if (javacOutputFile.exists()) {
			sb.append("<h6>Job Results:</h6>");
			sb.append("<p>0 Tests Passed.<br />1 Test Failed.</p>");
			sb.append("<pre>");

			String javacOutputFileContent =
				FileUtils.readFileToString(javacOutputFile);

			if (javacOutputFileContent.length() > 5000) {
				javacOutputFileContent = javacOutputFileContent.substring(
					javacOutputFileContent.length() - 5000);
			}

			sb.append(JenkinsResultsParserUtil.fixJSON(javacOutputFileContent));
			sb.append("</pre>");
		}

		project.setProperty("report.html.content", sb.toString());		
	}
}
