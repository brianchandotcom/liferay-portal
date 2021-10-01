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

package com.liferay.commerce.order.rule.service.impl;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryTable;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupTable;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.model.CommerceOrderTypeTable;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRelTable;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryTable;
import com.liferay.commerce.order.rule.service.base.CommerceOrderRuleEntryRelLocalServiceBaseImpl;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelTable;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel",
	service = AopService.class
)
public class CommerceOrderRuleEntryRelLocalServiceImpl
	extends CommerceOrderRuleEntryRelLocalServiceBaseImpl {

	@Override
	public CommerceOrderRuleEntryRel addCommerceOrderRuleEntryRel(
			long userId, String className, long classPK,
			long commerceOrderRuleEntryId)
		throws PortalException {

		// Commerce order rule entry rel

		User user = userLocalService.getUser(userId);

		long classNameId = classNameLocalService.getClassNameId(className);

		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel =
			commerceOrderRuleEntryRelPersistence.create(
				counterLocalService.increment());

		commerceOrderRuleEntryRel.setCompanyId(user.getCompanyId());
		commerceOrderRuleEntryRel.setUserId(user.getUserId());
		commerceOrderRuleEntryRel.setUserName(user.getFullName());
		commerceOrderRuleEntryRel.setClassNameId(classNameId);
		commerceOrderRuleEntryRel.setClassPK(classPK);
		commerceOrderRuleEntryRel.setCommerceOrderRuleEntryId(
			commerceOrderRuleEntryId);

		commerceOrderRuleEntryRel = commerceOrderRuleEntryRelPersistence.update(
			commerceOrderRuleEntryRel);

		// Commerce order rule entry

		reindexCommerceOrderRuleEntry(commerceOrderRuleEntryId);

		return commerceOrderRuleEntryRel;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceOrderRuleEntryRel deleteCommerceOrderRuleEntryRel(
			CommerceOrderRuleEntryRel commerceOrderRuleEntryRel)
		throws PortalException {

		commerceOrderRuleEntryRelPersistence.remove(commerceOrderRuleEntryRel);

		reindexCommerceOrderRuleEntry(
			commerceOrderRuleEntryRel.getCommerceOrderRuleEntryId());

		return commerceOrderRuleEntryRel;
	}

	@Override
	public CommerceOrderRuleEntryRel deleteCommerceOrderRuleEntryRel(
			long commerceOrderRuleEntryRelId)
		throws PortalException {

		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel =
			commerceOrderRuleEntryRelPersistence.findByPrimaryKey(
				commerceOrderRuleEntryRelId);

		return commerceOrderRuleEntryRelLocalService.
			deleteCommerceOrderRuleEntryRel(commerceOrderRuleEntryRel);
	}

	@Override
	public void deleteCommerceOrderRuleEntryRels(long commerceOrderRuleEntryId)
		throws PortalException {

		List<CommerceOrderRuleEntryRel> commerceOrderRuleEntryRels =
			commerceOrderRuleEntryRelPersistence.findByCommerceOrderRuleEntryId(
				commerceOrderRuleEntryId);

		for (CommerceOrderRuleEntryRel commerceOrderRuleEntryRel :
				commerceOrderRuleEntryRels) {

			commerceOrderRuleEntryRelLocalService.
				deleteCommerceOrderRuleEntryRel(commerceOrderRuleEntryRel);
		}
	}

	@Override
	public CommerceOrderRuleEntryRel fetchCommerceOrderRuleEntryRel(
		String className, long classPK, long commerceOrderRuleEntryId) {

		return commerceOrderRuleEntryRelPersistence.fetchByC_C_C(
			classNameLocalService.getClassNameId(className), classPK,
			commerceOrderRuleEntryId);
	}

	@Override
	public List<CommerceOrderRuleEntryRel>
			getCommerceOrderRuleEntryAccountEntryRels(
				long commerceOrderRuleEntryId, String keywords, int start,
				int end)
		throws PortalException {

		return dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommerceOrderRuleEntryRelTable.INSTANCE),
				AccountEntryTable.INSTANCE,
				AccountEntryTable.INSTANCE.accountEntryId.eq(
					CommerceOrderRuleEntryRelTable.INSTANCE.classPK),
				commerceOrderRuleEntryId, AccountEntry.class.getName(),
				keywords, AccountEntryTable.INSTANCE.name
			).limit(
				start, end
			));
	}

	@Override
	public int getCommerceOrderRuleEntryAccountEntryRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws PortalException {

		return dslQueryCount(
			_getGroupByStep(
				DSLQueryFactoryUtil.countDistinct(
					CommerceOrderRuleEntryRelTable.INSTANCE.
						commerceOrderRuleEntryId),
				AccountEntryTable.INSTANCE,
				AccountEntryTable.INSTANCE.accountEntryId.eq(
					CommerceOrderRuleEntryRelTable.INSTANCE.classPK),
				commerceOrderRuleEntryId, AccountEntry.class.getName(),
				keywords, AccountEntryTable.INSTANCE.name));
	}

	@Override
	public List<CommerceOrderRuleEntryRel>
			getCommerceOrderRuleEntryAccountGroupRels(
				long commerceOrderRuleEntryId, String keywords, int start,
				int end)
		throws PortalException {

		return dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommerceOrderRuleEntryRelTable.INSTANCE),
				AccountGroupTable.INSTANCE,
				AccountGroupTable.INSTANCE.accountGroupId.eq(
					CommerceOrderRuleEntryRelTable.INSTANCE.classPK),
				commerceOrderRuleEntryId, AccountGroup.class.getName(),
				keywords, AccountGroupTable.INSTANCE.name
			).limit(
				start, end
			));
	}

	@Override
	public int getCommerceOrderRuleEntryAccountGroupRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws PortalException {

		return dslQueryCount(
			_getGroupByStep(
				DSLQueryFactoryUtil.countDistinct(
					CommerceOrderRuleEntryRelTable.INSTANCE.
						commerceOrderRuleEntryId),
				AccountGroupTable.INSTANCE,
				AccountGroupTable.INSTANCE.accountGroupId.eq(
					CommerceOrderRuleEntryRelTable.INSTANCE.classPK),
				commerceOrderRuleEntryId, AccountGroup.class.getName(),
				keywords, AccountGroupTable.INSTANCE.name));
	}

	@Override
	public List<CommerceOrderRuleEntryRel>
			getCommerceOrderRuleEntryCommerceChannelRels(
				long commerceOrderRuleEntryId, String keywords, int start,
				int end)
		throws PortalException {

		return dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommerceOrderRuleEntryRelTable.INSTANCE),
				CommerceChannelTable.INSTANCE,
				CommerceChannelTable.INSTANCE.commerceChannelId.eq(
					CommerceOrderRuleEntryRelTable.INSTANCE.classPK),
				commerceOrderRuleEntryId, CommerceChannel.class.getName(),
				keywords, CommerceChannelTable.INSTANCE.name
			).limit(
				start, end
			));
	}

	@Override
	public int getCommerceOrderRuleEntryCommerceChannelRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws PortalException {

		return dslQueryCount(
			_getGroupByStep(
				DSLQueryFactoryUtil.countDistinct(
					CommerceOrderRuleEntryRelTable.INSTANCE.
						commerceOrderRuleEntryId),
				CommerceChannelTable.INSTANCE,
				CommerceChannelTable.INSTANCE.commerceChannelId.eq(
					CommerceOrderRuleEntryRelTable.INSTANCE.classPK),
				commerceOrderRuleEntryId, CommerceChannel.class.getName(),
				keywords, CommerceChannelTable.INSTANCE.name));
	}

	@Override
	public List<CommerceOrderRuleEntryRel>
			getCommerceOrderRuleEntryCommerceOrderTypeRels(
				long commerceOrderRuleEntryId, String keywords, int start,
				int end)
		throws PortalException {

		return dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommerceOrderRuleEntryRelTable.INSTANCE),
				CommerceOrderTypeTable.INSTANCE,
				CommerceOrderTypeTable.INSTANCE.commerceOrderTypeId.eq(
					CommerceOrderRuleEntryRelTable.INSTANCE.classPK),
				commerceOrderRuleEntryId, CommerceOrderType.class.getName(),
				keywords, CommerceOrderTypeTable.INSTANCE.name
			).limit(
				start, end
			));
	}

	@Override
	public int getCommerceOrderRuleEntryCommerceOrderTypeRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws PortalException {

		return dslQueryCount(
			_getGroupByStep(
				DSLQueryFactoryUtil.countDistinct(
					CommerceOrderRuleEntryRelTable.INSTANCE.
						commerceOrderRuleEntryId),
				CommerceOrderTypeTable.INSTANCE,
				CommerceOrderTypeTable.INSTANCE.commerceOrderTypeId.eq(
					CommerceOrderRuleEntryRelTable.INSTANCE.classPK),
				commerceOrderRuleEntryId, CommerceOrderType.class.getName(),
				keywords, CommerceOrderTypeTable.INSTANCE.name));
	}

	@Override
	public List<CommerceOrderRuleEntryRel> getCommerceOrderRuleEntryRels(
		long commerceOrderRuleEntryId) {

		return commerceOrderRuleEntryRelPersistence.
			findByCommerceOrderRuleEntryId(commerceOrderRuleEntryId);
	}

	@Override
	public List<CommerceOrderRuleEntryRel> getCommerceOrderRuleEntryRels(
		long commerceOrderRuleEntryId, int start, int end,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator) {

		return commerceOrderRuleEntryRelPersistence.
			findByCommerceOrderRuleEntryId(
				commerceOrderRuleEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceOrderRuleEntryRelsCount(
		long commerceOrderRuleEntryId) {

		return commerceOrderRuleEntryRelPersistence.
			countByCommerceOrderRuleEntryId(commerceOrderRuleEntryId);
	}

	protected void reindexCommerceOrderRuleEntry(long commerceOrderRuleEntryId)
		throws PortalException {

		Indexer<CommerceOrderRuleEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				CommerceOrderRuleEntry.class);

		indexer.reindex(
			CommerceOrderRuleEntry.class.getName(), commerceOrderRuleEntryId);
	}

	private GroupByStep _getGroupByStep(
			FromStep fromStep, Table innerJoinTable,
			Predicate innerJoinPredicate, Long commerceOrderRuleEntryId,
			String className, String keywords,
			Expression<String> keywordsPredicateExpression)
		throws PortalException {

		JoinStep joinStep = fromStep.from(
			CommerceOrderRuleEntryRelTable.INSTANCE
		).innerJoinON(
			CommerceOrderRuleEntryTable.INSTANCE,
			CommerceOrderRuleEntryTable.INSTANCE.commerceOrderRuleEntryId.eq(
				CommerceOrderRuleEntryRelTable.INSTANCE.
					commerceOrderRuleEntryId)
		).innerJoinON(
			innerJoinTable, innerJoinPredicate
		);

		return joinStep.where(
			() -> {
				Predicate predicate =
					CommerceOrderRuleEntryRelTable.INSTANCE.
						commerceOrderRuleEntryId.eq(
							commerceOrderRuleEntryId
						).and(
							CommerceOrderRuleEntryRelTable.INSTANCE.classNameId.
								eq(
									classNameLocalService.getClassNameId(
										className))
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

	@Reference
	private CustomSQL _customSQL;

}