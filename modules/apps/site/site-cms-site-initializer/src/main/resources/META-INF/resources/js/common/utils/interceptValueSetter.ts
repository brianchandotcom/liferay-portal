/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type EditableElement = HTMLInputElement | HTMLTextAreaElement;

const interceptedElements = new WeakSet<EditableElement>();

/**
 * Wraps the native `value` setter of an input or textarea so that
 * programmatic `element.value = '...'` assignments invoke a callback.
 *
 * Programmatic value assignments don't fire native `input` or `change`
 * events. This util is useful when detecting value changes that come
 * from scripts rather than direct user interaction — e.g. custom
 * fragments that update hidden/visible inputs imperatively.
 *
 * Scope is per-instance (only the passed element is affected),
 * so the global `HTMLInputElement.prototype` is not mutated. Calling
 * it more than once on the same element is a no-op.
 *
 * @param element The input or textarea to patch.
 * @param onValueChanged Called whenever `.value` is assigned a value
 * different from the current one.
 */
export default function interceptValueSetter(
	element: EditableElement,
	onValueChanged: () => void
): void {
	if (interceptedElements.has(element)) {
		return;
	}

	const prototype =
		element instanceof HTMLTextAreaElement
			? HTMLTextAreaElement.prototype
			: HTMLInputElement.prototype;

	const descriptor = Object.getOwnPropertyDescriptor(prototype, 'value');

	if (!descriptor?.get || !descriptor?.set) {
		return;
	}

	const nativeGet = descriptor.get;
	const nativeSet = descriptor.set;

	interceptedElements.add(element);

	Object.defineProperty(element, 'value', {
		configurable: true,
		get() {
			return nativeGet.call(this);
		},
		set(newValue) {
			const oldValue = nativeGet.call(this);

			nativeSet.call(this, newValue);

			if (oldValue !== nativeGet.call(this)) {
				onValueChanged();
			}
		},
	});
}
