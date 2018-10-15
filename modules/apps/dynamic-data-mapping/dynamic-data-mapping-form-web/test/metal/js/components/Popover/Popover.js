import Popover from 'source/components/Popover/Popover.es';
import {dom as MetalTestUtil} from 'metal-dom';

const spritemap = 'spritemap';
const props = {
	content: "This content will be displayed when popover appears",
	visible: false,
	placement: 0,
	title: "Liferay"
};

let alignElement;


describe(
	'Popover',
	() => {
		let component;

		afterEach(
			() => {
				if (component) {
					component.dispose();
				}
			}
		);

		beforeEach(
			() => {
				alignElement = document.createElement('div');
				alignElement.classList.add('align-element');

				document.querySelector('body').appendChild(alignElement);

				props.alignElement = alignElement;

				jest.useFakeTimers();
				fetch.resetMocks();
			}
		);

		it(
			'should render the default markup',
			() => {
				component = new Popover(props);
				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render popover opened',
			() => {
				component = new Popover({
					...props,
					visible: true
				});

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should open when the visible property changes',
			() => {
				component = new Popover(props);

				jest.runAllTimers();

				jest.useFakeTimers();

				component.willReceiveProps(
					{
						visible: {
							newVal: true
						}
					}
				)

				jest.runAllTimers();

				expect(component.state.displayed).toBeTruthy();
				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should open when alignedElement is clicked',
			() => {
				component = new Popover(props);

				jest.runAllTimers();

				MetalTestUtil.triggerEvent(alignElement, 'click')

				expect(component.state.displayed).toBeTruthy();
				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should close when it is already opened and the alignedElement is clicked',
			() => {
				component = new Popover({
					...props,
					visible: true
				});

				jest.runAllTimers();

				MetalTestUtil.triggerEvent(alignElement, 'click')

				expect(component.state.displayed).toBeFalsy();
				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should close when document has mousedown event',
			() => {
				component = new Popover({
					...props,
					visible: true
				});

				jest.runAllTimers();

				jest.useFakeTimers();

				MetalTestUtil.triggerEvent(document, 'mousedown')

				jest.runAllTimers();

				expect(component.state.displayed).toBeFalsy();
				expect(component).toMatchSnapshot();
			}
		);
	}
);