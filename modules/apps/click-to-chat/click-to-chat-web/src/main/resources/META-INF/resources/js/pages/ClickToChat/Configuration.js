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

import ClayButton from '@clayui/button';
import ClayForm, {
	ClayCheckbox,
	ClayInput,
	ClaySelectWithOption,
} from '@clayui/form';
import React, {useCallback, useState} from 'react';

import {
	PROVIDERS,
	PROVIDERS_HELP_TEXT,
	STRATEGIES,
	STRATEGY_HELP_TEXT,
} from '../../utils/constants';
import {sub} from '../../utils/lang';

const defaultState = {
	enable: false,
	provider: 'choose_an_option',
	strategy: 'choose_an_option',
	token: '',
};

const ClickToChatConfiguration = () => {
	const [form, setForm] = useState(defaultState);

	const setFormValue = ({target: {checked, name, value}}) => {
		let newForm = {
			...form,
			[name]: name === 'enable' ? checked : value,
		};

		if (name === 'strategy' && value === 'provide') {
			newForm = {
				...newForm,
				provider: 'choose_an_option',
				token: '',
			};
		}

		setForm(newForm);
	};

	const getFormStatus = useCallback(() => {
		let canSave = true;
		const {enable, provider, strategy, token} = form;
		const formDisabled =
			!enable || ['choose_an_option', 'provide'].includes(strategy);

		if (enable) {
			if (
				strategy !== 'provide' &&
				(provider === 'choose_an_option' || !token)
			) {
				canSave = false;
			}
		}

		return {canSave, formDisabled};
	}, [form]);

	const onSubmit = (event) => {
		event.preventDefault();
	};

	const {canSave, formDisabled} = getFormStatus();

	return (
		<ClayForm onSubmit={onSubmit}>
			<ClayForm.Group>
				<ClayCheckbox
					checked={form.enable}
					label={Liferay.Language.get('enable-click-to-chat')}
					name="enable"
					onChange={setFormValue}
				/>
			</ClayForm.Group>
			<ClayForm.Group>
				<label>{Liferay.Language.get('strategy')}</label>
				<ClaySelectWithOption
					disabled={!form.enable}
					name="strategy"
					onChange={setFormValue}
					options={STRATEGIES}
				/>
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						{STRATEGY_HELP_TEXT[form.strategy] ||
							STRATEGY_HELP_TEXT.choose_an_option}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			</ClayForm.Group>

			<ClayForm.Group>
				<label>{Liferay.Language.get('provider')}</label>
				<ClaySelectWithOption
					disabled={formDisabled}
					name="provider"
					onChange={setFormValue}
					options={PROVIDERS}
					value={form.provider}
				/>
				{PROVIDERS_HELP_TEXT[form.provider] && (
					<ClayForm.FeedbackGroup className="mt-4">
						<ClayForm.FeedbackItem>
							<a
								href={PROVIDERS_HELP_TEXT[form.provider].url}
								target="__blank"
							>
								{sub(
									Liferay.Language.get(
										'how-do-i-get-my-id-for-x'
									),
									[
										PROVIDERS.find(
											({value}) => value === form.provider
										).label,
									]
								)}
							</a>
						</ClayForm.FeedbackItem>
					</ClayForm.FeedbackGroup>
				)}
			</ClayForm.Group>

			<ClayForm.Group>
				<label>{Liferay.Language.get('provider-account-token')}</label>
				<ClayInput
					disabled={formDisabled}
					name="token"
					onChange={setFormValue}
					placeholder={Liferay.Language.get(
						'provider-account-token-description'
					)}
					value={form.token}
				/>
			</ClayForm.Group>
			<ClayButton disabled={!canSave} type="submit">
				{Liferay.Language.get('save')}
			</ClayButton>
			<ClayButton className="ml-2" displayType="secondary">
				{Liferay.Language.get('cancel')}
			</ClayButton>
		</ClayForm>
	);
};

export default ClickToChatConfiguration;
