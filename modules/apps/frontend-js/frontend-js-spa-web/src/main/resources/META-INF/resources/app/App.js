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

import {debounce, delegate} from 'frontend-js-web';

import EventEmitter from '../events/EventEmitter';
import EventHandler from '../events/EventHandler';
import globals from '../globals/globals';
import Route from '../route/Route';
import Screen from '../screen/Screen';
import Surface from '../surface/Surface';
import utils, {
	getCurrentBrowserPath,
	getCurrentBrowserPathWithoutHash,
	getNodeOffset,
	getUrlPath,
	getUrlPathWithoutHash,
	getUrlPathWithoutHashAndSearch,
	log,
	removePathTrailingSlash,
	setReferrer,
} from '../util/utils';

const NavigationStrategy = {
	IMMEDIATE: 'immediate',
	SCHEDULE_LAST: 'scheduleLast',
};

class App extends EventEmitter {

	/**
	 * App class that handle routes and screens lifecycle.
	 */
	constructor() {
		super();

		/**
		 * Holds the active screen.
		 * @type {?Screen}
		 * @protected
		 */
		this.activeScreen = null;

		/**
		 * Holds the active path containing the query parameters.
		 * @type {?string}
		 * @protected
		 */
		this.activePath = null;

		/**
		 * Allows prevent navigate from dom prevented event.
		 * @type {boolean}
		 * @default true
		 * @protected
		 */
		this.allowPreventNavigate = true;

		/**
		 * Holds link base path.
		 * @type {!string}
		 * @default ''
		 * @protected
		 */
		this.basePath = '';

		/**
		 * Holds the value of the browser path before a navigation is performed.
		 * @type {!string}
		 * @default the current browser path.
		 * @protected
		 */
		this.browserPathBeforeNavigate = utils.getCurrentBrowserPathWithoutHash();

		/**
		 * Captures scroll position from scroll event.
		 * @type {!boolean}
		 * @default true
		 * @protected
		 */
		this.captureScrollPositionFromScrollEvent = true;

		/**
		 * Holds the default page title.
		 * @type {string}
		 * @default null
		 * @protected
		 */
		this.defaultTitle = globals.document.title;

		/**
		 * Holds the form selector to define forms that are routed.
		 * @type {!string}
		 * @default form[enctype="multipart/form-data"]:not([data-senna-off])
		 * @protected
		 */
		this.formSelector =
			'form[enctype="multipart/form-data"]:not([data-senna-off])';

		/**
		 * When enabled, the route matching ignores query string from the path.
		 * @type {boolean}
		 * @default false
		 * @protected
		 */
		this.ignoreQueryStringFromRoutePath = false;

		/**
		 * Holds the link selector to define links that are routed.
		 * @type {!string}
		 * @default a:not([data-senna-off])
		 * @protected
		 */
		this.linkSelector = 'a:not([data-senna-off]):not([target="_blank"])';

		/**
		 * Holds the loading css class.
		 * @type {!string}
		 * @default senna-loading
		 * @protected
		 */
		this.loadingCssClass = 'senna-loading';

		/**
		 * Using the History API to manage your URLs is awesome and, as it happens,
		 * a crucial feature of good web apps. One of its downsides, however, is
		 * that scroll positions are stored and then, more importantly, restored
		 * whenever you traverse the history. This often means unsightly jumps as
		 * the scroll position changes automatically, and especially so if your app
		 * does transitions, or changes the contents of the page in any way.
		 * Ultimately this leads to an horrible user experience. The good news is,
		 * however, that there's a potential fix: history.scrollRestoration.
		 * https://developers.google.com/web/updates/2015/09/history-api-scroll-restoration
		 * @type {boolean}
		 * @protected
		 */
		this.nativeScrollRestorationSupported =
			'scrollRestoration' in globals.window.history;

		/**
		 * When set to NavigationStrategy.SCHEDULE_LAST means that the current navigation
		 * cannot be Cancelled to start another and will be queued in
		 * scheduledNavigationQueue. When NavigationStrategy.IMMEDIATE means that all
		 * navigation will be cancelled to start another.
		 * @type {!string}
		 * @default immediate
		 * @protected
		 */
		this.navigationStrategy = NavigationStrategy.IMMEDIATE;

		/**
		 * When set to true there is a pendingNavigate that has not yet been
		 * resolved or rejected.
		 * @type {boolean}
		 * @default false
		 * @protected
		 */
		this.isNavigationPending = false;

		/**
		 * Holds a deferred with the current navigation.
		 * @type {?Promise}
		 * @default null
		 * @protected
		 */
		this.pendingNavigate = null;

		/**
		 * Holds the window horizontal scroll position when the navigation using
		 * back or forward happens to be restored after the surfaces are updated.
		 * @type {!Number}
		 * @default 0
		 * @protected
		 */
		this.popstateScrollLeft = 0;

		/**
		 * Holds the window vertical scroll position when the navigation using
		 * back or forward happens to be restored after the surfaces are updated.
		 * @type {!Number}
		 * @default 0
		 * @protected
		 */
		this.popstateScrollTop = 0;

		/**
		 * Holds the redirect path containing the query parameters.
		 * @type {?string}
		 * @protected
		 */
		this.redirectPath = null;

		/**
		 * Holds the screen routes configuration.
		 * @type {?Array}
		 * @default []
		 * @protected
		 */
		this.routes = [];

		/**
		 * Holds a queue that stores every DOM event that can initiate a navigation.
		 * @type {!Event}
		 * @default []
		 * @protected
		 */
		this.scheduledNavigationQueue = [];

		/**
		 * Maps the screen instances by the url containing the parameters.
		 * @type {?Object}
		 * @default {}
		 * @protected
		 */
		this.screens = {};

		/**
		 * When set to true the first erroneous popstate fired on page load will be
		 * ignored, only if <code>globals.window.history.state</code> is also
		 * <code>null</code>.
		 * @type {boolean}
		 * @default false
		 * @protected
		 */
		this.skipLoadPopstate = false;

		/**
		 * Maps that index the surfaces instances by the surface id.
		 * @type {?Object}
		 * @default {}
		 * @protected
		 */
		this.surfaces = {};

		/**
		 * When set to true, moves the scroll position after popstate, or to the
		 * top of the viewport for new navigation. If false, the browser will
		 * take care of scroll restoration.
		 * @type {!boolean}
		 * @default true
		 * @protected
		 */
		this.updateScrollPosition = true;

		this.appEventHandlers_ = new EventHandler();

		this.appEventHandlers_.add(
			this.addDOMEventListener(
				globals.window,
				'scroll',
				debounce(this.onScroll_.bind(this), 100)
			),
			this.addDOMEventListener(
				globals.window,
				'load',
				this.onLoad_.bind(this)
			),
			this.addDOMEventListener(
				globals.window,
				'popstate',
				this.onPopstate_.bind(this)
			)
		);

		this.on('startNavigate', this.onStartNavigate_);
		this.on('beforeNavigate', this.onBeforeNavigate_);
		this.on('beforeNavigate', this.onBeforeNavigateDefault_, true);
		this.on('beforeUnload', this.onBeforeUnloadDefault_);

		this.setLinkSelector(this.linkSelector);
		this.setFormSelector(this.formSelector);

		this.maybeOverloadBeforeUnload_();
	}

