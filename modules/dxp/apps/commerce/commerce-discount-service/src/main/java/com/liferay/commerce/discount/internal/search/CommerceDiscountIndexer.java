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

package com.liferay.commerce.discount.internal.search;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.discount.service.CommerceDiscountUserSegmentRelLocalService;
import com.liferay.commerce.discount.target.CommerceDiscountOrderTarget;
import com.liferay.commerce.discount.target.CommerceDiscountProductTarget;
import com.liferay.commerce.discount.target.CommerceDiscountTarget;
import com.liferay.commerce.discount.target.CommerceDiscountTarget.Type;
import com.liferay.commerce.discount.target.CommerceDiscountTargetRegistry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.filter.TermsSetFilterBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = Indexer.class)
public class CommerceDiscountIndexer extends BaseIndexer<CommerceDiscount> {

	public static final String CLASS_NAME = CommerceDiscount.class.getName();

	public static final String FIELD_ACTIVE = "active";

	public static final String FIELD_COUPON_CODE = "couponCode";

	public static final String FIELD_TARGET_TYPE = "targetType";

	public static final String FIELD_USE_COUPON_CODE = "useCouponCode";

	public CommerceDiscountIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.NAME,
			Field.SCOPE_GROUP_ID, Field.UID);
		setFilterSearch(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		int[] statuses = GetterUtil.getIntegerValues(
			searchContext.getAttribute(Field.STATUS), null);

		if (ArrayUtil.isEmpty(statuses)) {
			int status = GetterUtil.getInteger(
				searchContext.getAttribute(Field.STATUS),
				WorkflowConstants.STATUS_APPROVED);

			statuses = new int[] {status};
		}

		if (!ArrayUtil.contains(statuses, WorkflowConstants.STATUS_ANY)) {
			TermsFilter statusesTermsFilter = new TermsFilter(Field.STATUS);

			statusesTermsFilter.addValues(ArrayUtil.toStringArray(statuses));

			contextBooleanFilter.add(
				statusesTermsFilter, BooleanClauseOccur.MUST);
		}
		else {
			contextBooleanFilter.addTerm(
				Field.STATUS, String.valueOf(WorkflowConstants.STATUS_IN_TRASH),
				BooleanClauseOccur.MUST_NOT);
		}

		long[] commerceUserSegmentEntryIds = GetterUtil.getLongValues(
			searchContext.getAttribute("commerceUserSegmentEntryIds"), null);

		if ((commerceUserSegmentEntryIds != null) &&
			(commerceUserSegmentEntryIds.length > 0)) {

			TermsSetFilterBuilder termsSetFilterBuilder =
				_filterBuilders.termsSetFilterBuilder();

			termsSetFilterBuilder.setFieldName("commerceUserSegmentEntryIds");
			termsSetFilterBuilder.setMinimumShouldMatchField(
				"commerceUserSegmentEntryIds_required_matches");

			List<String> values = new ArrayList<>(
				commerceUserSegmentEntryIds.length);

			for (long commerceUserSegmentEntryId :
					commerceUserSegmentEntryIds) {

				values.add(String.valueOf(commerceUserSegmentEntryId));
			}

			termsSetFilterBuilder.setValues(values);

			contextBooleanFilter.add(
				termsSetFilterBuilder.build(), BooleanClauseOccur.MUST);
		}

		boolean active = GetterUtil.getBoolean(
			searchContext.getAttribute(FIELD_ACTIVE));

		if (active) {
			contextBooleanFilter.addRequiredTerm(
				FIELD_ACTIVE, String.valueOf(active));
		}

		String targetType = GetterUtil.getString(
			searchContext.getAttribute("targetType"), null);

		if (Validator.isNotNull(targetType)) {
			contextBooleanFilter.addRequiredTerm(FIELD_TARGET_TYPE, targetType);
		}

		String couponCode = GetterUtil.getString(
			searchContext.getAttribute(FIELD_COUPON_CODE), null);

		if (Validator.isNotNull(couponCode)) {
			contextBooleanFilter.addRequiredTerm(FIELD_COUPON_CODE, couponCode);
			contextBooleanFilter.addRequiredTerm(
				FIELD_USE_COUPON_CODE, Boolean.TRUE.toString());
		}
		else {
			contextBooleanFilter.addRequiredTerm(
				FIELD_USE_COUPON_CODE, Boolean.FALSE.toString());
		}

		long cpDefinitionId = GetterUtil.getLong(
			searchContext.getAttribute("cpDefinitionId"));

		if (cpDefinitionId > 0) {
			CPDefinition cpDefinition =
				_cpDefinitionLocalService.getCPDefinition(cpDefinitionId);

			for (CommerceDiscountProductTarget commerceDiscountProductTarget :
					_commerceDiscountProductTargets) {

				commerceDiscountProductTarget.postProcessContextBooleanFilter(
					contextBooleanFilter, cpDefinition);
			}
		}

		long commerceOrderId = GetterUtil.getLong(
			searchContext.getAttribute("commerceOrderId"));

		if (commerceOrderId > 0) {
			CommerceOrder commerceOrder =
				_commerceOrderLocalService.getCommerceOrder(commerceOrderId);

			for (CommerceDiscountOrderTarget commerceDiscountOrderTarget :
					_commerceDiscountOrderTargets) {

				commerceDiscountOrderTarget.postProcessContextBooleanFilter(
					contextBooleanFilter, commerceOrder);
			}
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, Field.NAME, false);
		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				addSearchExpando(searchQuery, searchContext, expandoAttributes);
			}
		}
	}

	@Override
	protected void doDelete(CommerceDiscount commerceDiscount)
		throws Exception {

		deleteDocument(
			commerceDiscount.getCompanyId(),
			commerceDiscount.getCommerceDiscountId());
	}

	@Override
	protected Document doGetDocument(CommerceDiscount commerceDiscount)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing discount " + commerceDiscount);
		}

		CommerceDiscountTarget commerceDiscountTarget =
			_commerceDiscountTargetRegistry.getCommerceDiscountTarget(
				commerceDiscount.getTarget());

		Type commerceDiscountTargetType = commerceDiscountTarget.getType();

		Document document = getBaseModelDocument(CLASS_NAME, commerceDiscount);

		document.addText(Field.TITLE, commerceDiscount.getTitle());

		document.addText(
			FIELD_TARGET_TYPE, commerceDiscountTargetType.toString());
		document.addText(Field.USER_NAME, commerceDiscount.getUserName());
		document.addKeyword(FIELD_ACTIVE, commerceDiscount.getActive());
		document.addKeyword(
			FIELD_COUPON_CODE, commerceDiscount.getCouponCode());
		document.addKeyword(
			FIELD_USE_COUPON_CODE, commerceDiscount.getUseCouponCode());

		List<CommerceDiscountUserSegmentRel> commerceDiscountUserSegmentRels =
			_commerceDiscountUserSegmentRelLocalService.
				getCommerceDiscountUserSegmentRels(
					commerceDiscount.getCommerceDiscountId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

		Stream<CommerceDiscountUserSegmentRel> stream =
			commerceDiscountUserSegmentRels.stream();

		long[] commerceUserSegmentEntryIds = stream.mapToLong(
			CommerceDiscountUserSegmentRel::getCommerceUserSegmentEntryId).
				toArray();

		document.addNumber(
			"commerceUserSegmentEntryIds", commerceUserSegmentEntryIds);
		document.addNumber(
			"commerceUserSegmentEntryIds_required_matches",
			commerceUserSegmentEntryIds.length);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + commerceDiscount + " indexed successfully");
		}

		if (commerceDiscountTarget instanceof CommerceDiscountProductTarget) {
			((CommerceDiscountProductTarget)commerceDiscountTarget).
				contributeDocument(document, commerceDiscount);
		}

		if (commerceDiscountTarget instanceof CommerceDiscountOrderTarget) {
			((CommerceDiscountOrderTarget)commerceDiscountTarget).
				contributeDocument(document, commerceDiscount);
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, Field.ENTRY_CLASS_PK, Field.NAME);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CommerceDiscount commerceDiscount)
		throws Exception {

		Document document = getDocument(commerceDiscount);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commerceDiscount.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CommerceDiscount commerceDiscount =
			_commerceDiscountLocalService.getCommerceDiscount(classPK);

		doReindex(commerceDiscount);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCommerceDiscounts(companyId);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		service = CommerceDiscountOrderTarget.class
	)
	protected void registerCommerceDiscountOrderTarget(
		CommerceDiscountOrderTarget commerceDiscountOrderTarget) {

		_commerceDiscountOrderTargets.add(commerceDiscountOrderTarget);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		service = CommerceDiscountProductTarget.class
	)
	protected void registerCommerceDiscountProductTarget(
		CommerceDiscountProductTarget commerceDiscountProductTarget) {

		_commerceDiscountProductTargets.add(commerceDiscountProductTarget);
	}

	protected void reindexCommerceDiscounts(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commerceDiscountLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<CommerceDiscount>() {

				@Override
				public void performAction(CommerceDiscount commerceDiscount) {
					try {
						Document document = getDocument(commerceDiscount);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index commerce discount " +
									commerceDiscount.getCommerceDiscountId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	protected void unregisterCommerceDiscountOrderTarget(
		CommerceDiscountOrderTarget commerceDiscountOrderTarget) {

		_commerceDiscountOrderTargets.remove(commerceDiscountOrderTarget);
	}

	protected void unregisterCommerceDiscountProductTarget(
		CommerceDiscountProductTarget commerceDiscountProductTarget) {

		_commerceDiscountProductTargets.remove(commerceDiscountProductTarget);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountIndexer.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CommerceDiscountLocalService _commerceDiscountLocalService;

	private final List<CommerceDiscountOrderTarget>
		_commerceDiscountOrderTargets = new CopyOnWriteArrayList<>();
	private final List<CommerceDiscountProductTarget>
		_commerceDiscountProductTargets = new CopyOnWriteArrayList<>();

	@Reference
	private CommerceDiscountTargetRegistry _commerceDiscountTargetRegistry;

	@Reference
	private CommerceDiscountUserSegmentRelLocalService
		_commerceDiscountUserSegmentRelLocalService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private FilterBuilders _filterBuilders;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}