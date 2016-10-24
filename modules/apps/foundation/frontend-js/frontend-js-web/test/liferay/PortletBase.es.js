'use strict';

import PortletBase from '../../src/main/resources/META-INF/resources/liferay/PortletBase.es';

describe('PortletBase', () => {
	let namespace = '_com_liferay_test_portlet_';
	let portletBase;

	afterEach(() => portletBase.dispose());

	before(() => {
		window.Liferay = {
			Util: {
				ns() {}
			}
		};

		document.body.innerHTML += __html__['test/liferay/fixtures/PortletBase.html'];
	});

	beforeEach(() => {
		portletBase = new PortletBase(
			{
				namespace: namespace
			}
		);
	});

	describe('PortletBase.all', () => {
		it('should return an empty list if no elements are found', () => {
			let elements = portletBase.all('.bar');

			assert.isNotNull(elements);
			assert.strictEqual(elements.length, 0);
		});

		it('should get all matching nodes within the root node tree', () => {
			assert.strictEqual(portletBase.all('.foo').length, 2);
			assert.strictEqual(portletBase.all('.foo', '#_com_liferay_test_portlet_child_container').length, 1);
		});

		it('should use the document as root node if one has not been specified or the default has not been found', () => {
			portletBase = new PortletBase(
				{
					namespace: '_com_liferay_unknown_portlet'
				}
			);

			assert.strictEqual(portletBase.all('.foo').length, 4);
		});
	});

	describe('PortletBase.ns', () => {
		it('should namespace objects with the portlet namespace using the provided Liferay.Util.ns helper', () => {
			let stub = sinon.stub(Liferay.Util, 'ns');

			portletBase.ns('test');

			sinon.assert.calledWith(stub, namespace, 'test');
		});
	});

	describe('PortletBase.one', () => {
		it('should return null if no element is found', () => {
			assert.isNull(portletBase.one('.bar'));
		});

		it('should get the first matching node within the root node tree', () => {
			assert.strictEqual(portletBase.one('.foo'), document.getElementById('_com_liferay_test_portlet_child_container'));
			assert.strictEqual(portletBase.one('.foo', '#_com_liferay_test_portlet_child_container'), document.getElementById('_com_liferay_test_portlet_grand_child_container'));
		});

		it('should use the document as root node if one has not been specified or the default has not been found', () => {
			portletBase = new PortletBase(
				{
					namespace: '_com_liferay_unknown_portlet'
				}
			);

			assert.strictEqual(portletBase.one('.foo'), document.getElementById('p_p_id_com_liferay_test_portlet_'));
		});
	});

	describe('PortletBase.rootNode', () => {
		it('should set the root node by default', () => {
			assert.strictEqual(portletBase.rootNode, document.getElementById('p_p_id' + namespace));
		});

		it('should use a specified root node', () => {
			portletBase.rootNode = '#' + namespace + 'child_container';

			assert.strictEqual(portletBase.rootNode, document.getElementById(namespace + 'child_container'));
		});

		it('should override the default root node if specified', () => {
			portletBase = new PortletBase(
				{
					namespace: namespace,
					rootNode: '#' + namespace + 'child_container'
				}
			);

			assert.strictEqual(portletBase.rootNode, document.getElementById(namespace + 'child_container'));
		});
	});
});