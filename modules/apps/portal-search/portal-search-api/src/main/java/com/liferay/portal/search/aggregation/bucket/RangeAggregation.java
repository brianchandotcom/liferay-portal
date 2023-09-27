/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.bucket;

import com.liferay.portal.search.aggregation.FieldAggregation;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface RangeAggregation extends FieldAggregation {

	public void addRange(Range range);

	public void addRanges(Range... ranges);

	public void addUnboundedFrom(Double from);

	public void addUnboundedFrom(String key, Double from);

	public void addUnboundedTo(Double to);

	public void addUnboundedTo(String key, Double to);

	public String getFormat();

	public Boolean getKeyed();

	public List<Range> getRanges();

	public void setFormat(String format);

	public void setKeyed(Boolean keyed);

}