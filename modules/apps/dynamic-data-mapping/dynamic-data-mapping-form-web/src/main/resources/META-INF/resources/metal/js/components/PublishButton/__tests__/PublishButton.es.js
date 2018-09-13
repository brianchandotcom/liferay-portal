import PublishButton from '../PublishButton.es';
import {dom} from 'metal-dom';

const formInstanceId = '12345';
const namespace = 'portlet_namespace';
const spritemap = 'spritemap';
const url = 'publish/url';

const createInput = (id, value) => {
	dom.enterDocument(`<input id="${namespace}${id}" value="${value}" />`);

	return document.querySelector(`#${namespace}${id}`);
};

describe(
	'PublishButton',
	() => {
		let component;
		let formInstanceIdInput;

		afterEach(
			() => {
				if (component) {
					component.dispose();
				}

				dom.exitDocument(formInstanceIdInput);
			}
		);

		beforeEach(
			() => {
				jest.useFakeTimers();
				fetch.resetMocks();

				createInput('formInstanceId', formInstanceId);
			}
		);

		it(
			'should render published',
			() => {
				component = new PublishButton(
					{
						namespace,
						published: true,
						spritemap,
						url
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render unpublished',
			() => {
				component = new PublishButton(
					{
						namespace,
						published: false,
						spritemap,
						url
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		describe(
			'publish()',
			() => {
				it(
					'should call fetch with published=true',
					() => {
						component = new PublishButton(
							{
								namespace,
								published: false,
								spritemap,
								url
							}
						);

						const fetchSpy = jest.spyOn(component, 'fetch');

						fetchSpy.mockImplementation(
							() => Promise.resolve(
								{
									modifiedDate: 'December'
								}
							)
						);

						return component.publish().then(
							() => expect(fetchSpy).toHaveBeenCalledWith(
								{
									[`${namespace}formInstanceId`]: formInstanceId,
									[`${namespace}published`]: true
								}
							)
						);
					}
				);
			}
		);

		describe(
			'unpublish()',
			() => {
				it(
					'should call fetch with published=false',
					() => {
						component = new PublishButton(
							{
								namespace,
								published: false,
								spritemap,
								url
							}
						);

						const fetchSpy = jest.spyOn(component, 'fetch');

						fetchSpy.mockImplementation(
							() => Promise.resolve(
								{
									modifiedDate: 'December'
								}
							)
						);

						return component.unpublish().then(
							() => expect(fetchSpy).toHaveBeenCalledWith(
								{
									[`${namespace}formInstanceId`]: formInstanceId,
									[`${namespace}published`]: false
								}
							)
						);
					}
				);
			}
		);

		describe(
			'toggle()',
			() => {
				it(
					'should call publish() when props.published=false',
					() => {
						component = new PublishButton(
							{
								namespace,
								published: false,
								spritemap,
								url
							}
						);

						const publishSpy = jest.spyOn(component, 'publish');

						publishSpy.mockImplementation(() => Promise.resolve());

						return component.toggle().then(() => expect(publishSpy).toHaveBeenCalled());
					}
				);

				it(
					'should call unpublish() when props.published=true',
					() => {
						component = new PublishButton(
							{
								namespace,
								published: true,
								spritemap,
								url
							}
						);

						const unpublishSpy = jest.spyOn(component, 'unpublish');

						unpublishSpy.mockImplementation(() => Promise.resolve());

						return component.toggle().then(() => expect(unpublishSpy).toHaveBeenCalled());
					}
				);

				it(
					'should be called when button is clicked',
					() => {
						component = new PublishButton(
							{
								namespace,
								published: true,
								spritemap,
								url
							}
						);

						const toggleSpy = jest.spyOn(component, 'toggle');

						component.refs.button.emit('click');

						jest.runAllTimers();

						expect(toggleSpy).toHaveBeenCalled();
					}
				);
			}
		);
	}
);