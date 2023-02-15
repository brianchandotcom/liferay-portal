/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.connector.internal.expression;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.asah.connector.internal.expression.parser.IndividualSegmentsExpressionBaseVisitor;
import com.liferay.segments.asah.connector.internal.expression.parser.IndividualSegmentsExpressionLexer;
import com.liferay.segments.asah.connector.internal.expression.parser.IndividualSegmentsExpressionParser;

import java.util.Objects;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * @author Cristina González
 */
public class JSONObjectIndividualSegmentsExpressionVisitorImpl
	extends IndividualSegmentsExpressionBaseVisitor<Object> {

	@Override
	public JSONObject visitAndExpression(
		@NotNull IndividualSegmentsExpressionParser.AndExpressionContext
			andExpressionContext) {

		ParseTree parseTree1 = andExpressionContext.getChild(0);
		ParseTree parseTree2 = andExpressionContext.getChild(2);

		return _getConjunctionJSONObject(
			"and", (JSONObject)parseTree1.accept(this),
			(JSONObject)parseTree2.accept(this));
	}

	@Override
	public Object visitBooleanParenthesis(
		@NotNull IndividualSegmentsExpressionParser.BooleanParenthesisContext
			booleanParenthesisContext) {

		ParseTree parseTree = booleanParenthesisContext.getChild(1);

		return parseTree.accept(this);
	}

	@Override
	public Object visitChildren(@NotNull RuleNode node) {
		Object result = defaultResult();

		for (int i = 0; i < node.getChildCount(); i++) {
			if (!shouldVisitNextChild(node, result)) {
				break;
			}

			ParseTree parseTree = node.getChild(i);

			Object object = parseTree.accept(this);

			result = aggregateResult(result, object);
		}

		return result;
	}

	@Override
	public JSONObject visitOrExpression(
		@NotNull IndividualSegmentsExpressionParser.OrExpressionContext
			orExpressionContext) {

		ParseTree parseTree1 = orExpressionContext.getChild(0);
		ParseTree parseTree2 = orExpressionContext.getChild(2);

		return _getConjunctionJSONObject(
			"or", (JSONObject)parseTree1.accept(this),
			(JSONObject)parseTree2.accept(this));
	}

	@Override
	public String visitStringLiteral(
		@NotNull IndividualSegmentsExpressionParser.StringLiteralContext
			stringLiteralContext) {

		return _normalizeStringLiteral(stringLiteralContext.getText());
	}

	@Override
	public String visitTerminal(TerminalNode terminalNode) {
		if (Objects.equals(terminalNode.getText(), "<EOF>")) {
			return null;
		}

		return _normalizeStringLiteral(terminalNode.getText());
	}

	@Override
	public JSONObject visitToFilterByCountExpression(
		@NotNull
			IndividualSegmentsExpressionParser.ToFilterByCountExpressionContext
				toFilterByCountExpressionContext) {

		IndividualSegmentsExpressionParser.FilterByCountExpressionContext
			filterByCountExpressionContext =
				(IndividualSegmentsExpressionParser.
					FilterByCountExpressionContext)
						toFilterByCountExpressionContext.getChild(0);

		Token token = filterByCountExpressionContext.filter;

		String filterString = token.getText();

		filterString = filterString.substring(2, filterString.length() - 2);

		filterString = filterString.replaceAll("''", "'");

		IndividualSegmentsExpressionParser individualSegmentsExpressionParser =
			new IndividualSegmentsExpressionParser(
				new CommonTokenStream(
					new IndividualSegmentsExpressionLexer(
						new ANTLRInputStream(filterString))));

		IndividualSegmentsExpressionParser.ExpressionContext expressionContext =
			individualSegmentsExpressionParser.expression();

		Token tokenValue = filterByCountExpressionContext.value;

		FilterByCountIndividualSegmentsExpressionVisitorImpl.FilterByCount
			filterByCount =
				(FilterByCountIndividualSegmentsExpressionVisitorImpl.
					FilterByCount)expressionContext.accept(
						new FilterByCountIndividualSegmentsExpressionVisitorImpl());

		JSONObject jsonObject = filterByCount.toJSONObject();

		return jsonObject.put(
			"operatorName",
			_normalizeOperator(filterByCountExpressionContext.operator)
		).put(
			"value", _normalizeStringLiteral(tokenValue.getText())
		);
	}

	@Override
	protected Object aggregateResult(Object query, Object object) {
		if (query == null) {
			return object;
		}
		else if (object == null) {
			return query;
		}

		return object;
	}

	private JSONObject _getConjunctionJSONObject(
		String operation, JSONObject leftJSONObject,
		JSONObject rightJSONObject) {

		String conjunctionName = leftJSONObject.getString("conjunctionName");

		_groupCount++;

		if (Validator.isNotNull(conjunctionName) &&
			Objects.equals(
				conjunctionName.toLowerCase(LocaleUtil.ROOT),
				operation.toLowerCase(LocaleUtil.ROOT))) {

			return JSONUtil.put(
				"conjunctionName", operation
			).put(
				"groupId", "group_" + _groupCount
			).put(
				"items",
				leftJSONObject.getJSONArray(
					"items"
				).put(
					rightJSONObject
				)
			);
		}

		return JSONUtil.put(
			"conjunctionName", StringUtil.lowerCase(String.valueOf(operation))
		).put(
			"groupId", "group_" + _groupCount
		).put(
			"items", JSONUtil.putAll(leftJSONObject, rightJSONObject)
		);
	}

	private String _normalizeOperator(Token token) {
		return _normalizeStringLiteral(StringUtil.toLowerCase(token.getText()));
	}

	private String _normalizeStringLiteral(String literal) {
		literal = StringUtil.unquote(literal);

		return StringUtil.replace(
			literal, StringPool.DOUBLE_APOSTROPHE, StringPool.APOSTROPHE);
	}

	private int _groupCount;

}