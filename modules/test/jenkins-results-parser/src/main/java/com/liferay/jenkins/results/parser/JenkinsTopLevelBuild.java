package com.liferay.jenkins.results.parser;

public class JenkinsTopLevelBuild extends DefaultTopLevelBuild {

	public JenkinsTopLevelBuild(String url) {
		super(url);
	}

	public JenkinsTopLevelBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

}
