const sassRegex = /\.(scss|sass)$/;

module.exports = {
	babel: {
		plugins: [
			[
				'babel-plugin-root-import',
				{
					paths: [
						{
							rootPathPrefix: '~/',
							rootPathSuffix: './src',
						},
						{
							rootPathPrefix: '~/common',
							rootPathSuffix: './src/common',
						},
						{
							rootPathPrefix: '~/routes',
							rootPathSuffix: './src/routes',
						},
					],
				},
			],
		],
	},
	webpack: {
		configure: (webpackConfig) => {
			/**
			 * This change is necessary to import SCSS as string
			 * to inject into style tag
			 */

			webpackConfig.module.rules[1].oneOf.unshift({
				exclude: /node_modules/,
				test: sassRegex,
				use: [
					'sass-to-string',
					{
						loader: 'sass-loader',
						options: {
							sassOptions: {
								outputStyle: 'compressed',
							},
						},
					},
				],
			});

			/**
			 * Avoid hashes in filenames
			 */

			webpackConfig.output.chunkFilename = 'static/js/[name].js';
			webpackConfig.output.filename = 'static/js/[name].js';

			return webpackConfig;
		},
	},
};
