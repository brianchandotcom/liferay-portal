import {useEffect, useState} from 'react';

export function useLoadingObserver() {
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		const observer = new MutationObserver(() => {
			const loadingElement = document.querySelectorAll(
				'.page-container .loading-animation'
			);

			if (!loadingElement.length) {
				observer.disconnect();

				setLoading(false);
			}
		});

		observer.observe(document.body, {
			attributes: true,
			characterData: true,
			childList: true,
			subtree: true
		});

		return () => {
			observer.disconnect();
		};
	}, []);

	return loading;
}