	addDOMEventListener(element, eventName, callback) {
		element.addEventListener(eventName, callback);

		return {
			removeListener() {
				element.removeEventListener(eventName, callback);
			},
		};
	}

	/**
	 * Adds one or more screens to the application.
	 *
	 * Example:
	 *
	 * <code>
	 *   app.addRoutes({ path: '/foo', handler: FooScreen });
	 *   or
	 *   app.addRoutes([{ path: '/foo', handler: function(route) { return new FooScreen(); } }]);
	 * </code>
	 *
	 * @param {Object} or {Array} routes Single object or an array of object.
	 *     Each object should contain <code>path</code> and <code>screen</code>.
	 *     The <code>path</code> should be a string or a regex that maps the
	 *     navigation route to a screen class definition (not an instance), e.g:
	 *         <code>{ path: "/home:param1", handler: MyScreen }</code>
	 *         <code>{ path: /foo.+/, handler: MyScreen }</code>
	 * @chainable
	 */
	addRoutes(routes) {
		if (!Array.isArray(routes)) {
			routes = [routes];
		}
		routes.forEach((route) => {
			if (!(route instanceof Route)) {
				route = new Route(route.path, route.handler);
			}
			this.routes.push(route);
		});

		return this;
	}

	/**
	 * Adds one or more surfaces to the application.
	 * @param {Surface|String|Array.<Surface|String>} surfaces
	 *     Surface element id or surface instance. You can also pass an Array
	 *     whichcontains surface instances or id. In case of ID, these should be
	 *     the id of surface element.
	 * @chainable
	 */
	addSurfaces(surfaces) {
		if (!Array.isArray(surfaces)) {
			surfaces = [surfaces];
		}
		surfaces.forEach((surface) => {
			if (typeof surface === 'string') {
				surface = new Surface(surface);
			}
			this.surfaces[surface.getId()] = surface;
		});

		return this;
	}

	/**
	 * Returns if can navigate to path.
	 * @param {!string} url
	 * @return {boolean}
	 */
	canNavigate(url) {
		try {
			const uri = url.startsWith('/')
				? new URL(url, globals.window.location.origin)
				: new URL(url);

			const path = getUrlPath(url);

			if (!this.isLinkSameOrigin_(uri.host)) {
				log('Offsite link clicked');

				return false;
			}

			if (!this.isSameBasePath_(path)) {
				log("Link clicked outside app's base path");

				return false;
			}

			// Prevents navigation if it's a hash change on the same url.

			if (uri.hash && utils.isCurrentBrowserPath(path)) {
				return false;
			}

			if (!this.findRoute(path)) {
				log('No route for ' + path);

				return false;
			}

			return true;
		}
		catch (error) {
			return false;
		}
	}

	/**
	 * Clear screens cache.
	 * @chainable
	 */
	clearScreensCache() {
		Object.keys(this.screens).forEach((path) => {
			if (path === this.activePath) {
				this.activeScreen.clearCache();
			}
			else if (
				!(
					this.isNavigationPending &&
					this.pendingNavigate.path === path
				)
			) {
				this.removeScreen(path);
			}
		});
	}

