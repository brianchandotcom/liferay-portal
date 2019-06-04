import handleFieldSetAdded from 'source/components/LayoutProvider/handlers/fieldSetAddedHandler.es';
import mockPages from 'mock/mockPages.es';

describe('LayoutProvider/handlers/fieldSetAddedHandler', () => {
	describe('handleFieldSetAdded(props, state, event)', () => {
		it('should insert the fieldset page to the current page', () => {
			const event = {
				fieldSetPage: [
					{
						rows: [
							{
								columns: [
									{
										fields: [
											{
												fieldName: 'field1',
												settingsContext: {
													pages: []
												}
											}
										]
									}
								]
							}
						]
					}
				],
				target: {
					pageIndex: 0,
					rowIndex: 1
				}
			};
			const state = {
				pages: mockPages
			};

			const newState = handleFieldSetAdded({}, state, event);

			expect(
				newState.pages[0].rows[1].columns[0].fields[0].fieldName
			).toEqual('field1');
		});
	});
});
