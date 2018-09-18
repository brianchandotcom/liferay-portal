import {JSXComponent} from 'metal-jsx';
import {dom as MetalTestUtil} from 'metal-dom';
import SucessPageSettings from './__mock__/mockSuccessPage.es';
import SuccessPage from '../SuccessPage.es';

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
						titleLabel: 'Title',
						successPageSettings
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
				}
			
				component = new SuccessPage(
					{
						contentLabel: 'Content',
						titleLabel: 'Title',
						successPageSettings: newPageSettings
					}
				);
				const spy = jest.spyOn(component, 'emit');
				const titleNode = component.element.querySelector('input[data-setting="title"]');

				titleNode.value = "Some title";

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
				}
			
				component = new SuccessPage(
					{
						contentLabel: 'Content',
						titleLabel: 'Title',
						successPageSettings: newPageSettings
					}
				);
				const spy = jest.spyOn(component, 'emit');
				const titleNode = component.element.querySelector('input[data-setting="body"]');

				titleNode.value = "Some description";

				jest.runAllTimers();

				MetalTestUtil.triggerEvent(titleNode, 'keyup', {});

				expect(spy).toHaveBeenCalledWith('successPageChanged', expect.anything());
			}
		);
	}
);