/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.internal.resource.v1_0;

import com.liferay.analytics.cms.rest.dto.v1_0.ExpiredAsset;
import com.liferay.analytics.cms.rest.internal.depot.entry.util.DepotEntryUtil;
import com.liferay.analytics.cms.rest.resource.v1_0.ExpiredAssetResource;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.object.model.ObjectDefinitionTable;
import com.liferay.object.model.ObjectEntryFolderTable;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectEntryVersionTable;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.spi.expression.DSLFunction;
import com.liferay.petra.sql.dsl.spi.expression.DSLFunctionType;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.sql.Clob;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Thiago Buarque
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/expired-asset.properties",
	scope = ServiceScope.PROTOTYPE, service = ExpiredAssetResource.class
)
public class ExpiredAssetResourceImpl extends BaseExpiredAssetResourceImpl {

	@Override
	public Page<ExpiredAsset> getExpiredAssetsPage(
			Long depotEntryId, String languageId, Pagination pagination)
		throws Exception {

		Long[] groupIds = DepotEntryUtil.getGroupIds(
			DepotEntryUtil.getDepotEntries(
				contextCompany.getCompanyId(), depotEntryId));

		Locale locale = LocaleUtil.fromLanguageId(languageId, true, false);

		if (locale == null) {
			locale = contextUser.getLocale();
		}

		return Page.of(
			transform(
				_getExpiredObjectsList(
					languageId, groupIds, LanguageUtil.getLanguageId(locale),
					pagination),
				objects -> {
					ExpiredAsset expiredAsset = new ExpiredAsset();

					long objectEntryId = (Long)objects[3];

					expiredAsset.setHref(
						() -> StringBundler.concat(
							_portal.getPortalURL(contextHttpServletRequest),
							_portal.getPathMain(),
							GroupConstants.CMS_FRIENDLY_URL,
							"/edit_content_item?&p_l_mode=read&p_p_state=",
							LiferayWindowState.POP_UP, "&objectEntryId=",
							objectEntryId));

					expiredAsset.setTitle(
						() -> {
							String localizedTitle = String.valueOf(objects[1]);

							if (Validator.isNotNull(localizedTitle)) {
								return localizedTitle;
							}

							return String.valueOf(objects[4]);
						});
					expiredAsset.setUsages(
						() -> _getUsages(
							String.valueOf(objects[0]), groupIds,
							(Long)objects[2], objectEntryId));

					return expiredAsset;
				}),
			pagination, _getExpiredObjectsListTotalCount(languageId, groupIds));
	}

	private List<Object[]> _getExpiredObjectsList(
		String filterLanguageId, Long[] groupIds, String languageId,
		Pagination pagination) {

		DSLQuery dslQuery = DSLQueryFactoryUtil.select(
			_objectDefinitionTable.className,
			DSLFunctionFactoryUtil.castClobText(
				_getLocalizedTitleExpression(languageId)
			).as(
				"localized_title"
			),
			_objectEntryTable.objectDefinitionId,
			_objectEntryTable.objectEntryId,
			DSLFunctionFactoryUtil.castClobText(
				_getTitleExpression()
			).as(
				"title"
			)
		).from(
			ObjectEntryTable.INSTANCE
		).innerJoinON(
			_objectDefinitionTable,
			_objectDefinitionTable.objectDefinitionId.eq(
				_objectEntryTable.objectDefinitionId)
		).innerJoinON(
			_objectEntryFolderTable,
			_objectEntryFolderTable.objectEntryFolderId.eq(
				_objectEntryTable.objectEntryFolderId)
		).innerJoinON(
			_objectEntryVersionTable,
			_objectEntryVersionTable.objectEntryId.eq(
				_objectEntryTable.objectEntryId
			).and(
				_objectEntryVersionTable.version.eq(_objectEntryTable.version)
			)
		).where(
			_getPredicate(groupIds, filterLanguageId)
		).orderBy(
			_objectEntryVersionTable.statusDate.descending()
		).limit(
			pagination.getStartPosition(), pagination.getEndPosition()
		);

		return _objectEntryLocalService.dslQuery(dslQuery);
	}

