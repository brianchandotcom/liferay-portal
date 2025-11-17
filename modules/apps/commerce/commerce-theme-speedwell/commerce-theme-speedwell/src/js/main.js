/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

document.addEventListener('DOMContentLoaded', () => {
	const Speedwell = window.Speedwell;

	const CURRENT = 'current';
	const NEXT = 'next';
	const WILL_BE_NEXT = 'will-be-next';

	const STATES_MAP = {
		[CURRENT]: {
			backwards: NEXT,
			forwards: WILL_BE_NEXT,
		},
		[NEXT]: {
			backwards: WILL_BE_NEXT,
			forwards: CURRENT,
		},
		[WILL_BE_NEXT]: {
			backwards: CURRENT,
			forwards: NEXT,
		},
	};

	const BACKWARDS = 'backwards';
	const FORWARDS = 'forwards';

	function validateInterval(interval) {
		const MIN = 4000;

		if (interval > 0 && interval <= MIN) {
			return MIN;
		}
		else if (interval > MIN) {
			return interval;
		}

		return null;
	}

	const SpeedwellSlider = function (
		sliderContainer,
		setupDOMSlideFn,
		renderSlideContentFn,
		interval
	) {
		this.sliderWrapper = sliderContainer;
		this.setupDOMSlide = setupDOMSlideFn;
		this.renderSlideContent = renderSlideContentFn;
		this.interval = validateInterval(interval);

		if (this.sliderWrapper) {
			this.init();
		}
		else {
			throw new Error('Container not found.');
		}
	};

	SpeedwellSlider.prototype = {
		applyAnimation(direction, nextSlideContent) {
			return new Promise((restoreInteraction) => {
				const currentSlide = this.sliderWrapper.querySelector(
					'[data-state="current"]'
				);

				currentSlide.addEventListener(
					'webkitTransitionEnd',
					this.handleSlideChange.bind(
						this,
						direction,
						restoreInteraction,
						nextSlideContent
					),
					{once: true}
				);

				this.slides.forEach((slide) => {
					slide.classList.add(`is-sliding-${direction}`);
				});
			});
		},
		attachListeners() {
			this.controls = {
				container: this.sliderWrapper.querySelector(
					'div[class*=controls]'
				),
				nextBtn: this.sliderWrapper.querySelector(
					'button[class*=control--next]'
				),
				prevBtn: this.sliderWrapper.querySelector(
					'button[class*=control--prev]'
				),
			};

			this.controls.prevBtn.addEventListener(
				'click',
				this.throttleInteraction.bind(this),
				true
			);
			this.controls.nextBtn.addEventListener(
				'click',
				this.throttleInteraction.bind(this),
				true
			);

			this.checkControls();
		},

		checkControls() {
			if (this.interval) {
				this.controls.container.classList.add('self-sliding');
				this.interval = setInterval(() => {
					this.throttleInteraction(null);
				}, this.interval);
			}
		},

		constructor: SpeedwellSlider,
		controls: {
			nextBtn: null,
			prevBtn: null,
		},
		dataset: [],
		datasetSize: null,
		defaultSetup() {
			const dataset = this.dataset;
			const that = this;

			Object.keys(STATES_MAP).forEach((state, index) => {
				that.setupDOMSlide(state, index, dataset);
				that.stateCycleMap[state] = dataset[index];
			});
		},
		didPrepare(nextSlideContent) {
			return Promise.resolve(nextSlideContent);
		},
		getNextSlideContent(direction) {
			const nextSlideIndex =
				direction === FORWARDS
					? this.stateCycleMap[WILL_BE_NEXT].index + 1
					: this.stateCycleMap[CURRENT].index - 1;

			if (this.dataset[nextSlideIndex]) {
				return this.dataset[nextSlideIndex];
			}

			return direction === FORWARDS
				? this.dataset[0]
				: this.dataset[this.datasetSize - 1];
		},
		handleSlideChange(direction, restoreInteraction, nextSlideContent) {
			this.slides.forEach((slide) => this.setNextState(direction, slide));

			const $prepare = nextSlideContent
				? this.didPrepare(nextSlideContent)
				: this.prepareNow(direction);

			$prepare.then((slideContent) => {
				this.updateStateCycle(direction, slideContent);
				if (restoreInteraction) {
					restoreInteraction({isEnabled: true});
				}
			});
		},
		init() {
			this.setupData()
				.then(this.setupSliders.bind(this))
				.then(this.attachListeners.bind(this))
				.catch((error) => {
					const errorMessage =
						`Request code: ${error.statusCode.toString()}` ||
						'API error';

					Liferay.Util.openToast({
						message: errorMessage,
						title: '',
						type: 'danger',
					});
				});
		},

		interval: null,

		oneSlideSetup() {
			this.removeControls();
			this.setupDOMSlide(CURRENT, 0);

			this.sliderWrapper
				.querySelector('[data-state=current]')
				.classList.add('is-single-slide');
		},
		prepareLater() {
			return Promise.resolve(null);
		},

		prepareNextSlide(direction) {
			return new Promise((resolve) => {
				const nextSlideContent = this.getNextSlideContent(direction);

				this.renderSlideContent(this.sliderWrapper, nextSlideContent);

				resolve(nextSlideContent);
			});
		},

		prepareNow(direction) {
			return this.prepareNextSlide(direction);
		},
		removeControls() {
			const controlsElement = this.sliderWrapper.querySelector(
				'div[class*=controls]'
			);

			controlsElement.remove();
		},
		renderSlideContent() {},
		setNextState(direction, slide) {
			slide.classList.remove(`is-sliding-${direction}`);
			slide.dataset.state = STATES_MAP[slide.dataset.state][direction];
		},
		setupDOMSlide() {},

		setupData() {
			return new Promise((resolve, reject) => {
				try {
					const ldJson =
						this.sliderWrapper.querySelector(
							'.slider-dataset'
						).innerText;
					this.dataset = this.validateDataset(JSON.parse(ldJson));

					this.dataset.forEach((object, index) => {
						object.index = index;
					});

					this.datasetSize = this.dataset.length;
					resolve();
				}
				catch (error) {
					reject(new Error(error));
				}
			});
		},

		setupSliders() {
			return new Promise((resolve, reject) => {
				if (this.datasetSize === 0) {
					reject(new Error('No dataset size.'));
				}
				else if (this.datasetSize === 1) {
					this.oneSlideSetup();
				}
				else if (this.datasetSize === 2) {
					this.twoSlidesSetup();
				}
				else {
					this.defaultSetup();
				}

				this.slides = Array.from(
					this.sliderWrapper.querySelectorAll('[data-state]')
				);

				resolve();
			});
		},
		slides: [],
		stateCycleMap: {},

		throttleInteraction(event) {
			const direction =
				event instanceof Event &&
				event.currentTarget.className.indexOf('prev') > -1
					? BACKWARDS
					: FORWARDS;
			const prepare =
				direction === BACKWARDS
					? this.prepareNow.bind(this)
					: this.prepareLater;

			this.toggleControls({isEnabled: false});

			prepare(direction)
				.then((nextSlideContent) =>
					this.applyAnimation(direction, nextSlideContent)
				)
				.then((restore) => this.toggleControls(restore));
		},

		toggleControls({isEnabled}) {
			if (isEnabled) {
				this.controls.prevBtn.removeAttribute('disabled');
				this.controls.nextBtn.removeAttribute('disabled');
			}
			else {
				this.controls.prevBtn.setAttribute('disabled', isEnabled);
				this.controls.nextBtn.setAttribute('disabled', isEnabled);
			}
		},

		twoSlidesSetup() {
			this.dataset.push(this.dataset[0]);
			this.defaultSetup();
		},

		updateStateCycle(direction, nextSlideContent) {
			if (direction === FORWARDS) {
				this.stateCycleMap[CURRENT] = this.stateCycleMap[NEXT];
				this.stateCycleMap[NEXT] = this.stateCycleMap[WILL_BE_NEXT];
				this.stateCycleMap[WILL_BE_NEXT] = nextSlideContent;
			}
			else {
				this.stateCycleMap[WILL_BE_NEXT] = this.stateCycleMap[NEXT];
				this.stateCycleMap[NEXT] = this.stateCycleMap[CURRENT];
				this.stateCycleMap[CURRENT] = nextSlideContent;
			}
		},

		validateDataset(data) {
			return data;
		},
	};

	Liferay.component(
		'SpeedwellSlider',
		(function () {
			return {
				initialize(setupDOMSlideFn, renderSlideContentFn, interval) {
					const sliderContainer =
						window.document.querySelector('[data-will-load]');

					sliderContainer.removeAttribute('data-will-load');

					return new SpeedwellSlider(
						sliderContainer,
						setupDOMSlideFn,
						renderSlideContentFn,
						interval
					);
				},
			};
		})()
	);

	if (!!Speedwell && !!Speedwell.features) {
		Speedwell.features.sliders = [];

		if (
			'sliderCallbacks' in Speedwell.features &&
			Speedwell.features.sliderCallbacks.length
		) {
			Liferay.componentReady('SpeedwellSlider')
				.then((sliderComponent) => {
					Speedwell.features.sliderCallbacks.forEach((callback) => {
						Speedwell.features.sliders.push(
							callback(sliderComponent)
						);
					});

					return Promise.resolve();
				})
				.catch((initError) => {
					console.error(initError);

					return Promise.resolve();
				})
				.then(() => {
					Speedwell.features.sliderCallbacks = [];
				});
		}
	}
});

