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

package com.liferay.portal.fabric.repository;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class RepositoryHelperUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructor() {
		new RepositoryHelperUtil();
	}

	@Test
	public void testGetRepositoryFilePath() {
		AtomicLong idGenerator = RepositoryHelperUtil.idGenerator;

		long currentId = idGenerator.get();

		Path repositoryFilePath = RepositoryHelperUtil.getRepositoryFilePath(
			Paths.get("repository"), Paths.get("parent/child/remoteFile"));

		Pattern pattern = Pattern.compile(
			"repository/remoteFile-\\d+-" + currentId);

		Matcher matcher = pattern.matcher(repositoryFilePath.toString());

		Assert.assertTrue(matcher.find());

		repositoryFilePath = RepositoryHelperUtil.getRepositoryFilePath(
			Paths.get("repository"), Paths.get("parent/child/remoteFile.xyz"));

		pattern = Pattern.compile(
			"repository/remoteFile-\\d+-" + (currentId + 1) + ".xyz");

		matcher = pattern.matcher(repositoryFilePath.toString());

		Assert.assertTrue(matcher.find());
	}

}