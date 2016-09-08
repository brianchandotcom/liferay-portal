package com.liferay.jenkins.results.parser;

public class BuildFactory {

	public static Build newBuild(String url, Build parent) throws Exception {
		url = JenkinsResultsParserUtil.getLocalURL(url);

		if (url.contains("AXIS_VARIABLE=")) {
			return new AxisBuild(url, (BatchBuild)parent);
		}
		
		if (url.contains("-source")) {
			return new SourceBuild(url, (TopLevelBuild) parent);
		}

		for (String batchIndicator : _batchIndicators) {
			if (url.contains(batchIndicator)) {
				return new BatchBuild(url, (TopLevelBuild)parent);
			}
		}

		return new TopLevelBuild(url,(TopLevelBuild)parent);
	}

	private static final String[] _batchIndicators = {"-batch", "-dist"};
}
