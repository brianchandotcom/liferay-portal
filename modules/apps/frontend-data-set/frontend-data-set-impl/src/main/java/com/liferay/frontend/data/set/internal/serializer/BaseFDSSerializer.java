/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.serializer;

import com.liferay.frontend.data.set.url.FDSAPIURLResolver;
import com.liferay.frontend.data.set.url.FDSAPIURLResolverRegistry;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManagerProvider;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
public abstract class BaseFDSSerializer {

	public FDSAPIURLBuilder createFDSAPIURLBuilder(
		HttpServletRequest httpServletRequest, String restApplication,
		String restEndpoint, String restSchema) {

		return new FDSAPIURLBuilderImpl(
			httpServletRequest, restApplication, restEndpoint, restSchema);
	}

	public Map<String, Object> getDataSetObjectEntryProperties(
		String externalReferenceCode, HttpServletRequest httpServletRequest) {

		ObjectEntry objectEntry = _getObjectEntry(
			_getDataSetObjectDefinition(httpServletRequest),
			externalReferenceCode);

		if (objectEntry != null) {
			return objectEntry.getProperties();
		}

		return Collections.emptyMap();
	}

	public Set<ObjectEntry> getSortedRelatedObjectEntries(
		String externalReferenceCode,
		String dataSetObjectEntryComparatorIdsPropertyKey,
		HttpServletRequest httpServletRequest, Predicate<ObjectEntry> predicate,
		String... relationshipNames) {

		ObjectDefinition dataSetObjectDefinition = _getDataSetObjectDefinition(
			httpServletRequest);

		ObjectEntry dataSetObjectEntry = _getObjectEntry(
			dataSetObjectDefinition, externalReferenceCode);

		Set<ObjectEntry> objectEntries = new TreeSet<>(
			new ObjectEntryComparator(
				ListUtil.toList(
					ListUtil.fromString(
						MapUtil.getString(
							dataSetObjectEntry.getProperties(),
							dataSetObjectEntryComparatorIdsPropertyKey),
						StringPool.COMMA),
					GetterUtil::getLong)));

		for (String relationshipName : relationshipNames) {
			objectEntries.addAll(
				_getRelatedObjectEntries(
					dataSetObjectDefinition, dataSetObjectEntry, predicate,
					relationshipName));
		}

		return objectEntries;
	}

	public interface FDSAPIURLBuilder {

		public FDSAPIURLBuilder addParameter(String name, String value);

		public FDSAPIURLBuilder addQueryString(String queryString);

		public String build();

	}

	public class FDSAPIURLBuilderImpl implements FDSAPIURLBuilder {

		public FDSAPIURLBuilderImpl(
			HttpServletRequest httpServletRequest, String restApplication,
			String restEndpoint, String restSchema) {

			_httpServletRequest = httpServletRequest;
			_restApplication = restApplication;
			_restEndpoint = restEndpoint;
			_restSchema = restSchema;
		}

		@Override
		public FDSAPIURLBuilder addParameter(String name, String value) {
			if (Validator.isNotNull(name) && Validator.isNotNull(value)) {
				_queryStringItems.add(name + CharPool.EQUAL + value);
			}

			return this;
		}

		@Override
		public FDSAPIURLBuilder addQueryString(String queryString) {
			if (Validator.isNotNull(queryString)) {
				_queryStringItems.add(queryString);
			}

			return this;
		}

		@Override
		public String build() {
			StringBundler sb = new StringBundler(
				3 + (_queryStringItems.size() * 2));

			sb.append("/o");
			sb.append(
				StringUtil.replaceLast(
					_restApplication, "/v1.0", StringPool.BLANK));
			sb.append(_restEndpoint);

			_appendParameters(sb);

			return _interpolateURL(_resolveParameters(sb.toString()));
		}

		private void _appendParameters(StringBundler sb) {
			if (_queryStringItems.isEmpty()) {
				return;
			}

			sb.append(CharPool.QUESTION);

			int count = 0;

			for (String parameter : _queryStringItems) {
				sb.append(parameter);

				count++;

				if (count < _queryStringItems.size()) {
					sb.append(CharPool.AMPERSAND);
				}
			}
		}

		private String _interpolateURL(String apiURL) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			apiURL = StringUtil.replace(
				apiURL, "{siteId}",
				String.valueOf(themeDisplay.getScopeGroupId()));
			apiURL = StringUtil.replace(
				apiURL, "{scopeKey}",
				String.valueOf(themeDisplay.getScopeGroupId()));
			apiURL = StringUtil.replace(
				apiURL, "{userId}", String.valueOf(themeDisplay.getUserId()));

			if (StringUtil.contains(apiURL, "{") && _log.isWarnEnabled()) {
				_log.warn("Unsupported parameter in API URL: " + apiURL);
			}

