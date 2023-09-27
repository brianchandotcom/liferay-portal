/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.odata.filter.expression;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.odata.filter.expression.field.predicate.provider.FieldPredicateProvider;
import com.liferay.object.related.models.ObjectRelatedModelsPredicateProvider;
import com.liferay.object.related.models.ObjectRelatedModelsPredicateProviderRegistry;
import com.liferay.object.relationship.util.ObjectRelationshipUtil;
import com.liferay.object.rest.internal.odata.filter.expression.field.predicate.provider.FieldPredicateProviderTracker;
import com.liferay.object.rest.internal.util.BinaryExpressionConverterUtil;
import com.liferay.object.rest.odata.entity.v1_0.provider.EntityModelProvider;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalServiceUtil;
import com.liferay.petra.function.UnsafeBiFunction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.spi.expression.DefaultPredicate;
import com.liferay.petra.sql.dsl.spi.expression.Operand;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.CollectionPropertyExpression;
import com.liferay.portal.odata.filter.expression.ComplexPropertyExpression;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.LambdaFunctionExpression;
import com.liferay.portal.odata.filter.expression.LambdaVariableExpression;
import com.liferay.portal.odata.filter.expression.ListExpression;
import com.liferay.portal.odata.filter.expression.LiteralExpression;
import com.liferay.portal.odata.filter.expression.MemberExpression;
import com.liferay.portal.odata.filter.expression.MethodExpression;
import com.liferay.portal.odata.filter.expression.PrimitivePropertyExpression;
import com.liferay.portal.odata.filter.expression.PropertyExpression;
import com.liferay.portal.odata.filter.expression.UnaryExpression;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.ServerErrorException;

/**
 * @author Marco Leo
 */
