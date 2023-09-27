/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.web.internal.asset;

import com.liferay.asset.kernel.model.BaseDDMFormValuesReader;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslatorUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Adolfo Pérez
 */
public class DDLRecordDDMFormValuesReader extends BaseDDMFormValuesReader {

	public DDLRecordDDMFormValuesReader(DDLRecord ddlRecord) {
		_ddlRecord = ddlRecord;
	}

	@Override
	public DDMFormValues getDDMFormValues() throws PortalException {
		return DDMBeanTranslatorUtil.translate(_ddlRecord.getDDMFormValues());
	}

	private final DDLRecord _ddlRecord;

}