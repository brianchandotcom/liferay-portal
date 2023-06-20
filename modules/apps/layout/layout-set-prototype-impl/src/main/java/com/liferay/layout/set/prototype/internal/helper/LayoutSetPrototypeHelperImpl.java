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

package com.liferay.layout.set.prototype.internal.helper;

import com.liferay.layout.set.prototype.helper.LayoutSetPrototypeHelper;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.LayoutSetPrototypeTable;
import com.liferay.portal.kernel.model.LayoutSetTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = LayoutSetPrototypeHelper.class)
public class LayoutSetPrototypeHelperImpl implements LayoutSetPrototypeHelper {

	@Override
	public List<Layout> getDuplicatedFriendlyURLLayouts(Layout layout)
		throws PortalException {

		Group group = layout.getGroup();

		if (group.isLayoutSetPrototype()) {
			return _getDuplicatedFriendlyURLSiteLayouts(layout);
		}

		LayoutSet layoutSet = layout.getLayoutSet();

		if (!layoutSet.isLayoutSetPrototypeLinkActive()) {
			return Collections.emptyList();
		}

		Layout conflictLayout = _getDuplicatedFriendlyURLPrototypeLayout(
			layout);

		if (conflictLayout != null) {
			return Collections.singletonList(conflictLayout);
		}

		return Collections.emptyList();
	}