const searchBarElement = document.querySelector('.speedwell-search');

Liferay.on('search-bar-toggled', ({active}) => {
	document.querySelectorAll('.js-toggle-search').forEach((element) => {
		element.classList.toggle('is-active', active);
	});

	document.getElementById('speedwell').classList.toggle('has-search', active);

	if (searchBarElement) {
		searchBarElement.classList.toggle('is-open', active);
	}
});

(function (w) {
	'use strict';

	const KEYDOWN_EVENT = 'keydown';
	const TAB_KEYCODE = 9;
	const ACCESSIBILITY_CLASS = 'is-accessible';
	const TIMEOUT = 5000;

	const removeAfter = setTimeout(() => {
		w.removeEventListener(KEYDOWN_EVENT, needsAccessibility);
		clearTimeout(removeAfter);
	}, TIMEOUT);

	function needsAccessibility(event) {
		const isTabbing = event.which === TAB_KEYCODE;

		if (isTabbing) {
			w.document.body.classList.add(ACCESSIBILITY_CLASS);
			w.removeEventListener(KEYDOWN_EVENT, needsAccessibility);
		}
	}

	w.addEventListener(KEYDOWN_EVENT, needsAccessibility);
})(window);

(function (w) {
	'use strict';

	const TOPBAR_CLASS = 'speedwell-topbar';
	const TRANSLUCENT_CLASS = TOPBAR_CLASS + '--translucent';
	const TOGGLE_PREFIX = '.js-toggle-';
	const SPEEDWELL_PREFIX = '.speedwell-';
	const IS_OPEN = 'is-open';
	const IS_BEHIND = 'is-behind';

	const TOGGLES = {
		ACCOUNT: {name: 'account'},
		MAIN_MENU: {name: 'main-menu'},
		SEARCH: {name: 'search'},
	};

	const CONTAINER = window.document.getElementById('speedwell');

	let TOPBAR;
	let translucencyIsEnabled = false;

	function hideFiltersButtonOnMenuOpen() {
		Liferay.componentReady('SpeedwellMobileHelpers').then(
			(mobileHelpers) => {
				const catalogFiltersButton = mobileHelpers.getFiltersButton();

				if (catalogFiltersButton) {
					catalogFiltersButton.classList.toggle(
						IS_BEHIND,
						!isOpen(catalogFiltersButton)
					);
				}
			}
		);
	}

	function attachListener(currentToggle) {
		const toggleWrapper = TOGGLES[currentToggle].wrapper;

		TOGGLES[currentToggle].buttons.forEach((button) => {
			button.addEventListener('click', (_e) => {
				Liferay.componentReady('SpeedwellCategoryMenu').then(
					(categoryMenu) => {
						const categoryEl = categoryMenu.getElement();

						button.focus();
						toggleWrapper.classList.toggle(
							IS_OPEN,
							!isOpen(toggleWrapper)
						);
						categoryEl.classList.remove(IS_OPEN);

						if (Liferay.Browser.isMobile()) {
							hideFiltersButtonOnMenuOpen();
						}
					}
				);
			});
		});
	}

	function enableToggles() {
		Object.keys(TOGGLES).forEach(attachListener);
	}

	function wipeToggles() {
		Object.keys(TOGGLES).forEach((currentToggle) => {
			delete TOGGLES[currentToggle].buttons;
			delete TOGGLES[currentToggle].wrapper;
		});
	}

	function prepareToggles() {
		wipeToggles();

		Object.keys(TOGGLES).forEach((toggle) => {
			TOGGLES[toggle].buttons = Array.from(
				TOPBAR.querySelectorAll(TOGGLE_PREFIX + TOGGLES[toggle].name)
			);

			TOGGLES[toggle].wrapper = TOPBAR.querySelector(
				SPEEDWELL_PREFIX + TOGGLES[toggle].name
			);
		});
	}

	function isOpen(toggleElement) {
		return toggleElement.classList.contains(IS_OPEN);
	}

	function toggleTranslucencyOnScroll(scrollThreshold) {
		const isBeyond = w.scrollY <= scrollThreshold;

		if (translucencyIsEnabled) {
			TOPBAR.classList.toggle(TRANSLUCENT_CLASS, isBeyond);
		}
	}

	function isTranslucent() {
		translucencyIsEnabled = TOPBAR.classList.contains(TRANSLUCENT_CLASS);
	}

	function selectElements() {
		TOPBAR = CONTAINER.querySelector('.' + TOPBAR_CLASS);
	}

	selectElements();
	prepareToggles();
	enableToggles();
	isTranslucent();

	if (translucencyIsEnabled) {
		Liferay.componentReady('SpeedwellScrollHandler').then(
			(scrollHandler) => {
				scrollHandler.registerCallback(toggleTranslucencyOnScroll);
			}
		);
	}
})(window);

