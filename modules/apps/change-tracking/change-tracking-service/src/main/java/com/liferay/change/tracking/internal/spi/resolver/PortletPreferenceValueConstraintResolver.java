/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PortletPreferenceValue;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = ConstraintResolver.class)
public class PortletPreferenceValueConstraintResolver
	implements ConstraintResolver<PortletPreferenceValue> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-widget-preference-value";
	}

	@Override
	public Class<PortletPreferenceValue> getModelClass() {
		return PortletPreferenceValue.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "duplicate-widget-preference-value-was-removed";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			locale, PortletPreferenceValueConstraintResolver.class);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"portletPreferencesId", "index_", "name"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<PortletPreferenceValue>
				constraintResolverContext)
		throws PortalException {

		PortletPreferenceValue sourcePortletPreferenceValue =
			constraintResolverContext.getSourceCTModel();

		_portletPreferenceValueLocalService.deletePortletPreferenceValue(
			sourcePortletPreferenceValue);

		CTPersistence ctPersistence =
			_portletPreferenceValueLocalService.getCTPersistence();

		ctPersistence.flush();

		PortletPreferenceValue targetPortletPreferenceValue =
			constraintResolverContext.getTargetCTModel();

		targetPortletPreferenceValue.setReadOnly(
			sourcePortletPreferenceValue.isReadOnly());
		targetPortletPreferenceValue.setValue(
			sourcePortletPreferenceValue.getValue());

		_portletPreferenceValueLocalService.updatePortletPreferenceValue(
			targetPortletPreferenceValue);
	}

	@Reference
	private PortletPreferenceValueLocalService
		_portletPreferenceValueLocalService;

}