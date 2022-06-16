/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import react from '@vitejs/plugin-react';
import path from 'path';
import {defineConfig} from 'vite';
import {UserConfigExport as VitestConfigExport} from 'vitest/config';

export default defineConfig({
	build: {assetsDir: 'static', outDir: 'build'},
	plugins: [react()],
	test: {
		coverage: {
			all: true,
			exclude: [
				path.resolve(__dirname),
				'src/graphql/fragments',
				path.resolve(__dirname),
				'src/graphql/queries',
				path.resolve(__dirname),
				'src/graphql/mutations',
			],
			include: [path.resolve(__dirname), 'src'],
		},
		environment: 'jsdom',
		exclude: ['node_modules', 'build'],
		globals: true,
		include: ['**/(*.)?{test,spec}.{ts,tsx}'],
		setupFiles: ['./src/setupTests.ts'],
	},
} as VitestConfigExport);
