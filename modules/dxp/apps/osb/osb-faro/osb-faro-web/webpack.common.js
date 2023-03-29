const BundleQueryStringPlugin = require('./bundle-query-string-webpack-plugin');
const clayCss = require('@clayui/css');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const path = require('path');
const SpriteLoaderPlugin = require('svg-sprite-loader/plugin');
const webpack = require('webpack');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');

const PUBLIC_PATH = '/o/osb-faro-web/dist/';

function resolveModule(name = '') {
	return path.resolve(__dirname, 'src', 'main', 'js', name);
}

const include = [
	resolveModule(),
	path.resolve(__dirname, 'node_modules', 'isemail'),
	path.resolve(__dirname, 'node_modules', 'query-string'),
	path.resolve(__dirname, 'node_modules', 'strict-uri-encode')
];

const config = {
	entry: [
		'core-js/fn/array/fill',
		'core-js/fn/string/code-point-at',
		'core-js/fn/typed',
		'custom-event-polyfill',
		'unorm',
		'whatwg-fetch',
		resolveModule('main.jsx')
	],
	module: {
		rules: [
			{
				include,
				loader: 'ts-loader',
				options: {
					transpileOnly: true
				},
				resolve: {
					alias: {
						assets: resolveModule('assets'),
						'cerebro-shared': resolveModule('cerebro-shared'),
						'clay-charts-react': resolveModule('clay-charts-react'),
						commerce: resolveModule('commerce'),
						contacts: resolveModule('contacts'),
						'custom-types': resolveModule('custom-types'),
						'event-analysis': resolveModule('event-analysis'),
						experiments: resolveModule('experiments'),
						home: resolveModule('home'),
						individual: resolveModule('individual'),
						'route-middleware': resolveModule('route-middleware'),
						segment: resolveModule('segment'),
						settings: resolveModule('settings'),
						shared: resolveModule('shared'),
						sites: resolveModule('sites'),
						test: resolveModule('test'),
						touchpoints: resolveModule('touchpoints'),
						'ui-kit': resolveModule('ui-kit')
					},
					extensions: ['.js', '.jsx', '.ts', '.tsx']
				},
				test: /\.(js|ts)x?$/
			},
			{
				loader: 'graphql-tag/loader',
				test: /\.graphql$/
			},
			{
				include: path.resolve(__dirname, 'src', 'main', 'css'),
				test: /\.scss$/,
				use: [
					MiniCssExtractPlugin.loader,
					{
						loader: 'css-loader',
						options: {
							importLoaders: 2
						}
					},
					{
						loader: 'postcss-loader',
						options: {
							ident: 'postcss',
							plugins: () => [require('autoprefixer')()]
						}
					},
					{
						loader: 'sass-loader',
						options: {
							includePaths: clayCss.includePaths.concat(
								path.join(clayCss.includePaths[0], '../fonts')
							)
						}
					}
				]
			},
			{
				test: /\.svg$/,
				use: [
					{
						loader: 'svg-sprite-loader',
						options: {
							extract: true,
							spriteFilename: 'sprite.svg'
						}
					},
					{
						loader: 'svgo-loader',
						options: {
							plugins: [
								{removeDimensions: true},
								{removeUselessStrokeAndFill: false},
								{removeViewBox: false}
							]
						}
					}
				]
			},
			{
				test: /\.(eot|ttf|woff|woff2)(\?v=\d+\.\d+\.\d+)?$/,
				use: 'file-loader'
			}
		]
	},
	output: {
		filename: 'main.js',
		path: path.resolve('src/main/resources/META-INF/resources/dist'),
		pathinfo: false,
		publicPath: PUBLIC_PATH
	},
	plugins: [
		new MiniCssExtractPlugin({
			filename: 'main.css'
		}),
		new BundleQueryStringPlugin(),
		new ForkTsCheckerWebpackPlugin({
			eslint: {
				files: 'src/main/js/**/*.+(js|ts)?(x)'
			}
		}),
		new SpriteLoaderPlugin(),
		new webpack.DefinePlugin({
			FARO_ENV: JSON.stringify(process.env.FARO_ENVIRONMENT_NAME || '')
		}),
		new webpack.IgnorePlugin(/^\.\/locale$/, /moment$/)
	],
	target: 'web'
};

module.exports = {
	config,
	include,
	publicPath: PUBLIC_PATH,
	resolve: {
		extensions: ['', '.js', '.jsx', '.ts', '.tsx'],
		root: [resolveModule()]
	}
};