Liferay.component(
	'SpeedwellCategoryMenu',
	(function () {
		const MAIN_LINK_SELECTOR = '.main-link';
		const CATEGORY_NAV_SELECTOR = '.speedwell-category-nav';
		const IS_OPEN = 'is-open';
		let linkElements;
		let categoryNavigationElement;

		const CONTAINER = document.getElementById('speedwell');

		function showCategoryNavigationMenu(event) {
			const isCatalogLink =
				event.currentTarget.href.indexOf('/car-parts') > -1 ||
				event.currentTarget.href.indexOf('/catalog') > -1;

			if (isCatalogLink) {
				categoryNavigationElement.focus();
				categoryNavigationElement.classList.add(IS_OPEN);
			}
			else {
				categoryNavigationElement.classList.remove(IS_OPEN);
			}
		}

		function hideCategoryNavigationMenu() {
			categoryNavigationElement.classList.remove(IS_OPEN);
		}

		function attachListeners() {
			if (!Liferay.Browser.isMobile()) {
				linkElements.forEach((link) => {
					link.addEventListener(
						'mouseover',
						showCategoryNavigationMenu
					);
				});

				categoryNavigationElement.addEventListener(
					'focusout',
					hideCategoryNavigationMenu
				);
			}
		}

		function selectElements() {
			linkElements = Array.from(
				CONTAINER.querySelectorAll(MAIN_LINK_SELECTOR)
			);

			categoryNavigationElement = CONTAINER.querySelector(
				CATEGORY_NAV_SELECTOR
			);
		}

		selectElements();
		attachListeners();

		return {
			getElement() {
				return categoryNavigationElement;
			},
		};
	})(),
	{destroyOnNavigate: true}
);

