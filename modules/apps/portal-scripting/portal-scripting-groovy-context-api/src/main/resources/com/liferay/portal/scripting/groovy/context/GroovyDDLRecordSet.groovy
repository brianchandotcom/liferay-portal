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

package com.liferay.portal.scripting.groovy.context;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
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