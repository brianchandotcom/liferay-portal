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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.dao.orm.PortalCustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Adolfo Pérez
 */
public class SocialRelationQuery {

	public static SocialRelationQuery getRelations(long userId) {
		return new SocialRelationQuery(
			userId, null, 0, null, QueryType.NO_ENEMY);
	}

	public static SocialRelationQuery getMutualRelations(
		long userId1, long userId2) {

		return new SocialRelationQuery(
			userId1, new Long[0], userId2, new Long[0], QueryType.MUTUAL);
	}

	public static SocialRelationQuery getMutualRelationsByType(
		long userId1, long userId2, int... socialRelationTypeIds) {

		Long[] socialRelationTypes = ArrayUtil.toLongArray(
			socialRelationTypeIds);

		return new SocialRelationQuery(
			userId1, socialRelationTypes, userId2, socialRelationTypes,
			QueryType.MUTUAL);
	}

	public static SocialRelationQuery getRelationsByType(
		long userId, int... socialRelationTypeIds) {

		Long[] socialRelationTypes = ArrayUtil.toLongArray(
			socialRelationTypeIds);

		return new SocialRelationQuery(
			userId, socialRelationTypes, 0, null, QueryType.ANY_RELATIONSHIP);
	}

	public void addParameters(QueryPos qPos) {
		qPos.add(_userId1);

		if (isMutualRelationship()) {
			qPos.add(_userId2);
		}
	}

	public String getJoin() {
		String clause = null;

		if (isAnyRelationship()) {
			clause = PortalCustomSQLUtil.get(_JOIN_BY_SOCIAL_RELATION);
		}
		else if (isMutualRelationship()) {
			clause = PortalCustomSQLUtil.get(_JOIN_BY_SOCIAL_MUTUAL_RELATION);
		}
		else if (isNoEnemyRelationship()) {
			clause = PortalCustomSQLUtil.get(
				_JOIN_BY_SOCIAL_RELATION_BUT_ENEMY);
		}
		else {
			throw new IllegalStateException(
				_QUERY_TYPE_ERROR_MESSAGE + _queryType);
		}

		return clause;
	}

	public String getWhere() {
		String clause = null;

		if (isAnyRelationship()) {
			clause = addPersonalRelationWhereClause();
		}
		else if (isMutualRelationship()) {
			clause = addMutualRelationWhereClause();
		}
		else if (isNoEnemyRelationship()) {
			clause = PortalCustomSQLUtil.get(
				_JOIN_BY_SOCIAL_RELATION_BUT_ENEMY);
		}
		else {
			throw new IllegalStateException(
				_QUERY_TYPE_ERROR_MESSAGE + _queryType);
		}

		return clause;
	}

	protected SocialRelationQuery(
		long userId1, Long[] relationTypeIds1, long userId2,
		Long[] relationTypeIds2, QueryType queryType) {

		_queryType = queryType;
		_firstUserRelationTypeIds = relationTypeIds1;
		_secondUserRelationTypeIds = relationTypeIds2;
		_userId1 = userId1;
		_userId2 = userId2;
	}

	protected String addInOrEqual(String sql, String fieldName, Long[] values) {
		if (ArrayUtil.isEmpty(values)) {
			return sql;
		}

		StringBundler sb = new StringBundler(sql);

		sb.append(" AND (");
		sb.append(fieldName);

		if (values.length == 1) {
			sb.append(" = ");
			sb.append(values[0]);
		}
		else {
			sb.append(" IN (");
			sb.append(StringUtil.merge(values));
			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	protected String addMutualRelationWhereClause() {
		String sql = PortalCustomSQLUtil.get(_JOIN_BY_SOCIAL_MUTUAL_RELATION);

		String firstUserRelation = addInOrEqual(
			sql, "socialRelation1.type_", _firstUserRelationTypeIds);

		return addInOrEqual(
			firstUserRelation, "socialRelation2.type_",
			_secondUserRelationTypeIds);
	}

	protected String addPersonalRelationWhereClause() {
		String sql = PortalCustomSQLUtil.get(_JOIN_BY_SOCIAL_RELATION);

		return addInOrEqual(
			sql, "SocialRelation.type_", _firstUserRelationTypeIds);
	}

	protected boolean isAnyRelationship() {
		return _queryType == QueryType.ANY_RELATIONSHIP;
	}

	protected boolean isMutualRelationship() {
		return _queryType == QueryType.MUTUAL;
	}

	protected boolean isNoEnemyRelationship() {
		return _queryType == QueryType.NO_ENEMY;
	}

	private static final String _JOIN_BY_SOCIAL_MUTUAL_RELATION =
		UserFinder.class.getName() + ".joinBySocialMutualRelation";

	private static final String _JOIN_BY_SOCIAL_RELATION =
		UserFinder.class.getName() + ".joinBySocialRelation";

	private static final String _JOIN_BY_SOCIAL_RELATION_BUT_ENEMY =
		UserFinder.class.getName() + ".joinBySocialRelationButEnemy";

	private static final String _QUERY_TYPE_ERROR_MESSAGE =
		"expected one of: " + QueryType.values() + "; found: ";

	private Long[] _firstUserRelationTypeIds;
	private QueryType _queryType;
	private Long[] _secondUserRelationTypeIds;
	private long _userId1;
	private long _userId2;

	private static enum QueryType {

		ANY_RELATIONSHIP, MUTUAL, NO_ENEMY

	}

}