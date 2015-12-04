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

package com.liferay.portlet.exportimport.lar;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.spring.orm.LastSessionRecorderHelperUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.TypedModel;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

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
			long classPK)
		throws PortletDataException {

		importReferenceStagedModel(
			portletDataContext, stagedModelClass.getName(), classPK);
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
			long classPK)
		throws PortletDataException {

		Element referenceElement = portletDataContext.getReferenceElement(
			stagedModelClassName, classPK);

		doImportReferenceStagedModel(
			portletDataContext, referenceElement, stagedModelClassName,
			classPK);
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
			Class<?> stagedModelClass, long classPK)
		throws PortletDataException {

		importReferenceStagedModel(
			portletDataContext, referrerStagedModel, stagedModelClass.getName(),
			classPK);
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
			String stagedModelClassName, long classPK)
		throws PortletDataException {

		Element referenceElement = portletDataContext.getReferenceElement(
			referrerStagedModel, stagedModelClassName, classPK);

		doImportReferenceStagedModel(
			portletDataContext, referenceElement, stagedModelClassName,
			classPK);
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
			long classPK = GetterUtil.getLong(
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
			String stagedModelClassName, long classPK)
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

		if (referenceElement != null) {
			importStagedModel(portletDataContext, referenceElement);

			return;
		}

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"),
			portletDataContext.getSourceGroupId());

		String stagedModelPath = ExportImportPathUtil.getModelPath(
			groupId, stagedModelClassName, classPK);

		StagedModel stagedModel =
			(StagedModel)portletDataContext.getZipEntryAsObject(
				stagedModelPath);

		if (stagedModel != null) {
			importStagedModel(portletDataContext, stagedModel);
		}
	}

	private static StagedModel _getReferenceStagedModel(
		PortletDataContext portletDataContext, Element element) {

		long groupId = GetterUtil.getLong(element.attributeValue("group-id"));
		String className = element.attributeValue("class-name");
		long classPK = GetterUtil.getLong(element.attributeValue("class-pk"));

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
		Attribute classNameAttribute = null;

		String elementName = element.getName();

		if (elementName.equals("reference")) {
			stagedModel = _getReferenceStagedModel(portletDataContext, element);

			Element referenceStagedModelElement =
				portletDataContext.getImportDataElement(stagedModel);

			if (referenceStagedModelElement != null) {
				classNameAttribute = referenceStagedModelElement.attribute(
					"class-name");
			}
		}
		else {
			String path = element.attributeValue("path");

			stagedModel = (StagedModel)portletDataContext.getZipEntryAsObject(
				element, path);

			classNameAttribute = element.attribute("class-name");
		}

		if ((classNameAttribute != null) &&
			(stagedModel instanceof TypedModel)) {

			String className = classNameAttribute.getValue();

			if (Validator.isNotNull(className)) {
				long classNameId = PortalUtil.getClassNameId(className);

				TypedModel typedModel = (TypedModel)stagedModel;

				typedModel.setClassNameId(classNameId);
			}
		}

		return stagedModel;
	}

	private static <T extends StagedModel> StagedModelDataHandler<T>
		_getStagedModelDataHandler(T stagedModel) {

		StagedModelDataHandler<T> stagedModelDataHandler =
			(StagedModelDataHandler<T>)
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					ExportImportClassedModelUtil.getClassName(stagedModel));

		return stagedModelDataHandler;
	}

}