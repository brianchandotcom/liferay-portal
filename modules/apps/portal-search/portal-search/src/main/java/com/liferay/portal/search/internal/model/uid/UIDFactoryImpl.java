/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.model.uid;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
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
		return _getUID(classedModel);
	}

	@Override
	public String getUID(com.liferay.portal.kernel.search.Document document) {
		return _getUID(document);
	}

	@Override
	public String getUID(Document document) {
		return _getUID(document);
	}

	@Override
	public String getUID(
		String modelClassName, Serializable primaryKeyObject,
		long ctCollectionId) {

		return _getUID(modelClassName, primaryKeyObject, ctCollectionId);
	}

	@Override
	public void setUID(
		ClassedModel classedModel,
		com.liferay.portal.kernel.search.Document document) {

		document.addKeyword(Field.UID, _getUID(classedModel));
	}

	@Override
	public void setUID(
		ClassedModel classedModel, DocumentBuilder documentBuilder) {

		documentBuilder.setString(Field.UID, _getUID(classedModel));
	}

	private long _getCtCollectionId(ClassedModel classedModel) {
		if (classedModel instanceof CTModel<?>) {
			CTModel<?> ctModel = (CTModel<?>)classedModel;

			return ctModel.getCtCollectionId();
		}

		return CTConstants.CT_COLLECTION_ID_PRODUCTION;
	}

	private String _getUID(ClassedModel classedModel) {
		return _getUID(
			classedModel.getModelClassName(), classedModel.getPrimaryKeyObj(),
			_getCtCollectionId(classedModel));
	}

	private String _getUID(com.liferay.portal.kernel.search.Document document) {
		return document.getUID();
	}

	private String _getUID(Document document) {
		return document.getString(Field.UID);
	}

	private String _getUID(
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