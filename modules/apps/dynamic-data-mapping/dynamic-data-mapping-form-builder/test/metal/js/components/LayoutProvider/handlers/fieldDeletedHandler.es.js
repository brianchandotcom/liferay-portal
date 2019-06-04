import * as fieldDeletedHandler from 'source/components/LayoutProvider/handlers/fieldDeletedHandler.es';
import * as FormSupport from 'source/components/Form/FormSupport.es';
import mockPages from 'mock/mockPages.es';
import RulesSupport from 'source/components/RuleBuilder/RulesSupport.es';

describe('LayoutProvider/handlers/fieldDeletedHandler', () => {
	describe('handleFieldDeleted(state, event)', () => {
		it('should call removeRow() when row is left with no fields after delete operation', () => {
			const event = {
				columnIndex: 0,
				pageIndex: 0,
				rowIndex: 0
			};
			const state = {
				pages: mockPages,
				rules: []
			};

			const removeRowSpy = jest.spyOn(FormSupport, 'removeRow');

			removeRowSpy.mockImplementation(() => []);

			fieldDeletedHandler.handleFieldDeleted(state, event);

			expect(removeRowSpy).toHaveBeenCalled();

			removeRowSpy.mockRestore();
		});

		it('should call clearAllConditionFieldValues() when deleting a field used as the first operand of a condition', () => {
			const event = {
				columnIndex: 0,
				pageIndex: 0,
				rowIndex: 0
			};
			const state = {
				pages: mockPages,
				rules: [
					{
						actions: [],
						conditions: [
							{
								operands: [
									{
										value: 'radio'
									}
								]
							}
						]
					}
				]
			};

			const clearAllConditionFieldValuesSpy = jest.spyOn(
				RulesSupport,
				'clearAllConditionFieldValues'
			);

			clearAllConditionFieldValuesSpy.mockImplementation(() => []);

			fieldDeletedHandler.handleFieldDeleted(state, event);

			expect(clearAllConditionFieldValuesSpy).toHaveBeenCalled();

			clearAllConditionFieldValuesSpy.mockRestore();
		});
	});
});
