/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.arquillian.extension.junit.bridge.command;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;

import org.junit.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

/**
 * @author Shuyang Zhou
 */
public interface RunNotifierCommand extends Serializable {

	public static RunNotifierCommand assumptionFailed(
		Description description,
		AssumptionViolatedException assumptionViolatedException) {

		return runNotifier -> runNotifier.fireTestAssumptionFailed(
			new Failure(description, assumptionViolatedException));
	}

	public static RunNotifierCommand testFailure(
		Description description, Throwable throwable) {

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		throwable.printStackTrace(new PrintStream(byteArrayOutputStream));

		Throwable stacklessThrowable = new Throwable(
			byteArrayOutputStream.toString()) {

			@Override
			public String toString() {
				return getMessage();
			}

		};

		stacklessThrowable.setStackTrace(new StackTraceElement[0]);

		return runNotifier -> runNotifier.fireTestFailure(
			new Failure(description, stacklessThrowable));
	}

	public static RunNotifierCommand testFinished(Description description) {
		return runNotifier -> runNotifier.fireTestFinished(description);
	}

	public static RunNotifierCommand testStarted(Description description) {
		return runNotifier -> runNotifier.fireTestStarted(description);
	}

	public void execute(RunNotifier runNotifier);

}