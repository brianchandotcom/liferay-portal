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

package com.liferay.talend;

import com.liferay.talend.tliferayconnection.TLiferayConnectionDefinition;
import com.liferay.talend.tliferayinput.TLiferayInputDefinition;
import com.liferay.talend.tliferayoutput.TLiferayOutputDefinition;

import javax.inject.Inject;

import org.junit.Test;

import org.talend.components.api.component.ComponentDefinition;
import org.talend.components.api.service.common.DefinitionRegistry;
import org.talend.components.api.test.AbstractComponentTest2;
import org.talend.components.liferay.LiferayFamilyDefinition;
import org.talend.daikon.definition.service.DefinitionRegistryService;

/**
 * @author Zoltán Takács
 */
public class LiferayTestBase extends AbstractComponentTest2 {

	@Override
	public DefinitionRegistryService getDefinitionRegistry() {
		if (_definitionRegistry == null) {
			_definitionRegistry = new DefinitionRegistry();

			_definitionRegistry.registerComponentFamilyDefinition(
				new LiferayFamilyDefinition());
		}

		return _definitionRegistry;
	}

	@Test
	public void testComponentHasBeenRegistered() {
		assertComponentIsRegistered(
			ComponentDefinition.class,
			TLiferayConnectionDefinition.COMPONENT_NAME,
			TLiferayConnectionDefinition.class);
		assertComponentIsRegistered(
			ComponentDefinition.class, TLiferayInputDefinition.COMPONENT_NAME,
			TLiferayInputDefinition.class);
		assertComponentIsRegistered(
			ComponentDefinition.class, TLiferayOutputDefinition.COMPONENT_NAME,
			TLiferayOutputDefinition.class);
	}

	@Inject
	private DefinitionRegistry _definitionRegistry;

}