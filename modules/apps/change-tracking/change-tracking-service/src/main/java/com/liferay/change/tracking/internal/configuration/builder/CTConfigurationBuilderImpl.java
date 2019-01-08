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

package com.liferay.change.tracking.internal.configuration.builder;

import com.liferay.change.tracking.configuration.CTConfiguration;
import com.liferay.change.tracking.configuration.builder.CTConfigurationBuilder;
import com.liferay.change.tracking.internal.configuration.CTConfigurationImpl;

import java.io.Serializable;

import java.util.function.Function;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Gergely Mathe
 */
@Component(
	scope = ServiceScope.PROTOTYPE, service = CTConfigurationBuilder.class
)
public class CTConfigurationBuilderImpl<T, U>
	implements CTConfigurationBuilder<T, U> {

	@Activate
	public void activate() {
		_ctConfiguration = new CTConfigurationImpl<>();
	}

	@Override
	public ContentTypeLanguageKeyStep<T, U> setContentType(String contentType) {
		_ctConfiguration.setContentType(contentType);

		return new ContentTypeLanguageKeyStepImpl();
	}

	public class BuildStepImpl implements BuildStep {

		@Override
		public CTConfiguration build() {
			return _ctConfiguration;
		}

	}

	public class ContentTypeLanguageKeyStepImpl
		implements ContentTypeLanguageKeyStep<T, U> {

		@Override
		public EntityClassesStep<T, U> setContentTypeLanguageKey(
			String contentTypeLanguageKey) {

			_ctConfiguration.setContentTypeLanguageKey(contentTypeLanguageKey);

			return new EntityClassesStepImpl();
		}

	}

	public interface CTConfigurationExtended<T, U>
		extends CTConfiguration<T, U> {

		public void setContentType(String contentType);

		public void setContentTypeLanguageKey(String contentTypeLanguageKey);

		public void setResourceEntityByResourceEntityIdFunction(
			Function<Long, T> resourceEntityByResourceEntityIdFunction);

		public void setResourceEntityClass(Class<T> resourceEntityClass);

		public void setResourceEntityIdFromResourceEntityFunction(
			Function<T, Serializable>
				resourceEntityIdFromResourceEntityFunction);

		public void setResourceEntityIdFromVersionEntityFunction(
			Function<U, Serializable>
				resourceEntityIdFromVersionEntityFunction);

		public void setVersionEntityAllowedStatuses(Integer[] allowedStatuses);

		public void setVersionEntityByVersionEntityIdFunction(
			Function<Long, U> versionEntityByVersionEntityIdFunction);

		public void setVersionEntityClass(Class<U> versionEntityClass);

		public void setVersionEntityIdFromResourceEntityFunction(
			Function<T, Serializable>
				versionEntityIdFromResourceEntityFunction);

		public void setVersionEntityIdFromVersionEntityFunction(
			Function<U, Serializable> versionEntityIdFromVersionEntityFunction);

		public void setVersionEntityStatusFunction(
			Function<U, Integer> versionEntityStatusFunction);

	}

	public class EntityClassesStepImpl implements EntityClassesStep<T, U> {

		@Override
		public ResourceEntityByResourceEntityIdStep<T, U> setEntityClasses(
			Class<T> resourceEntityClass, Class<U> versionEntityClass) {

			_ctConfiguration.setResourceEntityClass(resourceEntityClass);
			_ctConfiguration.setVersionEntityClass(versionEntityClass);

			return new ResourceEntityByResourceEntityIdStepImpl();
		}

	}

	public class EntityIdsFromResourceEntityStepImpl
		implements EntityIdsFromResourceEntityStep<T, U> {

		@Override
		public VersionEntityByVersionEntityIdStep<U>
			setEntityIdsFromResourceEntityFunctions(
				Function<T, Serializable>
					resourceEntityIdFromResourceEntityFunction,
				Function<T, Serializable>
					versionEntityIdFromResourceEntityFunction) {

			_ctConfiguration.setResourceEntityIdFromResourceEntityFunction(
				resourceEntityIdFromResourceEntityFunction);
			_ctConfiguration.setVersionEntityIdFromResourceEntityFunction(
				versionEntityIdFromResourceEntityFunction);

			return new VersionEntityByVersionEntityIdStepImpl();
		}

	}

	public class EntityIdsFromVersionEntityStepImpl
		implements EntityIdsFromVersionEntityStep<U> {

		@Override
		public VersionEntityStatusInfoStep<U>
			setEntityIdsFromVersionEntityFunctions(
				Function<U, Serializable>
					resourceEntityIdFromVersionEntityFunction,
				Function<U, Serializable>
					versionEntityIdFromVersionEntityFunction) {

			_ctConfiguration.setResourceEntityIdFromVersionEntityFunction(
				resourceEntityIdFromVersionEntityFunction);
			_ctConfiguration.setVersionEntityIdFromVersionEntityFunction(
				versionEntityIdFromVersionEntityFunction);

			return new VersionEntityStatusInfoStepImpl();
		}

	}

	public class ResourceEntityByResourceEntityIdStepImpl
		implements ResourceEntityByResourceEntityIdStep<T, U> {

		@Override
		public EntityIdsFromResourceEntityStep<T, U>
			setResourceEntityByResourceEntityIdFunction(
				Function<Long, T> resourceEntityByResourceEntityIdFunction) {

			_ctConfiguration.setResourceEntityByResourceEntityIdFunction(
				resourceEntityByResourceEntityIdFunction);

			return new EntityIdsFromResourceEntityStepImpl();
		}

	}

	public class VersionEntityByVersionEntityIdStepImpl
		implements VersionEntityByVersionEntityIdStep<U> {

		@Override
		public EntityIdsFromVersionEntityStep<U>
			setVersionEntityByVersionEntityIdFunction(
				Function<Long, U> versionEntityByVersionEntityIdFunction) {

			_ctConfiguration.setVersionEntityByVersionEntityIdFunction(
				versionEntityByVersionEntityIdFunction);

			return new EntityIdsFromVersionEntityStepImpl();
		}

	}

	public class VersionEntityStatusInfoStepImpl
		implements VersionEntityStatusInfoStep<U> {

		@Override
		public BuildStep setVersionEntityStatusInfo(
			Integer[] versionEntityAllowedStatuses,
			Function<U, Integer> versionEntityStatusFunction) {

			_ctConfiguration.setVersionEntityAllowedStatuses(
				versionEntityAllowedStatuses);
			_ctConfiguration.setVersionEntityStatusFunction(
				versionEntityStatusFunction);

			return new BuildStepImpl();
		}

	}

	private CTConfigurationImpl<T, U> _ctConfiguration;

}