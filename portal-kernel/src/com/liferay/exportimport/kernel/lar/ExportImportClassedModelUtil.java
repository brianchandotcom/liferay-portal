/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lar;

import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.ResourcedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

/**
 * @author Máté Thurzó
 */
public class ExportImportClassedModelUtil {

	public static String getClassName(ClassedModel classedModel) {
		String modelClassName = classedModel.getModelClassName();

		if (classedModel instanceof StagedModel) {
			StagedModel stagedModel = (StagedModel)classedModel;

			StagedModelType stagedModelType = stagedModel.getStagedModelType();

			modelClassName = stagedModelType.getClassName();
		}

		return modelClassName;
	}

	public static long getClassNameId(ClassedModel classedModel) {
		if (classedModel instanceof StagedModel) {
			StagedModel stagedModel = (StagedModel)classedModel;

			StagedModelType stagedModelType = stagedModel.getStagedModelType();

			return stagedModelType.getClassNameId();
		}

		return PortalUtil.getClassNameId(classedModel.getModelClass());
	}

	public static long getClassPK(ClassedModel classedModel) {
		return GetterUtil.getLong(getPrimaryKeyObj(classedModel));
	}

	public static String getClassSimpleName(ClassedModel classedModel) {
		Class<?> modelClass = classedModel.getModelClass();

		String modelClassSimpleName = modelClass.getSimpleName();

		if (classedModel instanceof StagedModel) {
			StagedModel stagedModel = (StagedModel)classedModel;

			StagedModelType stagedModelType = stagedModel.getStagedModelType();

			modelClassSimpleName = stagedModelType.getClassSimpleName();
		}

		return modelClassSimpleName;
	}

	public static Serializable getPrimaryKeyObj(ClassedModel classedModel) {
		if (classedModel instanceof ResourcedModel) {
			ResourcedModel resourcedModel = (ResourcedModel)classedModel;

			return resourcedModel.getResourcePrimKey();
		}

		return classedModel.getPrimaryKeyObj();
	}

}