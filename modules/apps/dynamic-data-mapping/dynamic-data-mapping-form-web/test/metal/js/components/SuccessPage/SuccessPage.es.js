import {dom as MetalTestUtil} from 'metal-dom';
import SucessPageSettings from 'mock/mockSuccessPage.es';
import SuccessPage from 'source/components/SuccessPage/SuccessPage.es';

let component;
let successPageSettings;

describe.only(
	'SuccessPage',
	() => {
		beforeEach(
			() => {
				successPageSettings = JSON.parse(JSON.stringify(SucessPageSettings));

				jest.useFakeTimers();
			}
		);

		afterEach(
			() => {
				if (component) {
					component.dispose();
				}

				successPageSettings = null;
			}
		);

		it(
			'should render the default layour',
			() => {

				component = new SuccessPage(
					{
						contentLabel: 'Content',
						successPageSettings,
						titleLabel: 'Title'
					}
				);

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should emit success page changed when success page title is changed',
			() => {
				const newPageSettings = {
					...successPageSettings,
					enabled: true
				};

				component = new SuccessPage(
					{
						contentLabel: 'Content',
						successPageSettings: newPageSettings,
						titleLabel: 'Title'
					}
				);
				const spy = jest.spyOn(component, 'emit');
				const titleNode = component.element.querySelector('input[data-setting="title"]');

				titleNode.value = 'Some title';

				jest.runAllTimers();

				MetalTestUtil.triggerEvent(titleNode, 'keyup', {});

				expect(spy).toHaveBeenCalledWith('successPageChanged', expect.anything());
			}
		);

		it(
			'should emit success page changed when success page body is changed',
			() => {
				const newPageSettings = {
					...successPageSettings,
					enabled: true
				};

				component = new SuccessPage(
					{
						contentLabel: 'Content',
						successPageSettings: newPageSettings,
						titleLabel: 'Title'
					}
				);
				const spy = jest.spyOn(component, 'emit');
				const titleNode = component.element.querySelector('input[data-setting="body"]');

				titleNode.value = 'Some description';

				jest.runAllTimers();

				MetalTestUtil.triggerEvent(titleNode, 'keyup', {});

				expect(spy).toHaveBeenCalledWith('successPageChanged', expect.anything());
			}
		);
	}
);