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

package com.liferay.commerce.google.merchant.internal.xml;

import com.ctc.wstx.api.WstxOutputProperties;
import com.ctc.wstx.stax.WstxInputFactory;
import com.ctc.wstx.stax.WstxOutputFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.google.merchant.internal.xml.model.Feed;
import com.liferay.commerce.google.merchant.internal.xml.model.Link;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPQuery;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.data.source.CPDataSourceResult;
import com.liferay.commerce.product.exception.InvalidCommerceChannelTypeException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.interval.IntervalActionProcessor;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 *
 * Implementation for generating XML for Google Merchant Center feed in
 * Atom 1.0 XML format
 */
@Component(
	enabled = false, immediate = true, service = ProductFeedXMLGenerator.class
)
public class ProductFeedXMLGenerator {

	public String generateProductFeedXML(long commerceChannelId)
		throws PortalException {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(commerceChannelId);

		if (!CommerceChannelConstants.CHANNEL_TYPE_SITE.equals(
				commerceChannel.getType())) {

			throw new InvalidCommerceChannelTypeException(
				"Cannot generate products XML for channel with ID " +
					commerceChannelId +
						" because channel must be site type channel");
		}

		Feed feed = new Feed();

		feed.setTitle(commerceChannel.getName());

		Group group = _groupLocalService.getGroup(
			commerceChannel.getSiteGroupId());

		String href = _portal.getLayoutSetDisplayURL(
			group.getPublicLayoutSet(), false);

		Link link = new Link(href);

		feed.setLink(link);

		String updated = DateUtil.getCurrentDate(
			DateUtil.ISO_8601_PATTERN, null, TimeZoneUtil.GMT);

		feed.setUpdated(updated);

		int total = _countCPCatalogEntriesByChannel(commerceChannel);

		IntervalActionProcessor<Void> intervalActionProcessor =
			new IntervalActionProcessor<>(total);

		intervalActionProcessor.setPerformIntervalActionMethod(
			(start, end) -> {
				List<CPCatalogEntry> cpCatalogEntries =
					_getCPCatalogEntriesByChannel(commerceChannel, start, end);

				/* TODO: To be implemented in COMMERCE-2690.

				for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
					//TODO COMMERCE-2690 add XML for a product here
				}
				*/

				intervalActionProcessor.incrementStart(cpCatalogEntries.size());

				return null;
			});

		intervalActionProcessor.performIntervalActions();

		try {
			XMLInputFactory xmlInputFactory = new WstxInputFactory();

			XMLOutputFactory xmlOutputFactory = new WstxOutputFactory();

			xmlOutputFactory.setProperty(
				WstxOutputProperties.P_OUTPUT_VALIDATE_ATTR, true);

			XmlFactory xmlFactory = new XmlFactory(
				xmlInputFactory, xmlOutputFactory);

			XmlMapper xmlMapper = new XmlMapper(xmlFactory);

			xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

			return xmlMapper.writeValueAsString(feed);
		}
		catch (JsonProcessingException jsonProcessingException) {
			throw new PortalException(jsonProcessingException);
		}
	}

	private int _countCPCatalogEntriesByChannel(CommerceChannel commerceChannel)
		throws PortalException {

		long commerceChannelGroupId = commerceChannel.getGroupId();

		return GetterUtil.getInteger(
			_cpDefinitionHelper.searchCount(
				commerceChannelGroupId, _getSearchContext(commerceChannel),
				new CPQuery()));
	}

	private List<CPCatalogEntry> _getCPCatalogEntriesByChannel(
			CommerceChannel commerceChannel, int start, int end)
		throws PortalException {

		long commerceChannelGroupId = commerceChannel.getGroupId();

		CPDataSourceResult cpDataSourceResult = _cpDefinitionHelper.search(
			commerceChannelGroupId, _getSearchContext(commerceChannel),
			new CPQuery(), start, end);

		return cpDataSourceResult.getCPCatalogEntries();
	}

	private SearchContext _getSearchContext(CommerceChannel commerceChannel) {
		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.STATUS, WorkflowConstants.STATUS_APPROVED
			).put(
				"commerceAccountGroupIds",
				new long[] {CommerceAccountConstants.ACCOUNT_ID_GUEST}
			).put(
				"commerceChannelGroupId", commerceChannel.getGroupId()
			).build());
		searchContext.setCompanyId(commerceChannel.getCompanyId());

		return searchContext;
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}