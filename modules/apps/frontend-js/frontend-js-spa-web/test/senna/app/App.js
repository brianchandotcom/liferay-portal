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

import {dom, exitDocument} from 'metal-dom';
import {EventEmitter} from 'metal-events';

import App from '../../../src/main/resources/META-INF/resources/senna/app/App';
import globals from '../../../src/main/resources/META-INF/resources/senna/globals/globals';
import Route from '../../../src/main/resources/META-INF/resources/senna/route/Route';
import HtmlScreen from '../../../src/main/resources/META-INF/resources/senna/screen/HtmlScreen';
import Screen from '../../../src/main/resources/META-INF/resources/senna/screen/Screen';
import Surface from '../../../src/main/resources/META-INF/resources/senna/surface/Surface';
import utils from '../../../src/main/resources/META-INF/resources/senna/utils/utils';

class StubScreen extends Screen {}
StubScreen.prototype.activate = jest.fn();
StubScreen.prototype.beforeDeactivate = jest.fn();
StubScreen.prototype.deactivate = jest.fn();
StubScreen.prototype.flip = jest.fn();
StubScreen.prototype.load = jest.fn(() => Promise.resolve());
StubScreen.prototype.disposeInternal = jest.fn();
StubScreen.prototype.evaluateStyles = jest.fn();
StubScreen.prototype.evaluateScripts = jest.fn();

