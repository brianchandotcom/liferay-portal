/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.serializer;

import com.liferay.frontend.data.set.internal.url.FDSAPIURLBuilder;
import com.liferay.frontend.data.set.url.FDSAPIURLResolverRegistry;
import com.liferay.object.entry.util.ObjectEntryThreadLocal;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManagerProvider;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
public abstract class BaseFDSSerializer {

	public FDSAPIURLBuilder createFDSAPIURLBuilder(
		HttpServletRequest httpServletRequest, String restApplication,
		String restEndpoint, String restSchema) {

		return new FDSAPIURLBuilder(
			fdsAPIURLResolverRegistry, httpServletRequest, restApplication,
			restEndpoint, restSchema);
	}

	protected JSONArray serializeSnapshots(
			String fdsName, HttpServletRequest httpServletRequest,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			ObjectEntryManagerRegistry objectEntryManagerRegistry)
		throws Exception {

		ObjectEntryThreadLocal.setSkipObjectEntryResourcePermission(true);

		try {
			long companyId = PortalUtil.getCompanyId(httpServletRequest);
			long userId = PortalUtil.getUserId(httpServletRequest);

			ObjectDefinition objectDefinition =
				objectDefinitionLocalService.
					fetchObjectDefinitionByExternalReferenceCode(
						"L_DATA_SET_SNAPSHOT", companyId);

			ObjectEntryManager objectEntryManager =
				DefaultObjectEntryManagerProvider.provide(
					objectEntryManagerRegistry.getObjectEntryManager(
						objectDefinition.getCompanyId(),
						objectDefinition.getStorageType()));

			List<SharingEntry> sharingEntries =
				sharingEntryLocalService.getToUserSharingEntries(
					userId,
					classNameLocalService.getClassNameId(
						objectDefinition.getClassName()));

			Set<Long> sharedClassPKs = SetUtil.fromCollection(
				TransformUtil.transform(
					sharingEntries, SharingEntry::getClassPK));

			String sharedClause = "";

			if (!sharedClassPKs.isEmpty()) {
				sharedClause = StringBundler.concat(
					" or id in ('",
					StringUtil.merge(sharedClassPKs, "','"), "')");
			}

			Page<ObjectEntry> page = objectEntryManager.getObjectEntries(
				companyId, objectDefinition, null, null,
				new DefaultDTOConverterContext(
					false, null, null, null, null,
					LocaleUtil.getMostRelevantLocale(), null, null),
				StringBundler.concat(
					"(fdsName eq '", fdsName, "' and (creatorId eq ", userId,
					sharedClause, "))"),
				null, null, null);

			return JSONUtil.toJSONArray(
				page.getItems(),
				(ObjectEntry objectEntry) -> {
					Map<String, Object> properties =
						objectEntry.getProperties();

					Object viewConfig = properties.get("viewConfig");

					if (Validator.isNull(viewConfig)) {
						return null;
					}

					return JSONUtil.put(
						"configuration", viewConfig
					).put(
						"erc", objectEntry.getExternalReferenceCode()
					).put(
						"label", String.valueOf(properties.get("label"))
					).put(
						"shared", sharedClassPKs.contains(objectEntry.getId())
					);
				});
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to serialize snapshots", exception);
			}

			return JSONUtil.putAll();
		}
		finally {
			ObjectEntryThreadLocal.setSkipObjectEntryResourcePermission(false);
		}
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected FDSAPIURLResolverRegistry fdsAPIURLResolverRegistry;

	@Reference
	protected SharingEntryLocalService sharingEntryLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseFDSSerializer.class);

}