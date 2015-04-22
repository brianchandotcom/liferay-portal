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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Hugo Huijser
 */
public class SourceFormatter {

	public static void main(String[] args) {
		try {
			SourceFormatter sourceFormatter = SourceFormatterUtil.create(
				false, false, true, true);

			sourceFormatter.format();
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public SourceFormatter(
			boolean useProperties, boolean throwException, boolean printErrors,
			boolean autoFix)
		throws Exception {

		_useProperties = useProperties;
		_throwException = throwException;
		_printErrors = printErrors;
		_autoFix = autoFix;
	}

	public void format() throws Throwable {
		final AtomicReference<Throwable> exceptionReference1 =
			new AtomicReference<Throwable>();

		Thread thread1 = new Thread () {

			@Override
			public void run() {
				try {
					List<SourceProcessor> sourceProcessors =
						new ArrayList<SourceProcessor>();

					sourceProcessors.add(
						CSSSourceProcessor.class.newInstance());
					sourceProcessors.add(
						FTLSourceProcessor.class.newInstance());
					sourceProcessors.add(
						JSPSourceProcessor.class.newInstance());
					sourceProcessors.add(JSSourceProcessor.class.newInstance());
					sourceProcessors.add(
						PropertiesSourceProcessor.class.newInstance());
					sourceProcessors.add(SHSourceProcessor.class.newInstance());
					sourceProcessors.add(
						SQLSourceProcessor.class.newInstance());
					sourceProcessors.add(
						TLDSourceProcessor.class.newInstance());

					for (SourceProcessor sourceProcessor : sourceProcessors) {
						_runSourceProcessor(sourceProcessor);
					}
				}
				catch (Throwable t) {
					t.printStackTrace();

					exceptionReference1.set(t);
				}
			}

		};

		final AtomicReference<Throwable> exceptionReference2 =
			new AtomicReference<Throwable>();

		Thread thread2 = new Thread () {

			@Override
			public void run() {
				try {
					List<SourceProcessor> sourceProcessors =
						new ArrayList<SourceProcessor>();

					sourceProcessors.add(
						JavaSourceProcessor.class.newInstance());
					sourceProcessors.add(
						XMLSourceProcessor.class.newInstance());

					for (SourceProcessor sourceProcessor : sourceProcessors) {
						_runSourceProcessor(sourceProcessor);
					}
				}
				catch (Throwable t) {
					t.printStackTrace();

					exceptionReference2.set(t);
				}
			}

		};

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

		Throwable throwable1 = exceptionReference1.get();
		Throwable throwable2 = exceptionReference2.get();

		if (throwable1 != null) {
			if (throwable2 != null) {
				throwable1.addSuppressed(throwable2);
			}

			throw throwable1;
		}
		else if (throwable2 != null) {
			throw throwable2;
		}

		if (_throwException) {
			if (!_errorMessages.isEmpty()) {
				throw new Exception(StringUtil.merge(_errorMessages, "\n"));
			}

			if (_firstSourceMismatchException != null) {
				throw _firstSourceMismatchException;
			}
		}
	}

	public Tuple format(String fileName) throws Exception {
		SourceProcessor sourceProcessor = null;

		if (fileName.endsWith(".testjava")) {
			sourceProcessor = JavaSourceProcessor.class.newInstance();
		}
		else if (fileName.endsWith(".testsql")) {
			sourceProcessor = SQLSourceProcessor.class.newInstance();
		}
		else if (fileName.endsWith(".testtld")) {
			sourceProcessor = TLDSourceProcessor.class.newInstance();
		}
		else if (fileName.endsWith(".testxml")) {
			sourceProcessor = XMLSourceProcessor.class.newInstance();
		}

		if (sourceProcessor == null) {
			return null;
		}

		String newContent = sourceProcessor.format(
			fileName, _useProperties, _printErrors, _autoFix);

		return new Tuple(newContent, sourceProcessor.getErrorMessages());
	}

	private void _runSourceProcessor(SourceProcessor sourceProcessor)
		throws Exception {

		sourceProcessor.format(_useProperties, _printErrors, _autoFix);

		_errorMessages.addAll(sourceProcessor.getErrorMessages());

		if (_firstSourceMismatchException == null) {
			_firstSourceMismatchException =
				sourceProcessor.getFirstSourceMismatchException();
		}
	}

	private final boolean _autoFix;
	private final Set<String> _errorMessages = new LinkedHashSet<String>();
	private SourceMismatchException _firstSourceMismatchException;
	private final boolean _printErrors;
	private final boolean _throwException;
	private final boolean _useProperties;

}