/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template.velocity.internal;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.template.engine.BaseTemplateManager;
import com.liferay.portal.template.engine.TemplateContextHelper;
import com.liferay.portal.template.velocity.configuration.VelocityEngineConfiguration;

import java.util.Map;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 * @author Peter Fellwock
 */
@Component(
	configurationPid = "com.liferay.portal.template.velocity.configuration.VelocityEngineConfiguration",
	property = "language.type=" + TemplateConstants.LANG_TYPE_VM,
	service = TemplateManager.class
)
public class VelocityManager extends BaseTemplateManager {

	@Override
	public void destroy() {
		if (_velocityEngine == null) {
			return;
		}

		_velocityEngine = null;

		_templateContextHelper.removeAllHelperUtilities();
	}

	@Override
	public String getName() {
		return TemplateConstants.LANG_TYPE_VM;
	}

	@Override
	public String[] getRestrictedVariables() {
		return _velocityEngineConfiguration.restrictedVariables();
	}

	@Override
	public void init() throws TemplateException {
		if (_velocityEngine != null) {
			return;
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Class<?> clazz = getClass();

		currentThread.setContextClassLoader(clazz.getClassLoader());

		try {
			_velocityEngine = new VelocityEngine();

			ExtendedProperties extendedProperties =
				new FastExtendedProperties();

			extendedProperties.setProperty(
				VelocityEngine.DIRECTIVE_IF_TOSTRING_NULLCHECK,
				String.valueOf(
					_velocityEngineConfiguration.
						directiveIfToStringNullCheck()));
			extendedProperties.setProperty(
				VelocityEngine.EVENTHANDLER_METHODEXCEPTION,
				LiferayMethodExceptionEventHandler.class.getName());
			extendedProperties.setProperty(
				RuntimeConstants.INTROSPECTOR_RESTRICT_CLASSES,
				StringUtil.merge(
					_velocityEngineConfiguration.restrictedClasses()));
			extendedProperties.setProperty(
				"liferay." + RuntimeConstants.INTROSPECTOR_RESTRICT_CLASSES +
					".methods",
				_velocityEngineConfiguration.restrictedMethods());
			extendedProperties.setProperty(
				RuntimeConstants.INTROSPECTOR_RESTRICT_PACKAGES,
				StringUtil.merge(
					_velocityEngineConfiguration.restrictedPackages()));
			extendedProperties.setProperty(
				RuntimeConstants.PARSER_POOL_CLASS,
				VelocityParserPool.class.getName());
			extendedProperties.setProperty(
				VelocityEngine.RESOURCE_LOADER, "liferay");
			extendedProperties.setProperty(
				StringBundler.concat(
					"liferay.", VelocityEngine.RESOURCE_LOADER, ".",
					VelocityTemplateResourceLoader.class.getName()),
				_templateResourceLoader);

			boolean cacheEnabled = false;

			if (_velocityTemplateResourceCache.isEnabled()) {
				cacheEnabled = true;
			}

			extendedProperties.setProperty(
				"liferay." + VelocityEngine.RESOURCE_LOADER + ".cache",
				String.valueOf(cacheEnabled));
			extendedProperties.setProperty(
				"liferay." + VelocityEngine.RESOURCE_LOADER + ".class",
				LiferayResourceLoader.class.getName());
			extendedProperties.setProperty(
				"liferay." + VelocityEngine.RESOURCE_LOADER + "portal.cache",
				_velocityTemplateResourceCache.getSecondLevelPortalCache());
			extendedProperties.setProperty(
				VelocityEngine.RESOURCE_MANAGER_CLASS,
				LiferayResourceManager.class.getName());

			int resourceModificationCheckInterval =
				_velocityEngineConfiguration.
					resourceModificationCheckInterval();

			extendedProperties.setProperty(
				"liferay." + VelocityEngine.RESOURCE_MANAGER_CLASS +
					".resourceModificationCheckInterval",
				resourceModificationCheckInterval + "");

			extendedProperties.setProperty(
				VelocityTemplateResourceLoader.class.getName(),
				_templateResourceLoader);
			extendedProperties.setProperty(
				VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS,
				_velocityEngineConfiguration.logger());
			extendedProperties.setProperty(
				VelocityEngine.RUNTIME_LOG_LOGSYSTEM + ".log4j.category",
				_velocityEngineConfiguration.loggerCategory());
			extendedProperties.setProperty(
				RuntimeConstants.UBERSPECT_CLASSNAME,
				LiferaySecureUberspector.class.getName());
			extendedProperties.setProperty(
				VelocityEngine.VM_LIBRARY, _getVelocimacroLibrary(clazz));
			extendedProperties.setProperty(
				VelocityEngine.VM_LIBRARY_AUTORELOAD,
				String.valueOf(!cacheEnabled));
			extendedProperties.setProperty(
				VelocityEngine.VM_PERM_ALLOW_INLINE_REPLACE_GLOBAL,
				String.valueOf(!cacheEnabled));

			_velocityEngine.setExtendedProperties(extendedProperties);

			_velocityEngine.init();
		}
		catch (Exception exception) {
			throw new TemplateException(exception);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_velocityEngineConfiguration = ConfigurableUtil.createConfigurable(
			VelocityEngineConfiguration.class, properties);
	}

	@Override
	protected Template doGetTemplate(
		TemplateResource templateResource, boolean restricted,
		Map<String, Object> helperUtilities) {

		return new VelocityTemplate(
			templateResource, helperUtilities, _velocityEngine,
			_templateContextHelper, _velocityTemplateResourceCache, restricted);
	}

	@Override
	protected TemplateContextHelper getTemplateContextHelper() {
		return _templateContextHelper;
	}

	private String _getVelocimacroLibrary(Class<?> clazz) {
		String contextName = ClassLoaderPool.getContextName(
			clazz.getClassLoader());

		contextName = contextName.concat(
			TemplateConstants.CLASS_LOADER_SEPARATOR);

		String[] velocimacroLibrary =
			_velocityEngineConfiguration.velocimacroLibrary();

		StringBundler sb = new StringBundler(3 * velocimacroLibrary.length);

		for (String library : velocimacroLibrary) {
			sb.append(contextName);
			sb.append(library);
			sb.append(StringPool.COMMA);
		}

		if (velocimacroLibrary.length > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private static volatile VelocityEngineConfiguration
		_velocityEngineConfiguration;

	@Reference(
		target = "(component.name=com.liferay.portal.template.velocity.internal.helper.VelocityTemplateContextHelper)"
	)
	private TemplateContextHelper _templateContextHelper;

	@Reference(
		target = "(component.name=com.liferay.portal.template.velocity.internal.VelocityTemplateResourceLoader)"
	)
	private TemplateResourceLoader _templateResourceLoader;

	private VelocityEngine _velocityEngine;

	@Reference
	private VelocityTemplateResourceCache _velocityTemplateResourceCache;

}