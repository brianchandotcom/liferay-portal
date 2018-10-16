// Import needed polyfills before application is launched
import 'core-js/es7/reflect';
import 'zone.js/dist/zone';

// Declare Liferay AMD loader
declare var Liferay: any;

// Launch application
export default function(rootId: string) {
	Liferay.Loader.require(
		'${artifactId}@${packageJsonVersion}/lib/main',
		(main: any) => {
			main.default(rootId);
		},
	);
}