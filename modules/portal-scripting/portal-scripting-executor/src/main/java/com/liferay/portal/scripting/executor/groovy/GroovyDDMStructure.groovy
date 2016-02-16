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

package com.liferay.portal.scripting.executor.groovy;

import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants
import com.liferay.dynamic.data.mapping.model.DDMStructure
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil

/**
 * @author Michael C. Han
 */
class GroovyDDMStructure {

	GroovyDDMStructure(
		GroovySite groovySite_,
		String structureKey_, String name_,
		String description_, String xsd_) {
			groovySite = groovySite_
			structureKey = structureKey_
			name = name_
			xsd = xsd_
			description = description_
	}
		
	void create(GroovyScriptingContext scriptingContext) {
		ddmStructure = GroovyDDMStructure.fetchStructure(scriptingContext, groovySite, structureKey)

		if(ddmStructure !=null){
			return;
		}
		ddmStructure = DDMStructureLocalServiceUtil.addStructure(scriptingContext.getDefaultUserId(),
				groovySite.getSite().getGroupId(), 0,
				ClassNameLocalServiceUtil.getClassNameId(DDLRecordSet.class),
				null, GroovyScriptingContext.getLocalizationMap(name),
				GroovyScriptingContext.getLocalizationMap(description),
				xsd, "xml",
				DDMStructureConstants.TYPE_DEFAULT,
				scriptingContext.getServiceContext());
	}

	static DDMStructure fetchStructure(GroovyScriptingContext scriptingContext,
		GroovySite groovySite_, String structureKey_){
			Long classnameId = ClassNameLocalServiceUtil.getClassNameId(DDLRecordSet.class)
			return DDMStructureLocalServiceUtil.fetchStructure(groovySite_.getSite().getGroupId(),
			classnameId, structureKey_)
	}

	DDMStructure ddmStructure;
	GroovySite groovySite;
	String description;
	String name;
	String xsd;
	String structureKey;
}
