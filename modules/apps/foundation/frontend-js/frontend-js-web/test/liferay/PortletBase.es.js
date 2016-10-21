'use strict';

import PortletBase from '../../src/main/resources/META-INF/resources/liferay/PortletBase.es';

describe('PortletBase', () => {
	let namespace = '_portlet_namespace_';

	function createMockedHtml() {
		let div1 = document.createElement('div');
		div1.id = namespace + 'div1';
		div1.className = 'div';

		let div2 = document.createElement('div');
		div2.id = namespace + 'div2';
		div2.className = 'div';

		let div3 = document.createElement('div');
		div3.id = namespace + 'div3';
		div3.className = 'div';

		div1.appendChild(div3);

		document.body.appendChild(div1);
		document.body.appendChild(div2);
	}

	beforeEach((done) => {
		createMockedHtml();
		done();
	});

	afterEach((done) => {
		document.body.innerHTML = '';
		done();
	});

	it('should get the portlet namespace', (done) => {
		let portletBase = new PortletBase(
			{
				namespace: namespace
			}
		);

		assert.strictEqual(portletBase.get('namespace'), namespace);

		done();
	});

	it('should set the portlet rootNode', (done) => {
		let portletBase = new PortletBase(
			{
				namespace: namespace,
				rootNode: '#' + namespace + 'div1'
			}
		);

		let divElement = document.getElementById(namespace + 'div1');

		assert.strictEqual(portletBase.get('rootNode'), divElement);

		done();
	});

	it('should get all nodes with a specific class', (done) => {
		let portletBase = new PortletBase(
			{
				namespace: namespace
			}
		);

		assert.strictEqual(portletBase.all('.div').length, 3);

		done();
	});

	it('should get all nodes with a specific class inside a rootNode', (done) => {
		let portletBase = new PortletBase(
			{
				namespace: namespace,
				rootNode: '#' + namespace + 'div1'
			}
		);

		assert.strictEqual(portletBase.all('.div').length, 1);

		let portletBase2 = new PortletBase(
			{
				namespace: namespace
			}
		);

		assert.strictEqual(portletBase2.all('.div', '#' + namespace + 'div1').length, 1);

		done();
	});

	it('should get all nodes with an id without setting the namespace ', (done) => {
		let portletBase = new PortletBase(
			{
				namespace: namespace
			}
		);

		let divById = portletBase.all('#div1');

		assert.strictEqual(divById.length, 1);

		done();
	});

	it('should append the namespace to the given id', (done) => {
		let portletBase = new PortletBase(
			{
				namespace: namespace
			}
		);

		window.Liferay = {
			Util: {
				ns(a, b) {
					return a + b;
				}
			}
		};

		let stub = sinon.stub(Liferay.Util, 'ns');

		portletBase.ns('test');

		sinon.assert.calledWith(stub, namespace, 'test');

		done();
	});

	it('should get a node without setting the namespace', (done) => {
		let portletBase = new PortletBase(
			{
				namespace: namespace
			}
		);

		let divElement = document.getElementById(namespace + 'div1');

		assert.strictEqual(portletBase.one('#div1'), divElement);

		done();
	});

	it('should get a node which is inside a rootNode', (done) => {
		let portletBase = new PortletBase(
			{
				namespace: namespace,
				rootNode: '#' + namespace + 'div1'
			}
		);

		let divElement = document.getElementById(namespace + 'div3');

		assert.strictEqual(portletBase.one('#div3'), divElement);

		let portletBase2 = new PortletBase(
			{
				namespace: namespace
			}
		);

		assert.strictEqual(portletBase2.one('#div3', '#' + namespace + 'div1'), divElement);

		done();
	});

	it('should not get a node which is outside a specific rootNode', (done) => {
		let portletBase = new PortletBase(
			{
				namespace: namespace,
				rootNode: '#' + namespace + 'div1'
			}
		);

		assert.strictEqual(portletBase.one('#div2'), null);

		let portletBase2 = new PortletBase(
			{
				namespace: namespace
			}
		);

		assert.strictEqual(portletBase2.one('#div2', '#' + namespace + 'div1'), null);

		done();
	});

});