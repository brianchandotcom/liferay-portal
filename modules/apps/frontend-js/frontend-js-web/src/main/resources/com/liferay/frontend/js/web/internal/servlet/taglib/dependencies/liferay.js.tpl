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

	function defineReadOnlyGlobal(name, getValue) {
		Object.defineProperty(
			window,
			name,
			{
				get: getValue,
				set: (x) => {
					if (x !== getValue()) {
						console.error(`Global variable '${name}' is read-only`);
					}
				}
			}
		);
	}

	function isObject(item) {
		return (item && typeof item === 'object' && !Array.isArray(item));
	}

	function merge(target, source) {
		for (const key in source) {
			if (isObject(source[key])) {
				if (!target[key]) {
					Object.assign(target, { [key]: {} });
				}

				merge(target[key], source[key]);
			}
else {
				Object.assign(target, { [key]: source[key] });
			}
		}
	}

	let __liferay = {
[$DEFINITION$]
	};

	if (window.Liferay) {
		merge(window.Liferay, __liferay);
	}
	else {
		defineReadOnlyGlobal('Liferay', () => __liferay);
		defineReadOnlyGlobal('themeDisplay', () => window.Liferay.ThemeDisplay);
	}
})();