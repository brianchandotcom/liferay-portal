/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.guardrail.util;

import com.google.api.gax.rpc.NotFoundException;
import com.google.cloud.modelarmor.v1.LocationName;
import com.google.cloud.modelarmor.v1.ModelArmorClient;
import com.google.cloud.modelarmor.v1.Template;
import com.google.protobuf.FieldMask;

import com.liferay.ai.hub.internal.guardrail.ModelArmorTemplateConfig;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author João Victor Alves
 */
public class ModelArmorTemplateUtil {

	public static void createModelArmorTemplate(
		String projectId, ModelArmorClient modelArmorClient,
		ModelArmorTemplateConfig modelArmorTemplateConfig) {

		modelArmorClient.createTemplate(
			LocationName.of(projectId, modelArmorTemplateConfig.getLocation()),
			modelArmorTemplateConfig.getTemplate(),
			modelArmorTemplateConfig.getTemplateId());
	}

	public static void deleteModelArmorTemplate(
		String projectId, ModelArmorClient modelArmorClient, String location,
		String templateId) {

		try {
			modelArmorClient.deleteTemplate(
				StringBundler.concat(
					"projects/", projectId, "/locations/", location,
					"/templates/", templateId));
		}
		catch (NotFoundException notFoundException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Model Armor template not found, nothing to delete: " +
						templateId,
					notFoundException);
			}
		}
	}

	public static void updateModelArmorTemplate(
		String projectId, ModelArmorClient modelArmorClient,
		ModelArmorTemplateConfig modelArmorTemplateConfig) {

		if ((modelArmorTemplateConfig == null) ||
			Validator.isNull(modelArmorTemplateConfig.getTemplateId())) {

			return;
		}

		Template template = Template.newBuilder(
			modelArmorTemplateConfig.getTemplate()
		).setName(
			StringBundler.concat(
				"projects/", projectId, "/locations/",
				modelArmorTemplateConfig.getLocation(), "/templates/",
				modelArmorTemplateConfig.getTemplateId())
		).build();

		modelArmorClient.updateTemplate(
			template,
			FieldMask.newBuilder(
			).addPaths(
				"filter_config"
			).addPaths(
				"labels"
			).addPaths(
				"template_metadata"
			).build());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ModelArmorTemplateUtil.class);

}