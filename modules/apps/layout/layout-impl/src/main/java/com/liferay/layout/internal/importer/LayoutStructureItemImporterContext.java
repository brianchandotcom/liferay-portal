/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.importer;

import com.liferay.portal.kernel.model.Layout;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutStructureItemImporterContext {

	public LayoutStructureItemImporterContext(
		Layout layout, double pageDefinitionVersion, String parentItemId,
		int position, long segmentsExperienceId) {

		_layout = layout;
		_pageDefinitionVersion = pageDefinitionVersion;
		_parentItemId = parentItemId;
		_position = position;
		_segmentsExperienceId = segmentsExperienceId;
	}

	public Layout getLayout() {
		return _layout;
	}

	public double getPageDefinitionVersion() {
		return _pageDefinitionVersion;
	}

	public String getParentItemId() {
		return _parentItemId;
	}

	public int getPosition() {
		return _position;
	}

	public long getSegmentsExperienceId() {
		return _segmentsExperienceId;
	}

	private final Layout _layout;
	private final double _pageDefinitionVersion;
	private final String _parentItemId;
	private final int _position;
	private final long _segmentsExperienceId;

}