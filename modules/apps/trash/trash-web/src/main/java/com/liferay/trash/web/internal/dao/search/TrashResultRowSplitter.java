/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.web.internal.dao.search;

import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides an implementation of <code>ResultRowSplitter</code> (in
 * <code>com.liferay.portal.kernel</code>) for the {@link
 * com.liferay.trash.web.internal.portlet.TrashPortlet}. This class renders
 * search results in the <code>SearchIteratorTag</code> (in
 * <code>com.liferay.util.taglib</code>) using the <code>TrashHandler</code> (in
 * <code>com.liferay.portal.kernel</code>) corresponding to each Recycle Bin
 * entry.
 *
 * @author Eudaldo Alonso
 */
public class TrashResultRowSplitter implements ResultRowSplitter {

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		List<ResultRow> trashContainedResultRows = new ArrayList<>();
		List<ResultRow> trashContainerResultRows = new ArrayList<>();

		String containerModelName = null;
		String containedModelName = null;

		for (ResultRow resultRow : resultRows) {
			TrashedModel trashedModel = (TrashedModel)resultRow.getObject();

			ClassedModel classedModel = (ClassedModel)trashedModel;

			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					classedModel.getModelClassName());

			if (Validator.isNull(containerModelName) &&
				Validator.isNull(containedModelName)) {

				containerModelName = trashHandler.getTrashContainerModelName();
				containedModelName = trashHandler.getTrashContainedModelName();
			}

			if (trashHandler.isContainerModel()) {
				trashContainerResultRows.add(resultRow);
			}
			else {
				trashContainedResultRows.add(resultRow);
			}
		}

		if (!trashContainerResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					containerModelName, trashContainerResultRows));
		}

		if (!trashContainedResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					containedModelName, trashContainedResultRows));
		}

		return resultRowSplitterEntries;
	}

}