/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.remote.cors.internal.path.pattern.matcher;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;

/**
 * @author Arthur Chan
 */
public abstract class BasePathPatternMatcherTestCase {

	public void setUp() throws Exception {
		int iteration = 0;

		for (String pattern : PATTERNS) {
			matcher.insert(
				pattern,
				HashMapBuilder.put(
					pattern, "cargo" + iteration++
				).build());
		}

		buildTestResults();
	}

	public void testAllMatches() throws Exception {
		for (String path : PATHS) {
			Set<String> allMatches = allMatchResults.get(path);

			List<PatternTuple<Map<String, String>>> patternPackages =
				matcher.getPatternTuples(path);

			if (patternPackages.isEmpty()) {
				Assert.assertTrue(allMatches == null);

				continue;
			}

			if (allMatches == null) {
				Assert.assertTrue(patternPackages.isEmpty());

				continue;
			}

			Assert.assertEquals(
				path, allMatches.size(), patternPackages.size());

			for (PatternTuple<Map<String, String>> patternPackage :
					patternPackages) {

				Assert.assertTrue(
					allMatches.remove(patternPackage.getPattern()));
			}

			Assert.assertTrue(allMatches.isEmpty());
		}
	}

	public void testBestMatch() throws Exception {
		for (String path : PATHS) {
			String bestMatch = bestMatchResults.get(path);

			PatternTuple<Map<String, String>> patternPackage =
				matcher.getPatternTuple(path);

			if (patternPackage == null) {
				Assert.assertTrue(bestMatch == null);

				continue;
			}

			String found = patternPackage.getPattern();

			Assert.assertEquals(bestMatch, found);
		}
	}

	protected void addResult(
		String path, Map<String, String> bestMatches,
		Map<String, Set<String>> allMatches, int... patternIndexs) {

		if (patternIndexs.length > 0) {
			bestMatches.put(path, PATTERNS[patternIndexs[0]]);

			Set<String> allPatterns = new HashSet<>();

			for (int i : patternIndexs) {
				allPatterns.add(PATTERNS[i]);
			}

			allMatches.put(path, allPatterns);
		}
	}

	protected void buildTestResults() {
		bestMatchResults = new HashMap<>();
		allMatchResults = new HashMap<>();

		for (int i = 0; i < PATHS.length; ++i) {
			addResult(
				PATHS[i], bestMatchResults, allMatchResults,
				MATCHED_PATTERNS[i]);
		}
	}

	protected static final int[][] MATCHED_PATTERNS = {
		{12, 11}, {13, 11}, {14, 13, 11}, {15, 11}, {15, 11}, {16, 15, 11},
		{16, 15, 11}, {17, 15, 11}, {17, 15, 11}, {15, 11}, {15, 11},
		{18, 16, 15, 11}, {18, 16, 15, 11}, {19, 16, 15, 11}, {19, 16, 15, 11},
		{20, 17, 15, 11}, {20, 17, 15, 11}, {21, 17, 15, 11}, {21, 17, 15, 11},
		{15, 11}, {15, 11}, {15, 11}, {15, 11}, {22, 15, 11, 24}, {22, 15, 11},
		{23, 15, 11, 25}, {23, 15, 11}, {11}, {11}, {24}, {}, {25}, {}, {}, {},
		{6}, {}, {}, {}, {1, 26, 11}
	};

	protected static final String[] PATHS = {
		"/", "/*", "/*/", "/documents", "/documents/", "/documents/user1",
		"/documents/user1/", "/documents/user2", "/documents/user2/",
		"/documents/user3", "/documents/user3/", "/documents/user1/folder1",
		"/documents/user1/folder1/", "/documents/user1/folder2",
		"/documents/user1/folder2/", "/documents/user2/folder1",
		"/documents/user2/folder1/", "/documents/user2/folder2",
		"/documents/user2/folder2/", "/documents/user3/folder1",
		"/documents/user3/folder1/", "/documents/user3/folder2",
		"/documents/user3/folder2/", "/documents/main.jspf",
		"/documents/main.jspf/", "/documents/main.jsp", "/documents/main.jsp/*",
		"/test", "/test/", "test/main.jspf", "test.jspf/", "test.jsp",
		"test/main.jsp/*", "test", "test/", "no/leading/slash/*",
		"no/leading/slash/test", "no/leading/slash", "no/leading/slash/",
		"/c/portal/j_login"
	};

	protected static final String[] PATTERNS = {
		"/c/portal/j_login*", "/c/portal/j_login", "/c/portal/j_login*/",
		"/c/*/j_login", "/**", "/**/", "no/leading/slash/*",
		"no/leading/slash/*/", "no/leading/slash/*/*", "no/leading/slash/*//*",
		"no/leading/slash//*", "/*", "//*", "/*/*", "/*//*", "/documents/*",
		"/documents/user1/*", "/documents/user2/*",
		"/documents/user1/folder1/*", "/documents/user1/folder2/*",
		"/documents/user2/folder1/*", "/documents/user2/folder2/*",
		"/documents/main.jspf/*", "/documents/main.jsp/*", "*.jspf", "*.jsp",
		"/c/portal/j_login/*"
	};

	protected Map<String, Set<String>> allMatchResults;
	protected Map<String, String> bestMatchResults;
	protected PathPatternMatcher<Map<String, String>> matcher;

}