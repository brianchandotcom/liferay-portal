import pathToRegexp from 'path-to-regexp';
import {isNil, isString, zip} from 'lodash';

function comparePaths(curRoute, nextRoute) {
	return zip(curRoute, nextRoute).every(([curPath, nextPath]) => {
		if (isNil(curPath) || isNil(nextPath)) {
			return false;
		}

		const parsedPath = pathToRegexp.parse(curPath)[0];
		const nextParsedPath = pathToRegexp.parse(nextPath)[0];

		const pathIsDynamic = typeof parsedPath === 'object';
		const nextPathIsDynamic = typeof nextParsedPath === 'object';

		return (
			(pathIsDynamic &&
				!nextPathIsDynamic &&
				pathToRegexp(curPath).exec(nextParsedPath)) ||
			(pathIsDynamic &&
				nextPathIsDynamic &&
				(!!parsedPath.pattern.match(nextParsedPath.pattern) ||
					!!nextParsedPath.pattern.match(parsedPath.pattern))) ||
			curPath === nextPath
		);
	});
}

export default function (routesArr) {
	const splitRoutes = routesArr.map(route =>
		isString(route) ? route.split('/') : route
	);

	return splitRoutes
		.map((splitRoute, i) => {
			const remainingRoutes = splitRoutes.slice(i + 1);

			for (const nextRoute of remainingRoutes) {
				if (comparePaths(splitRoute, nextRoute)) {
					return nextRoute.join('/');
				}
			}
		})
		.filter(Boolean);
}