	/**
	 * Retrieves or create a screen instance to a path.
	 * @param {!string} path Path containing the querystring part.
	 * @return {Screen}
	 */
	createScreenInstance(path, route) {
		if (!this.pendingNavigate && path === this.activePath) {
			log('Already at destination, refresh navigation');

			return this.activeScreen;
		}
		/* jshint newcap: false */
		var screen = this.screens[path];
		if (!screen) {
			var handler = route.getHandler();
			if (
				handler === Screen ||
				Screen.isImplementedBy(handler.prototype)
			) {
				screen = new handler();
			}
			else {
				screen = handler(route) || new Screen();
			}
			log('Create screen for [' + path + '] [' + screen + ']');
		}

		return screen;
	}

	/**
	 * @inheritDoc
	 */
	disposeInternal() {
		if (this.activeScreen) {
			this.removeScreen(this.activePath);
		}
		this.clearScreensCache();
		this.formEventHandler_.dispose();
		this.linkEventHandler_.dispose();
		this.appEventHandlers_.removeAllListeners();
		super.disposeInternal();
	}

	/**
	 * Dispatches to the first route handler that matches the current path, if
	 * any.
	 * @return {Promise} Returns a pending request cancellable promise.
	 */
	dispatch() {
		return this.navigate(getCurrentBrowserPath(), true);
	}

	/**
	 * Starts navigation to a path.
	 * @param {!string} path Path containing the querystring part.
	 * @param {boolean=} opt_replaceHistory Replaces browser history.
	 * @return {Promise} Returns a pending request cancellable promise.
	 */
	doNavigate_(path, opt_replaceHistory) {
		var route = this.findRoute(path);
		if (!route) {
			return Promise.reject(new Error('No route for ' + path));
		}

		log('Navigate to [' + path + ']');

		this.stopPendingNavigate_();
		this.isNavigationPending = true;

		var nextScreen = this.createScreenInstance(path, route);

		return this.maybePreventDeactivate_()
			.then(() => this.maybePreventActivate_(nextScreen))
			.then(() => nextScreen.load(path))
			.then(() => {

				// At this point we cannot stop navigation and all received
				// navigate candidates will be queued at scheduledNavigationQueue.

				this.navigationStrategy = NavigationStrategy.SCHEDULE_LAST;

				if (this.activeScreen) {
					this.activeScreen.deactivate();
				}
				this.prepareNavigateHistory_(
					path,
					nextScreen,
					opt_replaceHistory
				);
				this.prepareNavigateSurfaces_(
					nextScreen,
					this.surfaces,
					this.extractParams(route, path)
				);
			})
			.then(() => nextScreen.evaluateStyles(this.surfaces))
			.then(() => nextScreen.flip(this.surfaces))
			.then(() => nextScreen.evaluateScripts(this.surfaces))
			.then(() => this.maybeUpdateScrollPositionState_())
			.then(() => this.syncScrollPositionSyncThenAsync_())
			.then(() => this.finalizeNavigate_(path, nextScreen))
			.then(() => this.maybeOverloadBeforeUnload_())
			.catch((reason) => {
				this.isNavigationPending = false;
				this.handleNavigateError_(path, nextScreen, reason);
				throw reason;
			})
			.finally(() => {
				this.navigationStrategy = NavigationStrategy.IMMEDIATE;

				if (this.scheduledNavigationQueue.length) {
					const scheduledNavigation = this.scheduledNavigationQueue.shift();
					this.maybeNavigate_(
						scheduledNavigation.href,
						scheduledNavigation
					);
				}
			});
	}

	/**
	 * Extracts params according to the given path and route.
	 * @param {!Route} route
	 * @param {string} path
	 * @param {!Object}
	 */
	extractParams(route, path) {
		return route.extractParams(this.getRoutePath(path));
	}

	/**
	 * Finalizes a screen navigation.
	 * @param {!string} path Path containing the querystring part.
	 * @param {!Screen} nextScreen
	 * @protected
	 */
	finalizeNavigate_(path, nextScreen) {
		nextScreen.activate();

		if (this.activeScreen && !this.activeScreen.isCacheable()) {
			if (this.activeScreen !== nextScreen) {
				this.removeScreen(this.activePath);
			}
		}

		this.activePath = path;
		this.activeScreen = nextScreen;
		this.browserPathBeforeNavigate = getCurrentBrowserPathWithoutHash();
		this.screens[path] = nextScreen;
		this.isNavigationPending = false;
		this.pendingNavigate = null;
		globals.capturedFormElement = null;
		globals.capturedFormButtonElement = null;
		log('Navigation done');
	}

	/**
	 * Finds a route for the test path. Returns true if matches has a route,
	 * otherwise returns null.
	 * @param {!string} path Path containing the querystring part.
	 * @return {?Object} Route handler if match any or <code>null</code> if the
	 *     path is the same as the current url and the path contains a fragment.
	 */
	findRoute(path) {
		path = this.getRoutePath(path);
		for (var i = 0; i < this.routes.length; i++) {
			var route = this.routes[i];
			if (route.matchesPath(path)) {
				return route;
			}
		}

		return null;
	}

