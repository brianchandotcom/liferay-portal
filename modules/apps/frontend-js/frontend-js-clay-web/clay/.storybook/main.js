/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-env node */

const path = require('path');

const config = {
	addons: [
		{
			name: '@storybook/addon-essentials',
			options: {
				docs: false,
			},
		},
		{
			name: '@storybook/addon-storysource',
			options: {
				loaderOptions: {
					parser: 'typescript',
				},
				rule: {
					test: /stories\/.*\.tsx$/,
				},
			},
		},

		'@storybook/addon-a11y',
		'@storybook/preset-scss',
	],
	babel: async (babelConfig) => ({
		...babelConfig,
		presets: [
			[
				'@babel/preset-env',
				{
					exclude: ['transform-regenerator'],
					targets: ['defaults'],
				},
			],
			'@babel/preset-react',
			'@babel/preset-typescript',
		],
	}),
	core: {
		builder: 'webpack5',
	},
	features: {
		postcss: false,
		storyStoreV7: true,
	},
	framework: '@storybook/react',
	stories: ['../*/stories/*.stories.@(js|jsx|ts|tsx)'],
	typescript: {
		checkOptions: {
			tsconfig: path.resolve(__dirname, '../tsconfig.json'),
		},
		reactDocgen: false,
	},
	webpackFinal: (config) => {
		config.resolve.alias = {
			'@clayui/alert': path.resolve(__dirname, '../clay-alert/src'),
			'@clayui/autocomplete': path.resolve(
				__dirname,
				'../clay-autocomplete/src'
			),
			'@clayui/badge': path.resolve(__dirname, '../clay-badge/src'),
			'@clayui/breadcrumb': path.resolve(
				__dirname,
				'../clay-breadcrumb/src'
			),
			'@clayui/button': path.resolve(__dirname, '../clay-button/src'),
			'@clayui/card': path.resolve(__dirname, '../clay-card/src'),
			'@clayui/charts': path.resolve(__dirname, '../clay-charts/src'),
			'@clayui/color-picker': path.resolve(
				__dirname,
				'../clay-color-picker/src'
			),
			'@clayui/core': path.resolve(__dirname, '../clay-core/src'),
			'@clayui/data-provider': path.resolve(
				__dirname,
				'../clay-data-provider/src'
			),
			'@clayui/date-picker': path.resolve(
				__dirname,
				'../clay-date-picker/src'
			),
			'@clayui/drop-down': path.resolve(
				__dirname,
				'../clay-drop-down/src'
			),
			'@clayui/empty-state': path.resolve(
				__dirname,
				'../clay-empty-state/src'
			),
			'@clayui/form': path.resolve(__dirname, '../clay-form/src'),
			'@clayui/icon': path.resolve(__dirname, '../clay-icon/src'),
			'@clayui/label': path.resolve(__dirname, '../clay-label/src'),
			'@clayui/layout': path.resolve(__dirname, '../clay-layout/src'),
			'@clayui/link': path.resolve(__dirname, '../clay-link/src'),
			'@clayui/list': path.resolve(__dirname, '../clay-list/src'),
			'@clayui/loading-indicator': path.resolve(
				__dirname,
				'../clay-loading-indicator/src'
			),
			'@clayui/localized-input': path.resolve(
				__dirname,
				'../clay-localized-input/src'
			),
			'@clayui/management-toolbar': path.resolve(
				__dirname,
				'../clay-management-toolbar/src'
			),
			'@clayui/modal': path.resolve(__dirname, '../clay-modal/src'),
			'@clayui/multi-select': path.resolve(
				__dirname,
				'../clay-multi-select/src'
			),
			'@clayui/multi-step-nav': path.resolve(
				__dirname,
				'../clay-multi-step-nav/src'
			),
			'@clayui/nav': path.resolve(__dirname, '../clay-nav/src'),
			'@clayui/navigation-bar': path.resolve(
				__dirname,
				'../clay-navigation-bar/src'
			),
			'@clayui/pagination': path.resolve(
				__dirname,
				'../clay-pagination/src'
			),
			'@clayui/pagination-bar': path.resolve(
				__dirname,
				'../clay-pagination-bar/src'
			),
			'@clayui/panel': path.resolve(__dirname, '../clay-panel/src'),
			'@clayui/popover': path.resolve(__dirname, '../clay-popover/src'),
			'@clayui/progress-bar': path.resolve(
				__dirname,
				'../clay-progress-bar/src'
			),
			'@clayui/provider': path.resolve(__dirname, '../clay-provider/src'),
			'@clayui/shared': path.resolve(__dirname, '../clay-shared/src'),
			'@clayui/slider': path.resolve(__dirname, '../clay-slider/src'),
			'@clayui/sticker': path.resolve(__dirname, '../clay-sticker/src'),
			'@clayui/table': path.resolve(__dirname, '../clay-table/src'),
			'@clayui/tabs': path.resolve(__dirname, '../clay-tabs/src'),
			'@clayui/time-picker': path.resolve(
				__dirname,
				'../clay-time-picker/src'
			),
			'@clayui/toolbar': path.resolve(__dirname, '../clay-toolbar/src'),
			'@clayui/tooltip': path.resolve(__dirname, '../clay-tooltip/src'),
			'@clayui/upper-toolbar': path.resolve(
				__dirname,
				'../clay-upper-toolbar/src'
			),
		};

		return config;
	},
};

module.exports = config;
