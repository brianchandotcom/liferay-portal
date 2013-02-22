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

package com.liferay.portal.templateparser;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateContextType;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.templateparser.TemplateContext;
import com.liferay.portal.kernel.templateparser.Transformer;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.xsl.XSLTemplateResource;
import com.liferay.portal.xsl.XSLURIResolver;
import com.liferay.portlet.journal.util.JournalXSLURIResolver;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Wesley Gong
 * @author Angelo Jefferson
 * @author Hugo Huijser
 * @author Marcellus Tavares
 * @author Juan Fernández
 */
public abstract class BaseTransformer implements Transformer {

	public String transform(
			ThemeDisplay themeDisplay, Map<String, Object> contextObjects,
			String script, String langType)
		throws Exception {

		if (Validator.isNull(langType)) {
			return null;
		}

		TemplateContext templateContext = getTemplateContext(
			themeDisplay, contextObjects, script, langType);

		TemplateParser templateParser = new TemplateParser(
			themeDisplay, contextObjects, templateContext);

		return templateParser.transform();
	}

	public String transform(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String viewMode, String languageId, String xml, String script,
			String langType)
		throws Exception {

		// Setup listeners

		if (_log.isDebugEnabled()) {
			_log.debug("Language " + languageId);
		}

		if (Validator.isNull(viewMode)) {
			viewMode = Constants.VIEW;
		}

		if (_logTokens.isDebugEnabled()) {
			String tokensString = PropertiesUtil.list(tokens);

			_logTokens.debug(tokensString);
		}

		if (_logTransformBefore.isDebugEnabled()) {
			_logTransformBefore.debug(xml);
		}

		List<TransformerListener> transformerListeners =
			new ArrayList<TransformerListener>();

		Set<String> transformerListenersClassNames =
			getTransformerListenersClassNames();

		for (String transformerListenersClassName :
				transformerListenersClassNames) {

			TransformerListener transformerListener = null;

			try {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Instantiate listener " +
							transformerListenersClassName);
				}

				ClassLoader classLoader =
					PortalClassLoaderUtil.getClassLoader();

				transformerListener =
					(TransformerListener)InstanceFactory.newInstance(
						classLoader, transformerListenersClassName);

				transformerListeners.add(transformerListener);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			// Modify XML

			if (_logXmlBeforeListener.isDebugEnabled()) {
				_logXmlBeforeListener.debug(xml);
			}

			if (transformerListener != null) {
				xml = transformerListener.onXml(xml, languageId, tokens);

				if (_logXmlAfterListener.isDebugEnabled()) {
					_logXmlAfterListener.debug(xml);
				}
			}

			// Modify script

			if (_logScriptBeforeListener.isDebugEnabled()) {
				_logScriptBeforeListener.debug(script);
			}

			if (transformerListener != null) {
				script = transformerListener.onScript(
					script, xml, languageId, tokens);

				if (_logScriptAfterListener.isDebugEnabled()) {
					_logScriptAfterListener.debug(script);
				}
			}
		}

		// Transform

		String output = null;

		if (Validator.isNull(langType)) {
			output = LocalizationUtil.getLocalization(xml, languageId);
		}
		else {
			TemplateContext templateContext = getTemplateContext(
				themeDisplay, tokens, languageId, xml, script, langType);

			TemplateParser templateParser = new TemplateParser(
				themeDisplay, tokens, viewMode, languageId, xml,
				templateContext);

			output = templateParser.transform();
		}

		// Postprocess output

		for (TransformerListener transformerListener : transformerListeners) {

			// Modify output

			if (_logOutputBeforeListener.isDebugEnabled()) {
				_logOutputBeforeListener.debug(output);
			}

			output = transformerListener.onOutput(output, languageId, tokens);

			if (_logOutputAfterListener.isDebugEnabled()) {
				_logOutputAfterListener.debug(output);
			}
		}

		if (_logTransfromAfter.isDebugEnabled()) {
			_logTransfromAfter.debug(output);
		}

