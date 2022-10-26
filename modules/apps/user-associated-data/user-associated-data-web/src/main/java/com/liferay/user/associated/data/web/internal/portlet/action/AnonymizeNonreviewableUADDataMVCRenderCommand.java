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

package com.liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.web.internal.constants.UADWebKeys;
import com.liferay.user.associated.data.web.internal.display.UADApplicationSummaryDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;
import com.liferay.user.associated.data.web.internal.util.UADLanguageUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/user_associated_data/anonymize_nonreviewable_uad_data"
	},
	service = MVCRenderCommand.class
)
public class AnonymizeNonreviewableUADDataMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			User selectedUser = _portal.getSelectedUser(renderRequest);

			SearchContainer<UADApplicationSummaryDisplay> searchContainer =
				_createSearchContainer(
					LocaleThreadLocal.getThemeDisplayLocale(), renderRequest,
					selectedUser.getUserId());

			int totalCount = 0;

			for (UADApplicationSummaryDisplay uadApplicationSummaryDisplay :
					searchContainer.getResults()) {

				totalCount += uadApplicationSummaryDisplay.getCount();
			}

			renderRequest.setAttribute(
				UADWebKeys.TOTAL_UAD_ENTITIES_COUNT, totalCount);

			renderRequest.setAttribute(
				WebKeys.SEARCH_CONTAINER, searchContainer);
		}
		catch (PortalException portalException) {
			throw new PortletException(portalException);
		}

		return "/anonymize_nonreviewable_uad_data.jsp";
	}

	private SearchContainer<UADApplicationSummaryDisplay>
			_createSearchContainer(
				Locale locale, RenderRequest renderRequest, long userId)
		throws PortalException {

		PortletRequest portletRequest =
			(PortletRequest)renderRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		SearchContainer<UADApplicationSummaryDisplay> searchContainer =
			new SearchContainer<>(
				portletRequest,
				PortletURLUtil.getCurrent(
					_portal.getLiferayPortletRequest(portletRequest),
					_portal.getLiferayPortletResponse(
						(PortletResponse)renderRequest.getAttribute(
							JavaConstants.JAVAX_PORTLET_RESPONSE))),
				null, null);

		searchContainer.setEmptyResultsMessage(
			"there-are-no-remaining-applications-to-anonymize");
		searchContainer.setId("uadApplicationSummaryDisplays");
		searchContainer.setOrderByCol(
			SearchOrderByUtil.getOrderByCol(
				portletRequest,
				UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
				"anonymize-order-by-col", "name"));
		searchContainer.setOrderByType(
			SearchOrderByUtil.getOrderByType(
				portletRequest,
				UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
				"anonymize-order-by-type", "asc"));

		List<UADApplicationSummaryDisplay> uadApplicationSummaryDisplays =
			_getUADApplicationSummaryDisplays(userId);

		Supplier<Stream<UADApplicationSummaryDisplay>> streamSupplier = () -> {
			Stream<UADApplicationSummaryDisplay> stream =
				uadApplicationSummaryDisplays.stream();

			return stream.filter(display -> display.getCount() > 0);
		};

		Stream<UADApplicationSummaryDisplay> summaryDisplayStream =
			streamSupplier.get();

		List<UADApplicationSummaryDisplay> results =
			summaryDisplayStream.sorted(
				_getComparator(
					locale, searchContainer.getOrderByCol(),
					searchContainer.getOrderByType())
			).skip(
				searchContainer.getStart()
			).limit(
				searchContainer.getDelta()
			).collect(
				Collectors.toList()
			);

		summaryDisplayStream = streamSupplier.get();

		searchContainer.setResultsAndTotal(
			() -> results, (int)summaryDisplayStream.count());

		return searchContainer;
	}

	private Comparator<UADApplicationSummaryDisplay> _getComparator(
		Locale locale, String orderByColumn, String orderByType) {

		Comparator<UADApplicationSummaryDisplay> comparator =
			Comparator.comparing(
				uadApplicationSummaryDisplay ->
					UADLanguageUtil.getApplicationName(
						uadApplicationSummaryDisplay.getApplicationKey(),
						locale));

		if (orderByColumn.equals("items") || orderByColumn.equals("status")) {
			comparator = Comparator.comparingInt(
				UADApplicationSummaryDisplay::getCount);
		}

		if (orderByType.equals("desc")) {
			comparator = comparator.reversed();
		}

		return comparator;
	}

	private UADApplicationSummaryDisplay _getUADApplicationSummaryDisplay(
			String applicationKey, long userId)
		throws PortalException {

		UADApplicationSummaryDisplay uadApplicationSummaryDisplay =
			new UADApplicationSummaryDisplay();

		Collection<UADAnonymizer<?>> nonreviewableApplicationUADAnonymizers =
			_uadRegistry.getNonreviewableApplicationUADAnonymizers(
				applicationKey);

		uadApplicationSummaryDisplay.setApplicationKey(applicationKey);

		int count = 0;

		for (UADAnonymizer<?> uadAnonymizer :
				nonreviewableApplicationUADAnonymizers) {

			count += uadAnonymizer.count(userId);
		}

		uadApplicationSummaryDisplay.setCount(count);

		return uadApplicationSummaryDisplay;
	}

	private List<UADApplicationSummaryDisplay>
			_getUADApplicationSummaryDisplays(long userId)
		throws PortalException {

		List<UADApplicationSummaryDisplay> uadApplicationSummaryDisplays =
			new ArrayList<>();

		Set<String> applicationUADAnonymizersKeySet =
			_uadRegistry.getApplicationUADAnonymizersKeySet();

		Iterator<String> iterator = applicationUADAnonymizersKeySet.iterator();

		while (iterator.hasNext()) {
			String applicationKey = iterator.next();

			uadApplicationSummaryDisplays.add(
				_getUADApplicationSummaryDisplay(applicationKey, userId));
		}

		uadApplicationSummaryDisplays.sort(
			(uadApplicationSummaryDisplay, uadApplicationSummaryDisplay2) -> {
				String applicationKey1 =
					uadApplicationSummaryDisplay.getApplicationKey();

				return applicationKey1.compareTo(
					uadApplicationSummaryDisplay2.getApplicationKey());
			});

		return uadApplicationSummaryDisplays;
	}

	@Reference
	private Portal _portal;

	@Reference
	private UADRegistry _uadRegistry;

}