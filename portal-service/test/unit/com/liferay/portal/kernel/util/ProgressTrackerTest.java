/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.TestCase;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.mock.web.MockHttpSession;

/**
 * @author Sergio González
 */
public class ProgressTrackerTest extends TestCase {

	@Test
	public void testGetMessage() throws Exception {
		_mockInstallProcess.initialize();

		ProgressTracker progressTracker = getAttribute(ProgressTracker.PERCENT);

		Assert.assertEquals(StringPool.BLANK, progressTracker.getMessage());

		_mockInstallProcess.download();

		progressTracker = getAttribute(ProgressTracker.PERCENT);

		Assert.assertEquals("downloading", progressTracker.getMessage());
	}

	@Test
	public void testGetPercent() throws Exception {
		_mockInstallProcess.initialize();

		ProgressTracker progressTracker = getAttribute(ProgressTracker.PERCENT);

		Assert.assertEquals(0, progressTracker.getPercent());

		_mockInstallProcess.download();
		_mockInstallProcess.copy();

		progressTracker = getAttribute(ProgressTracker.PERCENT);

		Assert.assertEquals(progressTracker.getPercent(), 50);
	}

	@Test
	public void testGetStatus() throws Exception {
		_mockInstallProcess.initialize();

		ProgressTracker progressTracker = getAttribute(ProgressTracker.PERCENT);

		_mockInstallProcess.download();
		_mockInstallProcess.copy();

		Assert.assertEquals(
			ProgressStatusConstants.COPYING, progressTracker.getStatus());
	}

	@Test
	public void testInitializeAndFinish() throws Exception {
		_mockInstallProcess.initialize();

		ProgressTracker progressTracker = getAttribute(ProgressTracker.PERCENT);

		Assert.assertNotNull(progressTracker);

		_mockInstallProcess.finish();

		progressTracker = getAttribute(ProgressTracker.PERCENT);

		Assert.assertNull(progressTracker);
	}

	@Test
	public void testInitialStatus() throws Exception {
		_mockInstallProcess.initialize();

		ProgressTracker progressTracker = getAttribute(ProgressTracker.PERCENT);

		Assert.assertEquals(
			ProgressStatusConstants.PREPARED, progressTracker.getStatus());
		Assert.assertEquals(StringPool.BLANK, progressTracker.getMessage());
		Assert.assertEquals(0, progressTracker.getPercent());
	}

	protected ProgressTracker getAttribute(String status) {
		ProgressTracker progressTracker =
			(ProgressTracker)_mockHttpSession.getAttribute(
				status + ProgressTrackerTest.class.getName());

		return progressTracker;
	}

	@Override
	protected void setUp() throws Exception {
		_mockHttpSession = new MockHttpSession();

		_mockInstallProcess = new MockInstallProcess(_mockHttpSession);
	}

	@Override
	protected void tearDown() throws Exception {
		_mockInstallProcess.finish();
	}

	private MockHttpSession _mockHttpSession;
	private MockInstallProcess _mockInstallProcess;

	private class MockInstallProcess {

		public MockInstallProcess(MockHttpSession mockHttpSession) {
			ProgressTracker progressTracker = new ProgressTracker(
				mockHttpSession, ProgressTrackerTest.class.getName());

			progressTracker.addProgress(
				ProgressStatusConstants.DOWNLOADING, 25, "downloading");
			progressTracker.addProgress(
				ProgressStatusConstants.COPYING, 50, "copying");

			_progressTracker = progressTracker;
		}

		public void copy() {
			_progressTracker.setStatus(ProgressStatusConstants.COPYING);
		}

		public void download() {
			_progressTracker.setStatus(ProgressStatusConstants.DOWNLOADING);
		}

		public void finish() {
			_progressTracker.finish();
		}

		public void initialize() {
			_progressTracker.initialize();
		}

		private ProgressTracker _progressTracker;

	}

}