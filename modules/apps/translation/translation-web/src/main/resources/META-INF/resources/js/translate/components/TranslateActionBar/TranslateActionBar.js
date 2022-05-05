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
		<nav className="component-tbar subnav-tbar-light tbar">
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
								small
								type="submit"
							>
								{saveButtonLabel}
							</ClayButton>

							<ClayButton
								disabled={publishButtonDisabled}
								displayType="primary"
								small
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