	/**
	 * Gets allow prevent navigate.
	 * @return {boolean}
	 */
	getAllowPreventNavigate() {
		return this.allowPreventNavigate;
	}

	/**
	 * Gets link base path.
	 * @return {!string}
	 */
	getBasePath() {
		return this.basePath;
	}

	/**
	 * Gets the default page title.
	 * @return {string} defaultTitle
	 */
	getDefaultTitle() {
		return this.defaultTitle;
	}

	/**
	 * Gets the form selector.
	 * @return {!string}
	 */
	getFormSelector() {
		return this.formSelector;
	}

	/**
	 * Check if route matching is ignoring query string from the route path.
	 * @return {boolean}
	 */
	getIgnoreQueryStringFromRoutePath() {
		return this.ignoreQueryStringFromRoutePath;
	}

	/**
	 * Gets the link selector.
	 * @return {!string}
	 */
	getLinkSelector() {
		return this.linkSelector;
	}

	/**
	 * Gets the loading css class.
	 * @return {!string}
	 */
	getLoadingCssClass() {
		return this.loadingCssClass;
	}

	/**
	 * Returns the given path formatted to be matched by a route. This will,
	 * for example, remove the base path from it, but make sure it will end
	 * with a '/'.
	 * @param {string} path
	 * @return {string}
	 */
	getRoutePath(path) {
		if (this.getIgnoreQueryStringFromRoutePath()) {
			path = getUrlPathWithoutHashAndSearch(path);

			return getUrlPathWithoutHashAndSearch(
				path.substr(this.basePath.length)
			);
		}

		path = getUrlPathWithoutHash(path);

		return getUrlPathWithoutHash(path.substr(this.basePath.length));
	}

	/**
	 * Gets the update scroll position value.
	 * @return {boolean}
	 */
	getUpdateScrollPosition() {
		return this.updateScrollPosition;
	}

	/**
	 * Handle navigation error.
	 * @param {!string} path Path containing the querystring part.
	 * @param {!Screen} nextScreen
	 * @param {!Error} error
	 * @protected
	 */
	handleNavigateError_(path, nextScreen, error) {
		log('Navigation error for [' + nextScreen + '] (' + error.stack + ')');
		this.emit('navigationError', {
			error,
			nextScreen,
			path,
		});
		if (!utils.isCurrentBrowserPath(path)) {
			if (this.isNavigationPending && this.pendingNavigate) {
				this.pendingNavigate.finally(
					() => this.removeScreen(path),
					this
				);
			}
			else {
				this.removeScreen(path);
			}
		}
	}

	/**
	 * Checks if app has routes.
	 * @return {boolean}
	 */
	hasRoutes() {
		return this.routes.length > 0;
	}

	/**
	 * Tests if host is an offsite link.
	 * @param {!string} host Link host to compare with
	 *     <code>globals.window.location.host</code>.
	 * @return {boolean}
	 * @protected
	 */
	isLinkSameOrigin_(host) {
		return host === globals.window.location.host;
	}

	/**
	 * Tests if link element has the same app's base path.
	 * @param {!string} path Link path containing the querystring part.
	 * @return {boolean}
	 * @protected
	 */
	isSameBasePath_(path) {
		return path.indexOf(this.basePath) === 0;
	}

	/**
	 * Lock the document scroll in order to avoid the browser native back and
	 * forward navigation to change the scroll position. In the end of
	 * navigation lifecycle scroll is repositioned.
	 * @protected
	 */
	lockHistoryScrollPosition_() {
		var state = globals.window.history.state;
		if (!state) {
			return;
		}

		// Browsers are inconsistent when re-positioning the scroll history on
		// popstate. At some browsers, history scroll happens before popstate, then
		// lock the scroll on the last known position as soon as possible after the
		// current JS execution context and capture the current value. Some others,
		// history scroll happens after popstate, in this case, we bind an once
		// scroll event to lock the las known position. Lastly, the previous two
		// behaviors can happen even on the same browser, hence the race will decide
		// the winner.

		var winner = false;
		var switchScrollPositionRace = function () {
			globals.document.removeEventListener(
				'scroll',
				switchScrollPositionRace,
				false
			);
			if (!winner) {
				globals.window.scrollTo(state.scrollLeft, state.scrollTop);
				winner = true;
			}
		};
		setTimeout(switchScrollPositionRace);
		globals.document.addEventListener(
			'scroll',
			switchScrollPositionRace,
			false
		);
	}

	/**
	 * If supported by the browser, disables native scroll restoration and
	 * stores current value.
	 */
	maybeDisableNativeScrollRestoration() {
		if (this.nativeScrollRestorationSupported) {
			this.nativeScrollRestoration_ =
				globals.window.history.scrollRestoration;
			globals.window.history.scrollRestoration = 'manual';
		}
	}

	/**
	 * This method is used to evaluate if is possible to queue received
	 *  dom event to scheduleNavigationQueue and enqueue it.
	 * @param {string} href Information about the link's href.
	 * @param {Event} event Dom event that initiated the navigation.
	 */
	maybeScheduleNavigation_(href, event) {
		if (
			this.isNavigationPending &&
			this.navigationStrategy === NavigationStrategy.SCHEDULE_LAST
		) {
			this.scheduledNavigationQueue = [
				{
					href,
					isScheduledNavigation: true,
				},
				...event,
			];

			return true;
		}

		return false;
	}

