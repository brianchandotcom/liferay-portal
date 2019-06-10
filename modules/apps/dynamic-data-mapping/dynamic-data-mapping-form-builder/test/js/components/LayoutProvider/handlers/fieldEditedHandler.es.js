import * as fieldEditedHandler from 'source/components/LayoutProvider/handlers/fieldEditedHandler.es';
import * as focusedFieldUtil from 'source/components/LayoutProvider/util/focusedField.es';
import mockPages from 'mock/mockPages.es';

describe('LayoutProvider/handlers/fieldEditedHandler', () => {
	describe('handleFieldEdited(state, event)', () => {
		it('should call updateFocusedField()', () => {
			const event = {
				propertyName: 'dataType',
				propertyValue: 'string'
			};
			const state = {
				focusedField: {},
				pages: mockPages,
				rules: []
			};

			const updateFocusedFieldSpy = jest.spyOn(
				focusedFieldUtil,
				'updateFocusedField'
			);

			updateFocusedFieldSpy.mockImplementation(() => ({}));

			fieldEditedHandler.handleFieldEdited(state, event);

			expect(updateFocusedFieldSpy).toHaveBeenCalled();

			updateFocusedFieldSpy.mockRestore();
		});

		it('should not call updateFocusedField() when changing name to an empty string', () => {
			const event = {
				propertyName: 'name',
				propertyValue: ''
			};
			const state = {
				focusedField: {},
				pages: mockPages,
				rules: []
			};

			const updateFocusedFieldSpy = jest.spyOn(
				focusedFieldUtil,
				'updateFocusedField'
			);

			updateFocusedFieldSpy.mockImplementation(() => ({}));

			fieldEditedHandler.handleFieldEdited(state, event);

			expect(updateFocusedFieldSpy).not.toHaveBeenCalled();

			updateFocusedFieldSpy.mockRestore();
		});
	});
});
