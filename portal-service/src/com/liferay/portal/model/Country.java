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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the Country service. Represents a row in the &quot;Country&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see CountryModel
 * @see com.liferay.portal.model.impl.CountryImpl
 * @see com.liferay.portal.model.impl.CountryModelImpl
 * @generated
 */
@ProviderType
public interface Country extends CountryModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.CountryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Country, Long> COUNTRY_ID_ACCESSOR = new Accessor<Country, Long>() {
			@Override
			public Long get(Country country) {
				return country.getCountryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Country> getTypeClass() {
				return Country.class;
			}
		};

	public java.lang.String getName(java.util.Locale locale);

	public java.lang.String getNameCurrentLanguageId();

	@com.liferay.portal.kernel.json.JSON()
	public java.lang.String getNameCurrentValue();

	public void setNameCurrentLanguageId(java.lang.String languageId);
}