	/**
	 * Maybe navigate to a path.
	 * @param {string} href Information about the link's href.
	 * @param {Event} event Dom event that initiated the navigation.
	 */
	maybeNavigate_(href, event) {
		if (!this.canNavigate(href)) {
			return;
		}

		const isNavigationScheduled = this.maybeScheduleNavigation_(
			href,
			event
		);

		if (isNavigationScheduled) {
			event.preventDefault();

			return;
		}

		var navigateFailed = false;
		try {
			this.navigate(getUrlPath(href), false, event);
		}
		catch (err) {

			// Do not prevent link navigation in case some synchronous error occurs

			navigateFailed = true;
		}

		if (!navigateFailed && !event.isScheduledNavigation) {
			event.preventDefault();
		}
	}

	/**
	 * Checks whether the onbeforeunload global event handler is overloaded
	 * by client code. If so, it replaces with a function that halts the normal
	 * event flow in relation with the client onbeforeunload function.
	 * This can be in most part used to prematurely terminate navigation to other pages
	 * according to the given constrait(s).
	 * @protected
	 */
	maybeOverloadBeforeUnload_() {
		if ('function' === typeof window.onbeforeunload) {
			window._onbeforeunload = window.onbeforeunload;

			window.onbeforeunload = (event) => {
				this.emit('beforeUnload', event);
				if (event && event.defaultPrevented) {
					return true;
				}
			};

			// mark the updated handler due unwanted recursion

			window.onbeforeunload._overloaded = true;
		}
	}

	/**
	 * Cancels navigation if nextScreen's beforeActivate lifecycle method
	 * resolves to true.
	 * @param {!Screen} nextScreen
	 * @return {!Promise}
	 */
	maybePreventActivate_(nextScreen) {
		return Promise.resolve()
			.then(() => {
				return nextScreen.beforeActivate();
			})
			.then((prevent) => {
				if (prevent) {
					return Promise.reject(
						new Error('Cancelled by next screen')
					);
				}
			});
	}

	/**
	 * Cancels navigation if activeScreen's beforeDeactivate lifecycle
	 * method resolves to true.
	 * @return {!Promise}
	 */
	maybePreventDeactivate_() {
		return Promise.resolve()
			.then(() => {
				if (this.activeScreen) {
					return this.activeScreen.beforeDeactivate();
				}
			})
			.then((prevent) => {
				if (prevent) {
					return Promise.reject(
						new Error('Cancelled by active screen')
					);
				}
			});
	}

	/**
	 * Maybe reposition scroll to hashed anchor.
	 */
	maybeRepositionScrollToHashedAnchor() {
		const hash = globals.window.location.hash;
		if (hash) {
			const anchorElement = globals.document.getElementById(
				hash.substring(1)
			);
			if (anchorElement) {
				const {offsetLeft, offsetTop} = getNodeOffset(anchorElement);
				globals.window.scrollTo(offsetLeft, offsetTop);
			}
		}
	}

	/**
	 * If supported by the browser, restores native scroll restoration to the
	 * value captured by `maybeDisableNativeScrollRestoration`.
	 */
	maybeRestoreNativeScrollRestoration() {
		if (
			this.nativeScrollRestorationSupported &&
			this.nativeScrollRestoration_
		) {
			globals.window.history.scrollRestoration = this.nativeScrollRestoration_;
		}
	}

	/**
	 * Maybe restore redirected path hash in case both the current path and
	 * the given path are the same.
	 * @param {!string} path Path before navigation.
	 * @param {!string} redirectPath Path after navigation.
	 * @param {!string} hash Hash to be added to the path.
	 * @return {!string} Returns the path with the hash restored.
	 */
	maybeRestoreRedirectPathHash_(path, redirectPath, hash) {
		if (redirectPath === getUrlPathWithoutHash(path)) {
			return redirectPath + hash;
		}

		return redirectPath;
	}

	/**
	 * Maybe update scroll position in history state to anchor on path.
	 * @param {!string} path Path containing anchor
	 */
	maybeUpdateScrollPositionState_() {
		var hash = globals.window.location.hash;
		var anchorElement = globals.document.getElementById(hash.substring(1));
		if (anchorElement) {
			const {offsetLeft, offsetTop} = getNodeOffset(anchorElement);
			this.saveHistoryCurrentPageScrollPosition_(offsetTop, offsetLeft);
		}
	}

	/**
	 * Navigates to the specified path if there is a route handler that matches.
	 * @param {!string} path Path to navigate containing the base path.
	 * @param {boolean=} opt_replaceHistory Replaces browser history.
	 * @param {Event=} event Optional event object that triggered the navigation.
	 * @return {Promise} Returns a pending request cancellable promise.
	 */
	navigate(path, opt_replaceHistory, opt_event) {
		if (opt_event) {
			globals.capturedFormElement = opt_event.capturedFormElement;
			globals.capturedFormButtonElement =
				opt_event.capturedFormButtonElement;
		}

		// When reloading the same path do replaceState instead of pushState to
		// avoid polluting history with states with the same path.

		if (path === this.activePath) {
			opt_replaceHistory = true;
		}

		this.emit('beforeNavigate', {
			event: opt_event,
			path,
			replaceHistory: !!opt_replaceHistory,
		});

		return this.pendingNavigate;
	}

