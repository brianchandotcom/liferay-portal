/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.web.internal.frontend.data.set.provider;

import com.liferay.audience.model.AudienceEntry;
import com.liferay.audience.service.AudienceEntryService;
import com.liferay.audience.web.internal.constants.AudienceFDSNames;
import com.liferay.audience.web.internal.frontend.data.set.model.FDSAudienceEntry;
import com.liferay.audience.web.internal.util.comparator.AudienceEntryModifiedDateComparator;
import com.liferay.audience.web.internal.util.comparator.AudienceEntryNameComparator;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "fds.data.provider.key=" + AudienceFDSNames.AUDIENCE_ENTRIES,
	service = FDSDataProvider.class
)
public class AudienceFDSDataProvider
	implements FDSDataProvider<FDSAudienceEntry> {

	@Override
	public List<FDSAudienceEntry> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<AudienceEntry> audienceEntries = null;

		if (Validator.isNotNull(fdsKeywords.getKeywords())) {
			audienceEntries = _audienceEntryService.getAudienceEntries(
				themeDisplay.getCompanyId(), fdsKeywords.getKeywords(),
				fdsPagination.getStartPosition(),
				fdsPagination.getEndPosition(), _getOrderByComparator(sort));
		}
		else {
			audienceEntries = _audienceEntryService.getAudienceEntries(
				themeDisplay.getCompanyId(), fdsPagination.getStartPosition(),
				fdsPagination.getEndPosition(), _getOrderByComparator(sort));
		}

		return TransformUtil.transform(
			audienceEntries,
			audienceEntry -> new FDSAudienceEntry(
				audienceEntry.getAudienceEntryId(),
				audienceEntry.getModifiedDate(), audienceEntry.getName()));
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (Validator.isNotNull(fdsKeywords.getKeywords())) {
			return _audienceEntryService.getAudienceEntriesCount(
				themeDisplay.getCompanyId(), fdsKeywords.getKeywords());
		}

		return _audienceEntryService.getAudienceEntriesCount(
			themeDisplay.getCompanyId());
	}

	private OrderByComparator<AudienceEntry> _getOrderByComparator(Sort sort) {
		if (sort == null) {
			return AudienceEntryModifiedDateComparator.getInstance(false);
		}

		boolean ascending = !sort.isReverse();

		if (Objects.equals(sort.getFieldName(), "name")) {
			return AudienceEntryNameComparator.getInstance(ascending);
		}

		return AudienceEntryModifiedDateComparator.getInstance(ascending);
	}

	@Reference
	private AudienceEntryService _audienceEntryService;

}