Liferay.component(
	'SpeedwellMobileHelpers',
	(function () {
		let filtersButton;
		let filtersHeader;
		let addToCartInline;
		let addToCartInlineDefaultPosition;

		const IS_OPEN_CLASS = 'is-open';
		const IS_FIXED_CLASS = 'is-fixed';

		function setupFiltersHeader() {
			filtersHeader.querySelector('.title').innerText =
				Liferay.Language.get('filters');
		}

		function listenToFiltersButton() {
			const filtersAreClosed =
				!filtersButton.classList.contains(IS_OPEN_CLASS);

			filtersButton.classList.toggle(IS_OPEN_CLASS, filtersAreClosed);

			filtersHeader
				.querySelector('.close-button')
				[
					filtersAreClosed
						? 'addEventListener'
						: 'removeEventListener'
				]('click', listenToFiltersButton);
		}

		function isFixed(element) {
			return element.classList.contains(IS_FIXED_CLASS);
		}

		function restoreAddToCartButton() {
			const isBelowViewport =
				window.scrollY < addToCartInlineDefaultPosition;

			if (isBelowViewport && isFixed(addToCartInline)) {
				addToCartInline.classList.remove(IS_FIXED_CLASS);
				window.removeEventListener('scroll', restoreAddToCartButton);
				window.addEventListener('scroll', fixAddToCartButton);
			}
		}

		function fixAddToCartButton() {
			const isAboveViewport =
				addToCartInline.getBoundingClientRect().top <= 0;

			if (isAboveViewport && !isFixed(addToCartInline)) {
				addToCartInline.classList.add(IS_FIXED_CLASS);
				window.removeEventListener('scroll', fixAddToCartButton);
				window.addEventListener('scroll', restoreAddToCartButton);
			}
		}

		function selectElements() {
			filtersButton = window.document.querySelector(
				'.mobile-filters-button'
			);
			filtersHeader = window.document.querySelector(
				'.mobile-filters-header'
			);
			addToCartInline = window.document.querySelector(
				'.add-to-cart-button--inline .commerce-button'
			);
		}

		selectElements();

		if (!!filtersButton && !!filtersHeader) {
			setupFiltersHeader();
			filtersButton.addEventListener('click', listenToFiltersButton);
		}

		if (addToCartInline) {
			addToCartInlineDefaultPosition =
				addToCartInline.getBoundingClientRect().top;
			window.addEventListener('scroll', fixAddToCartButton);
		}

		return {
			getFiltersButton() {
				return filtersButton;
			},
		};
	})(),
	{destroyOnNavigate: true}
);

