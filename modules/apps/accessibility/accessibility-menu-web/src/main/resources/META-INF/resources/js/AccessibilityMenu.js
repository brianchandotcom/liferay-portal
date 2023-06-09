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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import {checkCookieConsentForTypes} from '@liferay/cookies-banner-web';
import {COOKIE_TYPES, checkConsent} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import AccessibilitySetting from './AccessibilitySetting';
import {getSettingValue, toggleClassName} from './util';

const OPEN_ACCESSIBILITY_MENU_EVENT_NAME = 'openAccessibilityMenu';

const AccessibilityMenu = ({accessibilitySettings}) => {
	const [
		hasFunctionalCookiesConsent,
		setHasFunctionalCookiesConsent,
	] = useState(checkConsent(COOKIE_TYPES.FUNCTIONAL));

	const {observer, onOpenChange, open} = useModal();

	useEffect(() => {
		const openAccessibilityMenu = () => onOpenChange(true);

		Liferay.on(OPEN_ACCESSIBILITY_MENU_EVENT_NAME, openAccessibilityMenu);

		return () => {
			Liferay.detach(
				OPEN_ACCESSIBILITY_MENU_EVENT_NAME,
				openAccessibilityMenu
			);
		};
	}, [accessibilitySettings, onOpenChange]);

	const handleReviewCookies = () => {
		checkCookieConsentForTypes(COOKIE_TYPES.FUNCTIONAL)
			.then(() => {
				setHasFunctionalCookiesConsent(true);
			})
			.catch(() => {
				setHasFunctionalCookiesConsent(false);
			});
	};

	const isSettingsDisabled =
		!hasFunctionalCookiesConsent && !themeDisplay.isSignedIn();

	return (
		<>
			{open && (
				<ClayModal observer={observer} size="md">
					<ClayModal.Header>
						{Liferay.Language.get('accessibility-help-menu')}
					</ClayModal.Header>

					<ClayModal.Body>
						{isSettingsDisabled && (
							<ClayAlert
								className="mb-4"
								displayType="info"
								title={`${Liferay.Language.get('info')}:`}
							>
								{Liferay.Language.get(
									'accessibility-menu-cookies-alert'
								)}

								<ClayAlert.Footer>
									<ClayButton.Group>
										<ClayButton
											alert
											onClick={handleReviewCookies}
										>
											{Liferay.Language.get(
												'review-cookies'
											)}
										</ClayButton>
									</ClayButton.Group>
								</ClayAlert.Footer>
							</ClayAlert>
						)}

						<ul className="list-unstyled mb-0">
							{accessibilitySettings.map((setting, index) => (
								<AccessibilitySetting
									className={
										index + 1 < accessibilitySettings.length
											? 'mb-3'
											: ''
									}
									disabled={isSettingsDisabled}
									key={setting.key}
									setting={setting}
								/>
							))}
						</ul>
					</ClayModal.Body>
				</ClayModal>
			)}
		</>
	);
};

AccessibilityMenu.propTypes = {
	accessibilitySettings: PropTypes.arrayOf(
		PropTypes.shape({
			className: PropTypes.string,
			defaultValue: PropTypes.string,
			key: PropTypes.string,
			label: PropTypes.string,
			sessionClicksValue: PropTypes.string,
		})
	).isRequired,
};

export default AccessibilityMenu;
