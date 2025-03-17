/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.osgi.service.component.annotations.Component;

/**
 * @author Caio Pinheiro
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.Organization",
	service = ModelDocumentContributor.class
)
public class OrganizationModelDocumentContributor
	implements ModelDocumentContributor<Organization> {

	@Override
	public void contribute(Document document, Organization organization) {
		document.addDate(
			"dateModifiedTruncated", _getDateModifiedTruncated(organization));
	}

	private Date _getDateModifiedTruncated(Organization organization) {
		Date dateModified = organization.getModifiedDate();

		TimeZone timeZone = TimeZone.getDefault();

		Calendar calendar = Calendar.getInstance(timeZone, LocaleUtil.US);

		calendar.setTime(dateModified);

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

}