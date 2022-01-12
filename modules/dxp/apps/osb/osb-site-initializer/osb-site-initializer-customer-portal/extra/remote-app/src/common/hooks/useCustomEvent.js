/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

export function useCustomEvent(name) {
	return (data) => {
		try {
			const event = Liferay.publish(name, {
				async: true,
				fireOnce: true,
			});

			event.fire({
				detail: data,
			});
		}
		catch {
			window.dispatchEvent(
				new CustomEvent(name, {
					bubbles: true,
					composed: true,
					detail: data,
				})
			);
		}
	};
}
