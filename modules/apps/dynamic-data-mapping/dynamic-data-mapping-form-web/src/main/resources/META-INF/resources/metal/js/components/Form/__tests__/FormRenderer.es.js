import {dom as MetalTestUtil} from 'metal-dom';

import Context from './__mock__/mockContext.es';
import FormRenderer from '../FormRenderer.es';
import FormSupport from '../FormSupport.es';

let component;
let context = null;
const spritemap = 'icons.svg';

describe(
	'FormRenderer',
	() => {
		beforeEach(
			() => {
				context = JSON.parse(JSON.stringify(Context));

				jest.useFakeTimers();
			}
		);

		afterEach(
			() => {
				if (component) {
					component.dispose();
				}

				context = null;
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
						pages: context,
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
						pages: context,
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
						pages: context,
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
						pages: context,
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
						pages: context,
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'emit');

				jest.runAllTimers();

				component._handleUpdatePage(context[0], 0);

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
						pages: context,
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'emit');

				jest.runAllTimers();

				component._handleDeleteButtonClicked(context);

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('deleteButtonClicked', expect.any(Object));

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
						pages: context,
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'emit');

				jest.runAllTimers();

				component._handleDuplicateButtonClicked(context);

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('duplicateButtonClicked', expect.any(Object));

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should change the active page',
			() => {
				const pages = [...context];

				pages.push(pages[0]);

				component = new FormRenderer(
					{
						dragAndDropDisabled: true,
						editable: true,
						pages,
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
				const pages = [...context];

				pages.push(pages[0]);
				pages[1].rows = [{
					columns: [{
						fields: [],
						size: 12
					}]
				}];

				component = new FormRenderer(
					{
						dragAndDropDisabled: true,
						editable: true,
						pages,
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
						pages: context,
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
							columnIndex: false,
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
						pages: context,
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
						pages: context,
						spritemap
					}
				);

				const spy = jest.spyOn(component, '_startDrag');
				const spyDragAndDrop = jest.spyOn(
					component._dragAndDrop,
					'disposeInternal'
				);

				const newContext = FormSupport.removeFields(context, 0, 1, 0);

				component.setState(
					{
						pages: newContext
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
						pages: context,
						spritemap
					}
				);

				const spy = jest.spyOn(component, '_startDrag');

				const newContext = FormSupport.removeFields(context, 0, 1, 0);

				component.setState(
					{
						pages: newContext
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
						pages: context,
						spritemap
					}
				);

				const spy = jest.spyOn(component, '_startDrag');

				const newContext = FormSupport.removeFields(context, 0, 1, 0);

				component.setState(
					{
						pages: newContext
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

				const newContext = FormSupport.setColumnFields(
					context,
					pageIndex,
					rowIndex,
					columnIndex,
					fields
				);

				component = new FormRenderer(
					{
						editable: true,
						pages: newContext,
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

				const newContext = FormSupport.setColumnFields(
					context,
					pageIndex,
					rowIndex,
					columnIndex,
					fields
				);

				component = new FormRenderer(
					{
						editable: false,
						pages: newContext,
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
						pages: context,
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
						pages: context,
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
								pages: context,
								spritemap
							}
						);

						component._handleSettingsPageClicked(
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
								pages: context,
								spritemap
							}
						);

						component._handleSettingsPageClicked(
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
					'should delete the current page on layout render',
					() => {
						const pages = [...context];

						pages.push(pages[0]);

						component = new FormRenderer(
							{
								editable: true,
								pages,
								spritemap
							}
						);

						component._handleSettingsPageClicked(
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
			}
		);
	}
);