package com.liferay.jenkins.results.parser.test;

import java.io.File;

import org.apache.tools.ant.Project;

public interface ResultsParserTest {
	
	abstract Project getProject();
	
	abstract void createExpectedResultsFile(Project project, File testRoot) throws Exception;

	
	public static final String _expectedResultsFilePath =
			"expected-results/FailureMessageGeneratorTest.html";
	
}
