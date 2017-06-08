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

package com.liferay.exportimport.kernel.lar;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.spring.orm.LastSessionRecorderHelperUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Mate Thurzo
 */
@ProviderType
public class StagedModelDataHandlerUtil {

	public static void deleteStagedModel(
			PortletDataContext portletDataContext, Element deletionElement)
		throws PortalException {

		String className = deletionElement.attributeValue("class-name");
		String extraData = deletionElement.attributeValue("extra-data");
		String uuid = deletionElement.attributeValue("uuid");

		StagedModelDataHandler<?> stagedModelDataHandler =
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				className);

		if (stagedModelDataHandler != null) {
			stagedModelDataHandler.deleteStagedModel(
				uuid, portletDataContext.getScopeGroupId(), className,
				extraData);
		}
	}

	public static <T extends StagedModel> Element exportReferenceStagedModel(
			PortletDataContext portletDataContext, String referrerPortletId,
			T stagedModel)
		throws PortletDataException {

		Portlet referrerPortlet = PortletLocalServiceUtil.getPortletById(
			referrerPortletId);

		if (!ExportImportHelperUtil.isReferenceWithinExportScope(
				portletDataContext, stagedModel)) {

			return portletDataContext.addReferenceElement(
				referrerPortlet, portletDataContext.getExportDataRootElement(),
				stagedModel, PortletDataContext.REFERENCE_TYPE_DEPENDENCY,
				true);
		}

		exportStagedModel(portletDataContext, stagedModel);

		return portletDataContext.addReferenceElement(
			referrerPortlet, portletDataContext.getExportDataRootElement(),
			stagedModel, PortletDataContext.REFERENCE_TYPE_DEPENDENCY, false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #exportReferenceStagedModel(PortletDataContext, StagedModel,
	 *             StagedModel, String)}
	 */
	@Deprecated
	public static <T extends StagedModel, U extends StagedModel> Element
			exportReferenceStagedModel(
				PortletDataContext portletDataContext, T referrerStagedModel,
				Class<?> referrerStagedModelClass, U stagedModel,
				Class<?> stagedModelClass, String referenceType)
		throws PortletDataException {

		return exportReferenceStagedModel(
			portletDataContext, referrerStagedModel, stagedModel,
			referenceType);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #exportReferenceStagedModel(PortletDataContext, StagedModel,
	 *             StagedModel, String)}
	 */
	@Deprecated
	public static <T extends StagedModel, U extends StagedModel> Element
			exportReferenceStagedModel(
				PortletDataContext portletDataContext, T referrerStagedModel,
				Element referrerStagedModelElement, U stagedModel,
				Class<?> stagedModelClass, String referenceType)
		throws PortletDataException {

		return exportReferenceStagedModel(
			portletDataContext, referrerStagedModel, stagedModel,
			referenceType);
	}

	public static <T extends StagedModel, U extends StagedModel> Element
			exportReferenceStagedModel(
				PortletDataContext portletDataContext, T referrerStagedModel,
				U stagedModel, String referenceType)
		throws PortletDataException {

		Element referrerStagedModelElement =
			portletDataContext.getExportDataElement(referrerStagedModel);

		if (!ExportImportHelperUtil.isReferenceWithinExportScope(
				portletDataContext, stagedModel)) {

			return portletDataContext.addReferenceElement(
				referrerStagedModel, referrerStagedModelElement, stagedModel,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
		}

		exportStagedModel(portletDataContext, stagedModel);

		return portletDataContext.addReferenceElement(
			referrerStagedModel, referrerStagedModelElement, stagedModel,
			referenceType, false);
	}

	public static <T extends StagedModel> void exportStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		StagedModelDataHandler<T> stagedModelDataHandler =
			_getStagedModelDataHandler(stagedModel);

		if (stagedModelDataHandler == null) {
			return;
		}

		stagedModelDataHandler.exportStagedModel(
			portletDataContext, stagedModel);
	}

	public static <T extends StagedModel> String getDisplayName(T stagedModel) {
		StagedModelDataHandler<T> stagedModelDataHandler =
			_getStagedModelDataHandler(stagedModel);

		if (stagedModelDataHandler == null) {
			return StringPool.BLANK;
		}

		return stagedModelDataHandler.getDisplayName(stagedModel);
	}

	public static Map<String, String> getReferenceAttributes(
		PortletDataContext portletDataContext, StagedModel stagedModel) {

		StagedModelDataHandler<StagedModel> stagedModelDataHandler =
			_getStagedModelDataHandler(stagedModel);

		if (stagedModelDataHandler == null) {
			return Collections.emptyMap();
		}

		return stagedModelDataHandler.getReferenceAttributes(
			portletDataContext, stagedModel);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #importReferenceStagedModel(PortletDataContext, Class,
	 *             Serializable)}
	 */
	@Deprecated
	public static void importReferenceStagedModel(
			PortletDataContext portletDataContext, Class<?> stagedModelClass,
			long classPK)
		throws PortletDataException {

		importReferenceStagedModel(
			portletDataContext, stagedModelClass, Long.valueOf(classPK));
	}

	/**
	 * Imports the staged model that is referenced by a portlet. To import a
	 * staged model referenced by another staged model, use {@link
	 * #importReferenceStagedModel(PortletDataContext, StagedModel, Class,
	 * long)}.
	 *
	 * @param  portletDataContext the portlet data context of the current
	 *         process
	 * @param  stagedModelClass the class of the referenced staged model to be
	 *         imported
	 * @param  classPK the primary key of the referenced staged model to be
	 *         imported
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public static void importReferenceStagedModel(
			PortletDataContext portletDataContext, Class<?> stagedModelClass,
			Serializable classPK)
		throws PortletDataException {

		importReferenceStagedModel(
			portletDataContext, stagedModelClass.getName(), classPK);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #importReferenceStagedModel(PortletDataContext, String,
	 *             Serializable)}
	 */
	@Deprecated
	public static void importReferenceStagedModel(
			PortletDataContext portletDataContext, String stagedModelClassName,
			long classPK)
		throws PortletDataException {

		importReferenceStagedModel(
			portletDataContext, stagedModelClassName, Long.valueOf(classPK));
	}

	/**
	 * Imports the staged model that is referenced by a portlet. To import a
	 * staged model referenced by another staged model, use {@link
	 * #importReferenceStagedModel(PortletDataContext, StagedModel, String,
	 * long)}.
	 *
	 * @param  portletDataContext the portlet data context of the current
	 *         process
	 * @param  stagedModelClassName the class name of the referenced staged
	 *         model to be imported
	 * @param  classPK the primary key of the referenced staged model to be
	 *         imported
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public static void importReferenceStagedModel(
			PortletDataContext portletDataContext, String stagedModelClassName,
			Serializable classPK)
		throws PortletDataException {

		Element referenceElement = portletDataContext.getReferenceElement(
			stagedModelClassName, classPK);

		doImportReferenceStagedModel(
			portletDataContext, referenceElement, stagedModelClassName);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #importReferenceStagedModel(PortletDataContext, StagedModel,
	 *             Class, Serializable)}
	 */
	@Deprecated
	public static <T extends StagedModel> void importReferenceStagedModel(
			PortletDataContext portletDataContext, T referrerStagedModel,
			Class<?> stagedModelClass, long classPK)
		throws PortletDataException {

		importReferenceStagedModel(
			portletDataContext, referrerStagedModel, stagedModelClass,
			Long.valueOf(classPK));
	}

	/**
	 * Imports the staged model that is referenced by another staged model. To
	 * import a staged model referenced by a portlet, use {@link
	 * #importReferenceStagedModel(PortletDataContext, Class, long)}.
	 *
	 * @param  portletDataContext the portlet data context of the current
	 *         process
	 * @param  referrerStagedModel the staged model that references the staged
	 *         model to be imported
	 * @param  stagedModelClass the class of the referenced staged model to be
	 *         imported
	 * @param  classPK the primary key of the referenced staged model to be
	 *         imported
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public static <T extends StagedModel> void importReferenceStagedModel(
			PortletDataContext portletDataContext, T referrerStagedModel,
			Class<?> stagedModelClass, Serializable classPK)
		throws PortletDataException {

		importReferenceStagedModel(
			portletDataContext, referrerStagedModel, stagedModelClass.getName(),
			classPK);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #importReferenceStagedModel(PortletDataContext, StagedModel,
	 *             String, Serializable)}
	 */
	@Deprecated
	public static <T extends StagedModel> void importReferenceStagedModel(
			PortletDataContext portletDataContext, T referrerStagedModel,
			String stagedModelClassName, long classPK)
		throws PortletDataException {

		importReferenceStagedModel(
			portletDataContext, referrerStagedModel, stagedModelClassName,
			Long.valueOf(classPK));
	}

	/**
	 * Imports the staged model that is referenced by another staged model. To
	 * import a staged model referenced by a portlet, use {@link
	 * #importReferenceStagedModel(PortletDataContext, String, long)}.
	 *
	 * @param  portletDataContext the portlet data context of the current
	 *         process
	 * @param  referrerStagedModel the staged model that references the staged
	 *         model to be imported
	 * @param  stagedModelClassName the class name of the referenced staged
	 *         model to be imported
	 * @param  classPK the primary key of the referenced staged model to be
	 *         imported
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public static <T extends StagedModel> void importReferenceStagedModel(
			PortletDataContext portletDataContext, T referrerStagedModel,
			String stagedModelClassName, Serializable classPK)
		throws PortletDataException {

		Element referenceElement = portletDataContext.getReferenceElement(
			referrerStagedModel, stagedModelClassName, classPK);

		doImportReferenceStagedModel(
			portletDataContext, referenceElement, stagedModelClassName);
	}

	public static void importReferenceStagedModels(
			PortletDataContext portletDataContext, Class<?> stagedModelClass)
		throws PortletDataException {

		Element importDataRootElement =
			portletDataContext.getImportDataRootElement();

		Element referencesElement = importDataRootElement.element("references");

		if (referencesElement == null) {
			return;
		}

		List<Element> referenceElements = referencesElement.elements();

		for (Element referenceElement : referenceElements) {
			String className = referenceElement.attributeValue("class-name");
			String stagedModelClassName = stagedModelClass.getName();

			if (!stagedModelClassName.equals(className)) {
				continue;
			}

			boolean missing = portletDataContext.isMissingReference(
				referenceElement);

			StagedModelDataHandler<?> stagedModelDataHandler =
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					stagedModelClassName);

			if (stagedModelDataHandler == null) {
				continue;
			}

			if (missing) {
				stagedModelDataHandler.importMissingReference(
					portletDataContext, referenceElement);

				continue;
			}

			importStagedModel(portletDataContext, referenceElement);
		}
	}

	public static <T extends StagedModel> void importReferenceStagedModels(
			PortletDataContext portletDataContext, T referrerStagedModel,
			Class<?> stagedModelClass)
		throws PortletDataException {

		List<Element> referenceElements =
			portletDataContext.getReferenceElements(
				referrerStagedModel, stagedModelClass);

		for (Element referenceElement : referenceElements) {
			Serializable classPK = GetterUtil.getString(
				referenceElement.attributeValue("class-pk"));

			importReferenceStagedModel(
				portletDataContext, referrerStagedModel, stagedModelClass,
				classPK);
		}
	}

	public static void importStagedModel(
			PortletDataContext portletDataContext, Element element)
		throws PortletDataException {

		StagedModel stagedModel = _getStagedModel(portletDataContext, element);

		importStagedModel(portletDataContext, stagedModel);
	}

	public static <T extends StagedModel> void importStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		StagedModelDataHandler<T> stagedModelDataHandler =
			_getStagedModelDataHandler(stagedModel);

		if (stagedModelDataHandler == null) {
			return;
		}

		stagedModelDataHandler.importStagedModel(
			portletDataContext, stagedModel);

		LastSessionRecorderHelperUtil.syncLastSessionState();
	}

	protected static void doImportReferenceStagedModel(
			PortletDataContext portletDataContext, Element referenceElement,
			String stagedModelClassName)
		throws PortletDataException {

		if (referenceElement == null) {
			return;
		}

		boolean missing = portletDataContext.isMissingReference(
			referenceElement);

		StagedModelDataHandler<?> stagedModelDataHandler =
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				stagedModelClassName);

		if (stagedModelDataHandler == null) {
			return;
		}

		if (missing) {
			stagedModelDataHandler.importMissingReference(
				portletDataContext, referenceElement);

			return;
		}

		Attribute missingAttribute = referenceElement.attribute("missing");

		if ((missingAttribute != null) &&
			GetterUtil.getBoolean(missingAttribute.getValue())) {

			StagedModel stagedModel = _getReferenceStagedModel(
				portletDataContext, referenceElement);

			Element missingReferenceElement =
				portletDataContext.getMissingReferenceElement(stagedModel);

			if (missingReferenceElement != null) {
				String elementPath = missingReferenceElement.attributeValue(
					"element-path");

				if (Validator.isNotNull(elementPath)) {
					Element importDataRootElement =
						portletDataContext.getImportDataRootElement();

					try {
						Document document = SAXReaderUtil.read(
							portletDataContext.getZipEntryAsString(
								elementPath));

						portletDataContext.setImportDataRootElement(
							document.getRootElement());

						importStagedModel(portletDataContext, referenceElement);
					}
					catch (DocumentException de) {
						throw new RuntimeException(de);
					}
					finally {
						portletDataContext.setImportDataRootElement(
							importDataRootElement);
					}

					return;
				}
			}
		}

		importStagedModel(portletDataContext, referenceElement);
	}

	private static StagedModel _getReferenceStagedModel(
		PortletDataContext portletDataContext, Element element) {

		long groupId = GetterUtil.getLong(element.attributeValue("group-id"));
		String className = element.attributeValue("class-name");
		Serializable classPK = GetterUtil.getString(
			element.attributeValue("class-pk"));

		String path = ExportImportPathUtil.getModelPath(
			groupId, className, classPK);

		StagedModel stagedModel =
			(StagedModel)portletDataContext.getZipEntryAsObject(element, path);

		if (stagedModel != null) {
			return stagedModel;
		}

		path = ExportImportPathUtil.getCompanyModelPath(
			portletDataContext.getSourceCompanyId(), className, classPK);

		return (StagedModel)portletDataContext.getZipEntryAsObject(
			element, path);
	}

	private static StagedModel _getStagedModel(
		PortletDataContext portletDataContext, Element element) {

		StagedModel stagedModel = null;

		String elementName = element.getName();

		if (elementName.equals("reference")) {
			stagedModel = _getReferenceStagedModel(portletDataContext, element);
		}
		else {
			String path = element.attributeValue("path");

			stagedModel = (StagedModel)portletDataContext.getZipEntryAsObject(
				element, path);
		}

		return stagedModel;
	}

	private static <T extends StagedModel> StagedModelDataHandler<T>
		_getStagedModelDataHandler(T stagedModel) {

		if (stagedModel == null) {
			_log.error(
				"Unable to get a staged model data handler for a null value " +
					"because a model was not exported properly");

			return null;
		}

		StagedModelDataHandler<T> stagedModelDataHandler =
			(StagedModelDataHandler<T>)
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					ExportImportClassedModelUtil.getClassName(stagedModel));

		return stagedModelDataHandler;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagedModelDataHandlerUtil.class);

}