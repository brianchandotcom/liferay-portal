package com.liferay.jenkins.results.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.tools.ant.Project;
import org.json.JSONArray;
import org.json.JSONObject;

public class JenkinsPerformanceDataProcessor {

	public List<Result> getLongestResults(JSONObject jobJSONObject, Project project, int resultCount) throws Exception {
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

		return truncateList(resultList, resultCount);
	}

	public List<Result> truncateList(List<Result> list, int maxSize) {
		List<Result> truncatedList = null;

		if (list != null) {
			int size = maxSize;

			if (size > list.size()) {
				size = list.size();
			}

			truncatedList = new ArrayList<>(list.subList(0, size));
		}
		else {
			truncatedList = new ArrayList<>(0);
		}

		return truncatedList;
	}
	
	public void processPerformanceData(Project project) {
		if (beanShellMap.get("start-time") == null) {
			Long start = System.currentTimeMillis();

			beanShellMap.put("start-time", start);
		}

		String jenkinsJobURL = project.getProperty("jenkins.job.url");
		String reportSizeString = project.getProperty("report.size");

		int reportSize = Integer.parseInt(reportSizeString);

		JSONObject jobJSONObject = JenkinsResultsParserUtil.toJSONObject(JenkinsResultsParserUtil.getLocalURL(jenkinsJobURL + "testReport/api/json"));

		if (jobJSONObject != null) {
			List<Result> resultList = getLongestResults(jobJSONObject, project, reportSize);

			List<Result> globalList = beanShellMap.get("global-result-list");

			if (globalList == null) {
				globalList = new ArrayList<>();
			}

			globalList.addAll(resultList);

			Collections.sort(globalList);

			globalList = truncateList(globalList, reportSize);

			beanShellMap.put("global-result-list", globalList);
		}
		else {
			System.out.println("JSON data could not be loaded. URL: " + jenkinsJobURL + "testReport/api/json");
		}
	}
}
