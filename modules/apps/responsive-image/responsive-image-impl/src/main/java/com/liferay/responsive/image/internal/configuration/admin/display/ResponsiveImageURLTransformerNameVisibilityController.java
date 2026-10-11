/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationVisibilityController;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.responsive.image.ResponsiveImageURLTransformerProvider;

import java.io.Serializable;

import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Hides the transformer name for as long as naming one is not a choice.
 *
 * <p>
 * An installation deploying a single transformer has nothing to say, and a
 * blank name already resolves to that one, so asking for it would be asking an
 * administrator to repeat what the deployment already decided. The field
 * appears of its own accord once a second transformer is deployed.
 * </p>
 *
 * @author Daniel Sanz
 */
@Component(service = ConfigurationVisibilityController.class)
public class ResponsiveImageURLTransformerNameVisibilityController
	implements ConfigurationVisibilityController {

	@Override
	public String getKey() {
		return "responsive-image-url-transformer-name";
	}

	@Override
	public boolean isVisible(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		Set<String> responsiveImageURLTransformerNames =
			_responsiveImageURLTransformerProvider.
				getResponsiveImageURLTransformerNames();

		if (responsiveImageURLTransformerNames.size() > 1) {
			return true;
		}

		return false;
	}

	@Reference
	private ResponsiveImageURLTransformerProvider
		_responsiveImageURLTransformerProvider;

}