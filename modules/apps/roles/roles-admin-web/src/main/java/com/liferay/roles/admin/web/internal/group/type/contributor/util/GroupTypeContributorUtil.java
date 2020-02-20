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

package com.liferay.roles.admin.web.internal.group.type.contributor.util;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.roles.admin.group.type.contributor.GroupTypeContributor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = {})
public class GroupTypeContributorUtil {

	public static long[] getClassNameIds() {
		Stream<GroupTypeContributor> stream = _groupTypeContributors.stream();

		return ListUtil.toLongArray(
			stream.filter(
				GroupTypeContributor::isEnabled
			).map(
				GroupTypeContributor::getClassName
			).map(
				PortalUtil::getClassNameId
			).collect(
				Collectors.toList()
			),
			Long::valueOf);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	public void addGroupTypeContributor(
		GroupTypeContributor groupTypeContributor) {

		_groupTypeContributors.add(groupTypeContributor);
	}

	protected void removeGroupTypeContributor(
		GroupTypeContributor groupTypeContributor) {

		_groupTypeContributors.remove(groupTypeContributor);
	}

	private static final List<GroupTypeContributor> _groupTypeContributors =
		new ArrayList<>();

}