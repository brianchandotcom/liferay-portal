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

package com.liferay.change.tracking.internal.definition.builder;

import com.liferay.change.tracking.definition.CTDefinition;
import com.liferay.change.tracking.definition.builder.CTDefinitionBuilder;
import com.liferay.change.tracking.internal.definition.CTDefinitionImpl;
import com.liferay.portal.kernel.model.BaseModel;

import java.io.Serializable;

import java.util.List;
import java.util.function.Function;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Gergely Mathe
 */
@Component(scope = ServiceScope.PROTOTYPE, service = CTDefinitionBuilder.class)
public class CTDefinitionBuilderImpl<T extends BaseModel, U extends BaseModel>
	implements CTDefinitionBuilder<T, U> {

	@Activate
	public void activate() {
		_ctDefinition = new CTDefinitionImpl<>();
	}

	@Override
	public ContentTypeLanguageKeyStep<T, U> setContentType(String contentType) {
		_ctDefinition.setContentType(contentType);

		return new ContentTypeLanguageKeyStepImpl();
	}

	public class BuildStepImpl implements BuildStep {

		@Override
		public CTDefinition build() {
			return _ctDefinition;
		}

	}

	public class ContentTypeLanguageKeyStepImpl
		implements ContentTypeLanguageKeyStep<T, U> {

		@Override
		public EntityClassesStep<T, U> setContentTypeLanguageKey(
			String contentTypeLanguageKey) {

			_ctDefinition.setContentTypeLanguageKey(contentTypeLanguageKey);

			return new EntityClassesStepImpl();
		}

	}

	public interface CTDefinitionExtended
		<T extends BaseModel, U extends BaseModel>
			extends CTDefinition<T, U> {

		public void setContentType(String contentType);

		public void setContentTypeLanguageKey(String contentTypeLanguageKey);

		public void setResourceEntitiesByCompanyIdFunction(
			Function<Long, List<T>> resourceEntitiesByCompanyIdFunction);

		public void setResourceEntityByResourceEntityIdFunction(
			Function<Long, T> resourceEntityByResourceEntityIdFunction);

		public void setResourceEntityClass(Class<T> resourceEntityClass);

		public void setResourceEntityIdFromResourceEntityFunction(
			Function<T, Serializable>
				resourceEntityIdFromResourceEntityFunction);

		public void setResourceEntityIdFromVersionEntityFunction(
			Function<U, Serializable>
				resourceEntityIdFromVersionEntityFunction);

		public void setVersionEntitiesFromResourceEntityFunction(
			Function<T, List<U>> versionEntitiesFromResourceEntityFunction);

		public void setVersionEntityAllowedStatuses(Integer[] allowedStatuses);

		public void setVersionEntityByVersionEntityIdFunction(
			Function<Long, U> versionEntityByVersionEntityIdFunction);

		public void setVersionEntityClass(Class<U> versionEntityClass);

		public void setVersionEntityIdFromResourceEntityFunction(
			Function<T, Serializable>
				versionEntityIdFromResourceEntityFunction);

		public void setVersionEntityIdFromVersionEntityFunction(
			Function<U, Serializable> versionEntityIdFromVersionEntityFunction);

		public void setVersionEntityRelatedEntitiesFunctions(
			List<Function<U, List<? extends BaseModel>>>
				versionEntityRelatedEntitiesFunctions);

		public void setVersionEntitySiteNameFunction(
			Function<U, String> versionEntitySiteNameFunction);

		public void setVersionEntityStatusFunction(
			Function<U, Integer> versionEntityStatusFunction);

		public void setVersionEntityTitleFunction(
			Function<U, String> versionEntityTitleFunction);

		public void setVersionEntityVersionFunction(
			Function<U, Serializable> versionEntityVersionFunction);

	}

	public class EntityClassesStepImpl implements EntityClassesStep<T, U> {

		@Override
		public ResourceEntitiesByCompanyIdStep<T, U> setEntityClasses(
			Class<T> resourceEntityClass, Class<U> versionEntityClass) {

			_ctDefinition.setResourceEntityClass(resourceEntityClass);
			_ctDefinition.setVersionEntityClass(versionEntityClass);

			return new ResourceEntitiesByCompanyIdStepImpl();
		}

	}

	public class EntityIdsFromResourceEntityStepImpl
		implements EntityIdsFromResourceEntityStep<T, U> {

		@Override
		public VersionEntitiesFromResourceEntityStep<T, U>
			setEntityIdsFromResourceEntityFunctions(
				Function<T, Serializable>
					resourceEntityIdFromResourceEntityFunction,
				Function<T, Serializable>
					versionEntityIdFromResourceEntityFunction) {

			_ctDefinition.setResourceEntityIdFromResourceEntityFunction(
				resourceEntityIdFromResourceEntityFunction);
			_ctDefinition.setVersionEntityIdFromResourceEntityFunction(
				versionEntityIdFromResourceEntityFunction);

			return new VersionEntitiesFromResourceEntityStepImpl();
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

			_ctDefinition.setResourceEntityIdFromVersionEntityFunction(
				resourceEntityIdFromVersionEntityFunction);
			_ctDefinition.setVersionEntityIdFromVersionEntityFunction(
				versionEntityIdFromVersionEntityFunction);

			return new VersionEntityStatusInfoStepImpl();
		}

	}

	public class ResourceEntitiesByCompanyIdStepImpl
		implements ResourceEntitiesByCompanyIdStep<T, U> {

		@Override
		public ResourceEntityByResourceEntityIdStep<T, U>
			setResourceEntitiesByCompanyIdFunction(
				Function<Long, List<T>> resourceEntitiesByCompanyIdFunction) {

			_ctDefinition.setResourceEntitiesByCompanyIdFunction(
				resourceEntitiesByCompanyIdFunction);

			return new ResourceEntityByResourceEntityIdStepImpl();
		}

	}

	public class ResourceEntityByResourceEntityIdStepImpl
		implements ResourceEntityByResourceEntityIdStep<T, U> {

		@Override
		public EntityIdsFromResourceEntityStep<T, U>
			setResourceEntityByResourceEntityIdFunction(
				Function<Long, T> resourceEntityByResourceEntityIdFunction) {

			_ctDefinition.setResourceEntityByResourceEntityIdFunction(
				resourceEntityByResourceEntityIdFunction);

			return new EntityIdsFromResourceEntityStepImpl();
		}

	}

	public class VersionEntitiesFromResourceEntityStepImpl
		implements VersionEntitiesFromResourceEntityStep<T, U> {

		@Override
		public VersionEntityByVersionEntityIdStep<U>
			setVersionEntitiesFromResourceEntityFunction(
				Function<T, List<U>>
					versionEntitiesFromResourceEntityFunction) {

			_ctDefinition.setVersionEntitiesFromResourceEntityFunction(
				versionEntitiesFromResourceEntityFunction);

			return new VersionEntityByVersionEntityIdStepImpl();
		}

	}

	public class VersionEntityByVersionEntityIdStepImpl
		implements VersionEntityByVersionEntityIdStep<U> {

		@Override
		public VersionEntityDetailsStep<U>
			setVersionEntityByVersionEntityIdFunction(
				Function<Long, U> versionEntityByVersionEntityIdFunction) {

			_ctDefinition.setVersionEntityByVersionEntityIdFunction(
				versionEntityByVersionEntityIdFunction);

			return new VersionEntityDetailsStepImpl();
		}

	}

	public class VersionEntityDetailsStepImpl
		implements VersionEntityDetailsStep<U> {

		@Override
		public EntityIdsFromVersionEntityStep<U> setVersionEntityDetails(
			List<Function<U, List<? extends BaseModel>>>
				versionEntityRelatedEntitiesFunctions,
			Function<U, String> versionEntitySiteNameFunction,
			Function<U, String> versionEntityTitleFunction,
			Function<U, Serializable> versionEntityVersionFunction) {

			_ctDefinition.setVersionEntityRelatedEntitiesFunctions(
				versionEntityRelatedEntitiesFunctions);
			_ctDefinition.setVersionEntitySiteNameFunction(
				versionEntitySiteNameFunction);
			_ctDefinition.setVersionEntityTitleFunction(
				versionEntityTitleFunction);
			_ctDefinition.setVersionEntityVersionFunction(
				versionEntityVersionFunction);

			return new EntityIdsFromVersionEntityStepImpl();
		}

	}

	public class VersionEntityStatusInfoStepImpl
		implements VersionEntityStatusInfoStep<U> {

		@Override
		public BuildStep setVersionEntityStatusInfo(
			Integer[] versionEntityAllowedStatuses,
			Function<U, Integer> versionEntityStatusFunction) {

			_ctDefinition.setVersionEntityAllowedStatuses(
				versionEntityAllowedStatuses);
			_ctDefinition.setVersionEntityStatusFunction(
				versionEntityStatusFunction);

			return new BuildStepImpl();
		}

	}

	private CTDefinitionImpl<T, U> _ctDefinition;

}