	/**
	 * Befores navigation to a path.
	 * @param {!Event} event Event facade containing <code>path</code> and
	 *     <code>replaceHistory</code>.
	 * @protected
	 */
	onBeforeNavigate_(event) {
		if (globals.capturedFormElement) {
			event.form = globals.capturedFormElement;
		}
	}

	/**
	 * Befores navigation to a path. Runs after external listeners.
	 * @param {!Event} event Event facade containing <code>path</code> and
	 *     <code>replaceHistory</code>.
	 * @protected
	 */
	onBeforeNavigateDefault_(event) {
		if (this.pendingNavigate) {
			if (
				this.pendingNavigate.path === event.path ||
				this.navigationStrategy === NavigationStrategy.SCHEDULE_LAST
			) {
				log('Waiting...');

				return;
			}
		}

		this.emit('beforeUnload', event);

		this.emit('startNavigate', {
			form: event.form,
			path: event.path,
			replaceHistory: event.replaceHistory,
		});
	}

	/**
	 * Custom event handler that executes the original listener that has been
	 * added by the client code and terminates the navigation accordingly.
	 * @param {!Event} event original Event facade.
	 * @protected
	 */
	onBeforeUnloadDefault_(event) {
		var func = window._onbeforeunload;
		if (func && !func._overloaded && func()) {
			event.preventDefault();
		}
	}

	/**
	 * Intercepts document clicks and test link elements in order to decide
	 * whether Surface app can navigate.
	 * @param {!Event} event Event facade
	 * @protected
	 */
	onDocClickDelegate_(event) {
		if (
			event.altKey ||
			event.ctrlKey ||
			event.metaKey ||
			event.shiftKey ||
			event.button
		) {
			log(
				'Navigate aborted, invalid mouse button or modifier key pressed.'
			);

			return;
		}
		this.maybeNavigate_(
			event.target.closest(this.getLinkSelector()).href,
			event
		);
	}

	/**
	 * Intercepts document form submits and test action path in order to decide
	 * whether Surface app can navigate.
	 * @param {!Event} event Event facade
	 * @protected
	 */
	onDocSubmitDelegate_(event) {
		var form = event.target.closest(this.getFormSelector());
		if (form.method === 'get') {
			log('GET method not supported');

			return;
		}
		event.capturedFormElement = form;
		const buttonSelector =
			'button:not([type]),button[type=submit],input[type=submit]';
		if (globals.document.activeElement.matches(buttonSelector)) {
			event.capturedFormButtonElement = globals.document.activeElement;
		}
		else {
			event.capturedFormButtonElement = form.querySelector(
				buttonSelector
			);
		}
		this.maybeNavigate_(form.action, event);
	}

	/**
	 * Listens to the window's load event in order to avoid issues with some browsers
	 * that trigger popstate calls on the first load. For more information see
	 * http://stackoverflow.com/questions/6421769/popstate-on-pages-load-in-chrome.
	 * @protected
	 */
	onLoad_() {
		this.skipLoadPopstate = true;
		setTimeout(() => {

			// The timeout ensures that popstate events will be unblocked right
			// after the load event occured, but not in the same event-loop cycle.

			this.skipLoadPopstate = false;
		});

		// Try to reposition scroll to the hashed anchor when page loads.

		this.maybeRepositionScrollToHashedAnchor();
	}

	/**
	 * Handles browser history changes and fires app's navigation if the state
	 * belows to us. If we detect a popstate and the state is <code>null</code>,
	 * assume it is navigating to an external page or to a page we don't have
	 * route, then <code>globals.window.location.reload()</code> is invoked in order to
	 * reload the content to the current url.
	 * @param {!Event} event Event facade
	 * @protected
	 */
	onPopstate_(event) {
		if (this.skipLoadPopstate) {
			return;
		}

		// Do not navigate if the popstate was triggered by a hash change.

		if (utils.isCurrentBrowserPath(this.browserPathBeforeNavigate)) {
			this.maybeRepositionScrollToHashedAnchor();

			return;
		}

		var state = event.state;

		if (!state) {
			if (globals.window.location.hash) {

				// If senna is on an redirect path and a hash popstate happens
				// to a different url, reload the browser. This behavior doesn't
				// require senna to route hashed links and is closer to native
				// browser behavior.

				if (
					this.redirectPath &&
					!utils.isCurrentBrowserPath(this.redirectPath)
				) {
					this.reloadPage();
				}

				// Always try to reposition scroll to the hashed anchor when
				// hash popstate happens.

				this.maybeRepositionScrollToHashedAnchor();
			}
			else {
				this.reloadPage();
			}

			return;
		}

		if (state.senna) {
			log('History navigation to [' + state.path + ']');
			this.popstateScrollTop = state.scrollTop;
			this.popstateScrollLeft = state.scrollLeft;
			if (!this.nativeScrollRestorationSupported) {
				this.lockHistoryScrollPosition_();
			}
			this.once('endNavigate', () => {
				if (state.referrer) {
					setReferrer(state.referrer);
				}
			});
			const uri = state.path.startsWith('/')
				? new URL(state.path, globals.window.location.origin)
				: new URL(state.path);
			uri.hostname = globals.window.location.hostname;
			uri.port = globals.window.location.port;
			const isNavigationScheduled = this.maybeScheduleNavigation_(
				uri.toString(),
				new Map()
			);
			if (isNavigationScheduled) {
				return;
			}
			this.navigate(state.path, true);
		}
	}

