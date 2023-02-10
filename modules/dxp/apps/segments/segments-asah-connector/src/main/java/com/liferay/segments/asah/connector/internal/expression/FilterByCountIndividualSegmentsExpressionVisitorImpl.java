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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.asah.connector.internal.expression.parser.IndividualSegmentsExpressionBaseVisitor;
import com.liferay.segments.asah.connector.internal.expression.parser.IndividualSegmentsExpressionParser;

import java.util.Objects;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * @author Cristina González
 */
public class FilterByCountIndividualSegmentsExpressionVisitorImpl
	extends IndividualSegmentsExpressionBaseVisitor<Object> {

	@Override
	public FilterByCount visitAndExpression(
		@NotNull IndividualSegmentsExpressionParser.AndExpressionContext
			andExpressionContext) {

		ParseTree leftParseTree = andExpressionContext.getChild(0);
		ParseTree rightParseTree = andExpressionContext.getChild(2);

		Object left = leftParseTree.accept(this);
		Object right = rightParseTree.accept(this);

		if ((right instanceof FilterByCount.Day) &&
			(left instanceof FilterByCount.Event)) {

			return new FilterByCount(
				(FilterByCount.Day)right, (FilterByCount.Event)left);
		}

		throw new UnsupportedOperationException(
			StringBundler.concat(
				"Trying to create a FilterByCount Expression with  ",
				String.valueOf(left.getClass()), " and ",
				String.valueOf(right.getClass())));
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
	public Object visitEqualsExpression(
		@NotNull IndividualSegmentsExpressionParser.EqualsExpressionContext
			equalsExpressionContext) {

		ParseTree leftParseTree = equalsExpressionContext.getChild(0);

		Object value = leftParseTree.accept(this);

		if (Objects.equals("activityKey", value)) {
			ParseTree rightParseTree = equalsExpressionContext.getChild(2);

			String eventString = (String)rightParseTree.accept(this);

			String[] parts = eventString.split(StringPool.POUND);

			if (parts.length != 3) {
				throw new UnsupportedOperationException(
					"invalid event " + eventString);
			}

			return new FilterByCount.Event(parts[1], parts[2]);
		}
		else if (Objects.equals("day", value)) {
			return _getFilterByCountDay(
				leftParseTree, equalsExpressionContext.getChild(1),
				equalsExpressionContext.getChild(2));
		}

		throw new UnsupportedOperationException(
			"Unsupported operation with value " + value);
	}

	@Override
	public FilterByCount.Day visitGreaterThanExpression(
		@NotNull IndividualSegmentsExpressionParser.GreaterThanExpressionContext
			greaterThanExpressionContext) {

		return _getFilterByCountDay(
			greaterThanExpressionContext.getChild(0),
			greaterThanExpressionContext.getChild(1),
			greaterThanExpressionContext.getChild(2));
	}

	@Override
	public FilterByCount.Day visitGreaterThanOrEqualsExpression(
		@NotNull
			IndividualSegmentsExpressionParser.
				GreaterThanOrEqualsExpressionContext
					greaterThanOrEqualsExpressionContext) {

		return _getFilterByCountDay(
			greaterThanOrEqualsExpressionContext.getChild(0),
			greaterThanOrEqualsExpressionContext.getChild(1),
			greaterThanOrEqualsExpressionContext.getChild(2));
	}

	@Override
	public FilterByCount.Day visitLessThanExpression(
		@NotNull IndividualSegmentsExpressionParser.LessThanExpressionContext
			lessThanExpressionContext) {

		return _getFilterByCountDay(
			lessThanExpressionContext.getChild(0),
			lessThanExpressionContext.getChild(1),
			lessThanExpressionContext.getChild(2));
	}

	@Override
	public FilterByCount.Day visitLessThanOrEqualsExpression(
		@NotNull
			IndividualSegmentsExpressionParser.LessThanOrEqualsExpressionContext
				lessThanOrEqualsExpressionContext) {

		return _getFilterByCountDay(
			lessThanOrEqualsExpressionContext.getChild(0),
			lessThanOrEqualsExpressionContext.getChild(1),
			lessThanOrEqualsExpressionContext.getChild(2));
	}

	@Override
	public String visitTerminal(TerminalNode terminalNode) {
		if (Objects.equals(terminalNode.getText(), "<EOF>")) {
			return null;
		}

		return _normalizeStringLiteral(terminalNode.getText());
	}

	@Override
	public FilterByCount visitToLogicalAndExpression(
		@NotNull
			IndividualSegmentsExpressionParser.ToLogicalAndExpressionContext
				toLogicalAndExpressionContext) {

		Object object = visitChildren(toLogicalAndExpressionContext);

		if (object instanceof FilterByCount.Event) {
			return new FilterByCount(null, (FilterByCount.Event)object);
		}

		return (FilterByCount)object;
	}

	public static class FilterByCount {

		public FilterByCount(Day day, Event event) {
			_day = day;
			_event = event;
		}

		public Day getDay() {
			return _day;
		}

		public Event getEvent() {
			return _event;
		}

		public JSONObject toJSONObject() {
			if (_event != null) {
				JSONObject jsonObject = _event.toJSONObject();

				if (_day != null) {
					jsonObject.put("day", _day.toJSONObject());
				}

				return jsonObject;
			}

			return null;
		}

		public static class Day {

			public Day(String operator, String value) {
				_operator = operator;
				_value = value;
			}

			public String getOperator() {
				return _operator;
			}

			public String getValue() {
				return _value;
			}

			public JSONObject toJSONObject() {
				return JSONUtil.put(
					"operatorName", _operator
				).put(
					"value", _value
				);
			}

			private final String _operator;
			private final String _value;

		}

		public static class Event {

			public Event(String event, String assetId) {
				_event = event;
				_assetId = assetId;
			}

			public String getAssetId() {
				return _assetId;
			}

			public String getEvent() {
				return _event;
			}

			public JSONObject toJSONObject() {
				return JSONUtil.put(
					"assetId", _assetId
				).put(
					"propertyName", _event
				);
			}

			private final String _assetId;
			private String _event;

		}

		private final Day _day;
		private Event _event;

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

	private FilterByCount.Day _getFilterByCountDay(
		ParseTree leftParseTree, ParseTree operatorParseTree,
		ParseTree rightParseTree) {

		Object value = leftParseTree.accept(this);

		if (!Objects.equals("day", value)) {
			throw new UnsupportedOperationException(
				"Unsupported operation with value " + value);
		}

		return new FilterByCount.Day(
			(String)operatorParseTree.accept(this),
			(String)rightParseTree.accept(this));
	}

	private String _normalizeStringLiteral(String literal) {
		literal = StringUtil.unquote(literal);

		return StringUtil.replace(
			literal, StringPool.DOUBLE_APOSTROPHE, StringPool.APOSTROPHE);
	}

}