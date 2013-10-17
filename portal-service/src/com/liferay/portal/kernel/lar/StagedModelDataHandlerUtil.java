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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.StagedGroupedModel;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.TypedModel;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Mate Thurzo
 */
public class StagedModelDataHandlerUtil {

	public static void deleteStagedModel(
			PortletDataContext portletDataContext, Element deletionElement)
		throws PortalException, SystemException {

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

		Element referenceElement = null;

		Portlet referrerPortlet = PortletLocalServiceUtil.getPortletById(
			referrerPortletId);

		if (stagedModel instanceof StagedGroupedModel) {
			StagedGroupedModel stagedGroupedModel =
				(StagedGroupedModel)stagedModel;

			if (portletDataContext.isCompanyStagedGroupedModel(
					stagedGroupedModel)) {

				referenceElement = portletDataContext.addReferenceElement(
					referrerPortlet,
					portletDataContext.getExportDataRootElement(), stagedModel,
					stagedModel.getModelClass(),
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);

				_addReferenceAttributes(
					portletDataContext, stagedModel, referenceElement);

				return referenceElement;
			}
		}

		exportStagedModel(portletDataContext, stagedModel);

		referenceElement = portletDataContext.addReferenceElement(
			referrerPortlet, portletDataContext.getExportDataRootElement(),
			stagedModel, stagedModel.getModelClass(),
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY, false);

		_addReferenceAttributes(
			portletDataContext, stagedModel, referenceElement);

		return referenceElement;
	}

	public static <T extends StagedModel, U extends StagedModel> Element
		exportReferenceStagedModel(
			PortletDataContext portletDataContext, T referrerStagedModel,
			Class<?> referrerStagedModelClass, U stagedModel,
			Class<?> stagedModelClass, String referenceType)
		throws PortletDataException {

		Element referrerStagedModelElement =
			portletDataContext.getExportDataElement(
				referrerStagedModel, referrerStagedModelClass);

		return exportReferenceStagedModel(
			portletDataContext, referrerStagedModel, referrerStagedModelElement,
			stagedModel, stagedModelClass, referenceType);
	}

	public static <T extends StagedModel, U extends StagedModel> Element
		exportReferenceStagedModel(
			PortletDataContext portletDataContext, T referrerStagedModel,
			Element referrerStagedModelElement, U stagedModel,
			Class<?> stagedModelClass, String referenceType)
		throws PortletDataException {

		Element referenceElement = null;

		if (stagedModel instanceof StagedGroupedModel) {
			StagedGroupedModel stagedGroupedModel =
				(StagedGroupedModel)stagedModel;

			if (portletDataContext.isCompanyStagedGroupedModel(
					stagedGroupedModel)) {

				referenceElement = portletDataContext.addReferenceElement(
					referrerStagedModel, referrerStagedModelElement,
					stagedModel, stagedModelClass,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);

				_addReferenceAttributes(
					portletDataContext, stagedModel, referenceElement);

				return referenceElement;
			}
		}

		exportStagedModel(portletDataContext, stagedModel);

		referenceElement = portletDataContext.addReferenceElement(
			referrerStagedModel, referrerStagedModelElement, stagedModel,
			stagedModelClass, referenceType, false);

		_addReferenceAttributes(
			portletDataContext, stagedModel, referenceElement);

		return referenceElement;
	}

	public static <T extends StagedModel, U extends StagedModel> Element
		exportReferenceStagedModel(
			PortletDataContext portletDataContext, T referrerStagedModel,
			U stagedModel, String referenceType)
		throws PortletDataException {

		return exportReferenceStagedModel(
			portletDataContext, referrerStagedModel,
			referrerStagedModel.getModelClass(), stagedModel,
			stagedModel.getModelClass(), referenceType);
	}

	public static <T extends StagedModel> void exportStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		StagedModelDataHandler<T> stagedModelDataHandler =
			_getStagedModelDataHandler(stagedModel);

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

	public static <T extends StagedModel> void importReferenceStagedModel(
			PortletDataContext portletDataContext, T referrerStagedModel,
			Class<?> stagedModelClass, long classPk)
		throws PortletDataException {

		Element referenceElement =
			portletDataContext.getReferenceElement(
				referrerStagedModel, stagedModelClass, classPk);

		if (referenceElement == null) {
			return;
		}

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		if ((portletDataContext.getSourceCompanyGroupId() == groupId) &&
			(portletDataContext.getGroupId() !=
				portletDataContext.getCompanyGroupId())) {

			StagedModelDataHandler stagedModelDataHandler =
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					stagedModelClass.getName());

			stagedModelDataHandler.importCompanyStagedModel(
				portletDataContext, referenceElement);

			return;
		}

		Element referenceDataElement =
			portletDataContext.getReferenceDataElement(
				referrerStagedModel, stagedModelClass, classPk);

		importStagedModel(portletDataContext, referenceDataElement);
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

			long groupId = GetterUtil.getLong(
				referenceElement.attributeValue("group-id"));

			if ((portletDataContext.getSourceCompanyGroupId() == groupId) &&
				(portletDataContext.getGroupId() !=
					portletDataContext.getCompanyGroupId())) {

				StagedModelDataHandler stagedModelDataHandler =
					StagedModelDataHandlerRegistryUtil.
						getStagedModelDataHandler(stagedModelClass.getName());

				stagedModelDataHandler.importCompanyStagedModel(
					portletDataContext, referenceElement);

				continue;
			}

			long classPk = GetterUtil.getLong(
				referenceElement.attributeValue("class-pk"));

			String stagedModelPath = ExportImportPathUtil.getModelPath(
				portletDataContext, stagedModelClass.getName(), classPk);

			StagedModel stagedModel =
				(StagedModel)portletDataContext.getZipEntryAsObject(
					stagedModelPath);

			importStagedModel(portletDataContext, stagedModel);
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
			long classPk = GetterUtil.getLong(
				referenceElement.attributeValue("class-pk"));

			importReferenceStagedModel(
				portletDataContext, referrerStagedModel, stagedModelClass,
				classPk);
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

		stagedModelDataHandler.importStagedModel(
			portletDataContext, stagedModel);
	}

	private static void _addReferenceAttributes(
		PortletDataContext portletDataContext, StagedModel stagedModel,
		Element referenceElement) {

		StagedModelDataHandler stagedModelDataHandler =
			_getStagedModelDataHandler(stagedModel);

		Map<String, String> referenceAttributes =
			stagedModelDataHandler.getReferenceAttributes(
				portletDataContext, stagedModel);

		for (Map.Entry<String, String> referenceAttribute :
				referenceAttributes.entrySet()) {

			referenceElement.addAttribute(
				referenceAttribute.getKey(), referenceAttribute.getValue());
		}
	}

	private static StagedModel _getStagedModel(
		PortletDataContext portletDataContext, Element element) {

		String path = element.attributeValue("path");

		StagedModel stagedModel =
			(StagedModel)portletDataContext.getZipEntryAsObject(element, path);

		Attribute classNameAttribute = element.attribute("class-name");

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

		ClassedModel classedModel = stagedModel;

		StagedModelDataHandler<T> stagedModelDataHandler =
			(StagedModelDataHandler<T>)
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					classedModel.getModelClassName());

		return stagedModelDataHandler;
	}

}