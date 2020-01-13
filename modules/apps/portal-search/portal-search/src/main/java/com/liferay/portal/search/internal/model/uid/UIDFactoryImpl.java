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

package com.liferay.portal.search.internal.model.uid;

import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.ResourcedModel;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.internal.util.SearchStringUtil;
import com.liferay.portal.search.model.uid.UIDFactory;

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
		if (_ENFORCE_STANDARD_UID) {
			return _requireEquals(document.get("uidm"), _getUID(document));
		}

		return _getUID(document);
	}

	@Override
	public String getUID(Document document) {
		if (_ENFORCE_STANDARD_UID) {
			return _requireEquals(
				document.getString("uidm"), _getUID(document));
		}

		return _getUID(document);
	}

	@Override
	public void setUID(
		ClassedModel classedModel,
		com.liferay.portal.kernel.search.Document document) {

		if (_ENFORCE_STANDARD_UID) {
			if (!(classedModel instanceof ResourcedModel) &&
				!Validator.isBlank(_getUID(document))) {

				_requireEquals(_getUID(classedModel), _getUID(document));
			}

			document.addKeyword(Field.UID, _getUID(classedModel));
			document.addKeyword("uidm", _getUID(classedModel));

			return;
		}

		document.addKeyword(Field.UID, _getUID(classedModel));
	}

	@Override
	public void setUID(
		ClassedModel classedModel, DocumentBuilder documentBuilder) {

		if (_ENFORCE_STANDARD_UID) {
			documentBuilder.setString(Field.UID, _getUID(classedModel));
			documentBuilder.setString("uidm", _getUID(classedModel));

			return;
		}

		documentBuilder.setString(Field.UID, _getUID(classedModel));
	}

	private String _getUID(ClassedModel classedModel) {
		return classedModel.getModelClassName() + _UID_SEPARATOR +
			String.valueOf(classedModel.getPrimaryKeyObj());
	}

	private String _getUID(com.liferay.portal.kernel.search.Document document) {
		return document.getUID();
	}

	private String _getUID(Document document) {
		return document.getString(Field.UID);
	}

	private boolean _isKnownNonstandard(String uid) {
		return uid.startsWith(_SYSTEM_SETTINGS_UID_PREFIX);
	}

	private String _requireEquals(String expected, String actual) {
		if (_isKnownNonstandard(actual)) {
			return actual;
		}

		return SearchStringUtil.requireEquals(expected, actual);
	}

	private static final boolean _ENFORCE_STANDARD_UID = false;

	private static final String _SYSTEM_SETTINGS_UID_PREFIX =
		"com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet";

	private static final String _UID_SEPARATOR = "_PORTLET_";

}