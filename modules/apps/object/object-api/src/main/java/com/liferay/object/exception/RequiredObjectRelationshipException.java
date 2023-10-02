/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Arrays;
import java.util.List;

/**
 * @author Marco Leo
 */
public class RequiredObjectRelationshipException extends PortalException {

	public RequiredObjectRelationshipException(
		ObjectRelationship objectRelationship) {

		super(
			StringBundler.concat(
				"Object relationship ",
				objectRelationship.getObjectRelationshipId(),
				" does not allow deletes"));

		ObjectDefinition objectDefinition2 =
			ObjectDefinitionLocalServiceUtil.fetchObjectDefinition(
				objectRelationship.getObjectDefinitionId2());

		_arguments = Arrays.asList(
			objectRelationship.getName(), objectDefinition2.getShortName());

		_messageKey =
			"the-prevent-deletion-type-in-the-object-relationship-x-with-" +
				"object-definition-x-is-preventing-this-object-entry-from-" +
					"being-deleted";
	}

	public List<Object> getArguments() {
		return _arguments;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	private final List<Object> _arguments;
	private final String _messageKey;

}