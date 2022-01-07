export function useCustomEvent(event) {
	const dispatch = (data, _event = event) => {
		window.dispatchEvent(
			new CustomEvent(_event, {
				bubbles: true,
				composed: true,
				detail: {data},
			})
		);
	};

	return [dispatch];
}
