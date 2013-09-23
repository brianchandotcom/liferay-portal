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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UniqueList;

import java.util.ArrayList;
import java.util.List;

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
		catch (Exception e) {
			e.printStackTrace();
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

	public void format() throws Exception {
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
						JavaSourceProcessor.class.newInstance());
					sourceProcessors.add(JSSourceProcessor.class.newInstance());
					sourceProcessors.add(
						PropertiesSourceProcessor.class.newInstance());
					sourceProcessors.add(SHSourceProcessor.class.newInstance());
					sourceProcessors.add(
						SQLSourceProcessor.class.newInstance());
					sourceProcessors.add(
						TLDSourceProcessor.class.newInstance());
					sourceProcessors.add(
						XMLSourceProcessor.class.newInstance());

					for (SourceProcessor sourceProcessor : sourceProcessors) {
						sourceProcessor.format(
							_useProperties, _printErrors, _autoFix);

						_errorMessages.addAll(
							sourceProcessor.getErrorMessages());
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

		};

		Thread thread2 = new Thread () {

			@Override
			public void run() {
				try {
					SourceProcessor sourceProcessor =
						JSPSourceProcessor.class.newInstance();

					sourceProcessor.format(
						_useProperties, _printErrors, _autoFix);

					_errorMessages.addAll(sourceProcessor.getErrorMessages());
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

		};

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

		if (_throwException && !_errorMessages.isEmpty()) {
			throw new Exception(StringUtil.merge(_errorMessages, "\n"));
		}
	}

	public String[] format(String fileName) throws Exception {
		SourceProcessor sourceProcessor = null;

		if (fileName.endsWith(".testjava")) {
			sourceProcessor = JavaSourceProcessor.class.newInstance();
		}

		if (sourceProcessor == null) {
			return null;
		}

		String newContent = sourceProcessor.format(
			fileName, _useProperties, _printErrors, _autoFix);

		List<String> errorMessages = sourceProcessor.getErrorMessages();

		if (errorMessages.isEmpty()) {
			return new String[] {newContent, null};
		}
		else {
			return new String[] {newContent, errorMessages.get(0)};
		}
	}

	private static boolean _autoFix;
	private static List<String> _errorMessages = new UniqueList<String>();
	private static boolean _printErrors;
	private static boolean _throwException;
	private static boolean _useProperties;

}