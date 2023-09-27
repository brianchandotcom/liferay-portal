/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.internal.converter;

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.expression.constants.DDMExpressionConstants;
import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.visitor.ActionExpressionVisitor;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.visitor.ConditionExpressionVisitor;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.spi.converter.SPIDDMFormRuleConverter;
import com.liferay.dynamic.data.mapping.spi.converter.model.SPIDDMFormRule;
import com.liferay.dynamic.data.mapping.spi.converter.model.SPIDDMFormRuleAction;
import com.liferay.dynamic.data.mapping.spi.converter.model.SPIDDMFormRuleCondition;
import com.liferay.dynamic.data.mapping.spi.converter.serializer.SPIDDMFormRuleSerializerContext;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 * @author Marcellus Tavares
 */
@Component(service = SPIDDMFormRuleConverter.class)
public class DDMFormRuleConverterImpl implements SPIDDMFormRuleConverter {

	@Override
	public List<SPIDDMFormRule> convert(List<DDMFormRule> ddmFormRules) {
		return TransformUtil.transform(ddmFormRules, this::_convertRule);
	}

	@Override
	public List<DDMFormRule> convert(
		List<SPIDDMFormRule> spiDDMFormRules,
		SPIDDMFormRuleSerializerContext spiDDMFormRuleSerializerContext) {

		return TransformUtil.transform(
			spiDDMFormRules,
			formRule -> _convertRule(
				formRule, spiDDMFormRuleSerializerContext));
	}

	protected void setSPIDDMFormRuleActions(
		SPIDDMFormRule spiDDMFormRule, List<String> actions) {

		spiDDMFormRule.setSPIDDMFormRuleActions(
			TransformUtil.transform(actions, this::_convertAction));
	}

	protected void setSPIDDMFormRuleConditions(
		SPIDDMFormRule spiDDMFormRule, String conditionExpressionString) {

		Expression conditionExpression = _createExpression(
			conditionExpressionString);

		ConditionExpressionVisitor conditionExpressionVisitor =
			new ConditionExpressionVisitor();

		conditionExpression.accept(conditionExpressionVisitor);

		spiDDMFormRule.setSPIDDMFormRuleConditions(
			conditionExpressionVisitor.getSPIDDMFormRuleConditions());
		spiDDMFormRule.setLogicalOperator(
			conditionExpressionVisitor.getLogicalOperator());
	}

	@Reference
	protected DDMExpressionFactory ddmExpressionFactory;

	private SPIDDMFormRuleAction _convertAction(String actionExpressionString) {
		Expression actionExpression = _createExpression(actionExpressionString);

		return (SPIDDMFormRuleAction)actionExpression.accept(
			new ActionExpressionVisitor());
	}

	private String _convertCondition(
		SPIDDMFormRuleCondition spiDDMFormRuleCondition) {

		String operator = spiDDMFormRuleCondition.getOperator();

		List<SPIDDMFormRuleCondition.Operand> operands =
			spiDDMFormRuleCondition.getOperands();

		if (_operators.containsKey(operator)) {
			if (operands.size() < 2) {
				return StringPool.BLANK;
			}

			return String.format(
				_COMPARISON_EXPRESSION_FORMAT, _convertOperand(operands.get(0)),
				_operators.get(operator), _convertOperand(operands.get(1)));
		}

		String functionName = _operatorFunctionNames.getOrDefault(
			operator, operator);

		String condition = _createCondition(functionName, operands);

		if (operator.startsWith("not")) {
			return String.format(_NOT_EXPRESSION_FORMAT, condition);
		}

		return condition;
	}

	private String _convertConditions(
		String logicalOperator,
		List<SPIDDMFormRuleCondition> spiDDMFormRuleConditions) {

		if (spiDDMFormRuleConditions.size() == 1) {
			return _convertCondition(spiDDMFormRuleConditions.get(0));
		}

		StringBundler sb = new StringBundler(
			spiDDMFormRuleConditions.size() * 4);

		for (SPIDDMFormRuleCondition spiDDMFormRuleCondition :
				spiDDMFormRuleConditions) {

			sb.append(_convertCondition(spiDDMFormRuleCondition));
			sb.append(StringPool.SPACE);
			sb.append(logicalOperator);
			sb.append(StringPool.SPACE);
		}

		sb.setIndex(sb.index() - 3);

		return sb.toString();
	}

	private String _convertOperand(SPIDDMFormRuleCondition.Operand operand) {
		if (Objects.equals(operand.getType(), "field")) {
			return String.format(
				_FUNCTION_CALL_UNARY_EXPRESSION_FORMAT, "getValue",
				StringUtil.quote(operand.getValue()));
		}
		else if (Objects.equals(operand.getType(), "json")) {
			return String.format(
				_FUNCTION_CALL_UNARY_EXPRESSION_FORMAT, "getJSONValue",
				StringUtil.quote(operand.getValue()));
		}

		String value = operand.getValue();

		if (_isNumericConstant(operand.getType())) {
			return value;
		}

		if (Objects.equals(operand.getType(), "string")) {
			return StringUtil.quote(value);
		}

		String operandType = operand.getType();

		String string = StringUtil.merge(
			TransformUtil.transformToList(
				StringUtil.split(value),
				curVal -> StringUtil.quote(StringUtil.trim(curVal))),
			StringPool.COMMA_AND_SPACE);

		if (!operandType.equals("list")) {
			return string;
		}

		return StringBundler.concat(
			StringPool.OPEN_BRACKET, string, StringPool.CLOSE_BRACKET);
	}