	@Override
	public List<Long> getDuplicatedFriendlyURLPlids(LayoutSet layoutSet) {
		LayoutTable tempLayoutTable = LayoutTable.INSTANCE.as(
			"tempLayoutTable");

		return _layoutLocalService.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				LayoutTable.INSTANCE.plid
			).from(
				LayoutTable.INSTANCE
			).innerJoinON(
				LayoutSetTable.INSTANCE,
				LayoutSetTable.INSTANCE.companyId.eq(
					LayoutTable.INSTANCE.companyId
				).and(
					LayoutSetTable.INSTANCE.groupId.eq(
						LayoutTable.INSTANCE.groupId)
				).and(
					LayoutSetTable.INSTANCE.privateLayout.eq(
						LayoutTable.INSTANCE.privateLayout)
				)
			).innerJoinON(
				LayoutSetPrototypeTable.INSTANCE,
				LayoutSetPrototypeTable.INSTANCE.companyId.eq(
					LayoutSetTable.INSTANCE.companyId
				).and(
					LayoutSetPrototypeTable.INSTANCE.uuid.eq(
						LayoutSetTable.INSTANCE.layoutSetPrototypeUuid)
				)
			).innerJoinON(
				GroupTable.INSTANCE,
				GroupTable.INSTANCE.companyId.eq(
					LayoutSetPrototypeTable.INSTANCE.companyId
				).and(
					GroupTable.INSTANCE.classPK.eq(
						LayoutSetPrototypeTable.INSTANCE.layoutSetPrototypeId)
				)
			).innerJoinON(
				tempLayoutTable,
				tempLayoutTable.companyId.eq(
					GroupTable.INSTANCE.companyId
				).and(
					tempLayoutTable.groupId.eq(GroupTable.INSTANCE.groupId)
				).and(
					tempLayoutTable.friendlyURL.eq(
						LayoutTable.INSTANCE.friendlyURL)
				)
			).where(
				LayoutTable.INSTANCE.groupId.eq(
					layoutSet.getGroupId()
				).and(
					LayoutTable.INSTANCE.system.eq(false)
				).and(
					LayoutTable.INSTANCE.sourcePrototypeLayoutUuid.isNull()
				)
			));
	}

	@Override
	public List<Long> getDuplicatedFriendlyURLPlids(
			LayoutSetPrototype layoutSetPrototype)
		throws PortalException {

		LayoutTable tempLayoutTable = LayoutTable.INSTANCE.as(
			"tempLayoutTable");

		return _layoutLocalService.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				LayoutTable.INSTANCE.plid
			).from(
				LayoutTable.INSTANCE
			).innerJoinON(
				GroupTable.INSTANCE,
				GroupTable.INSTANCE.companyId.eq(
					LayoutTable.INSTANCE.companyId
				).and(
					GroupTable.INSTANCE.groupId.eq(LayoutTable.INSTANCE.groupId)
				)
			).innerJoinON(
				LayoutSetPrototypeTable.INSTANCE,
				LayoutSetPrototypeTable.INSTANCE.companyId.eq(
					GroupTable.INSTANCE.companyId
				).and(
					LayoutSetPrototypeTable.INSTANCE.layoutSetPrototypeId.eq(
						GroupTable.INSTANCE.classPK)
				)
			).innerJoinON(
				LayoutSetTable.INSTANCE,
				LayoutSetTable.INSTANCE.companyId.eq(
					LayoutSetPrototypeTable.INSTANCE.companyId
				).and(
					LayoutSetTable.INSTANCE.layoutSetPrototypeUuid.eq(
						LayoutSetPrototypeTable.INSTANCE.uuid)
				)
			).innerJoinON(
				tempLayoutTable,
				tempLayoutTable.companyId.eq(
					LayoutSetTable.INSTANCE.companyId
				).and(
					tempLayoutTable.groupId.eq(LayoutSetTable.INSTANCE.groupId)
				).and(
					tempLayoutTable.privateLayout.eq(
						LayoutSetTable.INSTANCE.privateLayout)
				).and(
					tempLayoutTable.friendlyURL.eq(
						LayoutTable.INSTANCE.friendlyURL)
				).and(
					tempLayoutTable.sourcePrototypeLayoutUuid.isNull()
				)
			).where(
				LayoutTable.INSTANCE.groupId.eq(
					layoutSetPrototype.getGroupId()
				).and(
					LayoutTable.INSTANCE.system.eq(false)
				)
			));
	}

	@Override
	public boolean hasDuplicatedFriendlyURLs(
			String layoutUuid, long groupId, boolean privateLayout,
			String friendlyURL)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		if (group.isLayoutSetPrototype()) {
			long count = _getDuplicatedFriendlyURLSiteLayoutsCount(
				layoutUuid, group.getCompanyId(), group.getGroupId(),
				friendlyURL);

			if (count > 0) {
				return true;
			}

			return false;
		}

		return _hasDuplicatedFriendlyURLPrototypeLayout(
			layoutUuid, groupId, privateLayout, friendlyURL);
	}

	private Layout _getDuplicatedFriendlyURLPrototypeLayout(Layout layout)
		throws PortalException {

		LayoutSet layoutSet = layout.getLayoutSet();

		if (!layoutSet.isLayoutSetPrototypeLinkActive()) {
			return null;
		}

		LayoutSetPrototype layoutSetPrototype =
			_layoutSetPrototypeLocalService.
				getLayoutSetPrototypeByUuidAndCompanyId(
					layoutSet.getLayoutSetPrototypeUuid(),
					layoutSet.getCompanyId());

		LayoutSet prototypeLayoutSet = layoutSetPrototype.getLayoutSet();

		LayoutFriendlyURL layoutFriendlyURL =
			_layoutFriendlyURLLocalService.fetchFirstLayoutFriendlyURL(
				prototypeLayoutSet.getGroupId(),
				prototypeLayoutSet.isPrivateLayout(), layout.getFriendlyURL());

		if (layoutFriendlyURL == null) {
			return null;
		}

		Layout foundLayout = _layoutLocalService.getLayout(
			layoutFriendlyURL.getPlid());

		String sourcePrototypeLayoutUuid =
			layout.getSourcePrototypeLayoutUuid();

		if (Validator.isNotNull(layout.getSourcePrototypeLayoutUuid()) &&
			sourcePrototypeLayoutUuid.equals(foundLayout.getUuid())) {

			return null;
		}

		return foundLayout;
	}

	private List<Layout> _getDuplicatedFriendlyURLSiteLayouts(Layout layout)
		throws PortalException {

		return _layoutLocalService.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				LayoutTable.INSTANCE
			).from(
				LayoutTable.INSTANCE
			).innerJoinON(
				LayoutSetTable.INSTANCE,
				LayoutSetTable.INSTANCE.companyId.eq(
					LayoutTable.INSTANCE.companyId
				).and(
					LayoutSetTable.INSTANCE.groupId.eq(
						LayoutTable.INSTANCE.groupId)
				).and(
					LayoutSetTable.INSTANCE.privateLayout.eq(
						LayoutTable.INSTANCE.privateLayout)
				)
			).innerJoinON(
				LayoutSetPrototypeTable.INSTANCE,
				LayoutSetPrototypeTable.INSTANCE.companyId.eq(
					LayoutSetTable.INSTANCE.companyId
				).and(
					LayoutSetPrototypeTable.INSTANCE.uuid.eq(
						LayoutSetTable.INSTANCE.layoutSetPrototypeUuid)
				)
			).innerJoinON(
				GroupTable.INSTANCE,
				GroupTable.INSTANCE.companyId.eq(
					LayoutSetPrototypeTable.INSTANCE.companyId
				).and(
					GroupTable.INSTANCE.classPK.eq(
						LayoutSetPrototypeTable.INSTANCE.layoutSetPrototypeId)
				)
			).where(
				LayoutSetTable.INSTANCE.companyId.eq(
					layout.getCompanyId()
				).and(
					LayoutTable.INSTANCE.friendlyURL.eq(layout.getFriendlyURL())
				).and(
					LayoutTable.INSTANCE.sourcePrototypeLayoutUuid.isNull()
				).and(
					GroupTable.INSTANCE.groupId.eq(layout.getGroupId())
				)
			));
	}

	private long _getDuplicatedFriendlyURLSiteLayoutsCount(
			String layoutUuid, long companyId, long groupId, String friendlyURL)
		throws PortalException {

		Predicate sourcePrototypeLayoutUuidPredicate =
			LayoutTable.INSTANCE.sourcePrototypeLayoutUuid.isNull();

		if (Validator.isNotNull(layoutUuid)) {
			sourcePrototypeLayoutUuidPredicate = Predicate.withParentheses(
				sourcePrototypeLayoutUuidPredicate.or(
					LayoutTable.INSTANCE.sourcePrototypeLayoutUuid.neq(
						layoutUuid)));
		}

		return _layoutLocalService.dslQuery(
			DSLQueryFactoryUtil.count(
			).from(
				LayoutTable.INSTANCE
			).innerJoinON(
				LayoutSetTable.INSTANCE,
				LayoutSetTable.INSTANCE.companyId.eq(
					LayoutTable.INSTANCE.companyId
				).and(
					LayoutSetTable.INSTANCE.groupId.eq(
						LayoutTable.INSTANCE.groupId)
				).and(
					LayoutSetTable.INSTANCE.privateLayout.eq(
						LayoutTable.INSTANCE.privateLayout)
				)
			).innerJoinON(
				LayoutSetPrototypeTable.INSTANCE,
				LayoutSetPrototypeTable.INSTANCE.companyId.eq(
					LayoutSetTable.INSTANCE.companyId
				).and(
					LayoutSetPrototypeTable.INSTANCE.uuid.eq(
						LayoutSetTable.INSTANCE.layoutSetPrototypeUuid)
				)
			).innerJoinON(
				GroupTable.INSTANCE,
				GroupTable.INSTANCE.companyId.eq(
					LayoutSetPrototypeTable.INSTANCE.companyId
				).and(
					GroupTable.INSTANCE.classPK.eq(
						LayoutSetPrototypeTable.INSTANCE.layoutSetPrototypeId)
				)
			).where(
				LayoutTable.INSTANCE.companyId.eq(
					companyId
				).and(
					GroupTable.INSTANCE.groupId.eq(groupId)
				).and(
					LayoutTable.INSTANCE.friendlyURL.eq(friendlyURL)
				).and(
					sourcePrototypeLayoutUuidPredicate
				)
			));
	}

	private boolean _hasDuplicatedFriendlyURLPrototypeLayout(
			String sourcePrototypeLayoutUuid, long groupId,
			boolean privateLayout, String friendlyURL)
		throws PortalException {

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			groupId, privateLayout);

		if (!layoutSet.isLayoutSetPrototypeLinkActive()) {
			return false;
		}

		LayoutSetPrototype layoutSetPrototype =
			_layoutSetPrototypeLocalService.
				getLayoutSetPrototypeByUuidAndCompanyId(
					layoutSet.getLayoutSetPrototypeUuid(),
					layoutSet.getCompanyId());

		LayoutSet prototypeLayoutSet = layoutSetPrototype.getLayoutSet();

		LayoutFriendlyURL layoutFriendlyURL =
			_layoutFriendlyURLLocalService.fetchFirstLayoutFriendlyURL(
				prototypeLayoutSet.getGroupId(),
				prototypeLayoutSet.isPrivateLayout(), friendlyURL);

		if (layoutFriendlyURL == null) {
			return false;
		}

		Layout foundLayout = _layoutLocalService.getLayout(
			layoutFriendlyURL.getPlid());

		if (Validator.isNotNull(sourcePrototypeLayoutUuid) &&
			sourcePrototypeLayoutUuid.equals(foundLayout.getUuid())) {

			return false;
		}

		return true;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

}