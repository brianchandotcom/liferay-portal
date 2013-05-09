/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ExportImportUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portlet.dynamicdatamapping.TemplateDuplicateTemplateKeyException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMTemplateUtil;

import java.io.File;

import java.util.Map;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public class DDMTemplateStagedModelDataHandler
	extends BaseStagedModelDataHandler<DDMTemplate> {

	public static final String[] CLASS_NAMES = {DDMTemplate.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	protected DDMTemplate addTemplate(
			long userId, long groupId, DDMTemplate template, long classPK,
			File smallFile, ServiceContext serviceContext)
		throws Exception {

		DDMTemplate newTemplate = null;

		try {
			return DDMTemplateLocalServiceUtil.addTemplate(
				userId, groupId, template.getClassNameId(), classPK,
				template.getTemplateKey(), template.getNameMap(),
				template.getDescriptionMap(), template.getType(),
				template.getMode(), template.getLanguage(),
				template.getScript(), template.isCacheable(),
				template.isSmallImage(), template.getSmallImageURL(), smallFile,
				serviceContext);
		}
		catch (TemplateDuplicateTemplateKeyException tdtke) {
			newTemplate = DDMTemplateLocalServiceUtil.addTemplate(
				userId, groupId, template.getClassNameId(), classPK, null,
				template.getNameMap(), template.getDescriptionMap(),
				template.getType(), template.getMode(), template.getLanguage(),
				template.getScript(), template.isCacheable(),
				template.isSmallImage(), template.getSmallImageURL(), smallFile,
				serviceContext);

			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(4);

				sb.append("A template with the key ");
				sb.append(template.getTemplateKey());
				sb.append(" already exists. The new generated key is ");
				sb.append(newTemplate.getTemplateKey());

				_log.warn(sb.toString());
			}
		}

		return newTemplate;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, DDMTemplate template)
		throws Exception {

		DDMStructure structure = DDMStructureLocalServiceUtil.fetchStructure(
			template.getClassPK());

		if (structure != null) {
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, structure);
		}

		Element templateElement = portletDataContext.getExportDataElement(
			template);

		if (template.isSmallImage()) {
			Image smallImage = ImageUtil.fetchByPrimaryKey(
				template.getSmallImageId());

			if (Validator.isNotNull(template.getSmallImageURL())) {
				String smallImageURL =
					ExportImportUtil.exportContentReferences(
						portletDataContext, template, templateElement,
						template.getSmallImageURL().concat(StringPool.SPACE));

				template.setSmallImageURL(smallImageURL);
			}
			else if (smallImage != null) {
				String smallImagePath = ExportImportPathUtil.getModelPath(
					template, smallImage.getImageId() + StringPool.PERIOD +
						template.getSmallImageType());

				templateElement.addAttribute(
					"small-image-path", smallImagePath);

				template.setSmallImageType(smallImage.getType());

				portletDataContext.addZipEntry(
					smallImagePath, smallImage.getTextObj());
			}
		}

		if (portletDataContext.getBooleanParameter(
				DDMPortletDataHandler.NAMESPACE, "embedded-assets")) {

			String content = ExportImportUtil.exportContentReferences(
				portletDataContext, template, templateElement,
				template.getScript());

			template.setScript(content);
		}

		portletDataContext.addClassedModel(
			templateElement, ExportImportPathUtil.getModelPath(template),
			template, DDMPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DDMTemplate template)
		throws Exception {

		long userId = portletDataContext.getUserId(template.getUserUuid());

		Map<Long, Long> structureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		String structurePath = ExportImportPathUtil.getModelPath(
			portletDataContext, DDMStructure.class.getName(),
			template.getClassPK());

		DDMStructure structure =
			(DDMStructure)portletDataContext.getZipEntryAsObject(structurePath);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, structure);

		long classPK = MapUtil.getLong(
			structureIds, template.getClassPK(), template.getClassPK());

		File smallFile = null;

		if (template.isSmallImage()) {
			Element element =
				portletDataContext.getImportDataStagedModelElement(template);

			String smallImagePath = element.attributeValue("small-image-path");

			if (Validator.isNotNull(template.getSmallImageURL())) {
				String smallImageURL =
					ExportImportUtil.importContentReferences(
						portletDataContext, element,
						template.getSmallImageURL());

				template.setSmallImageURL(smallImageURL);
			}
			else if (Validator.isNotNull(smallImagePath)) {
				byte[] bytes = portletDataContext.getZipEntryAsByteArray(
					smallImagePath);

				if (bytes != null) {
					smallFile = FileUtil.createTempFile(
						template.getSmallImageType());

					FileUtil.write(smallFile, bytes);
				}
			}
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			template, DDMPortletDataHandler.NAMESPACE);

		DDMTemplate importedTemplate = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DDMTemplate existingTemplate = DDMTemplateUtil.fetchByUUID_G(
				template.getUuid(), portletDataContext.getScopeGroupId());

			if (existingTemplate == null) {
				serviceContext.setUuid(template.getUuid());

				importedTemplate = addTemplate(
					userId, portletDataContext.getScopeGroupId(), template,
					classPK, smallFile, serviceContext);
			}
			else {
				importedTemplate = DDMTemplateLocalServiceUtil.updateTemplate(
					existingTemplate.getTemplateId(), template.getNameMap(),
					template.getDescriptionMap(), template.getType(),
					template.getMode(), template.getLanguage(),
					template.getScript(), template.isCacheable(),
					template.isSmallImage(), template.getSmallImageURL(),
					smallFile, serviceContext);
			}
		}
		else {
			importedTemplate = addTemplate(
				userId, portletDataContext.getScopeGroupId(), template, classPK,
				smallFile, serviceContext);
		}

		portletDataContext.importClassedModel(
			template, importedTemplate, DDMPortletDataHandler.NAMESPACE);

		Map<String, String> ddmTemplateKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class + ".ddmTemplateKey");

		ddmTemplateKeys.put(
			template.getTemplateKey(), importedTemplate.getTemplateKey());
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMTemplateStagedModelDataHandler.class);

}