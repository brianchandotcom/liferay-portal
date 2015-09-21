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

package com.liferay.portal.tools.service.builder;

import com.liferay.portal.kernel.util.CharPool;

/**
 * @author Glenn Powell
 * @author Brian Wing Shun Chan
 * @author Manuel de la Peña
 */
public class EntityMapping {

	public EntityMapping(
		String table, Entity company, String entity1, String entity2) {

		_table = table;
		_company = company;

		String companyModel =
			company.getPackagePath() + CharPool.PERIOD + company.getName();

		_entities[0] = companyModel;
		_entities[1] = entity1;
		_entities[2] = entity2;
	}

	public Entity getCompany() {
		return _company;
	}

	public int getEntitiesCount() {
		return _entities.length;
	}

	public String getEntity(int index) {
		try {
			return _entities[index];
		}
		catch (Exception e) {
			return null;
		}
	}

	public String getTable() {
		return _table;
	}

	private final Entity _company;
	private final String[] _entities = new String[3];
	private final String _table;

}