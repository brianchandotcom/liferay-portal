/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Disposable utility. When inherited provides the `dispose` function to its
 * subclass, which is responsible for disposing of any object references
 * when an instance won't be used anymore. Subclasses should override
 * `disposeInternal` to implement any specific disposing logic.
 * @constructor
 */
class Disposable {

	/**
	 * Disposable constructor
	 */
	constructor() {

		/**
		 * Flag indicating if this instance has already been disposed.
		 * @type {boolean}
		 * @protected
		 */
		this._disposed = false;
	}

	/**
	 * Disposes of this instance's object references. Calls `disposeInternal`.
	 */
	dispose() {
		if (!this._disposed) {
			this.disposeInternal();
			this._disposed = true;
		}
	}

	/**
	 * Subclasses should override this method to implement any specific
	 * disposing logic (like clearing references and calling `dispose` on other
	 * disposables).
	 */
	disposeInternal() {}

	/**
	 * Checks if this instance has already been disposed.
	 * @return {boolean}
	 */
	isDisposed() {
		return this._disposed;
	}
}

export default Disposable;
