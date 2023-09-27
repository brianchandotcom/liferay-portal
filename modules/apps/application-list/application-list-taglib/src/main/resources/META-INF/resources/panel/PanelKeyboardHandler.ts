/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const ARROW_KEYS = {
	ArrowDown: 'ArrowDown',
	ArrowLeft: 'ArrowLeft',
	ArrowRight: 'ArrowRight',
	ArrowUp: 'ArrowUp',
};

const FOCUSABLE_ELEMENT_SELECTOR = 'a';

export default function PanelKeyboardHandler({categoryKey, namespace}) {
	let focusedElement: HTMLElement | null = null;
	const wrapper = document.getElementById(`${namespace}${categoryKey}_panel`);

	const focusElement = (element: HTMLElement) => {
		if (wrapper.contains(element)) {
			if (focusedElement) {
				focusedElement.setAttribute('tabindex', '-1');
			}

			focusedElement = element;

			element.setAttribute('tabindex', '0');
			element.focus();
		}
	};

	const handleKeyDown = (event: KeyboardEvent) => {
		const {activeElement} = document;

		if (ARROW_KEYS[event.key] && activeElement === wrapper) {
			const firstFocusableChild = Array.from(
				wrapper.querySelectorAll(FOCUSABLE_ELEMENT_SELECTOR)
			).find((element) => isVisible(element));

			if (firstFocusableChild) {
				event.preventDefault();
				focusElement(firstFocusableChild);
			}
		}
		else if (
			ARROW_KEYS[event.key] &&
			activeElement instanceof HTMLElement &&
			wrapper.contains(activeElement)
		) {
			event.preventDefault();

			if (event.key === 'ArrowDown') {
				focusElement(getFocusableSibling(activeElement, 'down'));
			}
			else if (event.key === 'ArrowUp') {
				focusElement(getFocusableSibling(activeElement, 'up'));
			}
			else if (event.key === 'ArrowRight') {
				const expandedAttribute = activeElement.getAttribute(
					'aria-expanded'
				);

				if (expandedAttribute === 'false') {
					activeElement.click();
				}
				else {
					focusElement(getFocusableChild(wrapper, activeElement));
				}
			}
			else if (event.key === 'ArrowLeft') {
				const expandedAttribute = activeElement.getAttribute(
					'aria-expanded'
				);

				if (expandedAttribute === 'true') {
					activeElement.click();
				}
				else {
					focusElement(getFocusableParent(wrapper, activeElement));
				}
			}
		}
	};

	wrapper
		.querySelectorAll(FOCUSABLE_ELEMENT_SELECTOR)
		.forEach((element, index) => {
			if (index === 0) {
				focusedElement = element;

				element.setAttribute('tabindex', '0');
			}
			else {
				element.setAttribute('tabindex', '-1');
			}
		});

	wrapper.addEventListener('keydown', handleKeyDown, {capture: true});

	return {
		dispose() {
			wrapper.removeEventListener('keydown', handleKeyDown, {
				capture: true,
			});
		},
	};
}

function isVisible(element: Element): boolean {
	const computedStyle = window.getComputedStyle(element);
	const {parentElement} = element;

	return (
		computedStyle.display !== 'none' &&
		computedStyle.visibility !== 'collapse' &&
		computedStyle.visibility !== 'hidden' &&
		!element.hasAttribute('hidden') &&
		(!parentElement ||
			parentElement.classList.contains('page-editor__form-children') ||
			isVisible(parentElement))
	);
}

function isFocusable(element: Element) {
	return element.matches(FOCUSABLE_ELEMENT_SELECTOR);
}

function getFocusableChild(wrapper: HTMLElement, origin: HTMLElement) {
	if (origin.dataset.toggle === 'liferay-collapse') {
		const collapseElement = origin.nextElementSibling;

		if (collapseElement instanceof HTMLElement) {
			const childElement = collapseElement.querySelector(
				FOCUSABLE_ELEMENT_SELECTOR
			);

			if (
				childElement &&
				isVisible(childElement) &&
				isFocusable(childElement) &&
				wrapper.contains(childElement)
			) {
				return childElement;
			}
		}
	}

	return origin;
}

function getFocusableParent(wrapper: HTMLElement, origin: HTMLElement) {
	const collapseElement = origin.closest('.collapse');

	if (collapseElement instanceof HTMLElement) {
		const parentElement = getFocusableSibling(collapseElement, 'up');

		if (
			parentElement &&
			isVisible(parentElement) &&
			isFocusable(parentElement) &&
			wrapper.contains(parentElement)
		) {
			return parentElement;
		}
	}

	return origin;
}

function getFocusableSibling(
	origin: HTMLElement,
	direction: 'up' | 'down'
): HTMLElement {
	const findFocusableSibling = (
		element: HTMLElement | null
	): HTMLElement | null => {
		if (!element) {
			return null;
		}

		const nextElement =
			direction === 'up'
				? element.previousElementSibling
				: element.nextElementSibling;

		if (nextElement instanceof HTMLElement) {
			if (isVisible(nextElement)) {
				if (isFocusable(nextElement)) {
					return nextElement;
				}

				const focusableChildren = Array.from(
					nextElement.querySelectorAll(FOCUSABLE_ELEMENT_SELECTOR)
				).filter((element) => isVisible(element));

				if (focusableChildren.length) {
					if (direction === 'up') {
						return focusableChildren[focusableChildren.length - 1];
					}
					else {
						return focusableChildren[0];
					}
				}
			}

			return findFocusableSibling(nextElement);
		}

		if (element.parentElement) {
			return findFocusableSibling(element.parentElement);
		}

		return null;
	};

	return findFocusableSibling(origin) || origin;
}
