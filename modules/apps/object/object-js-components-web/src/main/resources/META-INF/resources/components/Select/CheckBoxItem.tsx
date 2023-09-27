/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

interface IProps {
	checked?: boolean;
	label: string;
	onChange: React.ChangeEventHandler<HTMLInputElement>;
}

export function CheckboxItem({checked, label, onChange}: IProps) {
	return (
		<li>
			<div className={`dropdown-item ${checked ? 'hover' : ''}`}>
				<div className="custom-checkbox custom-control">
					<label>
						<input
							checked={checked}
							className="custom-control-input"
							onChange={onChange}
							type="checkbox"
						/>

						<span className="custom-control-label">
							<span className="custom-control-label-text">
								{label}
							</span>
						</span>
					</label>
				</div>
			</div>
		</li>
	);
}
