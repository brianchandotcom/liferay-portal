/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.model.uid;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.search.model.uid.UIDFactory;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(service = UIDFactory.class)
public class UIDFactoryImpl implements UIDFactory {

	@Override
	public String getUID(ClassedModel classedModel) {
		long ctCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;

		if (classedModel instanceof CTModel<?>) {
			CTModel<?> ctModel = (CTModel<?>)classedModel;

			ctCollectionId = ctModel.getCtCollectionId();
		}

		return getUID(
			classedModel.getModelClassName(), classedModel.getPrimaryKeyObj(),
			ctCollectionId);
	}

	@Override
	public String getUID(
		String modelClassName, Serializable primaryKeyObject,
		long ctCollectionId) {

		if (ctCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			return StringBundler.concat(
				modelClassName, "_PORTLET_", primaryKeyObject, "_FIELD_",
				ctCollectionId);
		}

		return modelClassName + "_PORTLET_" + primaryKeyObject;
	}

}