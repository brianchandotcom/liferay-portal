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

package com.liferay.portal.reports.engine.console.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface DefinitionFinder {
	public int countByG_S_N_D_RN(long groupId, String name, String description,
		long sourceId, String reportName, boolean andOperator);

	public int filterCountByG_S_N_D_RN(long groupId, String name,
		String description, long sourceId, String reportName,
		boolean andOperator);

	public java.util.List<com.liferay.portal.reports.engine.console.model.Definition> filterFindByG_S_N_D_RN(
		long groupId, String name, String description, long sourceId,
		String reportName, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.reports.engine.console.model.Definition> orderByComparator);

	public java.util.List<com.liferay.portal.reports.engine.console.model.Definition> findByG_S_N_D_RN(
		long groupId, String name, String description, long sourceId,
		String reportName, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.reports.engine.console.model.Definition> orderByComparator);
}