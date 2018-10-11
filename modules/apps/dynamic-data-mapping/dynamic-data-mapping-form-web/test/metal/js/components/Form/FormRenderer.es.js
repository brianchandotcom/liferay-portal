import dom from 'metal-dom';
import FormRenderer from 'source/components/Form/FormRenderer.es';
import FormSupport from 'source/components/Form/FormSupport.es';
import mockPages from 'mock/mockPages.es';
import mockSuccessPage from 'mock/mockSuccessPage.es';

const spritemap = 'icons.svg';
let component;
let pages = null;
let successPageSettings = null;

describe(
	'FormRenderer',
	() => {
		beforeEach(
			() => {
				pages = JSON.parse(JSON.stringify(mockPages));
				successPageSettings = JSON.parse(JSON.stringify(mockSuccessPage));

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
						pages,
						spritemap,
						successPageSettings
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a layout in wizard mode',
			() => {
				component = new FormRenderer(
					{
						pages,
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a layout in pagination mode',
			() => {
				component = new FormRenderer(
					{
						pages,
						paginationMode: 'pagination',
						spritemap,
						successPageSettings
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
						spritemap,
						successPageSettings
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
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
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
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
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
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
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
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
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
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
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
			'should continue to propagate the fieldEdited event',
			() => {
				component = new FormRenderer(
					{
						dragAndDropDisabled: true,
						editable: true,
						pages,
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
					}
				);

				const spy = jest.spyOn(component, 'emit');
				const {pageRenderer} = component.refs;
				const mockEvent = jest.fn();

				pageRenderer.emit('fieldEdited', mockEvent);

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('fieldEdited', expect.anything());
			}
		);

		it(
			'should continue to propagate the fieldClicked event',
			() => {
				component = new FormRenderer(
					{
						dragAndDropDisabled: true,
						editable: true,
						pages,
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
					}
				);

				const spy = jest.spyOn(component, 'emit');
				const {pageRenderer} = component.refs;
				const mockEvent = jest.fn();

				pageRenderer.emit('fieldClicked', mockEvent);

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('fieldClicked', expect.anything());
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
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
					}
				);

				jest.runAllTimers();

				const pageWizard = component.element.querySelector('.multi-step-item[data-page-id="1"]');

				dom.triggerEvent(pageWizard, 'click', {});

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should change the pagination mode when switch pagination mode is clicked',
			() => {
				const newPages = [...pages, ...pages];

				component = new FormRenderer(
					{
						dragAndDropDisabled: true,
						editable: true,
						pages: newPages,
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
					}
				);
				const spy = jest.spyOn(component, 'emit');

				component._handlePageSettingsClicked(
					{
						data: {
							item: {
								settingsItem: 'switch-pagination-mode'
							}
						}
					}
				);

				jest.runAllTimers();

				expect(spy).toHaveBeenCalledWith('paginationModeUpdated');
				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should back the pagination mode to wizard mode when user clicks in switch page on the settings menu',
			() => {
				const newPages = [...pages, ...pages];

				component = new FormRenderer(
					{
						dragAndDropDisabled: true,
						editable: true,
						pages: newPages,
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
					}
				);
				const spy = jest.spyOn(component, 'emit');

				component._handlePageSettingsClicked(
					{
						data: {
							item: {
								settingsItem: 'switch-pagination-mode'
							}
						}
					}
				);

				jest.runAllTimers();

				expect(spy).toHaveBeenCalled();

				jest.useFakeTimers();

				component._handlePageSettingsClicked(
					{
						data: {
							item: {
								settingsItem: 'switch-pagination-mode'
							}
						}
					}
				);

				jest.runAllTimers();

				expect(spy).toHaveBeenCalledWith('paginationModeUpdated');
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
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
					}
				);

				jest.runAllTimers();

				const pageWizard = component.element.querySelector('.multi-step-item[data-page-id="1"]');

				dom.triggerEvent(pageWizard, 'click', {});

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should change the active by clicking on the pagination when the page mode is in pagination mode',
			() => {
				const newPages = [...pages, ...pages];

				component = new FormRenderer(
					{
						dragAndDropDisabled: true,
						editable: true,
						pages: newPages,
						paginationMode: 'pagination',
						spritemap,
						successPageSettings
					}
				);

				jest.runAllTimers();

				jest.useFakeTimers();

				const paginatorItem = component.element.querySelector('.ddm-pagination .page-item[data-page-id="1"]');

				dom.triggerEvent(paginatorItem, 'click', {});

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should change the active page by clicking on the arrow right icon in the pagination button',
			() => {
				const newPages = [...pages, ...pages];

				component = new FormRenderer(
					{
						activePage: 0,
						dragAndDropDisabled: true,
						editable: true,
						pages: newPages,
						paginationMode: 'pagination',
						spritemap,
						successPageSettings
					}
				);

				jest.runAllTimers();

				const spy = jest.spyOn(component, 'emit');

				component._handlePaginationRightClicked();

				expect(spy).toHaveBeenCalledWith(
					'activePageUpdated',
					1
				);
			}
		);

		it(
			'should change the page from a normal page to a success page by clicking on the arrow right',
			() => {
				const newPages = [...pages];

				component = new FormRenderer(
					{
						activePage: 0,
						dragAndDropDisabled: true,
						editable: true,
						pages: newPages,
						paginationMode: 'pagination',
						spritemap,
						successPageSettings: {
							...successPageSettings,
							enabled: true
						}
					}
				);

				jest.runAllTimers();

				const spy = jest.spyOn(component, 'emit');

				component._handlePaginationRightClicked();

				expect(spy).toHaveBeenCalledWith(
					'activePageUpdated',
					-1
				);
			}
		);

		it(
			'should change the page from a success page to a normal page by clicking on the arrow left',
			() => {
				const newPages = [...pages];

				component = new FormRenderer(
					{
						activePage: 0,
						dragAndDropDisabled: true,
						editable: true,
						pages: newPages,
						paginationMode: 'pagination',
						spritemap,
						successPageSettings: {
							...successPageSettings,
							enabled: true
						}
					}
				);

				component.activePage = -1;

				jest.runAllTimers();

				const spy = jest.spyOn(component, 'emit');

				component._handlePaginationLeftClicked();

				expect(spy).toHaveBeenCalledWith(
					'activePageUpdated',
					0
				);
			}
		);

		it(
			'should change the active by clicking on the arrow left icon in the pagination button',
			() => {
				const newPages = [...pages, ...pages];

				component = new FormRenderer(
					{
						activePage: 1,
						dragAndDropDisabled: true,
						editable: true,
						pages: newPages,
						paginationMode: 'pagination',
						spritemap,
						successPageSettings
					}
				);

				jest.runAllTimers();

				const spy = jest.spyOn(component, 'emit');

				component._handlePaginationLeftClicked();

				expect(spy).toHaveBeenCalledWith(
					'activePageUpdated',
					0
				);
			}
		);

		it(
			'should propagate field edit event',
			() => {
				component = new FormRenderer(
					{
						editable: true,
						pages,
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
					}
				);

				const spy = jest.spyOn(component, 'emit');

				component._handleFieldEdited();

				expect(spy).toHaveBeenCalled();
			}
		);

		it(
			'should render a layout and emit the field move event',
			() => {
				component = new FormRenderer(
					{
						editable: true,
						pages,
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
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
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
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
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
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
						pages: newmockPages,
						paginationMode: 'wizard'
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
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
					}
				);

				const spy = jest.spyOn(component, '_startDrag');

				const newmockPages = FormSupport.removeFields(pages, 0, 1, 0);

				component.setState(
					{
						pages: newmockPages,
						paginationMode: 'wizard'
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
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
					}
				);

				const spy = jest.spyOn(component, '_startDrag');

				const newmockPages = FormSupport.removeFields(pages, 0, 1, 0);

				component.setState(
					{
						pages: newmockPages,
						paginationMode: 'wizard'
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
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
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
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
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
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
					}
				);

				const delegateTarget = component.element.querySelector('.form-builder-page-header-title');

				delegateTarget.value = 'My Page Title';

				dom.triggerEvent(
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
						paginationMode: 'wizard',
						spritemap,
						successPageSettings
					}
				);

				const delegateTarget = component.element.querySelector('.form-builder-page-header-description');

				delegateTarget.value = 'My Page Description';

				dom.triggerEvent(
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
								paginationMode: 'wizard',
								spritemap,
								successPageSettings
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
								paginationMode: 'wizard',
								spritemap,
								successPageSettings
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
					'should add a success page',
					() => {
						component = new FormRenderer(
							{
								editable: true,
								pages,
								paginationMode: 'wizard',
								spritemap,
								successPageSettings
							}
						);

						component._handlePageSettingsClicked(
							{
								data: {
									item: {
										settingsItem: 'add-success-page'
									}
								}
							}
						);

						jest.runAllTimers();

						expect(component).toMatchSnapshot();
					}
				);

				it(
					'should delete the success page',
					() => {
						component = new FormRenderer(
							{
								editable: true,
								pages,
								paginationMode: 'wizard',
								spritemap,
								successPageSettings
							}
						);

						component._handlePageSettingsClicked(
							{
								data: {
									item: {
										settingsItem: 'add-success-page'
									}
								}
							}
						);

						jest.runAllTimers();

						jest.useFakeTimers();

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

				it(
					'should propagate success page changed when some success page field is changed',
					() => {
						component = new FormRenderer(
							{
								dragAndDropDisabled: true,
								editable: true,
								pages,
								paginationMode: 'wizard',
								spritemap,
								successPageSettings
							}
						);

						component.activePage = -1;

						jest.runAllTimers();

						const spy = jest.spyOn(component, 'emit');
						const {successPage} = component.refs;
						const mockEvent = jest.fn();

						successPage.emit('successPageChanged', mockEvent);

						expect(spy).toHaveBeenCalled();
						expect(spy).toHaveBeenCalledWith('successPageChanged', expect.anything());
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
								paginationMode: 'wizard',
								spritemap,
								successPageSettings
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
								},
								{
									'label': Liferay.Language.get('add-success-page'),
									'settingsItem': 'add-success-page'
								},
								{
									'label': 'switch-pagination-to-bottom',
									'settingsItem': 'switch-pagination-mode'
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
								paginationMode: 'wizard',
								spritemap,
								successPageSettings
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
								},
								{
									'label': Liferay.Language.get('add-success-page'),
									'settingsItem': 'add-success-page'
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
								paginationMode: 'wizard',
								spritemap,
								successPageSettings
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