import './__fixtures__/Fields.es';
import Context from './__mock__/mockContext.es';
import LayoutRenderer from '../LayoutRenderer.es';
import LayoutSupport from '../LayoutSupport.es';

let component;
let context = null;
const spritemap = 'icons.svg';

describe(
	'LayoutRenderer',
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
				component = new LayoutRenderer(
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
				component = new LayoutRenderer(
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
				component = new LayoutRenderer(
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
				component = new LayoutRenderer(
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
				component = new LayoutRenderer(
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
			'should render a layout and emit an event on button delete clicked',
			() => {
				component = new LayoutRenderer(
					{
						editable: true,
						pages: context,
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'emit');

				component.element.querySelector('button[aria-label=\'trash\']').click();

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('deleteButtonClicked', expect.any(Object));
			}
		);

		it(
			'should render a layout and continue to propagate the field edit event',
			() => {
				component = new LayoutRenderer(
					{
						editable: true,
						pages: context,
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'emit');

				jest.runAllTimers();

				component.refs.field.emitFieldEdited();

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith(
					'fieldEdited',
					{
						key: 'Bar',
						originalEvent: {},
						value: 'Foo'
					}
				);
			}
		);

		it(
			'should render a layout and emit the field move event',
			() => {
				component = new LayoutRenderer(
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
				component = new LayoutRenderer(
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
			'should render a layout with emit an field clicked event',
			() => {
				component = new LayoutRenderer(
					{
						dragAndDropDisabled: true,
						editable: true,
						pages: context,
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'emit');

				component.element.querySelector('.ddm-drag').click();

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('fieldClicked', expect.any(Object));
			}
		);

		it(
			'should emit a fieldClicked event with the field location',
			() => {
				component = new LayoutRenderer(
					{
						dragAndDropDisabled: true,
						editable: true,
						pages: context,
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'emit');

				component.element.querySelector('.ddm-drag').click();

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith(
					'fieldClicked',
					expect.objectContaining(
						{
							columnIndex: expect.anything(),
							pageIndex: expect.any(Number),
							rowIndex: expect.any(Number)
						}
					)
				);
			}
		);

		it(
			'should render a layout and reset drag and drop to every change of API pages when editable is true',
			() => {
				component = new LayoutRenderer(
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

				const newContext = LayoutSupport.removeFields(context, 0, 1, 0);

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
				component = new LayoutRenderer(
					{
						editable: false,
						pages: context,
						spritemap
					}
				);

				const spy = jest.spyOn(component, '_startDrag');

				const newContext = LayoutSupport.removeFields(context, 0, 1, 0);

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
				component = new LayoutRenderer(
					{
						dragAndDropDisabled: true,
						editable: true,
						pages: context,
						spritemap
					}
				);

				const spy = jest.spyOn(component, '_startDrag');

				const newContext = LayoutSupport.removeFields(context, 0, 1, 0);

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

				const newContext = LayoutSupport.addFields(
					context,
					pageIndex,
					rowIndex,
					columnIndex,
					fields
				);

				component = new LayoutRenderer(
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

				const newContext = LayoutSupport.addFields(
					context,
					pageIndex,
					rowIndex,
					columnIndex,
					fields
				);

				component = new LayoutRenderer(
					{
						editable: false,
						pages: newContext,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);
	}
);