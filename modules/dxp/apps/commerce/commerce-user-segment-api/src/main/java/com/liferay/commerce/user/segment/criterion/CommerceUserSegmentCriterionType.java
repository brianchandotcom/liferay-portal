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

package com.liferay.commerce.user.segment.criterion;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Locale;

/**
 * @author Marco Leo
 */
@ProviderType
public interface CommerceUserSegmentCriterionType {

	public void contributeToDocument(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion,
		Document document);

	public void contributeToDocument(Document document);

	public String getKey();

	public String getLabel(Locale locale);

	public String getPreview(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion, int length);

	public boolean isSatisfied(
			long commerceUserSegmentCriterionId, ServiceContext serviceContext)
		throws PortalException;

	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws PortalException;

}