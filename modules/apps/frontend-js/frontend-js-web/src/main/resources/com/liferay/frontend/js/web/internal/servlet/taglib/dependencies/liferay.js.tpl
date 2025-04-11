(function() {
	function buildESMStub(contextPath, symbol) {
		return (
			(...args) => {
				import(
					Liferay.ThemeDisplay.getPathContext() +
						'/o/' +
						contextPath +
						'/__liferay__/index.js'
				).then(
					(exports) => exports[symbol](...args)
				);
			}
		);
	}

	let __liferay = {
[$DEFINITION$]
	};

	if (window.Liferay) {
		window.Liferay = {
			...window.Liferay,
			...__liferay,
			__disableOverwriteCheck: true
		}
	}
	else {
		Object.defineProperty(
			window,
			"Liferay",
			{
				get: () => __liferay,
				set: (x) => {
					if (x.hasOwnProperty("Loader")) {
						__liferay.Loader = x.Loader;
						return;
					}

					if (x.hasOwnProperty("__disableOverwriteCheck")) {
						delete x.__disableOverwriteCheck;
						__liferay = x;
						return;
					}

					console.warn("Global variable 'Liferay' is read-only");
				}
			}
		);

		const themeDisplayLocations = new Set();

		Object.defineProperty(
			window,
			"themeDisplay",
			{
				get: () => {
					if ([$DEV_MODE$]) {
						let location = new Error().stack.split('\n')[1];

						if (location.includes(':')) {
							location = location.split(':')[0];
						}

						if (!themeDisplayLocations.has(location)) {
							console.warn("Global variable 'themeDisplay' is deprecated. Use 'Liferay.ThemeDisplay' instead.");

							themeDisplayLocations.add(location);
						}
					}

					return window.Liferay.ThemeDisplay;
				},
				set: () => {
					console.warn("Global variable 'themeDisplay' is read-only");
				}
			}
		);
	}
})();