	private long _getExpiredObjectsListTotalCount(
		String filterLanguageId, Long[] groupIds) {

		DSLQuery dslQuery = DSLQueryFactoryUtil.select(
			DSLFunctionFactoryUtil.count(
				_objectEntryTable.objectEntryId
			).as(
				"totalCount"
			)
		).from(
			ObjectEntryTable.INSTANCE
		).innerJoinON(
			_objectDefinitionTable,
			_objectDefinitionTable.objectDefinitionId.eq(
				_objectEntryTable.objectDefinitionId)
		).innerJoinON(
			_objectEntryFolderTable,
			_objectEntryFolderTable.objectEntryFolderId.eq(
				_objectEntryTable.objectEntryFolderId)
		).innerJoinON(
			_objectEntryVersionTable,
			_objectEntryVersionTable.objectEntryId.eq(
				_objectEntryTable.objectEntryId
			).and(
				_objectEntryVersionTable.version.eq(_objectEntryTable.version)
			)
		).where(
			_getPredicate(groupIds, filterLanguageId)
		);

		List<Object[]> results = _objectEntryLocalService.dslQuery(dslQuery);

		return GetterUtil.getLong(results.get(0));
	}

	private Expression<Clob> _getLocalizedTitleExpression(String languageId) {
		Column<ObjectEntryVersionTable, Clob> contentColumn =
			ObjectEntryVersionTable.INSTANCE.content;

		DB db = DBManagerUtil.getDB();

		if (db.getDBType() == DBType.HYPERSONIC) {
			DSLFunction<Object> dslFunction1 = new DSLFunction<>(
				new DSLFunctionType("REGEXP_SUBSTRING(", ")"),
				new DSLFunction<>(
					new DSLFunctionType("CONVERT(", ", SQL_VARCHAR)"),
					contentColumn),
				new DSLFunction<>(
					new DSLFunctionType("CAST(", " AS LONGVARCHAR)"),
					new Scalar<>("(?s)\"title_i18n\"\\s*:\\s*(\\{.*?\\})")));

			DSLFunction<Object> dslFunction2 = new DSLFunction<>(
				new DSLFunctionType("REGEXP_SUBSTRING(", ")"), dslFunction1,
				new DSLFunction<>(
					new DSLFunctionType("CAST(", " AS LONGVARCHAR)"),
					new Scalar<>(
						StringBundler.concat(
							"\"", languageId, "\"\\s*:\\s*\"([^\"]*)\""))));

			return new DSLFunction<>(
				new DSLFunctionType("REGEXP_REPLACE(", ")"), dslFunction2,
				new DSLFunction<>(
					new DSLFunctionType("CAST(", " AS LONGVARCHAR)"),
					new Scalar<>(
						StringBundler.concat(
							"^\"", languageId, "\"\\s*:\\s*\"([^\"]*)\"$"))),
				new DSLFunction<>(
					new DSLFunctionType("CAST(", " AS LONGVARCHAR)"),
					new Scalar<>("$1")));
		}

		return _getPropertyValueExpression(
			contentColumn, "properties.title_i18n." + languageId);
	}

	private Predicate _getPredicate(Long[] groupIds, String languageId) {
		Predicate predicate = _objectEntryTable.groupId.in(
			groupIds
		).and(
			_objectEntryTable.status.eq(WorkflowConstants.STATUS_EXPIRED)
		).and(
			_objectEntryFolderTable.externalReferenceCode.in(
				new String[] {"L_CONTENTS", "L_FILES"})
		);

		if (Validator.isNotNull(languageId)) {
			predicate = predicate.and(
				DSLFunctionFactoryUtil.castClobText(
					_getLocalizedTitleExpression(languageId)
				).isNotNull());
		}

		return predicate;
	}

