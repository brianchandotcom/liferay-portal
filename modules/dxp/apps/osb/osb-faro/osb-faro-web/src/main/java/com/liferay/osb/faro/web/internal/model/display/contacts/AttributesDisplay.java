/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.FieldMapping;

import java.util.Date;
import java.util.List;

/**
 * @author Rachael Koestartyo
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class AttributesDisplay {

	public AttributesDisplay() {
	}

	public AttributesDisplay(FieldMapping fieldMapping) {
		_dataSources = fieldMapping.getDataSources();
		_dateModified = fieldMapping.getDateModified();
		_fieldName = fieldMapping.getDisplayName();
	}

	private List<FieldMapping.DataSourceFieldName> _dataSources;
	private Date _dateModified;
	private String _fieldName;

}