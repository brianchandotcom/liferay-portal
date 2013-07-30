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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.TypedModel;
import com.liferay.portal.util.PortalUtil;

/**
 * @author Brian Wing Shun Chan
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

	public static void importStagedModel(
			PortletDataContext portletDataContext, Element element)
		throws PortletDataException {

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