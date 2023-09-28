/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.glowroot.plugin.freemarker;

import java.util.Map;

import org.glowroot.agent.plugin.api.Agent;
import org.glowroot.agent.plugin.api.MessageSupplier;
import org.glowroot.agent.plugin.api.OptionalThreadContext;
import org.glowroot.agent.plugin.api.TimerName;
import org.glowroot.agent.plugin.api.TraceEntry;
import org.glowroot.agent.plugin.api.weaving.BindParameter;
import org.glowroot.agent.plugin.api.weaving.BindParameterArray;
import org.glowroot.agent.plugin.api.weaving.BindThrowable;
import org.glowroot.agent.plugin.api.weaving.BindTraveler;
import org.glowroot.agent.plugin.api.weaving.OnBefore;
import org.glowroot.agent.plugin.api.weaving.OnReturn;
import org.glowroot.agent.plugin.api.weaving.OnThrow;
import org.glowroot.agent.plugin.api.weaving.Pointcut;
import org.glowroot.agent.plugin.api.weaving.Shim;

/**
 * @author Fabian Bouché
 */
public class TemplatesAspect {

	@Pointcut(
		className = "com.liferay.fragment.entry.processor.freemarker.FreeMarkerFragmentEntryProcessor",
		methodName = "processFragmentEntryLinkHTML",
		methodParameterTypes = {
			"com.liferay.fragment.model.FragmentEntryLink", "java.lang.String",
			"com.liferay.fragment.processor.FragmentEntryProcessorContext"
		},
		timerName = "Fragment Entry Link Template Parser Transform"
	)
	public static class FragmentEntryLinkTransformAdvice {

		@OnBefore
		public static TraceEntry onBefore(
			OptionalThreadContext context,
			@BindParameter FragmentEntryLinkShim fragmentEntryLink,
			@BindParameter String html,
			@BindParameter FragmentEntryProcessorContextShim
				fragmentEntryProcessorContext) {

			long companyId = fragmentEntryLink.getCompanyId();
			long siteGroupId = fragmentEntryLink.getGroupId();

			StringBuilder messageBuilder = new StringBuilder();

			messageBuilder.append("Fragment Entry Link Template Parser ");
			messageBuilder.append("Transform [fragmentEntryLinkId: ");
			messageBuilder.append(fragmentEntryLink.getFragmentEntryLinkId());
			messageBuilder.append(", companyId: ");
			messageBuilder.append(companyId);
			messageBuilder.append(", siteGroupId: ");
			messageBuilder.append(siteGroupId);
			messageBuilder.append("]");

			if (TemplatesPluginProperties.captureAsOuterTransaction()) {
				context.setTransactionOuter();

				if (TemplatesPluginProperties.
						captureTemplateScriptInTransaction()) {

					context.addTransactionAttribute(
						"Fragment Entry Link html", html);
				}

				return context.startTransaction(
					"Templates", messageBuilder.toString(),
					MessageSupplier.create(messageBuilder.toString()), _timer);
			}

			return context.startTraceEntry(
				MessageSupplier.create(messageBuilder.toString()), _timer);
		}

		@OnReturn
		public static void onReturn(@BindTraveler TraceEntry traceEntry) {
			traceEntry.end();
		}

		@OnThrow
		public static void onThrow(
			@BindThrowable Throwable throwable,
			@BindTraveler TraceEntry traceEntry) {

			traceEntry.endWithError(throwable);
		}

		private static final TimerName _timer = Agent.getTimerName(
			JournalTransformAdvice.class);

	}

	@Pointcut(
		className = "com.liferay.journal.internal.transformer.JournalTransformer",
		methodName = "transform",
		methodParameterTypes = {
			"com.liferay.journal.model.JournalArticle",
			"com.liferay.dynamic.data.mapping.model.DDMTemplate",
			"com.liferay.journal.util.JournalHelper", "java.lang.String",
			"com.liferay.layout.display.page.LayoutDisplayPageProviderRegistry",
			"java.util.List",
			"com.liferay.portal.kernel.portlet.PortletRequestModel", "boolean",
			"java.lang.String", "com.liferay.portal.kernel.theme.ThemeDisplay",
			"java.lang.String"
		},
		timerName = "Journal Template Parser Transform"
	)
	public static class JournalTransformAdvice {

