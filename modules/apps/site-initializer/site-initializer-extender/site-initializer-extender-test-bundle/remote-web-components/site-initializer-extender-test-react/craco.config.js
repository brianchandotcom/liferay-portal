const sassRegex = /\.(scss|sass)$/;

module.exports = {
	webpack: {
		configure: (webpackConfig, {env, paths}) => {
			/**
			 * This change is necessary to import SCSS as string
			 * to inject into style tag
			 */

			webpackConfig.module.rules[1].oneOf.unshift({
				test: sassRegex,
				exclude: /node_modules/,
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

			return webpackConfig;
		},
	},
};
