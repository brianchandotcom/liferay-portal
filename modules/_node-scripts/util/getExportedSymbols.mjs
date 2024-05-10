import projectScopeRequire from './projectScopeRequire.mjs';

const cachedExportedSymbols = {};

export default function getExportedSymbols(globalSymbols, moduleName) {
	if (cachedExportedSymbols[moduleName]) {
		return cachedExportedSymbols;
	}

	let symbols;

	try {
		if (globalSymbols[moduleName]) {
			symbols = {};

			globalSymbols[moduleName].forEach(
				symbol => symbols[symbol] = true
			);

			if (symbols['*']) {
				delete symbols['*'];

				Object.keys(loadSymbols(moduleName)).forEach(
					symbol => symbols[symbol] = true
				);
			}
		}
		else {
			symbols = loadSymbols(moduleName);
		}
	}
	catch(error) {
		throw new Error(
			`Cannot infer exported symbols for ${moduleName}: ${error}`
		);
	}

	return symbols;
}

function loadSymbols(moduleName) {
	const module = projectScopeRequire(moduleName);

	const symbols = Object.keys(module).reduce(
		(symbols, key) => {
			symbols[key] = true;

			return symbols;
		},
		{}
	);

	// Some modules config __esModule as non-enumerable, so we explicitly check for it
	
	if (module.__esModule) {
		symbols.__esModule = true;
	}

	return symbols;
}
