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

import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Hugo Huijser
 */
public class SourceFormatter {

	public static final String PROCESSED_FILES_ATTRIBUTE =
		"source.formatter.processed.files";

	public static void main(String[] args) {
		try {
			SourceFormatterBean sourceFormatterBean = new SourceFormatterBean();

			sourceFormatterBean.setAutoFix(true);
			sourceFormatterBean.setPrintErrors(true);
			sourceFormatterBean.setThrowException(false);
			sourceFormatterBean.setUseProperties(false);

			SourceFormatter sourceFormatter = new SourceFormatter(
				sourceFormatterBean);

			sourceFormatter.format();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SourceFormatter(SourceFormatterBean sourceFormatterBean) {
		_sourceFormatterBean = sourceFormatterBean;
	}

	public void format() throws Exception {
		List<SourceProcessor> sourceProcessors = new ArrayList<>();

		sourceProcessors.add(new CSSSourceProcessor());
		sourceProcessors.add(new FTLSourceProcessor());
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

		for (final SourceProcessor sourceProcessor : sourceProcessors) {
			executorService.submit(
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						_runSourceProcessor(sourceProcessor);

						return null;
					}

				}
			);
		}

		executorService.shutdown();

		while (!executorService.isTerminated()) {
			Thread.sleep(20);
		}

		if (_sourceFormatterBean.isThrowException()) {
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

	public SourceMismatchException getSourceMismatchException() {
		return _firstSourceMismatchException;
	}

	public List<String> getProcessedFiles() {
		return _processedFiles;
	}

	public SourceFormatterBean getSourceformatterBean() {
		return _sourceFormatterBean;
	}

	private void _runSourceProcessor(SourceProcessor sourceProcessor)
		throws Exception {

		sourceProcessor.setSourceFormatterBean(_sourceFormatterBean);

		sourceProcessor.format();

		_errorMessages.addAll(sourceProcessor.getErrorMessages());
		_processedFiles.addAll(sourceProcessor.getProcessedFiles());

		if (_firstSourceMismatchException == null) {
			_firstSourceMismatchException =
				sourceProcessor.getFirstSourceMismatchException();
		}
	}

	private final Set<String> _errorMessages = new ConcurrentSkipListSet<>();
	private volatile SourceMismatchException _firstSourceMismatchException;
	private final List<String> _processedFiles = new CopyOnWriteArrayList<>();
	private final SourceFormatterBean _sourceFormatterBean;

}