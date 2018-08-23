import PortletInit from '../../../src/main/resources/META-INF/resources/liferay/portlet/PortletInit.es';
import register from '../../../src/main/resources/META-INF/resources/liferay/portlet/register.es';

describe(
	'PortletHub',
	() => {
		describe(
			'register',
			() => {
				it(
					'should throw error if called without portletId',
					() => {
						expect.assertions(1);

						const testFn = () => register();

						expect(testFn).toThrow();
					}
				);

				it(
					'should return an instance of PortletInit',
					() => {
						return register('PortletA').then(
							hub => {
								expect(hub).toBeInstanceOf(PortletInit);
							}
						);
					}
				);
			}
		);
	}
);