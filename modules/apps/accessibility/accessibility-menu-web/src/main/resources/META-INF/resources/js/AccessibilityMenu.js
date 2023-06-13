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
import {
	COOKIE_TYPES,
	checkConsent,
	localStorage,
	setSessionValue,
} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import AccessibilitySetting from './AccessibilitySetting';
import {getSettingValue, toggleClassName} from './util';

const OPEN_ACCESSIBILITY_MENU_EVENT_NAME = 'openAccessibilityMenu';

const getInitialSettings = (settings) => {
	return settings.map((setting) => {
		const {
			className,
			defaultValue,
			key,
			label,
			sessionClicksValue,
		} = setting;

		const value = getSettingValue(defaultValue, sessionClicksValue, key);

		toggleClassName(className, value);

		return {className, key, label, value};
	});
};

const AccessibilityMenu = (props) => {
	const [settings, setSettings] = useState(() =>
		getInitialSettings(props.settings)
	);

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
	}, [onOpenChange]);

	const updateSetting = useCallback((settingKey, settingUpdates) => {
		setSettings((settings) =>
			settings.map((setting) => {
				if (settingKey === setting.key) {
					return {
						...setting,
						...settingUpdates,
					};
				}
				else {
					return setting;
				}
			})
		);
	}, []);

	const afterSettingValueChange = useCallback(
		(value, setting) => {
			toggleClassName(setting.className, value);

			updateSetting(setting.key, {disabled: false, value});
		},
		[updateSetting]
	);

	const handleAccessiblitySettingChange = useCallback(
		(value, setting) => {
			updateSetting(setting.key, {disabled: true});

			if (themeDisplay.isSignedIn()) {
				setSessionValue(setting.key, value).then(() => {
					afterSettingValueChange(value, setting);
				});
			}
			else {
				localStorage.setItem(
					setting.key,
					value,
					localStorage.TYPES.FUNCTIONAL
				);

				afterSettingValueChange(value, setting);
			}
		},
		[afterSettingValueChange, updateSetting]
	);

	const handleReviewCookies = useCallback(() => {
		checkCookieConsentForTypes(COOKIE_TYPES.FUNCTIONAL)
			.then(() => {
				setHasFunctionalCookiesConsent(true);
			})
			.catch(() => {
				setHasFunctionalCookiesConsent(false);
			});
	}, []);

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
							{settings.map((setting, index) => (
								<AccessibilitySetting
									className={
										index + 1 < settings.length
											? 'mb-3'
											: ''
									}
									disabled={
										isSettingsDisabled || setting.disabled
									}
									key={setting.key}
									label={setting.label}
									onChange={(value) =>
										handleAccessiblitySettingChange(
											value,
											setting
										)
									}
									value={setting.value}
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
	settings: PropTypes.arrayOf(
		PropTypes.shape({
			className: PropTypes.string,
			defaultValue: PropTypes.bool,
			key: PropTypes.string,
			label: PropTypes.string,
			sessionClicksValue: PropTypes.bool,
		})
	).isRequired,
};

export default AccessibilityMenu;
