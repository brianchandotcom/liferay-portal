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

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateContextType;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.template.RestrictedTemplate;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Map;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

/**
 * @author Raymond Augé
 */
public class VelocityManager implements TemplateManager {

	public void clearCache() {
		StringResourceRepository stringResourceRepository =
			StringResourceLoader.getRepository();

		if (stringResourceRepository != null) {
			((StringResourceRepositoryImpl)stringResourceRepository).removeAll(
				);
		}

		LiferayResourceCacheUtil.removeAll();
	}

	public void clearCache(String templateId) {
		StringResourceRepository stringResourceRepository =
			StringResourceLoader.getRepository();

		if (stringResourceRepository != null) {
			stringResourceRepository.removeStringResource(templateId);
		}

		LiferayResourceCacheUtil.remove(templateId);
	}

	public void destroy()throws TemplateException {
		_restrictedContext = null;
		_standardContext = null;
		_velocityEngine = null;
		_templateContextHelper = null;
	}

	public String getManagerName() {
		return VELOCITY;
	}

	public Template getTemplate(
		String templateId, String templateContent, String errorTemplateId,
		String errorTemplateContent, TemplateContextType templateContextType) {

		switch(templateContextType) {
			case EMPTY:
				return new VelocityTemplate(
						templateId, templateContent, errorTemplateId,
						errorTemplateContent, null, _velocityEngine,
						_templateContextHelper);
			case RESTRICTED:
				return new RestrictedTemplate(
					new VelocityTemplate(
						templateId, templateContent, errorTemplateId,
						errorTemplateContent, _restrictedContext,
						_velocityEngine, _templateContextHelper),
					_templateContextHelper.getRestrictedVariables());
			case STANDARD:
				return new VelocityTemplate(
						templateId, templateContent, errorTemplateId,
						errorTemplateContent, _standardContext, _velocityEngine,
						_templateContextHelper);
		}

		return null;
	}

	public Template getTemplate(
		String templateId, String templateContent, String errorTemplateId,
		TemplateContextType templateContextType) {

		return getTemplate(
			templateId, templateContent, errorTemplateId, null,
			templateContextType);
	}

	public Template getTemplate(
		String templateId, String templateContent,
		TemplateContextType templateContextType) {

		return getTemplate(
			templateId, templateContent, null, null, templateContextType);
	}

	public Template getTemplate(
		String templateId, TemplateContextType templateContextType) {

		return getTemplate(templateId, null, null, null, templateContextType);
	}

	public boolean hasTemplate(String templateId) {
		return _velocityEngine.resourceExists(templateId);
	}

	public void init() throws TemplateException {
		if (_velocityEngine != null) {
			return;
		}

		_velocityEngine = new VelocityEngine();

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
			VelocityEngine.RESOURCE_MANAGER_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_RESOURCE_MANAGER));

		extendedProperties.setProperty(
			VelocityEngine.RESOURCE_MANAGER_CACHE_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE));

		extendedProperties.setProperty(
			VelocityEngine.VM_LIBRARY,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_VELOCIMACRO_LIBRARY));

		extendedProperties.setProperty(
			VelocityEngine.VM_LIBRARY_AUTORELOAD,
			String.valueOf(
				!PropsValues.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_ENABLED));

		extendedProperties.setProperty(
			VelocityEngine.VM_PERM_ALLOW_INLINE_REPLACE_GLOBAL,
			String.valueOf(
				!PropsValues.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_ENABLED));

		extendedProperties.setProperty(
			VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER));

		extendedProperties.setProperty(
			VelocityEngine.RUNTIME_LOG_LOGSYSTEM + ".log4j.category",
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER_CATEGORY));

		_velocityEngine.setExtendedProperties(extendedProperties);

		try {
			_velocityEngine.init();
		}
		catch (Exception e) {
			throw new TemplateException(e);
		}

		_restrictedContext = new VelocityContext();

		Map<String, Object> helperUtilities =
			_templateContextHelper.getRestrictedHelperUtilities();

		for (Map.Entry<String, Object> entry : helperUtilities.entrySet()) {
			_restrictedContext.put(entry.getKey(), entry.getValue());
		}

		_standardContext = new VelocityContext();

		helperUtilities = _templateContextHelper.getHelperUtilities();

		for (Map.Entry<String, Object> entry : helperUtilities.entrySet()) {
			_standardContext.put(entry.getKey(), entry.getValue());
		}
	}

	public void setTemplateContextHelper(
		TemplateContextHelper templateContextHelper) {

		_templateContextHelper = templateContextHelper;
	}

	private static final String _RESOURCE_LOADER =
		VelocityEngine.RESOURCE_LOADER;

	private VelocityContext _restrictedContext;
	private VelocityContext _standardContext;
	private TemplateContextHelper _templateContextHelper;
	private VelocityEngine _velocityEngine;

}