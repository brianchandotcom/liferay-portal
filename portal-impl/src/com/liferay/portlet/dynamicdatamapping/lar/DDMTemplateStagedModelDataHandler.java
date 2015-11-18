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
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Locale;
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

	@Override
	public Map<String, String> getReferenceAttributes(
		PortletDataContext portletDataContext, DDMTemplate template) {

		Map<String, String> referenceAttributes = new HashMap<String, String>();

		referenceAttributes.put(
			"referenced-class-name", template.getClassName());
		referenceAttributes.put("template-key", template.getTemplateKey());

		long defaultUserId = 0;

		try {
			defaultUserId = UserLocalServiceUtil.getDefaultUserId(
				template.getCompanyId());
		}
		catch (Exception e) {
			return referenceAttributes;
		}

		boolean preloaded = false;

		if (defaultUserId == template.getUserId()) {
			preloaded = true;
		}

		referenceAttributes.put("preloaded", String.valueOf(preloaded));

		return referenceAttributes;
	}

	@Override
	public void importCompanyStagedModel(
			PortletDataContext portletDataContext, Element element)
		throws PortletDataException {

		String uuid = element.attributeValue("uuid");
		long classNameId = PortalUtil.getClassNameId(
			element.attributeValue("referenced-class-name"));
		String templateKey = element.attributeValue("template-key");
		boolean preloaded = GetterUtil.getBoolean(
			element.attributeValue("preloaded"));

		DDMTemplate existingTemplate = null;

		try {
			existingTemplate = getExistingTemplate(
				uuid, portletDataContext.getCompanyGroupId(), classNameId,
				templateKey, preloaded);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}

		Map<Long, Long> templateIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class);

		long templateId = GetterUtil.getLong(
			element.attributeValue("class-pk"));

		templateIds.put(templateId, existingTemplate.getTemplateId());

		Map<String, String> templateKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class + ".ddmTemplateKey");

		templateKeys.put(templateKey, existingTemplate.getTemplateKey());
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		String uuid = referenceElement.attributeValue("uuid");
		long classNameId = PortalUtil.getClassNameId(
			referenceElement.attributeValue("referenced-class-name"));
		String templateKey = referenceElement.attributeValue("template-key");
		boolean preloaded = GetterUtil.getBoolean(
			referenceElement.attributeValue("preloaded"));

		try {
			DDMTemplate existingTemplate = getExistingTemplate(
				uuid, portletDataContext.getScopeGroupId(), classNameId,
				templateKey, preloaded);

			if (existingTemplate == null) {
				existingTemplate = getExistingTemplate(
					uuid, portletDataContext.getCompanyGroupId(), classNameId,
					templateKey, preloaded);
			}

			if (existingTemplate == null) {
				return false;
			}

			return true;
		}
		catch (Exception e) {
			return false;
		}
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

		String script = ExportImportHelperUtil.replaceExportContentReferences(
			portletDataContext, template, templateElement, template.getScript(),
			portletDataContext.getBooleanParameter(
				DDMPortletDataHandler.NAMESPACE, "referenced-content"));

		template.setScript(script);

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			template.getCompanyId());

		if (defaultUserId == template.getUserId()) {
			templateElement.addAttribute("preloaded", "true");
		}

		portletDataContext.addClassedModel(
			templateElement, ExportImportPathUtil.getModelPath(template),
			template);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DDMTemplate template)
		throws Exception {

		prepareLanguagesForImport(template);

		long userId = portletDataContext.getUserId(template.getUserUuid());

		long classPK = template.getClassPK();

		if (classPK > 0) {
			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, template, DDMStructure.class, classPK);

			Map<Long, Long> structureIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DDMStructure.class);

			classPK = MapUtil.getLong(structureIds, classPK, classPK);
		}

		File smallFile = null;

		try {
			Element templateElement =
				portletDataContext.getImportDataStagedModelElement(template);

			if (template.isSmallImage()) {
				String smallImagePath = templateElement.attributeValue(
					"small-image-path");

				if (Validator.isNotNull(template.getSmallImageURL())) {
					String smallImageURL =
						ExportImportHelperUtil.replaceImportContentReferences(
							portletDataContext, templateElement,
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

			String script =
				ExportImportHelperUtil.replaceImportContentReferences(
					portletDataContext, templateElement, template.getScript(),
					true);

			template.setScript(script);

			ServiceContext serviceContext =
				portletDataContext.createServiceContext(template);

			DDMTemplate importedTemplate = null;

			if (portletDataContext.isDataStrategyMirror()) {
				boolean preloaded = GetterUtil.getBoolean(
					templateElement.attributeValue("preloaded"));

				DDMTemplate existingTemplate = getExistingTemplate(
					template.getUuid(), portletDataContext.getScopeGroupId(),
					template.getClassNameId(), template.getTemplateKey(),
					preloaded);

				if (existingTemplate == null) {
					serviceContext.setUuid(template.getUuid());

					importedTemplate = DDMTemplateLocalServiceUtil.addTemplate(
						userId, portletDataContext.getScopeGroupId(),
						template.getClassNameId(), classPK,
						template.getTemplateKey(), template.getNameMap(),
						template.getDescriptionMap(), template.getType(),
						template.getMode(), template.getLanguage(),
						template.getScript(), template.isCacheable(),
						template.isSmallImage(), template.getSmallImageURL(),
						smallFile, serviceContext);
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
				importedTemplate = DDMTemplateLocalServiceUtil.addTemplate(
					userId, portletDataContext.getScopeGroupId(),
					template.getClassNameId(), classPK, null,
					template.getNameMap(), template.getDescriptionMap(),
					template.getType(), template.getMode(),
					template.getLanguage(), template.getScript(),
					template.isCacheable(), template.isSmallImage(),
					template.getSmallImageURL(), smallFile, serviceContext);
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

	protected DDMTemplate getExistingTemplate(
			String uuid, long groupId, long classNameId, String templateKey,
			boolean preloaded)
		throws Exception {

		DDMTemplate existingTemplate = null;

		if (!preloaded) {
			existingTemplate =
				DDMTemplateLocalServiceUtil.fetchDDMTemplateByUuidAndGroupId(
					uuid, groupId);
		}
		else {
			existingTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(
				groupId, classNameId, templateKey);
		}

		return existingTemplate;
	}

	protected void prepareLanguagesForImport(DDMTemplate template)
		throws PortalException {

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			template.getDefaultLanguageId());

		Locale[] availableLocales = LocaleUtil.fromLanguageIds(
			template.getAvailableLanguageIds());

		Locale defaultImportLocale = LocalizationUtil.getDefaultImportLocale(
			DDMTemplate.class.getName(), template.getPrimaryKey(),
			defaultLocale, availableLocales);

		template.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

}