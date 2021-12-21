export function useCustomEvent(event) {
	const dispatch = (data) => {
		window.dispatchEvent(
			new CustomEvent(event, {
				bubbles: true,
				composed: true,
				detail: {...data},
			})
		);
	};

	return dispatch;
}
