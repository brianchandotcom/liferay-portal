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
					focusElement(getFocusableSibling(activeElement, 'down'));
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
					focusElement(getFocusableSibling(activeElement, 'up'));
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
