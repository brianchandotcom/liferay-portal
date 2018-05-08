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

package com.liferay.portal.security.auth.verifier.internal.module.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Tomas Polesovsky
 */
public interface BaseAuthVerifierConfiguration {

	@Meta.AD(deflt = "true", name = "enabled", required = false)
	public boolean enabled();

	@Meta.AD(name = "hosts-allowed", required = false)
	public String hostsAllowed();

	@Meta.AD(name = "urls-excludes", required = false)
	public String urlsExcludes();

	@Meta.AD(name = "urls-includes", required = false)
	public String urlsIncludes();

}