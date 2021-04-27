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
import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import React from 'react';

import TranslateLanguagesSelector from './TranslateLanguagesSelector';

const TransLateActionBar = ({
	autoTranslateEnabled,
	fetchAutoTranslateFields,
	fetchAutoTranslateStatus,
	formHasChanges,
	onSaveButtonClick,
	portletNamespace,
	publishButtonDisabled,
	publishButtonLabel,
	redirectURL,
	saveButtonDisabled,
	saveButtonLabel,
	translateLanguagesSelectorData,
}) => {
	const {message, status} = fetchAutoTranslateStatus;
	const isLoading = status === 'LOADING';

	return (
		<nav className="component-tbar subnav-tbar-light tbar">
			<ClayLayout.ContainerFluid view>
				<ul className="tbar-nav">
					<li
						className={classNames('tbar-item', {
							'tbar-item-expand': !autoTranslateEnabled,
						})}
					>
						<TranslateLanguagesSelector
							{...translateLanguagesSelectorData}
							formHasChanges={formHasChanges}
							portletNamespace={portletNamespace}
						/>
					</li>
					{autoTranslateEnabled && (
						<>
							<li className="tbar-item">
								<ClayButton
									disabled={isLoading}
									displayType="secondary"
									onClick={fetchAutoTranslateFields}
									small
									type="button"
								>
									{Liferay.Language.get('auto-translate')}
								</ClayButton>
							</li>
							<li className="tbar-item tbar-item-expand text-left">
								{isLoading && (
									<div>
										<span className="inline-item inline-item-before">
											<ClayLoadingIndicator small />
										</span>
										<span className="inline-item">
											{Liferay.Language.get(
												'requesting-translation'
											)}
										</span>
									</div>
								)}
								{status === 'SUCCESS' && (
									<div className="has-success">
										<ClayForm.FeedbackItem className="mt-0">
											<ClayForm.FeedbackIndicator symbol="check-circle-full" />
											{message}
										</ClayForm.FeedbackItem>
									</div>
								)}
								{status === 'ERROR' && (
									<div className="has-error">
										<ClayForm.FeedbackItem className="mt-0">
											<ClayForm.FeedbackIndicator symbol="exclamation-full" />
											{message}
										</ClayForm.FeedbackItem>
									</div>
								)}
							</li>
						</>
					)}
					<li className="tbar-item">
						<div className="metadata-type-button-row tbar-section text-right">
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
						</div>
					</li>
				</ul>
			</ClayLayout.ContainerFluid>
		</nav>
	);
};

export default TransLateActionBar;
