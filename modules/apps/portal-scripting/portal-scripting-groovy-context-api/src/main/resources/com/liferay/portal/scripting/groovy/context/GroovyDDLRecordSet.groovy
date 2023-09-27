/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scripting.groovy.context;

import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;

/**
 * @author Michael C. Han
 */
class GroovyDDLRecordSet {

	GroovyDDLRecordSet(
		GroovySite groovySite_, GroovyDDMStructure groovyDDMStructure_,
		String recordSetKey_, String name_, String description_) {

		this(
			groovySite_, groovyDDMStructure_, recordSetKey_, name_,
			description_, DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS);
	}

	GroovyDDLRecordSet(
		GroovySite groovySite_, GroovyDDMStructure groovyDDMStructure_,
		String recordSetKey_, String name_, String description_, int scope_) {

		groovySite = groovySite_;
		groovyDDMStructure = groovyDDMStructure_;
		recordSetKey = recordSetKey_;
		name = name_;
		description = description_;
		scope = scope_;
	}

	void addRecord(
		GroovyUser groovyUser, Map<String, Serializable> fieldsMap,
		GroovyScriptingContext groovyScriptingContext) {

		DDLRecordLocalServiceUtil.addRecord(
			groovyUser.user.getUserId(), groovySite.group.getGroupId(),
			ddlRecordSet.getRecordSetId(), 0, fieldsMap,
			groovyScriptingContext.serviceContext);
	}

	void create(
		GroovyUser groovyUser, GroovyScriptingContext groovyScriptingContext) {

		ddlRecordSet = DDLRecordSetLocalServiceUtil.fetchRecordSet(
			groovySite.group.getGroupId(), recordSetKey);

		if (ddlRecordSet != null) {
			return;
		}

		DDLRecordSetLocalServiceUtil.addRecordSet(
			groovyUser.user.getUserId(), groovySite.group.getGroupId(),
			groovyDDMStructure.getDdmStructure().getStructureId(), recordSetKey,
			GroovyScriptingContext.getLocalizationMap(name),
			GroovyScriptingContext.getLocalizationMap(description),
			DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT, scope,
			groovyScriptingContext.serviceContext);
	}

	List<DDLRecord> getRecords() {
		return ddlRecordSet.getRecords();
	}

	DDLRecordSet ddlRecordSet;
	String description;
	GroovyDDMStructure groovyDDMStructure;
	GroovySite groovySite;
	String name;
	String recordSetKey;
	int scope;

}