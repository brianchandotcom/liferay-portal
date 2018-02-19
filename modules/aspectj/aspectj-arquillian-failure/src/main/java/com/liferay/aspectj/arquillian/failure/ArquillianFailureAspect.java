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

package com.liferay.aspectj.arquillian.failure;

import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.process.EchoOutputProcessor;
import com.liferay.petra.process.OutputProcessor;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.process.ProcessUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.SuppressAjWarnings;

/**
 * @author Preston Crary
 */
@Aspect
@SuppressAjWarnings("adviceDidNotMatch")
public class ArquillianFailureAspect {

	@AfterThrowing(
		throwing = "e1",
		value = "execution(void org.jboss.arquillian.junit.Arquillian.run(org.junit.runner.notification.RunNotifier))"
	)
	public void logTomcatJStack(Exception e1) {
		try {
			NoticeableFuture<Entry<List<String>, String>> jpsNoticeableFuture =
				ProcessUtil.execute(new StringOutputProcessor(), "jps", "-ml");

			Entry<List<String>, String> entry = jpsNoticeableFuture.get();

			String pid = null;

			for (String line : entry.getKey()) {
				if (line.endsWith(_TOMCAT_PROCESS_KEY)) {
					pid = line.substring(
						0, line.length() - _TOMCAT_PROCESS_KEY.length());

					break;
				}
			}

			List<String> lines = entry.getKey();

			StringBundler sb = new StringBundler(lines.size() * 2 + 3);

			sb.append("pids:\n");

			for (String line : lines) {
				sb.append(line);
				sb.append(StringPool.NEW_LINE);
			}

			sb.append("errors:\n");
			sb.append(entry.getValue());

			System.out.println(sb.toString());

			if (pid == null) {
				return;
			}

			System.out.println("jstack for pid: " + pid);

			NoticeableFuture<Entry<Void, Void>> jstackNoticeableFuture =
				ProcessUtil.execute(
					EchoOutputProcessor.INSTANCE, "jstack", "-l", pid);

			jstackNoticeableFuture.get();
		}
		catch (Exception e2) {
			e1.addSuppressed(e2);
		}
	}

	private static final String _TOMCAT_PROCESS_KEY =
		" org.apache.catalina.startup.Bootstrap start";

	private static class StringOutputProcessor
		implements OutputProcessor<List<String>, String> {

		@Override
		public String processStdErr(InputStream stdErrInputStream)
			throws ProcessException {

			try {
				return StringUtil.read(stdErrInputStream);
			}
			catch (IOException ioe) {
				throw new ProcessException(ioe);
			}
		}

		@Override
		public List<String> processStdOut(InputStream stdOutInputStream)
			throws ProcessException {

			List<String> lines = new ArrayList<>();

			try (Reader reader = new InputStreamReader(stdOutInputStream);
				UnsyncBufferedReader unsyncBufferedReader =
					new UnsyncBufferedReader(reader)) {

				String line = null;

				while ((line = unsyncBufferedReader.readLine()) != null) {
					lines.add(line);
				}
			}
			catch (IOException ioe) {
				throw new ProcessException(ioe);
			}

			return lines;
		}

	}

}