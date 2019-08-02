/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import dom from 'metal-dom';
import Component from 'metal-component';
import IncrementalDomRenderer from 'metal-incremental-dom';
import KeyboardFocusManager from '../../../src/main/resources/META-INF/resources/liferay/keyboard-focus/KeyboardFocusManager.es';

class TestComponent extends Component {
	render() {
		IncrementalDOM.elementOpen('div');
		IncrementalDOM.elementVoid('button', null, null, 'ref', 'el-0');
		IncrementalDOM.elementVoid('button', null, null, 'ref', 'el-1');
		IncrementalDOM.elementVoid('button', null, null, 'ref', 'el-2');
		IncrementalDOM.elementClose('div');
	}
}
TestComponent.RENDERER = IncrementalDomRenderer;

describe('KeyboardFocusManager', function() {
	let component;
	let manager;

	afterEach(function() {
		if (component) {
			component.dispose();
		}
		if (manager) {
			manager.dispose();
		}
	});

	it('focus previous element when the left arrow key is pressed', function() {
		component = new TestComponent();
		manager = new KeyboardFocusManager(component, 'button');
		manager.start();

		dom.triggerEvent(component.refs['el-1'], 'keydown', {
			keyCode: 37
		});
		expect(component.refs['el-0']).toBe(document.activeElement);
	});

	it('focus previous element when the up arrow key is pressed', function() {
		component = new TestComponent();
		manager = new KeyboardFocusManager(component, 'button');
		manager.start();

		dom.triggerEvent(component.refs['el-1'], 'keydown', {
			keyCode: 38
		});
		expect(component.refs['el-0']).toBe(document.activeElement);
	});

	it('does not change focus when the left/up arrow keys are pressed on first element', function() {
		component = new TestComponent();
		manager = new KeyboardFocusManager(component, 'button');
		manager.start();

		const prevActiveElement = document.activeElement;
		dom.triggerEvent(component.refs['el-0'], 'keydown', {
			keyCode: 37
		});
		expect(prevActiveElement).toBe(document.activeElement);

		dom.triggerEvent(component.refs['el-0'], 'keydown', {
			keyCode: 38
		});
		expect(prevActiveElement).toBe(document.activeElement);
	});

	it('focus next element when the right arrow key is pressed', function() {
		component = new TestComponent();
		manager = new KeyboardFocusManager(component, 'button');
		manager.start();

		dom.triggerEvent(component.refs['el-0'], 'keydown', {
			keyCode: 39
		});
		expect(component.refs['el-1']).toBe(document.activeElement);
	});

	it('focus next element when the down arrow key is pressed', function() {
		component = new TestComponent();
		manager = new KeyboardFocusManager(component, 'button');
		manager.start();

		dom.triggerEvent(component.refs['el-0'], 'keydown', {
			keyCode: 40
		});
		expect(component.refs['el-1']).toBe(document.activeElement);
	});

	it('does not change focus when the right/down arrow keys are pressed on last element', function() {
		component = new TestComponent();
		manager = new KeyboardFocusManager(component, 'button');
		manager.start();

		const prevActiveElement = document.activeElement;
		dom.triggerEvent(component.refs['el-2'], 'keydown', {
			keyCode: 39
		});
		expect(prevActiveElement).toBe(document.activeElement);

		dom.triggerEvent(component.refs['el-2'], 'keydown', {
			keyCode: 40
		});
		expect(prevActiveElement).toBe(document.activeElement);
	});

	it('does not change focus when any non arrow keys are pressed', function() {
		component = new TestComponent();
		manager = new KeyboardFocusManager(component, 'button');
		manager.start();

		const prevActiveElement = document.activeElement;
		dom.triggerEvent(component.refs['el-0'], 'keydown', {
			keyCode: 10
		});
		expect(prevActiveElement).toBe(document.activeElement);
	});

	it('does not change focus when key is pressed on element without ref', function() {
		class TestComponentNoRef extends Component {
			render() {
				IncrementalDOM.elementOpen('div');
				IncrementalDOM.elementVoid('button');
				IncrementalDOM.elementVoid('button');
				IncrementalDOM.elementClose('div');
			}
		}
		TestComponentNoRef.RENDERER = IncrementalDomRenderer;

		component = new TestComponentNoRef();
		manager = new KeyboardFocusManager(component, 'button');
		manager.start();

		const prevActiveElement = document.activeElement;
		dom.triggerEvent(component.element.childNodes[0], 'keydown', {
			keyCode: 40
		});
		expect(prevActiveElement).toBe(document.activeElement);
	});

	it('does not change focus when key is pressed on element with ref outside expected format', function() {
		class TestComponentDifferentRef extends Component {
			render() {
				IncrementalDOM.elementOpen('div');
				IncrementalDOM.elementVoid(
					'button',
					null,
					null,
					'ref',
					'button0'
				);
				IncrementalDOM.elementVoid(
					'button',
					null,
					null,
					'ref',
					'button1'
				);
				IncrementalDOM.elementClose('div');
			}
		}
		TestComponentDifferentRef.RENDERER = IncrementalDomRenderer;

		component = new TestComponentDifferentRef();
		manager = new KeyboardFocusManager(component, 'button');
		manager.start();

		const prevActiveElement = document.activeElement;
		dom.triggerEvent(component.refs.button0, 'keydown', {
			keyCode: 40
		});
		expect(prevActiveElement).toBe(document.activeElement);
	});

	it("does not change focus when key is pressed on element that doesn't match the selector", function() {
		component = new TestComponent();
		manager = new KeyboardFocusManager(component, 'li');
		manager.start();

		dom.triggerEvent(component.refs['el-0'], 'keydown', {
			keyCode: 40
		});
		expect(component.refs['el-1']).not.toBe(document.activeElement);
	});

	it('change focus accordingly when key is pressed on any element when no selector is given', function() {
		class TestComponentNoSelector extends Component {
			render() {
				IncrementalDOM.elementOpen('div');
				IncrementalDOM.elementVoid('button', null, null, 'ref', 'el-0');
				IncrementalDOM.elementVoid(
					'li',
					null,
					null,
					'ref',
					'el-1',
					'tabindex',
					'0'
				);
				IncrementalDOM.elementClose('div');
			}
		}
		TestComponentNoSelector.RENDERER = IncrementalDomRenderer;

		component = new TestComponentNoSelector();
		manager = new KeyboardFocusManager(component);
		manager.start();

		dom.triggerEvent(component.refs['el-0'], 'keydown', {
			keyCode: 40
		});
		expect(component.refs['el-1']).toBe(document.activeElement);
	});

	it('skip elements with "data-unfocusable" set to true when focusing', function() {
		class TestComponentUnfocusable extends Component {
			render() {
				IncrementalDOM.elementOpen('div');
				IncrementalDOM.elementVoid('button', null, null, 'ref', 'el-0');
				IncrementalDOM.elementVoid(
					'button',
					null,
					null,
					'ref',
					'el-1',
					'data-unfocusable',
					'true'
				);
				IncrementalDOM.elementVoid('button', null, null, 'ref', 'el-2');
				IncrementalDOM.elementClose('div');
			}
		}
		TestComponentUnfocusable.RENDERER = IncrementalDomRenderer;

		component = new TestComponentUnfocusable();
		manager = new KeyboardFocusManager(component, 'button');
		manager.start();

		dom.triggerEvent(component.refs['el-0'], 'keydown', {
			keyCode: 39
		});
		expect(component.refs['el-2']).toBe(document.activeElement);
	});

	it('does not change focus when key is pressed before starting the manager', function() {
		component = new TestComponent();
		manager = new KeyboardFocusManager(component, 'button');

		dom.triggerEvent(component.refs['el-0'], 'keydown', {
			keyCode: 40
		});
		expect(component.refs['el-1']).not.toBe(document.activeElement);
	});

	it('does not change focus when key is pressed after stopping the manager', function() {
		component = new TestComponent();
		manager = new KeyboardFocusManager(component, 'button');
		manager.start();
		manager.stop();

		dom.triggerEvent(component.refs['el-0'], 'keydown', {
			keyCode: 40
		});
		expect(component.refs['el-1']).not.toBe(document.activeElement);
	});

	it('focus next elements correctly even if "start" is called more than once', function() {
		component = new TestComponent();
		manager = new KeyboardFocusManager(component, 'button');
		manager.start();
		manager.start();

		dom.triggerEvent(component.refs['el-1'], 'keydown', {
			keyCode: 37
		});
		expect(component.refs['el-0']).toBe(document.activeElement);
	});

	it('emit event when element is focused', function() {
		component = new TestComponent();
		manager = new KeyboardFocusManager(component, 'button');
		manager.start();

		const spy = jest.fn();
		manager.on(KeyboardFocusManager.EVENT_FOCUSED, spy);
		dom.triggerEvent(component.refs['el-0'], 'keydown', {
			keyCode: 39
		});
		expect(spy).toHaveBeenCalledTimes(1);
		expect(spy).toHaveBeenCalledWith({
			element: component.refs['el-1'],
			ref: 'el-1'
		});
	});

	describe('setCircularLength', function() {
		it('focus last element when the left arrow key is pressed on first element', function() {
			component = new TestComponent();
			manager = new KeyboardFocusManager(component, 'button')
				.setCircularLength(3)
				.start();

			dom.triggerEvent(component.refs['el-0'], 'keydown', {
				keyCode: 37
			});
			expect(component.refs['el-2']).toBe(document.activeElement);
		});

		it('focus last element when the up arrow key is pressed on first element', function() {
			component = new TestComponent();
			manager = new KeyboardFocusManager(component, 'button')
				.setCircularLength(3)
				.start();

			dom.triggerEvent(component.refs['el-0'], 'keydown', {
				keyCode: 38
			});
			expect(component.refs['el-2']).toBe(document.activeElement);
		});

		it('focus first element when the right arrow key is pressed on last element', function() {
			component = new TestComponent();
			manager = new KeyboardFocusManager(component, 'button')
				.setCircularLength(3)
				.start();

			dom.triggerEvent(component.refs['el-2'], 'keydown', {
				keyCode: 39
			});
			expect(component.refs['el-0']).toBe(document.activeElement);
		});

		it('focus first element when the down arrow key is pressed on last element', function() {
			component = new TestComponent();
			manager = new KeyboardFocusManager(component, 'button')
				.setCircularLength(3)
				.start();

			dom.triggerEvent(component.refs['el-2'], 'keydown', {
				keyCode: 40
			});
			expect(component.refs['el-0']).toBe(document.activeElement);
		});

		it('focus next element when right/down arrow key is pressed on non last element', function() {
			component = new TestComponent();
			manager = new KeyboardFocusManager(component, 'button')
				.setCircularLength(3)
				.start();

			dom.triggerEvent(component.refs['el-0'], 'keydown', {
				keyCode: 39
			});
			expect(component.refs['el-1']).toBe(document.activeElement);

			dom.triggerEvent(component.refs['el-1'], 'keydown', {
				keyCode: 40
			});
			expect(component.refs['el-2']).toBe(document.activeElement);
		});

		it('focus previous element when left/up arrow key is pressed on non first element', function() {
			component = new TestComponent();
			manager = new KeyboardFocusManager(component, 'button')
				.setCircularLength(3)
				.start();

			dom.triggerEvent(component.refs['el-2'], 'keydown', {
				keyCode: 37
			});
			expect(component.refs['el-1']).toBe(document.activeElement);

			dom.triggerEvent(component.refs['el-1'], 'keydown', {
				keyCode: 38
			});
			expect(component.refs['el-0']).toBe(document.activeElement);
		});
	});

	describe('setFocusHandler', function() {
		it('focus the element returned by the custom focus handler', function() {
			component = new TestComponent();
			manager = new KeyboardFocusManager(component, 'button')
				.setFocusHandler(() => component.refs['el-2'])
				.start();

			dom.triggerEvent(component.refs['el-0'], 'keydown', {
				keyCode: 10
			});
			expect(component.refs['el-2']).toBe(document.activeElement);
		});

		it('emit event for element focused due to the custom focus handler', function() {
			component = new TestComponent();
			manager = new KeyboardFocusManager(component, 'button')
				.setFocusHandler(() => component.refs['el-2'])
				.start();

			const spy = jest.fn();
			manager.on(KeyboardFocusManager.EVENT_FOCUSED, spy);
			dom.triggerEvent(component.refs['el-0'], 'keydown', {
				keyCode: 10
			});
			expect(spy).toHaveBeenCalledTimes(1);
			expect(spy).toHaveBeenCalledWith({
				element: component.refs['el-2'],
				ref: null
			});
		});

		it('focus the element with the ref returned by the custom focus handler', function() {
			component = new TestComponent();
			manager = new KeyboardFocusManager(component, 'button')
				.setFocusHandler(() => 'el-2')
				.start();

			dom.triggerEvent(component.refs['el-0'], 'keydown', {
				keyCode: 10
			});
			expect(component.refs['el-2']).toBe(document.activeElement);
		});

		it('emit event for element focused due to the custom focus handler via ref', function() {
			component = new TestComponent();
			manager = new KeyboardFocusManager(component, 'button')
				.setFocusHandler(() => 'el-2')
				.start();

			const spy = jest.fn();
			manager.on(KeyboardFocusManager.EVENT_FOCUSED, spy);
			dom.triggerEvent(component.refs['el-0'], 'keydown', {
				keyCode: 10
			});
			expect(spy).toHaveBeenCalledTimes(1);
			expect(spy).toHaveBeenCalledWith({
				element: component.refs['el-2'],
				ref: 'el-2'
			});
		});

		it('does not focus on any element if the custom focus handler returns nothing', function() {
			component = new TestComponent();
			manager = new KeyboardFocusManager(component, 'button')
				.setFocusHandler(() => null)
				.start();

			var prevActiveElement = document.activeElement;
			dom.triggerEvent(component.refs['el-0'], 'keydown', {
				keyCode: 39
			});
			expect(prevActiveElement).toBe(document.activeElement);
		});

		it('run default behavior if custom focus handler returns "true"', function() {
			component = new TestComponent();
			manager = new KeyboardFocusManager(component, 'button')
				.setFocusHandler(() => true)
				.start();

			dom.triggerEvent(component.refs['el-0'], 'keydown', {
				keyCode: 39
			});
			expect(component.refs['el-1']).toBe(document.activeElement);
		});
	});
});
