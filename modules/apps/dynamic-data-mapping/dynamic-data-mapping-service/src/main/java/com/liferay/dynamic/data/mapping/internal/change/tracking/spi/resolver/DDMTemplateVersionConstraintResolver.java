/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateVersionLocalService;
import com.liferay.dynamic.data.mapping.util.comparator.TemplateVersionVersionComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.language.LanguageResources;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noor Najjar
 */
@Component(
	property = "service.ranking:Integer=200", service = ConstraintResolver.class
)
public class DDMTemplateVersionConstraintResolver
	implements ConstraintResolver<DDMTemplateVersion> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-dynamic-data-mapping-template-version";
	}

	@Override
	public Class<DDMTemplateVersion> getModelClass() {
		return DDMTemplateVersion.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "the-dynamic-data-mapping-template-version-was-updated-to-" +
			"latest";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return LanguageResources.getResourceBundle(locale);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"templateId", "version"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<DDMTemplateVersion>
				constraintResolverContext)
		throws PortalException {

		DDMTemplateVersion ddmTemplateVersion =
			constraintResolverContext.getSourceCTModel();

		String latestVersion = "0.0";

		try {
			latestVersion = constraintResolverContext.getInTarget(
				() -> {
					DDMTemplateVersion latestProductionTemplateVersion =
						ddmTemplateVersionLocalService.getLatestTemplateVersion(
							ddmTemplateVersion.getTemplateId());

					return latestProductionTemplateVersion.getVersion();
				});
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			return;
		}

		List<DDMTemplateVersion> ddmTemplateVersions = ListUtil.filter(
			ListUtil.sort(
				ddmTemplateVersionLocalService.getTemplateVersions(
					ddmTemplateVersion.getTemplateId()),
				new TemplateVersionVersionComparator(false)),
			templateVersion ->
				templateVersion.getCtCollectionId() ==
					ddmTemplateVersion.getCtCollectionId());

		String currentVersion = _getLatestVersion(
			latestVersion, ddmTemplateVersions.size());

		CTPersistence ctPersistence =
			ddmTemplateVersionLocalService.getCTPersistence();

		for (DDMTemplateVersion templateVersion : ddmTemplateVersions) {
			int[] productionVersionParts = StringUtil.split(
				latestVersion, StringPool.PERIOD, 0);

			int[] publicationVersionParts = StringUtil.split(
				templateVersion.getVersion(), StringPool.PERIOD, 0);

			if (productionVersionParts[0] < publicationVersionParts[0]) {
				currentVersion = _getNextVersion(currentVersion);

				continue;
			}

			templateVersion.setVersion(currentVersion);

			ddmTemplateVersionLocalService.updateDDMTemplateVersion(
				templateVersion);

			ctPersistence.flush();

			currentVersion = _getNextVersion(currentVersion);
		}

		DDMTemplate ddmTemplate = ddmTemplateLocalService.getDDMTemplate(
			ddmTemplateVersion.getTemplateId());

		DDMTemplateVersion latestPublicationTemplateVersion =
			ddmTemplateVersionLocalService.getLatestTemplateVersion(
				ddmTemplateVersion.getTemplateId());

		ddmTemplate.setVersion(latestPublicationTemplateVersion.getVersion());

		ddmTemplateLocalService.updateDDMTemplate(ddmTemplate);
	}

	@Reference
	protected DDMTemplateLocalService ddmTemplateLocalService;

	@Reference
	protected DDMTemplateVersionLocalService ddmTemplateVersionLocalService;

	private String _getLatestVersion(String version, int size) {
		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		return versionParts[0] + StringPool.PERIOD + (versionParts[1] + size);
	}

	private String _getNextVersion(String version) {
		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		return versionParts[0] + StringPool.PERIOD + --versionParts[1];
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMTemplateVersionConstraintResolver.class);

}