package ${packagePath}.service.persistence;

import ${packagePath}.model.${entity.name};
import ${packagePath}.service.${entity.name}LocalServiceUtil;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

/**
 * @author ${author}
 * @generated
 */
public class ${entity.name}ExportActionableDynamicQuery extends ${entity.name}ActionableDynamicQuery {

	public ${entity.name}ExportActionableDynamicQuery(PortletDataContext portletDataContext) throws SystemException {
		_portletDataContext = portletDataContext;

		setCompanyId(_portletDataContext.getCompanyId());

		<#if entity.isStagedGroupedModel()>
			setGroupId(_portletDataContext.getScopeGroupId());
		</#if>
	}

	@Override
	public long performCount() throws PortalException, SystemException {
		ManifestSummary manifestSummary = _portletDataContext.getManifestSummary();

		StagedModelType stagedModelType = getStagedModelType();

		long modelAdditionCount = super.performCount();

		manifestSummary.addModelAdditionCount(stagedModelType.toString(), modelAdditionCount);

		long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(_portletDataContext, stagedModelType);

		manifestSummary.addModelDeletionCount(stagedModelType.toString(), modelDeletionCount);

		return modelAdditionCount;
	}

	@Override
	protected void addCriteria(DynamicQuery dynamicQuery) {
		<#if entity.isWorkflowEnabled()>
			Criterion modifiedDateCriterion = _portletDataContext.getDateRangeCriteria("modifiedDate");
			Criterion statusDateCriterion = _portletDataContext.getDateRangeCriteria("statusDate");

			if ((modifiedDateCriterion != null) && (statusDateCriterion != null)) {
				Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

				disjunction.add(modifiedDateCriterion);
				disjunction.add(statusDateCriterion);

				dynamicQuery.add(disjunction);
			}
		<#else>
			_portletDataContext.addDateRangeCriteria(dynamicQuery, "modifiedDate");
		</#if>

		<#if entity.isTypedModel()>
			if (getStagedModelType().getReferrerClassNameId() >= 0) {
				Property classNameIdProperty = PropertyFactoryUtil.forName("classNameId");

				dynamicQuery.add(classNameIdProperty.eq(getStagedModelType().getReferrerClassNameId()));
			}
		</#if>

		<#if entity.isWorkflowEnabled()>
			Property workflowStatusProperty = PropertyFactoryUtil.forName("status");

			if (_portletDataContext.isInitialPublication()) {
				dynamicQuery.add(workflowStatusProperty.ne(WorkflowConstants.STATUS_IN_TRASH));
			}
			else {
				StagedModelDataHandler<?> stagedModelDataHandler = StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(${entity.name}.class.getName());

				dynamicQuery.add(workflowStatusProperty.in(stagedModelDataHandler.getExportableStatuses()));
			}
		</#if>
	}

	<#if entity.isResourcedModel()>
		@Override
		protected Projection getCountProjection() {
			return ProjectionFactoryUtil.countDistinct("resourcePrimKey");
		}
	</#if>

	protected StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(${entity.name}.class.getName()));
	}

	@Override
	@SuppressWarnings("unused")
	protected void performAction(Object object) throws PortalException, SystemException {
		${entity.name} stagedModel = (${entity.name})object;

		StagedModelDataHandlerUtil.exportStagedModel(_portletDataContext, stagedModel);
	}

	private PortletDataContext _portletDataContext;

}