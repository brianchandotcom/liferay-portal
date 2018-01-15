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

import com.liferay.talend.tliferayinput.TLiferayInputDefinition;

import javax.inject.Inject;

import org.junit.Test;

import org.talend.components.api.component.ComponentDefinition;
import org.talend.components.api.test.AbstractComponentTest2;
import org.talend.daikon.definition.Definition;
import org.talend.daikon.definition.service.DefinitionRegistryService;

/**
 * @author Zoltán Takács
 */
public class LiferayInputTestBase extends AbstractComponentTest2 {

	@Override
	public DefinitionRegistryService getDefinitionRegistry() {
		return _definitionRegistry;
	}

	@Test
	public void testComponentHasBeenRegistered() {
		assertComponentIsRegistered(
			ComponentDefinition.class, "tLiferayInput",
			TLiferayInputDefinition.class);

		assertComponentIsRegistered(
			Definition.class, "tLiferayInput", TLiferayInputDefinition.class);
	}

	@Inject
	private DefinitionRegistryService _definitionRegistry;

}