		@OnBefore
		public static TraceEntry onBefore(
			OptionalThreadContext context,
			@BindParameterArray Object[] parameters) {

			ThemeDisplayShim themeDisplay = (ThemeDisplayShim)parameters[9];
			DDMTemplateShim ddmTemplate = (DDMTemplateShim)parameters[1];

			long companyId = themeDisplay.getCompanyId();
			long siteGroupId = themeDisplay.getSiteGroupId();

			StringBuilder messageBuilder = new StringBuilder();

			messageBuilder.append(
				"Journal Template Parser Transform [templateId: ");
			messageBuilder.append(ddmTemplate.getTemplateId());
			messageBuilder.append(", companyId: ");
			messageBuilder.append(companyId);
			messageBuilder.append(", siteGroupId: ");
			messageBuilder.append(siteGroupId);
			messageBuilder.append("]");

			if (TemplatesPluginProperties.captureAsOuterTransaction()) {
				context.setTransactionOuter();

				if (TemplatesPluginProperties.
						captureTemplateScriptInTransaction()) {

					context.addTransactionAttribute(
						"Template script", ddmTemplate.getScript());
				}

				return context.startTransaction(
					"Templates", messageBuilder.toString(),
					MessageSupplier.create(messageBuilder.toString()), _timer);
			}

			return context.startTraceEntry(
				MessageSupplier.create(messageBuilder.toString()), _timer);
		}

		@OnReturn
		public static void onReturn(@BindTraveler TraceEntry traceEntry) {
			traceEntry.end();
		}

		@OnThrow
		public static void onThrow(
			@BindThrowable Throwable throwable,
			@BindTraveler TraceEntry traceEntry) {

			traceEntry.endWithError(throwable);
		}

		private static final TimerName _timer = Agent.getTimerName(
			JournalTransformAdvice.class);

	}

	@Pointcut(
		className = "com.liferay.portal.templateparser.Transformer",
		methodName = "transform",
		methodParameterTypes = {
			"com.liferay.portal.kernel.theme.ThemeDisplay", "java.util.Map",
			"java.lang.String", "java.lang.String",
			"com.liferay.portal.kernel.io.unsync.UnsyncStringWriter",
			"javax.servlet.http.HttpServletRequest",
			"javax.servlet.http.HttpServletResponse"
		},
		timerName = "Template Parser Transform"
	)
	public static class TransformAdvice {

		@OnBefore
		public static TraceEntry onBefore(
			OptionalThreadContext context,
			@BindParameter ThemeDisplayShim themeDisplay,
			@BindParameter Map<String, Object> contextObjects,
			@BindParameter String script, @BindParameter String type) {

			long companyId = themeDisplay.getCompanyId();
			long siteGroupId = themeDisplay.getSiteGroupId();

			String templateId = String.valueOf(
				contextObjects.get("template_id"));

			StringBuilder messageBuilder = new StringBuilder();

			messageBuilder.append("Template Parser Transform [templateId: ");
			messageBuilder.append(templateId);
			messageBuilder.append(", companyId: ");
			messageBuilder.append(companyId);
			messageBuilder.append(", siteGroupId: ");
			messageBuilder.append(siteGroupId);
			messageBuilder.append("]");

			if (TemplatesPluginProperties.captureAsOuterTransaction()) {
				context.setTransactionOuter();

				if (TemplatesPluginProperties.
						captureTemplateScriptInTransaction()) {

					context.addTransactionAttribute("Template type", type);
					context.addTransactionAttribute("Template script", script);
				}

				return context.startTransaction(
					"Templates", messageBuilder.toString(),
					MessageSupplier.create(messageBuilder.toString()), _timer);
			}

			return context.startTraceEntry(
				MessageSupplier.create(messageBuilder.toString()), _timer);
		}

		@OnReturn
		public static void onReturn(@BindTraveler TraceEntry traceEntry) {
			traceEntry.end();
		}

		@OnThrow
		public static void onThrow(
			@BindThrowable Throwable throwable,
			@BindTraveler TraceEntry traceEntry) {

			traceEntry.endWithError(throwable);
		}

		private static final TimerName _timer = Agent.getTimerName(
			TransformAdvice.class);

	}

	@Shim("com.liferay.dynamic.data.mapping.model.DDMTemplate")
	public interface DDMTemplateShim {

		public String getScript();

		public long getTemplateId();

	}

	@Shim("com.liferay.fragment.model.FragmentEntryLink")
	public interface FragmentEntryLinkShim {

		public long getCompanyId();

		public long getFragmentEntryLinkId();

		public long getGroupId();

	}

	@Shim("com.liferay.fragment.processor.FragmentEntryProcessorContext")
	public interface FragmentEntryProcessorContextShim {
	}

	@Shim("com.liferay.journal.model.JournalArticle")
	public interface JournalArticleShim {
	}

	@Shim("com.liferay.portal.kernel.theme.ThemeDisplay")
	public interface ThemeDisplayShim {

		public long getCompanyGroupId();

		public long getCompanyId();

		public long getScopeGroupId();

		public long getSiteGroupId();

	}

}