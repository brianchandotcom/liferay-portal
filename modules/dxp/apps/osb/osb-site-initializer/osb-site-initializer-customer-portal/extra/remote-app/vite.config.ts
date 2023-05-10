/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import react from '@vitejs/plugin-react-swc';
import path from 'path';
import {defineConfig} from 'vite';
import {UserConfigExport} from 'vitest/config';

export default defineConfig({
	build: {
		assetsDir: 'static',
		outDir: 'build',
		rollupOptions: {
			output: {
				assetFileNames: 'static/[name].[hash][extname]',
				chunkFileNames: 'static/[name].js',
				entryFileNames: 'static/[name].js',
			},
		},
	},
	plugins: [react()],
	resolve: {
		alias: {
			'~': path.resolve(__dirname, './src/'),
		},
	},
	server: {
		port: 3000,
	},
	test: {
		coverage: {
			all: true,
			include: [path.resolve(__dirname), 'src'],
		},
		environment: 'jsdom',
		exclude: ['node_modules', 'build'],
		globals: true,
		include: ['**/(*.)?{test,spec}.{ts,tsx,js,jsx}'],
		setupFiles: ['./src/setupTests.ts'],
	},
} as UserConfigExport);
