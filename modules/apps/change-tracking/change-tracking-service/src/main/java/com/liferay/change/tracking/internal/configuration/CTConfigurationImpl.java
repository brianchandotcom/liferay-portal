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

package com.liferay.change.tracking.internal.configuration;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.internal.configuration.builder.CTConfigurationBuilderImpl;

import java.io.Serializable;

import java.util.function.Function;

/**
 * @author Máté Thurzó
 */
@ProviderType
public class CTConfigurationImpl<T, U>
	implements CTConfigurationBuilderImpl.CTConfigurationExtended<T, U> {

	public CTConfigurationImpl() {
		_resouceEntityInformation = new EntityInformation<>();
		_versionEntityInformation = new EntityInformation<>();
	}

	@Override
	public Function<Long, T> getResourceEntityByResourceEntityIdFunction() {
		return _resouceEntityInformation.getEntityFunction();
	}

	@Override
	public Class<T> getResourceEntityClass() {
		return _resouceEntityInformation.getEntityClass();
	}

	@Override
	public Function<T, Serializable>
		getResourceEntityIdFromResourceEntityFunction() {

		return _resouceEntityInformation.getResourceIdFunction();
	}

	@Override
	public Function<U, Serializable>
		getResourceEntityIdFromVersionEntityFunction() {

		return _versionEntityInformation.getResourceIdFunction();
	}

	@Override
	public Integer[] getVersionEntityAllowedStatuses() {
		return _versionEntityInformation.getAllowedStatuses();
	}

	@Override
	public Function<Long, U> getVersionEntityByVersionEntityIdFunction() {
		return _versionEntityInformation.getEntityFunction();
	}

	@Override
	public Class<U> getVersionEntityClass() {
		return _versionEntityInformation.getEntityClass();
	}

	@Override
	public Function<T, Serializable>
		getVersionEntityIdFromResourceEntityFunction() {

		return _resouceEntityInformation.getVersionIdFunction();
	}

	@Override
	public Function<U, Serializable>
		getVersionEntityIdFromVersionEntityFunction() {

		return _versionEntityInformation.getVersionIdFunction();
	}

	@Override
	public Function<U, Integer> getVersionEntityStatusFunction() {
		return _versionEntityInformation.getStatusFunction();
	}

	@Override
	public void setResourceEntityByResourceEntityIdFunction(
		Function<Long, T> resourceEntityByResourceEntityIdFunction) {

		_resouceEntityInformation.setEntityFunction(
			resourceEntityByResourceEntityIdFunction);
	}

	@Override
	public void setResourceEntityClass(Class<T> resourceEntityClass) {
		_resouceEntityInformation.setEntityClass(resourceEntityClass);
	}

	@Override
	public void setResourceEntityIdFromResourceEntityFunction(
		Function<T, Serializable> resourceEntityIdFromResourceEntityFunction) {

		_resouceEntityInformation.setResourceEntityIdFunction(
			resourceEntityIdFromResourceEntityFunction);
	}

	@Override
	public void setResourceEntityIdFromVersionEntityFunction(
		Function<U, Serializable> resourceEntityIdFromVersionEntityFunction) {

		_versionEntityInformation.setResourceEntityIdFunction(
			resourceEntityIdFromVersionEntityFunction);
	}

	@Override
	public void setVersionEntityAllowedStatuses(Integer[] allowedStatuses) {
		_versionEntityInformation.setAllowedStatuses(allowedStatuses);
	}

	@Override
	public void setVersionEntityByVersionEntityIdFunction(
		Function<Long, U> versionEntityByVersionEntityIdFunction) {

		_versionEntityInformation.setEntityFunction(
			versionEntityByVersionEntityIdFunction);
	}

	@Override
	public void setVersionEntityClass(Class<U> versionEntityClass) {
		_versionEntityInformation.setEntityClass(versionEntityClass);
	}

	@Override
	public void setVersionEntityIdFromResourceEntityFunction(
		Function<T, Serializable> versionEntityIdFromResourceEntityFunction) {

		_resouceEntityInformation.setVersionEntityIdFunction(
			versionEntityIdFromResourceEntityFunction);
	}

	@Override
	public void setVersionEntityIdFromVersionEntityFunction(
		Function<U, Serializable> versionEntityIdFromVersionEntityFunction) {

		_versionEntityInformation.setVersionEntityIdFunction(
			versionEntityIdFromVersionEntityFunction);
	}

	@Override
	public void setVersionEntityStatusFunction(
		Function<U, Integer> versionEntityStatusFunction) {

		_versionEntityInformation.setStatusFunction(
			versionEntityStatusFunction);
	}

	private final EntityInformation<T> _resouceEntityInformation;
	private final EntityInformation<U> _versionEntityInformation;

	private static class EntityInformation<T> {

		public EntityInformation() {
		}

		public Integer[] getAllowedStatuses() {
			return _allowedStatuses;
		}

		public Class<T> getEntityClass() {
			return _entityClass;
		}

		public Function<Long, T> getEntityFunction() {
			return _entityFunction;
		}

		public Function<T, Serializable> getResourceIdFunction() {
			return _resourceEntityIdFunction;
		}

		public Function<T, Integer> getStatusFunction() {
			return _statusFunction;
		}

		public Function<T, Serializable> getVersionIdFunction() {
			return _versionEntityIdFunction;
		}

		public void setAllowedStatuses(Integer[] allowedStatuses) {
			_allowedStatuses = allowedStatuses;
		}

		public void setEntityClass(Class<T> entityClass) {
			_entityClass = entityClass;
		}

		public void setEntityFunction(Function<Long, T> entityFunction) {
			_entityFunction = entityFunction;
		}

		public void setResourceEntityIdFunction(
			Function<T, Serializable> resourceEntityIdFunction) {

			_resourceEntityIdFunction = resourceEntityIdFunction;
		}

		public void setStatusFunction(Function<T, Integer> statusFunction) {
			_statusFunction = statusFunction;
		}

		public void setVersionEntityIdFunction(
			Function<T, Serializable> versionEntityIdFunction) {

			_versionEntityIdFunction = versionEntityIdFunction;
		}

		private Integer[] _allowedStatuses;
		private Class<T> _entityClass;
		private Function<Long, T> _entityFunction;
		private Function<T, Serializable> _resourceEntityIdFunction;
		private Function<T, Integer> _statusFunction;
		private Function<T, Serializable> _versionEntityIdFunction;

	}

}