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

package com.liferay.portal.ccpp.internal;

import com.liferay.portal.ccpp.PortalProfileFactory;

import com.sun.ccpp.ProfileFactoryImpl;

import java.util.Set;

import javax.ccpp.Attribute;
import javax.ccpp.Profile;
import javax.ccpp.ProfileDescription;
import javax.ccpp.ProfileFactory;
import javax.ccpp.ValidationMode;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(service = PortalProfileFactory.class)
public class PortalProfileFactoryImpl implements PortalProfileFactory {

	@Override
	public Profile getCCPPProfile(HttpServletRequest httpServletRequest) {
		ProfileFactory profileFactory = ProfileFactory.getInstance();

		if (profileFactory == null) {
			profileFactory = ProfileFactoryImpl.getInstance();

			ProfileFactory.setInstance(profileFactory);
		}

		Profile profile = profileFactory.newProfile(
			httpServletRequest, ValidationMode.VALIDATIONMODE_NONE);

		if (profile == null) {
			profile = _profile;
		}

		return profile;
	}

	private static final Profile _profile = new Profile() {

		@Override
		public Attribute getAttribute(String name) {
			return null;
		}

		@Override
		public Set<Attribute> getAttributes() {
			return null;
		}

		@Override
		public javax.ccpp.Component getComponent(String localtype) {
			return null;
		}

		@Override
		public Set<javax.ccpp.Component> getComponents() {
			return null;
		}

		@Override
		public ProfileDescription getDescription() {
			return null;
		}

	};

}