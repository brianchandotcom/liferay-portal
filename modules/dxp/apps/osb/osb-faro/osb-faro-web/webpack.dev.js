const common = require('./webpack.common');
const {responseInterceptor} = require('http-proxy-middleware');
const {merge} = require('webpack-merge');
const webpack = require('webpack');

require('dotenv').config();

const TARGET = (process.env.FARO_URL || 'http://0.0.0.0:8080').replace(
	/\/$/,
	''
);

// The dev server proxies to a remote Liferay (e.g. analytics-stg) whose
// `web.server.host` makes it emit absolute URLs back to itself. We buffer
// each response and rewrite those URLs (in Location, Set-Cookie Domain, and
// the body) so the browser stays on the dev server instead of leaving for
// the upstream host.

async function rewriteUpstreamResponse(responseBuffer, proxyRes, req, res) {
	const proxyOrigin = `http://${req.headers.host}`;

	if (proxyRes.headers['location']) {
		res.setHeader(
			'location',
			proxyRes.headers['location'].split(TARGET).join(proxyOrigin)
		);
	}

	if (proxyRes.headers['set-cookie']) {
		res.setHeader(
			'set-cookie',
			proxyRes.headers['set-cookie'].map(cookie =>
				cookie.replace(/;\s*Domain=[^;]+/gi, '')
			)
		);
	}

	const contentType = proxyRes.headers['content-type'] || '';
	const isTextual =
		contentType.includes('text/html') ||
		contentType.includes('javascript') ||
		contentType.includes('application/json') ||
		contentType.includes('text/css');

	if (!isTextual) {
		return responseBuffer;
	}

	return responseBuffer.toString('utf8').split(TARGET).join(proxyOrigin);
}

module.exports = merge(common.config, {
	devServer: {
		client: {
			overlay: false
		},
		host: '0.0.0.0',
		port: 3000,
		proxy: {
			'**': {
				changeOrigin: true,
				onProxyRes: responseInterceptor(rewriteUpstreamResponse),
				selfHandleResponse: true,
				target: TARGET
			}
		}
	},
	devtool: 'inline-source-map',
	mode: 'development',
	module: {
		rules: [
			{
				include: common.include,
				loader: 'liferay-lang-key-dev-loader',
				test: /\.(js|ts)x?$/
			}
		]
	},
	output: {
		chunkFilename: '[name].[chunkhash:8].js',
		publicPath: common.PUBLIC_PATH
	},
	plugins: [
		new webpack.DefinePlugin({
			FARO_DEV_MODE: true
		})
	]
});
