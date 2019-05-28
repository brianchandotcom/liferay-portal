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
	var INSTANCE_MAP = new WeakMap();

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

	var $doc = $(document);

	var listenerAdded = false;

	// Make sure we only add one resize listener to the page,
	// no matter how many components we have

	var addResizeListener = function() {
		if (!listenerAdded) {
			$(window).on(
				'resize',
				debounce(
					function(event) {
						Liferay.fire('screenChange.lexicon.sidenav');
					},
					150
				)
			);

			listenerAdded = true;
		}
	};

	var debounce = function(fn, delay) {
		var id;

		return function() {
			var instance = this;

			var args = arguments;

			var later = function() {
				id = null;

				fn.apply(instance, args);
			};

			clearTimeout(id);

			id = setTimeout(later, delay);
		};
	};

	var getBreakpointRegion = function() {
		var screenXs = 480;
		var screenSm = 768;
		var screenMd = 992;
		var screenLg = 1200;

		var windowWidth = window.innerWidth;
		var region = '';

		if (windowWidth >= screenLg) {
			region = 'lg';
		}
		else if (windowWidth >= screenMd) {
			region = 'md';
		}
		else if (windowWidth >= screenSm) {
			region = 'sm';
		}
		else if (windowWidth >= screenXs) {
			region = 'xs';
		}
		else {
			region = 'xxs';
		}

		return region;
	};

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

	var toInt = function(str) {
		return parseInt(str, 10) || 0;
	};

	var SideNavigation = function($toggler, options) {
		this.init($toggler, options);
	};

	SideNavigation.TRANSITION_DURATION = 500;

	SideNavigation.prototype = {
		init: function($toggler, options) {
			var instance = this;

			var useDataAttribute = $toggler.data('toggle') === 'sidenav';

			options = Object.assign({}, defaults, options);

			options.breakpoint = toInt(options.breakpoint);
			options.container = options.container || $toggler.data('target') || $toggler.attr('href');
			options.gutter = toInt(options.gutter);
			options.rtl = $doc.attr('dir') === 'rtl';
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
			var instance = this;

			var options = instance.options;

			var $container = $(options.container);

			var $content = $container.find(options.content).first();
			var $navigation = $container.find(options.navigation).first();
			var $menu = $container.find('.sidenav-menu').first();

			[$content, $navigation, $menu].forEach($element => {
				setStyles($element, {
					height: '',
					'min-height': '',
				});
			});
		},

		destroy: function() {
			var instance = this;

			var options = instance.options;

			var $container = $(options.container);

			// Detach sidenav close

			$doc.off('click.close.lexicon.sidenav', instance.closeButtonSelector);
			$doc.data(instance.dataCloseButtonSelector, null);

			// Detach toggler

			$doc.off('click.lexicon.sidenav', instance.togglerSelector);
			$doc.data(instance.dataTogglerSelector, null);

			// Remove Side Navigation

			INSTANCE_MAP.delete(this.$toggler.get(0));
		},

		hide: function() {
			var instance = this;

			if (instance.useDataAttribute) {
				instance.hideSimpleSidenav();
			}
			else {
				instance.toggleNavigation(false);
			}
		},

		hideSidenav: function() {
			var instance = this;
			var options = instance.options;

			var $container = $(options.container);
			var $content = $container.find(options.content).first();
			var $navigation = $container.find(options.navigation).first();
			var $menu = $navigation.find('.sidenav-menu').first();

			var sidenavRight = instance._isSidenavRight();

			var positionDirection = options.rtl ? 'right' : 'left';

			if (sidenavRight) {
				positionDirection = options.rtl ? 'left' : 'right';
			}

			var paddingDirection = 'padding-' + positionDirection;

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
			var instance = this;

			var options = instance.options;

			var simpleSidenavClosed = instance._isSimpleSidenavClosed();

			if (!simpleSidenavClosed) {
				var $content = $(options.content).first();
				var $sidenav = $(options.container);

				var closedClass = options.closedClass;
				var openClass = options.openClass;

				var $toggler = instance.$toggler;

				var target = $toggler.attr('data-target') || $toggler.attr('href');

				$sidenav.trigger({
					toggler: $(instance.togglerSelector),
					type: 'closedStart.lexicon.sidenav'
				});

				instance._onSidenavTransitionEnd($content, function() {
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
			var instance = this;

			var options = instance.options;

			var $container = $(options.container);
			var content = options.content;
			var navigation = options.navigation;

			var type = instance.mobile ? options.typeMobile : options.type;

			if (type !== 'fixed' && type !== 'fixed-push') {
				var $contentNode = $container.find(content).first();
				var $navNode = $container.find(navigation).first();
				var $sideNavMenuNode = $container.find('.sidenav-menu').first();

				var tallest = px(Math.max($contentNode.outerHeight(), $navNode.outerHeight()));

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
			var instance = this;

			if (instance.useDataAttribute) {
				instance.showSimpleSidenav();
			}
			else {
				instance.toggleNavigation(true);
			}
		},

		showSidenav: function() {
			var instance = this;
			var mobile = instance.mobile;
			var options = instance.options;

			var $container = $(options.container);
			var $content = $container.find(options.content).first();
			var $navigation = $container.find(options.navigation).first();
			var $menu = $navigation.find('.sidenav-menu').first();

			var sidenavRight = instance._isSidenavRight();
			var width = instance._getSidenavWidth();

			var offset = width + options.gutter;

			var url = options.url;

			if (url) {
				instance._loadUrl($menu, url);
			}

			setStyles($navigation, {
				width: px(width),
			});
			setStyles($menu, {
				width: px(width),
			});

			var positionDirection = options.rtl ? 'right' : 'left';

			if (sidenavRight) {
				positionDirection = options.rtl ? 'left' : 'right';
			}

			var paddingDirection = 'padding-' + positionDirection;

			var pushContentCssProperty = mobile ? positionDirection : paddingDirection;
			var type = mobile ? options.typeMobile : options.type;

			if (type !== 'fixed') {
				var navigationStartX = hasClass($container, 'open') ? $navigation.offset().left - options.gutter : $navigation.offset().left - offset;

				var contentStartX = $content.offset().left;
				var contentWidth = $content.innerWidth();

				var padding = '';

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
			var instance = this;

			var options = instance.options;

			var simpleSidenavClosed = instance._isSimpleSidenavClosed();

			if (simpleSidenavClosed) {
				var $content = $(options.content).first();
				var $sidenav = $(options.container);

				var closedClass = options.closedClass;
				var openClass = options.openClass;

				var $toggler = options.$toggler;

				var url = $toggler.data('url');

				if (url) {
					instance._loadUrl($sidenav, url);
				}

				$sidenav.trigger({
					toggler: $(instance.togglerSelector),
					type: 'openStart.lexicon.sidenav'
				});

				instance._onSidenavTransitionEnd($content, function() {
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
			var instance = this;

			if (instance.useDataAttribute) {
				instance.toggleSimpleSidenav();
			}
			else {
				instance.toggleNavigation();
			}
		},

		toggleNavigation: function(force) {
			var instance = this;
			var options = instance.options;

			var $container = $(options.container);
			var $menu = $container.find('.sidenav-menu').first();
			var $toggler = instance.$toggler;

			var width = options.width;

			var closed = typeof force === 'boolean' ? force : hasClass($container, 'closed');
			var sidenavRight = instance._isSidenavRight();

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

			instance._onSidenavTransitionEnd($container, function() {
				var $menu = $container.find('.sidenav-menu').first();

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

				var positionDirection = options.rtl ? 'left' : 'right';

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
			var instance = this;

			var simpleSidenavClosed = instance._isSimpleSidenavClosed();

			if (simpleSidenavClosed) {
				instance.showSimpleSidenav();
			}
			else {
				instance.hideSimpleSidenav();
			}
		},

		visible: function() {
			var instance = this;

			var closed;

			if (instance.useDataAttribute) {
				closed = instance._isSimpleSidenavClosed();
			}
			else {
				var $container = $(instance.options.container);

				closed = hasClass($container, 'sidenav-transition') ? !hasClass($container, 'closed') : hasClass($container, 'closed');
			}

			return !closed;
		},

		_bindUI: function() {
			var instance = this;

			if (!instance.useDataAttribute) {
				addResizeListener();
				instance._onScreenChange();
			}

			instance._onClickTrigger();

			instance._onClickSidenavClose();
		},

		_getSidenavWidth: function() {
			var instance = this;

			var options = instance.options;

			var widthOriginal = options.widthOriginal;

			var width = widthOriginal;
			var winWidth = window.innerWidth;

			if (winWidth < widthOriginal + 40) {
				width = winWidth - 40;
			}

			return width;
		},

		_getSimpleSidenavType: function() {
			var instance = this;

			var options = instance.options;

			var desktop = instance._isDesktop();
			var type = options.type;
			var typeMobile = options.typeMobile;

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
			var instance = this;
			var options = instance.options;

			var $container = $(options.container);
			var isSidenavRight = hasClass($container, 'sidenav-right');

			return isSidenavRight;
		},

		_isSimpleSidenavClosed: function() {
			var instance = this;
			var options = instance.options;

			var openClass = options.openClass;

			var $container = $(options.container);

			return !hasClass($container, openClass);
		},

		_loadUrl: function($sidenav, url) {
			var instance = this;

			var $sidebarBody = $sidenav.find('.sidebar-body').first();

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

		_onClickSidenavClose: function() {
			var instance = this;

			var options = instance.options;

			var containerSelector = options.container;

			var $closeButton = $(containerSelector).find('.sidenav-close').first();
			var closeButtonSelector = getUniqueSelector($closeButton);
			var dataCloseButtonSelector = 'lexicon.' + closeButtonSelector;

			if (!$doc.data(dataCloseButtonSelector)) {
				$doc.data(dataCloseButtonSelector, 'true');

				$doc.on('click.close.lexicon.sidenav', closeButtonSelector, function(event) {
					event.preventDefault();

					instance.toggle();
				});
			}

			instance.closeButtonSelector = closeButtonSelector;
			instance.dataCloseButtonSelector = dataCloseButtonSelector;
		},

		_onClickTrigger: function() {
			var instance = this;

			var $toggler = instance.$toggler;

			var togglerSelector = getUniqueSelector($toggler);

			var dataTogglerSelector = 'lexicon.' + togglerSelector;

			if (!$doc.data(dataTogglerSelector)) {
				$doc.data(dataTogglerSelector, 'true');

				$doc.on(
					'click.lexicon.sidenav',
					togglerSelector,
					function(event) {
						instance.toggle();

						event.preventDefault();
					}
				);
			}

			instance.togglerSelector = togglerSelector;
			instance.dataTogglerSelector = dataTogglerSelector;
		},

		_onScreenChange: function() {
			var instance = this;
			var options = instance.options;

			var $container = $(options.container);
			var $toggler = instance.$toggler;

			var screenStartDesktop = instance._setScreenSize();

			Liferay.on('screenChange.lexicon.sidenav', function(event) {
				var desktop = instance._setScreenSize();
				var sidenavRight = instance._isSidenavRight();
				var type = desktop ? options.type : options.typeMobile;

				var fixedMenu = type === 'fixed' || type === 'fixed-push';

				var $menu = $container.find('.sidenav-menu').first();

				var menuWidth;

				var originalMenuWidth = options.widthOriginal;

				var positionDirection = options.rtl ? 'left' : 'right';

				setClasses($container, {
					'sidenav-fixed': fixedMenu,
				});

				if ((!desktop && screenStartDesktop) || (desktop && !screenStartDesktop)) {
					instance.hideSidenav();

					instance.clearHeight();

					setClasses($container, {
						closed: true,
						open: false,
					});
					setClasses($toggler, {
						active: false,
						open: false,
					});

					screenStartDesktop = false;

					if (desktop) {
						if (sidenavRight) {
							setStyles($menu, {
								[positionDirection]: px(originalMenuWidth),
								width: px(originalMenuWidth),
							});
						}

						screenStartDesktop = true;
					}
				}

				var closed = hasClass($container, 'closed');

				if (!desktop) {
					menuWidth = originalMenuWidth;

					if (window.innerWidth <= originalMenuWidth) {
						menuWidth = window.innerWidth - options.gutter - 25;
					}

					if (sidenavRight) {
						if (closed) {
							setStyles($menu, {
								[positionDirection]: px(menuWidth),
							});
						}

						setStyles($menu, {
							width: px(menuWidth),
						});
					}

					screenStartDesktop = false;
				}

				if (!closed) {
					instance.clearHeight();

					instance.showSidenav();
					instance.setHeight();
				}
			});
		},

		_onSidenavTransitionEnd: function($el, fn) {
			var instance = this;

			var transitionEnd = 'bsTransitionEnd';

			var complete = function() {
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
			var instance = this;
			var options = instance.options;

			var $container = $(options.container);
			var $slider = $container.find(options.navigation).first();
			var $menu = $slider.find('.sidenav-menu').first();

			var closed = hasClass($container, 'closed');
			var sidenavRight = instance._isSidenavRight();
			var width = instance._getSidenavWidth();

			if (closed) {
				setStyles($menu, {
					width: px(width),
				});

				if (sidenavRight) {
					var positionDirection = options.rtl ? 'left' : 'right';

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
			var instance = this;
			var options = instance.options;

			var $container = $(options.container);
			var $toggler = instance.$toggler;

			var mobile = instance.mobile;
			var type = mobile ? options.typeMobile : options.type;

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
		},

		_setScreenSize: function() {
			var instance = this;

			var screenSize = getBreakpointRegion();

			var desktop = screenSize === 'sm' || screenSize === 'md' || screenSize === 'lg';

			instance.mobile = !desktop;
			instance.desktop = desktop;

			return desktop;
		}
	};

	var initialize = function($toggler, options) {
		var toggler = $toggler.get(0);
		var data = INSTANCE_MAP.get(toggler);

		if (!data) {
			if (!options) {
				options = {};
			}

			data = new SideNavigation($toggler, options);

			INSTANCE_MAP.set(toggler, data);
		}

		return data;
	};

	var Plugin = function(options) {
		var instance = this;

		var retVal = instance;
		var methodCall = typeof options === 'string';
		var returnInstance = options === 'instance';
		var args = Array.from(arguments).slice(1);

		if (methodCall) {
			this.each(
				function() {
					var data = INSTANCE_MAP.get(this);

					if (data) {
						if (returnInstance) {
							retVal = data;

							return false;
						}

						var methodRetVal;

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
	var defaults = {
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
		var $sidenav = $('[data-toggle="sidenav"]');

		Plugin.call($sidenav);
	});
}(jQuery);