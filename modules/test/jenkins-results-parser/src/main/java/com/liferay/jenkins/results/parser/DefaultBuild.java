package com.liferay.jenkins.results.parser;

import java.net.URL;

import org.dom4j.Element;

public class DefaultBuild extends BaseBuild {

	public DefaultBuild(String url) {
		super(url);

		getDuration();
	}

	@Override
	public URL getArtifactsBaseURL() {
		return null;
	}

	@Override
	public void addTimelineData(TimelineData timelineData) {
	}

	@Override
	protected Element getGitHubMessageJobResultsElement() {
		return null;
	}

}
