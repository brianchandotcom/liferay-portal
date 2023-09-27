/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class ServiceComponentImpl extends ServiceComponentBaseImpl {

	@Override
	public String getIndexesSQL() {
		return _getData("indexes-sql");
	}

	@Override
	public String getSequencesSQL() {
		return _getData("sequences-sql");
	}

	@Override
	public String getTablesSQL() {
		return _getData("tables-sql");
	}

	@Override
	public void setData(String data) {
		super.setData(data);

		_dataEl = null;
	}

	private String _getData(String name) {
		try {
			return _getDataEl().elementText(name);
		}
		catch (Exception exception) {
			_log.error(exception);

			return StringPool.BLANK;
		}
	}

	private Element _getDataEl() throws DocumentException {
		if (_dataEl == null) {
			Document doc = UnsecureSAXReaderUtil.read(getData());

			_dataEl = doc.getRootElement();
		}

		return _dataEl;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceComponentImpl.class);

	private Element _dataEl;

}