	private <T> Expression<T> _getPropertyValueExpression(
		Expression<T> columnExpression, String propertyPath) {

		DB db = DBManagerUtil.getDB();

		if ((db.getDBType() == DBType.MARIADB) ||
			(db.getDBType() == DBType.MYSQL)) {

			return new DSLFunction<>(
				new DSLFunctionType("JSON_UNQUOTE(", ")"),
				new DSLFunction<>(
					new DSLFunctionType("JSON_EXTRACT(", ")"), columnExpression,
					new Scalar<>("$." + propertyPath)));
		}
		else if (db.getDBType() == DBType.POSTGRESQL) {
			String[] propertyPathParts = propertyPath.split("\\.");

			Expression[] expressions =
				new Expression[propertyPathParts.length + 1];

			expressions[0] = columnExpression;

			for (int i = 1; i < expressions.length; i++) {
				expressions[i] = new Scalar<>(propertyPathParts[i]);
			}

			return new DSLFunction<>(
				new DSLFunctionType("json_extract_path_text(", ")"),
				expressions);
		}

		return new DSLFunction<>(
			new DSLFunctionType("JSON_VALUE(", ")"), columnExpression,
			new Scalar<>("$." + propertyPath));
	}

	private Expression<Clob> _getTitleExpression() {
		Column<ObjectEntryVersionTable, Clob> contentColumn =
			ObjectEntryVersionTable.INSTANCE.content;

		DB db = DBManagerUtil.getDB();

		if (db.getDBType() == DBType.HYPERSONIC) {
			DSLFunction<Object> dslFunction = new DSLFunction<>(
				new DSLFunctionType("REGEXP_SUBSTRING(", ")"),
				new DSLFunction<>(
					new DSLFunctionType("CONVERT(", ", SQL_VARCHAR)"),
					contentColumn),
				new DSLFunction<>(
					new DSLFunctionType("CAST(", " AS LONGVARCHAR)"),
					new Scalar<>(
						"\"title\"\\s*:\\s*\"(?:[^\"\\\\]|\\\\.)*\"")));

			return new DSLFunction<>(
				new DSLFunctionType("REGEXP_REPLACE(", ")"), dslFunction,
				new DSLFunction<>(
					new DSLFunctionType("CAST(", " AS LONGVARCHAR)"),
					new Scalar<>("^\"title\"\\s*:\\s*\"([^\"]*)\"$")),
				new DSLFunction<>(
					new DSLFunctionType("CAST(", " AS LONGVARCHAR)"),
					new Scalar<>("$1")));
		}

		return _getPropertyValueExpression(contentColumn, "properties.title");
	}

	private int _getUsages(
			String className, Long[] groupIds, long objectDefinitionId,
			long objectEntryId)
		throws PortalException {

		int usages =
			_layoutClassedModelUsageLocalService.
				getLayoutClassedModelUsagesCount(
					_portal.getClassNameId(className), objectEntryId);

		List<ObjectRelationship> objectRelationships =
			_objectRelationshipLocalService.
				getObjectRelationshipsByObjectDefinitionId2(objectDefinitionId);

		for (ObjectRelationship objectRelationship : objectRelationships) {
			ObjectRelatedModelsProvider objectRelatedModelsProvider =
				_objectRelatedModelsProviderRegistry.
					getObjectRelatedModelsProvider(
						className, contextCompany.getCompanyId(),
						objectRelationship.getType());

			for (long groupId : groupIds) {
				usages += objectRelatedModelsProvider.getRelatedModelsCount(
					groupId, objectRelationship.getObjectRelationshipId(),
					null, objectEntryId, null);
			}
		}

		return usages;
	}

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	private final ObjectDefinitionTable _objectDefinitionTable =
		ObjectDefinitionTable.INSTANCE;
	private final ObjectEntryFolderTable _objectEntryFolderTable =
		ObjectEntryFolderTable.INSTANCE;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	private final ObjectEntryTable _objectEntryTable =
		ObjectEntryTable.INSTANCE;
	private final ObjectEntryVersionTable _objectEntryVersionTable =
		ObjectEntryVersionTable.INSTANCE;

	@Reference
	private ObjectRelatedModelsProviderRegistry
		_objectRelatedModelsProviderRegistry;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private Portal _portal;

}