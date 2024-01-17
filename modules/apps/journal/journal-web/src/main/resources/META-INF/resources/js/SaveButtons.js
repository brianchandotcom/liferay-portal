/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import PublishModal from './modals/PublishModal';

export default function SaveButtons({
	articleId,
	defaultLanguageId,
	editingDefaultValues,
	permissionsURL,
	portletNamespace,
	publishButtonLabel,
	saveButtonLabel,
	selectedLanguageId,
}) {
	const [
		{publishModalAction, publishModalVisible},
		setPublishModalState,
	] = useState({publishModalAction: '', publishModalVisible: false});

	const onClick = (action) => {
		setPublishModalState({
			publishModalAction: action,
			publishModalVisible: true,
		});
	};

	const handleButtonClick = () => {
		document
			.querySelectorAll('.journal-alert-container')
			.forEach((alertElement) => {
				alertElement?.parentElement?.removeChild(alertElement);
			});

		const workflowActionInput = document.getElementById(
			`${portletNamespace}workflowAction`
		);

		if (publishModalAction === 'publish') {
			workflowActionInput.value = Liferay.Workflow.ACTION_PUBLISH;
		}

		const actionInput = document.getElementById(
			`${portletNamespace}javax-portlet-action`
		);

		if (editingDefaultValues) {
			Liferay.component(`${portletNamespace}dataEngineLayoutRenderer`)
				.reactComponentRef.current.getFields()
				.forEach((field) => {
					field.required = false;
				});

			actionInput.value = articleId
				? '/journal/update_data_engine_default_values'
				: '/journal/add_data_engine_default_values';
		}
		else {
			articleId = document.getElementById(`${portletNamespace}articleId`)
				.value;

			actionInput.value = articleId
				? '/journal/update_article'
				: '/journal/add_article';
		}

		const descriptionInputComponent = Liferay.component(
			`${portletNamespace}descriptionMapAsXML`
		);
		const titleInputComponent = Liferay.component(
			`${portletNamespace}titleMapAsXML`
		);

		[titleInputComponent, descriptionInputComponent].forEach(
			(inputComponent) => {
				const translatedLanguages = inputComponent.get(
					'translatedLanguages'
				);

				if (
					!translatedLanguages.has(selectedLanguageId) &&
					selectedLanguageId !== defaultLanguageId
				) {
					inputComponent.updateInput('');

					Liferay.Form.get(`${portletNamespace}fm1`).removeRule(
						`${portletNamespace}${inputComponent.get('id')}`,
						'required'
					);
				}
			}
		);
	};

	const dropdownItems = [
		{
			label: Liferay.Language.get('publish'),
			onClick: () => onClick('publish'),
			symbolLeft: 'arrow-right-full',
		},
		{
			label: Liferay.Language.get('schedule-publication'),
			onClick: () => {
				setPublishModalState({
					publishModalAction: 'schedule',
					publishModalVisible: false,
				});
				onClick('schedule');
			},
			symbolLeft: 'date-time',
		},
	];

	return (
		<div className="d-flex">
			{!Liferay.FeatureFlags['LPS-141392'] && !editingDefaultValues ? (
				<ClayButton
					className="mr-1"
					displayType="secondary"
					onClick={() => onClick('draft')}
				>
					{saveButtonLabel}
				</ClayButton>
			) : null}

			<ClayDropDownWithItems
				items={dropdownItems}
				trigger={
					<ClayButton>
						{publishButtonLabel}

						<span className="inline-item inline-item-after">
							<ClayIcon symbol="caret-bottom" />
						</span>
					</ClayButton>
				}
			/>

			{publishModalVisible ? (
				<PublishModal
					actionButton={publishModalAction}
					onCloseModal={() =>
						setPublishModalState({
							publishModalAction: '',
							publishModalVisible: false,
						})
					}
					onPublishButtonClick={handleButtonClick}
					permissionsURL={permissionsURL}
					portletNamespace={portletNamespace}
				/>
			) : null}
		</div>
	);
}
