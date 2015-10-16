package ${packagePath}.service.persistence;

import ${packagePath}.model.${entity.name};
import ${packagePath}.service.${entity.name}LocalServiceUtil;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.exportimport.lar.ExportImportHelperUtil;
import com.liferay.portlet.exportimport.lar.ManifestSummary;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;

/**
 * @author ${author}
 * @deprecated As of 7.0.0, replaced by {@link ${packagePath}.service.${entity.name}LocalServiceUtil#getExportActionableDynamicQuery(PortletDataContext)}
 * @generated
 */
@Deprecated
@ProviderType
public class ${entity.name}ExportActionableDynamicQuery extends ${entity.name}ActionableDynamicQuery {

	public ${entity.name}ExportActionableDynamicQuery(PortletDataContext portletDataContext) {
		_portletDataContext = portletDataContext;

		setCompanyId(_portletDataContext.getCompanyId());

		<#if entity.isStagedGroupedModel()>
			setGroupId(_portletDataContext.getScopeGroupId());
		</#if>
	}

	@Override
	public long performCount() throws PortalException {
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
		_portletDataContext.addDateRangeCriteria(dynamicQuery, "modifiedDate");

		<#if entity.isTypedModel()>
			if (getStagedModelType().getReferrerClassNameId() >= 0) {
				Property classNameIdProperty = PropertyFactoryUtil.forName("classNameId");

				dynamicQuery.add(classNameIdProperty.eq(getStagedModelType().getReferrerClassNameId()));
			}
		</#if>

		<#if entity.isWorkflowEnabled()>
			StagedModelDataHandler<?> stagedModelDataHandler = StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(${entity.name}.class.getName());

			Property workflowStatusProperty = PropertyFactoryUtil.forName("status");

			dynamicQuery.add(workflowStatusProperty.in(stagedModelDataHandler.getExportableStatuses()));
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
	protected void performAction(Object object) throws PortalException {
		${entity.name} stagedModel = (${entity.name})object;

		StagedModelDataHandlerUtil.exportStagedModel(_portletDataContext, stagedModel);
	}

	private PortletDataContext _portletDataContext;

}