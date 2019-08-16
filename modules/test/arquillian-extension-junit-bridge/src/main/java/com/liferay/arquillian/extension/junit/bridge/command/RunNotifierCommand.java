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
		Description description, AssumptionViolatedException ave) {

		return runNotifier -> runNotifier.fireTestAssumptionFailed(
			new Failure(description, ave));
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