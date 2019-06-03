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

package com.liferay.segments.internal.odata.filter.expression;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.CollectionPropertyExpression;
import com.liferay.portal.odata.filter.expression.ComplexPropertyExpression;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.LiteralExpression;
import com.liferay.portal.odata.filter.expression.MemberExpression;
import com.liferay.portal.odata.filter.expression.MethodExpression;
import com.liferay.portal.odata.filter.expression.PrimitivePropertyExpression;
import com.liferay.portal.odata.filter.expression.PropertyExpression;
import com.liferay.portal.odata.filter.expression.UnaryExpression;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizer;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizerRegistry;
import com.liferay.segments.internal.odata.entity.EntityModelFieldMapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Eduardo García
 */
public class ImportExpressionVisitorImpl implements ExpressionVisitor<Object> {

	public ImportExpressionVisitorImpl(
		PortletDataContext portletDataContext, EntityModel entityModel,
		EntityModelFieldMapper entityModelFieldMapper, String filterString,
		SegmentsFieldCustomizerRegistry segmentsFieldCustomizerRegistry) {

		_portletDataContext = portletDataContext;
		_entityModel = entityModel;
		_entityModelFieldMapper = entityModelFieldMapper;
		_segmentsFieldCustomizerRegistry = segmentsFieldCustomizerRegistry;

		_customFieldEntityFields =
			entityModelFieldMapper.getCustomFieldEntityFields(entityModel);

		_filterStringSB = new StringBuilder(filterString);
	}

	@Override
	public String visitBinaryExpressionOperation(
		BinaryExpression.Operation operation, Object left, Object right) {

		if (!Objects.equals(BinaryExpression.Operation.EQ, operation)) {
			return _filterStringSB.toString();
		}

		EntityField entityField = (EntityField)left;

		if (Objects.equals(EntityField.Type.ID, entityField.getType())) {
			_importEntityFieldIDReferences(entityField, right);
		}
		else if (_customFieldEntityFields.containsKey(entityField.getName())) {
			_importEntityFieldCustomFieldReferences(entityField);
		}

		return _filterStringSB.toString();
	}

	@Override
	public Object visitCollectionPropertyExpression(
		CollectionPropertyExpression collectionPropertyExpression) {

		return _filterStringSB.toString();
	}

	@Override
	public Object visitComplexPropertyExpression(
		ComplexPropertyExpression complexPropertyExpression) {

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		ComplexEntityField complexEntityField =
			(ComplexEntityField)entityFieldsMap.get(
				complexPropertyExpression.getName());

		PropertyExpression propertyExpression =
			complexPropertyExpression.getPropertyExpression();

		Map<String, EntityField> complexEntityFieldEntityFieldsMap =
			complexEntityField.getEntityFieldsMap();

		return complexEntityFieldEntityFieldsMap.get(
			propertyExpression.getName());
	}

	@Override
	public Object visitLiteralExpression(LiteralExpression literalExpression) {
		return StringUtil.removeChar(
			literalExpression.getText(), CharPool.APOSTROPHE);
	}

	@Override
	public Object visitMemberExpression(MemberExpression memberExpression)
		throws ExpressionVisitException {

		Expression expression = memberExpression.getExpression();

		return expression.accept(this);
	}

	@Override
	public Object visitMethodExpression(
		List<Object> expressions, MethodExpression.Type type) {

		return _filterStringSB.toString();
	}

	@Override
	public Object visitPrimitivePropertyExpression(
		PrimitivePropertyExpression primitivePropertyExpression) {

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		return entityFieldsMap.get(primitivePropertyExpression.getName());
	}

	@Override
	public String visitUnaryExpressionOperation(
		UnaryExpression.Operation operation, Object operand) {

		return _filterStringSB.toString();
	}

	private void _importEntityFieldCustomFieldReferences(
		EntityField entityField) {

		long expandoColumnId = _entityModelFieldMapper.getExpandoColumnId(
			entityField.getName());

		Map<Long, Long> newPrimaryKeysMap =
			(Map<Long, Long>)_portletDataContext.getNewPrimaryKeysMap(
				ExpandoColumn.class);

		long newPrimaryKey = MapUtil.getLong(
			newPrimaryKeysMap, GetterUtil.getLong(expandoColumnId),
			GetterUtil.getLong(expandoColumnId));

		_replace(
			_filterStringSB, String.valueOf(expandoColumnId),
			String.valueOf(newPrimaryKey));
	}

	private void _importEntityFieldIDReferences(
		EntityField entityField, Object value) {

		if (!Objects.equals(EntityField.Type.ID, entityField.getType())) {
			return;
		}

		Optional<SegmentsFieldCustomizer> segmentsFieldCustomizerOptional =
			_segmentsFieldCustomizerRegistry.getSegmentFieldCustomizerOptional(
				_entityModel.getName(), entityField.getName());

		if (!segmentsFieldCustomizerOptional.isPresent()) {
			return;
		}

		SegmentsFieldCustomizer segmentsFieldCustomizer =
			segmentsFieldCustomizerOptional.get();

		String className = segmentsFieldCustomizer.getClassName();

		if (className == null) {
			return;
		}

		Map<Long, Long> newPrimaryKeysMap =
			(Map<Long, Long>)_portletDataContext.getNewPrimaryKeysMap(
				className);

		long newPrimaryKey = MapUtil.getLong(
			newPrimaryKeysMap, GetterUtil.getLong(value),
			GetterUtil.getLong(value));

		_replace(_filterStringSB, (String)value, String.valueOf(newPrimaryKey));
	}

	private void _replace(StringBuilder sb, String oldValue, String newValue) {
		int pos = sb.indexOf(oldValue);

		sb.replace(pos, pos + oldValue.length(), newValue);
	}

	private final Map<String, EntityField> _customFieldEntityFields;
	private final EntityModel _entityModel;
	private final EntityModelFieldMapper _entityModelFieldMapper;
	private final StringBuilder _filterStringSB;
	private final PortletDataContext _portletDataContext;
	private final SegmentsFieldCustomizerRegistry
		_segmentsFieldCustomizerRegistry;

}