public class PredicateExpressionVisitorImpl
	implements ExpressionVisitor<Object> {

	public PredicateExpressionVisitorImpl(
		EntityModel entityModel, EntityModelProvider entityModelProvider,
		FieldPredicateProviderTracker fieldPredicateProviderTracker,
		ObjectDefinition objectDefinition,
		ObjectFieldBusinessTypeRegistry objectFieldBusinessTypeRegistry,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelatedModelsPredicateProviderRegistry
			objectRelatedModelsPredicateProviderRegistry) {

		this(
			entityModel, entityModelProvider, fieldPredicateProviderTracker,
			new HashMap<>(), objectDefinition, objectFieldBusinessTypeRegistry,
			objectFieldLocalService,
			objectRelatedModelsPredicateProviderRegistry);
	}

	@Override
	public Predicate visitBinaryExpressionOperation(
		BinaryExpression.Operation operation, Object left, Object right) {

		Predicate predicate = null;

		if (_isComplexProperExpression(left)) {
			predicate = _getObjectRelationshipPredicate(
				left,
				(objectFieldName, relatedObjectDefinition) -> _getPredicate(
					objectFieldName, relatedObjectDefinition, operation,
					right));
		}
		else {
			predicate = _getPredicate(
				left, _objectDefinition, operation, right);
		}

		if (predicate != null) {
			return predicate;
		}

		throw new UnsupportedOperationException(
			"Unsupported method visitBinaryExpressionOperation with " +
				"operation " + operation);
	}

	@Override
	public Predicate visitCollectionPropertyExpression(
			CollectionPropertyExpression collectionPropertyExpression)
		throws ExpressionVisitException {

		return _visitCollectionPropertyExpression(
			collectionPropertyExpression, _objectDefinition);
	}

	@Override
	public Object visitComplexPropertyExpression(
			ComplexPropertyExpression complexPropertyExpression)
		throws ExpressionVisitException {

		PropertyExpression propertyExpression =
			complexPropertyExpression.getPropertyExpression();

		if (propertyExpression instanceof CollectionPropertyExpression) {
			return _getObjectRelationshipPredicate(
				complexPropertyExpression.toString(),
				(objectFieldName, relatedObjectDefinition) ->
					_visitCollectionPropertyExpression(
						(CollectionPropertyExpression)propertyExpression,
						relatedObjectDefinition));
		}

		if (propertyExpression instanceof ComplexPropertyExpression) {
			return _handleComplexPropertyExpression(
				propertyExpression,
				new ArrayList<String>() {
					{
						add(complexPropertyExpression.getName());
					}
				});
		}

		return complexPropertyExpression.toString();
	}

	@Override
	public Object visitLambdaFunctionExpression(
			LambdaFunctionExpression.Type type, String variableName,
			Expression expression)
		throws ExpressionVisitException {

		return expression.accept(this);
	}

	@Override
	public Object visitLambdaVariableExpression(
		LambdaVariableExpression lambdaVariableExpression) {

		return _lambdaVariableExpressionFieldNames.get(
			lambdaVariableExpression.getVariableName());
	}

	@Override
	public Predicate visitListExpressionOperation(
			ListExpression.Operation operation, Object left,
			List<Object> rights)
		throws ExpressionVisitException {

		if (Objects.equals(ListExpression.Operation.IN, operation)) {
			Predicate predicate = null;

			if (_isComplexProperExpression(left)) {
				predicate = _getObjectRelationshipPredicate(
					left,
					(objectFieldName, relatedObjectDefinition) ->
						_getInPredicate(
							objectFieldName, relatedObjectDefinition, rights));
			}
			else {
				predicate = _getInPredicate(left, _objectDefinition, rights);
			}

			return predicate;
		}

		throw new UnsupportedOperationException(
			"Unsupported method visitListExpressionOperation with operation " +
				operation);
	}

	@Override
	public Object visitLiteralExpression(LiteralExpression literalExpression) {
		if (Objects.equals(
				LiteralExpression.Type.BOOLEAN, literalExpression.getType())) {

			return GetterUtil.getBoolean(literalExpression.getText());
		}
		else if (Objects.equals(
					LiteralExpression.Type.DATE, literalExpression.getType())) {

			return GetterUtil.getDate(
				literalExpression.getText(),
				DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd"));
		}
		else if (Objects.equals(
					LiteralExpression.Type.DOUBLE,
					literalExpression.getType())) {

			return GetterUtil.getDouble(literalExpression.getText());
		}
		else if (Objects.equals(
					LiteralExpression.Type.INTEGER,
					literalExpression.getType())) {

			return GetterUtil.getLong(literalExpression.getText());
		}
		else if (Objects.equals(
					LiteralExpression.Type.NULL, literalExpression.getType())) {

			return null;
		}
		else if (Objects.equals(
					LiteralExpression.Type.STRING,
					literalExpression.getType())) {

			return StringUtil.replace(
				StringUtil.unquote(literalExpression.getText()),
				StringPool.DOUBLE_APOSTROPHE, StringPool.APOSTROPHE);
		}

		return literalExpression.getText();
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

		if (expressions.size() == 2) {
			String left = (String)expressions.get(0);
			Object fieldValue = expressions.get(1);

			Predicate predicate = null;

			if (type == MethodExpression.Type.CONTAINS) {
				if (_isComplexProperExpression(left)) {
					predicate = _getObjectRelationshipPredicate(
						left,
						(objectFieldName, relatedObjectDefinition) -> _contains(
							objectFieldName, fieldValue,
							relatedObjectDefinition));
				}
				else {
					predicate = _contains(left, fieldValue, _objectDefinition);
				}

				if (predicate != null) {
					return predicate;
				}
			}
			else if (type == MethodExpression.Type.STARTS_WITH) {
				if (_isComplexProperExpression(left)) {
					predicate = _getObjectRelationshipPredicate(
						left,
						(objectFieldName, relatedObjectDefinition) ->
							_startsWith(
								objectFieldName, fieldValue,
								relatedObjectDefinition));
				}
				else {
					predicate = _startsWith(
						left, fieldValue, _objectDefinition);
				}

				if (predicate != null) {
					return predicate;
				}
			}
		}

		throw new UnsupportedOperationException(
			StringBundler.concat(
				"Unsupported method visitMethodExpression with method type ",
				type, " and ", expressions.size(), " parameters"));
	}

	@Override
	public Object visitPrimitivePropertyExpression(
		PrimitivePropertyExpression primitivePropertyExpression) {

		return primitivePropertyExpression.getName();
	}

	@Override
	public Predicate visitUnaryExpressionOperation(
		UnaryExpression.Operation operation, Object operand) {

		if (!Objects.equals(UnaryExpression.Operation.NOT, operation)) {
			throw new UnsupportedOperationException(
				"Unsupported method visitUnaryExpressionOperation with " +
					"operation " + operation);
		}

		DefaultPredicate defaultPredicate = (DefaultPredicate)operand;

		if (Objects.equals(Operand.IN, defaultPredicate.getOperand())) {
			return new DefaultPredicate(
				defaultPredicate.getLeftExpression(), Operand.NOT_IN,
				defaultPredicate.getRightExpression());
		}

		return Predicate.not(defaultPredicate);
	}

	private PredicateExpressionVisitorImpl(
		EntityModel entityModel, EntityModelProvider entityModelProvider,
		FieldPredicateProviderTracker fieldPredicateProviderTracker,
		Map<String, String> lambdaVariableExpressionFieldNames,
		ObjectDefinition objectDefinition,
		ObjectFieldBusinessTypeRegistry objectFieldBusinessTypeRegistry,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelatedModelsPredicateProviderRegistry
			objectRelatedModelsPredicateProviderRegistry) {

		_entityModels.put(
			objectDefinition.getObjectDefinitionId(), entityModel);
		_entityModelProvider = entityModelProvider;
		_fieldPredicateProviderTracker = fieldPredicateProviderTracker;
		_lambdaVariableExpressionFieldNames =
			lambdaVariableExpressionFieldNames;
		_objectDefinition = objectDefinition;
		_objectFieldBusinessTypeRegistry = objectFieldBusinessTypeRegistry;
		_objectFieldLocalService = objectFieldLocalService;
		_objectRelatedModelsPredicateProviderRegistry =
			objectRelatedModelsPredicateProviderRegistry;
	}

	private Predicate _contains(Column<?, ?> column, Object value) {
		return column.like(StringPool.PERCENT + value + StringPool.PERCENT);
	}

	private Predicate _contains(
		Object fieldName, Object fieldValue,
		ObjectDefinition objectDefinition) {

		FieldPredicateProvider fieldPredicateProvider =
			_fieldPredicateProviderTracker.getFieldPredicateProvider(
				String.valueOf(fieldName));

		if (fieldPredicateProvider != null) {
			return fieldPredicateProvider.getContainsPredicate(
				name -> _getColumn(name, objectDefinition), fieldValue);
		}

		return _contains(
			_getColumn(fieldName, objectDefinition),
			_getValue(fieldName, objectDefinition, fieldValue));
	}

	private ObjectRelationship _fetchObjectRelationship(
		ObjectDefinition objectDefinition, String objectRelationshipName) {

		try {
			return ObjectRelationshipLocalServiceUtil.
				getObjectRelationshipByObjectDefinitionId(
					objectDefinition.getObjectDefinitionId(),
					objectRelationshipName);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	private Column<?, Object> _getColumn(
		Object fieldName, ObjectDefinition objectDefinition) {

		EntityField entityField = _getEntityField(fieldName, objectDefinition);

		return (Column<?, Object>)_objectFieldLocalService.getColumn(
			objectDefinition.getObjectDefinitionId(),
			entityField.getFilterableName(null));
	}

	private EntityField _getEntityField(
		Object fieldName, ObjectDefinition objectDefinition) {

		Map<String, EntityField> entityFieldsMap = _getEntityFieldsMap(
			objectDefinition);

		return entityFieldsMap.get(GetterUtil.getString(fieldName));
	}

	private Map<String, EntityField> _getEntityFieldsMap(
		ObjectDefinition objectDefinition) {

		EntityModel entityModel = _getObjectDefinitionEntityModel(
			objectDefinition);

		return entityModel.getEntityFieldsMap();
	}

	private Predicate _getInPredicate(
		Object left, ObjectDefinition objectDefinition, List<Object> rights) {

		FieldPredicateProvider fieldPredicateProvider =
			_fieldPredicateProviderTracker.getFieldPredicateProvider(
				String.valueOf(left));

		if (fieldPredicateProvider != null) {
			return fieldPredicateProvider.getInPredicate(
				name -> _getColumn(name, objectDefinition), rights);
		}

		return _getColumn(
			left, objectDefinition
		).in(
			TransformUtil.transformToArray(
				rights, right -> _getValue(left, objectDefinition, right),
				Object.class)
		);
	}

	private EntityModel _getObjectDefinitionEntityModel(
		ObjectDefinition objectDefinition) {

		EntityModel entityModel = _entityModels.get(
			objectDefinition.getObjectDefinitionId());

		if (entityModel == null) {
			entityModel = _entityModelProvider.getEntityModel(objectDefinition);

			_entityModels.put(
				objectDefinition.getObjectDefinitionId(), entityModel);
		}

		return entityModel;
	}

	private Predicate _getObjectRelationshipPredicate(
		Object left,
		UnsafeBiFunction<String, ObjectDefinition, Predicate, Exception>
			unsafeBiFunction) {

		List<String> leftParts = ListUtil.fromString(
			(String)left, StringPool.SLASH);

		List<String> objectRelationshipNames = new ArrayList<>(
			leftParts.subList(0, leftParts.size() - 1));

		List<ObjectValuePair<ObjectRelationship, ObjectDefinition>>
			objectValuePairs = _getObjectValuePairs(
				_objectDefinition, objectRelationshipNames);

		ObjectValuePair<ObjectRelationship, ObjectDefinition> objectValuePair =
			objectValuePairs.remove(0);

		try {
			return _getObjectRelationshipPredicate(
				objectValuePair.getValue(), objectValuePairs,
				objectValuePair.getKey(),
				unsafeBiFunction.apply(
					leftParts.get(leftParts.size() - 1),
					ObjectRelationshipUtil.getRelatedObjectDefinition(
						objectValuePair.getValue(), objectValuePair.getKey())));
		}
		catch (InvalidFilterException invalidFilterException) {
			throw invalidFilterException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private Predicate _getObjectRelationshipPredicate(
			ObjectDefinition objectDefinition,
			List<ObjectValuePair<ObjectRelationship, ObjectDefinition>>
				objectValuePairs,
			ObjectRelationship objectRelationship, Predicate predicate)
		throws Exception {

		ObjectRelatedModelsPredicateProvider
			objectRelatedModelsPredicateProvider =
				_objectRelatedModelsPredicateProviderRegistry.
					getObjectRelatedModelsPredicateProvider(
						objectDefinition.getClassName(),
						objectRelationship.getType());

		if (objectValuePairs.isEmpty()) {
			return objectRelatedModelsPredicateProvider.getPredicate(
				objectRelationship, predicate);
		}

		ObjectValuePair<ObjectRelationship, ObjectDefinition> objectValuePair =
			objectValuePairs.remove(0);

		return _getObjectRelationshipPredicate(
			objectValuePair.getValue(), objectValuePairs,
			objectValuePair.getKey(),
			objectRelatedModelsPredicateProvider.getPredicate(
				objectRelationship, predicate));
	}

	private List<ObjectValuePair<ObjectRelationship, ObjectDefinition>>
		_getObjectValuePairs(
			ObjectDefinition objectDefinition,
			List<String> objectRelationshipNames) {

		List<ObjectValuePair<ObjectRelationship, ObjectDefinition>>
			objectValuePairs = new ArrayList<>();

		for (String objectRelationshipName : objectRelationshipNames) {
			ObjectRelationship objectRelationship = _fetchObjectRelationship(
				objectDefinition, objectRelationshipName);

			if (objectRelationship == null) {
				continue;
			}

			objectValuePairs.add(
				new ObjectValuePair<>(objectRelationship, objectDefinition));

			objectDefinition =
				ObjectRelationshipUtil.getRelatedObjectDefinition(
					objectDefinition, objectRelationship);
		}

		if (objectValuePairs.isEmpty()) {
			throw new ServerErrorException(
				500,
				new Exception(
					StringBundler.concat(
						"Unable to get object value pairs for object ",
						"definition ", objectDefinition.getObjectDefinitionId(),
						" and object relationship: ",
						StringUtil.merge(objectRelationshipNames))));
		}

		Collections.reverse(objectValuePairs);

		return objectValuePairs;
	}

	private Predicate _getPredicate(
		Object left, ObjectDefinition objectDefinition,
		BinaryExpression.Operation operation, Object right) {

		Predicate predicate = null;

		if (Objects.equals(BinaryExpression.Operation.AND, operation)) {
			predicate = Predicate.and(
				Predicate.withParentheses((Predicate)left),
				Predicate.withParentheses((Predicate)right));
		}
		else if (Objects.equals(BinaryExpression.Operation.OR, operation)) {
			predicate = Predicate.or(
				Predicate.withParentheses((Predicate)left),
				Predicate.withParentheses((Predicate)right));
		}
		else {
			ObjectField objectField = _objectFieldLocalService.fetchObjectField(
				objectDefinition.getObjectDefinitionId(), String.valueOf(left));

			if (objectField == null) {
				FieldPredicateProvider fieldPredicateProvider =
					_fieldPredicateProviderTracker.getFieldPredicateProvider(
						String.valueOf(left));

				if (fieldPredicateProvider != null) {
					predicate =
						fieldPredicateProvider.getBinaryExpressionPredicate(
							name -> _getColumn(name, objectDefinition), left,
							objectDefinition.getObjectDefinitionId(), operation,
							right);
				}
			}
			else if (StringUtil.equals(
						objectField.getBusinessType(),
						ObjectFieldConstants.
							BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

				predicate = _contains(left, right, objectDefinition);
			}
		}

		if (predicate != null) {
			return predicate;
		}

		return BinaryExpressionConverterUtil.getExpressionPredicate(
			_getColumn(left, objectDefinition), operation,
			_getValue(left, objectDefinition, right));
	}

	private Object _getValue(
		Object left, ObjectDefinition objectDefinition, Object right) {

		EntityField entityField = _getEntityField(left, objectDefinition);

		EntityField.Type entityType = entityField.getType();

		DB db = DBManagerUtil.getDB();

		if (entityType.equals(EntityField.Type.DATE_TIME) &&
			(db.getDBType() == DBType.HYPERSONIC)) {

			try {
				Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
					"dd-MMM-yyyy HH:mm:ss.SSS");

				DateFormat dateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss");

				Date date = dateFormat.parse(right.toString());

				right = format.format(date);
			}
			catch (ParseException parseException) {
				throw new RuntimeException(parseException);
			}
		}

		String entityFieldFilterableName = entityField.getFilterableName(null);
		String entityFieldName = entityField.getName();

		try {
			ObjectField objectField = _objectFieldLocalService.getObjectField(
				_objectDefinition.getObjectDefinitionId(),
				entityFieldFilterableName);

			ObjectFieldBusinessType objectFieldBusinessType =
				_objectFieldBusinessTypeRegistry.getObjectFieldBusinessType(
					objectField.getBusinessType());

			Object value = objectFieldBusinessType.getValue(
				objectField, PrincipalThreadLocal.getUserId(),
				Collections.singletonMap(entityFieldName, right));

			if (value == null) {
				return right;
			}

			if (Objects.equals(
					objectFieldBusinessType.getDBType(),
					ObjectFieldConstants.DB_TYPE_LONG) &&
				Validator.isNumber(String.valueOf(value))) {

				return GetterUtil.getLong(value);
			}

			return value;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return right;
		}
	}

	private Object _handleComplexPropertyExpression(
		PropertyExpression propertyExpression,
		List<String> relationshipsNames) {

		if (propertyExpression instanceof CollectionPropertyExpression) {
			relationshipsNames.add(propertyExpression.toString());

			return _getObjectRelationshipPredicate(
				StringUtil.merge(relationshipsNames, StringPool.SLASH),
				(objectFieldName, relatedObjectDefinition) ->
					_visitCollectionPropertyExpression(
						(CollectionPropertyExpression)propertyExpression,
						relatedObjectDefinition));
		}
		else if (propertyExpression instanceof ComplexPropertyExpression) {
			ComplexPropertyExpression complexPropertyExpression =
				(ComplexPropertyExpression)propertyExpression;

			relationshipsNames.add(propertyExpression.getName());

			return _handleComplexPropertyExpression(
				complexPropertyExpression.getPropertyExpression(),
				relationshipsNames);
		}

		relationshipsNames.add(propertyExpression.toString());

		return StringUtil.merge(relationshipsNames, StringPool.SLASH);
	}

	private boolean _isComplexProperExpression(Object object) {
		if (object instanceof String) {
			String string = (String)object;

			return string.contains(StringPool.SLASH);
		}

		return false;
	}

	private Predicate _startsWith(Column<?, ?> column, Object value) {
		return column.like(value + StringPool.PERCENT);
	}

	private Predicate _startsWith(
		Object fieldName, Object fieldValue,
		ObjectDefinition objectDefinition) {

		FieldPredicateProvider fieldPredicateProvider =
			_fieldPredicateProviderTracker.getFieldPredicateProvider(
				String.valueOf(fieldName));

		if (fieldPredicateProvider != null) {
			return fieldPredicateProvider.getStartsWithPredicate(
				name -> _getColumn(name, objectDefinition), fieldValue);
		}

		return _startsWith(
			_getColumn(fieldName, objectDefinition),
			_getValue(fieldName, objectDefinition, fieldValue));
	}

	private Predicate _visitCollectionPropertyExpression(
			CollectionPropertyExpression collectionPropertyExpression,
			ObjectDefinition objectDefinition)
		throws ExpressionVisitException {

		LambdaFunctionExpression lambdaFunctionExpression =
			collectionPropertyExpression.getLambdaFunctionExpression();

		return (Predicate)lambdaFunctionExpression.accept(
			new PredicateExpressionVisitorImpl(
				_getObjectDefinitionEntityModel(objectDefinition),
				_entityModelProvider, _fieldPredicateProviderTracker,
				Collections.singletonMap(
					lambdaFunctionExpression.getVariableName(),
					collectionPropertyExpression.getName()),
				objectDefinition, _objectFieldBusinessTypeRegistry,
				_objectFieldLocalService,
				_objectRelatedModelsPredicateProviderRegistry));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PredicateExpressionVisitorImpl.class);

	private EntityModelProvider _entityModelProvider;
	private final Map<Long, EntityModel> _entityModels = new HashMap<>();
	private FieldPredicateProviderTracker _fieldPredicateProviderTracker;
	private final Map<String, String> _lambdaVariableExpressionFieldNames;
	private final ObjectDefinition _objectDefinition;
	private final ObjectFieldBusinessTypeRegistry
		_objectFieldBusinessTypeRegistry;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRelatedModelsPredicateProviderRegistry
		_objectRelatedModelsPredicateProviderRegistry;

}