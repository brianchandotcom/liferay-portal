export function useCustomEvent(name) {
	try {
		const event = Liferay.publish(name, {
			async: true,
			fireOnce: true,
		});

		const dispatch = (data) =>
			event.fire({
				detail: data,
			});

		return dispatch;
	}
	catch {
		const dispatch = (data) => {
			window.dispatchEvent(
				new CustomEvent(name, {
					bubbles: true,
					composed: true,
					detail: data,
				})
			);
		};

		return dispatch;
	}
}
