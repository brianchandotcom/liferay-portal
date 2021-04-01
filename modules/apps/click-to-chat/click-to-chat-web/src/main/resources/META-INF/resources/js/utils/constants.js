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

export const PROVIDERS_HELP_TEXT = {
	chatwoot: {
		url: '',
	},
	jivochat: {
		url: '',
	},
};

export const PROVIDERS = [
	{
		label: Liferay.Language.get('choose-an-option'),
		value: 'choose_an_option',
	},
	{
		label: 'ChatWoot',
		value: 'chatwoot',
	},
	{
		label: 'Crisp',
		value: 'crisp',
	},
	{
		label: 'JivoChat',
		value: 'jivochat',
	},
	{
		label: 'Liveperson',
		value: 'liveperson',
	},
	{
		label: 'Smartsupp',
		value: 'smartsupp',
	},
	{
		label: 'Tidio',
		value: 'tidio',
	},
];

export const STRATEGIES = [
	{
		label: Liferay.Language.get('choose-an-option'),
		value: 'choose_an_option',
	},
	{
		label: Liferay.Language.get(
			'group-account-number-strategy.ALWAYS_INHERIT'
		),
		value: 'always_inherit',
	},
	{
		label: Liferay.Language.get('group-account-number-strategy.PROVIDE'),
		value: 'provide',
	},
	{
		label: Liferay.Language.get(
			'group-account-number-strategy.PROVIDE_OR_INHERIT'
		),
		value: 'provide_or_inherit',
	},
];

export const STRATEGY_HELP_TEXT = {
	always_inherit:
		'Always use the "Default Account Number" above for every site. This account number cannot be changed or overridden. This is useful is you want all provider requests to go to a single account regardless of where the request originates',
	choose_an_option:
		'Choose the strategy for determining the provider account that each site should connect to',
	provide: `An account number can be specified per site by going to [Your Site] -> Configuration -> Settings -> Custom Fields -> Click to Chat Embedded Script. However, if it's left blank, the site will use the "Default Embedded Script". This is useful to provide a global default and allow sites to customize if needed`,
	provide_or_inherit: `An account number can be specified per site by going to [Your Site] -> Configuration -> Settings -> Custom Fields -> Click to Chat Embedded Script. If it's left blank, this effectively disables provider for the given site. This is useful to force all sites to provide their own account number explicitly`,
};