	private String _convertOperands(
		List<SPIDDMFormRuleCondition.Operand> operands) {

		StringBundler sb = new StringBundler(operands.size());

		boolean hasNestedFunction = _hasNestedFunction(operands);

		for (int i = 0; i < operands.size(); i++) {
			SPIDDMFormRuleCondition.Operand operand = operands.get(i);

			if (hasNestedFunction) {
				sb.append(operand.getValue());
			}
			else {
				if ((i > 0) && Objects.equals(operand.getType(), "option")) {
					SPIDDMFormRuleCondition.Operand previousOperand =
						operands.get(i - 1);

					sb.append(
						String.format(
							_FUNCTION_CALL_BINARY_EXPRESSION_FORMAT,
							"getOptionLabel",
							StringUtil.quote(previousOperand.getValue()),
							StringUtil.quote(operand.getValue())));
				}
				else {
					sb.append(_convertOperand(operand));
				}
			}

			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private SPIDDMFormRule _convertRule(DDMFormRule ddmFormRule) {
		SPIDDMFormRule spiDDMFormRule = new SPIDDMFormRule();

		spiDDMFormRule.setName(ddmFormRule.getName());

		setSPIDDMFormRuleConditions(spiDDMFormRule, ddmFormRule.getCondition());
		setSPIDDMFormRuleActions(spiDDMFormRule, ddmFormRule.getActions());

		return spiDDMFormRule;
	}

	private DDMFormRule _convertRule(
		SPIDDMFormRule spiDDMFormRule,
		SPIDDMFormRuleSerializerContext spiDDMFormRuleSerializerContext) {

		return new DDMFormRule(
			TransformUtil.transform(
				spiDDMFormRule.getSPIDDMFormRuleActions(),
				spiDDMFormRuleAction -> spiDDMFormRuleAction.serialize(
					spiDDMFormRuleSerializerContext)),
			_convertConditions(
				spiDDMFormRule.getLogicalOperator(),
				spiDDMFormRule.getSPIDDMFormRuleConditions()),
			spiDDMFormRule.getName());
	}

	private String _createCondition(
		String functionName, List<SPIDDMFormRuleCondition.Operand> operands) {

		if (Objects.equals(functionName, "belongsTo")) {
			operands.removeIf(
				operand -> StringUtil.equals(operand.getType(), "user"));
		}

		return String.format(
			_FUNCTION_CALL_UNARY_EXPRESSION_FORMAT, functionName,
			_convertOperands(operands));
	}

	private Expression _createExpression(String expressionString) {
		try {
			CreateExpressionRequest createExpressionRequest =
				CreateExpressionRequest.Builder.newBuilder(
					expressionString
				).build();

			DDMExpression<Boolean> ddmExpression =
				ddmExpressionFactory.createExpression(createExpressionRequest);

			return ddmExpression.getModel();
		}
		catch (DDMExpressionException ddmExpressionException) {
			throw new IllegalStateException(
				String.format(
					"Unable to parse expression \"%s\"", expressionString),
				ddmExpressionException);
		}
	}

	private boolean _hasNestedFunction(
		List<SPIDDMFormRuleCondition.Operand> operands) {

		for (SPIDDMFormRuleCondition.Operand operand : operands) {
			if (_isNestedFunction(operand.getValue())) {
				return true;
			}
		}

		return false;
	}

	private boolean _isNestedFunction(String operandValue) {
		return operandValue.matches(
			DDMExpressionConstants.NESTED_FUNCTION_REGEX);
	}

	private boolean _isNumericConstant(String operandType) {
		if (operandType.equals("integer") || operandType.equals("double")) {
			return true;
		}

		return false;
	}

	private static final String _COMPARISON_EXPRESSION_FORMAT = "%s %s %s";

	private static final String _FUNCTION_CALL_BINARY_EXPRESSION_FORMAT =
		"%s(%s, %s)";

	private static final String _FUNCTION_CALL_UNARY_EXPRESSION_FORMAT =
		"%s(%s)";

	private static final String _NOT_EXPRESSION_FORMAT = "not(%s)";

	private static final Map<String, String> _operatorFunctionNames =
		HashMapBuilder.put(
			"belongs-to", "belongsTo"
		).put(
			"contains", "contains"
		).put(
			"equals-to", "equals"
		).put(
			"is-empty", "isEmpty"
		).put(
			"not-contains", "contains"
		).put(
			"not-equals-to", "equals"
		).put(
			"not-is-empty", "isEmpty"
		).build();
	private static final Map<String, String> _operators = HashMapBuilder.put(
		"greater-than", ">"
	).put(
		"greater-than-equals", ">="
	).put(
		"less-than", "<"
	).put(
		"less-than-equals", "<="
	).build();

}