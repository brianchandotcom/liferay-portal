import {dom as MetalTestUtil} from 'metal-dom';
import FormRenderer from '../FormRenderer.es';
import FormSupport from '../FormSupport.es';
import mockPages from './__mock__/mockPages.es';

let component;
let pages = null;
const spritemap = 'icons.svg';

describe(
	'FormRenderer',
	() => {
		beforeEach(
			() => {
				pages = JSON.parse(JSON.stringify(mockPages));

				jest.useFakeTimers();
			}
		);

		afterEach(
			() => {
				if (component) {
					component.dispose();
				}

				pages = null;
			}
		);

		it(
			'should render default markup',
			() => {
				component = new FormRenderer(
					{
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a layout with pages',
			() => {
				component = new FormRenderer(
					{
						pages,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a layout with pages in mode of list',
			() => {
				component = new FormRenderer(
					{
						modeRenderer: 'list',
						pages,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a layout in editable mode',
			() => {
				component = new FormRenderer(
					{
						editable: true,
						pages,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a layout with disabled drag and drop',
			() => {
				component = new FormRenderer(
					{
						dragAndDropDisabled: true,
						editable: true,
						pages,
						spritemap
					}
				);

				expect(component._dragAndDrop).toBeUndefined();
			}
		);

		it(
			'should receive an update page event and propage it',
			() => {
				component = new FormRenderer(
					{
						dragAndDropDisabled: true,
						editable: true,
						pages,
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'emit');

				jest.runAllTimers();

				component._handleUpdatePage(pages[0], 0);

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('pagesUpdated', expect.any(Object));

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should receive a delete button event and propage it',
			() => {
				component = new FormRenderer(
					{
						dragAndDropDisabled: true,
						editable: true,
						pages,
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'emit');

				jest.runAllTimers();

				component._handleDeleteButtonClicked(pages);

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('fieldDeleted', expect.any(Object));

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should receive a duplicate button event and propage it',
			() => {
				component = new FormRenderer(
					{
						dragAndDropDisabled: true,
						editable: true,
						pages,
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'emit');

				jest.runAllTimers();

				component._handleDuplicateButtonClicked(pages);

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('fieldDuplicated', expect.any(Object));

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should change the active page',
			() => {
				const newPages = [...pages, ...pages];

				component = new FormRenderer(
					{
						dragAndDropDisabled: true,
						editable: true,
						pages: newPages,
						spritemap
					}
				);

				jest.runAllTimers();

				const pageWizard = component.element.querySelector('.multi-step-item[data-page-id="1"]');

				MetalTestUtil.triggerEvent(pageWizard, 'click', {});

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should change the active page for a empty page',
			() => {
				const newPages = [...pages, ...pages];

				newPages[1].rows = [{
					columns: [{
						fields: [],
						size: 12
					}]
				}];

				component = new FormRenderer(
					{
						dragAndDropDisabled: true,
						editable: true,
						pages: newPages,
						spritemap
					}
				);

				jest.runAllTimers();

				const pageWizard = component.element.querySelector('.multi-step-item[data-page-id="1"]');

				MetalTestUtil.triggerEvent(pageWizard, 'click', {});

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a layout and emit the field move event',
			() => {
				component = new FormRenderer(
					{
						editable: true,
						pages,
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'emit');

				const source = component.element.querySelectorAll('.ddm-drag').item(2);
				const target = component.element.querySelector('.ddm-target');

				const mockEvent = {
					source,
					target
				};

				component._handleDragAndDropEnd(mockEvent);

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith(
					'fieldMoved',
					{
						data: mockEvent,
						source: {
							columnIndex: 1,
							pageIndex: 0,
							rowIndex: 1
						},
						target: {
							columnIndex: 0,
							pageIndex: 0,
							rowIndex: 0
						}
					}
				);
			}
		);

		it(
			'should render a layout and ignore the field move when there is no target',
			() => {
				component = new FormRenderer(
					{
						editable: true,
						pages,
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'emit');

				const source = component.element.querySelectorAll('.ddm-drag').item(2);

				const mockEvent = {
					source,
					target: null
				};

				component._handleDragAndDropEnd(mockEvent);

				expect(spy).not.toHaveBeenCalled();
				expect(spy).not.toHaveBeenCalledWith('fieldMoved', expect.any(Object));
			}
		);

		it(
			'should render a layout and reset drag and drop to every change of API pages when editable is true',
			() => {
				component = new FormRenderer(
					{
						editable: true,
						pages,
						spritemap
					}
				);

				const spy = jest.spyOn(component, '_startDrag');
				const spyDragAndDrop = jest.spyOn(
					component._dragAndDrop,
					'disposeInternal'
				);

				const newmockPages = FormSupport.removeFields(pages, 0, 1, 0);

				component.setState(
					{
						pages: newmockPages
					}
				);

				jest.runAllTimers();

				expect(spy).toHaveBeenCalled();
				expect(spyDragAndDrop).toHaveBeenCalled();
			}
		);

		it(
			'should render a layout and if it is not editable should not reset the drag-and-drop feature for all API page changes',
			() => {
				component = new FormRenderer(
					{
						editable: false,
						pages,
						spritemap
					}
				);

				const spy = jest.spyOn(component, '_startDrag');

				const newmockPages = FormSupport.removeFields(pages, 0, 1, 0);

				component.setState(
					{
						pages: newmockPages
					}
				);

				jest.runAllTimers();

				expect(spy).not.toHaveBeenCalled();
			}
		);

		it(
			'should render a layout and if it is disabled should not reset the drag-and-drop feature for all API page changes',
			() => {
				component = new FormRenderer(
					{
						dragAndDropDisabled: true,
						editable: true,
						pages,
						spritemap
					}
				);

				const spy = jest.spyOn(component, '_startDrag');

				const newmockPages = FormSupport.removeFields(pages, 0, 1, 0);

				component.setState(
					{
						pages: newmockPages
					}
				);

				jest.runAllTimers();

				expect(spy).not.toHaveBeenCalled();
			}
		);

		it(
			'should render a layout with an empty field only in editable mode',
			() => {
				const columnIndex = 2;
				const fields = [
					{
						spritemap: 'icons.svg',
						type: 'option_multiple'
					}
				];
				const pageIndex = 0;
				const rowIndex = 1;

				const newmockPages = FormSupport.setColumnFields(
					pages,
					pageIndex,
					rowIndex,
					columnIndex,
					fields
				);

				component = new FormRenderer(
					{
						editable: true,
						pages: newmockPages,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should not render the layout with the field empty in non-editable mode',
			() => {
				const columnIndex = 2;
				const fields = [
					{
						spritemap: 'icons.svg',
						type: 'option_multiple'
					}
				];
				const pageIndex = 0;
				const rowIndex = 1;

				const newmockPages = FormSupport.setColumnFields(
					pages,
					pageIndex,
					rowIndex,
					columnIndex,
					fields
				);

				component = new FormRenderer(
					{
						editable: false,
						pages: newmockPages,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should change the form page title',
			() => {
				component = new FormRenderer(
					{
						editable: true,
						pages,
						spritemap
					}
				);

				const delegateTarget = component.element.querySelector('.form-builder-page-header-title');

				delegateTarget.value = 'My Page Title';

				MetalTestUtil.triggerEvent(
					delegateTarget,
					'change',
					{
						delegateTarget
					}
				);

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should change the form page description',
			() => {
				component = new FormRenderer(
					{
						editable: true,
						pages,
						spritemap
					}
				);

				const delegateTarget = component.element.querySelector('.form-builder-page-header-description');

				delegateTarget.value = 'My Page Description';

				MetalTestUtil.triggerEvent(
					delegateTarget,
					'change',
					{
						delegateTarget
					}
				);

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		describe(
			'PagesSettings',
			() => {
				it(
					'should add a new page on the layout render',
					() => {
						component = new FormRenderer(
							{
								editable: true,
								pages,
								spritemap
							}
						);

						component._handlePageSettingsClicked(
							{
								data: {
									item: {
										settingsItem: 'add-page'
									}
								}
							}
						);

						jest.runAllTimers();

						expect(component).toMatchSnapshot();
					}
				);

				it(
					'should reset the current page on layout render',
					() => {
						component = new FormRenderer(
							{
								editable: true,
								pages,
								spritemap
							}
						);

						component._handlePageSettingsClicked(
							{
								data: {
									item: {
										settingsItem: 'reset-page'
									}
								}
							}
						);

						jest.runAllTimers();

						expect(component).toMatchSnapshot();
					}
				);

				it(
					'should show delete-field option, when the form builder has more than one page',
					() => {
						const pagesTemp = [...pages, ...pages];

						component = new FormRenderer(
							{
								editable: true,
								pages: pagesTemp,
								spritemap
							}
						);

						jest.runAllTimers();

						expect(
							component.prepareStateForRender(component).pageSettingsItems
						).toEqual(
							[
								{
									'label': Liferay.Language.get('add-new-page'),
									'settingsItem': 'add-page'
								},
								{
									'label': Liferay.Language.get('delete-current-page'),
									'settingsItem': 'delete-page'
								}
							]
						);
						expect(component).toMatchSnapshot();
					}
				);

				it(
					'should show reset-field option, when the form builder has only one page',
					() => {
						component = new FormRenderer(
							{
								editable: true,
								pages,
								spritemap
							}
						);

						jest.runAllTimers();

						expect(
							component.prepareStateForRender(component).pageSettingsItems
						).toEqual(
							[
								{
									'label': Liferay.Language.get('add-new-page'),
									'settingsItem': 'add-page'
								},
								{
									'label': Liferay.Language.get('reset-page'),
									'settingsItem': 'reset-page'
								}
							]
						);
						expect(component).toMatchSnapshot();
					}
				);

				it(
					'should delete the current page on layout render',
					() => {
						const newPages = [...pages, ...pages];

						component = new FormRenderer(
							{
								editable: true,
								pages: newPages,
								spritemap
							}
						);

						component._handlePageSettingsClicked(
							{
								data: {
									item: {
										settingsItem: 'delete-page'
									}
								}
							}
						);

						jest.runAllTimers();

						expect(component).toMatchSnapshot();
					}
				);
			}
		);
	}
);