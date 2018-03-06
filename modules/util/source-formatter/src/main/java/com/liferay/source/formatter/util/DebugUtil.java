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

package com.liferay.source.formatter.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import difflib.Chunk;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Hugo Huijser
 */
public class DebugUtil {

	public static void addCheckNames(
		CheckType checkType, List<String> checkNames) {

		_checkNamesMap.put(checkType, checkNames);
	}

	public static void addProcessorFileCount(String processorName, int count) {
		_processorFileCountMap.put(processorName, count);
	}

	public static void finishTask() {
		_concurrentTasksCount.decrementAndGet();
	}

	public static synchronized void increaseProcessingTime(
		String checkName, long processingTime) {

		double checkTotalProcessingTime = 0.0;

		if (_processingTimeMap.containsKey(checkName)) {
			checkTotalProcessingTime = _processingTimeMap.get(checkName);
		}

		checkTotalProcessingTime +=
			(double)processingTime / Math.max(1, _concurrentTasksCount.get());

		_processingTimeMap.put(checkName, checkTotalProcessingTime);
	}

	public static void printContentModifications(
		String checkName, String fileName, String originalContent,
		String modifiedContent) {

		List<String> originalLines = ListUtil.fromArray(
			StringUtil.splitLines(originalContent));
		List<String> modifiedLines = ListUtil.fromArray(
			StringUtil.splitLines(modifiedContent));

		Patch<String> patch = DiffUtils.diff(originalLines, modifiedLines);

		StringBundler sb = new StringBundler(5);

		sb.append("'");
		sb.append(checkName);
		sb.append("' modified '");
		sb.append(fileName);
		sb.append("':");

		System.out.println(sb.toString());

		String shortFileName = null;

		int pos = fileName.lastIndexOf(CharPool.SLASH);

		if (pos == -1) {
			shortFileName = fileName;
		}
		else {
			shortFileName = fileName.substring(pos + 1);
		}

		for (Delta<String> delta : patch.getDeltas()) {
			_printDelta(delta, shortFileName);
		}
	}

	public static void printSourceFormatterInformation() {
		_printProcessorInformation();

		_printProcessingTimeInformation(CheckType.CHECKSTYLE);
		_printProcessingTimeInformation(CheckType.SOURCE_CHECK);
	}

	public static void startTask() {
		_concurrentTasksCount.incrementAndGet();
	}

	private static void _printDelta(Delta<String> delta, String fileName) {
		StringBundler sb = new StringBundler();

		sb.append(fileName);
		sb.append(", ");

		if (delta.getType() == Delta.TYPE.CHANGE) {
			Chunk<String> originalChunk = delta.getOriginal();

			sb.append("line ");
			sb.append(originalChunk.getPosition() + 1);
			sb.append(" changed:\n");

			sb.append("before:\n");

			for (String line : originalChunk.getLines()) {
				sb.append("[");
				sb.append(line);
				sb.append("]\n");
			}

			sb.append("after:\n");

			Chunk<String> revisedChunk = delta.getRevised();

			for (String line : revisedChunk.getLines()) {
				sb.append("[");
				sb.append(line);
				sb.append("]");
				sb.append("\n");
			}

			sb.setIndex(sb.index() - 1);
		}
		else if (delta.getType() == Delta.TYPE.DELETE) {
			Chunk<String> originalChunk = delta.getOriginal();

			int startLine = originalChunk.getPosition() + 1;

			List<String> lines = originalChunk.getLines();

			if (lines.size() == 1) {
				sb.append(", line ");
				sb.append(startLine);
				sb.append(" was deleted");
			}
			else {
				sb.append(", lines ");
				sb.append(startLine);
				sb.append("-");
				sb.append(startLine + lines.size() - 1);
				sb.append(" were deleted");
			}
		}
		else if (delta.getType() == Delta.TYPE.INSERT) {
			Chunk<String> revisedChunk = delta.getRevised();

			int startLine = revisedChunk.getPosition() + 1;

			List<String> lines = revisedChunk.getLines();

			if (lines.size() == 1) {
				sb.append(", line ");
				sb.append(startLine);
				sb.append(" was added");
			}
			else {
				sb.append(", lines ");
				sb.append(startLine);
				sb.append("-");
				sb.append(startLine + lines.size() - 1);
				sb.append(" were added");
			}
		}

		System.out.println(sb.toString());
	}

	private static void _printProcessingTimeInformation(CheckType checkType) {
		if (!_checkNamesMap.containsKey(checkType)) {
			return;
		}

		final Map<String, Double> checkTypeProcessingTimeMap = new HashMap<>();

		for (String checkName : _checkNamesMap.get(checkType)) {
			if (_processingTimeMap.containsKey(checkName)) {
				checkTypeProcessingTimeMap.put(
					checkName, _processingTimeMap.get(checkName));
			}
		}

		if (checkTypeProcessingTimeMap.isEmpty()) {
			return;
		}

		StringBundler sb = new StringBundler(5);

		sb.append("\n");
		sb.append("==== Processing Time Information for '");
		sb.append(checkType.getValue());
		sb.append("' ====\n\n");

		System.out.println(sb.toString());

		List<String> keys = new ArrayList<>(
			checkTypeProcessingTimeMap.keySet());

		Collections.sort(
			keys,
			new Comparator<String>() {

				public int compare(String key1, String key2) {
					return Double.compare(
						checkTypeProcessingTimeMap.get(key2),
						checkTypeProcessingTimeMap.get(key1));
				}

			});

		double totalProcessingTime = 0.0;

		for (String key : keys) {
			totalProcessingTime += checkTypeProcessingTimeMap.get(key);
		}

		DecimalFormat decimalFormat = new DecimalFormat("0.00");

		for (String key : keys) {
			sb = new StringBundler(4);

			sb.append(key);
			sb.append(": ");

			double processingTime = checkTypeProcessingTimeMap.get(key);

			double percentage = (processingTime / totalProcessingTime) * 100;

			sb.append(decimalFormat.format(percentage));

			sb.append("%");

			System.out.println(sb.toString());
		}
	}

	private static void _printProcessorInformation() {
		System.out.println();
		System.out.println("==== SourceFormatter Processors Information ====");
		System.out.println();

		for (Map.Entry<String, Integer> entry :
				_processorFileCountMap.entrySet()) {

			StringBundler sb = new StringBundler(4);

			sb.append(entry.getKey());
			sb.append(" processed ");
			sb.append(entry.getValue());
			sb.append(" files.");

			System.out.println(sb.toString());
		}
	}

	private static final Map<CheckType, List<String>> _checkNamesMap =
		new HashMap<>();
	private static final AtomicInteger _concurrentTasksCount =
		new AtomicInteger();
	private static final Map<String, Double> _processingTimeMap =
		new ConcurrentHashMap<>();
	private static final Map<String, Integer> _processorFileCountMap =
		new ConcurrentSkipListMap<>();

}