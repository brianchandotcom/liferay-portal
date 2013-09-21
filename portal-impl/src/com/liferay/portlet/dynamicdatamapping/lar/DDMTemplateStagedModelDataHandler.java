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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
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
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.TemplateDuplicateTemplateKeyException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;

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
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException, SystemException {

		DDMTemplate ddmTemplate =
			DDMTemplateLocalServiceUtil.fetchDDMTemplateByUuidAndGroupId(
				uuid, groupId);

		if (ddmTemplate != null) {
			DDMTemplateLocalServiceUtil.deleteTemplate(ddmTemplate);
		}
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(DDMTemplate template) {
		return template.getNameCurrentValue();
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

		Element templateElement = portletDataContext.getExportDataElement(
			template);

		DDMStructure structure = DDMStructureLocalServiceUtil.fetchStructure(
			template.getClassPK());

		if (structure != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, template, structure,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}

		if (template.isSmallImage()) {
			Image smallImage = ImageLocalServiceUtil.fetchImage(
				template.getSmallImageId());

			if (Validator.isNotNull(template.getSmallImageURL())) {
				String smallImageURL =
					ExportImportHelperUtil.replaceExportContentReferences(
						portletDataContext, template, templateElement,
						template.getSmallImageURL().concat(StringPool.SPACE),
						true);

				template.setSmallImageURL(smallImageURL);
			}
			else if (smallImage != null) {
				String smallImagePath = ExportImportPathUtil.getModelPath(
					template,
					smallImage.getImageId() + StringPool.PERIOD +
						template.getSmallImageType());

				templateElement.addAttribute(
					"small-image-path", smallImagePath);

				template.setSmallImageType(smallImage.getType());

				portletDataContext.addZipEntry(
					smallImagePath, smallImage.getTextObj());
			}
		}

		if (portletDataContext.getBooleanParameter(
				DDMPortletDataHandler.NAMESPACE, "referenced-content")) {

			String content =
				ExportImportHelperUtil.replaceExportContentReferences(
					portletDataContext, template, templateElement,
					template.getScript(), true);

			template.setScript(content);
		}

		portletDataContext.addClassedModel(
			templateElement, ExportImportPathUtil.getModelPath(template),
			template);
	}

	@Override
	protected void doImportCompanyStagedModel(
			PortletDataContext portletDataContext, DDMTemplate template)
		throws Exception {

		DDMTemplate existingTemplate =
			DDMTemplateLocalServiceUtil.fetchDDMTemplateByUuidAndGroupId(
				template.getUuid(), portletDataContext.getCompanyGroupId());

		Map<Long, Long> templateIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class);

		templateIds.put(
			template.getTemplateId(), existingTemplate.getTemplateId());

		Map<String, String> templateKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class + ".ddmTemplateKey");

		templateKeys.put(
			template.getTemplateKey(), existingTemplate.getTemplateKey());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DDMTemplate template)
		throws Exception {

		long userId = portletDataContext.getUserId(template.getUserUuid());

		long classPK = template.getClassPK();

		Element structureElement = portletDataContext.getReferenceDataElement(
			template, DDMStructure.class, classPK);

		if (structureElement != null) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, structureElement);

			Map<Long, Long> structureIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DDMStructure.class);

			classPK = MapUtil.getLong(structureIds, classPK, classPK);
		}

		File smallFile = null;

		try {
			if (template.isSmallImage()) {
				Element element =
					portletDataContext.getImportDataStagedModelElement(
						template);

				String smallImagePath = element.attributeValue(
					"small-image-path");

				if (Validator.isNotNull(template.getSmallImageURL())) {
					String smallImageURL =
						ExportImportHelperUtil.replaceImportContentReferences(
							portletDataContext, element,
							template.getSmallImageURL(), true);

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

			ServiceContext serviceContext =
				portletDataContext.createServiceContext(template);

			DDMTemplate importedTemplate = null;

			if (portletDataContext.isDataStrategyMirror()) {
				DDMTemplate existingTemplate =
					DDMTemplateLocalServiceUtil.
						fetchDDMTemplateByUuidAndGroupId(
							template.getUuid(),
							portletDataContext.getScopeGroupId());

				if (existingTemplate == null) {
					serviceContext.setUuid(template.getUuid());

					importedTemplate = addTemplate(
						userId, portletDataContext.getScopeGroupId(), template,
						classPK, smallFile, serviceContext);
				}
				else {
					importedTemplate =
						DDMTemplateLocalServiceUtil.updateTemplate(
							existingTemplate.getTemplateId(),
							template.getClassPK(), template.getNameMap(),
							template.getDescriptionMap(), template.getType(),
							template.getMode(), template.getLanguage(),
							template.getScript(), template.isCacheable(),
							template.isSmallImage(),
							template.getSmallImageURL(), smallFile,
							serviceContext);
				}
			}
			else {
				importedTemplate = addTemplate(
					userId, portletDataContext.getScopeGroupId(), template,
					classPK, smallFile, serviceContext);
			}

			portletDataContext.importClassedModel(template, importedTemplate);

			Map<String, String> ddmTemplateKeys =
				(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
					DDMTemplate.class + ".ddmTemplateKey");

			ddmTemplateKeys.put(
				template.getTemplateKey(), importedTemplate.getTemplateKey());
		}
		finally {
			if (smallFile != null) {
				smallFile.delete();
			}
		}
	}

	@Override
	protected boolean validateMissingReference(
			String uuid, long companyId, long groupId)
		throws Exception {

		DDMTemplate template =
			DDMTemplateLocalServiceUtil.fetchDDMTemplateByUuidAndGroupId(
				uuid, groupId);

		if (template == null) {
			return false;
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMTemplateStagedModelDataHandler.class);

}