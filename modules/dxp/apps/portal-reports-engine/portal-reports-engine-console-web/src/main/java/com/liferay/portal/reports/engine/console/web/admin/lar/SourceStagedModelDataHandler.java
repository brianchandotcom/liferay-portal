/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.web.admin.lar;

import com.liferay.exportimport.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.reports.engine.console.model.Source;
import com.liferay.portal.reports.engine.console.service.SourceLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class SourceStagedModelDataHandler
	extends BaseStagedModelDataHandler<Source> {

	public static final String[] CLASS_NAMES = {Source.class.getName()};

	@Override
	public void deleteStagedModel(Source source) throws PortalException {
		_sourceLocalService.deleteSource(source);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Source source = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (source != null) {
			deleteStagedModel(source);
		}
	}

	@Override
	public Source fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		return _sourceLocalService.fetchSourceByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<Source> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _sourceLocalService.getSourcesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<Source>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(Source source) {
		return source.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Source source)
		throws Exception {

		Element sourceElement = portletDataContext.getExportDataElement(source);

		portletDataContext.addClassedModel(
			sourceElement, ExportImportPathUtil.getModelPath(source), source);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Source source)
		throws Exception {

		long userId = portletDataContext.getUserId(source.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			source);

		Source importedSource = null;

		if (portletDataContext.isDataStrategyMirror()) {
			Source existingSource = fetchStagedModelByUuidAndGroupId(
				source.getUuid(), portletDataContext.getScopeGroupId());

			if (existingSource == null) {
				serviceContext.setUuid(source.getUuid());

				importedSource = _sourceLocalService.addSource(
					userId, portletDataContext.getScopeGroupId(),
					source.getNameMap(), source.getDriverClassName(),
					source.getDriverUrl(), source.getDriverUserName(),
					source.getDriverPassword(), serviceContext);
			}
			else {
				importedSource = _sourceLocalService.updateSource(
					existingSource.getSourceId(), source.getNameMap(),
					source.getDriverClassName(), source.getDriverUrl(),
					source.getDriverUserName(), source.getDriverPassword(),
					serviceContext);
			}
		}
		else {
			importedSource = _sourceLocalService.addSource(
				userId, portletDataContext.getScopeGroupId(),
				source.getNameMap(), source.getDriverClassName(),
				source.getDriverUrl(), source.getDriverUserName(),
				source.getDriverPassword(), serviceContext);
		}

		portletDataContext.importClassedModel(source, importedSource);
	}

	@Reference
	private SourceLocalService _sourceLocalService;

}