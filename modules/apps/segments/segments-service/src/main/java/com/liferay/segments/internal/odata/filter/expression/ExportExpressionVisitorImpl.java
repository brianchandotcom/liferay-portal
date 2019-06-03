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
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
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
public class ExportExpressionVisitorImpl implements ExpressionVisitor<Object> {

	public ExportExpressionVisitorImpl(
		PortletDataContext portletDataContext, StagedModel stagedModel,
		EntityModel entityModel, EntityModelFieldMapper entityModelFieldMapper,
		SegmentsFieldCustomizerRegistry segmentsFieldCustomizerRegistry) {

		_portletDataContext = portletDataContext;
		_stagedModel = stagedModel;
		_entityModel = entityModel;
		_entityModelFieldMapper = entityModelFieldMapper;
		_segmentsFieldCustomizerRegistry = segmentsFieldCustomizerRegistry;

		_customFieldEntityFields =
			entityModelFieldMapper.getCustomFieldEntityFields(entityModel);
	}

	@Override
	public Void visitBinaryExpressionOperation(
		BinaryExpression.Operation operation, Object left, Object right) {

		if (!Objects.equals(BinaryExpression.Operation.EQ, operation)) {
			return null;
		}

		EntityField entityField = (EntityField)left;

		if (Objects.equals(EntityField.Type.ID, entityField.getType())) {
			_exportEntityFieldIDReferences(entityField, right);
		}
		else if (_customFieldEntityFields.containsKey(entityField.getName())) {
			_exportEntityFieldCustomFieldReferences(entityField);
		}

		return null;
	}

	@Override
	public Object visitCollectionPropertyExpression(
		CollectionPropertyExpression collectionPropertyExpression) {

		return null;
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

		return null;
	}

	@Override
	public Object visitPrimitivePropertyExpression(
		PrimitivePropertyExpression primitivePropertyExpression) {

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		return entityFieldsMap.get(primitivePropertyExpression.getName());
	}

	@Override
	public Void visitUnaryExpressionOperation(
		UnaryExpression.Operation operation, Object operand) {

		return null;
	}

	private void _exportEntityFieldCustomFieldReferences(
		EntityField entityField) {

		ExpandoColumn expandoColumn = _entityModelFieldMapper.getExpandoColumn(
			entityField.getName());

		if (expandoColumn == null) {
			return;
		}

		Element entityElement = _portletDataContext.getExportDataElement(
			_stagedModel);

		_portletDataContext.addReferenceElement(
			_stagedModel, entityElement, expandoColumn,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY, false);
	}

	private void _exportEntityFieldIDReferences(
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

		ClassedModel classedModel = segmentsFieldCustomizer.getClassedModel(
			(String)value);

		if (classedModel == null) {
			return;
		}

		Element entityElement = _portletDataContext.getExportDataElement(
			_stagedModel);

		_portletDataContext.addReferenceElement(
			_stagedModel, entityElement, classedModel,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY, false);
	}

	private final Map<String, EntityField> _customFieldEntityFields;
	private final EntityModel _entityModel;
	private final EntityModelFieldMapper _entityModelFieldMapper;
	private final PortletDataContext _portletDataContext;
	private final SegmentsFieldCustomizerRegistry
		_segmentsFieldCustomizerRegistry;
	private final StagedModel _stagedModel;

}