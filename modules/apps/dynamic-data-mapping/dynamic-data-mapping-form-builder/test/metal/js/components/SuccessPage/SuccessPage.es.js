import SuccessPage from 'source/components/SuccessPage/SuccessPage.es';
import SuccessPageSettings from 'mock/mockSuccessPage.es';
import {JSXComponent} from 'metal-jsx';
import {dom as MetalTestUtil} from 'metal-dom';

let component;
let successPageSettings;

const defaultStore = {
	props: {
		editingLanguageId: 'en_US'
	}
}

const getSuccessPage = ({contentLabel, successPageSettings, titleLabel}, dispatch = () => {}, store = defaultStore) => {
	return class Parent extends JSXComponent {
		render() {
			return (
				<SuccessPage ref="successPage" contentLabel={contentLabel} successPageSettings={successPageSettings} titleLabel={titleLabel}/>
			);
		}

		getChildContext() {
			return {
				store,
				dispatch
			}
		}
	}
}

describe(
	'SuccessPage',
	() => {
		beforeEach(
			() => {
				successPageSettings = JSON.parse(JSON.stringify(SuccessPageSettings));
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
			'should render the default layout',
			() => {
				const SuccessPage = getSuccessPage(
					{
						contentLabel: 'Content',
						successPageSettings,
						titleLabel: 'Title'
					}
				);

				component = new SuccessPage();

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should emit success page changed when success page title is changed',
			() => {
				const spy = jest.fn();

				const newPageSettings = {
					...successPageSettings,
					enabled: true
				};

				const SuccessPage = getSuccessPage(
					{
						contentLabel: 'Content',
						successPageSettings,
						titleLabel: 'Title'
					},
					spy
				);

				component = new SuccessPage();

				component.refs.successPage.successPageSettings = newPageSettings;

				const titleNode = component.element.querySelector('input[data-setting="title"]');

				titleNode.value = 'Some title';

				jest.runAllTimers();

				const event = {
					delegateTarget: {
						dataset: {
							setting: 'title'
						},
						value: titleNode
					}
				};

				component.refs.successPage._handleSuccessPageUpdated(event);

				expect(spy).toHaveBeenCalledWith('successPageChanged', expect.anything());
			}
		);

		it(
			'should emit success page changed when success page body is changed',
			() => {
				const spy = jest.fn();

				const newPageSettings = {
					...successPageSettings,
					enabled: true
				};

				const SuccessPage = getSuccessPage(
					{
						contentLabel: 'Content',
						successPageSettings,
						titleLabel: 'Title'
					},
					spy
				);

				component = new SuccessPage();

				component.refs.successPage.successPageSettings = newPageSettings;

				const titleNode = component.element.querySelector('input[data-setting="body"]');

				titleNode.value = 'Some description';

				jest.runAllTimers();

				const event = {
					delegateTarget: {
						dataset: {
							setting: 'title'
						},
						value: titleNode
					}
				};

				component.refs.successPage._handleSuccessPageUpdated(event);

				expect(spy).toHaveBeenCalledWith('successPageChanged', expect.anything());
			}
		);
	}
);