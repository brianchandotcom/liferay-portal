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

package com.liferay.exportimport.portlet.preferences.processor.capability;

import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	service = {Capability.class, ReferencedStagedModelImporterCapability.class}
)
public class ReferencedStagedModelImporterCapability implements Capability {

	@Override
	public PortletPreferences process(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		Element importDataRootElement =
			portletDataContext.getImportDataRootElement();

		Element referencesElement = importDataRootElement.element("references");

		if (referencesElement == null) {
			return portletPreferences;
		}

		List<Element> referenceElements = referencesElement.elements();

		long oldScopeGroupId = portletDataContext.getScopeGroupId();

		for (Element referenceElement : referenceElements) {
			try {
				String className = referenceElement.attributeValue(
					"class-name");
				long classPK = GetterUtil.getLong(
					referenceElement.attributeValue("class-pk"));

				String scopeLayoutUuid = GetterUtil.getString(
					referenceElement.attributeValue("scope-layout-uuid"));

				if (Validator.isNotNull(scopeLayoutUuid)) {
					try {
						Layout scopeLayout =
							_layoutLocalService.getLayoutByUuidAndGroupId(
								scopeLayoutUuid,
								portletDataContext.getGroupId(),
								portletDataContext.isPrivateLayout());

						Group scopeGroup = _layoutLocalService.checkScopeGroup(
							scopeLayout, portletDataContext.getUserId(null));

						portletDataContext.setScopeGroupId(
							scopeGroup.getGroupId());
					}
					catch (NoSuchLayoutException nsle) {
						if (_log.isInfoEnabled()) {
							StringBundler sb = new StringBundler(9);

							sb.append("Unable to find layout in group ");
							sb.append(portletDataContext.getGroupId());
							sb.append(" with UUID ");
							sb.append(scopeLayoutUuid);
							sb.append(
								" therefore the layout scoped element with " +
									"className ");
							sb.append(className);
							sb.append(" and classPK ");
							sb.append(classPK);
							sb.append(" cannot be imported");

							_log.info(sb.toString());
						}

						continue;
					}
					catch (PortalException pe) {
						throw new PortletDataException(pe);
					}
				}

				StagedModelDataHandlerUtil.importReferenceStagedModel(
					portletDataContext, className, classPK);
			}
			finally {
				portletDataContext.setScopeGroupId(oldScopeGroupId);
			}
		}

		return portletPreferences;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReferencedStagedModelImporterCapability.class);

	private LayoutLocalService _layoutLocalService;

}