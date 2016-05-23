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

package com.liferay.portal.workflow.kaleo.service.convert.database;

import com.liferay.portal.convert.database.DatabaseConverter;
import com.liferay.portal.convert.util.ModelMigrator;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoActionImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoConditionImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoDefinitionImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoInstanceImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoInstanceTokenImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoLogImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoNodeImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationRecipientImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskAssignmentImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskAssignmentInstanceImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTimerImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTimerInstanceTokenImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTransitionImpl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoDatabaseConverter implements DatabaseConverter {
	@Override
	public void convert(DataSource dataSource) throws Exception {
		_modelMigrator.migrate(dataSource, _getModels());
	}

	public void setModelMigrator(ModelMigrator modelMigrator) {
		_modelMigrator = modelMigrator;
	}

	private List<Class<?extends BaseModel<?>>> _getModels() {
		List<Class<?extends BaseModel<?>>> models = new ArrayList<Class<?extends BaseModel<?>>>();

		models.add(KaleoActionImpl.class);
		models.add(KaleoConditionImpl.class);
		models.add(KaleoDefinitionImpl.class);
		models.add(KaleoInstanceImpl.class);
		models.add(KaleoInstanceTokenImpl.class);
		models.add(KaleoLogImpl.class);
		models.add(KaleoNodeImpl.class);
		models.add(KaleoNotificationImpl.class);
		models.add(KaleoNotificationRecipientImpl.class);
		models.add(KaleoTaskImpl.class);
		models.add(KaleoTaskAssignmentImpl.class);
		models.add(KaleoTaskAssignmentInstanceImpl.class);
		models.add(KaleoTaskInstanceTokenImpl.class);
		models.add(KaleoTimerImpl.class);
		models.add(KaleoTimerInstanceTokenImpl.class);
		models.add(KaleoTransitionImpl.class);

		return models;
	}

	@ServiceReference(type = ModelMigrator.class)
	private ModelMigrator _modelMigrator;
}