describe('App', function () {
	beforeEach(() => {
		globals.window = window;
		globals.capturedFormElement = undefined;
		globals.capturedFormButtonElement = undefined;

		const beforeunload = jest.fn();
		window.onbeforeunload = beforeunload;

		jest.spyOn(console, 'log').mockImplementation(() => {});
		jest.spyOn(window, 'scrollTo').mockImplementation(() => {});
	});

	afterEach(() => {
		if (this.app && !this.app.isDisposed()) {
			this.app.dispose();
		}
		this.app = null;
	});

	it('adds route', () => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		var route = this.app.findRoute('/path');
		expect(this.app.hasRoutes()).toBe(true);
		expect(route).toBeInstanceOf(Route);
		expect(route.getPath()).toBe('/path');
		expect(route.getHandler()).toBe(Screen);
	});

	it('removes route', () => {
		this.app = new App();
		var route = new Route('/path', Screen);
		this.app.addRoutes(route);
		expect(this.app.removeRoute(route)).toBe(true);
	});

	it('sadds route from object', () => {
		this.app = new App();
		this.app.addRoutes({
			handler: Screen,
			path: '/path',
		});
		var route = this.app.findRoute('/path');
		expect(this.app.hasRoutes()).toBe(true);
		expect(route).toBeInstanceOf(Route);
		expect(route.getPath()).toBe('/path');
		expect(route.getHandler()).toBe(Screen);
	});

	it('adds route from array', () => {
		this.app = new App();
		this.app.addRoutes([
			{
				handler: Screen,
				path: '/path',
			},
			new Route('/pathOther', Screen),
		]);
		var route = this.app.findRoute('/path');
		expect(this.app.hasRoutes()).toBe(true);
		expect(route).toBeInstanceOf(Route);
		expect(route.getPath()).toBe('/path');
		expect(route.getHandler()).toBe(Screen);
		var routeOther = this.app.findRoute('/pathOther');
		expect(routeOther).toBeInstanceOf(Route);
		expect(routeOther.getPath()).toBe('/pathOther');
		expect(routeOther.getHandler()).toBe(Screen);
	});

	it('does not find route for not registered paths', () => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		expect(this.app.findRoute('/pathOther')).toBeNull();
	});

	it('does not allow navigation for urls with hashbang when navigating to same basepath', () => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		globals.window = {
			location: {
				hash: '',
				host: '',
				hostname: '',
				pathname: '/path',
				search: '',
			},
		};
		expect(this.app.canNavigate('/path#hashbang')).toBe(false);
	});

	it('allows navigation for urls with hashbang when navigating to different basepath', () => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		globals.window = {
			location: {
				hash: '',
				host: '',
				hostname: '',
				pathname: '/path1',
				search: '',
			},
		};
		expect(this.app.canNavigate('/path#hashbang')).toBe(true);
	});

	it('finds route for urls with hashbang for different basepath', () => {
		this.app = new App();
		this.app.addRoutes(new Route('/pathOther', Screen));
		globals.window = {
			location: {
				host: '',
				pathname: '/path',
				search: '',
			},
		};
		expect(this.app.findRoute('/pathOther#hashbang')).toBeTruthy();
	});

	it('finds route for urls ending with or without slash', () => {
		this.app = new App();
		this.app.addRoutes(new Route('/pathOther', Screen));
		globals.window = {
			location: {
				host: '',
				pathname: '/path/',
				search: '',
			},
		};
		expect(this.app.findRoute('/pathOther')).toBeTruthy();
		expect(this.app.findRoute('/pathOther/')).toBeTruthy();
	});

	it('ignores query string on findRoute when ignoreQueryStringFromRoutePath is enabled', () => {
		this.app = new App();
		this.app.setIgnoreQueryStringFromRoutePath(true);
		this.app.addRoutes(new Route('/path', Screen));
		expect(this.app.findRoute('/path?foo=1')).toBeTruthy();
	});

	it('does not ignore query string on findRoute when ignoreQueryStringFromRoutePath is disabled', () => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		expect(this.app.findRoute('/path?foo=1')).toBeFalsy();
	});

	it('adds surface', () => {
		this.app = new App();
		this.app.addSurfaces(new Surface('surfaceId'));
		expect(this.app.surfaces.surfaceId).toBeTruthy();
		expect(this.app.surfaces.surfaceId.getId()).toBe('surfaceId');
	});

	it('adds surface from surface id', () => {
		this.app = new App();
		this.app.addSurfaces('surfaceId');
		expect(this.app.surfaces.surfaceId).toBeTruthy();
		expect(this.app.surfaces.surfaceId.getId()).toBe('surfaceId');
	});

	it('adds surface from array', () => {
		this.app = new App();
		this.app.addSurfaces([new Surface('surfaceId'), 'surfaceIdOther']);
		expect(this.app.surfaces.surfaceId).toBeTruthy();
		expect(this.app.surfaces.surfaceIdOther).toBeTruthy();
		expect(this.app.surfaces.surfaceId.getId()).toBe('surfaceId');
		expect(this.app.surfaces.surfaceIdOther.getId()).toBe('surfaceIdOther');
	});

	it('creates screen instance to a route', () => {
		this.app = new App();
		var screen = this.app.createScreenInstance(
			'/path',
			new Route('/path', Screen)
		);
		expect(screen).toBeInstanceOf(Screen);
	});

	it('creates screen instance to a route for Screen class child', () => {
		this.app = new App();
		var screen = this.app.createScreenInstance(
			'/path',
			new Route('/path', HtmlScreen)
		);
		expect(screen).toBeInstanceOf(HtmlScreen);
	});

	it('creates screen instance to a route with function handler', () => {
		this.app = new App();
		var stub = jest.fn();
		var route = new Route('/path', stub);
		var screen = this.app.createScreenInstance('/path', route);
		expect(stub).toHaveBeenCalledTimes(1);
		expect(stub).toHaveBeenCalledWith(route);
		expect(stub).toHaveReturnedWith(undefined);
		expect(screen).toBeInstanceOf(Screen);
	});

	it('gets same screen instance to a route', () => {
		this.app = new App();
		var route = new Route('/path', Screen);
		var screen = this.app.createScreenInstance('/path', route);
		this.app.screens['/path'] = screen;
		expect(this.app.createScreenInstance('/path', route)).toBe(screen);
	});

	it('uses same screen instance when simulating navigate refresh', () => {
		this.app = new App();
		var route = new Route('/path', HtmlScreen);
		var screen = this.app.createScreenInstance('/path', route);
		this.app.screens['/path'] = screen;
		this.app.activePath = '/path';
		this.app.activeScreen = screen;
		var screenRefresh = this.app.createScreenInstance('/path', route);
		expect(screenRefresh).toBe(screen);
	});

	it('stores screen for path with query string when ignoreQueryStringFromRoutePath is enabled', (done) => {
		class NoCacheScreen extends Screen {
			constructor() {
				super();
			}
		}

		this.app = new App();
		this.app.setIgnoreQueryStringFromRoutePath(true);
		this.app.addRoutes(new Route('/path', NoCacheScreen));

		this.app.navigate('/path?foo=1').then(() => {
			expect(this.app.screens['/path?foo=1']).toBeTruthy();
			done();
		});
	});

	it('creates different screen instance when navigate to same path with different query strings if ignoreQueryStringFromRoutePath is enabled', (done) => {
		class NoCacheScreen extends Screen {
			constructor() {
				super();
			}
		}

		this.app = new App();
		this.app.setIgnoreQueryStringFromRoutePath(true);

		this.app.addRoutes(new Route('/path1', NoCacheScreen));
		this.app.addRoutes(new Route('/path2', NoCacheScreen));

		this.app.navigate('/path1?foo=1').then(() => {
			var screenFirstNavigate = this.app.screens['/path1?foo=1'];
			this.app.navigate('/path2').then(() => {
				this.app.navigate('/path1?foo=2').then(() => {
					expect(this.app.screens['/path1?foo=2']).not.toBe(
						screenFirstNavigate
					);
					done();
				});
			});
		});
	});

	it('creates different screen instance navigate when not cacheable', (done) => {
		class NoCacheScreen extends Screen {
			constructor() {
				super();
				this.cacheable = false;
			}
		}
		this.app = new App();
		this.app.addRoutes(new Route('/path1', NoCacheScreen));
		this.app.addRoutes(new Route('/path2', NoCacheScreen));
		this.app.navigate('/path1').then(() => {
			var screenFirstNavigate = this.app.screens['/path1'];
			this.app.navigate('/path2').then(() => {
				this.app.navigate('/path1').then(() => {
					expect(this.app.screens['/path1']).not.toBe(
						screenFirstNavigate
					);
					done();
				});
			});
		});
	});

	it('uses same screen instance navigate when is cacheable', (done) => {
		class CacheScreen extends Screen {
			constructor() {
				super();
				this.cacheable = true;
			}
		}
		this.app = new App();
		this.app.addRoutes(new Route('/path1', CacheScreen));
		this.app.addRoutes(new Route('/path2', CacheScreen));
		this.app.navigate('/path1').then(() => {
			var screenFirstNavigate = this.app.screens['/path1'];
			this.app.navigate('/path2').then(() => {
				this.app.navigate('/path1').then(() => {
					expect(this.app.screens['/path1']).toBe(
						screenFirstNavigate
					);
					done();
				});
			});
		});
	});

	it('clears screen cache', () => {
		this.app = new App();
		this.app.screens['/path'] = this.app.createScreenInstance(
			'/path',
			new Route('/path', HtmlScreen)
		);
		this.app.clearScreensCache();
		expect(Object.keys(this.app.screens)).toHaveLength(0);
	});

	it('clears all screen caches on app dispose', () => {
		this.app = new App();
		var screen1 = this.app.createScreenInstance(
			'/path1',
			new Route('/path1', HtmlScreen)
		);
		var screen2 = this.app.createScreenInstance(
			'/path2',
			new Route('/path2', HtmlScreen)
		);
		this.app.activePath = '/path1';
		this.app.activeScreen = screen1;
		this.app.screens['/path1'] = screen1;
		this.app.screens['/path2'] = screen2;
		this.app.dispose();
		expect(Object.keys(this.app.screens)).toHaveLength(0);
	});

	it('clears screen cache and remove surfaces', () => {
		this.app = new App();
		var surface = new Surface('surfaceId');
		surface.remove = jest.fn();
		this.app.addSurfaces(surface);
		this.app.screens['/path'] = this.app.createScreenInstance(
			'/path',
			new Route('/path', HtmlScreen)
		);
		this.app.clearScreensCache();
		expect(surface.remove).toHaveBeenCalledTimes(1);
	});

	it('clears screen cache for activeScreen but not remove it', () => {
		this.app = new App();
		this.app.screens['/path'] = this.app.createScreenInstance(
			'/path',
			new Route('/path', HtmlScreen)
		);
		this.app.activePath = '/path';
		this.app.activeScreen = this.app.screens['/path'];
		this.app.clearScreensCache();
		expect(Object.keys(this.app.screens)).toHaveLength(1);
		expect(this.app.activeScreen.getCache()).toBeNull();
	});

	it('does not clear screen cache the being used in a pending navigation', (done) => {
		const event = new EventEmitter();
		class StubScreen extends Screen {
			constructor() {
				super();

				this.cacheable = true;
			}

			flip(surfaces) {
				super.flip(surfaces);
				event.emit('flip');
			}
		}

		const callback = () => {

			//this.app.clearScreensCache();

			expect(Object.keys(this.app.screens)).toHaveLength(1);

			//event.dispose();

			done();
		};

		var route = new Route('/path1', StubScreen);

		this.app = new App();
		this.app.addSurfaces(new Surface('surfaceId'));
		this.app.screens['/path1'] = this.app.createScreenInstance(
			'/path1',
			route
		);
		this.app.addRoutes(route);
		this.app.navigate('/path1');
		event.on('flip', callback);
	});

	it('sets flag to prevent navigate', () => {
		this.app = new App();
		expect(this.app.getAllowPreventNavigate()).toBe(true);
		this.app.setAllowPreventNavigate(false);
		expect(this.app.getAllowPreventNavigate()).toBe(false);
	});

	it('gets default title', () => {
		globals.document.title = 'default';
		this.app = new App();
		expect(this.app.getDefaultTitle()).toBe('default');
		this.app.setDefaultTitle('title');
		expect(this.app.getDefaultTitle()).toBe('title');
	});

	it('gets basepath', () => {
		this.app = new App();
		expect(this.app.getBasePath()).toBe('');
		this.app.setBasePath('/base');
		expect(this.app.getBasePath()).toBe('/base');
	});

	it('gets update scroll position', () => {
		this.app = new App();
		expect(this.app.getUpdateScrollPosition()).toBe(true);
		this.app.setUpdateScrollPosition(false);
		expect(this.app.getUpdateScrollPosition()).toBe(false);
	});

	it('gets loading css class', () => {
		this.app = new App();
		expect(this.app.getLoadingCssClass()).toBe('senna-loading');
		this.app.setLoadingCssClass('');
		expect(this.app.getLoadingCssClass()).toBe('');
	});

	it('gets form selector', () => {
		this.app = new App();
		expect(this.app.getFormSelector()).toBe(
			'form[enctype="multipart/form-data"]:not([data-senna-off])'
		);
		this.app.setFormSelector('');
		expect(this.app.getFormSelector()).toBe('');
	});

	it('gets link selector', () => {
		this.app = new App();
		expect(this.app.getLinkSelector()).toBe(
			'a:not([data-senna-off]):not([target="_blank"])'
		);
		this.app.setLinkSelector('');
		expect(this.app.getLinkSelector()).toBe('');
	});

	it('tests if can navigate to url', () => {
		this.app = new App();
		globals.window = {
			history: {},
			location: {
				host: 'localhost',
				hostname: 'localhost',
				pathname: '/path',
				search: '',
			},
		};
		this.app.setBasePath('/base');
		this.app.addRoutes([
			new Route('/', Screen),
			new Route('/path', Screen),
		]);
		expect(this.app.canNavigate('http://localhost/base/')).toBe(true);
		expect(this.app.canNavigate('http://localhost/base')).toBe(true);
		expect(this.app.canNavigate('http://localhost/base/path')).toBe(true);
		expect(this.app.canNavigate('http://localhost/base/path1')).toBe(false);
		expect(this.app.canNavigate('http://localhost/path')).toBe(false);
		expect(this.app.canNavigate('http://external/path')).toBe(false);
		expect(this.app.canNavigate('tel:+0101010101')).toBe(false);
		expect(this.app.canNavigate('mailto:contact@sennajs.com')).toBe(false);
	});

	it('tests if can navigate to url with base path ending in "/"', () => {
		this.app = new App();
		globals.window = {
			history: {},
			location: {
				host: 'localhost',
				hostname: 'localhost',
				pathname: '/path',
				search: '',
			},
		};
		this.app.setBasePath('/base/');
		this.app.addRoutes([
			new Route('/', Screen),
			new Route('/path', Screen),
		]);
		expect(this.app.canNavigate('http://localhost/base/')).toBe(true);
		expect(this.app.canNavigate('http://localhost/base')).toBe(true);
		expect(this.app.canNavigate('http://localhost/base/path')).toBe(true);
		expect(this.app.canNavigate('http://localhost/base/path1')).toBe(false);
		expect(this.app.canNavigate('http://localhost/path')).toBe(false);
		expect(this.app.canNavigate('http://external/path')).toBe(false);
	});

	it.skip('is able to navigate to route that ends with "/"', () => {
		this.app = new App();
		globals.window = {
			history: {},
			location: {
				host: 'localhost',
				hostname: 'localhost',
				pathname: '/path',
				search: '',
			},
		};
		this.app.addRoutes([
			new Route('/path/', Screen),
			new Route('/path/(\\d+)/', Screen),
		]);
		expect(this.app.canNavigate('http://localhost/path')).toBe(true);
		expect(this.app.canNavigate('http://localhost/path/')).toBe(true);
		expect(this.app.canNavigate('http://localhost/path/123')).toBe(true);
		expect(this.app.canNavigate('http://localhost/path/123/')).toBe(true);
	});

	it('detects a navigation to different port and refresh page', () => {
		this.app = new App();
		globals.window = {
			history: {},
			location: {
				host: 'localhost:8080',
				pathname: '/path',
				search: '',
			},
		};
		this.app.addRoutes([
			new Route('/path/', Screen),
			new Route('/path/(\\d+)/', Screen),
		]);
		expect(this.app.canNavigate('http://localhost:9080/path')).toBe(false);
		expect(this.app.canNavigate('http://localhost:9081/path/')).toBe(false);
		expect(this.app.canNavigate('http://localhost:9082/path/123')).toBe(
			false
		);
		expect(this.app.canNavigate('http://localhost:9083/path/123/')).toBe(
			false
		);
	});

	it('is able to navigate to a path using default protocol port', () => {
		this.app = new App();
		globals.window = {
			history: {},
			location: {
				host: 'localhost',
				pathname: '/path',
				search: '',
			},
		};
		this.app.addRoutes([
			new Route('/path/', Screen),
			new Route('/path/(\\d+)/', Screen),
		]);
		expect(this.app.canNavigate('http://localhost:80/path')).toBe(true);
		expect(this.app.canNavigate('http://localhost:80/path/')).toBe(true);
		expect(this.app.canNavigate('http://localhost:80/path/123')).toBe(true);
		expect(this.app.canNavigate('http://localhost:80/path/123/')).toBe(
			true
		);
	});

	it('stores proper senna state after navigate', (done) => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		this.app.navigate('/path').then(() => {
			const state = globals.window.history.state;
			expect(state.path).toBe('/path');
			expect(state.redirectPath).toBe('/path');
			expect(state.scrollLeft).toBe(0);
			expect(state.scrollTop).toBe(0);
			expect(state.form).toBe(false);
			expect(state.referrer).toBeTruthy();
			expect(state.senna).toBeTruthy();
			done();
		});
	});

	it('emits startNavigate and endNavigate custom event', (done) => {
		var startNavigateStub = jest.fn();
		var endNavigateStub = jest.fn();
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		this.app.on('startNavigate', startNavigateStub);
		this.app.on('endNavigate', endNavigateStub);
		this.app.navigate('/path').then(() => {
			expect(startNavigateStub).toHaveBeenCalledTimes(1);
			expect(startNavigateStub).toHaveBeenCalledWith({
				path: '/path',
				replaceHistory: false,
			});
			expect(endNavigateStub).toHaveBeenCalledTimes(1);
			expect(endNavigateStub).toHaveBeenCalledWith({
				path: '/path',
			});
			done();
		});
	});

	it('emits startNavigate and endNavigate custom event with replace history', (done) => {
		var startNavigateStub = jest.fn();
		var endNavigateStub = jest.fn();
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		this.app.on('startNavigate', startNavigateStub);
		this.app.on('endNavigate', endNavigateStub);
		this.app.navigate('/path', true).then(() => {
			expect(startNavigateStub).toHaveBeenCalledTimes(1);
			expect(startNavigateStub).toHaveBeenCalledWith({
				path: '/path',
				replaceHistory: true,
			});
			expect(endNavigateStub).toHaveBeenCalledTimes(1);
			expect(endNavigateStub).toHaveBeenCalledWith({
				path: '/path',
			});
			done();
		});
	});

	it('overwrites event.path on beforeNavigate custom event', (done) => {
		this.app = new App();
		this.app.addRoutes(new Route('/path1', Screen));
		this.app.on('beforeNavigate', (event) => {
			event.path = '/path1';
		});
		this.app.navigate('/path').then(() => {
			expect(globals.window.location.pathname).toBe('/path1');
			done();
		});
	});

	it('cancles navigate', (done) => {
		var stub = jest.fn();
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		this.app.on('endNavigate', (payload) => {
			expect(payload.error).toBeInstanceOf(Error);
			stub();
		});
		this.app
			.navigate('/path')
			.catch((reason) => {
				expect(reason).toBeInstanceOf(Error);
				expect(stub).toHaveBeenCalledTimes(1);
				done();
			})
			.cancel();
	});

	it.skip('clears pendingNavigate after navigate', (done) => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		this.app.navigate('/path').then(() => {
			expect(this.app.pendingNavigate).toBe(false);
			done();
		});
		expect(this.app.pendingNavigate).toBe(true);
	});

	it.skip('waits for pendingNavigate if navigate to same path', (done) => {
		class CacheScreen extends Screen {
			constructor() {
				super();
				this.cacheable = true;
			}
		}
		this.app = new App();
		this.app.addRoutes(new Route('/path', CacheScreen));
		this.app.navigate('/path').then(() => {
			var pendingNavigate1 = this.app.navigate('/path');
			var pendingNavigate2 = this.app.navigate('/path');
			expect(pendingNavigate1).toBeTruthy();
			expect(pendingNavigate2).toBeTruthy();
			expect(pendingNavigate1).toBe(pendingNavigate2);
			done();
		});
	});

	it.skip('should navigate back when clicking browser back button', (done) => {
		this.app = new App();
		this.app.addRoutes(new Route('/path1', Screen));
		this.app.addRoutes(new Route('/path2', Screen));
		this.app
			.navigate('/path1')
			.then(() => this.app.navigate('/path2'))
			.then(() => {
				var activeScreen = this.app.activeScreen;
				assert.strictEqual('/path2', globals.window.location.pathname);
				this.app.once('endNavigate', () => {
					assert.strictEqual(
						'/path1',
						globals.window.location.pathname
					);
					assert.notStrictEqual(activeScreen, this.app.activeScreen);
					done();
				});
				globals.window.history.back();
			});
	});

	it.skip('should not navigate back on a hash change', (done) => {
		this.app = new App();
		this.app.addRoutes(new Route('/path1', Screen));
		this.app.addRoutes(new Route('/path2', Screen));
		this.app
			.navigate('/path1')
			.then(() => this.app.navigate('/path1#hash'))
			.then(() => {
				var startNavigate = sinon.stub();
				this.app.on('startNavigate', startNavigate);
				dom.once(globals.window, 'popstate', () => {
					assert.strictEqual(0, startNavigate.callCount);
					done();
				});
				globals.window.history.back();
			});
	});

	it.skip('should only call beforeNavigate when waiting for pendingNavigate if navigate to same path', (done) => {
		class CacheScreen extends Screen {
			constructor() {
				super();
				this.cacheable = true;
			}
		}
		this.app = new App();
		this.app.addRoutes(new Route('/path', CacheScreen));
		this.app.navigate('/path').then(() => {
			this.app.navigate('/path');
			var beforeNavigate = sinon.stub();
			var startNavigate = sinon.stub();
			this.app.on('beforeNavigate', beforeNavigate);
			this.app.on('startNavigate', startNavigate);
			this.app.navigate('/path');
			assert.strictEqual(1, beforeNavigate.callCount);
			assert.strictEqual(0, startNavigate.callCount);
			done();
		});
	});

	it.skip('should not wait for pendingNavigate if navigate to different path', (done) => {
		class CacheScreen extends Screen {
			constructor() {
				super();
				this.cacheable = true;
			}
		}
		this.app = new App();
		this.app.addRoutes(new Route('/path1', CacheScreen));
		this.app.addRoutes(new Route('/path2', CacheScreen));
		this.app
			.navigate('/path1')
			.then(() => this.app.navigate('/path2'))
			.then(() => {
				var pendingNavigate1 = this.app.navigate('/path1');
				var pendingNavigate2 = this.app.navigate('/path2');
				assert.ok(pendingNavigate1);
				assert.ok(pendingNavigate2);
				assert.notStrictEqual(pendingNavigate1, pendingNavigate2);
				done();
			});
	});

	it.skip('should simulate refresh on navigate to the same path', (done) => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		this.app.once('startNavigate', (payload) => {
			assert.ok(!payload.replaceHistory);
		});
		this.app.navigate('/path').then(() => {
			this.app.once('startNavigate', (payload) => {
				assert.ok(payload.replaceHistory);
			});
			this.app.navigate('/path').then(() => {
				done();
			});
		});
	});

	it.skip('should add loading css class on navigate', (done) => {
		var containsLoadingCssClass = () => {
			return globals.document.documentElement.classList.contains(
				this.app.getLoadingCssClass()
			);
		};
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		this.app.on('startNavigate', () =>
			assert.ok(containsLoadingCssClass())
		);
		this.app.on('endNavigate', () => {
			assert.ok(!containsLoadingCssClass());
			done();
		});
		this.app
			.navigate('/path')
			.then(() => assert.ok(!containsLoadingCssClass()));
	});

	it.skip('should not remove loading css class on navigate if there is pending navigate', (done) => {
		var containsLoadingCssClass = () => {
			return globals.document.documentElement.classList.contains(
				this.app.getLoadingCssClass()
			);
		};
		this.app = new App();
		this.app.addRoutes(new Route('/path1', Screen));
		this.app.addRoutes(new Route('/path2', Screen));
		this.app.once('startNavigate', () => {
			this.app.once('startNavigate', () =>
				assert.ok(containsLoadingCssClass())
			);
			this.app.once('endNavigate', () =>
				assert.ok(containsLoadingCssClass())
			);
			this.app.navigate('/path2').then(() => {
				assert.ok(!containsLoadingCssClass());
				done();
			});
		});
		this.app.navigate('/path1');
	});

	it.skip('should not navigate to unrouted paths', (done) => {
		this.app = new App();
		this.app.on('endNavigate', (payload) => {
			assert.ok(payload.error instanceof Error);
		});
		this.app.navigate('/path', true).catch((reason) => {
			assert.ok(reason instanceof Error);
			done();
		});
	});

	it.skip('should store scroll position on page scroll', (done) => {
		showPageScrollbar();
		this.app = new App();
		setTimeout(() => {
			assert.strictEqual(100, globals.window.history.state.scrollTop);
			assert.strictEqual(100, globals.window.history.state.scrollLeft);
			hidePageScrollbar();
			done();
		}, 300);
		globals.window.scrollTo(100, 100);
	});

	it.skip('should not store page scroll position during navigate', (done) => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		this.app.on('startNavigate', () => {
			this.app.onScroll_(); // Coverage
			assert.ok(!this.app.captureScrollPositionFromScrollEvent);
		});
		assert.ok(this.app.captureScrollPositionFromScrollEvent);
		this.app.navigate('/path').then(() => {
			assert.ok(this.app.captureScrollPositionFromScrollEvent);
			done();
		});
	});

	it.skip('should update scroll position on navigate', (done) => {
		showPageScrollbar();
		this.app = new App();
		this.app.addRoutes(new Route('/path1', Screen));
		this.app.addRoutes(new Route('/path2', Screen));
		this.app.navigate('/path1').then(() => {
			setTimeout(() => {
				this.app.navigate('/path2').then(() => {
					assert.strictEqual(0, window.pageXOffset);
					assert.strictEqual(0, window.pageYOffset);
					hidePageScrollbar();
					done();
				});
			}, 300);
			globals.window.scrollTo(100, 100);
		});
	});

	it.skip('should not update scroll position on navigate if updateScrollPosition is disabled', (done) => {
		showPageScrollbar();
		this.app = new App();
		this.app.setUpdateScrollPosition(false);
		this.app.addRoutes(new Route('/path1', Screen));
		this.app.addRoutes(new Route('/path2', Screen));
		this.app.navigate('/path1').then(() => {
			setTimeout(() => {
				this.app.navigate('/path2').then(() => {
					assert.strictEqual(100, window.pageXOffset);
					assert.strictEqual(100, window.pageYOffset);
					hidePageScrollbar();
					done();
				});
			}, 100);
			globals.window.scrollTo(100, 100);
		});
	});

	it.skip('should restore scroll position on navigate back', (done) => {
		showPageScrollbar();
		this.app = new App();
		this.app.addRoutes(new Route('/path1', Screen));
		this.app.addRoutes(new Route('/path2', Screen));
		this.app.navigate('/path1').then(() => {
			setTimeout(() => {
				this.app.navigate('/path2').then(() => {
					assert.strictEqual(0, window.pageXOffset);
					assert.strictEqual(0, window.pageYOffset);
					this.app.once('endNavigate', () => {
						assert.strictEqual(100, window.pageXOffset);
						assert.strictEqual(100, window.pageYOffset);
						hidePageScrollbar();
						done();
					});
					globals.window.history.back();
				});
			}, 300);
			globals.window.scrollTo(100, 100);
		});
	});

	it.skip('should dispatch navigate to current path', (done) => {
		globals.window.history.replaceState({}, '', '/path1?foo=1#hash');
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		this.app.on('endNavigate', (payload) => {
			assert.strictEqual('/path1?foo=1#hash', payload.path);
			globals.window.history.replaceState(
				{},
				'',
				utils.getCurrentBrowserPath()
			);
			done();
		});
		this.app.dispatch();
	});

	it.skip('should prevent navigation when beforeDeactivate returns "true"', (done) => {
		class NoNavigateScreen extends Screen {
			beforeDeactivate() {
				return true;
			}
		}
		this.app = new App();
		this.app.addRoutes(new Route('/path1', NoNavigateScreen));
		this.app.addRoutes(new Route('/path2', Screen));
		this.app.navigate('/path1').then(() => {
			this.app.on('endNavigate', (payload) => {
				assert.ok(payload.error instanceof Error);
			});
			this.app.navigate('/path2').catch((reason) => {
				assert.ok(reason instanceof Error);
				done();
			});
		});
	});

	it.skip('should prevent navigation when beforeDeactivate resolves to "true"', (done) => {
		class NoNavigateScreen extends Screen {
			beforeDeactivate() {
				return new Promise((resolve) => {
					resolve(true);
				});
			}
		}
		this.app = new App();
		this.app.addRoutes(new Route('/path1', NoNavigateScreen));
		this.app.addRoutes(new Route('/path2', Screen));
		this.app.navigate('/path1').then(() => {
			this.app.on('endNavigate', (payload) => {
				assert.ok(payload.error instanceof Error);
			});
			this.app.navigate('/path2').catch((reason) => {
				assert.ok(reason instanceof Error);
				done();
			});
		});
	});

	it.skip('should prevent navigation when beforeActivate returns "true"', (done) => {
		class NoNavigateScreen extends Screen {
			beforeActivate() {
				return true;
			}
		}

		this.app = new App();
		this.app.addRoutes(new Route('/path', NoNavigateScreen));
		this.app.on('endNavigate', (payload) => {
			assert.ok(payload.error instanceof Error);
		});
		this.app
			.navigate('/path')
			.then(() => assert.fail())
			.catch((reason) => {
				assert.ok(reason instanceof Error);
				assert.equal(reason.message, 'Cancelled by next screen');

				done();
			});
	});

	it.skip('should prevent navigation when beforeActivate promise resolves to "true"', (done) => {
		class NoNavigateScreen extends Screen {
			beforeActivate() {
				return new Promise((resolve) => {
					resolve(true);
				});
			}
		}

		this.app = new App();
		this.app.addRoutes(new Route('/path', NoNavigateScreen));
		this.app.on('endNavigate', (payload) => {
			assert.ok(payload.error instanceof Error);
		});
		this.app
			.navigate('/path')
			.then(() => assert.fail())
			.catch((reason) => {
				assert.ok(reason instanceof Error);
				assert.equal(reason.message, 'Cancelled by next screen');

				done();
			});
	});

	it.skip('should prefetch paths', (done) => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		this.app.prefetch('/path').then(() => {
			assert.ok(this.app.screens['/path'] instanceof Screen);
			done();
		});
	});

	it.skip('should prefetch fail on navigate to unrouted paths', (done) => {
		this.app = new App();
		this.app.on('endNavigate', (payload) => {
			assert.ok(payload.error instanceof Error);
		});
		this.app.prefetch('/path').catch((reason) => {
			assert.ok(reason instanceof Error);
			done();
		});
	});

	it.skip('should cancel prefetch', (done) => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		this.app.on('endNavigate', (payload) => {
			assert.ok(payload.error instanceof Error);
		});
		this.app
			.prefetch('/path')
			.catch((reason) => {
				assert.ok(reason instanceof Error);
				done();
			})
			.cancel();
	});

	it.skip('should navigate when clicking on routed links', () => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		dom.triggerEvent(enterDocumentLinkElement('/path'), 'click');
		assert.ok(this.app.pendingNavigate);
		exitDocumentLinkElement();
	});

	it.skip('should not navigate when clicking on target blank links', () => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		const link = enterDocumentLinkElement('/path');
		link.setAttribute('target', '_blank');
		link.addEventListener('click', (event) => event.preventDefault());
		dom.triggerEvent(link, 'click');
		exitDocumentLinkElement();
		assert.strictEqual(this.app.pendingNavigate, null);
	});

	it.skip('should pass original event object to "beforeNavigate" when a link is clicked', () => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		this.app.on('beforeNavigate', (data) => {
			assert.ok(data.event);
			assert.equal('click', data.event.type);
		});
		dom.triggerEvent(enterDocumentLinkElement('/path'), 'click');
		exitDocumentLinkElement();

		assert.notEqual('/path', window.location.pathname);
	});

	it.skip('should prevent navigation on both senna and the browser via beforeNavigate', () => {
		this.app = new App();
		this.app.addRoutes(new Route('/preventedPath', Screen));
		this.app.on('beforeNavigate', (data, event) => {
			data.event.preventDefault();
			event.preventDefault();
		});
		dom.triggerEvent(enterDocumentLinkElement('/preventedPath'), 'click');
		exitDocumentLinkElement();

		assert.notEqual('/preventedPath', window.location.pathname);
	});

	it.skip('should not navigate when clicking on external links', () => {
		var link = enterDocumentLinkElement('http://sennajs.com');
		this.app = new App();
		this.app.setAllowPreventNavigate(false);
		dom.on(link, 'click', preventDefault);
		dom.triggerEvent(link, 'click');
		assert.strictEqual(null, this.app.pendingNavigate);
		exitDocumentLinkElement();
	});

	it.skip('should not navigate when clicking on links outside basepath', () => {
		var link = enterDocumentLinkElement('/path');
		this.app = new App();
		this.app.setAllowPreventNavigate(false);
		this.app.setBasePath('/base');
		dom.on(link, 'click', preventDefault);
		dom.triggerEvent(link, 'click');
		assert.strictEqual(null, this.app.pendingNavigate);
		exitDocumentLinkElement();
	});

	it.skip('should not navigate when clicking on unrouted links', () => {
		var link = enterDocumentLinkElement('/path');
		this.app = new App();
		this.app.setAllowPreventNavigate(false);
		dom.on(link, 'click', preventDefault);
		dom.triggerEvent(link, 'click');
		assert.strictEqual(null, this.app.pendingNavigate);
		exitDocumentLinkElement();
	});

	it.skip('should not navigate when clicking on links with invalid mouse button or modifier keys pressed', () => {
		var link = enterDocumentLinkElement('/path');
		this.app = new App();
		this.app.setAllowPreventNavigate(false);
		this.app.addRoutes(new Route('/path', Screen));
		dom.on(link, 'click', preventDefault);
		dom.triggerEvent(link, 'click', {
			altKey: true,
		});
		dom.triggerEvent(link, 'click', {
			ctrlKey: true,
		});
		dom.triggerEvent(link, 'click', {
			metaKey: true,
		});
		dom.triggerEvent(link, 'click', {
			shiftKey: true,
		});
		dom.triggerEvent(link, 'click', {
			button: true,
		});
		assert.strictEqual(null, this.app.pendingNavigate);
		exitDocumentLinkElement();
	});

	it.skip('should not navigate when navigate fails synchronously', () => {
		var link = enterDocumentLinkElement('/path');
		this.app = new App();
		this.app.setAllowPreventNavigate(false);
		this.app.addRoutes(new Route('/path', Screen));
		this.app.navigate = () => {
			throw new Error();
		};
		dom.on(link, 'click', preventDefault);
		dom.triggerEvent(link, 'click');
		assert.strictEqual(null, this.app.pendingNavigate);
		exitDocumentLinkElement();
	});

	it.skip('should reload page on navigate back to a routed page without history state', (done) => {
		this.app = new App();
		this.app.reloadPage = sinon.stub();
		this.app.addRoutes(new Route('/path1', Screen));
		this.app.addRoutes(new Route('/path2', Screen));
		this.app.navigate('/path1').then(() => {
			window.history.replaceState(null, null, null);
			this.app.navigate('/path2').then(() => {
				dom.once(globals.window, 'popstate', () => {
					assert.strictEqual(1, this.app.reloadPage.callCount);
					done();
				});
				globals.window.history.back();
			});
		});
	});

	it.skip('should be able to update referrer when Screen history state returns null', (done) => {
		class NullStateScreen extends Screen {
			beforeUpdateHistoryState() {
				return null;
			}
		}
		this.app = new App();
		this.app.addRoutes(new Route('/path1', NullStateScreen));
		this.app.navigate('/path1').then(() => {
			this.app.navigate('/path1#hash').then(() => {
				dom.once(globals.window, 'popstate', () => {
					assert.strictEqual(
						'/path1',
						utils.getCurrentBrowserPath(document.referrer)
					);
					done();
				});
				globals.window.history.back();
			});
		});
	});

	it.skip('should not reload page on navigate back to a routed page with same path containing hashbang without history state', (done) => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		this.app.reloadPage = sinon.stub();
		this.app.navigate('/path').then(() => {
			globals.window.location.hash = 'hash1';
			window.history.replaceState(null, null, null);
			this.app.navigate('/path').then(() => {
				dom.once(globals.window, 'popstate', () => {
					assert.strictEqual(0, this.app.reloadPage.callCount);
					done();
				});
				globals.window.history.back();
			});
		});
	});

	it.skip('should reload page on navigate back to a routed page with different path containing hashbang without history state', (done) => {
		this.app = new App();
		this.app.addRoutes(new Route('/path1', Screen));
		this.app.addRoutes(new Route('/path2', Screen));
		this.app.reloadPage = sinon.stub();
		this.app.navigate('/path1').then(() => {
			globals.window.location.hash = 'hash1';
			window.history.replaceState(null, null, null);
			this.app.navigate('/path2').then(() => {
				dom.once(globals.window, 'popstate', () => {
					assert.strictEqual(1, this.app.reloadPage.callCount);
					done();
				});
				globals.window.history.back();
			});
		});
	});

	it.skip('should not reload page on clicking links with same path containing different hashbang without history state', (done) => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		this.app.reloadPage = sinon.stub();
		window.history.replaceState(null, null, '/path#hash1');
		dom.once(globals.window, 'popstate', () => {
			assert.strictEqual(0, this.app.reloadPage.callCount);
			done();
		});
		dom.triggerEvent(globals.window, 'popstate');
	});

	it.skip('should not navigate on clicking links when onbeforeunload returns truthy value', () => {
		const beforeunload = sinon.spy();
		window.onbeforeunload = beforeunload;
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		const link = enterDocumentLinkElement('/path');
		dom.triggerEvent(link, 'click');
		exitDocumentLinkElement();
		assert.strictEqual(1, beforeunload.callCount);
	});

	it.skip('should not navigate back to the previous page on navigate back when onbeforeunload returns a truthy value', (done) => {
		const beforeunload = sinon.spy();
		window.onbeforeunload = beforeunload;
		this.app = new App();
		this.app.addRoutes(new Route('/path1', Screen));
		this.app.addRoutes(new Route('/path2', Screen));
		this.app.navigate('/path1').then(() => {
			this.app.navigate('/path2').then(() => {
				globals.window.history.back();

				// assumes that the path must remain the same

				assert.strictEqual('/path2', this.app.activePath);
				assert.strictEqual(1, beforeunload.callCount);
				done();
			});
		});
	});

	it.skip('should resposition scroll to hashed anchors on hash popstate', (done) => {
		showPageScrollbar();
		var link = enterDocumentLinkElement('/path');
		link.style.position = 'absolute';
		link.style.top = '1000px';
		link.style.left = '1000px';
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		this.app.navigate('/path').then(() => {
			globals.window.location.hash = 'link';
			window.history.replaceState(null, null, null);
			globals.window.location.hash = 'other';
			window.history.replaceState(null, null, null);
			dom.once(globals.window, 'popstate', () => {
				assert.strictEqual(1000, window.pageXOffset);
				assert.strictEqual(1000, window.pageYOffset);
				exitDocumentLinkElement();
				hidePageScrollbar();

				dom.once(globals.window, 'popstate', () => {
					done();
				});
				globals.window.history.back();
			});
			globals.window.history.back();
		});
	});

	it.skip('should navigate when submitting routed forms', () => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		const form = enterDocumentFormElement('/path', 'post');
		dom.triggerEvent(form, 'submit');
		assert.ok(this.app.pendingNavigate);

		return this.app.on('endNavigate', () => {
			exitDocument(form);
		});
	});

	it.skip('should not navigate when submitting routed forms if submit event was prevented', () => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		var form = enterDocumentFormElement('/path', 'post');

		return new Promise((resolve, reject) => {
			dom.once(form, 'submit', (event) => {
				event.preventDefault();
				assert.ok(!this.app.pendingNavigate);
				resolve();
			});
			dom.triggerEvent(form, 'submit');
		}).thenAlways(() => {
			exitDocument(form);
		});
	});

	it.skip('should not capture form element when submit event was prevented', () => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		var form = enterDocumentFormElement('/path', 'post');

		return new Promise((resolve, reject) => {
			dom.once(form, 'submit', (event) => {
				event.preventDefault();
				assert.ok(!globals.capturedFormElement);
				resolve();
			});
			dom.triggerEvent(form, 'submit');
		}).thenAlways(() => {
			exitDocument(form);
		});
	});

	it.skip('should expose form reference in event data when submitting routed forms', (done) => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		const form = enterDocumentFormElement('/path', 'post');
		dom.triggerEvent(form, 'submit');
		this.app.on('startNavigate', (data) => {
			assert.ok(data.form);
		});
		this.app.on('endNavigate', (data) => {
			assert.ok(data.form);
			exitDocument(form);
			done();
		});
	});

	it.skip('should not navigate when submitting forms with method get', () => {
		var form = enterDocumentFormElement('/path', 'get');
		this.app = new App();
		this.app.setAllowPreventNavigate(false);
		this.app.addRoutes(new Route('/path', Screen));
		dom.on(form, 'submit', preventDefault);
		dom.triggerEvent(form, 'submit');
		assert.strictEqual(null, this.app.pendingNavigate);
		exitDocument(form);
	});

	it.skip('should not navigate when submitting on external forms', () => {
		var form = enterDocumentFormElement('http://sennajs.com', 'post');
		this.app = new App();
		this.app.setAllowPreventNavigate(false);
		dom.on(form, 'submit', preventDefault);
		dom.triggerEvent(form, 'submit');
		assert.strictEqual(null, this.app.pendingNavigate);
		exitDocument(form);
	});

	it.skip('should not navigate when submitting on forms outside basepath', () => {
		var form = enterDocumentFormElement('/path', 'post');
		this.app = new App();
		this.app.setAllowPreventNavigate(false);
		this.app.setBasePath('/base');
		dom.on(form, 'submit', preventDefault);
		dom.triggerEvent(form, 'submit');
		assert.strictEqual(null, this.app.pendingNavigate);
		exitDocument(form);
	});

	it.skip('should not navigate when submitting on unrouted forms', () => {
		var form = enterDocumentFormElement('/path', 'post');
		this.app = new App();
		this.app.setAllowPreventNavigate(false);
		dom.on(form, 'submit', preventDefault);
		dom.triggerEvent(form, 'submit');
		assert.strictEqual(null, this.app.pendingNavigate);
		exitDocument(form);
	});

	it.skip('should not capture form if navigate fails when submitting forms', () => {
		var form = enterDocumentFormElement('/path', 'post');
		this.app = new App();
		this.app.setAllowPreventNavigate(false);
		dom.on(form, 'submit', preventDefault);
		dom.triggerEvent(form, 'submit');
		assert.ok(!globals.capturedFormElement);
		exitDocument(form);
	});

	it.skip('should capture form on beforeNavigate', (done) => {
		const form = enterDocumentFormElement('/path', 'post');
		this.app = new App();
		this.app.setAllowPreventNavigate(false);
		this.app.addRoutes(new Route('/path', Screen));
		this.app.on('beforeNavigate', (event) => {
			assert.ok(event.form);
			exitDocument(form);
			globals.capturedFormElement = null;
			done();
		});
		dom.on(form, 'submit', sinon.stub());
		dom.triggerEvent(form, 'submit');
		assert.ok(globals.capturedFormElement);
	});

	it.skip('should capture form button when submitting', () => {
		const form = enterDocumentFormElement('/path', 'post');
		const button = globals.document.createElement('button');
		form.appendChild(button);
		this.app = new App();
		this.app.setAllowPreventNavigate(false);
		this.app.addRoutes(new Route('/path', StubScreen));

		return new Promise((resolve, reject) => {
			this.app.on('beforeNavigate', (event) => {
				assert.ok(globals.capturedFormButtonElement);
				resolve();
			});

			dom.triggerEvent(form, 'submit');
		}).thenAlways(() => {
			exitDocument(form);
			globals.capturedFormElement = null;
			globals.capturedFormButtonElement = null;
		});
	});

	it.skip('should capture form button when clicking submit button', () => {
		const form = enterDocumentFormElement('/path', 'post');
		const button = globals.document.createElement('button');
		button.type = 'submit';
		button.tabindex = 1;
		form.appendChild(button);
		this.app = new App();
		this.app.setAllowPreventNavigate(false);
		this.app.addRoutes(new Route('/path', Screen));
		button.click();
		assert.ok(globals.capturedFormButtonElement);
		globals.capturedFormButtonElement = null;
		exitDocument(form);
	});

	it.skip('should set redirect path if history path was redirected', (done) => {
		class RedirectScreen extends Screen {
			beforeUpdateHistoryPath() {
				return '/redirect';
			}
		}
		this.app = new App();
		this.app.addRoutes(new Route('/path', RedirectScreen));
		this.app.navigate('/path').then(() => {
			assert.strictEqual('/redirect', this.app.redirectPath);
			assert.strictEqual('/path', this.app.activePath);
			done();
		});
	});

	it.skip('should update the state with the redirected path', (done) => {
		class RedirectScreen extends Screen {
			beforeUpdateHistoryPath() {
				return '/redirect';
			}
		}
		this.app = new App();
		this.app.addRoutes(new Route('/path', RedirectScreen));
		this.app.navigate('/path').then(() => {
			assert.strictEqual('/redirect', globals.window.location.pathname);
			done();
		});
	});

	it.skip('should restore hashbang if redirect path is the same as requested path', (done) => {
		class RedirectScreen extends Screen {
			beforeUpdateHistoryPath() {
				return '/path';
			}
		}
		this.app = new App();
		this.app.addRoutes(new Route('/path', RedirectScreen));
		this.app.navigate('/path#hash').then(() => {
			assert.strictEqual('/path#hash', utils.getCurrentBrowserPath());
			done();
		});
	});

	it.skip('should not restore hashbang if redirect path is not the same as requested path', (done) => {
		class RedirectScreen extends Screen {
			beforeUpdateHistoryPath() {
				return '/redirect';
			}
		}
		this.app = new App();
		this.app.addRoutes(new Route('/path', RedirectScreen));
		this.app.navigate('/path#hash').then(() => {
			assert.strictEqual('/redirect', utils.getCurrentBrowserPath());
			done();
		});
	});

	it.skip('should skipLoadPopstate before page is loaded', (done) => {
		this.app = new App();
		this.app.onLoad_(); // Simulate
		assert.ok(this.app.skipLoadPopstate);
		setTimeout(() => {
			assert.ok(!this.app.skipLoadPopstate);
			done();
		}, 0);
	});

	it.skip('should respect screen lifecycle on navigate', () => {
		class StubScreen2 extends Screen {}
		StubScreen2.prototype.activate = sinon.spy();
		StubScreen2.prototype.beforeDeactivate = sinon.spy();
		StubScreen2.prototype.deactivate = sinon.spy();
		StubScreen2.prototype.flip = sinon.spy();
		StubScreen2.prototype.load = sinon.stub().returns(Promise.resolve());
		StubScreen2.prototype.evaluateStyles = sinon.spy();
		StubScreen2.prototype.evaluateScripts = sinon.spy();
		this.app = new App();
		this.app.addRoutes(new Route('/path1', StubScreen));
		this.app.addRoutes(new Route('/path2', StubScreen2));

		return this.app.navigate('/path1').then(() => {
			this.app.navigate('/path2').then(() => {
				var lifecycleOrder = [
					StubScreen.prototype.load,
					StubScreen.prototype.evaluateStyles,
					StubScreen.prototype.flip,
					StubScreen.prototype.evaluateScripts,
					StubScreen.prototype.activate,
					StubScreen.prototype.beforeDeactivate,
					StubScreen2.prototype.load,
					StubScreen.prototype.deactivate,
					StubScreen2.prototype.evaluateStyles,
					StubScreen2.prototype.flip,
					StubScreen2.prototype.evaluateScripts,
					StubScreen2.prototype.activate,
					StubScreen.prototype.disposeInternal,
				];
				for (var i = 1; i < lifecycleOrder.length - 1; i++) {
					assert.ok(
						lifecycleOrder[i - 1].calledBefore(lifecycleOrder[i])
					);
				}
			});
		});
	});

	it.skip('should render surfaces', (done) => {
		class ContentScreen extends Screen {
			getSurfaceContent(surfaceId) {
				return surfaceId;
			}
			getId() {
				return 'screenId';
			}
		}
		var surface = new Surface('surfaceId');
		surface.addContent = sinon.stub();
		this.app = new App();
		this.app.addRoutes(new Route('/path', ContentScreen));
		this.app.addSurfaces(surface);
		this.app.navigate('/path').then(() => {
			assert.strictEqual(1, surface.addContent.callCount);
			assert.strictEqual('screenId', surface.addContent.args[0][0]);
			assert.strictEqual('surfaceId', surface.addContent.args[0][1]);
			done();
		});
	});

	it.skip('should pass extracted params to "getSurfaceContent"', (done) => {
		var screen;
		class ContentScreen extends Screen {
			constructor() {
				super();
				screen = this;
			}

			getId() {
				return 'screenId';
			}
		}
		ContentScreen.prototype.getSurfaceContent = sinon.stub();

		var surface = new Surface('surfaceId');
		this.app = new App();
		this.app.addRoutes(new Route('/path/:foo(\\d+)/:bar', ContentScreen));
		this.app.addSurfaces(surface);
		this.app.navigate('/path/123/abc').then(() => {
			assert.strictEqual(1, screen.getSurfaceContent.callCount);
			assert.strictEqual(
				'surfaceId',
				screen.getSurfaceContent.args[0][0]
			);

			var expectedParams = {
				foo: '123',
				bar: 'abc',
			};
			assert.deepEqual(
				expectedParams,
				screen.getSurfaceContent.args[0][1]
			);
			done();
		});
	});

	it.skip('should pass extracted params to "getSurfaceContent" with base path', (done) => {
		var screen;
		class ContentScreen extends Screen {
			constructor() {
				super();
				screen = this;
			}

			getId() {
				return 'screenId';
			}
		}
		ContentScreen.prototype.getSurfaceContent = sinon.stub();

		var surface = new Surface('surfaceId');
		this.app = new App();
		this.app.setBasePath('/path');
		this.app.addRoutes(new Route('/:foo(\\d+)/:bar', ContentScreen));
		this.app.addSurfaces(surface);
		this.app.navigate('/path/123/abc').then(() => {
			assert.strictEqual(1, screen.getSurfaceContent.callCount);
			assert.strictEqual(
				'surfaceId',
				screen.getSurfaceContent.args[0][0]
			);

			var expectedParams = {
				foo: '123',
				bar: 'abc',
			};
			assert.deepEqual(
				expectedParams,
				screen.getSurfaceContent.args[0][1]
			);
			done();
		});
	});

	it.skip('should extract params for the given route and path', () => {
		this.app = new App();
		this.app.setBasePath('/path');
		var route = new Route('/:foo(\\d+)/:bar', () => {});
		var params = this.app.extractParams(route, '/path/123/abc');
		var expectedParams = {
			foo: '123',
			bar: 'abc',
		};
		assert.deepEqual(expectedParams, params);
	});

	it.skip('should render default surface content when not provided by screen', (done) => {
		class ContentScreen1 extends Screen {
			getSurfaceContent(surfaceId) {
				if (surfaceId === 'surfaceId1') {
					return 'content1';
				}
			}
			getId() {
				return 'screenId1';
			}
		}
		class ContentScreen2 extends Screen {
			getSurfaceContent(surfaceId) {
				if (surfaceId === 'surfaceId2') {
					return 'content2';
				}
			}
			getId() {
				return 'screenId2';
			}
		}
		dom.enterDocument(
			'<div id="surfaceId1"><div id="surfaceId1-default">default1</div></div>'
		);
		dom.enterDocument(
			'<div id="surfaceId2"><div id="surfaceId2-default">default2</div></div>'
		);
		var surface1 = new Surface('surfaceId1');
		var surface2 = new Surface('surfaceId2');
		surface1.addContent = sinon.stub();
		surface2.addContent = sinon.stub();
		this.app = new App();
		this.app.addRoutes(new Route('/path1', ContentScreen1));
		this.app.addRoutes(new Route('/path2', ContentScreen2));
		this.app.addSurfaces([surface1, surface2]);
		this.app.navigate('/path1').then(() => {
			assert.strictEqual(1, surface1.addContent.callCount);
			assert.strictEqual('screenId1', surface1.addContent.args[0][0]);
			assert.strictEqual('content1', surface1.addContent.args[0][1]);
			assert.strictEqual(1, surface2.addContent.callCount);
			assert.strictEqual('screenId1', surface2.addContent.args[0][0]);
			assert.strictEqual(undefined, surface2.addContent.args[0][1]);
			assert.strictEqual(
				'default2',
				surface2.getChild('default').innerHTML
			);
			this.app.navigate('/path2').then(() => {
				assert.strictEqual(2, surface1.addContent.callCount);
				assert.strictEqual('screenId2', surface1.addContent.args[1][0]);
				assert.strictEqual(undefined, surface1.addContent.args[1][1]);
				assert.strictEqual(
					'default1',
					surface1.getChild('default').innerHTML
				);
				assert.strictEqual(2, surface2.addContent.callCount);
				assert.strictEqual('screenId2', surface2.addContent.args[1][0]);
				assert.strictEqual('content2', surface2.addContent.args[1][1]);
				dom.exitDocument(surface1.getElement());
				dom.exitDocument(surface2.getElement());
				done();
			});
		});
	});

	it.skip('should add surface content after history path is updated', (done) => {
		var surface = new Surface('surfaceId');
		surface.addContent = () => {
			assert.strictEqual('/path', globals.window.location.pathname);
		};
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		this.app.addSurfaces(surface);
		this.app.navigate('/path').then(() => {
			done();
		});
	});

	it.skip('should not navigate when HTML5 History api is not supported', () => {
		var original = utils.isHtml5HistorySupported;
		assert.throws(() => {
			this.app = new App();
			this.app.addRoutes(new Route('/path', Screen));
			utils.isHtml5HistorySupported = () => {
				return false;
			};
			this.app.navigate('/path');
		}, Error);
		utils.isHtml5HistorySupported = original;
	});

	it.skip('should navigate cancelling navigation to multiple paths after navigation is scheduled to keep only the last one', (done) => {
		const app = (this.app = new App());

		class TestScreen extends Screen {
			evaluateStyles(surfaces) {
				dom.triggerEvent(enterDocumentLinkElement('/path2'), 'click');
				exitDocumentLinkElement();

				return super.evaluateStyles(surfaces);
			}

			evaluateScripts(surfaces) {
				assert.ok(app.scheduledNavigationEvent);

				return super.evaluateScripts(surfaces);
			}
		}

		class TestScreen2 extends Screen {
			evaluateStyles(surfaces) {
				dom.triggerEvent(enterDocumentLinkElement('/path3'), 'click');
				exitDocumentLinkElement();

				return super.evaluateStyles(surfaces);
			}

			evaluateScripts(surfaces) {
				assert.ok(app.scheduledNavigationEvent);

				return super.evaluateScripts(surfaces);
			}
		}

		this.app.addRoutes(new Route('/path1', TestScreen));
		this.app.addRoutes(new Route('/path2', TestScreen2));
		this.app.addRoutes(new Route('/path3', TestScreen2));

		this.app.navigate('/path1');

		this.app.on('endNavigate', (event) => {
			if (event.path === '/path3') {
				assert.ok(!this.app.scheduledNavigationEvent);
				assert.strictEqual(globals.window.location.pathname, '/path3');
				done();
			}
		});
	});

	it.skip('should navigate cancelling navigation to multiple paths when navigation strategy is setted up to be immediate', (done) => {
		this.app = new App();

		class TestScreen extends Screen {
			load(path) {
				dom.triggerEvent(enterDocumentLinkElement('/path2'), 'click');
				exitDocumentLinkElement();

				return super.load(path);
			}
		}

		class TestScreen2 extends Screen {
			load(path) {
				dom.triggerEvent(enterDocumentLinkElement('/path3'), 'click');
				exitDocumentLinkElement();

				return super.load(path);
			}
		}

		this.app.addRoutes(new Route('/path1', TestScreen));
		this.app.addRoutes(new Route('/path2', TestScreen2));
		this.app.addRoutes(new Route('/path3', TestScreen2));

		this.app.navigate('/path1');

		assert.ok(!this.app.scheduledNavigationEvent);

		this.app.on('endNavigate', (event) => {
			if (event.path === '/path3') {
				assert.ok(!this.app.scheduledNavigationEvent);
				assert.strictEqual(globals.window.location.pathname, '/path3');
				done();
			}
		});
	});

	it.skip('should set document title from screen title', (done) => {
		class TitledScreen extends Screen {
			getTitle() {
				return 'title';
			}
		}
		this.app = new App();
		this.app.addRoutes(new Route('/path', TitledScreen));
		this.app.navigate('/path').then(() => {
			assert.strictEqual('title', globals.document.title);
			done();
		});
	});

	it.skip('should set globals.capturedFormElement to null after navigate', (done) => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', Screen));
		this.app.navigate('/path').then(() => {
			assert.strictEqual(null, globals.capturedFormElement);
			done();
		});
	});

	it.skip('should cancel nested promises on canceled navigate', (done) => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', HtmlScreen));
		this.app
			.navigate('/path')
			.then(() => assert.fail())
			.catch(() => {
				assert.equal(this.requests.length, 0);
				done();
			})
			.cancel();
	});

	it.skip('should cancel nested promises on canceled prefetch', (done) => {
		this.app = new App();
		this.app.addRoutes(new Route('/path', HtmlScreen));
		this.app
			.prefetch('/path')
			.then(() => assert.fail())
			.catch(() => {
				assert.ok(this.requests[0].aborted);
				done();
			})
			.cancel();
	});

	it.skip('should wait for pendingNavigate before removing screen on double back navigation', (done) => {
		class CacheScreen extends Screen {
			constructor() {
				super();
				this.cacheable = true;
			}

			load() {
				return new Promise((resolve) => setTimeout(resolve, 100));
			}
		}

		var app = new App();
		this.app = app;
		app.addRoutes(new Route('/path1', CacheScreen));
		app.addRoutes(new Route('/path2', CacheScreen));
		app.addRoutes(new Route('/path3', CacheScreen));

		app.navigate('/path1')
			.then(() => app.navigate('/path2'))
			.then(() => app.navigate('/path3'))
			.then(() => {
				var pendingNavigate;
				app.on('startNavigate', () => {
					pendingNavigate = app.pendingNavigate;
					assert.ok(app.screens['/path2']);
				});
				app.once('endNavigate', () => {
					if (app.isNavigationPending) {
						assert.ok(!app.screens['/path2']);
						done();
					}
					else {
						pendingNavigate.thenAlways(() => {
							assert.ok(!app.screens['/path2']);
							done();
						});
						pendingNavigate.cancel();
					}
				});
				globals.window.history.go(-1);
				setTimeout(() => globals.window.history.go(-1), 50);
			});
	});

	it('scrolls to anchor element on navigate', (done) => {
		const parentNode = dom.enterDocument(
			'<div style="position:absolute;top:400px;"><div id="surfaceId1" style="position:relative;top:400px"></div></div>'
		);
		this.app = new App();
		this.app.addRoutes(new Route('/path1', Screen));
		this.app.addSurfaces(['surfaceId1']);
		this.app.navigate('/path1#surfaceId1').then(() => {
			const surfaceNode = document.querySelector('#surfaceId1');
			const {offsetLeft, offsetTop} = utils.getNodeOffset(surfaceNode);
			expect(window.pageYOffset).toBe(offsetTop);
			expect(window.pageXOffset).toBe(offsetLeft);
			dom.exitDocument(parentNode);
			done();
		});
	});

	it('updates the document.referrer upon navigation', (done) => {
		this.app = new App();
		this.app.addRoutes(new Route('/path1', Screen));
		this.app.addRoutes(new Route('/path2', Screen));
		this.app.addRoutes(new Route('/path3', Screen));

		this.app
			.navigate('/path1')
			.then(() => {
				return this.app.navigate('/path2');
			})
			.then(() => {
				expect(
					utils.getUrlPathWithoutHash(globals.document.referrer)
				).toBe('/path1');

				return this.app.navigate('/path3');
			})
			.then(() => {
				expect(
					utils.getUrlPathWithoutHash(globals.document.referrer)
				).toBe('/path2');
				this.app.on(
					'endNavigate',
					() => {
						expect(
							utils.getUrlPathWithoutHash(
								globals.document.referrer
							)
						).toBe('/path1');
						done();
					},
					true
				);
				globals.window.history.back();
			});
	});

	it('does reload page on navigate back to a routed page without history state and skipLoadPopstate is active', () => {
		this.app = new App();
		this.app.reloadPage = jest.fn();
		this.app.addRoutes(new Route('/path1', Screen));
		this.app.addRoutes(new Route('/path2', Screen));

		return this.app.navigate('/path1').then(() => {
			window.history.replaceState(null, null, null);

			return this.app.navigate('/path2').then(() => {
				return new Promise((resolve, reject) => {
					dom.once(globals.window, 'popstate', () => {
						expect(this.app.reloadPage).not.toHaveBeenCalled();
						resolve();
					});
					this.app.skipLoadPopstate = true;
					globals.window.history.back();
				});
			});
		});
	});
});

function enterDocumentLinkElement(href) {
	dom.enterDocument('<a id="link" href="' + href + '">link</a>');

	return document.getElementById('link');
}

function enterDocumentFormElement(action, method) {
	const random = Math.floor(Math.random() * 10000);
	dom.enterDocument(
		`<form id="form_${random}" action="${action}" method="${method}" enctype="multipart/form-data"></form>`
	);

	return document.getElementById(`form_${random}`);
}

function exitDocumentLinkElement() {
	dom.exitDocument(document.getElementById('link'));
}

function preventDefault(event) {
	event.preventDefault();
}

function showPageScrollbar() {
	globals.document.documentElement.style.height = '9999px';
	globals.document.documentElement.style.width = '9999px';
}

function hidePageScrollbar() {
	globals.document.documentElement.style.height = '';
	globals.document.documentElement.style.width = '';
}