		return output;
	}

	protected abstract String getErrorTemplateId(String langType);

	protected TemplateResource getErrorTemplateResource(String langType) {
		try {
			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			URL url = classLoader.getResource(getErrorTemplateId(langType));

			return new URLTemplateResource(getErrorTemplateId(langType), url);
		}
		catch (Exception e) {
		}

		return null;
	}

	protected TemplateContext getTemplateContext(
			ThemeDisplay themeDisplay, Map<String, Object> contextObjects,
			String script, String langType)
		throws Exception {

		long companyId = 0;
		long companyGroupId = 0;
		long groupId = 0;

		if (themeDisplay != null) {
			companyId = themeDisplay.getCompanyId();
			companyGroupId = themeDisplay.getCompanyGroupId();
			groupId = themeDisplay.getScopeGroupId();
		}

		String templateId = String.valueOf(contextObjects.get("template_id"));

		templateId = getTemplateId(
			templateId, companyId, companyGroupId, groupId);

		TemplateResource templateResource = new StringTemplateResource(
			templateId, script);

		TemplateResource errorTemplateResource = getErrorTemplateResource(
			langType);

		TemplateContextType templateContextType = getTemplateContextType(
			langType);

		return TemplateManagerUtil.getTemplate(
			langType, templateResource, errorTemplateResource,
			templateContextType);
	}

	protected TemplateContext getTemplateContext(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String languageId, String xml, String script, String langType)
		throws Exception {

		long companyId = 0;
		long companyGroupId = 0;
		long groupId = 0;

		if (themeDisplay != null) {
			companyId = themeDisplay.getCompanyId();
			companyGroupId = themeDisplay.getCompanyGroupId();
			groupId = themeDisplay.getScopeGroupId();
		}
		else if (tokens != null) {
			companyId =GetterUtil.getLong(tokens.get("company_id"));
			companyGroupId = GetterUtil.getLong(tokens.get("company_group_id"));
			groupId = GetterUtil.getLong(tokens.get("group_id"));
		}

		String templateId = tokens.get("template_id");

		templateId = getTemplateId(
			templateId, companyId, companyGroupId, groupId);

		TemplateResource templateResource = null;

		if (langType.equals(TemplateConstants.LANG_TYPE_XSL)) {
			XSLURIResolver xslURIResolver = new JournalXSLURIResolver(
				tokens, languageId);

			templateResource = new XSLTemplateResource(
				templateId, script, xslURIResolver, xml);
		}
		else {
			templateResource = new StringTemplateResource(templateId, script);
		}

		TemplateResource errorTemplateResource = getErrorTemplateResource(
			langType);

		TemplateContextType templateContextType = getTemplateContextType(
			langType);

		return TemplateManagerUtil.getTemplate(
			langType, templateResource, errorTemplateResource,
			templateContextType);
	}

	protected abstract TemplateContextType getTemplateContextType(
		String langType);

	protected String getTemplateId(
		String templateId, long companyId, long companyGroupId, long groupId) {

		StringBundler sb = new StringBundler(5);

		sb.append(companyId);
		sb.append(StringPool.POUND);

		if (companyGroupId > 0) {
			sb.append(companyGroupId);
		}
		else {
			sb.append(groupId);
		}

		sb.append(StringPool.POUND);
		sb.append(templateId);

		return sb.toString();
	}

	protected abstract Set<String> getTransformerListenersClassNames();

	private static Log _log = LogFactoryUtil.getLog(BaseTransformer.class);

	private static Log _logOutputAfterListener = LogFactoryUtil.getLog(
		BaseTransformer.class.getName() + ".OutputAfterListener");
	private static Log _logOutputBeforeListener = LogFactoryUtil.getLog(
		BaseTransformer.class.getName() + ".OutputBeforeListener");
	private static Log _logScriptAfterListener = LogFactoryUtil.getLog(
		BaseTransformer.class.getName() + ".ScriptAfterListener");
	private static Log _logScriptBeforeListener = LogFactoryUtil.getLog(
		BaseTransformer.class.getName() + ".ScriptBeforeListener");
	private static Log _logTokens = LogFactoryUtil.getLog(
		BaseTransformer.class.getName() + ".Tokens");
	private static Log _logTransformBefore = LogFactoryUtil.getLog(
		BaseTransformer.class.getName() + ".TransformBefore");
	private static Log _logTransfromAfter = LogFactoryUtil.getLog(
		BaseTransformer.class.getName() + ".TransformAfter");
	private static Log _logXmlAfterListener = LogFactoryUtil.getLog(
		BaseTransformer.class.getName() + ".XmlAfterListener");
	private static Log _logXmlBeforeListener = LogFactoryUtil.getLog(
		BaseTransformer.class.getName() + ".XmlBeforeListener");

}