/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class LDIFEntryOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		List<String> ldifEntries = new ArrayList<>();

		for (String ldifEntry : ListUtil.fromString(content, "\n\n")) {
			ldifEntries.add(_sortAttributes(ldifEntry));
		}

		Collections.sort(ldifEntries);

		StringBundler sb = new StringBundler(ldifEntries.size() * 2);

		for (String ldifEntry : ldifEntries) {
			sb.append(ldifEntry);
			sb.append("\n\n");
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private String _sortAttributes(String ldifEntry) {
		AttributeComparator attributeComparator = new AttributeComparator(
			ldifEntry);

		Matcher matcher = _attributePattern.matcher(ldifEntry);

		String previousAttribute = StringPool.BLANK;
		int previousAttributeStartPosition = 0;

		while (matcher.find()) {
			String attribute = matcher.group();
			int attributeStartPosition = matcher.start();

			if (Validator.isNull(previousAttribute)) {
				previousAttribute = attribute;
				previousAttributeStartPosition = attributeStartPosition;

				continue;
			}

			if (attributeComparator.compare(previousAttribute, attribute) > 0) {
				ldifEntry = StringUtil.replaceFirst(
					ldifEntry, attribute, previousAttribute,
					attributeStartPosition);

				return StringUtil.replaceFirst(
					ldifEntry, previousAttribute, attribute,
					previousAttributeStartPosition);
			}

			previousAttribute = attribute;
			previousAttributeStartPosition = attributeStartPosition;
		}

		return ldifEntry;
	}

	private static final Pattern _attributePattern = Pattern.compile(
		"(?<=(\\A|\n)).+?:[\\s\\S]*?(?=\n.+:|\\Z)");

	private static class AttributeComparator implements Comparator<String> {

		public AttributeComparator(String ldifEntry) {
			_ldifEntry = ldifEntry;
		}

		@Override
		public int compare(String attribute1, String attribute2) {
			if (attribute1.startsWith("dn:") &&
				_ldifEntry.contains("changetype: modify")) {

				return -1;
			}

			if (attribute2.startsWith("dn:") &&
				_ldifEntry.contains("changetype: modify")) {

				return 1;
			}

			String[] attributeValuePair1 = attribute1.split(
				StringPool.COLON, 2);
			String[] attributeValuePair2 = attribute2.split(
				StringPool.COLON, 2);

			if (attributeValuePair1[0].compareTo(attributeValuePair2[0]) == 0) {
				return attributeValuePair1[1].compareTo(attributeValuePair2[1]);
			}

			return attributeValuePair1[0].compareTo(attributeValuePair2[0]);
		}

		private final String _ldifEntry;

	}

}