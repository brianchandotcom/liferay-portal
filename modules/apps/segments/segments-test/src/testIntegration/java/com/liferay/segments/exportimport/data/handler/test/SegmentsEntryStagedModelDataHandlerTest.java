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

package com.liferay.segments.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.adapter.StagedExpandoColumn;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryRegistryUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.odata.normalizer.Normalizer;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.expando.util.test.ExpandoTestUtil;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalServiceUtil;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class SegmentsEntryStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_expandoTable = ExpandoTestUtil.addTable(
			PortalUtil.getClassNameId(User.class), "CUSTOM_FIELDS");

		_stagedModelRepository =
			(StagedModelRepository<StagedExpandoColumn>)
				StagedModelRepositoryRegistryUtil.getStagedModelRepository(
					StagedExpandoColumn.class.getName());
	}

	@Test
	public void testExportImportSegmentsEntryWithEntityFieldCustomField()
		throws Exception {

		initExport();

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		StagedExpandoColumn stagedExpandoColumn = ModelAdapterUtil.adapt(
			expandoColumn, ExpandoColumn.class, StagedExpandoColumn.class);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, stagedExpandoColumn);

		String columnValue = RandomTestUtil.randomString();

		String filterString = String.format(
			"(customField/%s eq '%s')", _encodeName(expandoColumn),
			columnValue);

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			stagingGroup.getGroupId(), _getCriteria(filterString),
			User.class.getName());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, segmentsEntry);

		initImport();

		StagedExpandoColumn exportedStagedExpandoColumn =
			(StagedExpandoColumn)readExportedStagedModel(stagedExpandoColumn);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedStagedExpandoColumn);

		List<StagedExpandoColumn> stagedExpandoColumns =
			_stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
				stagedExpandoColumn.getUuid(), liveGroup.getCompanyId());

		if (ListUtil.isEmpty(stagedExpandoColumns)) {
			return;
		}

		StagedExpandoColumn importedStagedExpandoColumn =
			stagedExpandoColumns.get(0);

		SegmentsEntry exportedSegmentsEntry =
			(SegmentsEntry)readExportedStagedModel(segmentsEntry);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedSegmentsEntry);

		SegmentsEntry importedSegmentsEntry = (SegmentsEntry)getStagedModel(
			segmentsEntry.getUuid(), liveGroup);

		String importedFilterString = _getFilterString(
			importedSegmentsEntry.getCriteriaObj());

		Assert.assertEquals(
			String.format(
				"(customField/%s eq '%s')",
				_encodeName(importedStagedExpandoColumn), columnValue),
			importedFilterString);
	}

	@Test
	public void testExportImportSegmentsEntryWithEntityFieldID()
		throws Exception {

		initExport();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId());

		Team team = TeamLocalServiceUtil.addTeam(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		StagedModelDataHandlerUtil.exportStagedModel(portletDataContext, team);

		String filterString = String.format(
			"(teamIds eq '%s')", team.getTeamId());

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			stagingGroup.getGroupId(), _getCriteria(filterString),
			User.class.getName());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, segmentsEntry);

		initImport();

		Team exportedTeam = (Team)readExportedStagedModel(team);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedTeam);

		Team importedTeam = TeamLocalServiceUtil.fetchTeamByUuidAndGroupId(
			team.getUuid(), liveGroup.getGroupId());

		SegmentsEntry exportedSegmentsEntry =
			(SegmentsEntry)readExportedStagedModel(segmentsEntry);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedSegmentsEntry);

		SegmentsEntry importedSegmentsEntry = (SegmentsEntry)getStagedModel(
			segmentsEntry.getUuid(), liveGroup);

		String importedFilterString = _getFilterString(
			importedSegmentsEntry.getCriteriaObj());

		Assert.assertEquals(
			String.format("(teamIds eq '%s')", importedTeam.getTeamId()),
			importedFilterString);
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		return SegmentsTestUtil.addSegmentsEntry(group.getGroupId());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return SegmentsEntryLocalServiceUtil.getSegmentsEntryByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return SegmentsEntry.class;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		SegmentsEntry segmentsEntry = (SegmentsEntry)stagedModel;
		SegmentsEntry importedSegmentsEntry =
			(SegmentsEntry)importedStagedModel;

		Assert.assertEquals(
			segmentsEntry.getSegmentsEntryKey(),
			importedSegmentsEntry.getSegmentsEntryKey());
		Assert.assertEquals(
			segmentsEntry.getName(), importedSegmentsEntry.getName());
		Assert.assertEquals(
			segmentsEntry.getDescription(),
			importedSegmentsEntry.getDescription());
		Assert.assertEquals(
			segmentsEntry.isActive(), importedSegmentsEntry.isActive());
		Assert.assertEquals(
			segmentsEntry.getCriteria(), importedSegmentsEntry.getCriteria());
		Assert.assertEquals(
			segmentsEntry.getSource(), importedSegmentsEntry.getSource());
	}

	private ExpandoColumn _addExpandoColumn(
			ExpandoTable expandoTable, String columnName, int columnType,
			int indexType)
		throws Exception {

		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			expandoTable, columnName, columnType);

		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		unicodeProperties.setProperty(
			ExpandoColumnConstants.INDEX_TYPE, String.valueOf(indexType));

		expandoColumn.setTypeSettingsProperties(unicodeProperties);

		return ExpandoColumnLocalServiceUtil.updateExpandoColumn(expandoColumn);
	}

	private String _encodeName(ExpandoColumn expandoColumn) {
		return StringBundler.concat(
			StringPool.UNDERLINE, expandoColumn.getColumnId(),
			StringPool.UNDERLINE,
			Normalizer.normalizeIdentifier(expandoColumn.getName()));
	}

	private String _getCriteria(String filterString) {
		Criteria criteria = new Criteria();

		_userSegmentsCriteriaContributor.contribute(
			criteria, filterString, Criteria.Conjunction.AND);

		return CriteriaSerializer.serialize(criteria);
	}

	private String _getFilterString(Criteria criteria) {
		Criteria.Criterion criterion =
			_userSegmentsCriteriaContributor.getCriterion(criteria);

		return criterion.getFilterString();
	}

	@DeleteAfterTestRun
	private ExpandoTable _expandoTable;

	private StagedModelRepository<StagedExpandoColumn> _stagedModelRepository;

	@Inject(
		filter = "segments.criteria.contributor.key=user",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _userSegmentsCriteriaContributor;

}