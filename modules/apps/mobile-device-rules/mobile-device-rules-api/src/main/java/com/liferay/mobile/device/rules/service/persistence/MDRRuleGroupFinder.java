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

package com.liferay.mobile.device.rules.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Edward C. Han
 * @generated
 */
@ProviderType
public interface MDRRuleGroupFinder {

	public int countByKeywords(
		long groupId, String keywords,
		java.util.LinkedHashMap<String, Object> params);

	public int countByG_N(
		long groupId, String name,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator);

	public int countByG_N(
		long groupId, String[] names,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator);

	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup>
		findByKeywords(
			long groupId, String keywords,
			java.util.LinkedHashMap<String, Object> params, int start, int end);

	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup>
		findByKeywords(
			long groupId, String keywords,
			java.util.LinkedHashMap<String, Object> params, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.mobile.device.rules.model.MDRRuleGroup> obc);

	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup>
		findByG_N(
			long groupId, String name,
			java.util.LinkedHashMap<String, Object> params,
			boolean andOperator);

	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup>
		findByG_N(
			long groupId, String name,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end);

	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup>
		findByG_N(
			long groupId, String[] names,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end);

	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup>
		findByG_N(
			long groupId, String[] names,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.mobile.device.rules.model.MDRRuleGroup> obc);

}