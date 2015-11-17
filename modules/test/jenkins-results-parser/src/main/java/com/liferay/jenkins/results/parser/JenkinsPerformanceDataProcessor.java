package com.liferay.jenkins.results.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.tools.ant.Project;
import org.json.JSONArray;
import org.json.JSONObject;

public class JenkinsPerformanceDataProcessor {

	protected static List<Result> getLongestResults(JSONObject jobJSONObject, Project project, int resultCount) throws Exception {
		JSONArray childReportsJSONArray = jobJSONObject.getJSONArray("childReports");
		List<Result> resultList = new ArrayList<>();

		for (int i=0; i < childReportsJSONArray.length(); i++) {
			JSONObject childReportJSONObject = childReportsJSONArray.getJSONObject(i);

			JSONObject childJSONObject = childReportJSONObject.getJSONObject("child");

			JSONObject resultJSONObject = childReportJSONObject.getJSONObject("result");

			JSONArray suitesJSONArray = resultJSONObject.getJSONArray("suites");

			for (int j=0; j < suitesJSONArray.length(); j++) {
				JSONObject suiteJSONObject = suitesJSONArray.getJSONObject(j);

				JSONArray casesJSONArray = suiteJSONObject.getJSONArray("cases");

				for (int k=0; k < casesJSONArray.length(); k++) {
					JSONObject caseJSONObject = casesJSONArray.getJSONObject(k);

					Result result = new Result(project.getProperty("jenkins.build.name"), caseJSONObject, childJSONObject);

					resultList.add(result);
				}
			}
		}

		Collections.sort(resultList);
		
		truncateList(resultList, resultCount);

		return resultList;
	}

	protected static void truncateList(List<Result> list, int maxSize) {
		while (list.size() > maxSize) {
			list.remove(list.size() - 1);
		}
	}
	
	public static void processPerformanceData(Project project) throws Exception {

		String jenkinsJobURL = project.getProperty("jenkins.job.url");
		String reportSizeString = project.getProperty("report.size");

		int reportSize = Integer.parseInt(reportSizeString);

		JSONObject jobJSONObject = JenkinsResultsParserUtil.toJSONObject(JenkinsResultsParserUtil.getLocalURL(jenkinsJobURL + "testReport/api/json"));

		if (jobJSONObject != null) {
			List<Result> resultList = getLongestResults(jobJSONObject, project, reportSize);

			_globalList.addAll(resultList);

			Collections.sort(_globalList);

			truncateList(_globalList, reportSize);
		}
		else {
			System.out.println("JSON data could not be loaded. URL: " + jenkinsJobURL + "testReport/api/json");
		}
	}
	
	protected static final List<Result> _globalList = new ArrayList<>();
}
