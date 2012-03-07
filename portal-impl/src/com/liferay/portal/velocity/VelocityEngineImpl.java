/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.velocity.VelocityContext;
import com.liferay.portal.kernel.velocity.VelocityEngine;
import com.liferay.portal.kernel.velocity.VelocityVariablesUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.Writer;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

/**
 * @author Raymond Augé
 */
public class VelocityEngineImpl implements VelocityEngine {

	public VelocityEngineImpl() {
	}

	public void flushTemplate(String velocityTemplateId) {
		StringResourceRepository stringResourceRepository =
			StringResourceLoader.getRepository();

		if (stringResourceRepository != null) {
			stringResourceRepository.removeStringResource(velocityTemplateId);
		}

		LiferayResourceCacheUtil.remove(velocityTemplateId);
	}

	public VelocityContext getEmptyContext() {
		return new VelocityContextImpl();
	}

	public VelocityContext getRestrictedToolsContext() {
		return _restrictedToolsContext;
	}

	public VelocityContext getStandardToolsContext() {
		return _standardToolsContext;
	}

	public VelocityContext getWrappedRestrictedToolsContext() {
		return new VelocityContextImpl(
			_restrictedToolsContext.getWrappedVelocityContext());
	}

	public VelocityContext getWrappedStandardToolsContext() {
		return new VelocityContextImpl(
			_standardToolsContext.getWrappedVelocityContext());
	}

	public void init() throws Exception {
		if (_velocityEngine != null) {
			return;
		}

		_velocityEngine = new org.apache.velocity.app.VelocityEngine();

		LiferayResourceLoader.setVelocityResourceListeners(
			PropsValues.VELOCITY_ENGINE_RESOURCE_LISTENERS);

		ExtendedProperties extendedProperties = new FastExtendedProperties();

		extendedProperties.setProperty(_RESOURCE_LOADER, "string,servlet");

		extendedProperties.setProperty(
			"string." + _RESOURCE_LOADER + ".cache",
			String.valueOf(
				PropsValues.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_ENABLED));

		extendedProperties.setProperty(
			"string." + _RESOURCE_LOADER + ".class",
			StringResourceLoader.class.getName());

		extendedProperties.setProperty(
			"string." + _RESOURCE_LOADER + ".repository.class",
			StringResourceRepositoryImpl.class.getName());

		extendedProperties.setProperty(
			"servlet." + _RESOURCE_LOADER + ".cache",
			String.valueOf(
				PropsValues.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_ENABLED));

		extendedProperties.setProperty(
			"servlet." + _RESOURCE_LOADER + ".class",
			LiferayResourceLoader.class.getName());

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.RESOURCE_MANAGER_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_RESOURCE_MANAGER));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.RESOURCE_MANAGER_CACHE_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.VM_LIBRARY,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_VELOCIMACRO_LIBRARY));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.VM_LIBRARY_AUTORELOAD,
			String.valueOf(
				!PropsValues.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_ENABLED));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.
				VM_PERM_ALLOW_INLINE_REPLACE_GLOBAL,
			String.valueOf(
				!PropsValues.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_ENABLED));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.RUNTIME_LOG_LOGSYSTEM +
				".log4j.category",
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER_CATEGORY));

		_velocityEngine.setExtendedProperties(extendedProperties);

		_velocityEngine.init();

		_restrictedToolsContext = new VelocityContextImpl();

		VelocityVariablesUtil.insertHelperUtilities(
			_restrictedToolsContext,
			PropsValues.JOURNAL_TEMPLATE_VELOCITY_RESTRICTED_VARIABLES);

		_standardToolsContext = new VelocityContextImpl();

		VelocityVariablesUtil.insertHelperUtilities(
			_standardToolsContext, null);
	}

	public boolean mergeTemplate(
			String velocityTemplateId, String velocityTemplateContent,
			VelocityContext velocityContext, Writer writer)
		throws Exception {

		if (Validator.isNotNull(velocityTemplateContent) &&
			(!PropsValues.LAYOUT_TEMPLATE_CACHE_ENABLED ||
			 !resourceExists(velocityTemplateId))) {

			StringResourceRepository stringResourceRepository =
				StringResourceLoader.getRepository();

			stringResourceRepository.putStringResource(
				velocityTemplateId, velocityTemplateContent);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Added " + velocityTemplateId +
						" to the Velocity template repository");
			}
		}

		VelocityContextImpl velocityContextImpl =
			(VelocityContextImpl)velocityContext;

		return _velocityEngine.mergeTemplate(
			velocityTemplateId, StringPool.UTF8,
			velocityContextImpl.getWrappedVelocityContext(), writer);
	}

	public boolean mergeTemplate(
			String velocityTemplateId, VelocityContext velocityContext,
			Writer writer)
		throws Exception {

		return mergeTemplate(velocityTemplateId, null, velocityContext, writer);
	}

	public boolean resourceExists(String resource) {
		return _velocityEngine.resourceExists(resource);
	}

	private static final String _RESOURCE_LOADER =
		org.apache.velocity.app.VelocityEngine.RESOURCE_LOADER;

	private static Log _log = LogFactoryUtil.getLog(VelocityEngineImpl.class);

	private VelocityContextImpl _restrictedToolsContext;
	private VelocityContextImpl _standardToolsContext;
	private org.apache.velocity.app.VelocityEngine _velocityEngine;

}