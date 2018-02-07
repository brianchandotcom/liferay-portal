'use strict';

import PortletBase from '../../src/main/resources/META-INF/resources/liferay/PortletBase.es';

describe(
	'PortletBase',
	() => {
		const namespace = '_com_liferay_test_portlet_';
		let portletBase;

		afterEach(() => portletBase.dispose());

		beforeAll(
			() => {
				document.body.innerHTML = `
					<div class="foo" id="p_p_id_com_liferay_test_portlet_">
						<div class="foo" id="_com_liferay_test_portlet_child_container">
							<div class="foo" id="_com_liferay_test_portlet_grand_child_container"></div>
						</div>
					</div>

					<div class="foo" id="p_p_id_com_liferay_another_portlet_"></div>
				`;
			}
		);

		beforeEach(
			() => {
				Liferay = {
					Util: {
						ns: jest.fn()
					}
				};

				portletBase = new PortletBase(
					{
						namespace: namespace
					}
				);
			}
		);

		describe(
			'PortletBase.all',
			() => {
				it(
					'should return an empty list if no elements are found',
					() => {
						const elements = portletBase.all('.bar');

						expect(elements).not.toBeNull();
						expect(elements.length).toEqual(0);
					}
				);

				it(
					'should get all matching nodes within the root node tree',
					() => {
						expect(portletBase.all('.foo').length).toEqual(2);
						expect(
							portletBase.all('.foo', '#_com_liferay_test_portlet_child_container')
								.length
						).toEqual(1);
					}
				);

				it(
					'should use the document as root node if one has not been specified or the default has not been found',
					() => {
						portletBase = new PortletBase(
							{
								namespace: '_com_liferay_unknown_portlet'
							}
						);

						expect(portletBase.all('.foo').length).toEqual(4);
					}
				);
			}
		);

		describe(
			'PortletBase.ns',
			() => {
				it(
					'should namespace objects with the portlet namespace using the provided Liferay.Util.ns helper',
					() => {
						portletBase.ns('test');

						expect(Liferay.Util.ns.mock.calls.length).toBe(1);
					}
				);
			}
		);

		describe(
			'PortletBase.one',
			() => {
				it(
					'should return null if no element is found',
					() => {
						expect(portletBase.one('.bar')).toBeNull();
					}
				);

				it(
					'should get the first matching node within the root node tree',
					() => {
						expect(portletBase.one('.foo')).toEqual(
							document.getElementById('_com_liferay_test_portlet_child_container')
						);

						expect(
							portletBase.one('.foo', '#_com_liferay_test_portlet_child_container')
						).toEqual(
							document.getElementById(
								'_com_liferay_test_portlet_grand_child_container'
							)
						);
					}
				);

				it(
					'should use the document as root node if one has not been specified or the default has not been found',
					() => {
						portletBase = new PortletBase(
							{
								namespace: '_com_liferay_unknown_portlet'
							}
						);

						expect(portletBase.one('.foo')).toEqual(
							document.getElementById('p_p_id_com_liferay_test_portlet_')
						);
					}
				);
			}
		);

		describe(
			'PortletBase.rootNode',
			() => {
				it(
					'should set the root node by default',
					() => {
						expect(portletBase.rootNode).toEqual(
							document.getElementById('p_p_id' + namespace)
						);
					}
				);

				it(
					'should use a specified root node',
					() => {
						portletBase.rootNode = '#' + namespace + 'child_container';

						expect(portletBase.rootNode).toEqual(
							document.getElementById(namespace + 'child_container')
						);
					}
				);

				it(
					'should override the default root node if specified',
					() => {
						portletBase = new PortletBase(
							{
								namespace: namespace,
								rootNode: '#' + namespace + 'child_container'
							}
						);

						expect(portletBase.rootNode).toEqual(
							document.getElementById(namespace + 'child_container')
						);
					}
				);
			}
		);
	}
);