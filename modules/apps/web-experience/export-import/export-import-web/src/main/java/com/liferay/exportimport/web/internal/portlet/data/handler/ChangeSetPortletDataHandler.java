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

package com.liferay.exportimport.web.internal.portlet.data.handler;

import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryRegistryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + ExportImportPortletKeys.CHANGE_SET},
	service = PortletDataHandler.class
)
public class ChangeSetPortletDataHandler extends BasePortletDataHandler {

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Activate
	protected void activate() {
		setDataLevel(DataLevel.PORTAL);
	}

	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		String[] classNameClassPKArray = parameterMap.get("classNameClassPK");

		if (classNameClassPKArray != null) {
			for (String classNameClassPK : classNameClassPKArray) {
				String className = _getClassName(classNameClassPK);
				long classPK = _getClassPK(classNameClassPK);

				_exportEntity(portletDataContext, className, classPK);
			}
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		Element importDataRootElement =
			portletDataContext.getImportDataRootElement();

		List<Element> entityTypeElements = importDataRootElement.elements();

		for (Element entityTypeElement : entityTypeElements) {
			List<Element> entityElements = entityTypeElement.elements();

			for (Element entityElement : entityElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, entityElement);
			}
		}

		return portletPreferences;
	}

	private void _exportEntity(
			PortletDataContext portletDataContext, String className,
			long classPK)
		throws PortalException {

		StagedModelRepository<?> stagedModelRepository =
			StagedModelRepositoryRegistryUtil.getStagedModelRepository(
				className);

		StagedModel stagedModel =
			stagedModelRepository.fetchStagedModelByClassPK(classPK);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, stagedModel);
	}

	private String _getClassName(String classNameClassPK) {
		return classNameClassPK.substring(
			0, classNameClassPK.indexOf(StringPool.POUND));
	}

	private long _getClassPK(String classNameClassPK) {
		return Long.valueOf(
			classNameClassPK.substring(
				classNameClassPK.indexOf(StringPool.POUND) + 1));
	}

}