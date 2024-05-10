import path from 'path';

import { SRC_PATH } from '../../util/constants.mjs';

export default function getScssLoaderPlugin(projectWebContextPath) {
	return {
		name: 'scss-loader-plugin',

		setup(build) {
			build.onLoad(
				{ 
					filter: /\.scss$/ 
				}, 
				async (args) => {
					const projectPath = path.relative(SRC_PATH, args.path);

					const cssPath = projectPath
						.split(path.sep)
						.join(path.posix.sep)
						.replace(/scss$/, 'css');

					const contents = `
const link = document.createElement('link');
link.setAttribute('rel', 'stylesheet');
link.setAttribute('type', 'text/css');
link.setAttribute(
	'href', 
	Liferay.ThemeDisplay.getPathContext() + '/o${projectWebContextPath}/${cssPath}'
);
document.querySelector('head').appendChild(link);
`;

					return {
						contents,
						loader: 'js',
					};
				}
			);
		},
	};
}
