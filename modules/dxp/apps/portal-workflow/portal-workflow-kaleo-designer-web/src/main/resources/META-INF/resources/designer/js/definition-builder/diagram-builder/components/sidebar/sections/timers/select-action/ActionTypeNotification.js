/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import React, {useState} from 'react';

const ActionTypeNotification = () => {
	const [notificationSections, setNotificationSections] = useState([
		{identifier: `${Date.now()}-0`},
	]);

	const deleteSection = (identifier) => {
		setNotificationSections((prevSections) => {
			const newSections = prevSections.filter(
				(prevSection) => prevSection.identifier !== identifier
			);

			return newSections;
		});
	};

	return notificationSections.map(({identifier}) => {
		return (
			<div key={`section-${identifier}`}>
				<div>Notification Placeholder {identifier}</div>

				<div className="section-buttons-area">
					<ClayButton
						className="mr-3"
						displayType="secondary"
						onClick={() =>
							setNotificationSections((prev) => {
								return [
									...prev,
									{
										identifier: `${Date.now()}-${
											prev.length
										}`,
									},
								];
							})
						}
					>
						Add Button Placeholder
					</ClayButton>

					{notificationSections.length > 1 && (
						<ClayButtonWithIcon
							className="delete-button"
							displayType="unstyled"
							onClick={() => deleteSection(identifier)}
							symbol="trash"
						/>
					)}
				</div>
			</div>
		);
	});
};

export default ActionTypeNotification;
