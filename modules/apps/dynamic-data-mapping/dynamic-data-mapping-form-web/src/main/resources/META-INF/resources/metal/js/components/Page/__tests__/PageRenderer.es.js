import './__fixtures__/Fields.es';
import {dom as MetalTestUtil} from 'metal-dom';
import mockPages from './__mock__/mockPages.es';
import PageRenderer from '../PageRenderer.es';

const spritemap = 'icons.svg';
let component;
let componentProps = null;
let pages = null;

describe(
	'PageRenderer',
	() => {
		beforeEach(
			() => {
				pages = JSON.parse(JSON.stringify(mockPages));
				componentProps = {
					activePage: 0,
					contentRenderer: 'grid',
					editable: true,
					page: pages,
					pageId: 0,
					spritemap,
					total: 1
				};

				jest.useFakeTimers();
			}
		);

		it(
			'should resize a specific column on the drag and drop layout',
			() => {
				component = new PageRenderer(
					{
						...componentProps
					}
				);

				component._handleOnClickResize();
				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should display empty drag message when there are rows with no columns specified',
			() => {
				component = new PageRenderer(
					{
						...componentProps,
						page: {
							rows: [{}]
						}
					}
				);
				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should change the page title',
			() => {
				component = new PageRenderer(
					{
						...componentProps
					}
				);

				const pageTitle = component.element.querySelector('.form-builder-page-header-title');
				const spy = jest.spyOn(component, 'emit');

				pageTitle.value = 'Page Title';

				jest.runAllTimers();
				MetalTestUtil.triggerEvent(pageTitle, 'keyup', {});

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('updatePage', expect.any(Object));

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should change the page title',
			() => {
				component = new PageRenderer(
					{
						...componentProps
					}
				);

				const pageDescription = component.element.querySelector('.form-builder-page-header-description');
				const spy = jest.spyOn(component, 'emit');

				pageDescription.value = 'Page Description';

				jest.runAllTimers();
				MetalTestUtil.triggerEvent(pageDescription, 'keyup', {});

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('updatePage', expect.any(Object));

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a layout and emit an event when delete button is clicked',
			() => {
				component = new PageRenderer(
					{
						...componentProps
					}
				);

				const spy = jest.spyOn(component, 'emit');

				component.element.querySelector('button[aria-label=\'trash\']').click();
				component.element.querySelector('.modal .btn-primary').click();

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('deleteButtonClicked', expect.any(Object));
			}
		);

		it(
			'should render a layout and emit an event when duplicate button is clicked',
			() => {
				component = new PageRenderer(
					{
						...componentProps
					}
				);

				const spy = jest.spyOn(component, 'emit');

				component.element.querySelector('button[aria-label=\'paste\']').click();

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('duplicateButtonClicked', expect.any(Object));
			}
		);

		it(
			'should render a layout with emit an field clicked event',
			() => {
				component = new PageRenderer(
					{
						...componentProps
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
				component = new PageRenderer(
					{
						...componentProps,
						dragAndDropDisabled: true
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
	}
);