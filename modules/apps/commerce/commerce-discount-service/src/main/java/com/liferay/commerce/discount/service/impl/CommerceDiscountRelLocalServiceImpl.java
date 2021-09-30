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

package com.liferay.commerce.discount.service.impl;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryTable;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.model.CommerceDiscountRelTable;
import com.liferay.commerce.discount.service.base.CommerceDiscountRelLocalServiceBaseImpl;
import com.liferay.commerce.discount.util.comparator.CommerceDiscountRelCreateDateComparator;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.model.CommercePricingClassTable;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLocalizationTable;
import com.liferay.commerce.product.model.CPDefinitionTable;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceTable;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountRelLocalServiceImpl
	extends CommerceDiscountRelLocalServiceBaseImpl {

	@Override
	public CommerceDiscountRel addCommerceDiscountRel(
			long commerceDiscountId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce discount rel

		User user = userLocalService.getUser(serviceContext.getUserId());

		long commerceDiscountRelId = counterLocalService.increment();

		CommerceDiscountRel commerceDiscountRel =
			commerceDiscountRelPersistence.create(commerceDiscountRelId);

		commerceDiscountRel.setCompanyId(user.getCompanyId());
		commerceDiscountRel.setUserId(user.getUserId());
		commerceDiscountRel.setUserName(user.getFullName());
		commerceDiscountRel.setCommerceDiscountId(commerceDiscountId);
		commerceDiscountRel.setClassName(className);
		commerceDiscountRel.setClassPK(classPK);

		commerceDiscountRel = commerceDiscountRelPersistence.update(
			commerceDiscountRel);

		// Commerce discount

		reindexCommerceDiscount(commerceDiscountId);

		return commerceDiscountRel;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceDiscountRel deleteCommerceDiscountRel(
			CommerceDiscountRel commerceDiscountRel)
		throws PortalException {

		// Commerce discount rel

		commerceDiscountRelPersistence.remove(commerceDiscountRel);

		// Commerce discount

		reindexCommerceDiscount(commerceDiscountRel.getCommerceDiscountId());

		return commerceDiscountRel;
	}

	@Override
	public CommerceDiscountRel deleteCommerceDiscountRel(
			long commerceDiscountRelId)
		throws PortalException {

		CommerceDiscountRel commerceDiscountRel =
			commerceDiscountRelPersistence.findByPrimaryKey(
				commerceDiscountRelId);

		return commerceDiscountRelLocalService.deleteCommerceDiscountRel(
			commerceDiscountRel);
	}

	@Override
	public void deleteCommerceDiscountRels(long commerceDiscountId)
		throws PortalException {

		List<CommerceDiscountRel> commerceDiscountRels =
			commerceDiscountRelPersistence.findByCommerceDiscountId(
				commerceDiscountId);

		for (CommerceDiscountRel commerceDiscountRel : commerceDiscountRels) {
			commerceDiscountRelLocalService.deleteCommerceDiscountRel(
				commerceDiscountRel);
		}
	}

	@Override
	public void deleteCommerceDiscountRels(String className, long classPK)
		throws PortalException {

		List<CommerceDiscountRel> commerceDiscountRels =
			commerceDiscountRelPersistence.findByCN_CPK(
				classNameLocalService.getClassNameId(className), classPK);

		for (CommerceDiscountRel commerceDiscountRel : commerceDiscountRels) {
			commerceDiscountRelLocalService.deleteCommerceDiscountRel(
				commerceDiscountRel);
		}
	}

	@Override
	public CommerceDiscountRel fetchCommerceDiscountRel(
		String className, long classPK) {

		return commerceDiscountRelPersistence.fetchByCN_CPK_First(
			classNameLocalService.getClassNameId(className), classPK,
			new CommerceDiscountRelCreateDateComparator());
	}

	@Override
	public List<CommerceDiscountRel> getCategoriesByCommerceDiscountId(
		long commerceDiscountId, String name, int start, int end) {

		return dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommerceDiscountRelTable.INSTANCE
				).from(
					CommerceDiscountRelTable.INSTANCE
				).innerJoinON(
					AssetCategoryTable.INSTANCE,
					AssetCategoryTable.INSTANCE.categoryId.eq(
						CommerceDiscountRelTable.INSTANCE.classPK)
				),
				AssetCategory.class.getName(), commerceDiscountId, name,
				AssetCategoryTable.INSTANCE.name
			).limit(
				start, end
			));
	}

	@Override
	public int getCategoriesByCommerceDiscountIdCount(
		long commerceDiscountId, String name) {

		return dslQueryCount(
			_getGroupByStep(
				DSLQueryFactoryUtil.countDistinct(
					CommerceDiscountRelTable.INSTANCE.commerceDiscountRelId
				).from(
					CommerceDiscountRelTable.INSTANCE
				).innerJoinON(
					AssetCategoryTable.INSTANCE,
					AssetCategoryTable.INSTANCE.categoryId.eq(
						CommerceDiscountRelTable.INSTANCE.classPK)
				),
				AssetCategory.class.getName(), commerceDiscountId, name,
				AssetCategoryTable.INSTANCE.name));
	}

	@Override
	public long[] getClassPKs(long commerceDiscountId, String className) {
		return ListUtil.toLongArray(
			commerceDiscountRelPersistence.findByCD_CN(
				commerceDiscountId,
				classNameLocalService.getClassNameId(className)),
			CommerceDiscountRel::getClassPK);
	}

	@Override
	public List<CommerceDiscountRel> getCommerceDiscountRels(
		long commerceDiscountId, String className) {

		return commerceDiscountRelPersistence.findByCD_CN(
			commerceDiscountId,
			classNameLocalService.getClassNameId(className));
	}

	@Override
	public List<CommerceDiscountRel> getCommerceDiscountRels(
		long commerceDiscountId, String className, int start, int end,
		OrderByComparator<CommerceDiscountRel> orderByComparator) {

		return commerceDiscountRelPersistence.findByCD_CN(
			commerceDiscountId, classNameLocalService.getClassNameId(className),
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceDiscountRelsCount(
		long commerceDiscountId, String className) {

		return commerceDiscountRelPersistence.countByCD_CN(
			commerceDiscountId,
			classNameLocalService.getClassNameId(className));
	}

	@Override
	public List<CommerceDiscountRel>
		getCommercePricingClassesByCommerceDiscountId(
			long commerceDiscountId, String title, int start, int end) {

		return dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommerceDiscountRelTable.INSTANCE
				).from(
					CommerceDiscountRelTable.INSTANCE
				).innerJoinON(
					CommercePricingClassTable.INSTANCE,
					CommercePricingClassTable.INSTANCE.commercePricingClassId.
						eq(CommerceDiscountRelTable.INSTANCE.classPK)
				),
				CommercePricingClass.class.getName(), commerceDiscountId, title,
				CommercePricingClassTable.INSTANCE.title
			).limit(
				start, end
			));
	}

	@Override
	public int getCommercePricingClassesByCommerceDiscountIdCount(
		long commerceDiscountId, String title) {

		return dslQueryCount(
			_getGroupByStep(
				DSLQueryFactoryUtil.countDistinct(
					CommerceDiscountRelTable.INSTANCE.commerceDiscountRelId
				).from(
					CommerceDiscountRelTable.INSTANCE
				).innerJoinON(
					CommercePricingClassTable.INSTANCE,
					CommercePricingClassTable.INSTANCE.commercePricingClassId.
						eq(CommerceDiscountRelTable.INSTANCE.classPK)
				),
				CommercePricingClass.class.getName(), commerceDiscountId, title,
				CommercePricingClassTable.INSTANCE.title));
	}

	@Override
	public List<CommerceDiscountRel> getCPDefinitionsByCommerceDiscountId(
		long commerceDiscountId, String name, String languageId, int start,
		int end) {

		return dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommerceDiscountRelTable.INSTANCE
				).from(
					CommerceDiscountRelTable.INSTANCE
				).innerJoinON(
					CPDefinitionTable.INSTANCE,
					CPDefinitionTable.INSTANCE.CPDefinitionId.eq(
						CommerceDiscountRelTable.INSTANCE.classPK)
				).leftJoinOn(
					CPDefinitionLocalizationTable.INSTANCE,
					CPDefinitionTable.INSTANCE.CPDefinitionId.eq(
						CPDefinitionLocalizationTable.INSTANCE.CPDefinitionId
					).and(
						CPDefinitionLocalizationTable.INSTANCE.languageId.eq(
							languageId)
					)
				),
				CPDefinition.class.getName(), commerceDiscountId, name,
				CPDefinitionLocalizationTable.INSTANCE.name
			).limit(
				start, end
			));
	}

	@Override
	public int getCPDefinitionsByCommerceDiscountIdCount(
		long commerceDiscountId, String name, String languageId) {

		return dslQueryCount(
			_getGroupByStep(
				DSLQueryFactoryUtil.countDistinct(
					CommerceDiscountRelTable.INSTANCE.commerceDiscountRelId
				).from(
					CommerceDiscountRelTable.INSTANCE
				).innerJoinON(
					CPDefinitionTable.INSTANCE,
					CPDefinitionTable.INSTANCE.CPDefinitionId.eq(
						CommerceDiscountRelTable.INSTANCE.classPK)
				).leftJoinOn(
					CPDefinitionLocalizationTable.INSTANCE,
					CPDefinitionTable.INSTANCE.CPDefinitionId.eq(
						CPDefinitionLocalizationTable.INSTANCE.CPDefinitionId
					).and(
						CPDefinitionLocalizationTable.INSTANCE.languageId.eq(
							languageId)
					)
				),
				CPDefinition.class.getName(), commerceDiscountId, name,
				CPDefinitionLocalizationTable.INSTANCE.name));
	}

	@Override
	public List<CommerceDiscountRel> getCPInstancesByCommerceDiscountId(
		long commerceDiscountId, String sku, int start, int end) {

		return dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommerceDiscountRelTable.INSTANCE
				).from(
					CommerceDiscountRelTable.INSTANCE
				).innerJoinON(
					CPInstanceTable.INSTANCE,
					CPInstanceTable.INSTANCE.CPInstanceId.eq(
						CommerceDiscountRelTable.INSTANCE.classPK)
				),
				CPInstance.class.getName(), commerceDiscountId, sku,
				CPInstanceTable.INSTANCE.sku
			).limit(
				start, end
			));
	}

	@Override
	public int getCPInstancesByCommerceDiscountIdCount(
		long commerceDiscountId, String sku) {

		return dslQueryCount(
			_getGroupByStep(
				DSLQueryFactoryUtil.countDistinct(
					CommerceDiscountRelTable.INSTANCE.commerceDiscountRelId
				).from(
					CommerceDiscountRelTable.INSTANCE
				).innerJoinON(
					CPInstanceTable.INSTANCE,
					CPInstanceTable.INSTANCE.CPInstanceId.eq(
						CommerceDiscountRelTable.INSTANCE.classPK)
				),
				CPInstance.class.getName(), commerceDiscountId, sku,
				CPInstanceTable.INSTANCE.sku));
	}

	protected void reindexCommerceDiscount(long commerceDiscountId)
		throws PortalException {

		CommerceDiscount commerceDiscount =
			commerceDiscountLocalService.getCommerceDiscount(
				commerceDiscountId);

		Indexer<CommerceDiscount> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommerceDiscount.class);

		indexer.reindex(commerceDiscount);
	}

	private GroupByStep _getGroupByStep(
		JoinStep joinStep, String className, Long commerceDiscountId,
		String keywords, Expression<String> keywordsPredicateExpression) {

		return joinStep.where(
			() -> {
				Predicate predicate =
					CommerceDiscountRelTable.INSTANCE.commerceDiscountId.eq(
						commerceDiscountId
					).and(
						CommerceDiscountRelTable.INSTANCE.classNameId.eq(
							classNameLocalService.getClassNameId(className))
					);

				if (Validator.isNotNull(keywords)) {
					predicate = predicate.and(
						Predicate.withParentheses(
							_customSQL.getKeywordsPredicate(
								DSLFunctionFactoryUtil.lower(
									keywordsPredicateExpression),
								_customSQL.keywords(keywords, true))));
				}

				return predicate;
			});
	}

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}