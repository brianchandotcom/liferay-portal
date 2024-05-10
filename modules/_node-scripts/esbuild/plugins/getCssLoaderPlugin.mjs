import getFlatName from '../../util/getFlatName.mjs';
import getPathPrefix from '../getPathPrefix.mjs';
	
export default function getCssLoaderPlugin(globalImports, type) {
	return {
		name: 'css-loader-plugin',

		setup(build) {
			build.onResolve(
				{ 
					filter: /\.css$/ 
				}, 
				(args) => ({path: `/$/css/${args.path}`})
			);

			build.onLoad(
				{ 
					filter: /\/\$\/css\/.*$/ 
				}, 
				async (args) => {
					const path = args.path.replace('/$/css/', '');
					
					if (!globalImports[path]) {
						throw new Error(`Cannot rewrite CSS import: ${path}`);
					}
					
					const {webContextPath} = globalImports[path];

					const contents = `
import '${getPathPrefix(type)}/${webContextPath}/__liferay__/exports/${getFlatName(path)}.js';
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
