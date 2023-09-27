/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.odata;

import com.fasterxml.jackson.databind.util.ISO8601Utils;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.ComplexPropertyExpression;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.ListExpression;
import com.liferay.portal.odata.filter.expression.LiteralExpression;
import com.liferay.portal.odata.filter.expression.MemberExpression;
import com.liferay.portal.odata.filter.expression.MethodExpression;
import com.liferay.portal.odata.filter.expression.PrimitivePropertyExpression;
import com.liferay.portal.odata.filter.expression.PropertyExpression;
import com.liferay.portal.odata.filter.expression.UnaryExpression;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.text.ParsePosition;

import java.time.Duration;
import java.time.Instant;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class ExpressionVisitorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_expressionVisitorImpl = new ExpressionVisitorImpl(0, _entityModel);
	}

	@Test
	public void testVisitBinaryExpressionOperationSub() throws Exception {
		Duration duration = Duration.ofDays(1);

		Date initialDate = new Date();

		Instant initialInstant = initialDate.toInstant();

		initialInstant = initialInstant.minusMillis(duration.toMillis());

		Date date = ISO8601Utils.parse(
			(String)_expressionVisitorImpl.visitBinaryExpressionOperation(
				BinaryExpression.Operation.SUB,
				ExpressionVisitorImpl.MethodType.NOW, duration),
			new ParsePosition(0));

		Instant instant = Instant.ofEpochMilli(date.getTime());

		Date finalDate = new Date();

		Instant finalInstant = finalDate.toInstant();

		finalInstant = finalInstant.minusMillis(duration.toMillis());

		Assert.assertTrue(
			instant.getEpochSecond() >= initialInstant.getEpochSecond());
		Assert.assertTrue(
			instant.getEpochSecond() <= finalInstant.getEpochSecond());
	}

	@Test
	public void testVisitBinaryExpressionOperationSubwithDate()
		throws Exception {

		Assert.assertEquals(
			"2022-12-27T23:00:00Z",
			_expressionVisitorImpl.visitBinaryExpressionOperation(
				BinaryExpression.Operation.SUB, "2022-12-28T23:00:00.000Z",
				Duration.ofDays(1)));
	}

	@Test
	public void testVisitBinaryExpressionOperationWithAndOperation()
		throws Exception {

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		JSONObject jsonObject =
			(JSONObject)_expressionVisitorImpl.visitBinaryExpressionOperation(
				BinaryExpression.Operation.AND,
				_expressionVisitorImpl.visitBinaryExpressionOperation(
					BinaryExpression.Operation.EQ, entityFieldsMap.get("title"),
					"title1"),
				_expressionVisitorImpl.visitBinaryExpressionOperation(
					BinaryExpression.Operation.LT, entityFieldsMap.get("id"),
					"2"));

		Assert.assertEquals("and", jsonObject.getString("conjunctionName"));
		Assert.assertEquals("group_1", jsonObject.getString("groupId"));

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"operatorName", "eq"
				).put(
					"propertyName", "title"
				).put(
					"value", "title1"
				),
				JSONUtil.put(
					"operatorName", "lt"
				).put(
					"propertyName", "id"
				).put(
					"value", "2"
				)
			).toString(),
			itemsJSONArray.toString());
	}

	@Test
	public void testVisitBinaryExpressionOperationWithComplexEntityField()
		throws Exception {

		BinaryExpression binaryExpression = new BinaryExpression() {

			@Override
			public <T> T accept(ExpressionVisitor<T> expressionVisitor)
				throws ExpressionVisitException {

				Expression leftOperationExpression =
					getLeftOperationExpression();

				Expression rightOperationExpression =
					getRightOperationExpression();

				return expressionVisitor.visitBinaryExpressionOperation(
					getOperation(),
					leftOperationExpression.accept(expressionVisitor),
					rightOperationExpression.accept(expressionVisitor));
			}

			@Override
			public Expression getLeftOperationExpression() {
				return new MemberExpression() {

					@Override
					public <T> T accept(ExpressionVisitor<T> expressionVisitor)
						throws ExpressionVisitException {

						return expressionVisitor.visitMemberExpression(this);
					}

					public Expression getExpression() {
						return new ComplexPropertyExpression() {

							@Override
							public <T> T accept(
									ExpressionVisitor<T> expressionVisitor)
								throws ExpressionVisitException {

								return expressionVisitor.
									visitComplexPropertyExpression(this);
							}

							@Override
							public String getName() {
								return "complexField";
							}

							@Override
							public PropertyExpression getPropertyExpression() {
								return new PrimitivePropertyExpression() {

									@Override
									public <T> T accept(
											ExpressionVisitor<T>
												expressionVisitor)
										throws ExpressionVisitException {

										return expressionVisitor.
											visitPrimitivePropertyExpression(
												this);
									}

									@Override
									public String getName() {
										return "fieldInsideComplexField";
									}

								};
							}

						};
					}

				};
			}

			@Override
			public Operation getOperation() {
				return Operation.EQ;
			}

			@Override
			public Expression getRightOperationExpression() {
				return new LiteralExpression() {

					@Override
					public <T> T accept(ExpressionVisitor<T> expressionVisitor)
						throws ExpressionVisitException {

						return expressionVisitor.visitLiteralExpression(this);
					}

					@Override
					public String getText() {
						return "complexFieldValue1";
					}

					@Override
					public Type getType() {
						return Type.STRING;
					}

				};
			}

		};

		JSONObject jsonObject = (JSONObject)binaryExpression.accept(
			_expressionVisitorImpl);

		Assert.assertEquals(
			JSONUtil.put(
				"operatorName", "eq"
			).put(
				"propertyName", "complexField/fieldInsideComplexField"
			).put(
				"value", "complexFieldValue1"
			).toString(),
			jsonObject.toString());
	}

	@Test
	public void testVisitBinaryExpressionOperationWithEqualOperation()
		throws Exception {

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		JSONObject jsonObject =
			(JSONObject)_expressionVisitorImpl.visitBinaryExpressionOperation(
				BinaryExpression.Operation.EQ, entityFieldsMap.get("title"),
				"title1");

		Assert.assertEquals(
			JSONUtil.put(
				"operatorName",
				StringUtil.toLowerCase(BinaryExpression.Operation.EQ.toString())
			).put(
				"propertyName", "title"
			).put(
				"value", "title1"
			).toString(),
			jsonObject.toString());
	}

	@Test
	public void testVisitBinaryExpressionOperationWithSameTitleNestedOperations()
		throws Exception {

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		JSONObject jsonObject =
			(JSONObject)_expressionVisitorImpl.visitBinaryExpressionOperation(
				BinaryExpression.Operation.OR,
				_expressionVisitorImpl.visitBinaryExpressionOperation(
					BinaryExpression.Operation.EQ, entityFieldsMap.get("title"),
					"title1"),
				_expressionVisitorImpl.visitBinaryExpressionOperation(
					BinaryExpression.Operation.AND,
					_expressionVisitorImpl.visitBinaryExpressionOperation(
						BinaryExpression.Operation.EQ,
						entityFieldsMap.get("title"), "title1"),
					_expressionVisitorImpl.visitBinaryExpressionOperation(
						BinaryExpression.Operation.EQ,
						entityFieldsMap.get("title"), "title1")));

		Assert.assertEquals("or", jsonObject.getString("conjunctionName"));
		Assert.assertEquals("group_2", jsonObject.getString("groupId"));

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"operatorName", "eq"
				).put(
					"propertyName", "title"
				).put(
					"value", "title1"
				),
				JSONUtil.put(
					"conjunctionName", "and"
				).put(
					"groupId", "group_1"
				).put(
					"items",
					JSONUtil.putAll(
						JSONUtil.put(
							"operatorName", "eq"
						).put(
							"propertyName", "title"
						).put(
							"value", "title1"
						),
						JSONUtil.put(
							"operatorName", "eq"
						).put(
							"propertyName", "title"
						).put(
							"value", "title1"
						))
				)
			).toString(),
			itemsJSONArray.toString());
	}

	@Test
	public void testVisitBinaryExpressionOperationWithSameTitleUnnestedOperations()
		throws Exception {

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		JSONObject jsonObject =
			(JSONObject)_expressionVisitorImpl.visitBinaryExpressionOperation(
				BinaryExpression.Operation.AND,
				_expressionVisitorImpl.visitBinaryExpressionOperation(
					BinaryExpression.Operation.AND,
					_expressionVisitorImpl.visitBinaryExpressionOperation(
						BinaryExpression.Operation.EQ,
						entityFieldsMap.get("title"), "title1"),
					_expressionVisitorImpl.visitBinaryExpressionOperation(
						BinaryExpression.Operation.EQ,
						entityFieldsMap.get("title"), "title1")),
				_expressionVisitorImpl.visitBinaryExpressionOperation(
					BinaryExpression.Operation.EQ, entityFieldsMap.get("title"),
					"title1"));

		Assert.assertEquals("and", jsonObject.getString("conjunctionName"));
		Assert.assertEquals("group_2", jsonObject.getString("groupId"));

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"operatorName", "eq"
				).put(
					"propertyName", "title"
				).put(
					"value", "title1"
				),
				JSONUtil.put(
					"operatorName", "eq"
				).put(
					"propertyName", "title"
				).put(
					"value", "title1"
				),
				JSONUtil.put(
					"operatorName", "eq"
				).put(
					"propertyName", "title"
				).put(
					"value", "title1"
				)
			).toString(),
			itemsJSONArray.toString());
	}

	@Test
	public void testVisitDurationLiteralExpression() throws Exception {
		LiteralExpression literalExpression = new LiteralExpression() {

			@Override
			public <T> T accept(ExpressionVisitor<T> expressionVisitor)
				throws ExpressionVisitException {

				return expressionVisitor.visitLiteralExpression(this);
			}

			@Override
			public String getText() {
				return "duration'PT24H'";
			}

			@Override
			public Type getType() {
				return Type.DURATION;
			}

		};

		Duration duration =
			(Duration)_expressionVisitorImpl.visitLiteralExpression(
				literalExpression);

		Assert.assertEquals("PT24H", duration.toString());
	}

	@Test
	public void testVisitListExpressionOperation() throws Exception {
		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		ListExpression listExpression = new ListExpression() {

			public <T> T accept(ExpressionVisitor<T> expressionVisitor)
				throws ExpressionVisitException {

				List<Object> objects = Arrays.asList("title1", "title2");

				return expressionVisitor.visitListExpressionOperation(
					Operation.IN, (T)entityFieldsMap.get("title"),
					(List<T>)objects);
			}

			@Override
			public Expression getLeftOperationExpression() {
				return null;
			}

			@Override
			public Operation getOperation() {
				return null;
			}

			@Override
			public List<Expression> getRightOperationExpressions() {
				return null;
			}

		};

		JSONObject jsonObject = (JSONObject)listExpression.accept(
			_expressionVisitorImpl);

		Assert.assertEquals(
			JSONUtil.put(
				"operatorName",
				StringUtil.toLowerCase(ListExpression.Operation.IN.toString())
			).put(
				"propertyName", "title"
			).put(
				"value", JSONUtil.putAll("title1", "title2")
			).toString(),
			jsonObject.toString());
	}

	@Test
	public void testVisitMethodExpressionWithContains() {
		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		JSONObject jsonObject =
			(JSONObject)_expressionVisitorImpl.visitMethodExpression(
				Arrays.asList(entityFieldsMap.get("title"), "title1"),
				MethodExpression.Type.CONTAINS);

		Assert.assertEquals(
			JSONUtil.put(
				"operatorName",
				StringUtil.toLowerCase(
					MethodExpression.Type.CONTAINS.toString())
			).put(
				"propertyName", "title"
			).put(
				"value", "title1"
			).toString(),
			jsonObject.toString());
	}

	@Test
	public void testVisitMethodExpressionWithNow() {
		Assert.assertEquals(
			ExpressionVisitorImpl.MethodType.NOW,
			_expressionVisitorImpl.visitMethodExpression(
				Collections.emptyList(), MethodExpression.Type.NOW));
	}

	@Test
	public void testVisitUnaryExpressionOperation() throws Exception {
		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		JSONObject jsonObject =
			_expressionVisitorImpl.visitUnaryExpressionOperation(
				UnaryExpression.Operation.NOT,
				_expressionVisitorImpl.visitBinaryExpressionOperation(
					BinaryExpression.Operation.GE, entityFieldsMap.get("id"),
					"4"));

		Assert.assertEquals(
			JSONUtil.put(
				"operatorName",
				StringUtil.toLowerCase(
					UnaryExpression.Operation.NOT + "-" +
						BinaryExpression.Operation.GE.toString())
			).put(
				"propertyName", "id"
			).put(
				"value", "4"
			).toString(),
			jsonObject.toString());
	}

	private static final EntityModel _entityModel = new EntityModel() {

		@Override
		public Map<String, EntityField> getEntityFieldsMap() {
			EntityField complexEntityField = new ComplexEntityField(
				"complexField",
				Collections.singletonList(
					new StringEntityField(
						"fieldInsideComplexField",
						locale -> "fieldInsideComplexFieldInternal")));
			EntityField integerEntityField = new IntegerEntityField(
				"id", locale -> "id");
			EntityField stringEntityField = new StringEntityField(
				"title", locale -> "title");

			return HashMapBuilder.put(
				complexEntityField.getName(), complexEntityField
			).put(
				integerEntityField.getName(), integerEntityField
			).put(
				stringEntityField.getName(), stringEntityField
			).build();
		}

		@Override
		public String getName() {
			return "SomeEntityName";
		}

	};

	private ExpressionVisitorImpl _expressionVisitorImpl;

}