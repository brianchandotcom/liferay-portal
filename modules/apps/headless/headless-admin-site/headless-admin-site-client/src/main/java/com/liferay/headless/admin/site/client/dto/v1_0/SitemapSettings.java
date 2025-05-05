/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.SitemapSettingsSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class SitemapSettings implements Cloneable, Serializable {

	public static SitemapSettings toDTO(String json) {
		return SitemapSettingsSerDes.toDTO(json);
	}

	public ChangeFrequency getChangeFrequency() {
		return changeFrequency;
	}

	public String getChangeFrequencyAsString() {
		if (changeFrequency == null) {
			return null;
		}

		return changeFrequency.toString();
	}

	public void setChangeFrequency(ChangeFrequency changeFrequency) {
		this.changeFrequency = changeFrequency;
	}

	public void setChangeFrequency(
		UnsafeSupplier<ChangeFrequency, Exception>
			changeFrequencyUnsafeSupplier) {

		try {
			changeFrequency = changeFrequencyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ChangeFrequency changeFrequency;

	public Boolean getInclude() {
		return include;
	}

	public void setInclude(Boolean include) {
		this.include = include;
	}

	public void setInclude(
		UnsafeSupplier<Boolean, Exception> includeUnsafeSupplier) {

		try {
			include = includeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean include;

	public Boolean getIncludeChildSitePages() {
		return includeChildSitePages;
	}

	public void setIncludeChildSitePages(Boolean includeChildSitePages) {
		this.includeChildSitePages = includeChildSitePages;
	}

	public void setIncludeChildSitePages(
		UnsafeSupplier<Boolean, Exception>
			includeChildSitePagesUnsafeSupplier) {

		try {
			includeChildSitePages = includeChildSitePagesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean includeChildSitePages;

	public Double getPagePriority() {
		return pagePriority;
	}

	public void setPagePriority(Double pagePriority) {
		this.pagePriority = pagePriority;
	}

	public void setPagePriority(
		UnsafeSupplier<Double, Exception> pagePriorityUnsafeSupplier) {

		try {
			pagePriority = pagePriorityUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double pagePriority;

	@Override
	public SitemapSettings clone() throws CloneNotSupportedException {
		return (SitemapSettings)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SitemapSettings)) {
			return false;
		}

		SitemapSettings sitemapSettings = (SitemapSettings)object;

		return Objects.equals(toString(), sitemapSettings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SitemapSettingsSerDes.toJSON(this);
	}

	public static enum ChangeFrequency {

		ALWAYS("Always"), HOURLY("Hourly"), DAILY("Daily"), WEEKLY("Weekly"),
		MONTHLY("Monthly"), YEARLY("Yearly"), NEVER("Never");

		public static ChangeFrequency create(String value) {
			for (ChangeFrequency changeFrequency : values()) {
				if (Objects.equals(changeFrequency.getValue(), value) ||
					Objects.equals(changeFrequency.name(), value)) {

					return changeFrequency;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private ChangeFrequency(String value) {
			_value = value;
		}

		private final String _value;

	}

}