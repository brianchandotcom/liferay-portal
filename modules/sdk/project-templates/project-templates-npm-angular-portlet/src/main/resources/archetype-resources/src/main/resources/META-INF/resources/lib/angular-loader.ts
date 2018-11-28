import 'core-js/es7/reflect';
import 'zone.js/dist/zone';

declare var Liferay: any;

export default function(rootId: string) {
	Liferay.Loader.require(
		'${artifactId}@${packageJsonVersion}/lib/main',
		(main: any) => {
			main.default(rootId);
		},
	);
}