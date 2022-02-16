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

package com.liferay.change.tracking.internal.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PortletPreferenceValueTable;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.persistence.PortletPreferenceValuePersistence;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = ConstraintResolver.class)
public class PortletPreferenceConstraintResolver
	implements ConstraintResolver<PortletPreferences> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-widget-preferences";
	}

	@Override
	public Class<PortletPreferences> getModelClass() {
		return PortletPreferences.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "duplicate-widget-preferences-were-removed";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			locale, PortletPreferenceConstraintResolver.class);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"ownerId", "ownerType", "plid", "portletId"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<PortletPreferences>
				constraintResolverContext)
		throws PortalException {

		PortletPreferences sourcePortletPreferences =
			constraintResolverContext.getSourceCTModel();

		_portletPreferencesLocalService.deletePortletPreferences(
			constraintResolverContext.getSourceCTModel());

		List<Long> portletPreferenceValueIds =
			_portletPreferenceValueLocalService.dslQuery(
				DSLQueryFactoryUtil.select(
					PortletPreferenceValueTable.INSTANCE.
						portletPreferenceValueId
				).from(
					PortletPreferenceValueTable.INSTANCE
				).where(
					PortletPreferenceValueTable.INSTANCE.portletPreferencesId.
						eq(
							sourcePortletPreferences.getPortletPreferencesId()
						).and(
							PortletPreferenceValueTable.INSTANCE.ctCollectionId.
								eq(sourcePortletPreferences.getCtCollectionId())
						)
				));

		for (long portletPreferenceValueId : portletPreferenceValueIds) {
			_portletPreferenceValueLocalService.deletePortletPreferenceValue(
				portletPreferenceValueId);
		}
	}

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private PortletPreferenceValueLocalService
		_portletPreferenceValueLocalService;

	@Reference
	private PortletPreferenceValuePersistence
		_portletPreferenceValuePersistence;

}