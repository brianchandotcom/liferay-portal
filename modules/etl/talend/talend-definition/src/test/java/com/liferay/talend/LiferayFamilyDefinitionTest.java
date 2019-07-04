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
import com.liferay.talend.wizard.LiferayConnectionEditWizardDefinition;
import com.liferay.talend.wizard.LiferayConnectionWizardDefinition;
import com.liferay.talend.wizard.LiferaySchemaWizardDefinition;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.talend.components.api.ComponentInstaller.ComponentFrameworkContext;
import org.talend.components.liferay.LiferayFamilyDefinition;
import org.talend.daikon.definition.Definition;

/**
 * @author Zoltán Takács
 */
public class LiferayFamilyDefinitionTest {

	@Before
	public void setUp() {
		_liferayFamilyDefinition = new LiferayFamilyDefinition();
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testAllComponentsDefinitionsCreated() {
		List<Class> expectedDefinitions = new ArrayList<>();

		expectedDefinitions.add(TLiferayConnectionDefinition.class);
		expectedDefinitions.add(TLiferayInputDefinition.class);
		expectedDefinitions.add(TLiferayOutputDefinition.class);
		expectedDefinitions.add(LiferayConnectionEditWizardDefinition.class);
		expectedDefinitions.add(LiferaySchemaWizardDefinition.class);
		expectedDefinitions.add(LiferayConnectionWizardDefinition.class);

		List<Class> actualDefinitionsNames = new ArrayList<>();

		for (Definition<?> definition :
				_liferayFamilyDefinition.getDefinitions()) {

			actualDefinitionsNames.add(definition.getClass());
		}

		Assert.assertEquals(expectedDefinitions, actualDefinitionsNames);
	}

	@Test
	public void testFamilyInstalled() {
		ComponentFrameworkContext componentFrameworkContext = Mockito.mock(
			ComponentFrameworkContext.class);

		_liferayFamilyDefinition.install(componentFrameworkContext);

		Mockito.verify(componentFrameworkContext);

		componentFrameworkContext.registerComponentFamilyDefinition(
			_liferayFamilyDefinition);
	}

	private LiferayFamilyDefinition _liferayFamilyDefinition;

}