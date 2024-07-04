/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.rest.internal.graphql.servlet.v1_0;

import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.language.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.portal.language.rest.internal.graphql.query.v1_0.Query;
import com.liferay.portal.language.rest.internal.resource.v1_0.MessageResourceImpl;
import com.liferay.portal.language.rest.resource.v1_0.MessageResource;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Thiago Buarque
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setMessageResourceComponentServiceObjects(
			_messageResourceComponentServiceObjects);

		Query.setMessageResourceComponentServiceObjects(
			_messageResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Portal.Language.REST";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/portal-language-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#createMessagesPageExportBatch",
						new ObjectValuePair<>(
							MessageResourceImpl.class,
							"postMessagesPageExportBatch"));
					put(
						"mutation#createMessage",
						new ObjectValuePair<>(
							MessageResourceImpl.class, "postMessage"));
					put(
						"mutation#createMessageBatch",
						new ObjectValuePair<>(
							MessageResourceImpl.class, "postMessageBatch"));
					put(
						"mutation#updateMessage",
						new ObjectValuePair<>(
							MessageResourceImpl.class, "putMessage"));
					put(
						"mutation#updateMessageBatch",
						new ObjectValuePair<>(
							MessageResourceImpl.class, "putMessageBatch"));
					put(
						"mutation#deleteMessageByKey",
						new ObjectValuePair<>(
							MessageResourceImpl.class, "deleteMessageByKey"));

					put(
						"query#messages",
						new ObjectValuePair<>(
							MessageResourceImpl.class, "getMessagesPage"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<MessageResource>
		_messageResourceComponentServiceObjects;

}