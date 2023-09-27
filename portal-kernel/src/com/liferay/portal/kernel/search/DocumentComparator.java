/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentComparator implements Comparator<Document> {

	public DocumentComparator() {
		this(true, false);
	}

	public DocumentComparator(boolean ascending, boolean caseSensitive) {
		_ascending = ascending;
		_caseSensitive = caseSensitive;
	}

	public void addOrderBy(String name) {
		addOrderBy(name, _ascending, _caseSensitive);
	}

	public void addOrderBy(String name, boolean ascending) {
		addOrderBy(name, ascending, _caseSensitive);
	}

	public void addOrderBy(
		String name, boolean ascending, boolean caseSensitive) {

		DocumentComparatorOrderBy orderBy = new DocumentComparatorOrderBy(
			name, ascending, caseSensitive);

		_columns.add(orderBy);
	}

	@Override
	public int compare(Document doc1, Document doc2) {
		for (DocumentComparatorOrderBy orderBy : _columns) {
			String value1 = doc1.get(orderBy.getName());
			String value2 = doc2.get(orderBy.getName());

			if (!orderBy.isAsc()) {
				String temp = value1;

				value1 = value2;
				value2 = temp;
			}

			int result = 0;

			if ((value1 != null) && (value2 != null)) {
				if (orderBy.isCaseSensitive()) {
					result = value1.compareTo(value2);
				}
				else {
					result = value1.compareToIgnoreCase(value2);
				}
			}

			if (result != 0) {
				return result;
			}
		}

		return 0;
	}

	private final boolean _ascending;
	private final boolean _caseSensitive;
	private final List<DocumentComparatorOrderBy> _columns = new ArrayList<>();

}