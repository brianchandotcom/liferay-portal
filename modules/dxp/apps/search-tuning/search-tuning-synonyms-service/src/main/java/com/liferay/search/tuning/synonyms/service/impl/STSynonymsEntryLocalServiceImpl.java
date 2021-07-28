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

package com.liferay.search.tuning.synonyms.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.search.tuning.synonyms.service.base.STSynonymsEntryLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(
	property = "model.class.name=com.liferay.search.tuning.synonyms.model.STSynonymsEntry",
	service = AopService.class
)
public class STSynonymsEntryLocalServiceImpl
	extends STSynonymsEntryLocalServiceBaseImpl {
}