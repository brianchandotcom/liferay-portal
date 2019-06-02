import EventEmitter from 'metal-events';

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
	if (element && element.jquery) {
		if (element.length > 1) {
			throw new Error(
				`getElement(): Expected at most one element, got ${
					element.length
				}`
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

function getInstance(element) {
	element = getElement(element);

	const instance = INSTANCE_MAP.get(element);

	return instance;
}

function addClass(element, className) {
	setClasses(element, {
		[className]: true
	});
}

function removeClass(element, className) {
	setClasses(element, {
		[className]: false
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
	} else if (
		typeof dimension === 'string' &&
		dimension.match(/^\s*\d+\s*$/)
	) {
		return dimension.trim() + 'px';
	} else {
		return dimension;
	}
}

/**
 * Replacement for jQuery's `offset().left`.
 *
 * @see: https://github.com/jquery/jquery/blob/438b1a3e8a52/src/offset.js#L94-L100
 */
function offsetLeft(element) {
	const elementLeft = element.getBoundingClientRect().left;

	const documentOffset = element.ownerDocument.defaultView.pageOffsetX || 0;

	return elementLeft + documentOffset;
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

function toInt(str) {
	return parseInt(str, 10) || 0;
}

function SideNavigation(toggler, options) {
	toggler = getElement(toggler);
	this.init(toggler, options);
}

SideNavigation.TRANSITION_DURATION = 500;

SideNavigation.prototype = {
	init: function(toggler, options) {
		const instance = this;

		const useDataAttribute = toggler.dataset.toggle === 'sidenav';

		options = Object.assign({}, defaults, options);

		options.breakpoint = toInt(options.breakpoint);
		options.container =
			options.container ||
			toggler.dataset.target ||
			toggler.getAttribute('href');
		options.gutter = toInt(options.gutter);
		options.rtl = document.dir === 'rtl';
		options.width = toInt(options.width);
		options.widthOriginal = options.width;

		// instantiate using data attribute

		if (useDataAttribute) {
			options.closedClass = toggler.dataset.closedClass || 'closed';
			options.content = toggler.dataset.content;
			options.loadingIndicatorTPL =
				toggler.dataset.loadingIndicatorTpl ||
				options.loadingIndicatorTPL;
			options.openClass = toggler.dataset.openClass || 'open';
			options.type = toggler.dataset.type;
			options.typeMobile = toggler.dataset.typeMobile;
			options.url = toggler.dataset.url;
			options.width = '';
		}

		instance.toggler = toggler;
		instance.options = options;
		instance.useDataAttribute = useDataAttribute;

		instance._emitter = new EventEmitter();

		instance._bindUI();
		instance._renderUI();
	},

	on: function(event, listener) {
		return this._emitter.on(event, listener);
	},

	_emit: function(event) {
		this._emitter.emit(event, this);
	},

	clearHeight: function() {
		const instance = this;

		const options = instance.options;
		const container = document.querySelector(options.container);

		if (container) {
			const content = container.querySelector(options.content);
			const navigation = container.querySelector(options.navigation);
			const menu = container.querySelector('.sidenav-menu');

			[content, navigation, menu].forEach(element => {
				setStyles(element, {
					height: '',
					'min-height': ''
				});
			});
		}
	},

	destroy: function() {
		const instance = this;

		const options = instance.options;

		if (instance._sidenavCloseSubscription) {
			instance._sidenavCloseSubscription.dispose();
			instance._sidenavCloseSubscription = null;
		}

		if (instance._togglerSubscription) {
			instance._togglerSubscription.dispose();
			instance._togglerSubscription = null;
		}

		INSTANCE_MAP.delete(instance.toggler);
	},

	hide: function() {
		const instance = this;

		if (instance.useDataAttribute) {
			instance.hideSimpleSidenav();
		} else {
			instance.toggleNavigation(false);
		}
	},

	hideSidenav: function() {
		const instance = this;
		const options = instance.options;

		const container = document.querySelector(options.container);

		if (container) {
			const content = container.querySelector(options.content);
			const navigation = container.querySelector(options.navigation);
			const menu = navigation.querySelector('.sidenav-menu');

			const sidenavRight = instance._isSidenavRight();

			let positionDirection = options.rtl ? 'right' : 'left';

			if (sidenavRight) {
				positionDirection = options.rtl ? 'left' : 'right';
			}

			const paddingDirection = 'padding-' + positionDirection;

			setStyles(content, {
				[paddingDirection]: '',
				[positionDirection]: ''
			});

			setStyles(navigation, {
				width: ''
			});

			if (sidenavRight) {
				setStyles(menu, {
					[positionDirection]: px(instance._getSidenavWidth())
				});
			}
		}
	},

	hideSimpleSidenav: function() {
		const instance = this;

		const options = instance.options;

		const simpleSidenavClosed = instance._isSimpleSidenavClosed();

		if (!simpleSidenavClosed) {
			const content = document.querySelector(options.content);
			const container = document.querySelector(options.container);

			const closedClass = options.closedClass;
			const openClass = options.openClass;

			const toggler = instance.toggler;

			const target =
				toggler.dataset.target || toggler.getAttribute('href');

			instance._emit('closedStart.lexicon.sidenav');

			instance._subscribeSidenavTransitionEnd(content, function() {
				removeClass(container, 'sidenav-transition');
				removeClass(toggler, 'sidenav-transition');

				instance._emit('closed.lexicon.sidenav');
			});

			if (hasClass(content, openClass)) {
				setClasses(content, {
					'sidenav-transition': true,
					[closedClass]: true,
					[openClass]: false
				});
			}

			addClass(container, 'sidenav-transition');
			addClass(toggler, 'sidenav-transition');

			setClasses(container, {
				[closedClass]: true,
				[openClass]: false
			});

			const nodes = document.querySelectorAll(
				`[data-target="${target}"], [href="${target}"]`
			);

			Array.from(nodes).forEach(node => {
				setClasses(node, {
					[openClass]: false,
					active: false
				});
				setClasses(node, {
					[openClass]: false,
					active: false
				});
			});
		}
	},

	setHeight: function() {
		const instance = this;

		const options = instance.options;

		const container = document.querySelector(options.container);

		const type = instance.mobile ? options.typeMobile : options.type;

		if (type !== 'fixed' && type !== 'fixed-push') {
			const content = container.querySelector(options.content);
			const navigation = container.querySelector(options.navigation);
			const menu = container.querySelector('.sidenav-menu');

			const contentHeight = content.getBoundingClientRect().height;
			const navigationHeight = navigation.getBoundingClientRect().height;

			const tallest = px(Math.max(contentHeight, navigationHeight));

			setStyles(content, {
				'min-height': tallest
			});

			setStyles(navigation, {
				height: '100%',
				'min-height': tallest
			});

			setStyles(menu, {
				height: '100%',
				'min-height': tallest
			});
		}
	},

	show: function() {
		const instance = this;

		if (instance.useDataAttribute) {
			instance.showSimpleSidenav();
		} else {
			instance.toggleNavigation(true);
		}
	},

	showSidenav: function() {
		const instance = this;
		const mobile = instance.mobile;
		const options = instance.options;

		const container = document.querySelector(options.container);
		const content = container.querySelector(options.content);
		const navigation = container.querySelector(options.navigation);
		const menu = navigation.querySelector('.sidenav-menu');

		const sidenavRight = instance._isSidenavRight();
		const width = instance._getSidenavWidth();

		const offset = width + options.gutter;

		const url = options.url;

		if (url) {
			instance._loadUrl(menu, url);
		}

		setStyles(navigation, {
			width: px(width)
		});

		setStyles(menu, {
			width: px(width)
		});

		let positionDirection = options.rtl ? 'right' : 'left';

		if (sidenavRight) {
			positionDirection = options.rtl ? 'left' : 'right';
		}

		const paddingDirection = 'padding-' + positionDirection;

		const pushContentCssProperty = mobile
			? positionDirection
			: paddingDirection;
		const type = mobile ? options.typeMobile : options.type;

		if (type !== 'fixed') {
			const navigationStartX = hasClass(container, 'open')
				? offsetLeft(navigation) - options.gutter
				: offsetLeft(navigation) - offset;

			const contentStartX = offsetLeft(content);
			const contentWidth = toInt(getComputedStyle(content).width);

			let padding = '';

			if (
				(options.rtl && sidenavRight) ||
				(!options.rtl && options.position === 'left')
			) {
				navigationStartX = offsetLeft(navigation) + offset;

				if (navigationStartX > contentStartX) {
					padding = navigationStartX - contentStartX;
				}
			} else if (
				(options.rtl && options.position === 'left') ||
				(!options.rtl && sidenavRight)
			) {
				if (navigationStartX < contentStartX + contentWidth) {
					padding = contentStartX + contentWidth - navigationStartX;

					if (padding >= offset) {
						padding = offset;
					}
				}
			}

			setStyles(content, {
				[pushContentCssProperty]: px(padding)
			});
		}
	},

	showSimpleSidenav: function() {
		const instance = this;

		const options = instance.options;

		const simpleSidenavClosed = instance._isSimpleSidenavClosed();

		if (simpleSidenavClosed) {
			const content = document.querySelector(options.content);
			const container = document.querySelector(options.container);

			const closedClass = options.closedClass;
			const openClass = options.openClass;

			const toggler = instance.toggler;

			const url = toggler.dataset.url;

			if (url) {
				instance._loadUrl(container, url);
			}

			instance._emit('openStart.lexicon.sidenav');

			instance._subscribeSidenavTransitionEnd(content, function() {
				removeClass(container, 'sidenav-transition');
				removeClass(toggler, 'sidenav-transition');

				instance._emit('open.lexicon.sidenav');
			});

			setClasses(content, {
				'sidenav-transition': true,
				[openClass]: true,
				[closedClass]: false
			});
			setClasses(container, {
				'sidenav-transition': true,
				[openClass]: true,
				[closedClass]: false
			});
			setClasses(toggler, {
				'sidenav-transition': true,
				active: true,
				[openClass]: true
			});
		}
	},

	toggle: function() {
		const instance = this;

		if (instance.useDataAttribute) {
			instance.toggleSimpleSidenav();
		} else {
			instance.toggleNavigation();
		}
	},

	toggleNavigation: function(force) {
		const instance = this;
		const options = instance.options;

		const container = document.querySelector(options.container);
		const menu = container.querySelector('.sidenav-menu');
		const toggler = instance.toggler;

		const width = options.width;

		const closed =
			typeof force === 'boolean' ? force : hasClass(container, 'closed');
		const sidenavRight = instance._isSidenavRight();

		if (closed) {
			instance._emit('openStart.lexicon.sidenav');
		} else {
			instance._emit('closedStart.lexicon.sidenav');
		}

		instance._subscribeSidenavTransitionEnd(container, function() {
			const menu = container.querySelector('.sidenav-menu');

			if (hasClass(container, 'closed')) {
				instance.clearHeight();

				setClasses(toggler, {
					open: false,
					'sidenav-transition': false
				});

				instance._emit('closed.lexicon.sidenav');
			} else {
				setClasses(toggler, {
					open: true,
					'sidenav-transition': false
				});

				instance._emit('open.lexicon.sidenav');
			}

			if (instance.mobile) {
				// ios 8 fixed element disappears when trying to scroll
				menu.focus();
			}
		});

		if (closed) {
			instance.setHeight();

			setStyles(menu, {
				width: px(width)
			});

			const positionDirection = options.rtl ? 'left' : 'right';

			if (sidenavRight) {
				setStyles(menu, {
					[positionDirection]: ''
				});
			}
		}

		addClass(container, 'sidenav-transition');
		addClass(toggler, 'sidenav-transition');

		if (closed) {
			instance.showSidenav();
		} else {
			instance.hideSidenav();
		}

		setClasses(container, {
			closed: !closed,
			open: closed
		});

		setClasses(toggler, {
			active: closed,
			open: closed
		});
	},

	toggleSimpleSidenav: function() {
		const instance = this;

		const simpleSidenavClosed = instance._isSimpleSidenavClosed();

		if (simpleSidenavClosed) {
			instance.showSimpleSidenav();
		} else {
			instance.hideSimpleSidenav();
		}
	},

	visible: function() {
		const instance = this;

		let closed;

		if (instance.useDataAttribute) {
			closed = instance._isSimpleSidenavClosed();
		} else {
			const container = document.querySelector(
				instance.options.container
			);

			closed = hasClass(container, 'sidenav-transition')
				? !hasClass(container, 'closed')
				: hasClass(container, 'closed');
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

		if (desktop && type === 'fixed-push') {
			return 'desktop-fixed-push';
		} else if (!desktop && typeMobile === 'fixed-push') {
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

		const container = document.querySelector(options.container);
		const isSidenavRight = hasClass(container, 'sidenav-right');

		return isSidenavRight;
	},

	_isSimpleSidenavClosed: function() {
		const instance = this;
		const options = instance.options;

		const openClass = options.openClass;

		const container = document.querySelector(options.container);

		return !hasClass(container, openClass);
	},

	_loadUrl: function(element, url) {
		const instance = this;

		const sidebar = element.querySelector('.sidebar-body');

		if (!instance._fetchPromise && sidebar) {
			const loading = document.createElement('div');
			addClass(loading, 'sidenav-loading');
			loading.innerHTML = instance.options.loadingIndicatorTPL;

			sidebar.appendChild(loading);
			instance._fetchPromise = fetch(url);

			instance._fetchPromise
				.then(response => {
					if (!response.ok) {
						throw new Error(`Failed to fetch ${url}`);
					}
					return response.text();
				})
				.then(text => {
					const range = document.createRange();

					range.selectNode(sidebar);

					// Unlike `.innerHTML`, this will eval scripts.
					const fragment = range.createContextualFragment(text);

					sidebar.removeChild(loading);

					sidebar.appendChild(fragment);

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
			const closeButton = document.querySelector(
				`${containerSelector} .sidenav-close`
			);

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

		if (!instance._togglerSubscription) {
			const toggler = instance.toggler;

			instance._togglerSubscription = subscribe(
				toggler,
				'click',
				function handleTogglerClick(event) {
					instance.toggle();

					event.preventDefault();
				}
			);
		}
	},

	_subscribeSidenavTransitionEnd: function(element, fn) {
		setTimeout(() => {
			removeClass(element, 'sidenav-transition');

			fn();
		}, SideNavigation.TRANSITION_DURATION);
	},

	_renderNav: function() {
		const instance = this;
		const options = instance.options;

		const container = document.querySelector(options.container);
		const navigation = container.querySelector(options.navigation);
		const menu = navigation.querySelector('.sidenav-menu');

		const closed = hasClass(container, 'closed');
		const sidenavRight = instance._isSidenavRight();
		const width = instance._getSidenavWidth();

		if (closed) {
			setStyles(menu, {
				width: px(width)
			});

			if (sidenavRight) {
				const positionDirection = options.rtl ? 'left' : 'right';

				setStyles(menu, {
					[positionDirection]: px(width)
				});
			}
		} else {
			instance.showSidenav();
			instance.setHeight();
		}
	},

	_renderUI: function() {
		const instance = this;
		const options = instance.options;

		const container = document.querySelector(options.container);
		const toggler = instance.toggler;

		const mobile = instance.mobile;
		const type = mobile ? options.typeMobile : options.type;

		if (!instance.useDataAttribute) {
			if (mobile) {
				setClasses(container, {
					closed: true,
					open: false
				});

				setClasses(toggler, {
					active: false,
					open: false
				});
			}

			if (options.position === 'right') {
				addClass(container, 'sidenav-right');
			}

			if (type !== 'relative') {
				addClass(container, 'sidenav-fixed');
			}

			instance._renderNav();
		}

		// Force Reflow for IE11 Browser Bug
		setStyles(container, {
			display: ''
		});
	}
};

SideNavigation.destroy = function destroy(element) {
	const instance = getInstance(element);

	if (instance) {
		instance.destroy();
	}
};

SideNavigation.hide = function hide(element) {
	const instance = getInstance(element);

	if (instance) {
		instance.hide();
	}
};

SideNavigation.initialize = function initialize(toggler, options = {}) {
	toggler = getElement(toggler);

	let instance = INSTANCE_MAP.get(toggler);

	if (!instance) {
		instance = new SideNavigation(toggler, options);

		INSTANCE_MAP.set(toggler, instance);
	}

	return instance;
};

SideNavigation.instance = getInstance;

/**
 * Options
 *
 * @property {String|Number}  breakpoint   The window width that defines the desktop size.
 * @property {String}         content      The class or ID of the content container.
 * @property {String}         container    The class or ID of the sidenav container.
 * @property {String|Number}  gutter       The space between the sidenav-slider and the sidenav-content.
 * @property {String}         navigation   The class or ID of the navigation container.
 * @property {String}         position     The position of the sidenav-slider. Possible values: left, right
 * @property {String}         type         The type of sidenav in desktop. Possible values: relative, fixed, fixed-push
 * @property {String}         typeMobile   The type of sidenav in mobile. Possible values: relative, fixed, fixed-push
 * @property {String|Object}  url          The URL to fetch the content to inject into .sidebar-body
 * @property {String|Number}  width        The width of the side navigation.
 */
const defaults = {
	breakpoint: 768,
	content: '.sidenav-content',
	gutter: '15px',
	loadingIndicatorTPL:
		'<div class="loading-animation loading-animation-md"></div>',
	navigation: '.sidenav-menu-slider',
	position: 'left',
	type: 'relative',
	typeMobile: 'relative',
	url: null,
	width: '225px'
};

function onReady() {
	const togglers = document.querySelectorAll('[data-toggle="sidenav"]');

	Array.from(togglers).forEach(SideNavigation.initialize);
}

if (document.readyState !== 'loading') {
	// readyState is "interactive" or "complete".
	onReady();
} else {
	document.addEventListener('DOMContentLoaded', () => {
		onReady();
	});
}

export default SideNavigation;
