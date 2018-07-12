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

						return register().catch(
							err => {
								expect(err.message).toEqual('Invalid portlet ID');
							}
						);
					}
				);

				it(
					'should return an instance of PortletInit',
					() => {
						return register('portletA').then(
							hub => {
								expect(hub).toBeInstanceOf(PortletInit);
							}
						);
					}
				);
			}
		)
	}
);
