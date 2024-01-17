/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
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
	timeZone,
}) {
	const formId = `${portletNamespace}fm1`;

	const [
		{publishModalAction, publishModalVisible},
		setPublishModalState,
	] = useState({publishModalAction: '', publishModalVisible: false});

	const onClick = (action) => {
		if (articleId) {
			handleButtonClick(action);
		}
		else {
			setPublishModalState({
				publishModalAction: action,
				publishModalVisible: true,
			});
		}
	};

	const handleButtonClick = (action) => {
		document
			.querySelectorAll('.journal-alert-container')
			.forEach((alertElement) => {
				alertElement?.parentElement?.removeChild(alertElement);
			});

		const workflowActionInput = document.getElementById(
			`${portletNamespace}workflowAction`
		);

		if (
			action === 'publish' ||
			publishModalAction === 'publish' ||
			publishModalAction === 'schedule'
		) {
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

					Liferay.Form.get(formId).removeRule(
						`${portletNamespace}${inputComponent.get('id')}`,
						'required'
					);
				}
			}
		);
	};

	return (
		<div className="d-flex">
			{!Liferay.FeatureFlags['LPS-141392'] && !editingDefaultValues ? (
				<ClayButton
					className="mr-1"
					displayType="secondary"
					form={formId}
					onClick={() => onClick('draft')}
					type={articleId ? 'submit' : 'button'}
				>
					{saveButtonLabel}
				</ClayButton>
			) : null}

			<ClayDropDown
				hasLeftSymbols
				trigger={
					<ClayButton>
						{publishButtonLabel}

						<span className="inline-item inline-item-after">
							<ClayIcon symbol="caret-bottom" />
						</span>
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList>
					<ClayDropDown.Item
						form={formId}
						onClick={() => onClick('publish')}
						symbolLeft="arrow-right-full"
						type={articleId ? 'submit' : 'button'}
					>
						{Liferay.Language.get('publish')}
					</ClayDropDown.Item>

					<ClayDropDown.Item
						onClick={() => {
							setPublishModalState({
								publishModalAction: 'schedule',
								publishModalVisible: true,
							});
						}}
						symbolLeft="date-time"
					>
						{Liferay.Language.get('schedule-publication')}
					</ClayDropDown.Item>
				</ClayDropDown.ItemList>
			</ClayDropDown>

			{publishModalVisible ? (
				<PublishModal
					actionButton={publishModalAction}
					articleId={articleId}
					onCloseModal={() =>
						setPublishModalState({
							publishModalAction: '',
							publishModalVisible: false,
						})
					}
					onPublishButtonClick={handleButtonClick}
					permissionsURL={permissionsURL}
					portletNamespace={portletNamespace}
					timeZone={timeZone}
				/>
			) : null}
		</div>
	);
}
