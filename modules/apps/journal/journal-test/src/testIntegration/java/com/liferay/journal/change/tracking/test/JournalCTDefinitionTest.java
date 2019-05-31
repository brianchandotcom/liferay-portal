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

package com.liferay.journal.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.definition.CTDefinition;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.io.Serializable;

import java.util.Optional;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gergely Mathe
 */
@RunWith(Arquillian.class)
public class JournalCTDefinitionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_journalArticleResource = _journalArticle.getArticleResource();
	}

	@Test
	public void testJournalArticleCTConfiguration() throws Exception {
		Optional<CTDefinition<?, ?>> ctConfigurationOptional =
			_getCTDefinitionOptional();

		Assert.assertTrue(
			"Change tracking configuration is not registered",
			ctConfigurationOptional.isPresent());

		JournalArticleResource resourceEntity =
			(JournalArticleResource)ctConfigurationOptional.map(
				CTDefinition::getResourceEntityByResourceEntityIdFunction
			).map(
				resourceEntityByResourceEntityIdFunction ->
					resourceEntityByResourceEntityIdFunction.apply(
						_journalArticleResource.getResourcePrimKey())
			).orElse(
				null
			);

		Assert.assertNotNull(
			"Resource entity by resource entity ID does not exist",
			resourceEntity);

		Assert.assertEquals(
			"Resource entity by resource entity ID is invalid",
			_journalArticleResource, resourceEntity);

		Class<JournalArticleResource> resourceClass =
			(Class<JournalArticleResource>)ctConfigurationOptional.map(
				CTDefinition::getResourceEntityClass
			).orElse(
				null
			);

		Assert.assertEquals(
			"Resource entity class is invalid",
			_journalArticleResource.getModelClass(), resourceClass);

		long resourceEntityId = (long)ctConfigurationOptional.map(
			CTDefinition::getResourceEntityIdFromResourceEntityFunction
		).map(
			resourceEntityIdFromResourceEntityFunction ->
				(Function<JournalArticleResource, Serializable>)
					resourceEntityIdFromResourceEntityFunction
		).map(
			resourceEntityIdFromResourceEntityFunction ->
				resourceEntityIdFromResourceEntityFunction.apply(
					_journalArticleResource)
		).orElse(
			0L
		);

		Assert.assertEquals(
			"Resource entity ID by resource entity is invalid",
			_journalArticleResource.getResourcePrimKey(), resourceEntityId);

		resourceEntityId = (long)ctConfigurationOptional.map(
			CTDefinition::getResourceEntityIdFromVersionEntityFunction
		).map(
			resourceEntityIdFromVersionEntityFunction ->
				(Function<JournalArticle, Serializable>)
					resourceEntityIdFromVersionEntityFunction
		).map(
			resourceEntityIdFromVersionEntityFunction ->
				resourceEntityIdFromVersionEntityFunction.apply(_journalArticle)
		).orElse(
			0L
		);

		Assert.assertEquals(
			"Resource entity ID by version entity is invalid",
			_journalArticleResource.getResourcePrimKey(), resourceEntityId);

		JournalArticle versionEntity =
			(JournalArticle)ctConfigurationOptional.map(
				CTDefinition::getVersionEntityByVersionEntityIdFunction
			).map(
				versionEntityByVersionEntityIdFunction ->
					versionEntityByVersionEntityIdFunction.apply(
						_journalArticle.getId())
			).orElse(
				null
			);

		Assert.assertNotNull(
			"Version entity by version entity ID does not exist",
			versionEntity);

		Assert.assertEquals(
			"Version entity by version entity ID is invalid", _journalArticle,
			versionEntity);

		Class<JournalArticle> versionClass =
			(Class<JournalArticle>)ctConfigurationOptional.map(
				CTDefinition::getVersionEntityClass
			).orElse(
				null
			);

		Assert.assertEquals(
			"Version entity class is invalid", _journalArticle.getModelClass(),
			versionClass);

		long versionEntityId = (long)ctConfigurationOptional.map(
			CTDefinition::getVersionEntityIdFromResourceEntityFunction
		).map(
			versionEntityIdFromResourceEntityFunction ->
				(Function<JournalArticleResource, Serializable>)
					versionEntityIdFromResourceEntityFunction
		).map(
			versionEntityIdFromResourceEntityFunction ->
				versionEntityIdFromResourceEntityFunction.apply(
					_journalArticleResource)
		).orElse(
			0L
		);

		Assert.assertEquals(
			"Version entity ID by resource entity is invalid",
			_journalArticle.getId(), versionEntityId);

		versionEntityId = (long)ctConfigurationOptional.map(
			CTDefinition::getVersionEntityIdFromVersionEntityFunction
		).map(
			versionEntityIdFromVersionEntityFunction ->
				(Function<JournalArticle, Serializable>)
					versionEntityIdFromVersionEntityFunction
		).map(
			versionEntityIdFromVersionEntityFunction ->
				versionEntityIdFromVersionEntityFunction.apply(_journalArticle)
		).orElse(
			0L
		);

		Assert.assertEquals(
			"Version entity ID by version entity is invalid",
			_journalArticle.getId(), versionEntityId);

		int versionEntityStatus = ctConfigurationOptional.map(
			CTDefinition::getVersionEntityStatusFunction
		).map(
			versionEntityStatusFunction ->
				(Function<JournalArticle, Integer>)versionEntityStatusFunction
		).map(
			versionEntityStatusFunction -> versionEntityStatusFunction.apply(
				_journalArticle)
		).orElse(
			0
		);

		Assert.assertEquals(
			"Version entity status is invalid", _journalArticle.getStatus(),
			versionEntityStatus);
	}

	private Optional<CTDefinition<?, ?>> _getCTDefinitionOptional()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		Object[] objects = registry.getServices(
			"com.liferay.change.tracking.definition.CTDefinitionRegistry",
			null);

		Object ctConfigurationRegistry = objects[0];

		return ReflectionTestUtil.invoke(
			ctConfigurationRegistry,
			"getCTDefinitionOptionalByVersionClassName",
			new Class<?>[] {String.class},
			new String[] {JournalArticle.class.getName()});
	}

	@DeleteAfterTestRun
	private Group _group;

	private JournalArticle _journalArticle;
	private JournalArticleResource _journalArticleResource;

}