			return apiURL;
		}

		private String _resolveParameters(String apiURL) {
			FDSAPIURLResolver fdsAPIURLResolver =
				fdsAPIURLResolverRegistry.getFDSAPIURLResolver(
					_restApplication, _restSchema);

			if (fdsAPIURLResolver == null) {
				return apiURL;
			}

			try {
				return fdsAPIURLResolver.resolve(apiURL, _httpServletRequest);
			}
			catch (PortalException portalException) {
				_log.error(portalException);

				return apiURL;
			}
		}

		private static final Log _log = LogFactoryUtil.getLog(
			FDSAPIURLBuilderImpl.class);

		private final HttpServletRequest _httpServletRequest;
		private final List<String> _queryStringItems = new LinkedList<>();
		private final String _restApplication;
		private final String _restEndpoint;
		private final String _restSchema;

	}

	protected String getType(ObjectEntry objectEntry) {
		Map<String, Object> properties = objectEntry.getProperties();

		return GetterUtil.getString(properties.get("type"));
	}

	@Reference
	protected ObjectDefinitionLocalService dataSetObjectDefinitionLocalService;

	@Reference
	protected ObjectEntryManagerRegistry dataSetObjectEntryManagerRegistry;

	@Reference
	protected FDSAPIURLResolverRegistry fdsAPIURLResolverRegistry;

	@Reference
	protected Portal portal;

	private ObjectDefinition _getDataSetObjectDefinition(
		HttpServletRequest httpServletRequest) {

		return dataSetObjectDefinitionLocalService.fetchObjectDefinition(
			portal.getCompanyId(httpServletRequest), "DataSet");
	}

	private ObjectEntry _getObjectEntry(
		ObjectDefinition dataSetObjectDefinition,
		String externalReferenceCode) {

		ObjectEntry objectEntry = null;

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, null, null, null, null,
				LocaleUtil.getMostRelevantLocale(), null, null);

		DefaultObjectEntryManager defaultObjectEntryManager =
			DefaultObjectEntryManagerProvider.provide(
				dataSetObjectEntryManagerRegistry.getObjectEntryManager(
					dataSetObjectDefinition.getStorageType()));

		try {
			objectEntry = defaultObjectEntryManager.getObjectEntry(
				dataSetObjectDefinition.getCompanyId(), dtoConverterContext,
				externalReferenceCode, dataSetObjectDefinition, null);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get data set object entry with external " +
						"reference code " + externalReferenceCode,
					exception);
			}
		}

		return objectEntry;
	}

	private Collection<ObjectEntry> _getRelatedObjectEntries(
		ObjectDefinition dataSetObjectDefinition,
		ObjectEntry dataSetObjectEntry, Predicate<ObjectEntry> predicate,
		String relationshipName) {

		Collection<ObjectEntry> objectEntries = null;

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, null, null, null, null,
				LocaleUtil.getMostRelevantLocale(), null, null);

		DefaultObjectEntryManager defaultObjectEntryManager =
			DefaultObjectEntryManagerProvider.provide(
				dataSetObjectEntryManagerRegistry.getObjectEntryManager(
					dataSetObjectDefinition.getStorageType()));

		try {
			Page<ObjectEntry> relatedObjectEntriesPage =
				defaultObjectEntryManager.getObjectEntryRelatedObjectEntries(
					dtoConverterContext, dataSetObjectDefinition,
					dataSetObjectEntry.getId(), relationshipName,
					Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS));

			objectEntries = relatedObjectEntriesPage.getItems();

			if (predicate != null) {
				objectEntries.removeIf(
					objectEntry -> !predicate.test(objectEntry));
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get related object entries for " +
						relationshipName,
					exception);
			}
		}

		return objectEntries;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseFDSSerializer.class);

	private static class ObjectEntryComparator
		implements Comparator<ObjectEntry> {

		public ObjectEntryComparator(List<Long> ids) {
			_ids = ids;
		}

		@Override
		public int compare(
			ObjectEntry dataSetObjectEntry1, ObjectEntry dataSetObjectEntry2) {

			long id1 = dataSetObjectEntry1.getId();
			long id2 = dataSetObjectEntry2.getId();

			int index1 = _ids.indexOf(id1);
			int index2 = _ids.indexOf(id2);

			if ((index1 == -1) && (index2 == -1)) {
				Date date = dataSetObjectEntry1.getDateCreated();

				return date.compareTo(dataSetObjectEntry2.getDateCreated());
			}

			if (index1 == -1) {
				return 1;
			}

			if (index2 == -1) {
				return -1;
			}

			return Long.compare(index1, index2);
		}

		private final List<Long> _ids;

	}

}