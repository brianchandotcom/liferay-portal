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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.source.formatter.util.GitUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Hugo Huijser
 */
public class SourceFormatter {

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		try {
			SourceFormatterArgs sourceFormatterArgs = new SourceFormatterArgs();

			boolean autoFix = GetterUtil.getBoolean(
				arguments.get("source.auto.fix"), SourceFormatterArgs.AUTO_FIX);

			sourceFormatterArgs.setAutoFix(autoFix);

			String baseDirName = GetterUtil.getString(
				arguments.get("source.base.dir"),
				SourceFormatterArgs.BASE_DIR_NAME);

			sourceFormatterArgs.setBaseDirName(baseDirName);

			boolean formatCurrentBranch = GetterUtil.getBoolean(
				arguments.get("format.current.branch"),
				SourceFormatterArgs.FORMAT_CURRENT_BRANCH);

			sourceFormatterArgs.setFormatCurrentBranch(formatCurrentBranch);

			boolean formatLatestAuthor = GetterUtil.getBoolean(
				arguments.get("format.latest.author"),
				SourceFormatterArgs.FORMAT_LATEST_AUTHOR);

			sourceFormatterArgs.setFormatLatestAuthor(formatLatestAuthor);

			boolean formatLocalChanges = GetterUtil.getBoolean(
				arguments.get("format.local.changes"),
				SourceFormatterArgs.FORMAT_LOCAL_CHANGES);

			sourceFormatterArgs.setFormatLocalChanges(formatLocalChanges);

			if (formatCurrentBranch) {
				sourceFormatterArgs.setRecentChangesFileNames(
					GitUtil.getCurrentBranchFileNames(baseDirName));
			}
			else if (formatLatestAuthor) {
				sourceFormatterArgs.setRecentChangesFileNames(
					GitUtil.getLatestAuthorFileNames(baseDirName));
			}
			else if (formatLocalChanges) {
				sourceFormatterArgs.setRecentChangesFileNames(
					GitUtil.getLocalChangesFileNames(baseDirName));
			}

			String copyrightFileName = GetterUtil.getString(
				arguments.get("source.copyright.file"),
				SourceFormatterArgs.COPYRIGHT_FILE_NAME);

			sourceFormatterArgs.setCopyrightFileName(copyrightFileName);

			String[] fileNames = StringUtil.split(
				arguments.get("source.files"), StringPool.COMMA);

			if (ArrayUtil.isNotEmpty(fileNames)) {
				sourceFormatterArgs.setFileNames(Arrays.asList(fileNames));
			}

			boolean printErrors = GetterUtil.getBoolean(
				arguments.get("source.print.errors"),
				SourceFormatterArgs.PRINT_ERRORS);

			sourceFormatterArgs.setPrintErrors(printErrors);

			boolean throwException = GetterUtil.getBoolean(
				arguments.get("source.throw.exception"),
				SourceFormatterArgs.THROW_EXCEPTION);

			sourceFormatterArgs.setThrowException(throwException);

			boolean useProperties = GetterUtil.getBoolean(
				arguments.get("source.use.properties"),
				SourceFormatterArgs.USE_PROPERTIES);

			sourceFormatterArgs.setUseProperties(useProperties);

			SourceFormatter sourceFormatter = new SourceFormatter(
				sourceFormatterArgs);

			sourceFormatter.format();
		}
		catch (GitException ge) {
			System.out.println(ge.getMessage());

			System.exit(0);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	public SourceFormatter(SourceFormatterArgs sourceFormatterArgs) {
		_sourceFormatterArgs = sourceFormatterArgs;
	}

	public void format() throws Exception {
		List<SourceProcessor> sourceProcessors = new ArrayList<>();

		sourceProcessors.add(new CSSSourceProcessor());
		sourceProcessors.add(new FTLSourceProcessor());
		sourceProcessors.add(new GradleSourceProcessor());
		sourceProcessors.add(new JavaSourceProcessor());
		sourceProcessors.add(new JSPSourceProcessor());
		sourceProcessors.add(new JSSourceProcessor());
		sourceProcessors.add(new PropertiesSourceProcessor());
		sourceProcessors.add(new SHSourceProcessor());
		sourceProcessors.add(new SQLSourceProcessor());
		sourceProcessors.add(new TLDSourceProcessor());
		sourceProcessors.add(new XMLSourceProcessor());

		ExecutorService executorService = Executors.newFixedThreadPool(
			sourceProcessors.size());

		List<Future<Void>> futures = new ArrayList<>(sourceProcessors.size());

		for (final SourceProcessor sourceProcessor : sourceProcessors) {
			Future<Void> future = executorService.submit(
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						_runSourceProcessor(sourceProcessor);

						return null;
					}

				}
			);

			futures.add(future);
		}

		ExecutionException ee1 = null;

		for (Future<Void> future : futures) {
			try {
				future.get();
			}
			catch (ExecutionException ee2) {
				if (ee1 == null) {
					ee1 = ee2;
				}
				else {
					ee1.addSuppressed(ee2);
				}
			}
		}

		executorService.shutdown();

		while (!executorService.isTerminated()) {
			Thread.sleep(20);
		}

		if (ee1 != null) {
			throw ee1;
		}

		if (_sourceFormatterArgs.isThrowException()) {
			if (!_errorMessages.isEmpty()) {
				throw new Exception(StringUtil.merge(_errorMessages, "\n"));
			}

			if (_firstSourceMismatchException != null) {
				throw _firstSourceMismatchException;
			}
		}
	}

	public List<String> getErrorMessages() {
		return new ArrayList<>(_errorMessages);
	}

	public List<String> getModifiedFileNames() {
		return _modifiedFileNames;
	}

	public SourceFormatterArgs getSourceFormatterArgs() {
		return _sourceFormatterArgs;
	}

	public SourceMismatchException getSourceMismatchException() {
		return _firstSourceMismatchException;
	}

	private void _runSourceProcessor(SourceProcessor sourceProcessor)
		throws Exception {

		sourceProcessor.setSourceFormatterArgs(_sourceFormatterArgs);

		sourceProcessor.format();

		_errorMessages.addAll(sourceProcessor.getErrorMessages());
		_modifiedFileNames.addAll(sourceProcessor.getModifiedFileNames());

		if (_firstSourceMismatchException == null) {
			_firstSourceMismatchException =
				sourceProcessor.getFirstSourceMismatchException();
		}
	}

	private final Set<String> _errorMessages = new ConcurrentSkipListSet<>();
	private volatile SourceMismatchException _firstSourceMismatchException;
	private final List<String> _modifiedFileNames =
		new CopyOnWriteArrayList<>();
	private final SourceFormatterArgs _sourceFormatterArgs;

}