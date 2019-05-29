/**
* Clay 2.14.2
*
* Copyright 2019, Liferay, Inc.
* All rights reserved.
* MIT license
*/
+function($) {
	/**
	 * Map from toggler DOM nodes to sidenav instances.
	 */
	const INSTANCE_MAP = new WeakMap();

	/**
	 * Utility function that strips off a possible jQuery and Metal
	 * component wrappers from a DOM element.
	 */
	function getElement(element) {
		// Remove jQuery wrapper, if any.
		if (element.jquery) {
			if (element.length > 1) {
				throw new Error(
					`getElement(): Expected at most one element, got ${element.length}`
				);
			}
			element = element.get(0);
		}

		// Remove Metal wrapper, if any.
		if (element && !(element instanceof HTMLElement)) {
			element = element.element;
		}

		return element;
	}

	function addClass(element, className) {
		setClasses(element, {
			[className]: true,
		});
	}

	function removeClass(element, className) {
		setClasses(element, {
			[className]: false,
		});
	}

	function setClasses(element, classes) {
		element = getElement(element);

		if (element) {
			// One at a time because IE 11: https://caniuse.com/#feat=classlist
			Object.entries(classes).forEach(([className, present]) => {
				// Some callers use multiple space-separated classNames for
				// `openClass`/`data-open-class`. (Looking at you,
				// product-navigation-simulation-web...)
				className.split(/\s+/).forEach(name => {
					if (present) {
						element.classList.add(name);
					} else {
						element.classList.remove(name);
					}
				});
			});
		}
	}

	function hasClass(element, className) {
		element = getElement(element);

		// Again, product-navigation-simulation-web passes multiple classNames.
		return className.split(/\s+/).every(name => {
			return element.classList.contains(name);
		});
	}

	function setStyles(element, styles) {
		element = getElement(element);

		if (element) {
			Object.entries(styles).forEach(([property, value]) => {
				element.style[property] = value;
			});
		}
	}

	/**
	 * For compatibility with jQuery, which will treat "100" as "100px".
	 */
	function px(dimension) {
		if (typeof dimension === 'number') {
			return dimension + 'px';
		} else if (typeof dimension === 'string' && dimension.match(/^\s*\d+\s*$/)) {
			return dimension.trim() + 'px';
		} else {
			return dimension;
		}
	}

	function subscribe(element, eventName, handler) {
		if (element) {
			element.addEventListener(eventName, handler);

			return {
				dispose() {
					element.removeEventListener(eventName, handler);
				}
			};
		}

		return null;
	}

	let selectorCounter = 0;

	/**
	 * Returns a unique ID-based selector for the supplied element,
	 * creating one and assigning it to the element if necessary.
	 */
	function getUniqueSelector(element) {
		element = getElement(element);

		let id = element.id

		if (!id) {
			id = 'generatedSideNavId__' + selectorCounter++;

			element.id = id;
		}

		return `#${id}`;
	}

	function toInt(str) {
		return parseInt(str, 10) || 0;
	}

	function SideNavigation($toggler, options) {
		this.init($toggler, options);
	};

	SideNavigation.TRANSITION_DURATION = 500;

	SideNavigation.prototype = {
		init: function($toggler, options) {
			const instance = this;

			const useDataAttribute = $toggler.data('toggle') === 'sidenav';

			options = Object.assign({}, defaults, options);

			options.breakpoint = toInt(options.breakpoint);
			options.container = options.container || $toggler.data('target') || $toggler.attr('href');
			options.gutter = toInt(options.gutter);
			options.rtl = document.dir === 'rtl';
			options.width = toInt(options.width);
			options.widthOriginal = options.width;

			// instantiate using data attribute

			if (useDataAttribute) {
				options.closedClass = $toggler.data('closed-class') || 'closed';
				options.content = $toggler.data('content');
				options.loadingIndicatorTPL = $toggler.data('loading-indicator-tpl') || options.loadingIndicatorTPL;
				options.openClass = $toggler.data('open-class') || 'open';
				options.$toggler = $toggler;
				options.type = $toggler.data('type');
				options.typeMobile = $toggler.data('type-mobile');
				options.url = $toggler.data('url');
				options.width = '';
			}

			instance.$toggler = $toggler;
			instance.options = options;
			instance.useDataAttribute = useDataAttribute;

			instance._bindUI();
			instance._renderUI();
		},

		clearHeight: function() {
			const instance = this;

			const options = instance.options;

			const $container = $(options.container);

			const $content = $container.find(options.content).first();
			const $navigation = $container.find(options.navigation).first();
			const $menu = $container.find('.sidenav-menu').first();

			[$content, $navigation, $menu].forEach($element => {
				setStyles($element, {
					height: '',
					'min-height': '',
				});
			});
		},

		destroy: function() {
			const instance = this;

			const options = instance.options;

			const $container = $(options.container);

			if (instance._sidenavCloseSubscription) {
				instance._sidenavCloseSubscription.dispose();
				instance._sidenavCloseSubscription = null;
			}

			if (instance._togglerSubscription) {
				instance._togglerSubscription.dispose();
				instance._togglerSubscription = null;
			}

			// Remove Side Navigation

			INSTANCE_MAP.delete(this.$toggler.get(0));
		},

		hide: function() {
			const instance = this;

			if (instance.useDataAttribute) {
				instance.hideSimpleSidenav();
			}
			else {
				instance.toggleNavigation(false);
			}
		},

		hideSidenav: function() {
			const instance = this;
			const options = instance.options;

			const $container = $(options.container);
			const $content = $container.find(options.content).first();
			const $navigation = $container.find(options.navigation).first();
			const $menu = $navigation.find('.sidenav-menu').first();

			const sidenavRight = instance._isSidenavRight();

			let positionDirection = options.rtl ? 'right' : 'left';

			if (sidenavRight) {
				positionDirection = options.rtl ? 'left' : 'right';
			}

			const paddingDirection = 'padding-' + positionDirection;

			setStyles($content, {
				[paddingDirection]: '',
				[positionDirection]: '',
			});

			setStyles($navigation, {
				width: '',
			});

			if (sidenavRight) {
				setStyles($menu, {
					[positionDirection]: px(instance._getSidenavWidth()),
				});
			}
		},

		hideSimpleSidenav: function() {
			const instance = this;

			const options = instance.options;

			const simpleSidenavClosed = instance._isSimpleSidenavClosed();

			if (!simpleSidenavClosed) {
				const $content = $(options.content).first();
				const $sidenav = $(options.container);

				const closedClass = options.closedClass;
				const openClass = options.openClass;

				const $toggler = instance.$toggler;

				const target = $toggler.attr('data-target') || $toggler.attr('href');

				$sidenav.trigger({
					toggler: $(instance.togglerSelector),
					type: 'closedStart.lexicon.sidenav'
				});

				instance._subscribeSidenavTransitionEnd($content, function() {
					removeClass($sidenav, 'sidenav-transition');
					removeClass($toggler, 'sidenav-transition');

					$sidenav.trigger({
						toggler: $(instance.togglerSelector),
						type: 'closed.lexicon.sidenav'
					});
				});

				if (hasClass($content, openClass)) {
					setClasses($content, {
						'sidenav-transition': true,
						[closedClass]: true,
						[openClass]: false,
					});
				}

				addClass($sidenav, 'sidenav-transition');
				addClass($toggler, 'sidenav-transition');

				setClasses($sidenav, {
					[closedClass]: true,
					[openClass]: false,
				});

				setClasses($('[data-target="' + target + '"]'), {
					[openClass]: false,
					active: false,
				});
				setClasses($('[href="' + target + '"]'), {
					[openClass]: false,
					active: false,
				});
			}
		},

		setHeight: function() {
			const instance = this;

			const options = instance.options;

			const $container = $(options.container);
			const content = options.content;
			const navigation = options.navigation;

			const type = instance.mobile ? options.typeMobile : options.type;

			if (type !== 'fixed' && type !== 'fixed-push') {
				const $contentNode = $container.find(content).first();
				const $navNode = $container.find(navigation).first();
				const $sideNavMenuNode = $container.find('.sidenav-menu').first();

				const tallest = px(Math.max($contentNode.outerHeight(), $navNode.outerHeight()));

				setStyles($contentNode, {
					'min-height': tallest,
				});

				setStyles($navNode, {
					height: '100%',
					'min-height': tallest,
				});

				setStyles($sideNavMenuNode, {
					height: '100%',
					'min-height': tallest,
				});
			}
		},

		show: function() {
			const instance = this;

			if (instance.useDataAttribute) {
				instance.showSimpleSidenav();
			}
			else {
				instance.toggleNavigation(true);
			}
		},

		showSidenav: function() {
			const instance = this;
			const mobile = instance.mobile;
			const options = instance.options;

			const $container = $(options.container);
			const $content = $container.find(options.content).first();
			const $navigation = $container.find(options.navigation).first();
			const $menu = $navigation.find('.sidenav-menu').first();

			const sidenavRight = instance._isSidenavRight();
			const width = instance._getSidenavWidth();

			const offset = width + options.gutter;

			const url = options.url;

			if (url) {
				instance._loadUrl($menu, url);
			}

			setStyles($navigation, {
				width: px(width),
			});
			setStyles($menu, {
				width: px(width),
			});

			let positionDirection = options.rtl ? 'right' : 'left';

			if (sidenavRight) {
				positionDirection = options.rtl ? 'left' : 'right';
			}

			const paddingDirection = 'padding-' + positionDirection;

			const pushContentCssProperty = mobile ? positionDirection : paddingDirection;
			const type = mobile ? options.typeMobile : options.type;

			if (type !== 'fixed') {
				const navigationStartX = hasClass($container, 'open') ? $navigation.offset().left - options.gutter : $navigation.offset().left - offset;

				const contentStartX = $content.offset().left;
				const contentWidth = $content.innerWidth();

				let padding = '';

				if ((options.rtl && sidenavRight) || (!options.rtl && options.position === 'left')) {
					navigationStartX = $navigation.offset().left + offset;

					if (navigationStartX > contentStartX) {
						padding = navigationStartX - contentStartX;
					}
				}
				else if ((options.rtl && options.position === 'left') || (!options.rtl && sidenavRight)) {
					if (navigationStartX < contentStartX + contentWidth) {
						padding = (contentStartX + contentWidth) - navigationStartX;

						if (padding >= offset) {
							padding = offset;
						}
					}
				}

				setStyles($content, {
					[pushContentCssProperty]: px(padding),
				});
			}
		},

		showSimpleSidenav: function() {
			const instance = this;

			const options = instance.options;

			const simpleSidenavClosed = instance._isSimpleSidenavClosed();

			if (simpleSidenavClosed) {
				const $content = $(options.content).first();
				const $sidenav = $(options.container);

				const closedClass = options.closedClass;
				const openClass = options.openClass;

				const $toggler = options.$toggler;

				const url = $toggler.data('url');

				if (url) {
					instance._loadUrl($sidenav, url);
				}

				$sidenav.trigger({
					toggler: $(instance.togglerSelector),
					type: 'openStart.lexicon.sidenav'
				});

				instance._subscribeSidenavTransitionEnd($content, function() {
					removeClass($sidenav, 'sidenav-transition');
					removeClass($toggler, 'sidenav-transition');

					$sidenav.trigger({
						toggler: $(instance.togglerSelector),
						type: 'open.lexicon.sidenav'
					});
				});

				setClasses($content, {
					'sidenav-transition': true,
					[openClass]: true,
					[closedClass]: false,
				});
				setClasses($sidenav, {
					'sidenav-transition': true,
					[openClass]: true,
					[closedClass]: false,
				});
				setClasses($toggler, {
					'sidenav-transition': true,
					active: true,
					[openClass]: true,
				});
			}
		},

		toggle: function() {
			const instance = this;

			if (instance.useDataAttribute) {
				instance.toggleSimpleSidenav();
			}
			else {
				instance.toggleNavigation();
			}
		},

		toggleNavigation: function(force) {
			const instance = this;
			const options = instance.options;

			const $container = $(options.container);
			const $menu = $container.find('.sidenav-menu').first();
			const $toggler = instance.$toggler;

			const width = options.width;

			const closed = typeof force === 'boolean' ? force : hasClass($container, 'closed');
			const sidenavRight = instance._isSidenavRight();

			if (closed) {
				$container.trigger({
					toggler: $toggler,
					type: 'openStart.lexicon.sidenav'
				});
			}
			else {
				$container.trigger({
					toggler: $toggler,
					type: 'closedStart.lexicon.sidenav'
				});
			}

			instance._subscribeSidenavTransitionEnd($container, function() {
				const $menu = $container.find('.sidenav-menu').first();

				if (hasClass($container, 'closed')) {
					instance.clearHeight();

					setClasses($toggler, {
						open: false,
						'sidenav-transition': false,
					});

					$container.trigger({
						toggler: $toggler,
						type: 'closed.lexicon.sidenav'
					});
				}
				else {
					setClasses($toggler, {
						open: true,
						'sidenav-transition': false,
					});

					$container.trigger({
						toggler: $toggler,
						type: 'open.lexicon.sidenav'
					});
				}

				if (instance.mobile) {
					// ios 8 fixed element disappears when trying to scroll
					$menu.focus();
				}
			});

			if (closed) {
				instance.setHeight();

				setStyles($menu, {
					width: px(width),
				});

				const positionDirection = options.rtl ? 'left' : 'right';

				if (sidenavRight) {
					setStyles($menu, {
						[positionDirection]: '',
					});
				}
			}

			addClass($container, 'sidenav-transition');
			addClass($toggler, 'sidenav-transition');

			if (closed) {
				instance.showSidenav();
			} else {
				instance.hideSidenav();
			}

			setClasses($container, {
				closed: !closed,
				open: closed,
			});
			setClasses($toggler, {
				active: closed,
				open: closed,
			});
		},

		toggleSimpleSidenav: function() {
			const instance = this;

			const simpleSidenavClosed = instance._isSimpleSidenavClosed();

			if (simpleSidenavClosed) {
				instance.showSimpleSidenav();
			}
			else {
				instance.hideSimpleSidenav();
			}
		},

		visible: function() {
			const instance = this;

			let closed;

			if (instance.useDataAttribute) {
				closed = instance._isSimpleSidenavClosed();
			}
			else {
				const $container = $(instance.options.container);

				closed = hasClass($container, 'sidenav-transition') ? !hasClass($container, 'closed') : hasClass($container, 'closed');
			}

			return !closed;
		},

		_bindUI: function() {
			const instance = this;

			instance._subscribeClickTrigger();

			instance._subscribeClickSidenavClose();
		},

		_getSidenavWidth: function() {
			const instance = this;

			const options = instance.options;

			const widthOriginal = options.widthOriginal;

			let width = widthOriginal;
			const winWidth = window.innerWidth;

			if (winWidth < widthOriginal + 40) {
				width = winWidth - 40;
			}

			return width;
		},

		_getSimpleSidenavType: function() {
			const instance = this;

			const options = instance.options;

			const desktop = instance._isDesktop();
			const type = options.type;
			const typeMobile = options.typeMobile;

			if (desktop && (type === 'fixed-push')) {
				return 'desktop-fixed-push';
			}
			else if (!desktop && (typeMobile === 'fixed-push')) {
				return 'mobile-fixed-push';
			}

			return 'fixed';
		},

		_isDesktop: function() {
			return window.innerWidth >= this.options.breakpoint;
		},

		_isSidenavRight: function() {
			const instance = this;
			const options = instance.options;

			const $container = $(options.container);
			const isSidenavRight = hasClass($container, 'sidenav-right');

			return isSidenavRight;
		},

		_isSimpleSidenavClosed: function() {
			const instance = this;
			const options = instance.options;

			const openClass = options.openClass;

			const $container = $(options.container);

			return !hasClass($container, openClass);
		},

		_loadUrl: function($sidenav, url) {
			const instance = this;

			const $sidebarBody = $sidenav.find('.sidebar-body').first();

			if (!instance._fetchPromise && $sidebarBody.length) {
				$sidebarBody.append('<div class="sidenav-loading">' + instance.options.loadingIndicatorTPL + '</div>');

				instance._fetchPromise = fetch(url);

				instance._fetchPromise
					.then(response => {
						if (!response.ok) {
							throw new Error(`Failed to fetch ${url}`);
						}
						return response.text();
					})
					.then(text => {
						$sidebarBody.append(text);

						$sidebarBody.find('.sidenav-loading').remove();

						instance.setHeight();
					})
					.catch(err => {
						console.log(err);
					});
			}
		},

		_subscribeClickSidenavClose: function() {
			const instance = this;

			const options = instance.options;

			const containerSelector = options.container;

			if (!instance._sidenavCloseSubscription) {
				const closeButton = document.querySelector(`${containerSelector} .sidenav-close`);

				instance._sidenavCloseSubscription = subscribe(
					closeButton,
					'click',
					function handleSidenavClose(event) {
						event.preventDefault();
						instance.toggle();
					}
				);
			}
		},

		_subscribeClickTrigger: function() {
			const instance = this;

			const $toggler = instance.$toggler;

			const togglerSelector = getUniqueSelector($toggler);

			if (!instance._togglerSubscription) {
				const toggler = document.querySelector(togglerSelector);

				instance._togglerSubscription = subscribe(
					toggler,
					'click',
					function handleTogglerClick(event) {
						instance.toggle();

						event.preventDefault();
					}
				);
			}

			instance.togglerSelector = togglerSelector;
		},

		_subscribeSidenavTransitionEnd: function($el, fn) {
			const instance = this;

			const transitionEnd = 'bsTransitionEnd';

			function complete() {
				removeClass($el, 'sidenav-transition');

				if (fn) {
					fn();
				}
			};

			if (!bootstrap.Util.supportsTransitionEnd()) {
				complete.call(instance);
			}
			else {
				$el.one(transitionEnd, function(event) {
					complete();
				})
				.emulateTransitionEnd(SideNavigation.TRANSITION_DURATION);
			}
		},

		_renderNav: function() {
			const instance = this;
			const options = instance.options;

			const $container = $(options.container);
			const $slider = $container.find(options.navigation).first();
			const $menu = $slider.find('.sidenav-menu').first();

			const closed = hasClass($container, 'closed');
			const sidenavRight = instance._isSidenavRight();
			const width = instance._getSidenavWidth();

			if (closed) {
				setStyles($menu, {
					width: px(width),
				});

				if (sidenavRight) {
					const positionDirection = options.rtl ? 'left' : 'right';

					setStyles($menu, {
						[positionDirection]: px(width),
					});
				}
			}
			else {
				instance.showSidenav();
				instance.setHeight();
			}
		},

		_renderUI: function() {
			const instance = this;
			const options = instance.options;

			const $container = $(options.container);
			const $toggler = instance.$toggler;

			const mobile = instance.mobile;
			const type = mobile ? options.typeMobile : options.type;

			if (!instance.useDataAttribute) {
				if (mobile) {
					setClasses($container, {
						closed: true,
						open: false,
					});
					setClasses($toggler, {
						active: false,
						open: false,
					});
				}

				if (options.position === 'right') {
					addClass($container, 'sidenav-right');
				}

				if (type !== 'relative') {
					addClass($container, 'sidenav-fixed');
				}

				instance._renderNav();
			}

			// Force Reflow for IE11 Browser Bug
			setStyles($container, {
				display: '',
			});
		}
	};

	function initialize($toggler, options) {
		const toggler = $toggler.get(0);
		let data = INSTANCE_MAP.get(toggler);

		if (!data) {
			if (!options) {
				options = {};
			}

			data = new SideNavigation($toggler, options);

			INSTANCE_MAP.set(toggler, data);
		}

		return data;
	};

	function Plugin(options) {
		const instance = this;

		let retVal = instance;
		const methodCall = typeof options === 'string';
		const returnInstance = options === 'instance';
		const args = Array.from(arguments).slice(1);

		if (methodCall) {
			this.each(
				function() {
					const data = INSTANCE_MAP.get(this);

					if (data) {
						if (returnInstance) {
							retVal = data;

							return false;
						}

						let methodRetVal;

						if ($.isFunction(data[options]) && options.indexOf('_') !== 0) {
							methodRetVal = data[options].apply(data, args);
						}

						if (methodRetVal !== data && methodRetVal !== undefined) {
							if (methodRetVal.jquery) {
								retVal = retVal.pushStack(methodRetVal.get());
							}
							else {
								retVal = methodRetVal;
							}

							return false;
						}
					}
					else if (returnInstance) {
						retVal = null;

						return false;
					}
				}
			);
		}
		else {
			this.each(
				function() {
					initialize($(this), options);
				}
			);
		}

		return retVal;
	};

	/**
	 * Plugin options
	 * @property {String|Number}  breakpoint   The window width that defines the desktop size.
	 * @property {String}         content      The class or ID of the content container.
	 * @property {String}         container    The class or ID of the sidenav container.
	 * @property {String|Number}  gutter       The space between the sidenav-slider and the sidenav-content.
	 * @property {String}         navigation   The class or ID of the navigation container.
	 * @property {String}         position     The position of the sidenav-slider. Possible values: left, right
	 * @property {String}         type         The type of sidenav in desktop. Possible values: relative, fixed, fixed-push
	 * @property {String}         typeMobile   The type of sidenav in mobile. Possible values: relative, fixed, fixed-push
	 * @property {String|Object}  url          The URL or $.ajax config object to fetch the content to inject into .sidebar-body
	 * @property {String|Number}  width        The width of the side navigation.
	 */
	const defaults = {
		breakpoint: 768,
		content: '.sidenav-content',
		gutter: '15px',
		loadingIndicatorTPL: '<div class="loading-animation loading-animation-md"></div>',
		navigation: '.sidenav-menu-slider',
		position: 'left',
		type: 'relative',
		typeMobile: 'relative',
		url: null,
		width: '225px'
	};

	Plugin.Constructor = SideNavigation;

	$.fn.sideNavigation = Plugin;

	$(function() {
		const $sidenav = $('[data-toggle="sidenav"]');

		Plugin.call($sidenav);
	});
}(jQuery);