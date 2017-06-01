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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.checks.comparator.ElementComparator;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

/**
 * @author Hugo Huijser
 */
public abstract class BaseFileCheck
	extends BaseSourceCheck implements FileCheck {

	@Override
	public String process(String fileName, String absolutePath, String content)
		throws Exception {

		clearSourceFormatterMessages(fileName);

		return doProcess(fileName, absolutePath, content);
	}

	protected void checkElementOrder(
		String fileName, Element rootElement, String elementName,
		String parentElementName, ElementComparator elementComparator) {

		if (rootElement == null) {
			return;
		}

		Node previousNode = null;

		Iterator<Node> iterator = rootElement.nodeIterator();

		while (iterator.hasNext()) {
			Node curNode = (Node)iterator.next();

			if (curNode instanceof Text) {
				continue;
			}

			if (previousNode == null) {
				previousNode = curNode;

				continue;
			}

			if (curNode instanceof Element && previousNode instanceof Element) {
				Element curElement = (Element)curNode;
				Element previousElement = (Element)previousNode;

				String curElementName = curElement.getName();
				String previousElementName = previousElement.getName();

				if (curElementName.equals(elementName) &&
					previousElementName.equals(elementName) &&
					(elementComparator.compare(previousElement, curElement) >
						0)) {

					StringBundler sb = new StringBundler(7);

					sb.append("Incorrect order '");
					sb.append(elementName);
					sb.append("':");

					if (Validator.isNotNull(parentElementName)) {
						sb.append(StringPool.SPACE);
						sb.append(parentElementName);
					}

					sb.append(StringPool.SPACE);
					sb.append(elementComparator.getElementName(curElement));

					addMessage(fileName, sb.toString());
				}
			}

			previousNode = curNode;
		}
	}

	protected abstract String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception;

	protected BNDSettings getBNDSettings(String fileName) throws Exception {
		for (Map.Entry<String, BNDSettings> entry :
				_bndSettingsMap.entrySet()) {

			String bndFileLocation = entry.getKey();

			if (fileName.startsWith(bndFileLocation)) {
				return entry.getValue();
			}
		}

		String bndFileLocation = fileName;

		while (true) {
			int pos = bndFileLocation.lastIndexOf(StringPool.SLASH);

			if (pos == -1) {
				return null;
			}

			bndFileLocation = bndFileLocation.substring(0, pos + 1);

			File file = new File(bndFileLocation + "bnd.bnd");

			if (file.exists()) {
				return new BNDSettings(bndFileLocation, FileUtil.read(file));
			}

			bndFileLocation = StringUtil.replaceLast(
				bndFileLocation, CharPool.SLASH, StringPool.BLANK);
		}
	}

	protected String getLine(String content, int lineCount) {
		int nextLineStartPos = getLineStartPos(content, lineCount);

		if (nextLineStartPos == -1) {
			return null;
		}

		int nextLineEndPos = content.indexOf(
			CharPool.NEW_LINE, nextLineStartPos);

		if (nextLineEndPos == -1) {
			return content.substring(nextLineStartPos);
		}

		return content.substring(nextLineStartPos, nextLineEndPos);
	}

	protected int getLineLength(String line) {
		int lineLength = 0;

		int tabLength = 4;

		for (char c : line.toCharArray()) {
			if (c == CharPool.TAB) {
				for (int i = 0; i < tabLength; i++) {
					lineLength++;
				}

				tabLength = 4;
			}
			else {
				lineLength++;

				tabLength--;

				if (tabLength <= 0) {
					tabLength = 4;
				}
			}
		}

		return lineLength;
	}

	protected int getLineStartPos(String content, int lineCount) {
		int x = 0;

		for (int i = 1; i < lineCount; i++) {
			x = content.indexOf(CharPool.NEW_LINE, x + 1);

			if (x == -1) {
				return x;
			}
		}

		return x + 1;
	}

	protected void putBNDSettings(BNDSettings bndSettings) {
		_bndSettingsMap.put(bndSettings.getFileLocation(), bndSettings);
	}

	protected static final String METHOD_CALL_SORT_EXCLUDES =
		"method.call.sort.excludes";

	protected static final String RUN_OUTSIDE_PORTAL_EXCLUDES =
		"run.outside.portal.excludes";

	private final Map<String, BNDSettings> _bndSettingsMap =
		new ConcurrentHashMap<>();

}