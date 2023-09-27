/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {buildFragment} from 'frontend-js-web';

import Surface from '../../src/main/resources/META-INF/resources/surface/Surface';

describe('Surface', () => {
	beforeAll(() => {
		window.Liferay.DOMTaskRunner = {
			runTasks: jest.fn(),
		};
	});

	describe('Constructor', () => {
		it('throws error when surface id not specified', () => {
			expect(() => {
				new Surface();
			}).toThrow();
		});

		it('does not throw error when surface id specified', () => {
			expect(() => {
				new Surface('id');
			}).not.toThrow();
		});
	});

	describe('Surfaces', () => {
		it('creates surface child when adding screen content to surface', () => {
			enterDocumentSurfaceElement('surfaceId');
			const surface = new Surface('surfaceId');
			const surfaceChild = surface.addContent('screenId', 'content');
			expect(surfaceChild.nodeType).toBe(1);
			exitDocumentSurfaceElement('surfaceId');
		});

		it('creates surface child when adding screen content to surface outside document', () => {
			const surface = new Surface('virtualSurfaceId');
			const surfaceChild = surface.addContent('screenId', 'content');
			expect('content').toEqual(surfaceChild.innerHTML);
		});

		it('wraps initial surface content as default child if default wrapper missing', () => {
			enterDocumentSurfaceElement('surfaceId', 'default');
			const surface = new Surface('surfaceId');
			expect('default').toEqual(surface.defaultChild.innerHTML);
			exitDocumentSurfaceElement('surfaceId');
		});

		it('adds screen content to surface child', () => {
			enterDocumentSurfaceElement('surfaceId');
			const surface = new Surface('surfaceId');
			const surfaceChild = surface.addContent('screenId', 'content');
			expect('content').toEqual(surfaceChild.innerHTML);
			exitDocumentSurfaceElement('surfaceId');
		});

		it('adds empty string as screen content to surface child', () => {
			enterDocumentSurfaceElement('surfaceId');
			const surface = new Surface('surfaceId');
			const surfaceChild = surface.addContent('screenId', '');
			expect('').toEqual(surfaceChild.innerHTML);
			exitDocumentSurfaceElement('surfaceId');
		});

		it('does not add null/undefined as screen content to surface child', () => {
			enterDocumentSurfaceElement('surfaceId');
			const surface = new Surface('surfaceId');
			let surfaceChild = surface.addContent('screenId', undefined);
			expect(surface.defaultChild).toEqual(surfaceChild);
			surfaceChild = surface.addContent('screenId', null);
			expect(surface.defaultChild).toEqual(surfaceChild);
			exitDocumentSurfaceElement('surfaceId');
		});

		it('inserts surface child be inserted into surface element', () => {
			enterDocumentSurfaceElement('surfaceId');
			const surface = new Surface('surfaceId');
			const surfaceChild = surface.addContent('screenId', 'content');
			expect(surface.getElement()).toEqual(surfaceChild.parentNode);
			exitDocumentSurfaceElement('surfaceId');
		});

		it('inserts invisible surface child into document', () => {
			enterDocumentSurfaceElement('surfaceId');
			const surface = new Surface('surfaceId');
			const surfaceChild = surface.addContent('screenId', 'content');
			expect('none').toEqual(surfaceChild.style.display);
			exitDocumentSurfaceElement('surfaceId');
		});

		it('makes surface child visible for its screen', () => {
			enterDocumentSurfaceElement('surfaceId');
			const surface = new Surface('surfaceId');
			const surfaceChild = surface.addContent('screenId', 'content');
			surface.show('screenId');
			expect('block').toEqual(surfaceChild.style.display);
			exitDocumentSurfaceElement('surfaceId');
		});

		it('ensures only one surface child is visible at a time', () => {
			enterDocumentSurfaceElement('surfaceId');
			const surface = new Surface('surfaceId');
			const surfaceChild = surface.addContent('screenId', 'content');
			surface.show('screenId');
			const surfaceChildNext = surface.addContent(
				'screenNextId',
				'content'
			);
			expect('none').toEqual(surfaceChildNext.style.display);
			surface.show('screenNextId');
			expect('none').toEqual(surfaceChild.style.display);
			expect('block').toEqual(surfaceChildNext.style.display);
			exitDocumentSurfaceElement('surfaceId');
		});

		it('removes screen content from surface child', () => {
			enterDocumentSurfaceElement('surfaceId');
			const surface = new Surface('surfaceId');
			surface.addContent('screenId', 'content');
			surface.remove('screenId');
			expect(null).toEqual(surface.getChild('screenId'));
			exitDocumentSurfaceElement('surfaceId');
		});

		it('removes screen content from surface child outside document', () => {
			enterDocumentSurfaceElement('surfaceId');
			const surface = new Surface('surfaceId');
			surface.remove('screenId');
			expect(null).toEqual(surface.getChild('screenId'));
			exitDocumentSurfaceElement('surfaceId');
		});

		it('creates surface child relating surface id and screen id', () => {
			const surface = new Surface('surfaceId');
			const surfaceChild = surface.createChild('screenId');
			expect('surfaceId-screenId').toEqual(surfaceChild.id);
		});

		it('gets surface element by surfaceId', () => {
			const surfaceElement = enterDocumentSurfaceElement('surfaceId');
			const surface = new Surface('surfaceId');
			expect(surfaceElement).toEqual(surface.getElement());
			exitDocumentSurfaceElement('surfaceId');
		});

		it('gets surface id', () => {
			const surface = new Surface('surfaceId');
			expect('surfaceId').toEqual(surface.getId());
			surface.setId('otherId');
			expect('otherId').toEqual(surface.getId());
		});

		it('shows default surface child if screen id not found and hide when found', () => {
			const defaultChild = enterDocumentSurfaceElement(
				'surfaceId-default'
			);
			enterDocumentSurfaceElement('surfaceId').appendChild(defaultChild);
			const surface = new Surface('surfaceId');
			surface.show('screenId');
			const surfaceChild = surface.addContent('screenId', 'content');
			expect('none').toEqual(surfaceChild.style.display);
			expect('block').toEqual(defaultChild.style.display);
			surface.show('screenId');
			expect('block').toEqual(surfaceChild.style.display);
			expect('none').toEqual(defaultChild.style.display);
			exitDocumentSurfaceElement('surfaceId');
		});

		it('removes surface child content if already in document', () => {
			const surfaceChild = enterDocumentSurfaceElement(
				'surfaceId-screenId'
			);
			enterDocumentSurfaceElement('surfaceId').appendChild(surfaceChild);
			surfaceChild.innerHTML = 'temp';
			const surface = new Surface('surfaceId');
			surface.addContent('screenId', 'content');
			expect('content').toEqual(surfaceChild.innerHTML);
			exitDocumentSurfaceElement('surfaceId');
		});

		it('overwrites default transition', () => {
			enterDocumentSurfaceElement('surfaceId');
			const surface = new Surface('surfaceId');
			surface.addContent('screenId', 'content');
			const transitionFn = jest.fn();
			surface.setTransitionFn(transitionFn);
			surface.show('screenId');
			expect(transitionFn).toHaveBeenCalledTimes(1);
			expect(transitionFn).toEqual(surface.getTransitionFn());
			exitDocumentSurfaceElement('surfaceId');
		});

		it('waits deferred transition before removing visible surface child', (done) => {
			enterDocumentSurfaceElement('surfaceId');
			const surface = new Surface('surfaceId');
			const surfaceChild = surface.addContent('screenId', 'content');
			const surfaceChildNext = surface.addContent(
				'screenNextId',
				'content'
			);
			const transitionFn = () => Promise.resolve();
			surface.setTransitionFn(transitionFn);
			surface.show('screenId');
			surface.show('screenNextId').then(() => {
				expect(!surfaceChild.parentNode).toBeTruthy();
				expect(surfaceChildNext.parentNode).toBeTruthy();
				done();
			});
			expect(surfaceChild.parentNode).toBeTruthy();
			expect(surfaceChildNext.parentNode).toBeTruthy();
			exitDocumentSurfaceElement('surfaceId');
		});

		it.skip('cancels deferred transition deferred', (done) => {
			const surface = new Surface('surfaceId');
			const transitionFn = () => Promise.resolve();
			surface.setTransitionFn(transitionFn);
			surface
				.transition(null, null)
				.catch(() => done())
				.cancel();
		});
	});
});

function enterDocumentSurfaceElement(surfaceId, opt_content) {
	document.body.appendChild(
		buildFragment(
			`<div id="${surfaceId}">${opt_content ? opt_content : ''}</div>`
		)
	);

	return document.getElementById(surfaceId);
}

function exitDocumentSurfaceElement(surfaceId) {
	return document.getElementById(surfaceId).remove();
}