Liferay.component(
	'SpeedwellScrollHandler',
	(function () {
		const SCROLL_EVENT = 'scroll';
		const callbackQueueOnScroll = {};

		function sign(x) {
			return (x > 0) - (x < 0) || +x;
		}

		let lastKnownScrollPosition = 0;
		let lastKnownScrollOffset = 0;
		let ticking = false;

		const scrollThreshold = 100;
		const myMap = new Map();

		myMap.set(-1, 'up');
		myMap.set(1, 'down');

		function handleOnScroll() {
			const offset = window.scrollY - lastKnownScrollPosition;

			lastKnownScrollPosition = window.scrollY;
			lastKnownScrollOffset =
				sign(offset) === sign(lastKnownScrollOffset)
					? lastKnownScrollOffset + offset
					: offset;

			if (!ticking) {
				window.requestAnimationFrame(() => {
					ticking = false;
				});

				ticking = true;
			}

			Object.keys(callbackQueueOnScroll).forEach((callbackName) => {
				callbackQueueOnScroll[callbackName](scrollThreshold);
			});
		}

		window.addEventListener(SCROLL_EVENT, handleOnScroll, false);

		return {
			registerCallback(callback) {
				callbackQueueOnScroll[callback.name] = callback;
			},

			unregisterCallback(callback) {
				delete callbackQueueOnScroll[callback.name];
			},
		};
	})(),
	{destroyOnNavigate: true}
);
