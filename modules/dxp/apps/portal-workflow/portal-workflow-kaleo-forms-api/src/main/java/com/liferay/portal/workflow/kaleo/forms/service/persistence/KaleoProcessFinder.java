/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.forms.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Marcellus Tavares
 * @generated
 */
@ProviderType
public interface KaleoProcessFinder {
	public int countByKeywords(long groupId, String keywords);

	public int countByG_N_D(long groupId, String name, String description,
		boolean andOperator);

	public int filterCountByKeywords(long groupId, String keywords);

	public int filterCountByG_N_D(long groupId, String name,
		String description, boolean andOperator);

	public java.util.List<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> filterFindByKeywords(
		long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> orderByComparator);

	public java.util.List<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> filterFindByG_N_D(
		long groupId, String name, String description, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> orderByComparator);

	public java.util.List<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> filterFindByG_N_D(
		long groupId, String[] names, String[] descriptions,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> orderByComparator);

	public java.util.List<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> findByKeywords(
		long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> orderByComparator);

	public java.util.List<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> findByG_N_D(
		long groupId, String name, String description, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> orderByComparator);

	public java.util.List<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> findByG_N_D(
		long groupId, String[] names, String[] descriptions,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> orderByComparator);
}