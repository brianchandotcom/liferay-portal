import PublishButton from 'source/components/PublishButton/PublishButton.es';
import * as util from 'source/util/fetch.es';

const formInstanceId = '12345';
const namespace = 'portlet_namespace';
const spritemap = 'spritemap';
const url = 'publish/url';

const props = {
	formInstanceId,
	namespace,
	published: true,
	resolvePublishURL: () => Promise.resolve({formInstanceId, publishURL: 'published/form'}),
	spritemap,
	url
};

describe(
	'PublishButton',
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
				jest.useFakeTimers();
				fetch.resetMocks();
			}
		);

		it(
			'should render published',
			() => {
				component = new PublishButton(props);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render unpublished',
			() => {
				component = new PublishButton(
					{
						...props,
						published: false
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
						component = new PublishButton(props);

						const fetchSpy = jest.spyOn(util, 'makeFetch');

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
									body: util.convertToSearchParams(
										{
											[`${namespace}formInstanceId`]: formInstanceId,
											[`${namespace}published`]: true
										}
									),
									url
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
						component = new PublishButton(props);

						const fetchSpy = jest.spyOn(util, 'makeFetch');

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
									body: util.convertToSearchParams(
										{
											[`${namespace}formInstanceId`]: formInstanceId,
											[`${namespace}published`]: true
										}
									),
									url
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
								...props,
								published: false
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
								...props,
								published: true
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
						component = new PublishButton(props);

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