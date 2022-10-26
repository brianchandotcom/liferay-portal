/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.search.experiences.rest.resource.v1_0.SentenceTransformerValidationResultResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/sentence-transformer-validation-result.properties",
	scope = ServiceScope.PROTOTYPE,
	service = SentenceTransformerValidationResultResource.class
)
public class SentenceTransformerValidationResultResourceImpl
	extends BaseSentenceTransformerValidationResultResourceImpl {
}