	/**
	 * Listens document scroll changes in order to capture the possible lock
	 * scroll position for history scrolling.
	 * @protected
	 */
	onScroll_() {
		if (this.captureScrollPositionFromScrollEvent) {
			this.saveHistoryCurrentPageScrollPosition_(
				globals.window.pageYOffset,
				globals.window.pageXOffset
			);
		}
	}

	/**
	 * Starts navigation to a path.
	 * @param {!Event} event Event facade containing <code>path</code> and
	 *     <code>replaceHistory</code>.
	 * @protected
	 */
	onStartNavigate_(event) {
		this.maybeDisableNativeScrollRestoration();
		this.captureScrollPositionFromScrollEvent = false;
		globals.document.documentElement.classList.add(this.loadingCssClass);

		var endNavigatePayload = {
			form: event.form,
			path: event.path,
		};

		this.pendingNavigate = this.doNavigate_(
			event.path,
			event.replaceHistory
		)
			.catch((reason) => {
				endNavigatePayload.error = reason;
				throw reason;
			})
			.finally(() => {
				if (
					!this.pendingNavigate &&
					!this.scheduledNavigationQueue.length
				) {
					globals.document.documentElement.classList.remove(
						this.loadingCssClass
					);
					this.maybeRestoreNativeScrollRestoration();
					this.captureScrollPositionFromScrollEvent = true;
				}
				this.emit('endNavigate', endNavigatePayload);
			});

		this.pendingNavigate.path = event.path;
	}

	/**
	 * Prefetches the specified path if there is a route handler that matches.
	 * @param {!string} path Path to navigate containing the base path.
	 * @return {Promise} Returns a pending request cancellable promise.
	 */
	prefetch(path) {
		var route = this.findRoute(path);
		if (!route) {
			return Promise.reject(new Error('No route for ' + path));
		}

		log('Prefetching [' + path + ']');

		var nextScreen = this.createScreenInstance(path, route);

		return nextScreen
			.load(path)
			.then(() => {
				this.screens[path] = nextScreen;
			})
			.catch((reason) => {
				this.handleNavigateError_(path, nextScreen, reason);
				throw reason;
			});
	}

	/**
	 * Prepares screen flip. Updates history state and surfaces content.
	 * @param {!string} path Path containing the querystring part.
	 * @param {!Screen} nextScreen
	 * @param {boolean=} opt_replaceHistory Replaces browser history.
	 */
	prepareNavigateHistory_(path, nextScreen, opt_replaceHistory) {
		let title = nextScreen.getTitle();
		if (typeof title !== 'string') {
			title = this.getDefaultTitle();
		}
		let redirectPath = nextScreen.beforeUpdateHistoryPath(path);
		const hash = path.startsWith('/')
			? new URL(path, globals.window.location.origin).hash
			: new URL(path).hash;
		redirectPath = this.maybeRestoreRedirectPathHash_(
			path,
			redirectPath,
			hash
		);
		const historyState = {
			form: !!globals.capturedFormElement,
			path,
			redirectPath,
			scrollLeft: 0,
			scrollTop: 0,
			senna: true,
		};
		if (opt_replaceHistory) {
			historyState.scrollTop = this.popstateScrollTop;
			historyState.scrollLeft = this.popstateScrollLeft;
		}
		this.updateHistory_(
			title,
			redirectPath,
			nextScreen.beforeUpdateHistoryState(historyState),
			opt_replaceHistory
		);
		this.redirectPath = redirectPath;
	}

	/**
	 * Prepares screen flip. Updates history state and surfaces content.
	 * @param {!Screen} nextScreen
	 * @param {!Object} surfaces Map of surfaces to flip keyed by surface id.
	 * @param {!Object} params Params extracted from the current path.
	 */
	prepareNavigateSurfaces_(nextScreen, surfaces, params) {
		Object.keys(surfaces).forEach((id) => {
			var surfaceContent = nextScreen.getSurfaceContent(id, params);
			surfaces[id].addContent(nextScreen.getId(), surfaceContent);
			log(
				'Screen [' +
					nextScreen.getId() +
					'] add content to surface ' +
					'[' +
					surfaces[id] +
					'] [' +
					(surfaceContent ? '...' : 'empty') +
					']'
			);
		});
	}

	/**
	 * Reloads the page by performing `window.location.reload()`.
	 */
	reloadPage() {
		globals.window.location.reload();
	}

	/**
	 * Removes route instance from app routes.
	 * @param {Route} route
	 * @return {boolean} True if an element was removed.
	 */
	removeRoute(route) {
		const routeIndex = this.routes.indexOf(route);

		if (routeIndex >= 0) {
			this.routes.splice(routeIndex, 1);
		}

		return routeIndex >= 0;
	}

