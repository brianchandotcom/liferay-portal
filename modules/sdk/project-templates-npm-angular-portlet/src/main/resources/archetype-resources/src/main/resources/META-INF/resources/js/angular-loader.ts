// Import needed polyfills before application is launched
import 'reflect-metadata';
import 'zone.js';

// Declare Liferay AMD loader
declare var Liferay: any;

// Launch application
export default function() {
    Liferay.Loader.require('${artifactId}@${pkgJsonVersion}/js/main', (main: any) => {
        main.default();
    });
}
