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

package com.liferay.change.tracking.configuration.builder;

import com.liferay.change.tracking.configuration.CTConfiguration;

import java.io.Serializable;

import java.util.List;
import java.util.function.Function;

/**
 * @author Gergely Mathe
 */
public interface CTConfigurationBuilder<T, U> {

	public ContentTypeLanguageKeyStep<T, U> setContentType(String contentType);

	public interface BuildStep {

		public CTConfiguration build();

	}

	public interface ContentTypeLanguageKeyStep<T, U> {

		public EntityClassesStep<T, U> setContentTypeLanguageKey(
			String contentTypeLanguageKey);

	}

	public interface EntityClassesStep<T, U> {

		public ResourceEntitiesByCompanyIdStep<T, U> setEntityClasses(
			Class<T> resourceEntityClass, Class<U> versionEntityClass);

	}

	public interface EntityIdsFromResourceEntityStep<T, U> {

		public VersionEntitiesFromResourceEntityStep<T, U>
			setEntityIdsFromResourceEntityFunctions(
				Function<T, Serializable>
					resourceEntityIdFromResourceEntityFunction,
				Function<T, Serializable>
					versionEntityIdFromResourceEntityFunction);

	}

	public interface EntityIdsFromVersionEntityStep<U> {

		public VersionEntityStatusInfoStep<U>
			setEntityIdsFromVersionEntityFunctions(
				Function<U, Serializable>
					resourceEntityIdFromVersionEntityFunction,
				Function<U, Serializable>
					versionEntityIdFromVersionEntityFunction);

	}

	public interface ResourceEntitiesByCompanyIdStep<T, U> {

		public ResourceEntityByResourceEntityIdStep<T, U>
			setResourceEntitiesByCompanyIdFunction(
				Function<Long, List<T>> resourceEntitiesByCompanyIdFunction);

	}

	public interface ResourceEntityByResourceEntityIdStep<T, U> {

		public EntityIdsFromResourceEntityStep<T, U>
			setResourceEntityByResourceEntityIdFunction(
				Function<Long, T> resourceEntityByResourceEntityIdFunction);

	}

	public interface VersionEntitiesFromResourceEntityStep<T, U> {

		public VersionEntityByVersionEntityIdStep<U>
			setVersionEntitiesFromResourceEntityFunction(
				Function<T, List<U>> versionEntitiesFromResourceEntityFunction);

	}

	public interface VersionEntityByVersionEntityIdStep<U> {

		public VersionEntityDetailsStep<U>
			setVersionEntityByVersionEntityIdFunction(
				Function<Long, U> versionEntityByVersionEntityIdFunction);

	}

	public interface VersionEntityDetailsStep<U> {

		public EntityIdsFromVersionEntityStep<U> setVersionEntityDetails(
			Function<U, String> versionEntitySiteNameFunction,
			Function<U, String> versionEntityTitleFunction,
			Function<U, Serializable> versionEntityVersionFunction);

	}

	public interface VersionEntityStatusInfoStep<U> {

		public BuildStep setVersionEntityStatusInfo(
			Integer[] versionEntityAllowedStatuses,
			Function<U, Integer> versionEntityStatusFunction);

	}

}