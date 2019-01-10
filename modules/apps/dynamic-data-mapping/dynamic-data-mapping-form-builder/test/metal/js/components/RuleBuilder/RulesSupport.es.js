import RulesSupport from 'source/components/RuleBuilder/RulesSupport.es';

const mockActions = [
	{
		action: 'show',
		target: 'text1'
	},
	{
		action: 'enable',
		target: 'text2'
	}
];

const mockConditions = [
	{
		operands: [
			{
				type: 'show',
				value: 'text1'
			},
			{
				type: 'enable',
				value: 'text2'
			}
		],
		operator: 'isEmpty'
	}
];

describe(
	'RulesSupport',
	() => {
		it(
			'should clear the action target value',
			() => {
				const mockArgument = [...mockActions];

				const actions = RulesSupport.clearTargetValue(mockArgument, 0);

				expect(actions[0].target).toEqual('');
			}
		);

		it(
			'should clear the first operand value',
			() => {
				const mockArgument = [...mockConditions];

				const condition = RulesSupport.clearFirstOperandValue(mockArgument[0]);

				expect(condition.operands[0].type).toEqual('');
				expect(condition.operands[0].value).toEqual('');
			}
		);

		it(
			'should clear the operator value',
			() => {
				const mockArgument = [...mockConditions];

				const condition = RulesSupport.clearOperatorValue(mockArgument[0]);

				expect(condition.operator).toEqual('');
			}
		);

		it(
			'should clear the second operand value',
			() => {
				const mockArgument = [...mockConditions];

				const condition = RulesSupport.clearSecondOperandValue(mockArgument[0]);

				expect(condition.operands[1].type).toEqual('');
				expect(condition.operands[1].value).toEqual('');
			}
		);
	}
);