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

package com.liferay.portal.workflow.kaleo.runtime.internal.notification.recipient.script;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.workflow.kaleo.definition.ScriptLanguage;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.notification.recipient.script.NotificationRecipientEvaluator;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Michael C. Han
 */
@Component(service = NotificationRecipientEvaluator.class)
public class MultiLanguageNotificationRecipientEvaluator
	implements NotificationRecipientEvaluator {

	@Override
	public Map<String, ?> evaluate(
			KaleoNotificationRecipient kaleoNotificationRecipient,
			ExecutionContext executionContext)
		throws PortalException {

		String notificationRecipientEvaluatorKey =
			_getNotificationRecipientEvaluatorKey(
				kaleoNotificationRecipient.getRecipientScriptLanguage(),
				kaleoNotificationRecipient.getRecipientScript());

		NotificationRecipientEvaluator notificationRecipientEvaluator =
			_serviceTrackerMap.getService(notificationRecipientEvaluatorKey);

		if (notificationRecipientEvaluator == null) {
			throw new IllegalArgumentException(
				"No notification recipient evaluator for script language " +
					notificationRecipientEvaluatorKey);
		}

		return notificationRecipientEvaluator.evaluate(
			kaleoNotificationRecipient, executionContext);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, NotificationRecipientEvaluator.class,
			"(scripting.language=*)",
			(serviceReference, emitter) -> {
				Object propertyValue = serviceReference.getProperty(
					"scripting.language");

				NotificationRecipientEvaluator notificationRecipientEvaluator =
					bundleContext.getService(serviceReference);

				try {
					for (String scriptingLanguage :
							GetterUtil.getStringValues(
								propertyValue,
								new String[] {String.valueOf(propertyValue)})) {

						emitter.emit(
							_getNotificationRecipientEvaluatorKey(
								scriptingLanguage,
								ClassUtil.getClassName(
									notificationRecipientEvaluator)));
					}
				}
				catch (KaleoDefinitionValidationException
							kaleoDefinitionValidationException) {

					throw new RuntimeException(
						kaleoDefinitionValidationException);
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private String _getNotificationRecipientEvaluatorKey(
			String language, String notificationRecipientEvaluatorClassName)
		throws KaleoDefinitionValidationException {

		ScriptLanguage scriptLanguage = ScriptLanguage.parse(language);

		if (scriptLanguage.equals(ScriptLanguage.JAVA)) {
			return language + StringPool.COLON +
				notificationRecipientEvaluatorClassName;
		}

		return language;
	}

	private ServiceTrackerMap<String, NotificationRecipientEvaluator>
		_serviceTrackerMap;

}