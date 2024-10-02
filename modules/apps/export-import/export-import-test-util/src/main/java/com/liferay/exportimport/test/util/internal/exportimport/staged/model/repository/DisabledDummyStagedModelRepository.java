/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.exportimport.test.util.model.DisabledDummy;
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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(
	property = "model.class.name=com.liferay.exportimport.test.util.model.DisabledDummy",
	service = StagedModelRepository.class
)
public class DisabledDummyStagedModelRepository
	implements StagedModelRepository<DisabledDummy> {

	@Override
	public DisabledDummy addStagedModel(
			PortletDataContext portletDataContext, DisabledDummy disabledDummy1)
		throws PortalException {

		DisabledDummy disabledDummy2 = new DisabledDummy();

		disabledDummy1.setId(disabledDummy2.getId());

		if ((portletDataContext != null) &&
			(portletDataContext.getUserIdStrategy() != null)) {

			disabledDummy1.setUserId(
				portletDataContext.getUserId(disabledDummy1.getUserUuid()));
		}

		_disabledDummies.add(disabledDummy1);

		return disabledDummy1;
	}

	@Override
	public void deleteStagedModel(DisabledDummy disabledDummy)
		throws PortalException {

		if (_disabledDummies.remove(disabledDummy)) {
			systemEventLocalService.addSystemEvent(
				0, disabledDummy.getGroupId(),
				disabledDummy.getModelClassName(),
				disabledDummy.getPrimaryKey(), disabledDummy.getUuid(),
				StringPool.BLANK, SystemEventConstants.TYPE_DELETE,
				StringPool.BLANK);
		}
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_disabledDummies.removeIf(
			disabledDummy ->
				Objects.equals(disabledDummy.getUuid(), uuid) &&
				(disabledDummy.getGroupId() == groupId));
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_disabledDummies.clear();
	}

	public DisabledDummy fetchDisabledDummyById(long id) {
		for (DisabledDummy disabledDummy : _disabledDummies) {
			if (id == disabledDummy.getId()) {
				return disabledDummy;
			}
		}

		return null;
	}

	public List<DisabledDummy> fetchDummiesByFolderId(long folderId) {
		return ListUtil.filter(
			_disabledDummies,
			disabledDummy -> folderId == disabledDummy.getFolderId());
	}

	@Override
	public DisabledDummy fetchMissingReference(String uuid, long groupId) {
		return fetchStagedModelByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public DisabledDummy fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		for (DisabledDummy disabledDummy : _disabledDummies) {
			if (Objects.equals(uuid, disabledDummy.getUuid()) &&
				(groupId == disabledDummy.getGroupId())) {

				return disabledDummy;
			}
		}

		return null;
	}

	@Override
	public List<DisabledDummy> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return ListUtil.filter(
			_disabledDummies,
			disabledDummy ->
				Objects.equals(uuid, disabledDummy.getUuid()) &&
				(companyId == disabledDummy.getCompanyId()));
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

					long modelAdditionCount = _disabledDummies.size();

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
			new DisabledDummyBaseLocalServiceImpl());

		Class<?> clazz = getClass();

		exportActionableDynamicQuery.setClassLoader(clazz.getClassLoader());

		exportActionableDynamicQuery.setModelClass(DisabledDummy.class);
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
							getStagedModelDataHandler(
								DisabledDummy.class.getName());

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
			(DisabledDummy disabledDummy) ->
				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, disabledDummy));
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				portal.getClassNameId(DisabledDummy.class.getName()),
				StagedModelType.REFERRER_CLASS_NAME_ID_ALL));

		return exportActionableDynamicQuery;
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, DisabledDummy stagedModel)
		throws PortletDataException {
	}

	@Override
	public DisabledDummy saveStagedModel(DisabledDummy disabledDummy)
		throws PortalException {

		deleteStagedModel(disabledDummy);

		addStagedModel(null, disabledDummy);

		return disabledDummy;
	}

	@Override
	public DisabledDummy updateStagedModel(
			PortletDataContext portletDataContext, DisabledDummy disabledDummy)
		throws PortalException {

		DisabledDummy existingDisabledDummy = _fetchDisabledDummy(
			disabledDummy);

		existingDisabledDummy.setUserId(
			portletDataContext.getUserId(disabledDummy.getUserUuid()));

		return disabledDummy;
	}

	public class DisabledDummyBaseLocalServiceImpl
		extends BaseLocalServiceImpl {

		public List<DisabledDummy> dynamicQuery(DynamicQuery dynamicQuery) {
			try {
				Object detachedCriteria = ReflectionTestUtil.getFieldValue(
					dynamicQuery, "_detachedCriteria");

				Object criteriaImpl = ReflectionTestUtil.invoke(
					detachedCriteria, "getCriteriaImpl", new Class<?>[0]);

				Iterator<?> iterator = ReflectionTestUtil.invoke(
					criteriaImpl, "iterateExpressionEntries", new Class<?>[0]);

				if (!iterator.hasNext()) {
					return _disabledDummies;
				}

				Predicate<DisabledDummy> predicate = getPredicate(
					String.valueOf(iterator.next()));

				while (iterator.hasNext()) {
					predicate = predicate.and(
						getPredicate(String.valueOf(iterator.next())));
				}

				return ListUtil.filter(_disabledDummies, predicate);
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		public long dynamicQueryCount(
			DynamicQuery dynamicQuery, Projection projection) {

			return _disabledDummies.size();
		}

		public Predicate<DisabledDummy> getPredicate(String expression) {
			if (expression.startsWith("groupId=")) {
				return disabledDummy ->
					disabledDummy.getGroupId() == Long.valueOf(
						expression.substring("groupId=".length()));
			}

			if (expression.contains("id>-1")) {
				return disabledDummy -> disabledDummy.getId() > -1;
			}

			if (expression.startsWith("companyId=")) {
				return disabledDummy ->
					disabledDummy.getCompanyId() == Long.valueOf(
						expression.substring("companyId=".length()));
			}

			return disabledDummy -> true;
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

	private DisabledDummy _fetchDisabledDummy(DisabledDummy disabledDummy)
		throws NoSuchModelException {

		int i = _disabledDummies.indexOf(disabledDummy);

		if (i < 0) {
			throw new NoSuchModelException();
		}

		return _disabledDummies.get(i);
	}

	private final List<DisabledDummy> _disabledDummies = new ArrayList<>();

}