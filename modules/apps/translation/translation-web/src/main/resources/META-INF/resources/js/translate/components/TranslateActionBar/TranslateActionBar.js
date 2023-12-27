/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import React from 'react';

import AutoTranslate from './AutoTranslate';
import ExperienceSelector from './ExperienceSelector';
import TranslateLanguagesSelector from './TranslateLanguagesSelector';

const TransLateActionBar = ({
	autoTranslateEnabled,
	confirmChangesBeforeReload,
	experienceSelectorData,
	fetchAutoTranslateFields,
	fetchAutoTranslateStatus,
	onSaveButtonClick,
	publishButtonDisabled,
	publishButtonLabel,
	redirectURL,
	saveButtonDisabled,
	saveButtonLabel,
	translateLanguagesSelectorData,
}) => {
	return (
		<nav className="management-bar management-bar-light navbar navbar-expand-md">
			<ClayLayout.ContainerFluid view>
				<ul className="tbar-nav">
					{experienceSelectorData && (
						<li className="tbar-item">
							<ExperienceSelector
								{...experienceSelectorData}
								confirmChangesBeforeReload={
									confirmChangesBeforeReload
								}
							/>
						</li>
					)}

					<li className="tbar-item">
						<TranslateLanguagesSelector
							{...translateLanguagesSelectorData}
							confirmChangesBeforeReload={
								confirmChangesBeforeReload
							}
						/>
					</li>

					{autoTranslateEnabled && (
						<li className="tbar-item">
							<AutoTranslate
								fetchAutoTranslateFields={
									fetchAutoTranslateFields
								}
								fetchAutoTranslateStatus={
									fetchAutoTranslateStatus
								}
							/>
						</li>
					)}

					<li className="align-items-end tbar-item tbar-item-expand">
						<ClayButton.Group spaced>
							<ClayLink
								button={{small: true}}
								displayType="secondary"
								href={redirectURL}
							>
								{Liferay.Language.get('cancel')}
							</ClayLink>

							<ClayButton
								disabled={saveButtonDisabled}
								displayType="secondary"
								onClick={onSaveButtonClick}
								size="sm"
								type="submit"
							>
								{saveButtonLabel}
							</ClayButton>

							<ClayButton
								disabled={publishButtonDisabled}
								displayType="primary"
								size="sm"
								type="submit"
							>
								{publishButtonLabel}
							</ClayButton>
						</ClayButton.Group>
					</li>
				</ul>
			</ClayLayout.ContainerFluid>
		</nav>
	);
};

export default TransLateActionBar;
