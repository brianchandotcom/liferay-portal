/**
 *
 * @param {String} name
 * @returns {Function}
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
