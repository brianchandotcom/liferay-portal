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

package com.liferay.exportimport.test.util.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.test.util.model.Dummy;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.exportimport.test.util.model.Dummy",
	service = StagedModelRepository.class
)
public class DummyStagedModelRepository
	implements StagedModelRepository<Dummy> {

	@Override
	public Dummy addStagedModel(
			PortletDataContext portletDataContext, Dummy dummy1)
		throws PortalException {

		Dummy dummy2 = new Dummy();

		dummy1.setId(dummy2.getId());

		if ((portletDataContext != null) &&
			(portletDataContext.getUserIdStrategy() != null)) {

			dummy1.setUserId(
				portletDataContext.getUserId(dummy1.getUserUuid()));
		}

		_dummies.add(dummy1);

		return dummy1;
	}

	@Override
	public void deleteStagedModel(Dummy dummy) throws PortalException {
		if (_dummies.remove(dummy)) {
			systemEventLocalService.addSystemEvent(
				0, dummy.getGroupId(), dummy.getModelClassName(),
				dummy.getPrimaryKey(), dummy.getUuid(), StringPool.BLANK,
				SystemEventConstants.TYPE_DELETE, StringPool.BLANK);
		}
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_dummies.removeIf(
			dummy ->
				Objects.equals(dummy.getUuid(), uuid) &&
				(dummy.getGroupId() == groupId));
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_dummies.clear();
	}

	public List<Dummy> fetchDummiesByFolderId(long folderId) {
		Stream<Dummy> dummiesStream = _dummies.stream();

		return dummiesStream.filter(
			d -> d.getFolderId() == folderId
		).collect(
			Collectors.toList()
		);
	}

	public Dummy fetchDummyById(long id) {
		Stream<Dummy> dummiesStream = _dummies.stream();

		List<Dummy> dummies = dummiesStream.filter(
			d -> d.getId() == id
		).collect(
			Collectors.toList()
		);

		if (dummies.isEmpty()) {
			return null;
		}

		return dummies.get(0);
	}

	@Override
	public Dummy fetchMissingReference(String uuid, long groupId) {
		return fetchStagedModelByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public Dummy fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		Stream<Dummy> dummiesStream = _dummies.stream();

		List<Dummy> dummies = dummiesStream.filter(
			dummy ->
				Objects.equals(dummy.getUuid(), uuid) &&
				(dummy.getGroupId() == groupId)
		).collect(
			Collectors.toList()
		);

		if (dummies.isEmpty()) {
			return null;
		}

		return dummies.get(0);
	}

	@Override
	public List<Dummy> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		Stream<Dummy> dummiesStream = _dummies.stream();

		return dummiesStream.filter(
			dummy ->
				Objects.equals(dummy.getUuid(), uuid) &&
				(dummy.getCompanyId() == companyId)
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			new ExportActionableDynamicQuery() {

				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = _dummies.size();

					manifestSummary.addModelAdditionCount(
						stagedModelType, modelAdditionCount);

					manifestSummary.addModelDeletionCount(stagedModelType, 0);

					return modelAdditionCount;
				}

				@Override
				protected Projection getCountProjection() {
					return ProjectionFactoryUtil.countDistinct(
						"resourcePrimKey");
				}

			};

		exportActionableDynamicQuery.setBaseLocalService(
			new DummyBaseLocalServiceImpl());

		Class<?> clazz = getClass();

		exportActionableDynamicQuery.setClassLoader(clazz.getClassLoader());

		exportActionableDynamicQuery.setModelClass(Dummy.class);

		exportActionableDynamicQuery.setPrimaryKeyPropertyName("id");

		exportActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Criterion modifiedDateCriterion =
					portletDataContext.getDateRangeCriteria("modifiedDate");

				if (modifiedDateCriterion != null) {
					Conjunction conjunction =
						RestrictionsFactoryUtil.conjunction();

					conjunction.add(modifiedDateCriterion);

					Disjunction disjunction =
						RestrictionsFactoryUtil.disjunction();

					disjunction.add(
						RestrictionsFactoryUtil.gtProperty(
							"modifiedDate", "lastPublishDate"));

					Property lastPublishDateProperty =
						PropertyFactoryUtil.forName("lastPublishDate");

					disjunction.add(lastPublishDateProperty.isNull());

					conjunction.add(disjunction);

					modifiedDateCriterion = conjunction;
				}

				Criterion statusDateCriterion =
					portletDataContext.getDateRangeCriteria("statusDate");

				if ((modifiedDateCriterion != null) &&
					(statusDateCriterion != null)) {

					Disjunction disjunction =
						RestrictionsFactoryUtil.disjunction();

					disjunction.add(modifiedDateCriterion);
					disjunction.add(statusDateCriterion);

					dynamicQuery.add(disjunction);
				}

				StagedModelType stagedModelType =
					exportActionableDynamicQuery.getStagedModelType();

				long referrerClassNameId =
					stagedModelType.getReferrerClassNameId();

				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				if ((referrerClassNameId !=
						StagedModelType.REFERRER_CLASS_NAME_ID_ALL) &&
					(referrerClassNameId !=
						StagedModelType.REFERRER_CLASS_NAME_ID_ANY)) {

					dynamicQuery.add(
						classNameIdProperty.eq(
							stagedModelType.getReferrerClassNameId()));
				}
				else if (referrerClassNameId ==
							StagedModelType.REFERRER_CLASS_NAME_ID_ANY) {

					dynamicQuery.add(classNameIdProperty.isNotNull());
				}

				Property workflowStatusProperty = PropertyFactoryUtil.forName(
					"status");

				if (portletDataContext.isInitialPublication()) {
					dynamicQuery.add(
						workflowStatusProperty.ne(
							WorkflowConstants.STATUS_IN_TRASH));
				}
				else {
					StagedModelDataHandler<?> stagedModelDataHandler =
						StagedModelDataHandlerRegistryUtil.
							getStagedModelDataHandler(Dummy.class.getName());

					dynamicQuery.add(
						workflowStatusProperty.in(
							stagedModelDataHandler.getExportableStatuses()));
				}
			});

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(
			(Dummy dummy) -> StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, dummy));

		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				portal.getClassNameId(Dummy.class.getName()),
				StagedModelType.REFERRER_CLASS_NAME_ID_ALL));

		return exportActionableDynamicQuery;
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, Dummy stagedModel)
		throws PortletDataException {
	}

	@Override
	public Dummy saveStagedModel(Dummy dummy) throws PortalException {
		deleteStagedModel(dummy);

		addStagedModel(null, dummy);

		return dummy;
	}

	@Override
	public Dummy updateStagedModel(
			PortletDataContext portletDataContext, Dummy dummy)
		throws PortalException {

		Dummy existingDummy = _fetchDummy(dummy);

		existingDummy.setUserId(
			portletDataContext.getUserId(dummy.getUserUuid()));

		return dummy;
	}

	public class DummyBaseLocalServiceImpl extends BaseLocalServiceImpl {

		public List<Dummy> dynamicQuery(DynamicQuery dynamicQuery) {
			List<Dummy> result = _dummies;

			try {
				Object detachedCriteria = ReflectionTestUtil.getFieldValue(
					dynamicQuery, "_detachedCriteria");

				Object criteriaImpl = ReflectionTestUtil.invoke(
					detachedCriteria, "getCriteriaImpl", new Class<?>[0]);

				Iterator<?> iterator = ReflectionTestUtil.invoke(
					criteriaImpl, "iterateExpressionEntries", new Class<?>[0]);

				while (iterator.hasNext()) {
					Stream<Dummy> dummiesStream = result.stream();

					result = dummiesStream.filter(
						getPredicate(String.valueOf(iterator.next()))
					).collect(
						Collectors.toList()
					);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return result;
		}

		public long dynamicQueryCount(
			DynamicQuery dynamicQuery, Projection projection) {

			return _dummies.size();
		}

		public Predicate<? super Dummy> getPredicate(String expression) {
			if (expression.startsWith("groupId=")) {
				return d ->
					d.getGroupId() == Long.valueOf(
						expression.substring("groupId=".length()));
			}

			if (expression.contains("id>-1")) {
				return d -> d.getId() > -1;
			}

			if (expression.startsWith("companyId=")) {
				return d ->
					d.getCompanyId() == Long.valueOf(
						expression.substring("companyId=".length()));
			}

			return d -> true;
		}

		@Override
		protected ClassLoader getClassLoader() {
			return super.getClassLoader();
		}

		@Override
		protected Map<Locale, String> getLocalizationMap(String value) {
			return super.getLocalizationMap(value);
		}

	}

	@Reference
	protected Portal portal;

	@Reference
	protected SystemEventLocalService systemEventLocalService;

	private Dummy _fetchDummy(Dummy dummy) throws NoSuchModelException {
		int i = _dummies.indexOf(dummy);

		if (i < 0) {
			throw new NoSuchModelException();
		}

		return _dummies.get(i);
	}

	private final List<Dummy> _dummies = new ArrayList<>();

}