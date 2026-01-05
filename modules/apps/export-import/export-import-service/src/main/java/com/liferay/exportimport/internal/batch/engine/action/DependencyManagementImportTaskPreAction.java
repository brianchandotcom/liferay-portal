/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.batch.engine.action;

import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.action.ImportTaskPreAction;
import com.liferay.batch.engine.context.ImportTaskContext;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.content.processor.ExportImportContentProcessorRegistryUtil;
import com.liferay.exportimport.internal.lar.ExportImportDescriptorThreadLocal;
import com.liferay.exportimport.internal.lar.PortletDataContextThreadLocal;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Method;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Correa
 */
@Component(service = ImportTaskPreAction.class)
public class DependencyManagementImportTaskPreAction
	implements ImportTaskPreAction {

	@Override
	public void run(
			BatchEngineImportTask batchEngineImportTask,
			BatchEngineTaskItemDelegate<?> batchEngineTaskItemDelegate,
			ImportTaskContext importTaskContext, Object item)
		throws Exception {

		if (!ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
			exportImportDescriptor =
				ExportImportDescriptorThreadLocal.getExportImportDescriptor();

		if (exportImportDescriptor == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to get the ",
						ExportImportVulcanBatchEngineTaskItemDelegate.
							ExportImportDescriptor.class.getSimpleName(),
						" from the ThreadLocal"));
			}

			return;
		}

		Map<String, String[]> references =
			exportImportDescriptor.getReferences();

		if (MapUtil.isEmpty(references)) {
			return;
		}

		PortletDataContext portletDataContext =
			PortletDataContextThreadLocal.getPortletDataContext();

		if (portletDataContext == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get the " +
						PortletDataContext.class.getSimpleName() +
							" from the ThreadLocal");
			}

			return;
		}

		for (Map.Entry<String, String[]> entry : references.entrySet()) {
			String fieldName = entry.getKey();

			Method getMethod = _fetchDeclaredMethod(
				item.getClass(),
				"get" + StringUtil.upperCaseFirstLetter(fieldName));

			Method getPropertyValueMethod = null;

			if (getMethod == null) {
				getPropertyValueMethod = _fetchDeclaredMethod(
					item.getClass(), "getPropertyValue", String.class);
			}

			if ((getMethod == null) && (getPropertyValueMethod == null)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find the get method for the field \"" +
							fieldName + "\"");
				}

				continue;
			}

			Method setMethod = _fetchDeclaredMethod(
				item.getClass(), "setPropertyValue", String.class,
				Object.class);

			if (setMethod == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find the set method for the field \"" +
							fieldName + "\"");
				}

				continue;
			}

			for (String contentProcessorType : entry.getValue()) {
				ExportImportContentProcessor<?> exportImportContentProcessor =
					ExportImportContentProcessorRegistryUtil.
						getExportImportContentProcessorByContentProcessorType(
							contentProcessorType);

				if (exportImportContentProcessor == null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							StringBundler.concat(
								"Unable to get the ",
								ExportImportContentProcessor.class.
									getSimpleName(),
								" for content processor type: \"",
								contentProcessorType, "\""));
					}

					continue;
				}

				String value;

				if (getMethod != null) {
					value = GetterUtil.getString(getMethod.invoke(item));
				}
				else {
					value = GetterUtil.getString(
						getPropertyValueMethod.invoke(item, fieldName));
				}

				setMethod.invoke(
					item, fieldName,
					exportImportContentProcessor.replaceImportContentReferences(
						value, portletDataContext));
			}
		}
	}

	private Method _fetchDeclaredMethod(
		Class<?> clazz, String name, Class<?>... parameterTypes) {

		if (clazz == Object.class) {
			return null;
		}

		Method method = ReflectionUtil.fetchDeclaredMethod(
			clazz, name, parameterTypes);

		if (method == null) {
			return _fetchDeclaredMethod(
				clazz.getSuperclass(), name, parameterTypes);
		}

		return method;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DependencyManagementImportTaskPreAction.class);

}