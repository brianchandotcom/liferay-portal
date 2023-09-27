/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.highlight;

import com.liferay.portal.search.highlight.FieldConfig;
import com.liferay.portal.search.highlight.FieldConfigBuilder;
import com.liferay.portal.search.highlight.Highlight;
import com.liferay.portal.search.highlight.HighlightBuilder;
import com.liferay.portal.search.highlight.HighlightBuilderFactory;
import com.liferay.portal.search.highlight.Highlights;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 */
@Component(service = Highlights.class)
public class HighlightsImpl implements Highlights {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             HighlightBuilderFactory#builder()}
	 */
	@Deprecated
	@Override
	public HighlightBuilder builder() {
		return new HighlightImpl.HighlightBuilderImpl();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public FieldConfig fieldConfig(String field) {
		return fieldConfigBuilder(
		).field(
			field
		).build();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             FieldConfigBuilderFactory#builder(String)}
	 */
	@Deprecated
	@Override
	public FieldConfigBuilder fieldConfigBuilder() {
		return new FieldConfigImpl.FieldConfigBuilderImpl(null);
	}

	@Override
	public Highlight highlight(FieldConfig fieldConfig) {
		return highlightBuilderFactory.builder(
		).addFieldConfig(
			fieldConfig
		).build();
	}

	@Reference
	protected HighlightBuilderFactory highlightBuilderFactory;

}