package com.liferay.jenkins.results.parser;

public class JenkinsPerformanceDataProcessor {
	public String getLocalURL(String remoteURL) {
		Pattern pattern = Pattern.compile("https://test.liferay.com/([0-9]+)/");

		Matcher matcher = pattern.matcher(remoteURL);

		if (matcher.find()) {
			StringBuilder sb = new StringBuilder();

			sb.append("http://test-");
			sb.append(matcher.group(1));
			sb.append("/");
			sb.append(matcher.group(1));
			sb.append("/");

			return remoteURL.replaceAll(matcher.group(0), sb.toString());
		}

		pattern = Pattern.compile("https://(test-[0-9]+-[0-9]+).liferay.com/");

		matcher = pattern.matcher(remoteURL);

		if (matcher.find()) {
			StringBuilder sb = new StringBuilder();

			sb.append("http://");
			sb.append(matcher.group(1));
			sb.append("/");

			return remoteURL.replaceAll(matcher.group(0), sb.toString());
		}

		return remoteURL;
	}

	public List getLongestResults(JSONObject jobJSONObject, int resultCount) {
		JSONArray childReportsJSONArray = jobJSONObject.getJSONArray("childReports");
		List resultList = new ArrayList();

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

					Result result = new Result(caseJSONObject, childJSONObject);

					resultList.add(result);
				}
			}
		}

		Collections.sort(resultList);

		return truncateList(resultList, resultCount);
	}

	public String toString(String url) {
		URI uri = new URI(url);

		URL urlObject = uri.toURL();

		InputStreamReader inputStreamReader = new InputStreamReader(urlObject.openStream());

		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String line = null;

		StringBuilder sb = new StringBuilder();

		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}

		bufferedReader.close();

		return sb.toString();
	}

	public JSONObject toJSONObject(String url) {
		String localURL = getLocalURL(url);

		try {
			return new JSONObject(toString(localURL));
		}
		catch (IOException e) {
			System.out.println("IOException while reading url: " + localURL);
		}

		return null;
	}

	public List truncateList(List list, int maxSize) {
		List truncatedList = null;

		if (list != null) {
			int size = maxSize;

			if (size > list.size()) {
				size = list.size();
			}

			truncatedList = new ArrayList(list.subList(0, size));
		}
		else {
			truncatedList = new ArrayList(0);
		}

		return truncatedList;
	}
	
	public void processPerformanceData() {
		if (beanShellMap.get("start-time") == null) {
			Long start = System.currentTimeMillis();

			beanShellMap.put("start-time", start);
		}

		String jenkinsJobURL = project.getProperty("jenkins.job.url");
		String reportSizeString = project.getProperty("report.size");

		int reportSize = Integer.parseInt(reportSizeString);

		JSONObject jobJSONObject = toJSONObject(jenkinsJobURL + "testReport/api/json");

		if (jobJSONObject != null) {
			List resultList = getLongestResults(jobJSONObject, reportSize);

			List globalList = beanShellMap.get("global-result-list");

			if (globalList == null) {
				globalList = new ArrayList();
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