	/**
	 * Removes a screen.
	 * @param {!string} path Path containing the querystring part.
	 */
	removeScreen(path) {
		var screen = this.screens[path];
		if (screen) {
			Object.keys(this.surfaces).forEach((surfaceId) =>
				this.surfaces[surfaceId].remove(screen.getId())
			);
			screen.dispose();
			delete this.screens[path];
		}
	}

	/**
	 * Saves given scroll position into history state.
	 * @param {!number} scrollTop Number containing the top scroll position to be saved.
	 * @param {!number} scrollLeft Number containing the left scroll position to be saved.
	 */
	saveHistoryCurrentPageScrollPosition_(scrollTop, scrollLeft) {
		var state = globals.window.history.state;
		if (state && state.senna) {
			[state.scrollTop, state.scrollLeft] = [scrollTop, scrollLeft];
			globals.window.history.replaceState(state, null, null);
		}
	}

	/**
	 * Sets allow prevent navigate.
	 * @param {boolean} allowPreventNavigate
	 */
	setAllowPreventNavigate(allowPreventNavigate) {
		this.allowPreventNavigate = allowPreventNavigate;
	}

	/**
	 * Sets link base path.
	 * @param {!string} path
	 */
	setBasePath(basePath) {
		this.basePath = removePathTrailingSlash(basePath);
	}

	/**
	 * Sets the default page title.
	 * @param {string} defaultTitle
	 */
	setDefaultTitle(defaultTitle) {
		this.defaultTitle = defaultTitle;
	}

	/**
	 * Sets the form selector.
	 * @param {!string} formSelector
	 */
	setFormSelector(formSelector) {
		this.formSelector = formSelector;
		if (this.formEventHandler_) {
			this.formEventHandler_.dispose();
		}
		this.formEventHandler_ = delegate(
			document,
			'submit',
			this.formSelector,
			this.onDocSubmitDelegate_.bind(this),
			this.allowPreventNavigate
		);
	}

	/**
	 * Sets if route matching should ignore query string from the route path.
	 * @param {boolean} ignoreQueryStringFromRoutePath
	 */
	setIgnoreQueryStringFromRoutePath(ignoreQueryStringFromRoutePath) {
		this.ignoreQueryStringFromRoutePath = ignoreQueryStringFromRoutePath;
	}

	/**
	 * Sets the link selector.
	 * @param {!string} linkSelector
	 */
	setLinkSelector(linkSelector) {
		this.linkSelector = linkSelector;
		if (this.linkEventHandler_) {
			this.linkEventHandler_.dispose();
		}
		this.linkEventHandler_ = delegate(
			document,
			'click',
			this.linkSelector,
			this.onDocClickDelegate_.bind(this),
			this.allowPreventNavigate
		);
	}

	/**
	 * Sets the loading css class.
	 * @param {!string} loadingCssClass
	 */
	setLoadingCssClass(loadingCssClass) {
		this.loadingCssClass = loadingCssClass;
	}

	/**
	 * Sets the update scroll position value.
	 * @param {boolean} updateScrollPosition
	 */
	setUpdateScrollPosition(updateScrollPosition) {
		this.updateScrollPosition = updateScrollPosition;
	}

	/**
	 * Cancels pending navigate with <code>Cancel pending navigation</code> error.
	 * @protected
	 */
	stopPendingNavigate_() {
		if (this.pendingNavigate) {

			//this.pendingNavigate.cancel('Cancel pending navigation');

		}
		this.pendingNavigate = null;
	}

	/**
	 * Sync document scroll position twice, the first one synchronous and then
	 * one inside <code>setTimeout(cb, 0)</code>. Relevant to browsers that fires
	 * scroll restoration asynchronously after popstate.
	 * @protected
	 * @return {?Promise=}
	 */
	syncScrollPositionSyncThenAsync_() {
		var state = globals.window.history.state;
		if (!state) {
			return;
		}

		var scrollTop = state.scrollTop;
		var scrollLeft = state.scrollLeft;

		var sync = () => {
			if (this.updateScrollPosition) {
				globals.window.scrollTo(scrollLeft, scrollTop);
			}
		};

		return new Promise((resolve) => {
			sync();

			setTimeout(() => {
				sync();
				resolve();
			});
		});
	}

	/**
	 * Updates or replace browser history.
	 * @param {?string} title Document title.
	 * @param {!string} path Path containing the querystring part.
	 * @param {!object} state
	 * @param {boolean=} opt_replaceHistory Replaces browser history.
	 * @protected
	 */
	updateHistory_(title, path, state, opt_replaceHistory) {
		const referrer = globals.window.location.href;

		if (state) {
			state.referrer = referrer;
		}

		if (opt_replaceHistory) {
			globals.window.history.replaceState(state, title, path);
		}
		else {
			globals.window.history.pushState(state, title, path);
		}

		setReferrer(referrer);

		const titleNode = globals.document.querySelector('title');
		if (titleNode) {
			titleNode.innerHTML = title;
		}
		else {
			globals.document.title = title;
		}
	}
}

export default App;
