/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {Toggle} from '@liferay/object-js-components-web';
import {sub} from 'frontend-js-web';
import React from 'react';

import './ConfigurationContainer.scss';

interface ConfigurationContainerProps {
	hasUpdateObjectDefinitionPermission: boolean;
	isApproved: boolean;
	isEnableObjectEntrySchedule: boolean;
	isLinkedObjectDefinition?: boolean;
	onSubmit?: (editedObjectDefinition?: Partial<ObjectDefinition>) => void;
	setValues: (values: Partial<ObjectDefinition>) => void;
	values: Partial<ObjectDefinition>;
}

export function ConfigurationContainer({
	hasUpdateObjectDefinitionPermission,
	isApproved,
	isEnableObjectEntrySchedule,
	isLinkedObjectDefinition,
	onSubmit,
	setValues,
	values,
}: ConfigurationContainerProps) {
	const isReadOnly = !values.modifiable && values.system;

	const disabled =
		!hasUpdateObjectDefinitionPermission ||
		isLinkedObjectDefinition ||
		isReadOnly;

	return (
		<div className="lfr-objects__object-definition-details-configuration">
			<ClayForm.Group>
				<Toggle
					disabled={disabled}
					label={sub(
						Liferay.Language.get('show-widget-in-x'),
						Liferay.Language.get('page-builder')
					)}
					name="showWidget"
					onBlur={(event) => {
						event.stopPropagation();

						if (onSubmit) {
							onSubmit();
						}
					}}
					onToggle={() => setValues({portlet: !values.portlet})}
					toggled={values.portlet}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<Toggle
					disabled={disabled}
					label={sub(
						Liferay.Language.get('enable-x'),
						Liferay.Language.get('categorization-of-object-entries')
					)}
					name="enableCategorization"
					onBlur={(event) => {
						event.stopPropagation();

						if (onSubmit) {
							onSubmit();
						}
					}}
					onToggle={() =>
						setValues({
							enableCategorization: !values.enableCategorization,
						})
					}
					toggled={values.enableCategorization}
				/>
			</ClayForm.Group>

			<ClayForm.Group className="lfr-objects__comments-enable-comments">
				<Toggle
					disabled={disabled}
					label={sub(
						Liferay.Language.get('enable-x'),
						Liferay.FeatureFlags['LPD-69419']
							? Liferay.Language.get('comments')
							: Liferay.Language.get('comments-in-page-builder')
					)}
					name="enableComments"
					onBlur={(event) => {
						event.stopPropagation();

						if (onSubmit) {
							onSubmit();
						}
					}}
					onToggle={() =>
						setValues({
							enableComments: !values.enableComments,
						})
					}
					toggled={values.enableComments}
				/>

				{Liferay.FeatureFlags['LPD-69419'] && (
					<>
						&nbsp;
						<ClayTooltipProvider>
							<span
								title={Liferay.Language.get(
									'comments-are-supported-in-page-builder-and-api'
								)}
							>
								<ClayIcon
									className="lfr-objects__comments-tooltip-icon"
									symbol="question-circle-full"
								/>
							</span>
						</ClayTooltipProvider>
					</>
				)}
			</ClayForm.Group>

			<ClayForm.Group>
				<Toggle
					disabled={disabled || isApproved}
					label={sub(
						Liferay.Language.get('enable-x'),
						Liferay.Language.get('indexed-search')
					)}
					name="enableIndexSearch"
					onBlur={(event) => {
						event.stopPropagation();

						if (onSubmit) {
							onSubmit();
						}
					}}
					onToggle={() =>
						setValues({
							enableIndexSearch: !values.enableIndexSearch,
						})
					}
					toggled={values.enableIndexSearch}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<Toggle
					disabled={isLinkedObjectDefinition || isReadOnly}
					label={sub(
						Liferay.Language.get('enable-x'),
						Liferay.Language.get('entry-history-in-audit-framework')
					)}
					name="enableEntryHistory"
					onBlur={(event) => {
						event.stopPropagation();

						if (onSubmit) {
							onSubmit();
						}
					}}
					onToggle={() =>
						setValues({
							enableObjectEntryHistory:
								!values.enableObjectEntryHistory,
						})
					}
					toggled={values.enableObjectEntryHistory}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<Toggle
					disabled={disabled}
					label={Liferay.Language.get(
						'allow-users-to-save-entries-as-draft'
					)}
					name="enableObjectEntryDraft"
					onBlur={(event) => {
						event.stopPropagation();

						if (onSubmit) {
							onSubmit();
						}
					}}
					onToggle={() =>
						setValues({
							enableObjectEntryDraft:
								!values.enableObjectEntryDraft,
						})
					}
					toggled={values.enableObjectEntryDraft}
				/>
			</ClayForm.Group>

			{Liferay.FeatureFlags['LPD-17564'] && (
				<>
					<ClayForm.Group>
						<Toggle
							disabled={
								disabled ||
								(isEnableObjectEntrySchedule && isApproved)
							}
							label={Liferay.Language.get(
								'allow-users-to-schedule-a-display-expiration-and-review-date-for-entries'
							)}
							name="enableObjectEntrySchedule"
							onBlur={(event) => {
								event.stopPropagation();

								if (onSubmit) {
									onSubmit();
								}
							}}
							onToggle={() => {
								setValues({
									enableObjectEntrySchedule:
										!values.enableObjectEntrySchedule,
								});
							}}
							toggled={values.enableObjectEntrySchedule}
						/>
					</ClayForm.Group>

					<ClayForm.Group>
						<Toggle
							disabled={disabled}
							label={sub(
								Liferay.Language.get('enable-x'),
								Liferay.Language.get(
									'mapping-in-form-container'
								)
							)}
							name="enableFormContainer"
							onBlur={(event) => {
								event.stopPropagation();

								if (onSubmit) {
									onSubmit();
								}
							}}
							onToggle={() => {
								setValues({
									enableFormContainer:
										!values.enableFormContainer,
								});
							}}
							toggled={values.enableFormContainer}
						/>
					</ClayForm.Group>
				</>
			)}
		</div>
	);
}
